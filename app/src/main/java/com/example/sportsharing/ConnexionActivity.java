package com.example.sportsharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sportsharing.Classe.DossierVariableClasse;
import com.example.sportsharing.Classe.Sportif;
import com.example.sportsharing.ClasseDAO.SportifDAO;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConnexionActivity extends AppCompatActivity {

    //VARIABLES maquette
    private TextInputEditText login, password;
    private Button connect;
    private TextView passwordOublie, inscription;

    //VARIABLES
    private Intent demarre;
    private DossierVariableClasse global;
    private SportifDAO sportifDAO;

    private String MESSAGE_ERROR_LOGIN;
    private String MESSAGE_ERROR_PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_application);

        //Initialisation des messages d'erreurs
        MESSAGE_ERROR_LOGIN = getString(R.string.error_login);
        MESSAGE_ERROR_PASSWORD = getString(R.string.error_password);

        //Instance de DossierVariableClasse
        global = DossierVariableClasse.getInstance();

        //Instance sportifDAO
        sportifDAO = new SportifDAO(this);

        //Récupération du profil à charger avec le paramètre resteConnecte à true
        Sportif aConnecte = sportifDAO.getSportifResteConnecte();
        if(aConnecte != null) {
            global.utilisateur = aConnecte;

            //Ouverture maquette Accueil
            demarre = new Intent(getApplicationContext(), AccueilActivity.class);
            startActivity(demarre);
            finish();
        }

        //Initialisation des Variables
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        connect = findViewById(R.id.connect);
        passwordOublie = findViewById(R.id.passwordOublie);
        inscription = findViewById(R.id.inscription);

        //Attribution OnClick à variable
        connect.setOnClickListener(connexionAppli);
        inscription.setOnClickListener(creationCompte);
    }

    //Fonctions onClick
    View.OnClickListener connexionAppli = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Récupération info login et password
            String loginText = login.getText().toString();
            String motDePasseText = password.getText().toString();

            //Récupération du sportif dans la base de donnée
            Sportif utilisateur = sportifDAO.getSportif(loginText);
            if(utilisateur != null) {
                if(motDePasseText.equals(utilisateur.getMotDePasse())) {

                    //Définition du profil chargé
                    global.setUtilisateur(utilisateur);

                    //Ouverture maquette Accueil
                    demarre = new Intent(getApplicationContext(), AccueilActivity.class);
                    startActivity(demarre);
                    finish();
                }
                else {
                    password.setError(MESSAGE_ERROR_PASSWORD);
                }
            }
            else {
                login.setError(MESSAGE_ERROR_LOGIN);
            }
        }
    };

    View.OnClickListener creationCompte = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Création du nouveau profil
            global.utilisateur = new Sportif();

            //Ouverture maquette inscription
            demarre = new Intent(getApplicationContext(), Inscription1Activity.class);
            startActivity(demarre);
            finish();
        }
    };
}
