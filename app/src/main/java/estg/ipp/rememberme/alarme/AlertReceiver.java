package estg.ipp.rememberme.alarme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

//vai ser chamado quando o alarme disparar
public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //mostrar a notificação
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder mNotificationbuilder = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, mNotificationbuilder.build());
    }
}
