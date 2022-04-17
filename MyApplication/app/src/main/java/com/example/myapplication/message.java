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

public class message extends AppCompatActivity {
    public TextView textView;
    public Button button;
    private EditText editTextNumber;
    private EditText editTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActivityCompat.requestPermissions(message.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        button = findViewById(R.id.button);
        editTextMessage = findViewById(R.id.editTextTextPersonName);
        editTextNumber = findViewById(R.id.editTextTextPersonName2);
        textView = findViewById(R.id.textView8);
        textView.setText(R.string.td);
    }
    public void SendSMS(View view){
        String message = editTextMessage.getText().toString();
        String number = editTextNumber.getText().toString();
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
    }
}