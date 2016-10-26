package com.tn3.mobile.hermes.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.google.gson.Gson;
import com.tn3.mobile.hermes.dao.AuthDAO;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.dto.PostJsonDTO;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.task.PostColetasTask;
import com.tn3.mobile.hermes.utils.Utils;

import java.util.List;

public class PostColetasConcluidasService extends Service {

    private static final String TAG = "PostColetasService";
    static String URL = "http://interno.tn3.com.br:8083/hermesws/app/manifest/documentos";

    private static final long REPEAT_TIME = 420000; //7 minutos
    Handler handler = null;

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HandlerThread hThread = new HandlerThread("HandlerThreadColetas");
        hThread.start();
        handler = new Handler(hThread.getLooper());

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                //new ColetasDAO(getApplicationContext()).resetColetasSync();
                List<Coletas> coletas = new ColetasDAO(getApplicationContext()).findColetasConcluidas();

                if (!coletas.isEmpty()) {

                    List<String> params = new AuthDAO(getApplicationContext()).findAuth();
                    String token = params.get(0);
                    String imei = params.get(1);

                    Log.i(TAG, "Rodou servico para post de coletas.");

                    PostJsonDTO postJsonDTO = new PostJsonDTO();
                    postJsonDTO.setColetasList(coletas);
                    postJsonDTO.setImei(imei);
                    postJsonDTO.setToken(token);

                    Object json = new Gson().toJson(postJsonDTO);
                    Log.d(TAG, "rodou Object json: " + json);

                    try {

                        String postJson = Utils.convertJsonToB64(json);
                        new PostColetasTask(URL, getApplicationContext(), coletas).execute(postJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "JSONObject: " + e.getMessage());
                    }
                } else {
                    Log.i(TAG, "Nenhuma coleta para sincronizar!");
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
