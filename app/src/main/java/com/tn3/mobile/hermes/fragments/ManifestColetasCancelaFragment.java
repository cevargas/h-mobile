package com.tn3.mobile.hermes.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tn3.mobile.hermes.CancelarColetaActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.adapters.CancelaColetaAdapter;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.Utils;

import java.io.File;
import java.io.InputStream;

public class ManifestColetasCancelaFragment extends Fragment {

    Utils utils = new Utils();
    Coletas coleta;
    RecyclerView cancelaColeta;
    CancelaColetaAdapter cancelaColetaAdapter;
    private String fileLocation = Environment.getExternalStorageDirectory() +"/TN3Hermes/";
    String filename;
    File fileImageCapture;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private FloatingActionButton fabFoto;
    public ManifestColetasCancelaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manifest_coletas_cancela, container, false);

        String idColeta = getArguments().getString("idColeta");

        coleta = new ColetasDAO(getContext()).findColetaById(idColeta);

        cancelaColeta = (RecyclerView) view.findViewById(R.id.itemColetaCancela);
        cancelaColeta.setNestedScrollingEnabled(false);

        fabFoto = (FloatingActionButton) view.findViewById(R.id.fabFoto);

        if (coleta.getStatusApp().equals("2") || coleta.getStatusApp().equals("3")) {
            fabFoto.setVisibility(View.GONE);
        }
        else {

            fabFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    filename = "COLETA_" + coleta.getId() + "_" + System.currentTimeMillis() + ".png";
                    fileImageCapture = new File(fileLocation, filename);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImageCapture));
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            });
        }

        return view;
    }

    public void updateView(){
        cancelaColetaAdapter = new CancelaColetaAdapter(getContext(), coleta);
        cancelaColeta.setAdapter(cancelaColetaAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cancelaColeta.setLayoutManager(linearLayoutManager);
        cancelaColeta.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            Log.d("onActivityResult()", "foto capturada!!!");

            String descricao = getArguments().getString("descricao");
            int motivo = getArguments().getInt("motivo");

            coleta.setMotivoCancelamento(descricao);
            coleta.setMotivoTipoCancelamento(motivo);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap newBitmap = BitmapFactory.decodeFile(fileImageCapture.getAbsolutePath(), options);
            byte[] foto = utils.getBytes(newBitmap);
            newBitmap.recycle();
            fileImageCapture.delete();

            coleta.setFotoCancelamentoColeta(foto);

            new ColetasDAO(getContext()).updateCancelaColetaFoto(coleta.getId(), coleta.getFotoCancelamentoColeta());

            if (cancelaColetaAdapter != null) {
                cancelaColetaAdapter.notifyDataSetChanged();
            }
        }
    }
}
