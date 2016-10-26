package com.tn3.mobile.hermes;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.tn3.mobile.hermes.adapters.ViewPagerAdapter;
import com.tn3.mobile.hermes.dao.AuthDAO;
import com.tn3.mobile.hermes.fragments.EntregasFragment;
import com.tn3.mobile.hermes.fragments.ManifestFragment;
import com.tn3.mobile.hermes.observers.ObservableObject;
import com.tn3.mobile.hermes.services.ConsultaManifestoService;
import com.tn3.mobile.hermes.services.PostColetasConcluidasService;
import com.tn3.mobile.hermes.task.SincronizaTask;
import org.json.JSONException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AbstractActivity implements Observer {

    static final String CHECK_STATUS = "status";
    private boolean status = false;
    private static String IMEI;
    private static String TOKEN;

    static String URL = "http://interno.tn3.com.br:8083/hermesws/app/manifest/list/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObservableObject.getInstance().addObserver(this);

        boolean isAuth = new AuthDAO(this).checkAuth();

        if(isAuth) {
            List<String> params = new AuthDAO(this).findAuth();
            TOKEN = params.get(0);
            IMEI = params.get(1);

            status = savedInstanceState != null && savedInstanceState.getBoolean(CHECK_STATUS);
            if(!isConnected() && !status) {
                status = true;
                alert("Você não esta conectado a internet!", Toast.LENGTH_LONG);
            }
            else {
                //inicializa servicos de consulta ao WS
                this.startService(new Intent(this, PostColetasConcluidasService.class));
                Log.i("STARTSRV", "Iniciou servico post coletas");

                this.startService(new Intent(this, ConsultaManifestoService.class));
                Log.i("STARTSRV", "Iniciou servico consulta manifesto");
            }
        } else {
            Intent intent = new Intent(this, VerificacaoActivity.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //ManifestFragment: vai carregar a lista de manifestos para este motorista
        adapter.addFragment(new ManifestFragment(), "Manifestos");
        adapter.addFragment(new EntregasFragment(), "Entregas");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void update(Observable observable, Object data) {
        //Log.i("UPDATE","The update is called in MainActivity.");
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(CHECK_STATUS, status);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = getIntent();

        switch (id) {

            case R.id.action_settings:
                //// TODO: 29/07/2016 vai ter alguma configuracao?
                break;
            case R.id.action_logout:
                //// TODO: 29/07/2016 analizar o logout..deletar tudo do banco? por enquanto deleta so os dados de auth
                new AuthDAO(this).delete();
                startActivity(intent);
                finish();
                break;
            case R.id.action_sincronizar:
                sync();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sync() {
        if (isConnected()) {
            try {
                //// TODO: 10/06/2016 o SincronizaTask precisa ser reimplementado para chamar os dois servicos, so esta buscando o manifesto por enquanto
                new SincronizaTask(URL, this).execute(utils.convertToJson(TOKEN, IMEI));
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        else {
            alert("Você não esta conectado!", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
    }
}