package com.example.tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Exercice1_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice12);
        FragmentAffichage bottomFragment = (FragmentAffichage) this.getSupportFragmentManager().findFragmentById(R.id.fragmentAffichage);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String prenom = extra.getString("prenom");
        String nom = extra.getString("nom");
        String email = extra.getString("email");
        String phone = extra.getString("phone");
        int sport = extra.getInt("sport");
        int musique = extra.getInt("musique");
        int lecture = extra.getInt("lecture");
        int informatique = extra.getInt("informatique");
        bottomFragment.showText(prenom, nom, email, phone, sport, musique, lecture, informatique);
    }
}


