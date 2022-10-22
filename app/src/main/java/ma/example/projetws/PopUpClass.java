package ma.example.projetws;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import beans.Etudiant;
import ma.example.projetws.R;
public class PopUpClass implements View.OnClickListener {

    private Button update;
    private Button delete;
    private Button yes;
    private Button no;
    private Button cancel;
    private Button confirm;
    PopupWindow popupWindow;
    LinearLayout update_window;
    LinearLayout delete_window;
    LinearLayout choice_window;
    listActivity list_activity;
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private ImageView img;

    public PopUpClass(listActivity listActivity) {
        this.list_activity = listActivity;
    }

    //PopupWindow display method

    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popupwindow, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        delete = popupView.findViewById(R.id.delete);
        update = popupView.findViewById(R.id.update);
        confirm = popupView.findViewById(R.id.confirm);
        cancel = popupView.findViewById(R.id.cancel);
        no = popupView.findViewById(R.id.no);
        yes = popupView.findViewById(R.id.yes);
        choice_window = popupView.findViewById(R.id.choice_window);
        update_window = popupView.findViewById(R.id.update_window);
        delete_window = popupView.findViewById(R.id.delete_window);
        nom = (EditText) popupView.findViewById(R.id.nom);
        prenom = (EditText) popupView.findViewById(R.id.prenom);
        ville = (Spinner) popupView.findViewById(R.id.ville);
        m = (RadioButton) popupView.findViewById(R.id.m);
        f = (RadioButton) popupView.findViewById(R.id.f);
        img = popupView.findViewById(R.id.img);

        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        no.setOnClickListener(this);
        yes.setOnClickListener(this);



        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Etudiant e = listActivity.getEtudiants().get(listActivity.position);
        if(v == update){
            byte[] decodedString = Base64.decode(e.getImg_code(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);
            nom.setText(e.getNom());
            prenom.setText(e.getPrenom());
            Log.d("sexe", e.getSexe());
            if (e.getSexe().equals("homme")) {
                m.setChecked(true);
                f.setChecked(false);
            } else {
                m.setChecked(false);
                f.setChecked(true);
            }
            for(int i = 0; i < 3; i++){
                if (ville.getItemAtPosition(i).toString().equals(e.getVille())){
                    ville.setSelection(i);
                }
            }
            choice_window.setVisibility(View.GONE);
            update_window.setVisibility(View.VISIBLE);
        } else if (v == delete) {
            choice_window.setVisibility(View.GONE);
            delete_window.setVisibility(View.VISIBLE);
        } else if (v == yes){
            list_activity.delete_etudiant();
            popupWindow.dismiss();
        } else if (v == confirm){
            String sexe = "";
            if (m.isChecked())
                sexe = "homme";
            else
                sexe = "femme";
            e.setNom(nom.getText().toString());
            e.setPrenom(prenom.getText().toString());
            e.setSexe(sexe);
            e.setVille(ville.getSelectedItem().toString());
            list_activity.update_etudiant(e);
            popupWindow.dismiss();
        } else {
            popupWindow.dismiss();
        }

    }
}