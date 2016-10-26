package com.tn3.mobile.hermes.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.dto.ResultWSDTO;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.HttpUtils;

import java.util.List;

public class PostColetasTask extends AsyncTask<String, Void, ResultWSDTO> {

    private String URL;
    private Context context;
    private String TAG = "PostColetasTask";
    private List<Coletas> coletas;

    public PostColetasTask(String URL, Context context, List<Coletas> coletas) {
        this.URL = URL;
        this.context = context;
        this.coletas = coletas;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected ResultWSDTO doInBackground(String... params) {
        String jsonDATA = params[0];
        return HttpUtils.post(URL, jsonDATA, "PUT");
    }

    @Override
    protected void onPostExecute(ResultWSDTO result) {
        if(result != null) {
            if (result.stat.equals("error")) {
                Log.w(TAG, result.msg);
                Log.e(TAG, result.cause);
            }
            else {
                if(!coletas.isEmpty()) {
                    for (Coletas c : coletas) {
                        new ColetasDAO(context).updateColetasSincronizadas(c.getId().toString());
                    }
                }
            }
        }
    }
}