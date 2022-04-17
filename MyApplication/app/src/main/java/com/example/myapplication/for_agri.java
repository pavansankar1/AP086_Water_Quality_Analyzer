package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import in.unicodelabs.kdgaugeview.KdGaugeView;

public class for_agri extends AppCompatActivity {
    KdGaugeView Ph,Turbidity,Tds,Do,ec,temp;
    TextView decision ;
    TextView error,tec,tturb,tph,tdo ;

    float x = 10;
    float y;

    String GET_URL="https://waterqualitysensor.blob.core.windows.net/sensordata/Agriculture_Data.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_agri);
        Ph = (KdGaugeView) findViewById(R.id.speedMeter);
        Turbidity = (KdGaugeView) findViewById(R.id.speedMeter2);
        Tds = (KdGaugeView) findViewById(R.id.speedMeter3);
        Do = (KdGaugeView) findViewById(R.id.speedMeter4);
        ec = (KdGaugeView) findViewById(R.id.speedMeter6);
        temp = (KdGaugeView) findViewById(R.id.speedMeter5);
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
            y = (float) (arr[4]/10.0);
            decision.setText(Float.toString(y));
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
//
//        Ph.setSpeed(x);
//        Turbidity.setSpeed(x);
//        Tds.setSpeed(x);
//        Do.setSpeed(x);
//        ec.setSpeed(x);
    }
}
class RequestJson extends AsyncTask<String, String, Integer[]> {
    int i =0;
    ArrayList<Integer> data = new ArrayList<>();
    @Nullable
    @Override
    public Integer[] doInBackground(String... uri) {
        Log.d("bb",uri[0].toString());
        try{
            URL obj = new URL(uri[0]);
            URLConnection conn = obj.openConnection();

            conn.setConnectTimeout(6000);

//            conn.connect();
            System.out.println(conn.getContent()+" res");
            InputStream stream = conn.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(stream,"UTF-8"));
            reader.beginObject();

            while (reader.hasNext()){
                String key = reader.nextName();
                System.out.println(key);
                if (key.equals("Turbidity")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("TDS")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("EC")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("Temp")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("ph")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("do")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("Decision")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("Turbidityd")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("TDSd")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("ECd")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("phd")){
                    data.add(reader.nextInt());
                }
                else if(key.equals("dod")){
                    data.add(reader.nextInt());
                }
            }

            System.out.println(Arrays.toString(data.toArray()));


        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return data.toArray(new Integer[data.size()]);
    }
}