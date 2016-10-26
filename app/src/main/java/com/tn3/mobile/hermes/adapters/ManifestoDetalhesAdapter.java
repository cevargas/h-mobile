package com.tn3.mobile.hermes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tn3.mobile.hermes.R;
import com.tn3.mobile.hermes.dao.ColetasDAO;
import com.tn3.mobile.hermes.holder.ManifestoDetalhesHolder;
import com.tn3.mobile.hermes.models.Manifestos;
import com.tn3.mobile.hermes.utils.Utils;

//import com.google.android.gms.maps.MapView;

public class ManifestoDetalhesAdapter extends RecyclerView.Adapter<ManifestoDetalhesHolder> {

    private Manifestos manifesto;
    private Context mContext;
    //private MapView mapView;
    //protected List<MapLocation> mMapLocations = new ArrayList<>();

    //private ListView listCidadesView;

    public ManifestoDetalhesAdapter(Context context, Manifestos manifesto) {
        this.manifesto = manifesto;
        this.mContext = context;
        //this.listCidadesView = listCidadesView;
        //this.mapView = mapView;
    }

    @Override
    public ManifestoDetalhesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manifest_details, viewGroup, false);
        //return new ManifestoDetalhesHolder(viewGroup.getContext(), view, mapView); //TODO: Mapa
        return new ManifestoDetalhesHolder(viewGroup.getContext(), view);
    }

    @Override
    public void onBindViewHolder(ManifestoDetalhesHolder manifestoDetalhesHolder, int i) {
        Utils utils = new Utils(mContext);

        /**
         * TODO: a parte do mapa de rotas das cidades esta comentada
         * */

        /*
        if (!utils.isConnected()) {
            //manifestoDetalhesHolder.imgColetaMapa.setVisibility(View.GONE);
            utils.alert("Não foi possível determinar a Rota no Mapa: Sem acesso a Internet", Toast.LENGTH_LONG);

        } else if(!utils.checkGPS() && !utils.checkNetwork()){
            utils.alert("Não foi possível determinar sua localização: Sem acesso ao GPS)", Toast.LENGTH_LONG);

        } else {

            if(Geocoder.isPresent()) {

                Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

                ArrayList<String> addressFragments = new ArrayList<>();
                List<Address> destinations = new ArrayList<>();

                //cidades da rota
                //TODO tem que pegar as cidade inicial e a final alem das cidades intermediarias entre elas que nao estao mapeado ainda
                addressFragments.add("Passo Fundo - RS");
                addressFragments.add("Marau - RS");
                addressFragments.add("Porto Alegre - RS");
                addressFragments.add("Novo Hamburgo - RS");

                try {
                    for (i = 0; i < addressFragments.size(); i++) {

                        List<Address> addresses = geocoder.getFromLocationName(addressFragments.get(i), 1);

                        Address dest;
                        if (addresses != null && addresses.size() > 0) {
                            dest = addresses.get(0);
                            destinations.add(dest);
                        }
                    }

                    if (destinations.size() > 0) {
                        for (Address destination : destinations) {
                            double latitude = destination.getLatitude();
                            double longitude = destination.getLongitude();
                            MapLocation mapLocation = new MapLocation(destination.getFeatureName(), latitude, longitude);
                            mMapLocations.add(mapLocation);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //manifestoDetalhesHolder.imgColetaMapa.setTag(mMapLocations);
                manifestoDetalhesHolder.setMapLocation(mMapLocations);
            }
        }
        */

        //Rota de Cidades
        /*if(manifesto.getRota() != null) {
            if (manifesto.getRota().getViagem().size() > 0) {

                Log.d("MANIDEST", String.valueOf(manifesto.getRota().getViagem().size()));

                final ListaCidadesAdapter cidadesAdapter = new ListaCidadesAdapter(mContext, manifesto.getRota().getViagem());
                manifestoDetalhesHolder.listaCidadesColeta.setAdapter(cidadesAdapter);
            }
        }*/

        Utils date = new Utils();
        String ndate = date.formatDate(manifesto.getDataManifesto());
        manifestoDetalhesHolder.textDataResult.setText(ndate);

        String totalAbertos = new ColetasDAO(mContext).findColetasStatusApp(manifesto.getId(), "1");
        String totalConcluidos = new ColetasDAO(mContext).findColetasStatusApp(manifesto.getId(), "2");
        String totalCancelados = new ColetasDAO(mContext).findColetasStatusApp(manifesto.getId(), "3");

        manifestoDetalhesHolder.textCountColetados.setText(totalConcluidos);
        manifestoDetalhesHolder.textCountCancelados.setText(totalCancelados);
        manifestoDetalhesHolder.textCountRestantes.setText(totalAbertos);
        manifestoDetalhesHolder.textSituacaoResult.setText(manifesto.getSituacaoManifesto());
        manifestoDetalhesHolder.textCidadeOrigem.setText(manifesto.getRota().getOrigem().getNome());
        manifestoDetalhesHolder.textCidadeDestino.setText(manifesto.getRota().getDestino().getNome());

        String tipo = "";
        if(manifesto.getTipoManifesto().equals("1")) {
            tipo = "Coleta";
        }
        else {
            tipo = "Transferência";
        }

        manifestoDetalhesHolder.textTipoResult.setText(tipo);

        /*manifestoDetalhesHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MapaActivity.class);
                intent.putExtra("manifestoId", manifesto.getId());
                mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return (null != manifesto ? 1 : 0);
    }

    @Override
    public long getItemId(int position) {
        return manifesto.getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
}
