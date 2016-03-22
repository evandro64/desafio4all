package com.efb.desafio4all.fragments;

/**
 * Created by barbosev on 22/03/2016.
 */

import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efb.desafio4all.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;


public class Map_Fragment extends Fragment implements OnMapReadyCallback {

    private Context mContext;
    private SupportMapFragment supportMapFragment;
    private View rootView;
    private GoogleMap googleMap;
    private LatLng mLocal;

    public static Map_Fragment
        newInstance(LatLng local) {
            Map_Fragment fragment = new Map_Fragment();
            Bundle args = new Bundle();
            args.putParcelable("teste",local);
            fragment.setArguments(args);
        return fragment;
        }

    public Map_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLocal = getArguments().getParcelable("teste"); //Recupera o local passado por parâmetro
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        supportMapFragment = SupportMapFragment.newInstance();
        rootView = inflater.inflate(R.layout.fragment_map, null);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().replace(R.id.map, supportMapFragment).commitAllowingStateLoss();
        }
        supportMapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        configBasicMap(googleMap);
    }

    private void configBasicMap(GoogleMap googleMap) {
        //LatLng sydney = new LatLng(-33.867, 151.206); //Cria uma coordenada de ponto para o mapa
        //Configura o zoom da camera do mapa
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        /**
         * Adiciona um pino na posição da coordenada.
         * Ao clicar em cima do pino exibirá o título Sidney e a descrição que está no snippet
         */
        /*googleMap.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("Cidade mais populosa da Austrália.")
                .position(sydney));*/
        //getMyLocation();

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        //LatLng myLocal = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng myLocal;
        myLocal = mLocal;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocal, 13));
        googleMap.addMarker(new MarkerOptions()
                .title("Teste Title")
                .snippet("Descrição do Title")
                .position(myLocal));
    }
}

