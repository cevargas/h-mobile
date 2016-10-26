package com.tn3.mobile.hermes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.holder.CidadesHolder;
import com.tn3.mobile.hermes.models.Cidade;
import com.tn3.mobile.hermes.models.Coletas;

import java.util.ArrayList;
import java.util.List;

public class ListaCidadesAdapter extends RecyclerView.Adapter<CidadesHolder> {

//public class ListaCidadesAdapter extends ArrayAdapter<Cidade> {

    private List<Cidade> listaCidades;
    private Context mContext;


    public ListaCidadesAdapter(Context context, List<Cidade> listaCidades) {
        this.listaCidades = listaCidades;
        this.mContext = context;
    }

    @Override
    public CidadesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cidades, null);
        return new CidadesHolder(view);
    }

    @Override
    public void onBindViewHolder(CidadesHolder cidadesHolder, final int i) {
        final Cidade cidade = listaCidades.get(i);

        if(i > 0) {
            cidadesHolder.itemCidadeLabel.setVisibility(View.GONE);
        }

        cidadesHolder.itemCidade.setText(cidade.getNome());
    }

    @Override
    public int getItemCount() {
        return (null != listaCidades ? listaCidades.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    /*
    public ListaCidadesAdapter(Context context, List<Cidade> listaCidades) {
        super(context, R.layout.list_cidades, listaCidades);
        this.listaCidades = listaCidades;
        this.mContext = context;
    }

    public View getView(int position, View v, ViewGroup parent) {

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_cidades, parent, false);
        }

        final Cidade cidade = getItem(position);
        Log.d("CIDADE", String.valueOf(position));

        TextView itemCidade = (TextView) v.findViewById(R.id.itemCidade);
        itemCidade.setText(cidade.getNome());

        return v;
    }

    @Override
    public Cidade getItem(int position) {
        return listaCidades.get(position);
    }

    @Override
    public int getCount() {
        return listaCidades.size();
    }

    @Override
    public int getPosition(Cidade item) {
        return super.getPosition(item);
    }*/

}