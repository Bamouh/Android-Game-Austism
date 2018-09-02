package com.example.bamouhmohamed.androidproject.enfant;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamouhmohamed.androidproject.beans.Game;
import com.example.bamouhmohamed.androidproject.beans.Personne;
import com.example.bamouhmohamed.androidproject.database.PersonneBDD;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.localDBConnections;
import com.example.bamouhmohamed.androidproject.menu.MainActivity;
import com.example.bamouhmohamed.androidproject.parent.LocationClass;
import com.google.common.util.concurrent.ServiceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import static com.example.bamouhmohamed.androidproject.menu.MainActivity.HeureDebut;
import static com.example.bamouhmohamed.androidproject.menu.MainActivity.id_accompagnant;
import static com.example.bamouhmohamed.androidproject.menu.MainActivity.id_apprenant;

public class EnfantActivity extends AppCompatActivity {

    public static int LEVEL = 1;
    public static int BALL = R.drawable.ball;
    TextView text;
    ImageView ball;
    List<ImageView> listView;
    ImageButton retourner;
    ImageButton voiceButton;
    static PersonneBDD bdd;
    Personne winner;
    ImageView thumb;
    long temps_operation;
    static List<Integer> tab_temps_operation = new ArrayList<Integer>();
    List<Integer> loseMessages;
    List<Integer> winSounds;
    List<Integer> winMessages;
    List<Integer> loseSounds;
    List<Integer> instructionSounds;

    MediaPlayer mpInstruction;
    MediaPlayer mpEnregistrementVocal;
    MediaPlayer mpWin;
    MediaPlayer mpLose;

    //public static Game_Database_Handler GameBDD;
    public static Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfant);
        bdd = new PersonneBDD(this.getApplicationContext());
        bdd.open();
        text = this.findViewById(R.id.message);
        ball = this.findViewById(R.id.ball);
        ball.setImageResource(BALL);
        thumb = this.findViewById(R.id.thumb);
        retourner = this.findViewById(R.id.retournerEnfant);
        voiceButton = this.findViewById(R.id.voiceButton);

        temps_operation = System.currentTimeMillis();

        listView = new ArrayList<ImageView>();
        listView.add((ImageView) findViewById(R.id.famille1));
        listView.add((ImageView) findViewById(R.id.famille2));
        listView.add((ImageView) findViewById(R.id.famille3));
        listView.add((ImageView) findViewById(R.id.famille4));
        listView.add((ImageView) findViewById(R.id.famille5));
        listView.add((ImageView) findViewById(R.id.famille6));

        Collections.shuffle(listView); //Rend l'ordre des Imageview aléatoire pour que les images aient des emplacements différents

        try {
            winner = selectRandomPerson(); //Selection du membre de famille a trouver
        } catch (Exception e) {
            winner = null;
        }

        if (winner == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Aucun membre de famille enregistré. Veillez les ajouter dans les Options", Toast.LENGTH_SHORT).show();
                }
            }, 1000);
            finish();
        } else {
            text.setText(getResources().getString(R.string.send_ball_to) + "\n" + winner.getLabel());
            this.configureFamille(winner, listView.get(0));

            initializeSounds();

            levelCreation();
        /*
        ball.setOnLongClickListener(new View.OnLongClickListener() {
            // Defines the one method for the interface, which is called when the View is long-clicked
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("famille", winner.getLabel());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });//Applique la fonction de drag'n drop a la balle après un long clic sur celle-ci
        */
            ball.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("famille", winner.getLabel());
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadowBuilder, v, 0);
                        v.setVisibility(View.INVISIBLE);
                        return true;
                    } else return false;
                }
            });//Applique la fonction de drag'n drop a la balle après avoir touché celle-ci
            this.findViewById(R.id.jeu).setOnDragListener(new View.OnDragListener() {
                public boolean onDrag(View v, DragEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case DragEvent.ACTION_DROP:
                            ImageView view = (ImageView) event.getLocalState();
                            view.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });//Configure l'arrière plan en tant que Listener du drag'n drop pour ramener la balle a son emplacement initial si l'enfant relache la balle sur l'arrière plan
            retourner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            voiceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mpInstruction = MediaPlayer.create(EnfantActivity.this, instructionSounds.get(0));
                    mpInstruction.start();
                }
            });

            //GameBDD = new Game_Database_Handler(this.getApplicationContext());
            initGame();
        }
    }

    @Override
    protected void onDestroy() {
        bdd.close();
        int temps = (int) (temps_operation / 1000);
        if(winner!=null ) {
            if( temps < 86400 ) {
                game.setHeure_fin(new Time(System.currentTimeMillis()));
                tab_temps_operation.add(temps);
                int min = tab_temps_operation.get(0);
                for (int i = 0; i < tab_temps_operation.size(); i++) {
                    if (tab_temps_operation.get(i) < min) {
                        min = tab_temps_operation.get(i);
                    }
                }
                game.setMinimum_temps_operation_sec(min);
                int somme = 0;
                for (int i = 0; i < tab_temps_operation.size(); i++) {
                    somme += tab_temps_operation.get(i);
                    System.out.println("aaaaaaaaaaaaaaaaaaaaa: " + tab_temps_operation.get(i));
                }
                game.setMoyen_temps_operation_sec(somme / tab_temps_operation.size());

                System.out.println("Date actuelle : " + game.getDate_actuelle());
                System.out.println("Appareil : " + game.getDevice());
                System.out.println("Heure de début : " + game.getHeure_debut());
                System.out.println("Heure de fin : " + game.getHeure_fin());
                System.out.println("Temps minimal d'une opération : " + game.getMinimum_temps_operation_sec());
                System.out.println("Latitude : " + game.getLatitude());
                System.out.println("Longitude : " + game.getLongitude());
                System.out.println("Nombre d'opérations réussies : " + game.getNombre_operation_reuss());
                System.out.println("Nombre d'opérations échouées : " + game.getNombre_operation_echou());
                System.out.println("Temps moyen d'une opération : " + game.getMoyen_temps_operation_sec());
                System.out.println("Id de l'accompagnant : " + game.getId_accompagnant());
                System.out.println("Id de l'apprenanant : " + game.getId_apprenant());
                System.out.println("Id de l'application : " + game.getId_application());
                System.out.println("Id de l'exercice : " + game.getId_exercice());
                System.out.println("Id du niveau : " + game.getId_niveau());

                localDBConnections localDB = new localDBConnections(this.getApplicationContext(), 0);
                localDB.setBasicInfo(localDB.getMacAddress(), localDB.getUserId(), localDB.getAnomalieState());
                localDB.addGame(game, this.getApplicationContext());

                String e = localDB.getEverything(this.getApplicationContext());
                System.out.println(e);
            }
        }

        super.onDestroy();
    }

    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }

    public void initGame() {
        game = new Game();
        LocationClass lc = new LocationClass(this.getApplicationContext(),this);
        //Ne pas oublier d'activer le service de localisation du télépohone et laisser ce dernier determiner la position de l'appareil
        //Si le service n'est pas activé, ou bien si l'appareil n'a pas eu suffisament de temps pour trouver la position de l'appareil, la latitude/longitude seront nulles (0.0)
        game.setLatitude(lc.lat);
        game.setLongitude(lc.longi);
        game.setHeure_debut(HeureDebut);
        game.setDevice(android.os.Build.MODEL);
        game.setDate_actuelle(new Date(Calendar.getInstance().getTimeInMillis()));
        game.setId_application(getResources().getString(R.string.id_application));
        game.setId_exercice(getResources().getString(R.string.id_exercice));
        game.setId_niveau(Integer.toString(LEVEL));
        game.setId_accompagnant(id_accompagnant);
        game.setId_apprenant(id_apprenant);
    }

    public void levelCreation(){//Attache les ImageView restantes a des membres de famille aléatoires
        for(int i=1;i<LEVEL;i++){
            Personne loser = selectRandomPerson();
            this.configureFamille(loser,listView.get(i));
        }
    }

    public void initializeSounds(){
        //Messages d'encouragement si l'enfant rate
        loseMessages = new ArrayList<>();
        loseMessages.add(R.string.lose1);
        loseMessages.add(R.string.lose2);
        loseMessages.add(R.string.lose3);
        loseMessages.add(R.string.lose4);
        //Sons d'échec
        loseSounds = new ArrayList<>();
        loseSounds.add(R.raw.fail_sound);
        loseSounds.add(R.raw.fail_sound2);
        loseSounds.add(R.raw.fail_sound3);
        loseSounds.add(R.raw.fail_sound4);
        loseSounds.add(R.raw.fail_sound5);
        //Sons de victoire
        winSounds = new ArrayList<>();
        winSounds.add(R.raw.win_sound);
        winSounds.add(R.raw.win_sound2);
        winSounds.add(R.raw.win_sound3);
        winSounds.add(R.raw.win_sound4);
        winSounds.add(R.raw.win_sound5);
        //Messages de victoire
        winMessages = new ArrayList<>();
        winMessages.add(R.string.win);
        winMessages.add(R.string.win2);
        winMessages.add(R.string.win3);
        winMessages.add(R.string.win4);
        winMessages.add(R.string.win5);
        //Message d'instruction
        instructionSounds = new ArrayList<>();
        instructionSounds.add(R.raw.instruction_sound);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mpInstruction= MediaPlayer.create(EnfantActivity.this, instructionSounds.get(0));
                mpInstruction.start();
                mpInstruction.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            }
        }, 2000);
    }

    public Personne selectRandomPerson(){
        Personne personne=null;
        boolean err=true;
        while(err){
            try{//Boucle pour éviter qu'une personne dont l'ID n'existe plus (donc null) soit choisie (au cas ou une personne est crée puis supprimée de la bdd)
                personne = bdd.getRandomPersonne();
                System.out.println(personne.getLabel());
                err=false;
            }catch(NullPointerException e) {
                err = true;
            }
        }
        return personne;
    }

    public void configureFamille(final Personne personne, ImageView famille){//méthode pour rattacher une personne a un ImageView et rendre ce dernier sensible au drag'n drop
        final Bitmap bitmap;
        try{
            File file = new File(personne.getImage());//Récupère l'image via son chemin pour la transférer a une nouvelle activité permettant d'aggrandir l'image ou cas ou l'icone n'est pas bien visible
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 2;//Reduit la qualité pour empecher l'excpetion java.lang.OutOfMemoryError
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
            famille.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent _intent;
                    _intent = new Intent(EnfantActivity.this, activity_bigger_image.class);
                    //Bitmap bitmap=personne.getImage(); // your bitmap
                    ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, _bs);
                    _intent.putExtra("byteArray", _bs.toByteArray());
                    startActivity(_intent);
                }
            });
        }catch(Exception e){
            Toast.makeText(this.getApplicationContext(),"NO IMAGE AVAILABLE",Toast.LENGTH_SHORT).show();
        }
        final File audio = personne.getAudio();//Récupère l'audio
        famille.setImageBitmap(personne.getIcon());
        // Sets a long click listener for the ImageView using an anonymous listener object that
        // implements the OnLongClickListener interface
        famille.setOnDragListener(new View.OnDragListener(){
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        try {
                            mpEnregistrementVocal= MediaPlayer.create(EnfantActivity.this, Uri.fromFile(audio));
                            mpEnregistrementVocal.start();//Lance l'audio quand la balle entre la zone d'une ImageView appartenant a une personne
                            mpEnregistrementVocal.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }
                            });
                        } catch (Exception e) {}
                        v.setBackgroundColor(Color.GREEN);
                        v.invalidate();
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        try {
                            if(mpEnregistrementVocal.isPlaying()){mpEnregistrementVocal.stop();}
                        } catch (Exception e) {}
                        v.setBackgroundColor(Color.RED);
                        v.invalidate();
                        break;
                    case DragEvent.ACTION_DROP:
                        try {
                            if(mpEnregistrementVocal.isPlaying()){mpEnregistrementVocal.stop();}
                        } catch (Exception e) {}
                        if((personne.getLabel().equals(winner.getLabel()))){
                            thumb.bringToFront();
                            thumb.setImageDrawable(getResources().getDrawable(R.drawable.thumb_up));
                            Collections.shuffle(winMessages);
                            Toast.makeText(getApplicationContext() , getResources().getString(winMessages.get(0)), Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.shuffle(winSounds);
                                    mpWin= MediaPlayer.create(EnfantActivity.this, winSounds.get(0));
                                    mpWin.start();
                                    mpWin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        public void onCompletion(MediaPlayer mp) {
                                            mp.release();
                                        }
                                    });
                                    recreate();
                                }
                            }, 2000);
                            game.setNombre_operation_reuss(++MainActivity.nbOpreussies);
                            temps_operation = System.currentTimeMillis() - temps_operation;//temps actuel - temps depuis le début = durée du jeu
                            break;
                        }
                        else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.shuffle(loseMessages);
                                    Toast.makeText(getApplicationContext() , getResources().getString(loseMessages.get(0)), Toast.LENGTH_SHORT).show();
                                }
                            }, 500);
                            Collections.shuffle(loseSounds);

                            mpLose= MediaPlayer.create(EnfantActivity.this, loseSounds.get(0));
                            mpLose.start();
                            mpLose.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }
                            });

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            game.setNombre_operation_echou(++MainActivity.nbOpechouees);
                            return false;
                        }
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundColor(Color.GRAY);
                        v.invalidate();
                        try {
                            if(mpEnregistrementVocal.isPlaying()){mpEnregistrementVocal.stop();}
                        } catch (Exception e) {}
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


}