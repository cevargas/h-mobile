package com.tn3.mobile.hermes.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.dao.ChaveNfeDAO;
import com.tn3.mobile.hermes.holder.FinalizaColetaHolder;
import com.tn3.mobile.hermes.models.Coletas;

import java.util.List;

public class FinalizaColetaAdapter extends RecyclerView.Adapter<FinalizaColetaHolder> {

    List<String> listchaves;
    private Context mContext;
    private Coletas coleta;

    public FinalizaColetaAdapter(Context context, List<String> listchaves, Coletas coleta) {
        this.listchaves = listchaves;
        this.mContext = context;
        this.coleta = coleta;
    }

    @Override
    public FinalizaColetaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_chaves_nfes, viewGroup, false);
        return new FinalizaColetaHolder(viewGroup.getContext(), view);
    }

    @Override
    public void onBindViewHolder(FinalizaColetaHolder coletaFinalizaHolder, int i) {

        final String chave = listchaves.get(i);
        coletaFinalizaHolder.itemChave.setText(chave);
        coletaFinalizaHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                dialog.setTitle("Confirmação");
                dialog.setMessage("Deseja excluir a chave " + chave);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int buttonId) {
                        Log.d("DELETECHAVE", chave);
                        listchaves.remove(chave);
                        notifyDataSetChanged();
                        new ChaveNfeDAO(mContext).deleteChave(chave);
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                    }
                });
                dialog.show();
                Log.d("CLICKNACHAVE", chave);
            }
        });

        if (coleta.getStatusApp().equals("2") || coleta.getStatusApp().equals("3")) {
            coletaFinalizaHolder.itemView.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return (null != listchaves ? listchaves.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
