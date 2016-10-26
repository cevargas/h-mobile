package com.tn3.mobile.hermes.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tn3.mobile.hermes.CancelarColetaActivity;
import com.tn3.mobile.hermes.ColetasActivity;
import com.tn3.mobile.hermes.FinalizaColetaActivity;
import com.tn3.mobile.hermes.MainActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.holder.ColetaDetalhesHolder;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.models.Manifestos;

import java.sql.Struct;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListaColetasDetalhesAdapter extends RecyclerView.Adapter<ColetaDetalhesHolder> {

    private Coletas coleta;
    private Context mContext;
    private String idManifesto;

    public Locale BRAZIL = new Locale("pt", "BR");

    public ListaColetasDetalhesAdapter(Context context, Coletas coleta, String idManifesto) {
        this.coleta = coleta;
        this.mContext = context;
        this.idManifesto = idManifesto;
    }

    @Override
    public ColetaDetalhesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manifest_coletas_details, null);
        return new ColetaDetalhesHolder(view);
    }

    @Override
    public void onBindViewHolder(ColetaDetalhesHolder coletaDetalhesHolder, int position) {

        coletaDetalhesHolder.textHoraFixaValor.setText(coleta.getHoraFixa());
        coletaDetalhesHolder.textHoraLimiteValor.setText(coleta.getHoraLimite());
        coletaDetalhesHolder.textNomeCliente.setText(coleta.getNomeCliente());

        String endereco = null;
        if(coleta.getEndereco().getLogradouro()!= null) {

            endereco = coleta.getEndereco().getLogradouro();

            if(coleta.getEndereco().getNumero() > 0) {
                endereco = endereco + ", " + coleta.getEndereco().getNumero();
            }
        }
        coletaDetalhesHolder.textEnderecoCliente.setText(endereco);

        if(coleta.getEndereco().getBairro() != null) {
            coletaDetalhesHolder.textEnderecoBairro.setText(coleta.getEndereco().getBairro());
        }

        if(coleta.getEndereco().getComplemento() != null) {
            coletaDetalhesHolder.textEnderecoComplemento.setText(coleta.getEndereco().getComplemento());
        }
        String cidade = null;
        if( coleta.getEndereco().getCidade() != null) {
            cidade = coleta.getEndereco().getCidade() + " - " + coleta.getEndereco().getEstado();
        }
        coletaDetalhesHolder.textEnderecoCidade.setText(cidade);

        if(coleta.getEndereco().getSetor() != null) {
            coletaDetalhesHolder.textClienteSetor.setText(coleta.getEndereco().getSetor());
        }

        if(coleta.getTotalCarga() != null) {
            coletaDetalhesHolder.textCargaTotalValor.setText(String.valueOf(coleta.getTotalCarga()));
        }
        if(coleta.getCubagem() != null) {
            coletaDetalhesHolder.textCubagemValor.setText(coleta.getCubagem());
        }
        if(coleta.getQtdNotas() > 0) {
            coletaDetalhesHolder.textQuantidadeNfesValor.setText(String.valueOf(coleta.getQtdNotas()));
        }

        if(coleta.getValorTotal() != null) {
            NumberFormat n = NumberFormat.getInstance(BRAZIL);
            n.setMinimumFractionDigits(2); //Seta o número mínimo de casa decimal
            n.setMaximumFractionDigits(2); //Seta o número máximo de casa decimal
            String total = n.format(coleta.getValorTotal());
            coletaDetalhesHolder.textTotalValorRSNfesValor.setText(total);
        }

        if(coleta.getStatusApp().equals("2")) {
            coletaDetalhesHolder.linearStatusColeta.setVisibility(View.VISIBLE);
            coletaDetalhesHolder.linearStatusColeta.setAlpha(0.0f);
            coletaDetalhesHolder.linearStatusColeta.animate().translationY(coletaDetalhesHolder.linearStatusColeta.getHeight()).alpha(1.0f).setDuration(1000);

            coletaDetalhesHolder.linearStatusColeta.setBackgroundColor(ContextCompat.getColor(mContext, R.color.accentdark));
            coletaDetalhesHolder.textStatusColeta.setText("Coleta concluída");
            coletaDetalhesHolder.iconStatus.setText(R.string.fa_check_circle);

            coletaDetalhesHolder.linearStatusColeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_fin = new Intent(mContext, FinalizaColetaActivity.class);
                    intent_fin.putExtra("idColeta", coleta.getId().toString());
                    intent_fin.putExtra("idManifesto", idManifesto);
                    mContext.startActivity(intent_fin);
                }
            });
        }
        if(coleta.getStatusApp().equals("3")) {
            coletaDetalhesHolder.linearStatusColeta.setVisibility(View.VISIBLE);
            coletaDetalhesHolder.linearStatusColeta.setAlpha(0.0f);
            coletaDetalhesHolder.linearStatusColeta.animate().translationY(coletaDetalhesHolder.linearStatusColeta.getHeight()).alpha(1.0f).setDuration(1000);

            coletaDetalhesHolder.linearStatusColeta.setBackgroundColor(ContextCompat.getColor(mContext, R.color.accentover));
            coletaDetalhesHolder.textStatusColeta.setText("Coleta cancelada");
            coletaDetalhesHolder.iconStatus.setText(R.string.fa_exclamation_circle);

            coletaDetalhesHolder.linearStatusColeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CancelarColetaActivity.class);
                    intent.putExtra("idColeta", coleta.getId().toString());
                    intent.putExtra("idManifesto", idManifesto);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != coleta ? 1 : 0);
    }

    @Override
    public long getItemId(int position) {
        return coleta.getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
