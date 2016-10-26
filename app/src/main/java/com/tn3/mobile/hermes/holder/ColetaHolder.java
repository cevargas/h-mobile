package com.tn3.mobile.hermes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tn3.mobile.hermes.R;

public class ColetaHolder extends RecyclerView.ViewHolder {

    public TextView textItensColetaCliente;
    public TextView textItensColetaHora;
    //public com.tn3.mobile.hermes.utils.TextAwesome iconClock;
    public com.tn3.mobile.hermes.utils.TextAwesome iconSincronizado;


    public ColetaHolder(View view) {
        super(view);
        this.textItensColetaCliente = (TextView) view.findViewById(R.id.textItensColetaCliente);
        this.textItensColetaHora = (TextView) view.findViewById(R.id.textItensColetaHora);
        //this.iconClock = (com.tn3.mobile.hermes.utils.TextAwesome) view.findViewById(R.id.iconClock);
        this.iconSincronizado = (com.tn3.mobile.hermes.utils.TextAwesome) view.findViewById(R.id.iconSincronizado);
    }
}
