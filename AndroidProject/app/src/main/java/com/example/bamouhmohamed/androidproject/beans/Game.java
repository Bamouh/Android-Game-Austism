package com.example.bamouhmohamed.androidproject.beans;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Lenovo on 4/15/2018.
 */

public class Game {

    private String id_application;
    private String id_apprenant;
    private String id_accompagnant;
    private String id_exercice;
    private String id_niveau;
    private Date date_actuelle;
    private Time heure_debut;
    private Time heure_fin;
    private int Nombre_operation_reuss;
    private int Nombre_operation_echou;
    private int minimum_temps_operation_sec;
    private int moyen_temps_operation_sec;
    private double longitude;
    private double latitude;
    private String device;
    private boolean flag;

    public Game(String id_application, String id_apprenant, String id_accompagnant, String id_exercice, String id_niveau, Date date_actuelle, Time heure_debut, Time heure_fin, int nombre_operation_reuss, int nombre_operation_echou, int minimum_temps_operation_sec, int moyen_temps_operation_sec, double longitude, double latitude, String device, boolean flag) {
        this.id_application = id_application;
        this.id_apprenant = id_apprenant;
        this.id_accompagnant = id_accompagnant;
        this.id_exercice = id_exercice;
        this.id_niveau = id_niveau;
        this.date_actuelle = date_actuelle;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        Nombre_operation_reuss = nombre_operation_reuss;
        Nombre_operation_echou = nombre_operation_echou;
        this.minimum_temps_operation_sec = minimum_temps_operation_sec;
        this.moyen_temps_operation_sec = moyen_temps_operation_sec;
        this.longitude = longitude;
        this.latitude = latitude;
        this.device = device;
        this.flag = flag;
    }

    public Game() {
        id_application="";
        id_apprenant="";
        id_accompagnant="";
        id_exercice="";
        id_niveau="";
        date_actuelle=java.sql.Date.valueOf( String.format("%d-%02d-%02d", 2011, 01, 01) );
        heure_debut = java.sql.Time.valueOf("00:00:00");
        heure_fin =  java.sql.Time.valueOf("00:00:00");
        Nombre_operation_reuss=0;
        Nombre_operation_echou=0;
        minimum_temps_operation_sec=0;
        moyen_temps_operation_sec=0;
        longitude=0;
        latitude=0;
        device="";
        flag=false;
    }

    public String getId_application() {
        return id_application;
    }

    public String getId_apprenant() {
        return id_apprenant;
    }

    public String getId_accompagnant() {
        return id_accompagnant;
    }

    public String getId_exercice() {
        return id_exercice;
    }

    public String getId_niveau() {
        return id_niveau;
    }

    public Date getDate_actuelle() {
        return date_actuelle;
    }

    public Time getHeure_debut() {
        return heure_debut;
    }

    public Time getHeure_fin() {
        return heure_fin;
    }

    public int getNombre_operation_reuss() {
        return Nombre_operation_reuss;
    }

    public int getNombre_operation_echou() {
        return Nombre_operation_echou;
    }

    public int getMinimum_temps_operation_sec() {
        return minimum_temps_operation_sec;
    }

    public int getMoyen_temps_operation_sec() {
        return moyen_temps_operation_sec;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDevice() {
        return device;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setId_application(String id_application) {
        this.id_application = id_application;
    }

    public void setId_apprenant(String id_apprenant) {
        this.id_apprenant = id_apprenant;
    }

    public void setId_accompagnant(String id_accompagnant) {
        this.id_accompagnant = id_accompagnant;
    }

    public void setId_exercice(String id_exercice) {
        this.id_exercice = id_exercice;
    }

    public void setId_niveau(String id_niveau) {
        this.id_niveau = id_niveau;
    }

    public void setDate_actuelle(Date date_actuelle) {
        this.date_actuelle = date_actuelle;
    }

    public void setHeure_debut(Time heure_debut) {
        this.heure_debut = heure_debut;
    }

    public void setHeure_fin(Time heure_fin) {
        this.heure_fin = heure_fin;
    }

    public void setNombre_operation_reuss(int nombre_operation_reuss) {
        Nombre_operation_reuss = nombre_operation_reuss;
    }

    public void setNombre_operation_echou(int nombre_operation_echou) {
        Nombre_operation_echou = nombre_operation_echou;
    }

    public void setMinimum_temps_operation_sec(int minimum_temps_operation_sec) {
        this.minimum_temps_operation_sec = minimum_temps_operation_sec;
    }

    public void setMoyen_temps_operation_sec(int moyen_temps_operation_sec) {
        this.moyen_temps_operation_sec = moyen_temps_operation_sec;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
