import subprocess
import time
import time
import json
from unicodedata import name
from azure.storage.blob import BlobServiceClient, BlobClient, ContainerClient

#Initialing all the water quality parameters to Zero
Turbidity = 0
TDS = 0
Temperature = 0
EC = 0
PH = 0
DO = 0
flag = 0
sensor_data = {"Turbidity": 0, "TDS": 0, "EC": 0, "Temp": 0, "ph": 0, "do": 0, "Decision": 0, "Turbidityd": 0, "TDSd": 0, "ECd": 0, "phd": 0, "dod": 0}

#Exporting a GPIO Pin
def export(pin):
    subprocess.Popen("echo %d > /sys/class/gpio/export"%(pin) , shell=True, stdout=subprocess.PIPE)

#Defining a GPIO Pin as a input pin
def direction(pin,direct):
    subprocess.Popen("echo %s > /sys/class/gpio/gpio%d/direction"%(direct,pin) , shell=True, stdout=subprocess.PIPE)

#Assigning a value to a GPIO Pin
def value(pin,val):
    subprocess.Popen("echo %d > /sys/class/gpio/gpio%d/value"%(val,pin) , shell=True, stdout=subprocess.PIPE)

#Reading the value of a GPIO Pin
def read(pin):
    s=subprocess.Popen("cat /sys/class/gpio/gpio%d/value"%(pin) , shell=True, stdout=subprocess.PIPE)
    v=s.stdout.read()
    return int(v)

#Exporting a GPIO Pin
def unexport(pin):
    subprocess.Popen("echo %d > /sys/class/gpio/unexport"%(pin) , shell=True, stdout=subprocess.PIPE)

#Finding the sensor data from the digital data
def sensor_value():
    value = 0
    for i in range(14):
        pin = 1804+i
        value += read(pin)*(2**j)
    return value

#Decision making for Drinking Water
def Drinking():
    good_parameters = 0
    if (Turbidity < 5):
        sensor_data["Turbidityd"] = 1
        good_parameters += 1
    else :
        sensor_data["Turbidityd"] = 0
        
    if (50 < TDS < 300):
        sensor_data["TDSd"] = 1
        good_parameters += 1
    else :
        sensor_data["TDSd"] = 0
        
    if (200 < EC < 800):
        sensor_data["ECd"] = 1
        good_parameters += 1
    else :
        sensor_data["ECd"] = 0
        
    if (6.5 < PH < 8.5):
        sensor_data["phd"] = 1
        good_parameters += 1
    else :
        sensor_data["phd"] = 0
        
    if (6.5 < DO < 8):
        sensor_data["dod"] = 1
        good_parameters += 1
    else :
        sensor_data["dod"] = 0
    sensor_data["Decision"] = water_quality(good_parameters)

#Decision making for Industrial Water
def Industrial():
    good_parameters = 0
    if (Turbidity < 1):
        sensor_data["Turbidityd"] = 1
        good_parameters += 1
    else :
        sensor_data["Turbidityd"] = 0
        
    if (TDS < 900):
        sensor_data["TDSd"] = 1
        good_parameters += 1
    else :
        sensor_data["TDSd"] = 0
        
    if (EC > 10000):
        sensor_data["ECd"] = 1
        good_parameters += 1
    else :
        sensor_data["ECd"] = 0
        
    if (6.8 < PH < 8.6):
        sensor_data["phd"] = 1
        good_parameters += 1
    else :
        sensor_data["phd"] = 0
        
    if (7.3 < DO < 9.5):
        sensor_data["dod"] = 1
        good_parameters += 1
    else :
        sensor_data["dod"] = 0
    sensor_data["Decision"] = water_quality(good_parameters)

#Decision making for Agricultural Water
def Agriculture():
    good_parameters = 0
    if (Turbidity < 10):
        sensor_data["Turbidityd"] = 1
        good_parameters += 1
    else :
        sensor_data["Turbidityd"] = 0
        
    if (450 < TDS < 2000):
        sensor_data["TDSd"] = 1
        good_parameters += 1
    else :
        sensor_data["TDSd"] = 0
        
    if (700 < EC < 1400):
        sensor_data["ECd"] = 1
        good_parameters += 1
    else :
        sensor_data["ECd"] = 0
        
    if (4.5 < PH < 8.5):
        sensor_data["phd"] = 1
        good_parameters += 1
    else :
        sensor_data["phd"] = 0
        
    if (DO > 5):
        sensor_data["dod"] = 1
        good_parameters += 1
    else :
        sensor_data["dod"] = 0
    sensor_data["Decision"] = water_quality(good_parameters)

#Decision making for Aquaculture Water
def Aquaculture():
    good_parameters = 0
    if (Turbidity < 25):
        sensor_data["Turbidityd"] = 1
        good_parameters += 1
    else :
        sensor_data["Turbidityd"] = 0
        
    if (500 < TDS < 750):
        sensor_data["TDSd"] = 1
        good_parameters += 1
    else :
        sensor_data["TDSd"] = 0
        
    if (30 < EC < 5000):
        sensor_data["ECd"] = 1
        good_parameters += 1
    else :
        sensor_data["ECd"] = 0
        
    if (6.5 < PH < 9.5):
        sensor_data["phd"] = 1
        good_parameters += 1
    else :
        sensor_data["phd"] = 0
        
    if (5 < DO < 20):
        sensor_data["dod"] = 1
        good_parameters += 1
    else :
        sensor_data["dod"] = 0
    sensor_data["Decision"] = water_quality(good_parameters)

#Decision making for Domestic Water
def Domestic():
    good_parameters = 0
    if (1.5 < Turbidity < 8):
        sensor_data["Turbidityd"] = 1
        good_parameters += 1
    else :
        sensor_data["Turbidityd"] = 0
        
    if (50 < TDS < 600):
        sensor_data["TDSd"] = 1
        good_parameters += 1
    else :
        sensor_data["TDSd"] = 0
        
    if (EC > 200):
        sensor_data["ECd"] = 1
        good_parameters += 1
    else :
        sensor_data["ECd"] = 0
        
    if (7 < PH < 13):
        sensor_data["phd"] = 1
        good_parameters += 1
    else :
        sensor_data["phd"] = 0
        
    if (6.5 < DO < 10):
        sensor_data["dod"] = 1
        good_parameters += 1
    else :
        sensor_data["dod"] = 0
    sensor_data["Decision"] = water_quality(good_parameters)

#Finding the Water Quality based on the number of good quality parameters of the water
def water_quality(good_parameters):
    if (good_parameters == 5):
        decision = 1 #Excellent
    elif(good_parameters == 4):
        decision = 2 #Good
    elif(good_parameters == 3):
        decision = 3 #Average
    else:
        decision = 4 #Not Good

#Exporting the 15 GPIO Pins to Receive the digital data from the Arduino
for i in range(14):
    pin = 1804+i
    export(pin)

#Declaring all the 15 GPIO Pins as input pins
for i in range(14):
    pin = 1804+i
    direction(pin,'in')

#Retrieving the json data from Microsoft Azure Blob Storage containing the type of water to be tested given by the user in the mobile application

connection_string = "DefaultEndpointsProtocol=https;AccountName=waterqualitysensordata;AccountKey=dur+uOwlxilXTa8zD6GV5MOim8txKzUqDG5arfV9vPRL98bw6GyESQ5kOj4K/IoL1/VdwFDeT1HgFIc1SFHkVw==;EndpointSuffix=core.windows.net"
blob_service_client = BlobServiceClient.from_connection_string(connection_string)
container1 = "sensordata"
container1_client = blob_service_client.get_container_client(container = container1)

blob_client = container1_client.get_blob_client(blob='Selection.json')
data = blob_client.download_blob().readall()
selection_dict = eval(data.decode())
selection = selection_dict["Selection"]

while True:
    try:
        #Assigning the sensor values to the variables based on the sensor index
        sensor_index = read(1817)*4 + read(1818)*2 + read(1819)
        if sensor_index == 0:
            Turbidity = pinsread()
        elif sensor_index == 1:
            TDS = pinsread()
        elif sensor_index == 2:
            EC = pinsread()
        elif sensor_index == 3:
            Temperature = pinsread()
        elif sensor_index == 4:
            PH = pinsread()
        elif sensor_index == 5:
            DO = pinsread()
            flag = 1

        #Appending all the sensor data and the decisions into a dictionary
        if (Turbidity, TDS, Temperature, EC, PH, DO, flag):
            print("Turbidity : %d NTU ,TDS :%d ppm , Temperature = %d deg , EC = %d mho, PH = %d , DO = %d mg/l"%(Turbidity, TDS, Temperature, EC, PH, DO))
            flag = 0
            sensor_data["Turbidity"] = Turbidity
            sensor_data["TDS"] = TDS
            sensor_data["EC"] = EC
            sensor_data["Temp"] = Temperature
            sensor_data["ph"] = PH
            sensor_data["do"] = DO
            if selection == 1:
                Drinking()
            elif selection == 2:
                Agriculture()
            elif selection == 3:
                Aquaculture()
            elif selection == 4:
                Industrial()
            elif selection == 5:
                Domestic()

            #Writing the dictionary to a json file
            f=open("Sensordata_Decision.json",'w')
            json.dump(d,f)
            f.close()
            
            #Sending the json file to the Microsoft Azure Cloud Blob Storage
            with open('Sensordata_Decision.json', 'rb') as j:
                container1_client.upload_blob(data=j, name='Sensordata_Decision.json',overwrite=True)
    except:
        #Unexporting all 15 GPIO Pins in case of any error of after stopping the program
        for i in range(14):
            pin = 1804+i
            unexport(pin)
        print("Execution Stopped")
        break
    time.sleep(1)   
    

