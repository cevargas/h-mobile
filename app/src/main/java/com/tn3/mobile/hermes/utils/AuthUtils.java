package com.tn3.mobile.hermes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.io.File;

public class AuthUtils {

    private Context context;

    public AuthUtils(Context context) {
        this.context = context;
    }

    /**
     * Method: isAuth()
     * return boolean
     * Description: Verifica se o usuario ja realizou a verificacao de seguranca no primeiro acesso
     */
    public boolean isAuth() {

        SharedPreferences settings;
        boolean auth;
        settings = context.getSharedPreferences("pref_auth", Context.MODE_PRIVATE);
        auth = settings.getBoolean("autenticado", false);
        return auth;
    }

    /**
     * Method: removeAuth()
     * return boolean
     * Description: Remove o arquivo de preferencias do app no Android que armazena a confirmacao de autenticacao
     */
    public void removeAuth(){
        //local do arquivo de preferencias
        File f = context.getDatabasePath("pref_auth.xml");
        if (f != null) {
            Log.d("ConnectionCheckUtils", f.getAbsolutePath());
            SharedPreferences preferences = context.getSharedPreferences("pref_auth", 0);
            preferences.edit().remove("autenticado").apply();
        }
    }
}
