package com.tn3.mobile.hermes.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tn3.mobile.hermes.ColetasActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.holder.ColetaHolder;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.Utils;

import java.util.List;

public class ListaColetasAdapter extends RecyclerView.Adapter<ColetaHolder> {

    private List<Coletas> listaColetas;
    private Context mContext;
    private String idManifesto;
    private Utils utils = new Utils();

    public ListaColetasAdapter(Context context, List<Coletas> coletaItemList, String idManifesto) {
        this.listaColetas = coletaItemList;
        this.mContext = context;
        this.idManifesto = idManifesto;
    }

    @Override
    public ColetaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_itens_coletas, null);
        return new ColetaHolder(view);
    }

    @Override
    public void onBindViewHolder(ColetaHolder coletaHolder, final int i) {

        final Coletas coleta = listaColetas.get(i);

        coletaHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ColetasActivity.class);
                intent.putExtra("idColeta", coleta.getId());
                intent.putExtra("idManifesto", Long.valueOf(idManifesto));
                mContext.startActivity(intent);
            }
        });

        switch (coleta.getStatusApp()) {
            case "1":
                coletaHolder.iconSincronizado.setText(R.string.fa_clock_o);
                coletaHolder.iconSincronizado.setTextColor(ContextCompat.getColor(mContext, R.color.yellowborderbg));
                coletaHolder.iconSincronizado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("CLICKSYNC", "clicou cancelada");
                        AlertDialog alert = new AlertDialog.Builder(mContext).create();
                        alert.setTitle("Status da Coleta");
                        alert.setMessage("Aguardando coleta");
                        alert.setCancelMessage(null);
                        alert.show();
                    }
                });
                break;
            case "3":
                coletaHolder.iconSincronizado.setText(R.string.fa_exclamation_circle);
                coletaHolder.iconSincronizado.setTextColor(ContextCompat.getColor(mContext, R.color.accentover));
                coletaHolder.iconSincronizado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("CLICKSYNC", "clicou cancelada");
                        AlertDialog alert = new AlertDialog.Builder(mContext).create();
                        alert.setTitle("Status da Coleta");
                        alert.setMessage("Coleta cancelada");
                        alert.setCancelMessage(null);
                        alert.show();
                    }
                });
                break;
            default:
                coletaHolder.iconSincronizado.setText(R.string.fa_check_circle);
                coletaHolder.iconSincronizado.setTextColor(ContextCompat.getColor(mContext, R.color.accentdark));
                coletaHolder.iconSincronizado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("CLICKSYNC", "clicou finalizado");
                        AlertDialog alert = new AlertDialog.Builder(mContext).create();
                        alert.setTitle("Status da Coleta");
                        alert.setMessage("Coleta concluída");
                        alert.setCancelMessage(null);
                        alert.show();
                    }
                });
                break;
        }

        if(coleta.isSincronizado()) {
            coletaHolder.iconSincronizado.setText(R.string.fa_cloud_upload);
            coletaHolder.iconSincronizado.setTextColor(ContextCompat.getColor(mContext, R.color.blueborderbg));

            final String data = utils.dbFormatDate(coleta.getDhsincronizado());
            final String hora = utils.dbFormatHour(coleta.getDhsincronizado());

            coletaHolder.iconSincronizado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CLICKSYNC", "clicou sincronizado");

                    AlertDialog alert = new AlertDialog.Builder(mContext).create();
                    alert.setTitle("Status da Coleta");
                    alert.setMessage("Coleta sincronizada com o Servidor às " + hora + ", " + data);
                    alert.setCancelMessage(null);
                    alert.show();
                }
            });
        }

        coletaHolder.textItensColetaCliente.setText(coleta.getNomeCliente());

        String hora = (coleta.getHoraFixa() != null) ? coleta.getHoraFixa() : coleta.getHoraLimite();
        if(hora != null) {
            String[] output = hora.split(":");
            String formatHr = output[0] + "h" + output[1];
            coletaHolder.textItensColetaHora.setText(formatHr);
        }
    }

    @Override
    public int getItemCount() {
        return (null != listaColetas ? listaColetas.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return listaColetas.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
