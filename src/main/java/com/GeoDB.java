package com;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.tasks.RuntimeExecutionException;

import java.io.FileInputStream;

public class GeoDB {

    private String dbUrl;
    private String filePath;

    private GeoFire geoFire;

    public GeoDB(String dbUrl, String filePath) {
        this.dbUrl = dbUrl;
        this.filePath = filePath;
    }

    public GeoDB init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream(filePath))
                    .setDatabaseUrl(dbUrl)
                    .build();

            FirebaseApp.initializeApp(options);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(dbUrl);
            geoFire = new GeoFire(ref);
            return this;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    public GeoDB setLocation(double lo, double la) {
        geoFire.setLocation("user-"+System.currentTimeMillis(), new GeoLocation(lo, la), new GeoFire.CompletionListener() {
            public void onComplete(String s, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                } else {
                    System.out.println("Location saved on server successfully!");
                }

            }
        });
        return this;
    }
}
