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

    String number = "", message = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Detecto cuando el telefono se encuentra sonando por una llamada
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            //Creo un toast que me señale cuando detecta que el telefono esta sonando, deberia funcionar fuera
            //de mi app


            //Aqui deberia extraer lo datos del intent

            Toast toast = Toast.makeText(context,
                    "Ché, tenes una llamada entrate",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Log.i("Llamada entrante", intent.getStringExtra(TelephonyManager.EXTRA_STATE));

            SmsManager smsManager = SmsManager.getDefault();;
            smsManager.sendTextMessage(
                    "(650)555-1212",
                    null, "Boca sho te amo",
                    null,null);

            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    System.out.println("incomingNumber : "+incomingNumber);
                    Toast.makeText(context, "El numero entrante es: " + incomingNumber, Toast.LENGTH_LONG).show();
                    number = incomingNumber;
                }
            },PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}
