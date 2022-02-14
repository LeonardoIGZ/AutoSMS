package com.example.autosms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class BroadcastReceiverSMS extends BroadcastReceiver {

    private String userNumber="6505551212", message = "Hola pa, como andás?";
    private String incomingN = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Detecto cuando el telefono se encuentra sonando por una llamada
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){

            //Creo un toast que me señale cuando detecta que el telefono esta sonando,
            //deberia funcionar incluso fuera de mi app
            Toast toast = Toast.makeText(context,
                    "Amigo, tienes una llamada entrante",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Log.i("Llamada entrante", intent.getStringExtra(TelephonyManager.EXTRA_STATE));

            // Con el siguiente metodo extraigo el numero de la llamada entrante
            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    System.out.println("incomingNumber : "+incomingNumber);

                    incomingN = incomingNumber;
                    //Comparo el numero entrante con el que paso el usuario, solo si coinciden mando mensaje
                    if(incomingN.equals(userNumber)){
                        Toast.makeText(context, "El numero entrante es: " + incomingN, Toast.LENGTH_LONG).show();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(
                                userNumber,
                                null, message,
                                null,null);
                    }

                }
            },PhoneStateListener.LISTEN_CALL_STATE);




        }
    }
}
