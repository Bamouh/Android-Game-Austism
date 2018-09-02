package com.example.bamouhmohamed.androidproject.parent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.beans.Personne;

import java.util.List;

/**
 * Created by BamouhMohamed on 26/03/2018.
 */

public class ArrayPersonneAdapter extends ArrayAdapter<Personne> {
    private LayoutInflater inflater;
    private List<Personne> Personnes;

    public ArrayPersonneAdapter(Context context, int textViewResourceId, List<Personne> Personnes) {
        //mettre le layout qui contient la liste
        super(context, R.layout.activity_parent, Personnes);

        this.inflater = LayoutInflater.from(context);
        this. Personnes= Personnes;

    }

    @Override
// getView retournera la vue de l’item pour l’affichage.
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Réutilisation des vues
        if(convertView == null) {
            holder = new ViewHolder();
            //charger le fichier xml item
            convertView = inflater.inflate(R.layout.item_parent, null);
            // Associer les views du layout item au holder pour le retouner à la listView
            holder.label = (TextView)convertView.findViewById(R.id.label);
            holder.img=  (ImageView) convertView.findViewById(R.id.img);
            //sauvgarde la ref du holder en memoire pour la réutilisation par la suite
            convertView.setTag(holder);

        } else {
            //réutilisation du holder déja existant
            holder = (ViewHolder) convertView.getTag();
        }

        //stocker les données dans une vue via settag
        holder.label.setText(Personnes.get(position).getLabel());
        if(Personnes.get(position).getIcon()!=null){
            holder.img.setImageBitmap(Personnes.get(position).getIcon());
        }
        else{
            holder.img.setImageResource(R.drawable.notavailable);
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView label ;
        public ImageView img;
    }
}
