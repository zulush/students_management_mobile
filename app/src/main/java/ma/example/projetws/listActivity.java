package ma.example.projetws;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.UserAdapter;
import beans.Etudiant;

public class listActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    private ListView listContainer;
    static private List<Etudiant> etudiants;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.56.1/projetSchool/ws/loadEtudiant.php";
    String deleteUrl = "http://192.168.56.1/projetSchool/ws/deleteEtudiant.php";
    String updateUrl = "http://192.168.56.1/projetSchool/ws/updateEtudiant.php";

    public static void setEtudiants(List<Etudiant> etudiants) {
        listActivity.etudiants = etudiants;
    }

    public static List<Etudiant> getEtudiants() {
        return etudiants;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listContainer = findViewById(R.id.listContainer);
        setData();
        builder = new AlertDialog.Builder(this);

        
    }

    private void setData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("data", response);
                Type type = new TypeToken<Collection<Etudiant>>() {
                }.getType();
                List<Etudiant>data = new Gson().fromJson(response, type);
                Log.d("data", data.toString());
                listActivity.setEtudiants(data);
                update_liste();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }
        };
        requestQueue.add(request);

    }
    static public int position = -1;
    private void popupwindow(View view) {
        PopUpClass popUpClass = new PopUpClass(this);
        popUpClass.showPopupWindow(view);
    }

    public void update_etudiant(Etudiant e){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                updateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
                e.setId(getEtudiants().get(position).getId());
                update_liste();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", listActivity.etudiants.get(position).getId()+"");
                params.put("nom", e.getNom()+"");
                params.put("prenom", e.getPrenom()+"");
                params.put("ville", e.getVille()+"");
                params.put("sexe", e.getSexe()+"");
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void delete_etudiant(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                deleteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
                getEtudiants().remove(position);
                update_liste();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", listActivity.etudiants.get(position).getId()+"");
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void update_liste() {
        listContainer.setAdapter(new UserAdapter(listActivity.this, etudiants));
        listContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listActivity.position = position;
                popupwindow(view);
            }
        });
    }
}