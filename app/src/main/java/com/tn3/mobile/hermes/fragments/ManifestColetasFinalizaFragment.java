package com.tn3.mobile.hermes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tn3.mobile.hermes.FinalizaColetaActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.adapters.FinalizaColetaAdapter;
import com.tn3.mobile.hermes.dao.ChaveNfeDAO;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManifestColetasFinalizaFragment extends Fragment implements FinalizaColetaActivity.FragmentListener {

    List<String> lstChaves = new ArrayList<>();
    Utils utils = new Utils();
    Coletas coleta;
    RecyclerView finalizaColeta;
    FinalizaColetaAdapter finalizaColetaAdapter;

    public ManifestColetasFinalizaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manifest_coletas_finaliza, container, false);

        String idColeta = getArguments().getString("idColeta");

        coleta = new ColetasDAO(getContext()).findColetaById(idColeta);

        if(coleta.getChavesNfe() != null) {
            for(String chave : coleta.getChavesNfe()) {
                lstChaves.add(chave);
            }
            coleta.setChavesNfe(lstChaves);
        }

        finalizaColeta = (RecyclerView) view.findViewById(R.id.itemColetaFinalizar);
        return view;
    }

    public void updateList(){
        finalizaColetaAdapter = new FinalizaColetaAdapter(getContext(), coleta.getChavesNfe(), coleta);
        finalizaColeta.setAdapter(finalizaColetaAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        finalizaColeta.setLayoutManager(linearLayoutManager);
        finalizaColeta.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((FinalizaColetaActivity) getActivity()).setFragmentListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
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
    public void updateFragmentList(String chave) {
        //Recupera o valor da chave escaneada ou digitadas passado pela activity
        if(chave != null) {
            lstChaves.add(0, chave);
            coleta.setChavesNfe(lstChaves);

            if(finalizaColetaAdapter != null) {
                finalizaColetaAdapter.notifyDataSetChanged();
            }

            new ChaveNfeDAO(getContext()).salvarChave(coleta.getId(), chave);
        }
    }
}
