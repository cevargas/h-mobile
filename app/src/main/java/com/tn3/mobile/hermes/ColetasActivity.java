package com.tn3.mobile.hermes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.tn3.mobile.hermes.adapters.ViewPagerAdapter;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.fragments.ManifestColetasDetalhesFragment;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.TextAwesome;

public class ColetasActivity extends AbstractActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    static final int defaultValue = 0;

    private String idColeta;
    private String idManifesto;

    private Coletas coleta = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletas);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Detalhes da Coleta");
        }

        //recupera parametro passado pela ManifestColetasFragment
        idColeta = String.valueOf(getIntent().getLongExtra("idColeta", defaultValue));
        idManifesto = String.valueOf(getIntent().getLongExtra("idManifesto", defaultValue));

        //busca coleta no bd para verificar status e habilitar ou nao as opcoes de scan e finalizacao da coleta
        coleta = new ColetasDAO(this).findStatusAppColeta(idColeta);

        Bundle args = new Bundle();
        args.putString("idColeta", idColeta);
        args.putString("idManifesto", idManifesto);

        Log.d("ColetasActivity", "ID " + idColeta);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coletas, menu);
        return true;
    }

    private void setupViewPager(ViewPager viewPager, Bundle args) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ManifestColetasDetalhesFragment manifestColetasDetalhesFragment = new ManifestColetasDetalhesFragment();
        manifestColetasDetalhesFragment.setArguments(args);
        adapter.addFragment(manifestColetasDetalhesFragment, "Detalhes da Coleta");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (coleta.getStatusApp().equals("2") || coleta.getStatusApp().equals("3")) {
            menu.findItem(R.id.action_btn_finalizar).setVisible(false);
            menu.findItem(R.id.action_btn_cancelar).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        int id = item.getItemId();

        switch (id) {

            case R.id.action_btn_finalizar:
                Intent intent_fin = new Intent(ColetasActivity.this, FinalizaColetaActivity.class);
                intent_fin.putExtra("idColeta", idColeta);
                intent_fin.putExtra("idManifesto", idManifesto);
                startActivity(intent_fin);
                break;

            case R.id.action_btn_cancelar:
                Intent intent_can = new Intent(ColetasActivity.this, CancelarColetaActivity.class);
                intent_can.putExtra("idColeta", idColeta);
                intent_can.putExtra("idManifesto", idManifesto);
                startActivity(intent_can);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
    }
}
