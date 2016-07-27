package com.hashrail.marketplace.GridListView;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.hashrail.marketplace.R;
import com.hashrail.marketplace.RecylcerViewList.product;

import java.util.ArrayList;
import java.util.List;

public class GridProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private GridProductsAdapter adapter1;
    private List<product> albumList;
    private GridLayoutManager lLayout1;

    public GridProductsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_grid);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProductGrid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //initCollapsingToolbar();

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view11);


        albumList = new ArrayList<>();

        adapter1 = new GridProductsAdapter(this, albumList);

        lLayout1 = new GridLayoutManager(GridProductsActivity.this, 2);


        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(lLayout1);
        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(adapter1);



      //  prepareAlbums();

        try {


            //Glide.with(this).load("http://www.gstatic.com/webp/gallery/1.webp").into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * Adding few albums for testing
     */
    /*private void prepareAlbums() {
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
