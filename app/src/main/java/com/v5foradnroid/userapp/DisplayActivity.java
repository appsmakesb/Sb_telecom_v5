package com.v5foradnroid.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class DisplayActivity extends AppCompatActivity {

    Intent intent;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView f166mn;
    private String number;
    String opn;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.display_activity);
        Intent intent2 = getIntent();
        this.intent = intent2;
        this.opn = intent2.getExtras().getString("opt");
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mViewPager = viewPager;
        viewPager.setAdapter(this.mSectionsPagerAdapter);
        ((TabLayout) findViewById(R.id.tablayout)).setupWithViewPager(this.mViewPager);
        this.number = this.intent.getExtras().getString("number");
        TextView textView = (TextView) findViewById(R.id.number);
        this.f166mn = textView;
        textView.setText(this.number);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static Recharge.PlaceholderFragment newInstance(int i) {
            Recharge.PlaceholderFragment placeholderFragment = new Recharge.PlaceholderFragment();
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
                return "Amount";
            }
            if (i == 1) {
                return "Drive";
            }
            if (i != 2) {
                return null;
            }
            return "Regular";
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
