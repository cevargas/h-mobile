package com.tn3.mobile.hermes.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.tn3.mobile.hermes.R;

public class CancelaColetaHolder extends RecyclerView.ViewHolder {

    public EditText descricaoMotivoCanc;
    //public RadioButton radioAusente;
    //public RadioButton radioRecusa;
    public Spinner spinnerOptsCanc;
    public ImageView imageColetaCancelamento;

    public CancelaColetaHolder(Context context, View view) {
        super(view);
        this.descricaoMotivoCanc = (EditText) view.findViewById(R.id.descricaoMotivoCanc);
        this.spinnerOptsCanc = (Spinner) view.findViewById(R.id.spinnerOptsCanc);
        //this.radioRecusa = (RadioButton) view.findViewById(R.id.radioRecusa);
        this.imageColetaCancelamento = (ImageView) view.findViewById(R.id.imageColetaCancelamento);
    }
}
