package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class finalpage extends AppCompatActivity {
    public TextView textView;
    private EditText editTextNumber;
    private EditText editTextMessage;
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalpage);
        ActivityCompat.requestPermissions(finalpage.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        button = findViewById(R.id.button);
        editTextMessage = findViewById(R.id.editTextTextPersonName);
        editTextNumber = findViewById(R.id.editTextTextPersonName2);
        textView.setText("tds");
    }
    public void SendSMS(View view){
        String message = editTextMessage.getText().toString();
        String number = editTextNumber.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
    }
}