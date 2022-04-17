package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import in.unicodelabs.kdgaugeview.KdGaugeView;

public class for_aqua extends AppCompatActivity {
    KdGaugeView Ph,Turbidity,Tds,Do,ec,temp;
    TextView decision;
    int x = 10;
    TextView error,tec,tturb,tph,tdo ;
    String GET_URL="https://waterqualitysensor.blob.core.windows.net/sensordata/Aquaculture_Data.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_aqua);
        Ph = (KdGaugeView) findViewById(R.id.speedMeter);
        Turbidity = (KdGaugeView) findViewById(R.id.speedMeter2);
        Tds = (KdGaugeView) findViewById(R.id.speedMeter3);
        Do = (KdGaugeView) findViewById(R.id.speedMeter4);
        ec = (KdGaugeView) findViewById(R.id.speedMeter6);
        temp = (KdGaugeView) findViewById(R.id.speedMeter5);
        Ph.setSpeed(x);
        Turbidity.setSpeed(x);
        Tds.setSpeed(x);
        Do.setSpeed(x);
        ec.setSpeed(x);
        decision = findViewById(R.id.textView17);
        error = findViewById(R.id.textView15);
        tturb = findViewById(R.id.textViewturb);
        tph = findViewById(R.id.textViewph);
        tdo = findViewById(R.id.textViewdo);
        tec = findViewById(R.id.textViewec);
        AsyncTask<String, String, Integer[]> x = new RequestJson().execute(GET_URL);
        try {
            Integer[] arr = x.get();
            System.out.println(arr[6]);
            Turbidity.setSpeed((float)(arr[0]/10.0));
            tturb.setText(Float.toString((float)(arr[0]/10.0)));
            Tds.setSpeed(arr[1]);
            ec.setSpeed((float)(arr[2]/100.00));
            tec.setText(Float.toString((float)(arr[2]/100.00)));
            temp.setSpeed(arr[3]);
            Ph.setSpeed((float)(arr[4]/10.0));
            tph.setText(Float.toString((float)(arr[4]/10.0)));
            Do.setSpeed((float)(arr[5]/10.0));
            tdo.setText(Float.toString((float)(arr[5]/10.0)));
            if(arr[6]==4) {
                decision.setText("Excellent to use");
            }
            else if(arr[6]==3) {
                decision.setText("Good to use");
            }
            else if(arr[6]==2) {
                decision.setText("Average to Use");
            }
            else if(arr[6]==1) {
                decision.setText("Worst Better Don't use");
            }
            String e="";
            int c=0;
            if(arr[7]==0){
                e+=" Turbidity";
                c = c+1;
            }
            if(arr[8]==0){
                e+=", TDS ";
                c = c+1;
            }
            if(arr[9]==0){
                e+=", Conductivity ";
                c = c+1;
            }
            if(arr[10]==0){
                e+=", PH ";
                c = c+1;
            }
            if(arr[11]==0){
                e+=", Dissolved oxygen ";
                c = c+1;
            }
            if(!(TextUtils.isEmpty(e))){
                if(c==1){
                    error.setText(e+" is out of range.");
                }
                else{
                    error.setText(e+" are out of range");
                }
            }
            else{
                error.setText(" ");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
