package com.tn3.mobile.hermes.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tn3.mobile.hermes.MainActivity;
import com.tn3.mobile.hermes.dao.ManifestosDAO;
import com.tn3.mobile.hermes.dto.ResultWSDTO;
import com.tn3.mobile.hermes.utils.HttpUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SincronizaTask extends AsyncTask<String, Void, ResultWSDTO> {

    private String URL;
    private Context context;
    private ProgressDialog progress;

    public SincronizaTask(String URL, Context context) {
        this.URL = URL;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progress = ProgressDialog.show(context, "Sincronização de Dados", "Sincronizando, aguarde...", true, true);
    }

    @Override
    protected ResultWSDTO doInBackground(String... params) {
        String jsonDATA = params[0];
        return HttpUtils.post(URL, jsonDATA, "POST");
    }

    @Override
    protected void onPostExecute(ResultWSDTO rs) {

        if(rs != null) {

            if(rs.content != null) {

                //chama metodo para salvar em banco o retorno json da api
                new ManifestosDAO(context).salvar(rs.content);

                Toast.makeText(context, "Sincronização concluída!", Toast.LENGTH_LONG).show();

                //chama activity main para recarregar lista
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
            else {
                Toast.makeText(context, "Sincronização concluída!", Toast.LENGTH_LONG).show();
            }

            progress.dismiss();

        }
        else {

            progress.setMessage("Serviço indisponível, tente novamente mais tarde.");
            progress.setTitle("Falha na Sincronização");

            progressRunnable();
        }
    }

    //se ocorrer erro, exibe a mensagem de falha antes de fechar o dialog
    void progressRunnable(){
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        int timer = 5000;
        pdCanceller.postDelayed(progressRunnable, timer);
    }

    boolean isUrlAvaliable(String surl) {

        java.net.URL url;
        HttpURLConnection conn;

        try {

            url = new URL(surl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000); //set timeout to 5 seconds
            conn.setReadTimeout(5000);

            if(conn.getResponseCode() == 200) {
                return true;
            }

        } catch (IOException e) {
            Log.e("SincronizaTask", "error", e);
        }

        return false;
    }
}
