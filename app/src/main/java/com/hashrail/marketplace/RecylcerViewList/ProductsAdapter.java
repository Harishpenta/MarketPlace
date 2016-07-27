package com.hashrail.marketplace.RecylcerViewList;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.hashrail.marketplace.AppController;
import com.hashrail.marketplace.R;

import java.util.List;

/**
 * Created by Dreamworld Solutions on 15-7-2016.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context mContext;
    private List<product> albumList;

    public ProductsAdapter(Context mContext, List<product> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        product pro = albumList.get(position);
        holder.title.setText(pro.getName());
        holder.count.setText(pro.getNumOfProducts() + " products");
        RippleForegroundListener rippleForegroundListener = new RippleForegroundListener();
        // loading album cover using Glide library
        //Glide.with(mContext).load(pro.getThumbnail()).into(holder.thumbnail);
        //Uri uri = Uri.parse(pro.getThumbnail());

        //holder.thumbnail.setImageURI(uri);
        holder.thumbnail.setImageUrl(pro.getThumbnail(), imageLoader);
// If you are using NetworkImageView


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked at position -> " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_product, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView overflow;
        public NetworkImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);


        }
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
