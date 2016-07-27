package com.hashrail.marketplace.volleyIntegration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hashrail.marketplace.AppController;
import com.hashrail.marketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyActivity extends AppCompatActivity {
    TextView lblShow;
    ArrayList<String> dlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        lblShow = (TextView) findViewById(R.id.lblShow);
        String tag_json_arry = "json_array_req";

        String url = "http://192.168.0.103:8080/gcm/image_url_json.php";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Log.d("data", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject c = response.getJSONObject(i);
                                String img_url = c.getString("image_url");
                                dlist.add(img_url);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        lblShow.setText(dlist.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("err", "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }
}
