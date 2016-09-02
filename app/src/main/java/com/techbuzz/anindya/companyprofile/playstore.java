package com.techbuzz.anindya.companyprofile;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Angry_Birds on 8/29/2016.
 */
public class playstore extends AppCompatActivity {

    private AdView mAdMobAdView;
    private String YourDeveloperName = "Lab Mimosa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // animation for activity entry
        this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_exit);

        setContentView(R.layout.playstore);

        // Set up the toolbar.
        Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.playstore_title);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Initializing Google AdMob
        mAdMobAdView = (AdView)findViewById(R.id.admob_adview);
        AdRequest adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("1797D2757F5140AA8F98809B458DB26F")// real device id here*/
                .build();
        mAdMobAdView.loadAd(adRequest);

        showGplayDialogToUser(playstore.this);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Detect when the back button is pressed

    @Override
    public void onBackPressed() {
            // Let the system handle the back button*/
            super.onBackPressed();
            // animation for activity exit
            overridePendingTransition(R.anim.animation_exit, R.anim.animation_enter);
       /* }*/
    }

//back button press from Google Play Store turn to home page
    public void onBackPressedGplay() {
        super.onBackPressed();
        // animation for activity exit
        overridePendingTransition(R.anim.animation_exit, R.anim.animation_enter);
        startActivity(new Intent(playstore.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showThanksDialogToUser(playstore.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Create Dialog popup for Google Play store
    public void showGplayDialogToUser(final Context context){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("Would you like to open your Google Play Store Apps?")
                .setTitle("Enter Google Play Store")
                .setMessage("For getting all the Apps List you have to open your Google Play Store Apps.")
                .setPositiveButton("Go Play Store", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Uri uri = Uri.parse("market://search?q=pub:" + YourDeveloperName);
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                       onBackPressedGplay();

                        try {

                            startActivity(myAppLinkToMarket);

                        } catch (ActivityNotFoundException e) {

                            //the device hasn't installed Google Play
                            Toast.makeText(getApplicationContext(), "You don't have Google Play installed", Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton(" Not Now ", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                startActivity(new Intent(playstore.this, MainActivity.class));
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    // Create Dialog popup for rating
    public static void showThanksDialogToUser(final Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Thanks")
                .setMessage("We are happy to see your interest to rate us")
                .setNegativeButton(" Ok ", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}