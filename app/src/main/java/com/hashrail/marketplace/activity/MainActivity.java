package com.hashrail.marketplace.activity;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hashrail.marketplace.Filter.FilterActivity;
import com.hashrail.marketplace.ProfileMaking.UpdateProfile;
import com.hashrail.marketplace.R;
import com.hashrail.marketplace.RecylcerViewList.ProductsActivity;
import com.hashrail.marketplace.fragments.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    Toolbar toolbarMain;
    Button btnsetProfile;
    AnimationDrawable drawable;
    DrawerLayout drawer_layout;
    ImageView imgLogoAnimate;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarMain = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnsetProfile = (Button) findViewById(R.id.btnsetProfile);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbarMain);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch

//Uri uri = Uri.parse("http://www.gstatic.com/webp/gallery/2.webp");
        // SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        // draweeView.setImageURI(uri);

        btnsetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, ProductsActivity.class);
                startActivity(i);
            }
        });


    }


    //***********NotificationCode**************************************


    //***********NotificationCode**************************************

    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                Intent i = new Intent(MainActivity.this, UpdateProfile.class);
                startActivity(i);

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:

                break;

            case 4:
                //Snackbar.make(drawer_layout, "Please Check Internet Connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                break;


            default:

                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }

    }


}

