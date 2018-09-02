package com.example.bamouhmohamed.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OrganisationsActivity extends AppCompatActivity {
    private String[] lv_arr;
    private Integer[] lv_arr_keys;
    int idOrganisation;
    private NetworkStateReceiver networkStateReceiver;
    @Override
    protected void  onResume(){
        super.onResume();
        String x = SaveSharedPreference.getUserName(OrganisationsActivity.this);
        if(x.length() == 0){
            logoffer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        localDBConnections localDB = new localDBConnections(OrganisationsActivity.this,1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisations);

        final ListView Organisation = (ListView) findViewById(R.id.kids_list);
        ArrayList<String> list = new ArrayList<String>(localDB.getOrganisationsInfo().values());
        ArrayList<Integer> listkeys = new ArrayList<Integer>(localDB.getOrganisationsInfo().keySet());

        lv_arr =list.toArray(new String[list.size()]);
        lv_arr_keys = listkeys.toArray(new Integer[listkeys.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lv_arr
        );


        Organisation.setAdapter(adapter);
        Organisation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(OrganisationsActivity.this, StudentList.class);
                idOrganisation = lv_arr_keys[position];
                String val = String.valueOf(idOrganisation);

                intent.putExtra("Organisation_name", Organisation.getItemAtPosition(position).toString());
                intent.putExtra("type_of_user","Organisation");
                intent.putExtra("id_organisation",val);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void logoffer() {
        SaveSharedPreference.setUserName(OrganisationsActivity.this,"");
        OrganisationsActivity.this.finish();
    }
    public void organisationlogoff(View view) {
        SaveSharedPreference.setUserName(OrganisationsActivity.this,"");
        OrganisationsActivity.this.finish();
    }


    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    public void refresh(View view){

//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("http://www.mocky.io/v2/")
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.build();
//
//        RetrofitConnections client = retrofit.create(RetrofitConnections.class);
//        Call<RecievedData> call = client.Login(SaveSharedPreference.getAccessToken(OrganisationsActivity.this));
//
//        call.enqueue(new Callback<RecievedData>() {
//            @Override
//            public void onResponse(Call<RecievedData> call, Response<RecievedData> response) {
//                if(response.isSuccessful()){
//                    RecievedData repos = response.body();
//                    localDBConnections a = new localDBConnections(OrganisationsActivity.this,0);
//                    try {
//                        a.insertOrganisationswithkids(repos,type);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    //listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
//                }
//                else {
//
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<RecievedData> call, Throwable t) {
//                Toast.makeText(OrganisationsActivity.this, "error :(", Toast.LENGTH_SHORT).show();
//            }
//        });
//        finish();
//        startActivity(getIntent());
    }


}
