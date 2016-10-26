package com.tn3.mobile.hermes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.tn3.mobile.hermes.R;

public class ChaveNfeHolder extends RecyclerView.ViewHolder {

    public TextView chave;

    public ChaveNfeHolder(View view) {
        super(view);
        this.chave = (TextView) view.findViewById(R.id.itemChave);
    }
}
