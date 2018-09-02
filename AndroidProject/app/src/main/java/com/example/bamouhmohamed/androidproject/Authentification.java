package com.example.bamouhmohamed.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authentification extends AppCompatActivity{
    final String WIFI_ACCESS_ERROR = "s'il te plait . il faut nous donner l'access au donner Wifi";
    final int CONNECTION_ERROR = 0;
    final int FALSE_CONNECTION = 1;
    final int SUCCESS_CONNECTION = 2;
    final String ErrorMsg = "Authentification Error";
    localDBConnections a;
    AlertsAndOther alert;
    TextView login;
    EditText username;
    EditText userpass;
    String usernameValue;
    String userpassValue;
    LoadToast lt;
    int k=0;
    verifications ver = new verifications();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Verify if already connected
        if(!SaveSharedPreference.getUserName(Authentification.this).equals("")){
            goToSnextActivity(Authentification.this,SaveSharedPreference.getType(Authentification.this));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);


        alert = new AlertsAndOther(Authentification.this);
        permissionsClass permissions = new permissionsClass(Authentification.this);
        if(!permissions.getMacAddressOk()){
            alert.showOkalert(WIFI_ACCESS_ERROR);
        }




        //Verification si elle est deja connected sur se device il va automatiquement a la list des des eleve ou fils
        // si non l'activité continue en attendant On click event (login_click())

    }

    public static void goToSnextActivity(Context mContext, String Parent_ou_Tuteur) {
        Intent myIntent;
        if(Parent_ou_Tuteur.equals("Parent")){
            myIntent = new Intent(mContext, StudentList.class);
            myIntent.putExtra("type_of_user","Parent");
        }
        else {
            myIntent = new Intent(mContext, OrganisationsActivity.class);
        }

        mContext.startActivity(myIntent);
    }

    public void login_click(View view) throws JSONException, InterruptedException {
        login = (TextView) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        userpass = (EditText) findViewById(R.id.userpass);
        usernameValue = username.getText().toString();
        userpassValue = userpass.getText().toString();
        stateReturned = -1;
        lt = new LoadToast(Authentification.this);
        lt.setText("Loading");
        lt.show();
        lt.setTranslationY(500);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.serverPaths))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        RetrofitConnections client = retrofit.create(RetrofitConnections.class);
        Call<PathsClass> call = client.getSeverPaths();
        call.enqueue(new Callback<PathsClass>() {
            @Override
            public void onResponse(Call<PathsClass> call, Response<PathsClass> response) {
                PathsClass q = new PathsClass();
                SaveSharedPreference.createSharedPref(Authentification.this,"Main_Server",response.body().getMain_Server());
                SaveSharedPreference.createSharedPref(Authentification.this,"Secondary_Server",response.body().getSecondary_Server());
                SaveSharedPreference.createSharedPref(Authentification.this,"GET_DATA_PATH",response.body().getGET_DATA_PATH());
                SaveSharedPreference.createSharedPref(Authentification.this,"GET_ORGANISATION_KIDS_PATH",response.body().getGET_ORGANISATION_KIDS_PATH());
                SaveSharedPreference.createSharedPref(Authentification.this,"GET_USER_ORGANISATION_PATH",response.body().getGET_USER_ORGANISATION_PATH());
                SaveSharedPreference.createSharedPref(Authentification.this,"GET_USER_ENROLLEMENENTS",response.body().getGET_USER_ENROLLEMENENTS());
                SaveSharedPreference.createSharedPref(Authentification.this,"GET_GAME_PATH",response.body().getGET_USER_ENROLLEMENENTS());
                if(ver.isvalideEmail(usernameValue)){

                    getTokens();

                }
                else{
                    Toast.makeText(Authentification.this, "Address Email Non valid", Toast.LENGTH_SHORT).show();
                    lt.error();

                }
            }



            @Override
            public void onFailure(Call<PathsClass> call, Throwable t) {
                Toast.makeText(Authentification.this, "error", Toast.LENGTH_SHORT).show();
                lt.error();
                stateReturned = CONNECTION_ERROR;
            }
        });
//        if(ConnectionCheck.getInstance(this).isOnline()){


        //}

//        }
//        else{
//            TextView connError = (TextView) findViewById(R.id.Connection_error);
//            connError.setVisibility(View.VISIBLE);
//        }
    }

    public int getTokens(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SaveSharedPreference.getSharedPref(Authentification.this,"Main_Server"))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        LoginSentData loginData = new LoginSentData(usernameValue,userpassValue,getString(R.string.app_id));
        RetrofitConnections client = retrofit.create(RetrofitConnections.class);
        Call<RecievedData> call = client.getData(loginData);
        call.enqueue(new Callback<RecievedData>() {
            @Override
            public void onResponse(Call<RecievedData> call, Response<RecievedData> response) {
                if(response.isSuccessful()){

                    //String repos = response.body().toString();
                    String type;
                    SaveSharedPreference.setAccessToken(Authentification.this,response.body().getAccess_token());
                    if(response.body().getEnrollments() == null){
                        type = "Tuteur";
                        Toast.makeText(Authentification.this, "this is a teacher", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        type = "Parent";
                        Toast.makeText(Authentification.this, "this is a Parent", Toast.LENGTH_SHORT).show();
                    }

                    RecievedData repos = response.body();
                    a = new localDBConnections(Authentification.this,0);
                    SaveSharedPreference.setUserName(Authentification.this,usernameValue);
                    SaveSharedPreference.setType(Authentification.this,type);

                    if(type.equals("Tuteur"))
                        SaveSharedPreference.setId_accomp(Authentification.this,Integer.toString(repos.getOrganizations().get(0).getPivot().getUser_id()));
                    else
                        SaveSharedPreference.setId_accomp(Authentification.this,Integer.toString(repos.getEnrollments().get(0).getUser_id()));
                    if(type.equals("Parent")){
                        try {
                            a.insertParentsKids(repos,type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        goToSnextActivity(Authentification.this,SaveSharedPreference.getType(Authentification.this));
                    }
                    else{
                        localDBConnections b = new localDBConnections(Authentification.this,0);
                        a.insertOrganisations(repos);
                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl(SaveSharedPreference.getSharedPref(Authentification.this,"Main_Server"))
                                .addConverterFactory(GsonConverterFactory.create());

                        Retrofit retrofit = builder.build();
                        //LoginSentData loginData = new LoginSentData(usernameValue,userpassValue,"2018_8_6_3_55371");
                        RetrofitConnections client = retrofit.create(RetrofitConnections.class);
                        for(int i=0 ; i<repos.getOrganizations().size();i++){
                            OrgKidsRequest requestData = new OrgKidsRequest(repos.getOrganizations().get(i).getPivot().getUser_id(), getString(R.string.app_id),repos.getOrganizations().get(i).getId());
                            Call<Assignments> call2 = client.getOrgKids(requestData,"Bearer "+repos.getAccess_token());
                            call2.enqueue(new Callback<Assignments>() {
                                @Override
                                public void onResponse(Call<Assignments> call, Response<Assignments> response) {
                                    if(response.isSuccessful()){
                                        Assignments respons = response.body();
                                        for (int j=0;j<respons.getAssignments().size();j++){
                                            a.insertOrgKid(respons.getAssignments().get(j));
                                            Toast.makeText(Authentification.this, "Inserted " +j, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(Authentification.this, "No data was inserted ", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onFailure(Call<Assignments> call, Throwable t) {
                                    Toast.makeText(Authentification.this, "No kids Available ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        goToSnextActivity(Authentification.this,SaveSharedPreference.getType(Authentification.this));
                    }
                    lt.success();



                    stateReturned = SUCCESS_CONNECTION;

//                        if(repos.contains("organizations")){

//                        }
//                        else {
//                            Toast.makeText(Authentification.this, "okkkk", Toast.LENGTH_SHORT).show();
//                        }
                    //getServerData(repos.getAccess_token());

                    stateReturned = SUCCESS_CONNECTION;

                }
                else {
                    Toast.makeText(Authentification.this, "Les Données Sont Incorrect", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Authentification.this, response.toString(), Toast.LENGTH_SHORT).show();
                    stateReturned = FALSE_CONNECTION;
                }
            }



            @Override
            public void onFailure(Call<RecievedData> call, Throwable t) {


                Toast.makeText(Authentification.this, "Connection Error 1", Toast.LENGTH_SHORT).show();


                lt.error();

                stateReturned = CONNECTION_ERROR;
            }
        });
        return stateReturned;
    }
    int stateReturned;
    public int getServerData(String accessToken) throws InterruptedException {


        return stateReturned;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
