package com.v5foradnroid.userapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Recharge extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.tact);
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mViewPager = viewPager;
        viewPager.setAdapter(this.mSectionsPagerAdapter);
        ((TabLayout) findViewById(R.id.tablayout)).setupWithViewPager(this.mViewPager);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int i) {
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, i);
            placeholderFragment.setArguments(bundle);
            return placeholderFragment;
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return layoutInflater.inflate(R.layout.tact, viewGroup, false);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int i) {
            if (i == 0) {
                return "Chats";
            }
            if (i == 1) {
                return "Status";
            }
            if (i != 2) {
                return null;
            }
            return "Calls";
        }

        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            if (i == 0) {
                return new Frag1();
            }
            if (i == 1) {
                return new Frag2();
            }
            if (i != 2) {
                return null;
            }
            return new Frag3();
        }
    }
}
