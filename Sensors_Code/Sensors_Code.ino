#include<Arduino.h>
#include <OneWire.h>
#include <DallasTemperature.h>

//Defining all the Analog Pins on the Arduino Board
int Turbidity_pin = A0;
const byte TDS_pin = A1;
const byte Temperature_pin = A2;
int PH_pin = A3;
int DO_pin = A4;
OneWire oneWire(Temperature_pin);
DallasTemperature dallasTemperature(&oneWire);

//Defining all the pins as inputs and outputs in the Arduino
void setup(){
  pinMode(0,OUTPUT);
  pinMode(1,OUTPUT);
  pinMode(2,OUTPUT);
  pinMode(3,OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(5,OUTPUT);
  pinMode(6,OUTPUT);
  pinMode(7,OUTPUT);
  pinMode(8,OUTPUT);
  pinMode(9,OUTPUT);
  pinMode(10,OUTPUT);
  pinMode(11,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
  pinMode(A5,OUTPUT);
  Serial.begin(9600);
  dallasTemperature.begin();
}

void loop()
{
  sensor_index(0,0,0);
  convert_binary(Turbidity());
  delay(1000);
  
  tds_ec_temp();
  sensor_index(1,0,0);
  convert_binary(ph());
  delay(1000);
  
  sensor_index(1,0,1);
  convert_binary(Dissolved_Oxygen);
  delay(1000);
}

//Determining the Turbidity value from the Turbidity Sensor
int Turbidity(){
  float Turbidity_Sensor_Voltage = 0;
  int samples = 600;
  float Turbidity_NTU;
  for(int i=0; i<samples; i++)
  {
    Turbidity_Sensor_Voltage += ((float)analogRead(Turbidity_pin)/1024)*5;
  }
  Turbidity_Sensor_Voltage = (Turbidity_Sensor_Voltage/samples);
  if(Turbidity_Sensor_Voltage < 2.5){
    Turbidity_NTU = 3000;
  }
  else{
    Turbidity_NTU = -1120.4*square(Turbidity_Sensor_Voltage)+ 5742.3*Turbidity_Sensor_Voltage - 4352.9; 
  }
  return Turbidity_NTU;
}

//Determining the TDS, Temperature and Electrical Coductivity values from the TDS and Temperature Sensors
void tds_ec_temp(){
  float ecCalibration = 2;
  dallasTemperature.requestTemperatures();
  float waterTemp = dallasTemperature.getTempCByIndex(0);
  float rawEc = analogRead(TDS_pin) * 5 / 1024.0;
  float temperatureCoefficient = 1.0 + 0.02 * (waterTemp - 25.0);   // temperature compensation formula: fFinalResult(25^C) = fFinalResult(current)/(1.0+0.02*(fTP-25.0));
  float ec = (rawEc / temperatureCoefficient) * ecCalibration;  // temperature and calibration compensation
  unsigned int tds = (133.42 * pow(ec, 3) - 255.86 * ec * ec + 857.39 * ec) * 0.5;   //convert voltage value to tds value
  int tdsv= round(abs(tds));
  int ecv =round(abs(ec)*100);
  int tempv = round(abs(waterTemp));
  sensor_index(0,0,1);
  convert_binary(tdsv);
  delay(1000);
  sensor_index(0,1,0);
  convert_binary(ecv);
  delay(1000);
  sensor_index(0,1,1);
  convert_binary(tempv);
  delay(1000);
}

//Determining the pH value from the pH Sensor
int ph() {
  float calibration_value = 21.34-0.7;
  unsigned long int avgph = 0; 
  int ph_array[10],temp;
  for(int i=0;i<10;i++) { 
    ph_array[i] = analogRead(PH_pin);
  }
  for(int i=0;i<9;i++){
    for(int j=i+1;j<10;j++){
      if(ph_array[i] > ph_array[j]){
        temp = ph_array[i];
        ph_array[i] = ph_array[j];
        ph_array[j] = temp;
      }
    }
  }
  for(int i=2;i<8;i++)
    avgph += ph_array[i];
  float ph_voltage = (float)avgph*5.0/1024/6; 
  float ph_value = -5.70 * ph_voltage + calibration_value;
  int ph_int = round(ph_value*10);
  return ph_int;
}

//Determining the Dissolved Oxygen content from the Dissolved Oxygen Sensor
#define VREF 5000    //VREF (mv)
#define ADC_RES 1024 //ADC Resolution
#define TWO_POINT_CALIBRATION 0
#define CAL1_V (1860) //mv
#define CAL1_T (25)   //℃
#define CAL2_V (1300) //mv
#define CAL2_T (15)   //℃
const uint16_t DO_Table[] = {
    14460, 14220, 13820, 13440, 13090, 12740, 12420, 12110, 11810, 11530,
    11260, 11010, 10770, 10530, 10300, 10080, 9860, 9660, 9460, 9270,
    9080, 8900, 8730, 8570, 8410, 8250, 8110, 7960, 7820, 7690,
    7560, 7430, 7300, 7180, 7070, 6950, 6840, 6730, 6630, 6530, 6410};
uint8_t Temperature;
uint16_t ADC_Raw;
uint16_t ADC_Voltage;
uint16_t DO;
int16_t readDO(uint32_t voltage_mv, uint8_t temperature_c)
{
  #if TWO_POINT_CALIBRATION == 00
    uint16_t V_saturation = (uint32_t)CAL1_V + (uint32_t)35 * temperature_c - (uint32_t)CAL1_T * 35;
    return (voltage_mv * DO_Table[temperature_c] / V_saturation);
  #else
    uint16_t V_saturation = (int16_t)((int8_t)temperature_c - CAL2_T) * ((uint16_t)CAL1_V - CAL2_V) / ((uint8_t)CAL1_T - CAL2_T) + CAL2_V;
    return (voltage_mv * DO_Table[temperature_c] / V_saturation);
  #endif
}
 
int Dissolved_Oxygen()
{ 
  dallasTemperature.requestTemperatures();
  uint8_t Temperature = dallasTemperature.getTempCByIndex(0);
  ADC_Raw = analogRead(DO_pin);
  ADC_Voltage = uint32_t(VREF) * ADC_Raw / ADC_RES;
  int DO =round(readDO(ADC_Voltage, Temperature)/10);
  return DO;
}

//Converting the Sensor Value to Binary
void convert_binary(int n){
  int a[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
  int i=0, pin=0;
  while(i<=11){
    a[i]=n%2;;
    digitalWrite(pin,a[i]);
    Serial.print(a[i]);
    pin+=1;
    n=n/2;
    i++;
  }
  Serial.print("\n");
}

//Generating the index for the Sensor Values
void sensor_index(int a,int b,int c){
  digitalWrite(12,a);
  Serial.print(a);
  digitalWrite(13,b);
  Serial.print(b);
  digitalWrite(A5,c);
  Serial.print(c);
  Serial.print("\n");
}
