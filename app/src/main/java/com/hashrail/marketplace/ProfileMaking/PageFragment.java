package com.hashrail.marketplace.ProfileMaking;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hashrail.marketplace.R;

/**
 * Created by Dreamworld Solutions on 20-7-2016.
 */
public class PageFragment extends Fragment {
    private String imageResource;
    public static PageFragment getInstance(String resourceID) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString("image_source", resourceID);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResource = getArguments().getString("image_source");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.image);
        Uri uri = Uri.parse(imageResource);
        imageView.setImageURI(uri);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
