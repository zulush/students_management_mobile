package beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Etudiant implements Parcelable {
    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;
    private String img_code;
    public Etudiant() {
    }
    public Etudiant(int id, String nom, String prenom, String ville, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.sexe = sexe;
    }

    public Etudiant(int id, String nom, String prenom, String ville, String sexe, String img_code) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.sexe = sexe;
        this.img_code = img_code;
    }

    public String getImg_code() {
        return img_code;
    }

    public void setImg_code(String img_code) {
        this.img_code = img_code;
    }

    protected Etudiant(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        ville = in.readString();
        sexe = in.readString();
        img_code = in.readString();
    }

    public static final Creator<Etudiant> CREATOR = new Creator<Etudiant>() {
        @Override
        public Etudiant createFromParcel(Parcel in) {
            return new Etudiant(in);
        }

        @Override
        public Etudiant[] newArray(int size) {
            return new Etudiant[size];
        }
    };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ville='" + ville + '\'' +
                ", sexe='" + sexe + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nom);
        parcel.writeString(prenom);
        parcel.writeString(ville);
        parcel.writeString(sexe);
        parcel.writeString(img_code);
    }
}
