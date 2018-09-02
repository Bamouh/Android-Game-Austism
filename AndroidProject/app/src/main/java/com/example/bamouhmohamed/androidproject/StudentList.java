package com.example.bamouhmohamed.androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    String user_type;
    String OrganisationName;
    int idOrganisation;
    List<String> list;
    private NetworkStateReceiver networkStateReceiver;
    private String[] lv_arr = {};
    private Integer[] lv_arr_keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String x = SaveSharedPreference.getUserName(StudentList.this);
        if(x.equals("")){
            logoffer();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        startNetworkBroadcastReceiver(this);

        localDBConnections localDB = new localDBConnections(StudentList.this,1);
        TextView Organisation = (TextView) findViewById(R.id.listOfKids);
        //Lorsque on va travailer avec une base de donnée on vas utilisé CursorAdapter
        // Instead of ArrayAdapter (c'est le meme  principe on a just besoin de base de donnée
        Intent intent = getIntent();
        user_type = intent.getStringExtra("type_of_user");
        if(user_type.equals("Parent")){
            ImageView a = (ImageView) findViewById(R.id.undo);
            a.setVisibility(View.GONE);
        }

        if(user_type.equals("Parent")){
            Organisation.setText("Mes fils");
            list = new ArrayList<String>(localDB.getkidsInfo(0).values());
        }
        else {
            OrganisationName = intent.getStringExtra("Organisation_name");
            idOrganisation = Integer.parseInt(intent.getStringExtra("id_organisation"));
            list = new ArrayList<String>(localDB.getkidsInfo(idOrganisation).values());

            Organisation.setText(OrganisationName);
        }
        ArrayList<Integer> listkeys = new ArrayList<Integer>(localDB.getkidsInfo(idOrganisation).keySet());
        lv_arr_keys = listkeys.toArray(new Integer[listkeys.size()]);



        lv_arr = list.toArray(new String[list.size()]);
                final ListView kids_list = (ListView) findViewById(R.id.kids_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lv_arr
        );

        kids_list.setAdapter(adapter);

        kids_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(StudentList.this, com.example.bamouhmohamed.androidproject.menu.MainActivity.class);



                intent.putExtra("kid_id", String.valueOf(lv_arr_keys[position]));
                intent.putExtra("id_accomp",SaveSharedPreference.getId_accomp(StudentList.this));


                startActivity(intent);
            }
        });
    }

    public void logoffstudent(View view) {
        SaveSharedPreference.setUserName(StudentList.this,"");
        StudentList.this.finish();
    }
    private void logoffer() {
        SaveSharedPreference.setUserName(StudentList.this,"");
        localDBConnections a = new localDBConnections(StudentList.this,1);
        a.clearTables();
        StudentList.this.finish();
    }

    public void undo(View view) {
        StudentList.this.finish();
    }

    public void refresh(View view){

//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("http://www.mocky.io/v2/")
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.build();
//
//        RetrofitConnections client = retrofit.create(RetrofitConnections.class);
//        Call<RecievedData> call = client.Login(SaveSharedPreference.getAccessToken(StudentList.this));
//
//        call.enqueue(new Callback<RecievedData>() {
//            @Override
//            public void onResponse(Call<RecievedData> call, Response<RecievedData> response) {
//                if(response.isSuccessful()){
//                    RecievedData repos = response.body();
//                    localDBConnections a = new localDBConnections(StudentList.this,0);
//                    try {
//                        a.insertOrganisationswithkids(repos);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<RecievedData> call, Throwable t) {
//                Toast.makeText(StudentList.this, "error Connecting to the database", Toast.LENGTH_SHORT).show();
//            }
//        });
//        finish();
//        startActivity(getIntent());
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    @Override
    public void networkAvailable() {
        localDBConnections a = new localDBConnections(StudentList.this,1);
        //localDBConnections.insertSomegame(StudentList.this);
        if(a.rowCount("Game_Infos")>0){
            Intent i = new Intent(this, BackgroundGameService.class);
            startService(i);

        }
    }

    @Override
    public void networkUnavailable() {

    }
    public void startBackgroundGameService(){
        Intent intentToService = new Intent(this, BackgroundGameService.class);
        startService(intentToService);
    }



    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    /**
     * Register the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     Unregister the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }
}