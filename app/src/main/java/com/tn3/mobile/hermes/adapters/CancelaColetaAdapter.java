package com.tn3.mobile.hermes.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tn3.mobile.hermes.CancelarColetaActivity;
import com.tn3.mobile.hermes.MainActivity;
import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.holder.CancelaColetaHolder;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class CancelaColetaAdapter extends RecyclerView.Adapter<CancelaColetaHolder> {

    private Coletas coleta;
    private Context mContext;
    private Utils utils = new Utils();

    public CancelaColetaAdapter(Context context, Coletas coleta) {
        this.coleta = coleta;
        this.mContext = context;
    }

    @Override
    public CancelaColetaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manifest_coletas_cancela, viewGroup, false);
        return new CancelaColetaHolder(viewGroup.getContext(), view);
    }

    @Override
    public void onBindViewHolder(final CancelaColetaHolder cancelaColetaHolder, int i) {

        cancelaColetaHolder.descricaoMotivoCanc.setText(coleta.getMotivoCancelamento());
        int pos = 0;
        if(coleta.getMotivoTipoCancelamento() > 0) {
            //tem que fazer esse de para correspondente ao codido do cancelamento no hermes
            //indice da lista no android dos tipos de cancelamento nao tem id, eh referenciado pela posicao.
            int tpMotivo = coleta.getMotivoTipoCancelamento();
            if(tpMotivo == 7) {
                pos = 0;
            }
            else if(tpMotivo == 8) {
                pos = 1;
            }
            else {
                pos = 2;
            }

            String items = mContext.getResources().getStringArray(R.array.list_opts_canc_array)[pos];
            Log.d("ITEMDB", items);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, R.array.list_opts_canc_array, android.R.layout.simple_spinner_dropdown_item);
        cancelaColetaHolder.spinnerOptsCanc.setAdapter(adapter);
        cancelaColetaHolder.spinnerOptsCanc.setSelection(pos);
        cancelaColetaHolder.spinnerOptsCanc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(coleta.getFotoCancelamentoColeta() != null) {

            Bitmap scaled = ThumbnailUtils.extractThumbnail(utils.getImage(coleta.getFotoCancelamentoColeta()), 256, 256);

            cancelaColetaHolder.imageColetaCancelamento.setImageBitmap(scaled);
            cancelaColetaHolder.imageColetaCancelamento.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Log.d("LONGCLICKIMAGE", "click na imagem");

                    AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                    dialog.setTitle("Confirmação");
                    dialog.setMessage("Deseja excluir a foto?");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {
                            Log.d("DELETEFOTO", "Deletou foto");
                            cancelaColetaHolder.imageColetaCancelamento.setVisibility(View.GONE);
                            notifyDataSetChanged();
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int buttonId) {
                        }
                    });
                    dialog.show();

                    return true;
                }
            });
        }

        if (coleta.getStatusApp().equals("2") || coleta.getStatusApp().equals("3")) {
            cancelaColetaHolder.descricaoMotivoCanc.setEnabled(false);
            cancelaColetaHolder.spinnerOptsCanc.setEnabled(false);
            cancelaColetaHolder.imageColetaCancelamento.setLongClickable(false);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return (null != coleta ? 1 : 0);
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
