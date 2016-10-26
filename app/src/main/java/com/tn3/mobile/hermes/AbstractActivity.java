package com.tn3.mobile.hermes;

import android.support.v7.app.AppCompatActivity;
import com.tn3.mobile.hermes.utils.Utils;

public class AbstractActivity extends AppCompatActivity {

    //definicao da identificacao da classe nos logs
    final String TAG = this.getClass().getSimpleName();

    Utils utils = new Utils(this);

    void alert(String msg, int duraction) {
        utils.alert(msg, duraction);
    }

    boolean isConnected() {
        return utils.isConnected();
    }

    String getImei(){
        return utils.getImei();
    }
}
