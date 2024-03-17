package com.v5foradnroid.userapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.v5foradnroid.userapp.fragments.FragmentHome;
import com.v5foradnroid.userapp.fragments.FragmentRec;
import com.v5foradnroid.userapp.post.NotesDBMain;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Return your Fragments based on the position
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentRec();
            case 2:
                return new NotesDBMain();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Recharge";
            case 2:
                return "TallyKhata";
            default:
                return null;
        }
    }
}

