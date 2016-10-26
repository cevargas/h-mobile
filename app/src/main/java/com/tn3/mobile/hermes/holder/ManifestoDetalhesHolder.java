package com.tn3.mobile.hermes.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tn3.mobile.hermes.R;

/*
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
*/

//public class ManifestoDetalhesHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
public class ManifestoDetalhesHolder extends RecyclerView.ViewHolder  {

    public TextView textCountColetados;
    public TextView textCountCancelados;
    public TextView textCountRestantes;
    public TextView textTipoResult;
    public TextView textDataResult;
    public TextView textSituacaoResult;
    public TextView textRota;
    public Context mContext;
    //public RecyclerView listaCidadesColeta;
    //public ListView listaCidadesColeta;
    public TextView textCidadeOrigem;
    public TextView textCidadeDestino;

    //TODO: Mapa
    //public MapView imgColetaMapa;
    //protected GoogleMap mGoogleMap;
    //protected List<MapLocation> mMapLocationList;
    //public ManifestoDetalhesHolder(Context context, View view, MapView mapView) {

    public ManifestoDetalhesHolder(Context context, View view) {
        super(view);
        mContext = context;

        //this.imgColetaMapa = mapView;
        //this.imgColetaMapa.getMapAsync(this);

        this.textCountColetados = (TextView) view.findViewById(R.id.textCountColetados);
        this.textCountRestantes = (TextView) view.findViewById(R.id.textCountRestantes);
        this.textCountCancelados = (TextView) view.findViewById(R.id.textCountCancelados);
        this.textTipoResult = (TextView) view.findViewById(R.id.textTipoResult);
        this.textDataResult = (TextView) view.findViewById(R.id.textDataResult);
        this.textSituacaoResult = (TextView) view.findViewById(R.id.textSituacaoResult);
        this.textCidadeOrigem = (TextView) view.findViewById(R.id.textCidadeOrigem);
        this.textCidadeDestino = (TextView) view.findViewById(R.id.textCidadeDestino);
        //this.listaCidadesColeta = (ListView) view.findViewById(R.id.listaCidadesColeta);
    }

    /*
    public void setMapLocation(List<MapLocation> mapLocation) {
        // mMapLocation = mapLocation;
        mMapLocationList = mapLocation;

        if (mGoogleMap != null) {
            updateMapContents();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        MapsInitializer.initialize(mContext);

        if (mMapLocationList != null) {
            updateMapContents();
        }
    }

    protected void updateMapContents() {

        mGoogleMap.clear();

        if (mMapLocationList.size() > 0) {

            PolylineOptions rectOptions = new PolylineOptions();
            for (MapLocation map : mMapLocationList) {
                mGoogleMap.addMarker(new MarkerOptions().position(map.center)).setTitle(map.name);
                rectOptions.add(map.center).width(5).color(Color.BLUE);
            }

            mGoogleMap.addPolyline(rectOptions);

            mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
            mGoogleMap.getUiSettings().setCompassEnabled(true);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(mMapLocationList.get(0).center).zoom(12).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    try {
                        mGoogleMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        Log.e("ManifestoDetalhesHolder", e.getMessage());
                    }
                }
            });
        }
    }*/
}
