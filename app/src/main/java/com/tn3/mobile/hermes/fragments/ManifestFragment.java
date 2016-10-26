package com.tn3.mobile.hermes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.adapters.ListaManifestosAdapter;
import com.tn3.mobile.hermes.dao.AuthDAO;
import com.tn3.mobile.hermes.dao.ManifestosDAO;
import com.tn3.mobile.hermes.models.Manifestos;
import com.tn3.mobile.hermes.task.ConsultaManifestoTask;
import com.tn3.mobile.hermes.utils.Utils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManifestFragment extends Fragment {

    private static String TOKEN = null;
    private static String IMEI = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    static String URL = "http://interno.tn3.com.br:8083/hermesws/app/manifest/list";

    private List<Manifestos> lista;
    private ListaManifestosAdapter manifestoAdapter;

    public ManifestFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> params = new AuthDAO(getContext().getApplicationContext()).findAuth();
        TOKEN = params.get(0);
        IMEI = params.get(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manifest, container, false);
        final Context context = getActivity();
        final Utils utils = new Utils(context);

        //CARREGA LISTA DE MANIFESTOS DO BANCO
        //new ManifestosDAO(context).delete();

        lista = new ManifestosDAO(context).list();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listManifestos);
        final TextView textView = (TextView) view.findViewById(R.id.empty_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        manifestoAdapter = new ListaManifestosAdapter(context, lista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(manifestoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(lista.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(!utils.isConnected()){
                    Toast.makeText(context, "Você não esta conectado a internet!", Toast.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else {
                    try {
                        new ConsultaManifestoTask(URL, context, recyclerView, false, mSwipeRefreshLayout).execute(utils.convertToJson(TOKEN, IMEI));
                    } catch (Exception e) {
                        Log.e("ManifestFragment", e.getMessage());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }
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