package com.hashrail.marketplace.Filter;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hashrail.marketplace.R;

public class FilterActivity extends AppCompatActivity {

    TextView lblMaxValue, lblMinValue;
    int yourStep = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_and_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSortFilter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Filter");
        //initCollapsingToolbar();
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        lblMaxValue = (TextView) findViewById(R.id.lblMaxValue);
        lblMinValue = (TextView) findViewById(R.id.lblMinValue);

        seekBar.setMax(10000);
        lblMaxValue.setText("₹" + Integer.toString(seekBar.getMax()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //Toast.makeText(getApplicationContext(), progress + "", Toast.LENGTH_LONG).show();
                progress = ((int) Math.round(progress / yourStep)) * yourStep;
                seekBar.setProgress(progress);
                lblMinValue.setText("₹" + Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
