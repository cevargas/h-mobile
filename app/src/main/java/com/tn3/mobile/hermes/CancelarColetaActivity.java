package com.tn3.mobile.hermes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tn3.mobile.hermes.adapters.ViewPagerAdapter;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.fragments.ManifestColetasCancelaFragment;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.Utils;

public class CancelarColetaActivity extends AbstractActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private String idColeta = null;
    private String idManifesto = null;
    private Bundle args = new Bundle();
    private Utils utils = new Utils();
    private TextView descricaoMotivoCanc;
    private Spinner spinnerOptsCanc;

    //corresponde ao id das opcoes de cancelamento do ws do hermes referente ao item list_opts_canc_array em Strings.xml
    //7 - Cancelado em viagem
    //8 - Nao realizada
    //9 - Sem coleta
    final int[] actualValues={7,8,9};
    int opt;

    private Coletas coleta = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletas_cancelar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Cancelar Coleta");
        }
        //recupera parametro passado pela outra activity
        idColeta = getIntent().getStringExtra("idColeta");
        args.putString("idColeta", idColeta);

        idManifesto = getIntent().getStringExtra("idManifesto");
        args.putString("idManifesto", idManifesto);

        //busca coleta no bd para verificar status e habilitar ou nao as opcoes de scan e finalizacao da coleta
        coleta = new ColetasDAO(this).findStatusAppColeta(idColeta);

        Log.d("CancelarColetaActivity", "ID Coleta " + idColeta);
        Log.d("CancelarColetaActivity", "ID Manifesto " + idManifesto);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, args);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (coleta.getStatusApp().equals("2") || coleta.getStatusApp().equals("3")) {
            menu.findItem(R.id.action_btn_cancelar).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cancela_coleta, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        spinnerOptsCanc = (Spinner) findViewById(R.id.spinnerOptsCanc);
        if(spinnerOptsCanc != null) {
            int opt = actualValues[ spinnerOptsCanc.getSelectedItemPosition() ];
            args.putInt("motivo", opt);
        }
        descricaoMotivoCanc = (TextView) findViewById(R.id.descricaoMotivoCanc);
        if(descricaoMotivoCanc != null) {
            args.putString("descricao", descricaoMotivoCanc.getText().toString());
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.action_btn_cancelar:
                //chama metodo para cancelar a coleta
                cancelaColeta();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(final ViewPager viewPager, Bundle args) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ManifestColetasCancelaFragment manifestColetasCancelaFragment = new ManifestColetasCancelaFragment();
        manifestColetasCancelaFragment.setArguments(args);
        adapter.addFragment(manifestColetasCancelaFragment, "Cancelamento de Coleta");
        viewPager.setAdapter(adapter);
    }

    public void cancelaColeta() {

        spinnerOptsCanc = (Spinner) findViewById(R.id.spinnerOptsCanc);
        assert spinnerOptsCanc != null;
        opt = actualValues[ spinnerOptsCanc.getSelectedItemPosition() ];

        TextView descricaoMotivoCanc = (TextView) findViewById(R.id.descricaoMotivoCanc);
        assert descricaoMotivoCanc != null;
        String motivo = descricaoMotivoCanc.getText().toString();

        new ColetasDAO(CancelarColetaActivity.this).updateCancelaColeta(idColeta, opt, motivo);

        Log.d("CANCELACOLETA", "Cancelou coleta " + idColeta + ", manifesto " + idManifesto);

        alert("Coleta cancelada com sucesso!", Toast.LENGTH_LONG);
        Intent intent = new Intent(CancelarColetaActivity.this, ManifestoActivity.class);
        intent.putExtra("idManifesto", Long.valueOf(idManifesto));
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
    }
}
