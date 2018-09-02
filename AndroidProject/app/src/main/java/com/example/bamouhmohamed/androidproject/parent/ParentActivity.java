package com.example.bamouhmohamed.androidproject.parent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bamouhmohamed.androidproject.beans.Personne;

import com.example.bamouhmohamed.androidproject.R;
import com.example.bamouhmohamed.androidproject.database.PersonneBDD;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParentActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;

    ImageButton retourner;

    static int tempPosition;
    static int tempPosition2;

    MediaRecorder recorder;

    byte[] byteImage1 = null;

    File mCurrentPhoto;

    private List<Personne> Personnes;
    ArrayPersonneAdapter adapter;
    ListView l;
    public static PersonneBDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        retourner = this.findViewById(R.id.retournerParent);
        bdd = new PersonneBDD(this.getApplicationContext());
        bdd.open();
        //Au cas ou les permissions ne soient pas accordées automatiquement par l'appareil, on les accorde manuellement
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {android.Manifest.permission.CAMERA},REQUEST_TAKE_PHOTO);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_TAKE_PHOTO);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_TAKE_PHOTO);
        }
        retourner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        bdd.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Personne> Personnes = new ArrayList<Personne>();
        Personnes = bdd.getAllPersonnes();
        adapter = new ArrayPersonneAdapter(this, R.layout.item_parent, Personnes);
        //L'association entre la ListView et l'Adapter
        l = (ListView) findViewById(R.id.listview);
        // Binding resources Array to ListAdapter
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tempPosition=position;
                Personne item = (Personne) l.getAdapter().getItem(position);
                Toast.makeText(getApplicationContext() , item.getLabel() + " selected", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            }
        });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Toast.makeText(getApplicationContext() ," Début de l'enregistrement audio ", Toast.LENGTH_LONG).show();
                tempPosition2=position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myAudio();
                    }
                }, 1000);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                return true;
            }
        });

    }

    private void myAudio(){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy-MM-dd-HH.mm.ss");
        String fileName = "audio_" + timeStampFormat.format(new Date())
                + ".mp4";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(getExternalCacheDir().getAbsolutePath()+"/"+fileName);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recorder.stop();
        FileInputStream instream = null;
        Personne item=null;
        try {
            instream = new FileInputStream(getExternalCacheDir().getAbsolutePath()+"/"+fileName);
            File audioFile = new File(getExternalCacheDir().getAbsolutePath()+"/"+fileName);
            item = (Personne) l.getAdapter().getItem(tempPosition2);
            item.setAudio(audioFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream bif = new BufferedInputStream(instream);
        try {
            byteImage1 = new byte[bif.available()];
            bif.read(byteImage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext() ," Fin de l'enregistrement audio ", Toast.LENGTH_LONG).show();
        bdd.updatebddPersonneAudio(item, byteImage1);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            mCurrentPhoto = null;
            try {
                mCurrentPhoto = createImageFile();
            } catch (IOException ex) {
                System.out.println("ERROR CREATING FILE");
            }
            // Continue only if the File was successfully created
            if (mCurrentPhoto != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider", mCurrentPhoto);
                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);

                List<ResolveInfo> resolvedIntentActivities = this.getApplicationContext().getPackageManager()
                        .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                    String packageName = resolvedIntentInfo.activityInfo.packageName;

                    this.getApplicationContext().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                //Solution trouvée sur Internet pour accorder des permissions supplémentaires pour prendre une photo, sans elles, la caméra crashe
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap image = BitmapFactory.decodeFile(mCurrentPhoto.getPath(),bmOptions);
            System.out.println("------------------------- " + tempPosition);
            Personne item = (Personne) l.getAdapter().getItem(tempPosition);
            Bitmap icon = ThumbnailUtils.extractThumbnail(image,512,384);
            item.setIcon(icon);
            item.setImage(mCurrentPhoto.getAbsolutePath());
            bdd.updatebddPersonneIcon(item);//Stocke l'icone dans la base de données
            bdd.updatebddPersonneImagePath(item);//Stocke le chemin d'accès de l'image dans la base de données
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1: {
                Toast.makeText(this, getResources().getString(R.string.menu_1),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(this, activity_niveau.class);
                this.startActivity(intent);
                break;
            }
            case R.id.item2: {
                Toast.makeText(this, getResources().getString(R.string.menu_2), Toast.LENGTH_LONG).show();
                Intent intent= new Intent(this, activity_add_personne.class);
                this.startActivity(intent);
                break;
            }

            case R.id.item3: {
                Toast.makeText(this, getResources().getString(R.string.menu_3), Toast.LENGTH_LONG).show();
                Intent intent= new Intent(this, activity_remove_personne.class);
                this.startActivity(intent);
                break;
            }
            case R.id.item4: {
                Toast.makeText(this, getResources().getString(R.string.ball), Toast.LENGTH_LONG).show();
                Intent intent= new Intent(this, BallActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
