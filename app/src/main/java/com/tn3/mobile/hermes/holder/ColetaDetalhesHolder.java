package com.tn3.mobile.hermes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.utils.TextAwesome;

public class ColetaDetalhesHolder extends RecyclerView.ViewHolder {
    public TextView textHoraFixaValor;
    public TextView textHoraLimiteValor;
    public TextView textNomeCliente;
    public TextView textEnderecoCliente;
    public TextView textEnderecoBairro;
    public TextView textEnderecoComplemento;
    public TextView textEnderecoCidade;
    public TextView textClienteSetor;
    public TextView textQuantidadeNfesValor;
    public TextView textTotalValorRSNfesValor;
    public TextView textCargaTotalValor;
    public TextView textCubagemValor;
    public TextView textStatusColeta;

    public TextAwesome iconStatus;
    public RelativeLayout linearStatusColeta;

    public ColetaDetalhesHolder(View view) {
        super(view);

        this.textHoraFixaValor = (TextView) view.findViewById(R.id.textHoraFixaValor);
        this.textHoraLimiteValor = (TextView) view.findViewById(R.id.textHoraLimiteValor);
        this.textNomeCliente = (TextView) view.findViewById(R.id.textNomeCliente);
        this.textEnderecoCliente = (TextView) view.findViewById(R.id.textEnderecoCliente);
        this.textEnderecoBairro = (TextView) view.findViewById(R.id.textEnderecoBairro);
        this.textEnderecoComplemento = (TextView) view.findViewById(R.id.textEnderecoComplemento);
        this.textEnderecoCidade = (TextView) view.findViewById(R.id.textEnderecoCidade);
        this.textClienteSetor = (TextView) view.findViewById(R.id.textClienteSetor);
        this.textQuantidadeNfesValor = (TextView) view.findViewById(R.id.textQuantidadeNfesValor);
        this.textTotalValorRSNfesValor = (TextView) view.findViewById(R.id.textTotalValorRSNfesValor);
        this.textCargaTotalValor = (TextView) view.findViewById(R.id.textCargaTotalValor);
        this.textCubagemValor = (TextView) view.findViewById(R.id.textCubagemValor);
        this.textStatusColeta = (TextView) view.findViewById(R.id.textStatusColeta);

        this.linearStatusColeta = (RelativeLayout) view.findViewById(R.id.linearStatusColeta);
        this.iconStatus = (TextAwesome) view.findViewById(R.id.iconStatus);
    }
}
