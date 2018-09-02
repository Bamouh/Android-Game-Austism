package com.example.bamouhmohamed.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bamouhmohamed.androidproject.beans.Game;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by younes on 01/05/2018.
 */

public class localDBConnections {

    Context mContext;
    SQLiteDatabase db;
    Cursor authentificationDataCursor;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Game";
    // Table name
    private static final String TABLE_GAME_i = "Game_Infos";
    private String a="id";
    private String b="id_application";
    private String c="id_apprenant";
    private String d="id_accompagnant";
    private String e="id_exercice";
    private String f="id_niveau";
    private String g="date_actuelle";
    private String h="heure_debut";
    private String i="heure_fin";
    private String j="Nombre_operation_reuss";
    private String k="Nombre_operation_echou";
    private String l="minimum_temps_operation_sec";
    private String m="moyen_temps_operation_sec";
    private String n="longitude";
    private String o="latitude";
    private String p="device";
    private String q="flag";
    public localDBConnections(Context mContext,int i){

        this.mContext = mContext;
        if(i==0){
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        db.execSQL("create table if not exists BasicInfoTable (" +
                "mac_address Text Not NULL," +
                "id_user Integer Not NULL," +
                "anomalie_state INTEGER NOT NULL );");
        db.execSQL("create table if not exists Organisations (" +
                "id_Organisation INTEGER Not NULL," +
                "name_organisation TEXT Not NULL);");
        db.execSQL("create table if not exists Kids (" +
                "id_kid INTEGER Not NULL," +
                "id_organisation INTEGER," +
                "prenom TEXT Not NULL," +
                "nom TEXT Not NULL," +
                "genre TEXT Not NULL);");

                createGameTable();
        db.close();
        }

//create table if not exists Organisations (id_Organisation INTEGER NOT NULL,name_organisation TEXT NOT NULL);
//create table if not exists Kids (id_kid INTEGER NOT NULL,name_kid TEXT NOT NULL);
    }
    public void createGameTable (){
        String a="id";
        String b="id_application";
        String c="id_apprenant";
        String d="id_accompagnant";
        String e="id_exercice";
        String f="id_niveau";
        String g="date_actuelle";
        String h="heure_debut";
        String i="heure_fin";
        String j="Nombre_operation_reuss";
        String k="Nombre_operation_echou";
        String l="minimum_temps_operation_sec";
        String m="moyen_temps_operation_sec";
        String n="longitude";
        String o="latitude";
        String p="device";
        String q="flag";
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GAME_i + "("
                + a + " INTEGER,"
                + b + " VARCHAR(100),"
                + c + " VARCHAR(100),"
                + d + " VARCHAR(100),"
                + e + " VARCHAR(100),"
                + f + " VARCHAR(100),"
                + g + " VARCHAR(100),"
                + h + " VARCHAR(100),"
                + i + " VARCHAR(100),"
                + j + " INTEGER,"
                + k + " INTEGER,"
                + l + " INTEGER,"
                + m + " INTEGER,"
                + n + " DOUBLE,"
                + o + " DOUBLE,"
                + p + " VARCHAR(100),"
                + q + " VARCHAR(100)"
                +");";

        db.execSQL(CREATE_TABLE);
    }
    public void setBasicInfo(String macAddress,int id_user,int anomalie_state){
        db = mContext.openOrCreateDatabase("Game",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO basicinfoTable VALUES('"+macAddress+"',"+id_user+","+anomalie_state+");");
        db.close();
    }
//    public void setOrganisationsInfo (int id_Organisation,String name_organisation){
//        db = SQLiteDatabase.openOrCreateDatabase("Game", mContext.MODE_PRIVATE,null);
//        db.execSQL("INSERT INTO organisations VALUES('"+id_Organisation+"',"+name_organisation+");");
//        db.close();
//    }
//    public void setKidsInfo (int id_kid,String name_kid){
//        db = SQLiteDatabase.openOrCreateDatabase("Game", mContext.MODE_PRIVATE,null);
//        db.execSQL("INSERT INTO Kids VALUES('"+id_kid+"',"+name_kid+");");
//        db.close();
//    }

    public static void insertSomegame(Context context){

        String id_application;
        String id_apprenant;
        String id_accompagnant;
        String id_exercice;
        String id_niveau;
        String date_actuelle;
        String heure_debut;
        String heure_fin;
        int Nombre_operation_reuss;
        int Nombre_operation_echou;
        int minimum_temps_operation_sec;
        int moyen_temps_operation_sec;
        double longitude;
        double latitude;
        String device;
        String flag;
        SQLiteDatabase dba = context.openOrCreateDatabase("Game", MODE_PRIVATE, null);
            id_application="testy";
            id_apprenant="test";
            id_accompagnant="testast";
            id_exercice="exercice";
            id_niveau="walou";
            date_actuelle=java.sql.Date.valueOf( String.format("%d-%02d-%02d", 2011, 01, 01) ).toString();
            heure_debut = java.sql.Time.valueOf("00:00:00").toString();
            heure_fin =  java.sql.Time.valueOf("00:00:00").toString();
            Nombre_operation_reuss=0;
            Nombre_operation_echou=0;
            minimum_temps_operation_sec=0;
            moyen_temps_operation_sec=0;
            longitude=0;
            latitude=0;
            device="hello";
            flag="false";
            dba.execSQL("INSERT INTO "+TABLE_GAME_i+" VALUES(1,'apptest','appretest','acctest','exo','niv','date_acc','heure_deb','heure_fin',111,222,333,444,555,666,'device','flag')");
//            dba.execSQL("INSERT INTO "+TABLE_GAME_i+" VALUES(1,'"+
//                    id_application+"'," + "'"+
//                    id_apprenant+"'," + "'"+
//                    id_accompagnant+"'," + "'"+
//                    id_exercice+"'," + "'"+
//                    id_niveau+"'," + "'"+
//                    date_actuelle+"'," + "'"+
//                    heure_debut+"'," + "'"+
//                    heure_fin+"'," +
//                    Nombre_operation_reuss+"," +
//                    Nombre_operation_echou+"," +
//                    minimum_temps_operation_sec+"," +
//                    moyen_temps_operation_sec+"," +
//                    longitude+"," +
//                    latitude+"," + "'"+
//                    device+"'," + "'"+
//                    flag+"')");
            dba.close();

    }

    public void addGame(Game Game, Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase("Game", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(b, Game.getId_application());
        values.put(c, Game.getId_apprenant());
        values.put(d, Game.getId_accompagnant());
        values.put(e, Game.getId_exercice());
        values.put(f, Game.getId_niveau());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdfm = new SimpleDateFormat("HH:mm:ss");
        String da = df.format(Game.getDate_actuelle());
        String hd = sdfm.format(Game.getHeure_debut());
        String hf = sdfm.format(Game.getHeure_fin());
        values.put(g, da);
        values.put(h, hd);
        values.put(i, hf);
        values.put(j, Game.getNombre_operation_reuss());
        values.put(k, Game.getNombre_operation_echou());
        values.put(l, Game.getMinimum_temps_operation_sec());
        values.put(m, Game.getMoyen_temps_operation_sec());
        values.put(n, Game.getLongitude());
        values.put(o, Game.getLatitude());
        values.put(p, Game.getDevice());
        values.put(q, Game.isFlag());

        // Inserting Row
        db.insert(TABLE_GAME_i, null, values);
        db.close(); // Closing database connection
    }

    public String getEverything(Context context){
        SQLiteDatabase db = context.openOrCreateDatabase("Game", MODE_PRIVATE, null);
        String s="";
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_GAME_i,null);
        c.moveToFirst();
        do {
            s+=c.getString(c.getColumnIndex("id_application"))+" \n";
            s+=c.getString(c.getColumnIndex("id_apprenant"))+" \n";
            s+=c.getString(c.getColumnIndex("id_accompagnant"))+" \n";
            s+=c.getString(c.getColumnIndex("id_exercice"))+" \n";
            s+=c.getString(c.getColumnIndex("id_niveau"))+" \n";
            s+=c.getString(c.getColumnIndex("date_actuelle"))+" \n";
            s+=c.getString(c.getColumnIndex("heure_debut"))+" \n";
            s+=c.getString(c.getColumnIndex("heure_fin"))+" \n";
            s+=c.getString(c.getColumnIndex("Nombre_operation_reuss"))+" \n";
            s+=c.getString(c.getColumnIndex("Nombre_operation_echou"))+" \n";
            s+=c.getString(c.getColumnIndex("minimum_temps_operation_sec"))+" \n";
            s+=c.getString(c.getColumnIndex("moyen_temps_operation_sec"))+" \n";
            s+=c.getString(c.getColumnIndex("longitude"))+" \n";
            s+=c.getString(c.getColumnIndex("latitude"))+" \n";
            s+=c.getString(c.getColumnIndex("device"))+" \n";
        } while(c.moveToNext());

        db.close();
        return s;
    }

    public String getMacAddress(){
        String macAddress;
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        authentificationDataCursor = db.rawQuery("SELECT * FROM basicinfotable",null);
        if(authentificationDataCursor.moveToFirst()){
            macAddress = authentificationDataCursor.getString(authentificationDataCursor.getColumnIndex("mac_address"));
        }
        else{
            macAddress = "";
        }
        db.close();
        return macAddress;
    }                                                                // Done
    public int getUserId(){
        int userId;
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        authentificationDataCursor = db.rawQuery("SELECT * FROM basicinfotable",null);
        if(authentificationDataCursor.moveToFirst()){
            userId = authentificationDataCursor.getInt(authentificationDataCursor.getColumnIndex("id_user"));
        }
        else{
            userId = -1;
        }
        db.close();
        return userId;
    }                                                                       // Done
    public int getAnomalieState(){
        int anomalieState;
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        authentificationDataCursor = db.rawQuery("SELECT * FROM basicinfotable",null);
        if(authentificationDataCursor.moveToFirst()){
            anomalieState = authentificationDataCursor.getInt(authentificationDataCursor.getColumnIndex("anomalie_state"));
        }
        else{
            anomalieState = -1;
        }
        db.close();
        return anomalieState;
    }                                                                // Done
    public Map getOrganisationsInfo(){
        int id;
        String name;
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        Map<Integer,String> map = new HashMap<>();

        authentificationDataCursor = db.rawQuery("SELECT * FROM Organisations",null);
        if(authentificationDataCursor.moveToFirst()){
            do{
                id = authentificationDataCursor.getInt(authentificationDataCursor.getColumnIndex("id_Organisation"));
                name = authentificationDataCursor.getString(authentificationDataCursor.getColumnIndex("name_organisation"));
                map.put(id,name);
            }while(authentificationDataCursor.moveToNext());
        }
        db.close();
        return map;
    }                                                 // Done
    public Map getkidsInfo(int id_organisation){
        int id;
        String name;

        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        Map<Integer,String> map = new HashMap<>();
        if(id_organisation>0){
        authentificationDataCursor = db.rawQuery("SELECT * FROM kids WHERE id_organisation = "+id_organisation,null);
        }
        else{
            authentificationDataCursor = db.rawQuery("SELECT * FROM kids",null);
        }
        if(authentificationDataCursor.moveToFirst()){
            do{
                id = authentificationDataCursor.getInt(authentificationDataCursor.getColumnIndex("id_kid"));
                name = authentificationDataCursor.getString(authentificationDataCursor.getColumnIndex("prenom"))
                        +" "+authentificationDataCursor.getString(authentificationDataCursor.getColumnIndex("nom"));
                map.put(id,name);
            }while(authentificationDataCursor.moveToNext());

        }
        db.close();
        return map;
    }                                      // Done

    public void insertParentsKids(RecievedData repos,String type) throws JSONException {
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        int id_organisation;
        int id_kid;
        String name_organisation;
        String prenom_kid;
        String id_apprenant;
        String nom_kid;
        String genre;

        if(type.equals("Parent")){
            for(int i=0;i<repos.getEnrollments().size();i++){
                id_kid = repos.getEnrollments().get(i).getChild_id();
                prenom_kid = repos.getEnrollments().get(i).getChild().getFirst_name();
                nom_kid = repos.getEnrollments().get(i).getChild().getLast_name();
                genre = repos.getEnrollments().get(i).getChild().getGender();
                db.execSQL("INSERT INTO kids(id_kid,prenom,nom,genre) VALUES("+id_kid+",'"+prenom_kid+"','"+nom_kid+"','"+genre+"')");
            }
        }
//        else if (type.equals("Tuteur")){
//            for(int i=0;i<repos.getOrganizations().size();i++){
//                id_organisation = repos.getOrganizations().get(i).getId();
//                name_organisation = repos.getOrganisations().get(i).getName();
//                db.execSQL("INSERT INTO organisations VALUES("+id_organisation+",'"+name_organisation+"')");
//                for(int j=0;j<repos.getOrganisations().get(i).getChildren().size();j++){
//                    id_kid = repos.getOrganisations().get(i).getChildren().get(j).getId();
//                    prenom_kid = repos.getOrganisations().get(i).getChildren().get(j).getFirst_name();
//                    nom_kid = repos.getOrganisations().get(i).getChildren().get(j).getLast_name();
//                    genre = repos.getOrganisations().get(i).getChildren().get(j).getGenre();
//                    db.execSQL("INSERT INTO kids VALUES("+id_kid+","+id_organisation+",'"+prenom_kid+"','"+nom_kid+"','"+genre+"')");
//                }
//            }
//        }
        db.close();
    }                                    // Done
    public void insertOrgKid(OrgKid kid){
        db = mContext.openOrCreateDatabase("Game",MODE_PRIVATE,null);
        System.out.println("//"+kid.getChild().getId()+"//"+ kid.getOrganization_id() +"//"+kid.getChild().getFirst_name());
        db.execSQL("INSERT INTO kids VALUES("+kid.getChild().getId()+","+kid.getOrganization_id()+",'"+kid.getChild().getFirst_name()+"','"+kid.getChild().getLast_name()+"','"+kid.getChild().getGender()+"')");
        db.close();
    }
    public void insertOrganisations(RecievedData repos){
        db = mContext.openOrCreateDatabase("Game",MODE_PRIVATE,null);
        for(int i=0;i<repos.getOrganizations().size();i++){
                int id_organisation;
                String name_organisation;
                id_organisation = repos.getOrganizations().get(i).getId();
                name_organisation = repos.getOrganizations().get(i).getName();
            String changedUserString = name_organisation.replace("'","''");
                db.execSQL("INSERT INTO organisations VALUES("+id_organisation+",'"+changedUserString+"')");
        }
        db.close();

    }
    // Done
    public void insertkids(Map<Integer ,String> map){
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        for(Map.Entry m:map.entrySet()){
            db.execSQL("INSERT INTO kids VALUES("+m.getKey()+",'"+m.getValue()+"')");
        }
        db.close();
    }                                             // Done
    int rowCount (String tableName){
        int rowsNumber;
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        authentificationDataCursor = db.rawQuery("SELECT COUNT(*) FROM "+tableName,null);
        authentificationDataCursor.moveToFirst();
        rowsNumber = authentificationDataCursor.getInt(0);
        db.close();
        return rowsNumber;
    }                                                              // Done
    public void clearTables(){
        db = mContext.openOrCreateDatabase("Game", MODE_PRIVATE,null);
        db.execSQL("DELETE FROM organisations");
        db.execSQL("DELETE FROM kids");
        db.close();
    }
}
