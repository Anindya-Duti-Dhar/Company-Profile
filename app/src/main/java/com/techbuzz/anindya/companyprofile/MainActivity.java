package com.techbuzz.anindya.companyprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    MenuItem mPreviousMenuItem;
    private AdView mAdMobAdView;

/*    //json variable
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    static String NAME = "Name";
    static String DESCRIPTION1 = "Desc1";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Welcome Toast
        Toast.makeText(getApplicationContext(),"Our Home Page",Toast.LENGTH_SHORT).show();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.app_name);

            // Initializing Google AdMob
            mAdMobAdView = (AdView)findViewById(R.id.admob_adview);
            AdRequest adRequest = new AdRequest.Builder()
                    /*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("1797D2757F5140AA8F98809B458DB26F")// real device id here*/
                    .build();
            mAdMobAdView.loadAd(adRequest);

        // Initializing Internet Check
        if (hasConnection(MainActivity.this)){
            //call methods
            //json call
            /*new DownloadJSON().execute();
*/

        }
        else{
            showNetDisabledAlertToUser(MainActivity.this);
        }

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                menuItem.setCheckable(true);
                menuItem.setChecked(true);
                if (mPreviousMenuItem != null) {
                    mPreviousMenuItem.setChecked(false);
                }
                mPreviousMenuItem = menuItem;


               /* //To Set Custom Title bar for each fragment of selected item
                setTitle(menuItem.getTitle());*/


                //Closing drawer on item click
                drawerLayout.closeDrawers();


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Go about Page;
                    case R.id.about:
                        Toast.makeText(getApplicationContext(),"About Selected",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, about.class));
                        return true;


                    //Go contact Page;
                    case R.id.contact:
                        Toast.makeText(getApplicationContext(),"contact Selected",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, contact.class));
                        return true;


                    // Go playstore page;
                    case R.id.playstore:
                        Toast.makeText(getApplicationContext(),"playstore Selected",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, playstore.class));
                        return true;

                    // Go website page;
                    case R.id.website:
                        Toast.makeText(getApplicationContext(),"website Selected",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, website.class));
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });


        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {

                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };


        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


   // LoadJSON AsyncTask
   // DownloadJSON AsyncTask
 /*  private class DownloadJSON extends AsyncTask<Void, Void, Void> {

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           // Create a progressdialog
           mProgressDialog = new ProgressDialog(MainActivity.this);
           // Set progressdialog title
           mProgressDialog.setTitle("Data Fetching");
           // Set progressdialog message
           mProgressDialog.setMessage("Loading...");
           mProgressDialog.setIndeterminate(false);
           // Show progressdialog
           mProgressDialog.show();
       }

       @Override
       protected Void doInBackground(Void... params) {
           // Create an array

           String json = null;
           try {
               InputStream is = getAssets().open("khans.json");
               int size = is.available();
               byte[] buffer = new byte[size];
               is.read(buffer);
               is.close();
               json = new String(buffer, "UTF-8");
           } catch (IOException ex) {
               ex.printStackTrace();
               return null;
           }


           try {
               // Locate the array name in JSON
               JSONObject obj = new JSONObject(json);
               JSONArray m_jArry = obj.getJSONArray("model");

               for (int i = 0; i < jsonarray.length(); i++) {
                   HashMap<String, String> map = new HashMap<String, String>();
                   jsonobject = jsonarray.getJSONObject(i);
                   // Retrive JSON Objects
                   map.put("Name", jsonobject.getString("Name"));
                   map.put("Desc1", jsonobject.getString("Desc1"));
                   // Set the JSON Objects into the array
                   arraylist.add(map);
               }
           } catch (JSONException e) {
               Log.e("Error", e.getMessage());
               e.printStackTrace();
           }
           return null;

       }

       @Override
       protected void onPostExecute(Void args) {
           // Locate the listview in listview_main.xml
           listview = (ListView) findViewById(R.id.listview);
           // Pass the results into ListViewAdapter.java
           adapter = new ListViewAdapter(MainActivity.this, arraylist);
           // Set the adapter to the ListView
           listview.setAdapter(adapter);
           // Close the progressdialog
           mProgressDialog.dismiss();
       }
   }*/



    // Internet check method
    public boolean hasConnection(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()){
            return true;
        }
        NetworkInfo mobileNetwork=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()){
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()){
            return true;
        }
        return false;
    }

    // Create Dialog popup for internet checking
    public static void showNetDisabledAlertToUser(final Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Would you like to enable it?")
                .setTitle("No Internet Connection")
                .setMessage("For getting updates you have to enable your internet connection")
                .setPositiveButton(" Enable Internet ", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);
                    }
                });

        alertDialogBuilder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
            showThanksDialogToUser(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Create Dialog popup for Rating
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