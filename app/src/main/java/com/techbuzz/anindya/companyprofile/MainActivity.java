package com.techbuzz.anindya.companyprofile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private CardView cardView, cardView1;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    MenuItem mPreviousMenuItem;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    private AdView mAdMobAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing Google AdMob
            mAdMobAdView = (AdView)findViewById(R.id.admob_adview);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("1797D2757F5140AA8F98809B458DB26F")// real device id here
                    .build();
            mAdMobAdView.loadAd(adRequest);

        // Initializing Internet Check
        if (hasConnection(MainActivity.this)){
            //call methods
            //getJsonData();
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


                //To Set Custom Title bar for each fragment of selected item
                setTitle(menuItem.getTitle());


                //Closing drawer on item click
                drawerLayout.closeDrawers();


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with about Which is our Inbox View;
                    case R.id.about:
                        Toast.makeText(getApplicationContext(),"About Selected",Toast.LENGTH_SHORT).show();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        about fragment = new about();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                         /* //for animation transaction
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out);*/
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;


                    //Replacing the main content with contact Which is our discover View;
                    case R.id.contact:
                        Toast.makeText(getApplicationContext(),"contact Selected",Toast.LENGTH_SHORT).show();
                        contact fragment1 = new contact();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                         /* //for animation transaction
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out);*/
                        fragmentTransaction.replace(R.id.frame,fragment1);
                        fragmentTransaction.commit();
                        return true;


                    //Replacing the main content with playstore Which is our media View;
                    case R.id.playstore:
                        Toast.makeText(getApplicationContext(),"playstore Selected",Toast.LENGTH_SHORT).show();
                        playstore fragment2 = new playstore();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        /* //for animation transaction
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out);*/
                        fragmentTransaction.replace(R.id.frame,fragment2);
                        fragmentTransaction.commit();
                        return true;


                    //Replacing the main content with website Which is our Friends View;
                    case R.id.website:
                        Toast.makeText(getApplicationContext(),"website Selected",Toast.LENGTH_SHORT).show();
                        website fragment3 = new website();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        /* //for animation transaction
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_up_out);*/
                        fragmentTransaction.replace(R.id.frame,fragment3);
                        fragmentTransaction.commit();
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

    public static void showNetDisabledAlertToUser(final Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Would you like to enable it?")
                .setTitle("No Internet Connection")
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}