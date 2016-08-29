package com.techbuzz.anindya.companyprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class about extends Fragment{

    private AdView mAdMobAdView;

    public about() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.about, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Initialize any kind of view
        super.onViewCreated(view, savedInstanceState);

        // Initializing Google AdMob
        mAdMobAdView = (AdView) view.findViewById(R.id.admob_adview);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("1797D2757F5140AA8F98809B458DB26F")// real device id here
                .build();
        mAdMobAdView.loadAd(adRequest);

    }

}
