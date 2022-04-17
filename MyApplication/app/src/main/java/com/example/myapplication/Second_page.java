package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Second_page extends AppCompatActivity {


    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pym = py.getModule("test");



        imageButton = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Second_page.this, "Drinking", Toast.LENGTH_SHORT).show();
                String Sel="Drinking";
//                try{
//                    PyObject request = pym.callAttr("sendType",Sel);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                openDrinking();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Second_page.this, "Agriculture", Toast.LENGTH_SHORT).show();
                String Sel="Agriculture";
//                try{
//                    PyObject request = pym.callAttr("sendType",Sel);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                openAgriculture();

            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Second_page.this, "Industry", Toast.LENGTH_SHORT).show();
                String Sel="Industry";
//                try{
//                    PyObject request = pym.callAttr("sendType",Sel);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                openIndustry();

            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Second_page.this, "aquaculture", Toast.LENGTH_SHORT).show();
                String Sel="Aquaculture";
//                try{
//                    PyObject request = pym.callAttr("sendType",Sel);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                openAquarium();
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Second_page.this, "domestic", Toast.LENGTH_SHORT).show();
                String Sel="Domestic";
//                try{
//                    PyObject request = pym.callAttr("sendType",Sel);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                openDomestic();
            }
        });
    }
    public void openDrinking(){
        Intent intent= new Intent(this,Third_page.class);
        startActivity(intent);
    }
    public void openAgriculture(){
        Intent intent= new Intent(this,for_agri.class);
        startActivity(intent);
    }
    public void openIndustry(){
        Intent intent= new Intent(this,for_ind.class);
        startActivity(intent);
    }
    public void openAquarium(){
        Intent intent= new Intent(this,for_aqua.class);
        startActivity(intent);
    }
    public void openDomestic(){
        Intent intent= new Intent(this,for_dom.class);
        startActivity(intent);
    }

}