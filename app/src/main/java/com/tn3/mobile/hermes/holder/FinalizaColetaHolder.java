package com.tn3.mobile.hermes.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tn3.mobile.hermes.R;

public class FinalizaColetaHolder extends RecyclerView.ViewHolder {

    public TextView itemChave;

    public FinalizaColetaHolder(Context context, View view) {
        super(view);
        this.itemChave = (TextView) view.findViewById(R.id.itemChave);
    }
}
