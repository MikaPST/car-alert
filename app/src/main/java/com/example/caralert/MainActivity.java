package com.example.caralert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //déclaration des constantes URL
    String URL_SEND_IMATT = "https://www.declique.net/cours/caralert/index.php/get/";
    String URL_DOWNLOAD_NUM = "https://www.declique.net/cours/caralert/index.php/load/";


    //il nous faut un adapteur (qui utilise une class générique ArrayAdapter qui est spécialisé en String
    ArrayAdapter<String> adapter;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    EditText mEditText;
    ImageButton mImageButton, mImageButton2, mImageButton3 ;
    TextView mTextView;
    Button mButton;

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEditText = findViewById(R.id.editText);
        mImageButton = findViewById(R.id.btnAlerte);
        mImageButton2 = findViewById(R.id.btnAlerteAll);
        mButton = findViewById(R.id.btnCompte);
        mImageButton3 = findViewById(R.id.btnPhoto);
        mTextView = findViewById(R.id.textView);
    }

    public void btnStartCompte(View view) {
    Intent i = new Intent(MainActivity.this, CompteActivity.class );
    startActivity(i);
    }

    public void alerte(View view) {
        try{
            //on récupère le code saisi dans la zone de texte
            String sendImatt = mEditText.getText().toString();

            //on interroge le Web Service
            HttpClient client = new HttpClient(URL_SEND_IMATT + sendImatt);
            client.start();
            client.join();
            String reponseWeb = client.getResponse();

            //on récupère l'objet JSON
            JSONObject json = new JSONObject(reponseWeb);

            //on descend encore mais cette fois-ci on récupère la valeur
            String numero = json.getString("numero");


            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("sms:" +numero));
            i.putExtra("sms_body","Votre voiture immatriculée " +sendImatt+ " gêne la voie public.");

            startActivity(i);



        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
