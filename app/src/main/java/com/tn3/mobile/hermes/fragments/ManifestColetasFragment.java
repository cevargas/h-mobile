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
import com.tn3.mobile.hermes.adapters.ListaColetasAdapter;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.models.Coletas;
import java.util.List;

public class ManifestColetasFragment extends Fragment {

    public ManifestColetasFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manifest_coletas, container, false);

        //Recupera o ID do Manifesto passado da Activity
        String idManifesto = getArguments().getString("idManifesto");

        //Vai buscar no banco os dados deste ID
        List<Coletas> lista = new ColetasDAO(getContext()).findColetaManifestoById(null, Long.valueOf(idManifesto));

        final RecyclerView listColetas = (RecyclerView) view.findViewById(R.id.listColetas);
        final ListaColetasAdapter coletasAdapter = new ListaColetasAdapter(getContext(), lista, idManifesto);
        listColetas.setHasFixedSize(true);
        listColetas.setAdapter(coletasAdapter);
        listColetas.setLayoutManager(new LinearLayoutManager(getContext()));
        listColetas.setItemAnimator(new DefaultItemAnimator());

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
