package adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import ma.example.projetws.R;
import beans.Etudiant;

public class UserAdapter extends BaseAdapter {
    private List<Etudiant> etudiants;
    private LayoutInflater inflater;

    public UserAdapter(Activity activity, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public Object getItem(int position) {
        return etudiants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.item, null);

        TextView id = convertView.findViewById(R.id.id);
        TextView nom = convertView.findViewById(R.id.nom);
        TextView prenom = convertView.findViewById(R.id.prenom);
        TextView ville = convertView.findViewById(R.id.ville);
        TextView sexe = convertView.findViewById(R.id.sexe);
        ImageView img = convertView.findViewById(R.id.img);

        id.setText(etudiants.get(position).getId()+"");
        nom.setText("Nom : " + etudiants.get(position).getNom());
        prenom.setText("Prenom : "+etudiants.get(position).getPrenom()+"");
        ville.setText("Ville : " + etudiants.get(position).getVille()+"");
        sexe.setText("Sexe : " + etudiants.get(position).getSexe());

        byte[] decodedString = Base64.decode(etudiants.get(position).getImg_code(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
        return convertView;
    }
}

