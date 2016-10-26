package com.tn3.mobile.hermes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.adapters.ListaColetasDetalhesAdapter;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.models.Cidade;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.models.Endereco;

import java.math.BigDecimal;

public class ManifestColetasDetalhesFragment extends Fragment {

    public ManifestColetasDetalhesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manifest_coletas_details, container, false);

        //Recupera o ID da Coleta passado da ColetasActivity
        String idColeta = getArguments().getString("idColeta");
        String idManifesto = getArguments().getString("idManifesto");

        Coletas coleta = new ColetasDAO(getContext()).findColetaById(idColeta);

        final RecyclerView listManifestos = (RecyclerView) view.findViewById(R.id.itemColeta);
        final ListaColetasDetalhesAdapter coletaDetalhesAdapter = new ListaColetasDetalhesAdapter(getContext(), coleta, idManifesto);
        listManifestos.setHasFixedSize(true);
        listManifestos.setAdapter(coletaDetalhesAdapter);
        listManifestos.setLayoutManager(new LinearLayoutManager(getContext()));
        listManifestos.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
