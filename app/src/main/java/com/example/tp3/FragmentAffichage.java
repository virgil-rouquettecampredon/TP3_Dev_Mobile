package com.example.tp3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class FragmentAffichage extends Fragment {

    private TextView textViewFullName;
    private Button button;
    private Button button2;
    private Button buttonFile;
    private String prenom;
    private String nom;
    private String email;
    private String phone;
    private int sport;
    private int informatique;
    private int lecture;
    private int musique;
    private String filename = "informations.json";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Read xml file and return View object.
        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
        View view = inflater.inflate(R.layout.fragment_affichage, container, false);
        textViewFullName = (TextView) view.findViewById(R.id.tvAffichage);
        button = (Button) view.findViewById(R.id.buttonSubmit);
        button2 = (Button) view.findViewById(R.id.buttonRetour);
        buttonFile = (Button) view.findViewById(R.id.buttonFile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("prenom", prenom);
                    jsonObject.put("nom", nom);
                    jsonObject.put("email", email);
                    jsonObject.put("phone", phone);
                    jsonObject.put("sport", sport);
                    jsonObject.put("informatique", informatique);
                    jsonObject.put("musique", musique);
                    jsonObject.put("lecture", lecture);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String content = jsonObject.toString();

                File file = new File(getActivity().getFilesDir(),filename);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(content);
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        buttonFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Open stream to read file.
                    FileInputStream in = getActivity().openFileInput(filename);

                    BufferedReader br= new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb= new StringBuilder();
                    String s= null;
                    while((s= br.readLine())!= null)  {
                        sb.append(s).append("\n");
                    }
                    Toast.makeText(getActivity(),sb.toString(),Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(),"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    public void showText(String prenom, String nom, String email, String phone, int sport, int musique, int lecture, int informatique) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.phone = phone;
        this.sport = sport;
        this.musique = musique;
        this.lecture = lecture;
        this.informatique = informatique;

        textViewFullName.setText("Prénom : \t"+prenom+"\n" +"Nom: \t" + nom + "\nEmail : \t" + email + "\nPhone :\t" + phone + "\nCentre d'intérêts :\n\tSport :\t" + sport + "\n\tMusique :\t" + musique + "\n\tLecture :\t" + lecture + "\n\tInformatique :\t" + informatique);
    }
}