package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private Button button2;
    private TextInputEditText textInputEditText;
    private EditText editTextPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        button2 = findViewById(R.id.button2);
        textInputEditText = findViewById(R.id.textInputEditText);
        editTextPhone = findViewById(R.id.editTextPhone);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = textInputEditText.getText().toString();
                String ph = editTextPhone.getText().toString();

                if(TextUtils.isEmpty(s) || TextUtils.isEmpty(ph)){
                    Toast.makeText(MainActivity.this, "Enter details", Toast.LENGTH_SHORT).show();
                }
                else{
                    openActivity2();
                    Toast.makeText(MainActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void openActivity2(){
        Intent intent= new Intent(this,Second_page.class);
        startActivity(intent);
    }
}