package com.tn3.mobile.hermes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tn3.mobile.hermes.ManifestoActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.holder.ManifestoHolder;
import com.tn3.mobile.hermes.models.Manifestos;
import com.tn3.mobile.hermes.utils.Utils;

import java.text.ParseException;
import java.util.List;

public class ListaManifestosAdapter extends RecyclerView.Adapter<ManifestoHolder> {

    private List<Manifestos> listaManifestos;
    private Context mContext;

    public ListaManifestosAdapter(Context context, List<Manifestos> manifestos) {
        this.listaManifestos = manifestos;
        this.mContext = context;
    }

    @Override
    public ManifestoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_itens_manifestos, null);
        return new ManifestoHolder(view);
    }

    @Override
    public void onBindViewHolder(ManifestoHolder manifestoHolder, final int i) {

        final Manifestos manifesto = listaManifestos.get(i);

        manifestoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ManifestoActivity.class);
                intent.putExtra("idManifesto", manifesto.getId());
                mContext.startActivity(intent);
             }
        });

        Utils date = new Utils();
        String ndate = date.formatDate(manifesto.getDataManifesto());
        String mes = null, dia = null;
        try {
            mes = date.getMonth(ndate);
            dia = date.getDay(ndate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //TODO: analisar status de viagens
        if(manifesto.getSituacaoManifesto().equals("Em viagem")) {
            manifestoHolder.textRota.setText(manifesto.getRota().getDescricao());
            manifestoHolder.textStatus.setText(manifesto.getSituacaoManifesto());
            manifestoHolder.textStatus.setTextColor(ContextCompat.getColor(mContext, R.color.yellowborderbg));
            manifestoHolder.lnRelativeLine.setBackgroundResource(R.drawable.border_set_iniciado);
        }
        else {
            manifestoHolder.textRota.setText(manifesto.getRota().getDescricao());
            manifestoHolder.textStatus.setText(manifesto.getSituacaoManifesto());
        }

        manifestoHolder.diaManifesto.setText(dia);
        manifestoHolder.mesManifesto.setText(mes);
    }

    @Override
    public int getItemCount() {
        return (null != listaManifestos ? listaManifestos.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return listaManifestos.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}



