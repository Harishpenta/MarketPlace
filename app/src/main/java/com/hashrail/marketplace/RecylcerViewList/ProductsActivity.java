package com.hashrail.marketplace.RecylcerViewList;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

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
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6;
    private ProductsAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6;
    private List<product> albumList;

    public ProductsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProduct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //initCollapsingToolbar();

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
      /*  recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view3);
        recyclerView4 = (RecyclerView) findViewById(R.id.recycler_view4);
        recyclerView5 = (RecyclerView) findViewById(R.id.recycler_view5);
        recyclerView6 = (RecyclerView) findViewById(R.id.recycler_view6);*/


        albumList = new ArrayList<>();

        adapter1 = new ProductsAdapter(this, albumList);
       /* adapter2 = new ProductsAdapter(this, albumList);
        adapter3 = new ProductsAdapter(this, albumList);
        adapter4 = new ProductsAdapter(this, albumList);
        adapter5 = new ProductsAdapter(this, albumList);
        adapter6 = new ProductsAdapter(this, albumList);
*/
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //LinearLayoutManager lLayout1 = new GridLayoutManager(ProductsActivity.this, 2);
       /* LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager3
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager4
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager5
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager6
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);*/

        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(adapter1);

       /* recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(adapter2);

        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setAdapter(adapter3);

        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView4.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView4.setItemAnimator(new DefaultItemAnimator());
        recyclerView4.setAdapter(adapter4);

        recyclerView5.setLayoutManager(layoutManager5);
        recyclerView5.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView5.setItemAnimator(new DefaultItemAnimator());
        recyclerView5.setAdapter(adapter5);

        recyclerView6.setLayoutManager(layoutManager6);
        recyclerView6.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView6.setItemAnimator(new DefaultItemAnimator());
        recyclerView6.setAdapter(adapter6);*/

        // prepareAlbums();

        try {


            //Glide.with(this).load("http://www.gstatic.com/webp/gallery/1.webp").into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        callJaosnFromServer();
    }

    private void callJaosnFromServer() {

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
                                product a = new product(img_url);
                                albumList.add(a);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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


    /*private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }*/


    //******************************************************


    //******************************************************

    /**
     * Adding few albums for testing
     */
   /* private void prepareAlbums() {
        String[] covers = new String[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        product a = new product("True Romance", 13, covers[0]);
        albumList.add(a);

        a = new product("Xscpae", 8, covers[1]);
        albumList.add(a);

        a = new product("Maroon 5", 11, covers[2]);
        albumList.add(a);

        a = new product("Born to Die", 12, covers[3]);
        albumList.add(a);

        a = new product("Honeymoon", 14, covers[4]);
        albumList.add(a);

        a = new product("I Need a Doctor", 1, covers[5]);
        albumList.add(a);

        a = new product("Loud", 11, covers[6]);
        albumList.add(a);

        a = new product("Legend", 14, covers[7]);
        albumList.add(a);

        a = new product("Hello", 11, covers[8]);
        albumList.add(a);

        a = new product("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        adapter1.notifyDataSetChanged();
       // adapter2.notifyDataSetChanged();
        //adapter3.notifyDataSetChanged();
       // adapter4.notifyDataSetChanged();
        //adapter5.notifyDataSetChanged();
       // adapter6.notifyDataSetChanged();
    }*/

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
