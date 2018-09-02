package com.example.bamouhmohamed.androidproject.beans;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by BamouhMohamed on 26/03/2018.
 */

public class Personne {
    private int id;
    private int img; //inutile
    private Bitmap icon; //icone
    private String image; //Le chemin de l'image
    private File audio; //Audio
    private String label; //Titre

    public Personne(int img, String label) {
        this.img = img;
        this.label = label;
    }

    public Personne(String label) {
        this.label = label;
    }

    public Personne() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public File getAudio() {
        return audio;
    }

    public void setAudio(File audio) {
        this.audio = audio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
