package com.tn3.mobile.hermes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tn3.mobile.hermes.R;

public class CidadesHolder extends RecyclerView.ViewHolder {
    public TextView itemCidade;
    public TextView itemCidadeLabel;

    public CidadesHolder(View view) {
        super(view);
        this.itemCidade = (TextView) view.findViewById(R.id.itemCidade);
        this.itemCidadeLabel = (TextView) view.findViewById(R.id.itemCidadeLabel);
    }
}
