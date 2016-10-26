package com.tn3.mobile.hermes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tn3.mobile.hermes.observers.ObservableObject;
import com.tn3.mobile.hermes.task.AutenticaTask;
import com.tn3.mobile.hermes.utils.TextViewMask;

import org.json.JSONException;
import java.util.Observable;
import java.util.Observer;

/**
 * TN3 Hermes
 * VerificacaoActivity.java
 * Proposta: Autenticar o Imei do dispositivo com o Token do Usuario no Webservice
 *
 * @author Carlos Eduardo de Vargas
 * @version 1.0 29/07/2016
 */
public class VerificacaoActivity extends AbstractActivity implements Observer {

    private static String IMEI;
    private static String TOKEN;
    static String URL = "http://interno.tn3.com.br:8083/hermesws/app/autoriza";

    TextView _textImei;
    EditText _textCode;
    Button _btnLogin;

    private Integer maxLenToken = 12;
    private TextWatcher tokenMask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacao);

        //Instancia um Observador para ser avisado quando a internet estiver disponivel
        //Eh avisado atraves do broadcast.ConnectionBroadcastReceiver
        ObservableObject.getInstance().addObserver(this);

        _btnLogin = (Button)findViewById(R.id.btnLogin);
        _textCode = (EditText)findViewById(R.id.textCode);
        _textImei = (TextView)findViewById(R.id.textImei);

        int maxLength = maxLenToken;
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);

        //// TODO: TEM QUE REVISAR A MASCARA PARA O TOKEN
        //mascara token tem que testar
        tokenMask = TextViewMask.insert("####-####-####", _textCode);
        _textCode.addTextChangedListener(tokenMask);
        //_textCode.setFilters(FilterArray);

        enableButtonAuth();

        IMEI = getImei();
        if(_textImei != null) {
            _textImei.setText(IMEI);
        }

        _btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i(TAG, "Observable do VerificacaoActivity notificado");
        enableButtonAuth();
    }

    /**
     * Habilita ou desabilita o botao de autenticacao se a conexao com a internet estiver ou nao disponivel
     * @return void
     */
    public void enableButtonAuth(){

        Log.i(TAG, "enableButtonAuth() em VerificacaoActivity");

        if(!isConnected()) {
            alert("Você não esta conectado a internet!", Toast.LENGTH_LONG);
            _btnLogin.setEnabled(false);
            _btnLogin.setText("Verifique sua conexão!");
            _btnLogin.setTextColor(Color.parseColor("#FFCCCCCC"));
            _textCode.clearFocus();
            _textCode.setCursorVisible(false);
            _textCode.setFocusableInTouchMode(false);
            _textCode.setFocusable(false);
            _textCode.setText(null);
            _textCode.setError(null);
        }
        else {
            _btnLogin.setText("Autenticar");
            _btnLogin.setEnabled(true);
            _btnLogin.setTextColor(Color.parseColor("#ff23729a"));
            _textCode.setFocusableInTouchMode(true);
            _textCode.setError(null);
        }
    }

    /**
     * Recupera os dados do imei e token, serializa para json e chama a task AutenticaTask para autenticar o dispositivo
     * @return void Se autenticar vai carregar a ActivityMain.class
     */
    public void login() throws JSONException {

        Log.d(TAG, "Verificação de Autenticação");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _btnLogin = (Button)findViewById(R.id.btnLogin);
        if(_btnLogin != null) {
            _btnLogin.setEnabled(true);
        }

        _textCode = (EditText)findViewById(R.id.textCode);
        if(_textCode != null) {
            TOKEN = _textCode.getText().toString().replaceAll("\\-", "");
        }

        Log.i("login", TOKEN);

        String jsonSerialized = utils.convertToJson(TOKEN, IMEI);

        new AutenticaTask(URL, this).execute(jsonSerialized, TOKEN, IMEI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {}

    @Override
    public void onBackPressed() {}

    public void onLoginFailed() {
        _btnLogin = (Button)findViewById(R.id.btnLogin);
        if(_btnLogin != null) {
            _btnLogin.setEnabled(true);
        }
    }

    /**
     * Verifica o tamanho do token digitado.
     * Deve conter 12 caracteres
     * @return boolean
     */
    public boolean validate() {

        boolean valid = true;

        _textCode = (EditText)findViewById(R.id.textCode);
        if(_textCode != null) {

            String code = _textCode.getText().toString().trim().replaceAll("\\-", "");

            Log.i("validate", code);

            if (code.length() != maxLenToken) {
                _textCode.setError("O token de autenticação deve conter "+maxLenToken+" caracteres.");
                valid = false;
            } else {
                _textCode.setError(null);
            }
        }

        return valid;
    }
}