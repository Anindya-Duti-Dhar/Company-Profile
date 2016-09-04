package com.techbuzz.anindya.companyprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    MenuItem mPreviousMenuItem;
    private AdView mAdMobAdView;

    //json variable
    TextView mCategory, mDesc1, mDesc2, mBorn, mId, mPicture, mEmptyTextView;
    ImageView mprofileImage;
    ProgressBar mProgressBar;
    ListView list;
    String friendsJSON;
    ArrayList<Category> arraylist;
    JsonParsingHelper parser = new JsonParsingHelper();

    Context context;

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
        }
        else{
            showNetDisabledAlertToUser(MainActivity.this);
        }

        //JSON call
        list = (ListView) findViewById(android.R.id.list);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) findViewById(R.id.empty);

        mEmptyTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        //read json file from assets folder
        friendsJSON = Utils.jsonToStringFromAssetFolder("khans.json",
                getApplicationContext());

        //call json method
        new GetList().execute();

        //add listener to each item on listView
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(),
                        "selected " + arraylist.get(arg2).getName(), Toast.LENGTH_SHORT)
                        .show();
                //Passing data via Intent to the next Page
                Intent intent = new Intent(MainActivity.this, SingleItemView.class);
                // Pass all data name
                intent.putExtra("name", arraylist.get(arg2).getName());
                /*// Pass all data born
                intent.putExtra("born", arraylist.get(arg2).getBorn());*/
                // Pass all data id
                intent.putExtra("id", arraylist.get(arg2).getId());
                // Pass all data description1
                intent.putExtra("desc1", arraylist.get(arg2).getDesc1());
                // Pass all data description2
                intent.putExtra("desc2", arraylist.get(arg2).getDesc2());


                // Start SingleItemView Class
                startActivity(intent);
            }

        });

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


    //Json calling Method
    private class GetList extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Void... unused) {

            try {
                arraylist = parser.getCities(friendsJSON);
            } catch (Exception e) {
                // TODO: handle exception
            }
            return null;
        }

        protected void onPostExecute(Void unused) {

            if (arraylist != null && arraylist.size() - 1 != 0) {
                list.setAdapter(new MyAdapter());
            } else {
                mEmptyTextView.setVisibility(View.VISIBLE);
                mEmptyTextView.setText("No data found..!!");
            }
            mProgressBar.setVisibility(View.GONE);
        }


        class MyAdapter extends BaseAdapter {

            public int getCount() {
                // TODO Auto-generated method stub
                return arraylist.size();
            }

            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return null;
            }

            public long getItemId(int arg0) {
                // TODO Auto-generated method stub
                return 0;
            }


            //Inflate the listView
            public View getView(final int position, View v1, ViewGroup arg2) {
                // TODO Auto-generated method stub
                View v = null;

                LayoutInflater l = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = l.inflate(R.layout.list_item, arg2, false);

                /*// list-optimisation
                View rowview = v;
                if (rowview == null) {
                    LayoutInflater linf = (LayoutInflater) getApplicationContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    rowview = linf.inflate(R.layout.list_item, null);
                }*/

                mCategory = (TextView) v.findViewById(R.id.user_name);
               /* mBorn = (TextView) v.findViewById(R.id.user_born);*/
                mId = (TextView) v.findViewById(R.id.user_id);
                mDesc1 = (TextView) v.findViewById(R.id.user_desc1);
                mDesc2 = (TextView) v.findViewById(R.id.user_desc2);
                /*mprofileImage = (ImageView) v.findViewById(R.id.profile_image);*/

                mCategory.setText(arraylist.get(position).getName());
                /*mBorn.setText(arraylist.get(position).getBorn());*/
                mId.setText(arraylist.get(position).getId());
                mDesc1.setText(arraylist.get(position).getDesc1());
                mDesc2.setText(arraylist.get(position).getDesc2());

                return v;

            }
        }

    }
}