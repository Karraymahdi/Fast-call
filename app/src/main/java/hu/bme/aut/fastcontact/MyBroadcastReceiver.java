package hu.bme.aut.fastcontact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //We are checking which broadcast was actually fired
    /*    boolean airPlaneState = intent.getBooleanExtra("state", false);

        Toast.makeText(context, "Airplane state: "+airPlaneState, Toast.LENGTH_LONG).show();

        Intent i = new Intent(context,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/
        boolean state = intent.getBooleanExtra("state", false);
        Toast.makeText(context, "Airplane state: "+state, Toast.LENGTH_LONG).show();



    }
          //  Toast.makeText(context, "Airplace Mode Changed", Toast.LENGTH_SHORT).show();

        // Toast.makeText(context, "Vibrator.",
          //      Toast.LENGTH_LONG).show();
        // Vibrate the mobile phone
        //Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //vibrator.vibrate(2000);


}
