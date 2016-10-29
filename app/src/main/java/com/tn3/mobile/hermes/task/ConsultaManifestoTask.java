package com.tn3.mobile.hermes.task;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tn3.mobile.hermes.MainActivity;
import com.tn3.mobile.hermes.ManifestoActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.adapters.ListaManifestosAdapter;
import com.tn3.mobile.hermes.dao.ManifestosDAO;
import com.tn3.mobile.hermes.dto.ManifestosDTO;
import com.tn3.mobile.hermes.dto.ResultWSDTO;
import com.tn3.mobile.hermes.models.Manifestos;
import com.tn3.mobile.hermes.utils.HttpUtils;

import java.util.List;

public class ConsultaManifestoTask extends AsyncTask<String, Void, ResultWSDTO> {
    private String URL;
    private Context context;
    private String TAG = "ConsultaManifTask";
    private int NOTIFICATION_ID = 1;
    private final RecyclerView recyclerView;
    private boolean notificar;
    private Long idManifesto;

    SwipeRefreshLayout mSwipeRefreshLayout;

    public ConsultaManifestoTask(String URL, Context context, RecyclerView recyclerView, boolean notificar, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.URL = URL;
        this.context = context;
        this.recyclerView = recyclerView;
        this.notificar = notificar;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected ResultWSDTO doInBackground(String... params) {
        String jsonDATA = params[0];
        return HttpUtils.post(URL, jsonDATA, "POST");
    }

    @Override
    protected void onPostExecute(ResultWSDTO result) {

        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if(result != null) {
            Log.i(TAG, "Retorno da consulta Manifesto..");

            if (result.stat.equals("error")) {
                Log.w(TAG, (result.msg != null) ? result.msg : "result.msg nao retornou mensagem.");
                Log.w(TAG, (result.cause != null) ? result.cause : "result.cause nao retornou mensagem.");
            }
            else {

                if(result.content != null) {

                    ManifestosDTO[] manifestosDTO = result.content;
                    boolean[] res = new ManifestosDAO(context).salvar(manifestosDTO);

                    if(res[0]) {
                        List<Manifestos> lista = new ManifestosDAO(context).list();
                        final ListaManifestosAdapter manifestoAdapter = new ListaManifestosAdapter(context, lista);
                        recyclerView.setAdapter(manifestoAdapter);
                        synchronized (recyclerView) {
                            recyclerView.notify();
                        }
                    }

                    if(res[0] && notificar) {
                        //Foi add um novo manifesto
                        sendNotification("Você tem um novo Manifesto");
                    }

                    if(res[1] && notificar) {
                        //Foi add uma nova coleta
                        sendNotification("Uma nova coleta foi adicionada");
                    }
                }
                else {
                    Log.i(TAG, "Content retornou null");
                }
            }
        }
    }

    public void sendNotification(String msn) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_stat_hermes);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        //builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tn3_hermes));
        builder.setContentTitle("TN3 Hermes");
        builder.setContentText(msn);
        //builder.setSubText("Você tem um novo manifesto");
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        builder.setLights(Color.BLUE, 3000, 3000);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
