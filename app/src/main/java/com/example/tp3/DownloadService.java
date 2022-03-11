package com.example.tp3;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DownloadService extends Service {

    private Looper looper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler {

        private DownloadManager manager;

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            download();
            parse();

        }

        private void parse() {
            File file = new File("information.json");
            FileReader fileReader = null;
            String informations="";
            try {
                FileInputStream in = getBaseContext().openFileInput("informations.json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                String responce = stringBuilder.toString();
                JSONObject jsonObject = new JSONObject(responce);
                String nom = jsonObject.get("nom").toString();
                String prenom = jsonObject.get("prenom").toString();
                String email = jsonObject.get("email").toString();
                String phone = jsonObject.get("phone").toString();

                informations = "Nom: "+ nom + " Prenom: " + prenom + " Email: " + email + " Phone: " + phone;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String finalInformations = informations;
            serviceHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DownloadService.this, finalInformations, Toast.LENGTH_LONG).show();
                }
            });

        }

        public void download() {
            File JsonStorageDir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "JsonFolder");
            if (!JsonStorageDir.exists()) {
                JsonStorageDir.mkdirs();
            }
            String file = getString(R.string.app_name) + "-download_URL.json";

            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse("https://lostmypieces.com:3000/kanjis");

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setDestinationInExternalPublicDir(JsonStorageDir.getPath() + File.separator, file)
                    .setTitle(file)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            manager.enqueue(request);
            System.out.println("Téléchargement terminer");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceDownload", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        looper = thread.getLooper();
        serviceHandler = new ServiceHandler(looper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        return START_STICKY;
    }
}


