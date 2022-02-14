package com.example.autosms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;

import com.example.autosms.receiver.BroadcastReceiverSMS;

public class MainActivity extends AppCompatActivity {
    EditText txtNumber, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pregunto por el prermiso de leer el estado de la llamada del telefono
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                        != PackageManager.PERMISSION_GRANTED) {
            //Para que el usuario me acepte los permisos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG, Manifest.permission.SEND_SMS}, 1);
        }

        //Asigno el contenido de mis capos de texto a las variables;
        txtNumber = findViewById(R.id.txtPhoneNumber);
        txtMessage = findViewById(R.id.txtSMS);

        Intent intent = new Intent(this, BroadcastReceiverSMS.class);
        // Send String
        intent.putExtra("Phone", txtNumber.toString());
        intent.putExtra("Message", txtMessage.toString());
        startActivity(intent);

    }
}