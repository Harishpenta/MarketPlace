package com.hashrail.marketplace.RealmProject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hashrail.marketplace.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        Realm myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(RealmActivity.this)
                                .name("myRealm.realm")
                                .build()
                );


        // For Inserting Data into Realm Object
        myRealm.beginTransaction();
        // Create an object

        Country country1 = myRealm.createObject(Country.class);

        // Set its fields
        country1.setName("India");
        country1.setPopulation(150165800);
        country1.setCode("+91");

        myRealm.commitTransaction();


        RealmResults<Country> results1 =
                myRealm.where(Country.class).equalTo("code","+91").findAll();
        for (Country c : results1) {
            Log.d("results1", c.getName());
        }
    }
}
