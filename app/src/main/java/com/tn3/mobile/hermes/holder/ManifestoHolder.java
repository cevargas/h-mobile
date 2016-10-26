package com.tn3.mobile.hermes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tn3.mobile.hermes.R;

public class ManifestoHolder extends RecyclerView.ViewHolder {

    public TextView textRota;
    public TextView textStatus;
    public TextView mesManifesto;
    public TextView diaManifesto;

    public LinearLayout lnLayt;
    public RelativeLayout lnRelativeLine;

    public ManifestoHolder(View view) {
        super(view);
        this.textRota = (TextView) view.findViewById(R.id.textRota);
        this.textStatus = (TextView) view.findViewById(R.id.textStatus);
        this.mesManifesto = (TextView) view.findViewById(R.id.mesManifesto);
        this.diaManifesto = (TextView) view.findViewById(R.id.diaManifesto);
        this.lnLayt = (LinearLayout) view.findViewById(R.id.lnLayt);

        this.lnRelativeLine = (RelativeLayout) view.findViewById(R.id.lnRelativeLine);
    }
}
