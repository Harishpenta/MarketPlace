package com.hashrail.marketplace.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hashrail.marketplace.ProfileMaking.PageFragment;

import java.util.List;

/**
 * Created by Dreamworld Solutions on 20-7-2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> images;

    public ViewPagerAdapter(FragmentManager fm, List<String> imagesList) {
        super(fm);
        this.images = imagesList;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}