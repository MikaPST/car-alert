package com.example.caralert;

import androidx.appcompat.app.AppCompatActivity;



import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompteActivity extends AppCompatActivity {

    String URL_DOWNLOAD_NUM = "https://www.declique.net/cours/caralert/index.php/load/";

    EditText mEditText, mEditText2, mEditText3;
    TextView mTextView;
    ImageButton mImageButton, mImageButton2, mImageButton3, mImageButton4 ;
    SQLiteDatabase db;


    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        mImageButton = findViewById(R.id.btnSearch);
        mImageButton2 = findViewById(R.id.btnAdd);
        mImageButton3 = findViewById(R.id.btnDelete);
        mImageButton4 = findViewById(R.id.btnValid);
        mEditText = findViewById(R.id.etSearch);
        mEditText2 = findViewById(R.id.etPlaque1);
        mEditText3 = findViewById(R.id.etPlaque2);
        mTextView = findViewById(R.id.textView);

    }

    public void btnStopActivity(View view) {
        finish();
    }

    public void Search(View view) throws InterruptedException {

        try {
            //on récupère le code saisi dans la zone de texte
            String numero = mEditText.getText().toString();

            //on interroge le Web Service
            HttpClient client = new HttpClient(URL_DOWNLOAD_NUM + numero);
            client.start();
            client.join();
            String result = client.getResponse();
            String numPlaque = "";

            //on descend encore mais on met dans un tableau
            JSONArray jsonArray = new JSONArray(result);

            //on remplit le tableau avec tous les noms
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject plaque = jsonArray.getJSONObject(i);
                numPlaque = plaque.getString("plaque");
            }

            mEditText2.setText(numPlaque);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
