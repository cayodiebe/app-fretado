package Views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceberAlarme extends BroadcastReceiver {



    @Override

    public void onReceive(Context c, Intent i) {

        Toast.makeText(c, "Alarme!!!!", Toast.LENGTH_SHORT).show();

    }
}
