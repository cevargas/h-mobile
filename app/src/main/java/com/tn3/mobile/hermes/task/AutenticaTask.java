package com.tn3.mobile.hermes.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.tn3.mobile.hermes.MainActivity;
import com.tn3.mobile.hermes.dao.AuthDAO;
import com.tn3.mobile.hermes.dto.ResultWSDTO;
import com.tn3.mobile.hermes.utils.HttpUtils;

public class AutenticaTask extends AsyncTask<String, Void, ResultWSDTO> {

    private String URL;
    private Context context;
    private ProgressDialog progress;
    private String TOKEN;
    private String IMEI;

    public AutenticaTask(String URL, Context context) {
        this.URL = URL;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progress = ProgressDialog.show(context, "Autenticando", "Autenticando, aguarde...", true, true);

    }

    @Override
    protected ResultWSDTO doInBackground(String... params) {
        String jsonDATA = params[0];
        TOKEN = params[1];
        IMEI = params[2];
        return HttpUtils.post(URL, jsonDATA, "POST");
    }

    @Override
    protected void onPostExecute(ResultWSDTO result) {

        if(result != null) {

            Log.d("AutenticaTask", result.stat+" "+ result.msg);

            if(result.stat.equals("error")) {
                Toast.makeText(context, "Falha na autenticação: " + result.msg, Toast.LENGTH_LONG).show();
            } else {
                new AuthDAO(context).salvar(result, TOKEN, IMEI);
            }

            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            progress.dismiss();
        }
        else {
            progress.setMessage("Serviço indisponível, tente novamente mais tarde.");
            progress.setTitle("Falha na Autenticação");
            progressRunnable();
        }
    }

    /**
     * Se ocorrer erro, exibe a mensagem de falha antes de fechar o dialog
     */
    void progressRunnable(){
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        int timer = 3000;
        pdCanceller.postDelayed(progressRunnable, timer);
    }
}