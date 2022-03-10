package com.example.tp3;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class FragmentSaisie extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private CheckBox checkBoxSport;
    private CheckBox checkBoxMusique;
    private CheckBox checkBoxLecture;
    private CheckBox checkBoxInformatique;

    private Button buttonApply;
    private Button buttonDownload;

    private DownloadManager manager;

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Read xml file and return View object.
        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
        View view = inflater.inflate(R.layout.fragment_saisie, container, false);

        editTextFirstName = (EditText) view.findViewById(R.id.editText_firstName);
        editTextLastName = (EditText) view.findViewById(R.id.editText_lastName);
        editTextEmail = (EditText) view.findViewById(R.id.editText_email);
        editTextPhone = (EditText) view.findViewById(R.id.editText_phone);
        checkBoxSport = (CheckBox) view.findViewById(R.id.cbSport);
        checkBoxLecture = (CheckBox) view.findViewById(R.id.cbLecture);
        checkBoxInformatique = (CheckBox) view.findViewById(R.id.cbInformatique);
        checkBoxMusique = (CheckBox) view.findViewById(R.id.cbMusique);

        buttonApply = (Button) view.findViewById(R.id.button_apply);
        buttonDownload = (Button) view.findViewById(R.id.button_download);

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("https://lostmypieces.com:3000/kanjis");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                long reference = manager.enqueue(request);
                System.out.println("Téléchargement terminer");

            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                applyText();
            }
        });

        return view;
    }

    // Called when a fragment is first attached to its context.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            this.mainActivity = (MainActivity) context;
        }
    }


    private void applyText() {
        Intent intent = new Intent(getActivity(), Exercice1_2.class);
        intent.putExtra("prenom", this.editTextFirstName.getText().toString());
        intent.putExtra("nom", this.editTextLastName.getText().toString());
        intent.putExtra("email", this.editTextEmail.getText().toString());
        intent.putExtra("phone", this.editTextPhone.getText().toString());

        if (checkBoxSport.isChecked()) {
            intent.putExtra("sport", 1);
        } else {
            intent.putExtra("sport", 0);
        }
        if (checkBoxMusique.isChecked()) {
            intent.putExtra("musique", 1);
        } else {
            intent.putExtra("musique", 0);
        }
        if (checkBoxLecture.isChecked()) {
            intent.putExtra("lecture", 1);
        } else {
            intent.putExtra("lecture", 0);
        }
        if (checkBoxInformatique.isChecked()) {
            intent.putExtra("informatique", 1);
        } else {
            intent.putExtra("informatique", 0);
        }
        startActivity(intent);
    }
}