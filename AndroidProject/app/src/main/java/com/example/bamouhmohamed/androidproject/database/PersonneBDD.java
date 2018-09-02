package com.example.bamouhmohamed.androidproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.bamouhmohamed.androidproject.beans.Personne;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by BamouhMohamed on 18/02/2018.
 */

public class PersonneBDD {
    private static final int VERSION_BDD = 13;
    private static final String NOM_BDD = "database.db";
    private static final String TABLE_Personne = "table_personne";
    private static final String COL_ID = "id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_LABEL = "label";
    private static final int NUM_COL_LABEL = 1;
    private static final String COL_IMG = "img";
    private static final int NUM_COL_IMG = 2;
    private static final String COL_ICON = "icon";
    private static final int NUM_COL_ICON = 3;
    private static final String COL_AUDIO = "audio";
    private static final int NUM_COL_AUDIO = 4;
    private static final String COL_IMAGE = "image";
    private static final int NUM_COL_IMAGE = 5;

    SQLiteDatabase bdd;
    MaBaseSQLite maBaseSQLite;

    public PersonneBDD(Context context) {
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }
    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }
    public void close() {
        bdd.close();
    }
    public boolean isOpened() {
        if (bdd.isOpen()){
            return true;
        }
        else return false;
    }
    public long insertPersonne(Personne Personne){
        if(getPersonne(Personne.getLabel())==null){
            ContentValues values = new ContentValues();
            values.put(COL_LABEL, Personne.getLabel());
            values.put(COL_IMG, Personne.getImg());
            return bdd.insert(TABLE_Personne, null, values);
        }
        else return 0;
    }

    public void updatebddPersonneIcon(Personne personne){
        ContentValues values = new ContentValues();
        values.put(COL_LABEL, personne.getLabel());
        values.put(COL_IMG, personne.getImg());
        values.put(COL_ICON,getBitmapAsByteArray(personne.getIcon()));
        String whereClause = COL_LABEL + "=?";
        String[] selectionArgs = {personne.getLabel()};
        bdd.update(TABLE_Personne, values, whereClause , selectionArgs);
    }

    public void updatebddPersonneImagePath(Personne personne){
        ContentValues values = new ContentValues();
        values.put(COL_LABEL, personne.getLabel());
        values.put(COL_IMG, personne.getImg());
        values.put(COL_IMAGE,personne.getImage());
        String whereClause = COL_LABEL + "=?";
        String[] selectionArgs = {personne.getLabel()};
        bdd.update(TABLE_Personne, values, whereClause , selectionArgs);
    }

    public void updatebddPersonneAudio(Personne personne, byte[] byteimage){
        ContentValues values = new ContentValues();
        values.put(COL_LABEL, personne.getLabel());
        values.put(COL_IMG, personne.getImg());
        values.put(COL_AUDIO,byteimage);
        String whereClause = COL_LABEL + "=?";
        String[] selectionArgs = {personne.getLabel()};
        bdd.update(TABLE_Personne, values, whereClause , selectionArgs);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public List<Personne> getAllPersonnes(){
        List<Personne> list=new ArrayList<Personne>();
        Cursor c = bdd.query(TABLE_Personne, new String[] {COL_ID, COL_LABEL, COL_IMG, COL_ICON, COL_AUDIO, COL_IMAGE}, null , null,null, null, null);
        try{
            while(c.moveToNext()){
                System.out.println("OOK");
                list.add(cursorToPersonnewithoutclosing(c));
            }
        }
        finally{
            c.close();
        }

        return list;
    }

    public Personne getPersonne(String label){
        String[] selectionArgs = {label};
        String whereClause = COL_LABEL + "=?";
        Cursor c = bdd.query(TABLE_Personne, new String[] {COL_ID, COL_LABEL, COL_IMG, COL_ICON, COL_AUDIO, COL_IMAGE}, whereClause , selectionArgs,null, null, null);
        return cursorToPersonne(c);
    }

    public Personne getRandomPersonne(){
        Cursor cc = bdd.query(TABLE_Personne, new String[] {COL_ID, COL_LABEL, COL_IMG, COL_ICON, COL_AUDIO, COL_IMAGE}, null , null,null, null, COL_ID);
        cc.moveToLast();
        int nb = cc.getInt(NUM_COL_ID);
        cc.close();
        System.out.println("------------------- "+ nb);
        Random r = new Random();
        int randint = r.nextInt(nb)+1;
        System.out.println("-----------------------"+randint);
        String rand = Integer.toString(randint);
        String[] selectionArgs = {rand};
        String whereClause = COL_ID + "=?";
        Cursor c = bdd.query(TABLE_Personne, new String[] {COL_ID, COL_LABEL, COL_IMG, COL_ICON, COL_AUDIO, COL_IMAGE}, whereClause , selectionArgs,null, null, null);
        return cursorToPersonne(c);
    }

    private Personne cursorToPersonne(Cursor c){
        if (c.getCount() == 0) return null;
        c.moveToFirst();
        Personne Personne = new Personne();
        Personne.setId(c.getInt(NUM_COL_ID));
        Personne.setLabel(c.getString(NUM_COL_LABEL));
        Personne.setImg(c.getInt(NUM_COL_IMG));
        try {
            byte[] imgByte = c.getBlob(NUM_COL_ICON);
            Bitmap icon = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            Personne.setIcon(icon);
        }catch(NullPointerException e){}
        Personne.setImage(c.getString(NUM_COL_IMAGE));
        try {
            byte[] audioByte = c.getBlob(NUM_COL_AUDIO);
            File soundDataFile = File.createTempFile( "sound", "sound" );
            FileOutputStream fos = new FileOutputStream( soundDataFile );
            fos.write( audioByte );
            fos.close();
            Personne.setAudio(soundDataFile);
        }catch(NullPointerException e){} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.close();
        return Personne;
    }

    //Retourne une personne sans fermer la curseur, utile pour des méthodes comme getAllPersonnes()
    private Personne cursorToPersonnewithoutclosing(Cursor c){
        Personne Personne = new Personne();
        Personne.setId(c.getInt(NUM_COL_ID));
        Personne.setLabel(c.getString(NUM_COL_LABEL));
        Personne.setImg(c.getInt(NUM_COL_IMG));
        try {
            byte[] imgByte = c.getBlob(NUM_COL_ICON);
            Bitmap icon = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            Personne.setIcon(icon);
        }catch(NullPointerException e){}
        Personne.setImage(c.getString(NUM_COL_IMAGE));
        try {
            byte[] audioByte = c.getBlob(NUM_COL_AUDIO);
            File soundDataFile = File.createTempFile( "sound", "sound" );
            FileOutputStream fos = new FileOutputStream( soundDataFile );
            fos.write( audioByte );
            fos.close();
            Personne.setAudio(soundDataFile);
        }catch(NullPointerException e){} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Personne;
    }

    public void removePersonne(String Label){
        if(getPersonne(Label)!=null){
            String whereClause = COL_LABEL + "=?";
            String[] whereArgs = {Label};
            bdd.delete(TABLE_Personne, whereClause, whereArgs);
        }
    }
}
