package com.tn3.mobile.hermes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.adapters.ListaCidadesAdapter;
import com.tn3.mobile.hermes.adapters.ManifestoDetalhesAdapter;
import com.tn3.mobile.hermes.dao.ManifestosDAO;
import com.tn3.mobile.hermes.models.Cidade;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.models.Endereco;
import com.tn3.mobile.hermes.models.Manifestos;
import com.tn3.mobile.hermes.models.Rota;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.google.android.gms.maps.MapView;

public class ManifestDetailsFragment extends Fragment {

    //MapView mapView = null;

    //public ListView listaCidadesColeta;

    public ManifestDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manifest_details, container, false);
        final Context context = getActivity();

        //Recupera o ID do Manifesto passado da Activity
        String idManifesto = getArguments().getString("idManifesto");

        //busca no banco os dados deste ID
        Manifestos manifesto = new ManifestosDAO(context).findManifestoById(idManifesto);

        final RecyclerView detalhesManifesto = (RecyclerView) view.findViewById(R.id.manifestDetails);
        detalhesManifesto.setNestedScrollingEnabled(false);
        //detalhesManifesto.setHasFixedSize(false);

        final RecyclerView rwCidades = (RecyclerView) view.findViewById(R.id.listaCidadesColeta);
        rwCidades.setNestedScrollingEnabled(false);
        //rwCidades.setHasFixedSize(false);


        /*TODO: Chamada do MapView para o google maps
        mapView = (MapView) view.findViewById(R.id.imgColetaMapa);
        mapView.onCreate(null);
        mapView.onResume();
        final ManifestoDetalhesAdapter manifestoDetalhesAdapter = new ManifestoDetalhesAdapter(getContext(), manifesto, mapView);
        */

        //listaCidadesColeta = (ListView) view.findViewById(listaCidadesColeta.);

        final ManifestoDetalhesAdapter manifestoDetalhesAdapter = new ManifestoDetalhesAdapter(getContext(), manifesto);
        detalhesManifesto.setLayoutManager(new LinearLayoutManager(getContext()));
        detalhesManifesto.setItemAnimator(new DefaultItemAnimator());
        detalhesManifesto.setAdapter(manifestoDetalhesAdapter);

        if(manifesto.getRota() != null) {
            if (manifesto.getRota().getViagem().size() > 0) {
                final ListaCidadesAdapter listaCidadesAdapter = new ListaCidadesAdapter(getContext(), manifesto.getRota().getViagem());
                rwCidades.setLayoutManager(new LinearLayoutManager(getContext()));
                rwCidades.setItemAnimator(new DefaultItemAnimator());
                rwCidades.setAdapter(listaCidadesAdapter);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //mapView.onLowMemory();
    }
}
