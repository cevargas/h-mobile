package com.tn3.mobile.hermes.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.dao.AuthDAO;
import com.tn3.mobile.hermes.task.ConsultaManifestoTask;
import com.tn3.mobile.hermes.utils.Utils;
import org.json.JSONException;
import java.util.List;

public class ConsultaManifestoService extends Service {

    private static final String TAG = "ConsultaManifSrv";
    private static String TOKEN;
    private static String IMEI ;
    static String URL = "http://interno.tn3.com.br:8083/hermesws/app/manifest/list";

    private Utils utils = new Utils();
    private static final long REPEAT_TIME = 300000;//5 minutos

    Handler handler = null;

    @Override
    public void onCreate() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HandlerThread hThread = new HandlerThread("HandlerThreadManifesto");
        hThread.start();
        handler = new Handler(hThread.getLooper());

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fragment_manifest, null);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listManifestos);

                List<String> params = new AuthDAO(getApplicationContext()).findAuth();
                TOKEN = params.get(0);
                IMEI = params.get(1);

                Log.i(TAG, "Rodou servico para consulta manifesto.");

                try {
                    new ConsultaManifestoTask(URL, getApplicationContext(), recyclerView, true, null).execute(utils.convertToJson(TOKEN, IMEI));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(this, REPEAT_TIME);
            }
        };

        handler.postDelayed(runnable, 0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
