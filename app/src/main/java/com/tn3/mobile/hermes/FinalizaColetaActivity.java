package com.tn3.mobile.hermes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tn3.mobile.hermes.adapters.ViewPagerAdapter;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.fragments.ManifestColetasFinalizaFragment;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.TextViewMask;
import java.util.Collection;
import java.util.Collections;

public class FinalizaColetaActivity extends AbstractActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private FloatingActionButton fabScan;
    private FloatingActionButton fabDigitaChave;

    private String idColeta = null;
    private String idManifesto = null;
    private FragmentListener fragmentListener;
    private Bundle args = new Bundle();

    private TextWatcher chaveMask;
    private AlertDialog dialog;
    private AlertDialog dialogFinaliza;
    private Coletas coleta = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletas_finalizar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Concluir Coleta");
        }

        //recupera parametro passado pela outra activity
        idColeta = getIntent().getStringExtra("idColeta");
        args.putString("idColeta", idColeta);

        idManifesto = getIntent().getStringExtra("idManifesto");
        args.putString("idManifesto", idManifesto);

        Log.d("FinalizaColetaActivity", "ID " + idColeta);

        //busca coleta no bd para verificar status e habilitar ou nao as opcoes de scan e finalizacao da coleta
        coleta = new ColetasDAO(this).findStatusAppColeta(idColeta);

        fabDigitaChave = (FloatingActionButton) findViewById(R.id.fabDigitaChave);
        fabScan = (FloatingActionButton) findViewById(R.id.fabScan);

        if (coleta.getStatusApp().equals("2") || coleta.getStatusApp().equals("3")) {
            fabDigitaChave.setVisibility(View.GONE);
            fabScan.setVisibility(View.GONE);
        }
        else {

            fabDigitaChave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final EditText userInput = new EditText(FinalizaColetaActivity.this);
                    userInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                    int maxLength = 54;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    userInput.setFilters(FilterArray);

                    dialog = new AlertDialog.Builder(FinalizaColetaActivity.this)
                            .setView(userInput)
                            .setTitle("Chave da NFe")
                            .setMessage("Informe a chave da NFe de 44 digitos")
                            .setPositiveButton(android.R.string.ok, null)
                            .setNegativeButton(android.R.string.cancel, null)
                            .create();

                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(final DialogInterface dialog) {
                            Button b = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                            b.setText("Salvar");
                            b.setEnabled(false);
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String chaveDigitada = userInput.getText().toString().replaceAll("\\.", "");

                                    String modelNf = chaveDigitada.substring(20, 22);

                                    if(modelNf.equals("55")) {
                                        sendDataToFragment(chaveDigitada);
                                        Log.d("SCAN_CHAVE_OK", chaveDigitada);
                                    }
                                    else {
                                        Log.d("SCAN_CHAVE_INV", "Tipo do Documento invalido, Modelo " + modelNf);
                                        alert("Tipo do Documento inválido, aceita apenas NFe Modelo 55. Identificou " + modelNf, Toast.LENGTH_LONG);
                                    }

                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    dialog.show();
                    chaveMask = TextViewMask.insertToDialog("####.####.####.####.####.####.####.####.####.####.####", userInput, dialog);
                    userInput.addTextChangedListener(chaveMask);
                }
            });

            fabScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Collection<String> BARCODE_TYPES = Collections.unmodifiableCollection(Collections.singletonList("CODE_128"));
                    new IntentIntegrator(FinalizaColetaActivity.this).initiateScan(BARCODE_TYPES);
                }
            });
        }

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
            menu.findItem(R.id.action_btn_finalizar).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finaliza_coleta, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.action_btn_finalizar:
                finaliza();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void finaliza() {

        final EditText userInput = new EditText(FinalizaColetaActivity.this);
        userInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        dialogFinaliza = new AlertDialog.Builder(FinalizaColetaActivity.this)
                .setView(userInput)
                .setTitle("Volumes da Coleta")
                .setMessage("Informe o total de volumes coletados")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialogFinaliza.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button b = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                b.setText("Concluir Coleta");
                b.setEnabled(true);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String volumes = userInput.getText().toString();
                        if(!volumes.equals("")) {
                            new ColetasDAO(FinalizaColetaActivity.this).updateConcluiColeta(idColeta, volumes);
                            dialogFinaliza.dismiss();
                            alert("Coleta finalizada com sucesso!", Toast.LENGTH_LONG);
                            Intent intent = new Intent(FinalizaColetaActivity.this, ManifestoActivity.class);
                            intent.putExtra("idManifesto", Long.valueOf(idManifesto));

                            Log.d("FINALIZACOLETA", "Finalizou coleta " + idColeta + ", manifesto " + idManifesto);

                            startActivity(intent);
                        }
                        else {
                            userInput.setError("Informe o total de volumes!!");
                        }
                    }
                });
            }
        });
        dialogFinaliza.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (dialogFinaliza != null) {
            dialogFinaliza.dismiss();
            dialogFinaliza = null;
        }
    }

    private void setupViewPager(final ViewPager viewPager, Bundle args) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ManifestColetasFinalizaFragment manifestColetasFinalizaFragment = new ManifestColetasFinalizaFragment();
        manifestColetasFinalizaFragment.setArguments(args);
        adapter.addFragment(manifestColetasFinalizaFragment, "Finalização da Coleta");
        viewPager.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String result = scanResult.getContents();

            if(result != null) {
                if (result.length() == 44) {
                    String modelNf = result.substring(20, 22);

                    if(modelNf.equals("55")) {
                        sendDataToFragment(result);
                        Log.d("SCAN_CHAVE_OK", result);
                    }
                    else {
                        Log.d("SCAN_CHAVE_INV", "Tipo do Documento invalido, Modelo " + modelNf);
                        alert("Tipo do Documento inválido, aceita apenas NFe Modelo 55. Identificou " + modelNf, Toast.LENGTH_LONG);
                    }
                } else {
                    alert("Falha na leitura da Chave, tente novamente: " + result, Toast.LENGTH_LONG);
                    Log.d("SCAN_CHAVE_FAIL", result);
                }
            }
        }
    }

    public interface FragmentListener {
        void updateFragmentList(String chave);
    }

    public void setFragmentListener(FragmentListener listener) {
        this.fragmentListener = listener;
    }

    public void sendDataToFragment(String chave) {
        this.fragmentListener.updateFragmentList(chave);
    }
}
