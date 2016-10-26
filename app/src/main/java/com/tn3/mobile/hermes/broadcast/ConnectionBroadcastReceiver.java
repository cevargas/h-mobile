package com.tn3.mobile.hermes.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.tn3.mobile.hermes.dao.AuthDAO;
import com.tn3.mobile.hermes.observers.ObservableObject;
import com.tn3.mobile.hermes.services.ConsultaManifestoService;
import com.tn3.mobile.hermes.services.PostColetasConcluidasService;
import com.tn3.mobile.hermes.utils.ConnectionCheckUtils;

public class ConnectionBroadcastReceiver extends BroadcastReceiver {

    private Context mContext;

    public ConnectionBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;

        if(checkInternet(context)) {

            boolean isAuth = new AuthDAO(context).checkAuth();
            if (isAuth) {

                Log.i("CONNECTION", "Received");
                // trigger change

                context.startService(new Intent(context, PostColetasConcluidasService.class));
                Log.i("STARTSRV", "Iniciou servico post coletas");

                context.startService(new Intent(context, ConsultaManifestoService.class));
                Log.i("STARTSRV", "Iniciou servico consulta manifesto");
            }
        }
        else {

            if(isMyServiceRunning(PostColetasConcluidasService.class)) {
                context.stopService(new Intent(context, PostColetasConcluidasService.class));
                Log.i("STOPSRV", "Parou servico post coletas");
            }

            if(isMyServiceRunning(ConsultaManifestoService.class)) {
                context.stopService(new Intent(context, ConsultaManifestoService.class));
                Log.i("STOPSRV", "Parou servico consulta manifesto");
            }
        }

        ObservableObject.getInstance().updateValue(intent);
    }

    boolean checkInternet(Context context) {
        ConnectionCheckUtils serviceManager = new ConnectionCheckUtils(context);
        return serviceManager.hasInternetConnection();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}