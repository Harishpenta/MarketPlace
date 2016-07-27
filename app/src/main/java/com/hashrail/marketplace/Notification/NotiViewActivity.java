package com.hashrail.marketplace.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hashrail.marketplace.R;

public class NotiViewActivity extends AppCompatActivity {
    TextView lblsetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_view);

        lblsetText = (TextView) findViewById(R.id.lblsetText);
        Intent i = getIntent();
        String url = i.getStringExtra("Notif");
        String msg = i.getStringExtra("SURL");

        lblsetText.setText(url + "\n" + msg);

    }
}
