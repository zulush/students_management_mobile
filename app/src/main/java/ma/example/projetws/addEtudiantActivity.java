package ma.example.projetws;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.Etudiant;


public class addEtudiantActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_REQUEST = 0;
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button add;
    private Button showList;
    private Button addImg;
    private ImageView img;
    private Uri fileUri;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.56.1/projetSchool/ws/createEtudiant.php";
    private String picturePath;
    private Bitmap bitmap;
    private String encoded = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_etudiant);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        ville = (Spinner) findViewById(R.id.ville);
        add = (Button) findViewById(R.id.add);
        addImg = (Button) findViewById(R.id.addImg);
        showList = (Button) findViewById(R.id.showList);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);
        img = (ImageView) findViewById(R.id.img);
        add.setOnClickListener(this);
        showList.setOnClickListener(this);
        addImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("ok", "ok");
        if (v == add) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST,
                    insertUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("TAG", response);
                    Type type = new TypeToken<Collection<Etudiant>>() {
                    }.getType();
                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                    for (Etudiant e : etudiants) {
                        Log.d("TAG", e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error : " + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String sexe = "";
                    if (m.isChecked())
                        sexe = "homme";
                    else
                        sexe = "femme";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("ville", ville.getSelectedItem().toString());
                    params.put("sexe", sexe);
                    params.put("img_code", encoded);
                    return params;
                }
            };
            requestQueue.add(request);
        } else if (v == showList){
            startActivity(new Intent(addEtudiantActivity.this, listActivity.class));
        } else if (v == addImg) {
            getImage();
        }
    }

    private void getImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData(); // 1
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage); // 2
                        img.setImageBitmap(bitmap); // 3
                        uploadImage();
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    private void uploadImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.i("TAG", "encoded : " + encoded);
    }
}