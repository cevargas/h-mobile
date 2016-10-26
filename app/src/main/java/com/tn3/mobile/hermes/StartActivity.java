package com.tn3.mobile.hermes;

import android.content.Intent;
import android.os.Bundle;

/**
 * TN3 Hermes
 * StartActivity.java
 * Proposta: Tela inicial quando o aplicativo for executado
 *
 * @author Carlos Eduardo de Vargas
 * @version 1.0 29/07/2016
 */

public class StartActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
