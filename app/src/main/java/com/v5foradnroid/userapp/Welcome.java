package com.v5foradnroid.userapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.v5foradnroid.userapp.activities.ActivityHistory;
import com.v5foradnroid.userapp.activities.BngQrPg;
import com.v5foradnroid.userapp.activities.StoreMainAc;
import com.v5foradnroid.userapp.fragments.FragmentHome;
import com.v5foradnroid.userapp.fragments.FragmentRec;
import com.v5foradnroid.userapp.post.NotesDBMain;
import com.v5foradnroid.userapp.sliderAdapter.WebActivity;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton qr_fab;
    int tabids = 0;
    String url, livechat, helpline;
    TabLayout tabLayout;
    TabItem tabHome, tabRecharge, tabShop, tabTallyKhata;
    Toolbar toolbar;
    static String Service_id = "service";
    static String Service_n = "name";
    public static String TAG = "Welcome";
    String ims = "L21kc2FiYmlyLnBocA==";
    static String act = "act";
    static String img = "img";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    ViewPager viewPagerNew;


    /* Foysal Tech && ict Foysal */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        Dev(Develop.DeV);

        checkPermissions();
        requestNotificationPermission();
        String pref = getPref("phone", getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Initialize TabItems
        tabHome = findViewById(R.id.home);
        tabRecharge = findViewById(R.id.Recharge);
        tabShop = findViewById(R.id.Shop);
        tabTallyKhata = findViewById(R.id.TallyKhata);
        viewPagerNew = findViewById(R.id.viewPagerNew);
        tabLayout = findViewById(R.id.tablayoutNew);
        qr_fab = findViewById(R.id.qr_fab);
        /*PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPagerNew.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPagerNew);*/
        setTabIcon(tabLayout.getTabAt(0), R.drawable.ic_baseline_home_24, "Home");
        setTabIcon(tabLayout.getTabAt(1), R.drawable.ic_recharge, "Recharge");
        setTabIcon(tabLayout.getTabAt(2), R.drawable.ic_qr_icon, "বাংলা QR");
        setTabIcon(tabLayout.getTabAt(3), R.drawable.ic_baseline_shopping_cart_24, "Shop");
        setTabIcon(tabLayout.getTabAt(4), R.drawable.ic_baseline_menu_book_24, "Tallykhata");
//        viewPagerNew.setCurrentItem(0);
        showFragment(new FragmentHome());

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null) {
                    switch (tab.getPosition()) {
                        case 0:
                            showFragment(new FragmentHome());
                            tabids = 0;
                            break;
                        case 1:
                            showFragment(new FragmentRec());
                            tabids = 1;
                            break;
                        case 2:
                            tabids = 2;
                            break;
                        case 3:
                            startActivity(new Intent(Welcome.this, StoreMainAc.class));
                            overridePendingTransition(0, 0);
                            tabids = 3;
                            break;
                        case 4:
                            showFragment(new NotesDBMain());
                            tabids = 4;
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView hmobileTextView = headerView.findViewById(R.id.hmobile);
        TextView haccTextView = headerView.findViewById(R.id.hacc);
        LinearLayout headpCo = headerView.findViewById(R.id.hrad_p);
        hmobileTextView.setText("");
        haccTextView.setText(pref);

        qr_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this, BngQrPg.class));
            }
        });

        String LvUrl = getPref("fourth", getApplicationContext());
        fetchData(LvUrl);

        if (loadColor() != 0) {
            headpCo.setBackgroundColor(loadColor());
            tabLayout.setBackgroundColor(loadColor());
            qr_fab.setBackgroundTintList(ColorStateList.valueOf(loadColor()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int colorValue = loadColor();
                toolbar.setBackgroundTintList(ColorStateList.valueOf(colorValue));
            }
            getWindow().setNavigationBarColor(loadColor());
            getWindow().setStatusBarColor(loadColor());
        }


    }

    private boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        switch (menuItem.getItemId()) {
            case R.id.dv:
                startActivityForResult(new Intent(this, Dlock.class), 0);
                return true;
            case R.id.logout:
                SavePreferences("phone", "no");
                SavePreferences("pass", "no");
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                finish();
                return true;
            case R.id.pasc:
                startActivityForResult(new Intent(this, Password.class), 0);
                return true;
            case R.id.pinc:
                startActivityForResult(new Intent(this, Pinc.class), 0);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }

    private boolean checkPermissions() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        showDialog_permission(this);
        return false;
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (tabids == 1) {
            showFragment(new FragmentHome());
            tabLayout.getTabAt(0).select();
        } else if (tabids == 3) {
            showFragment(new FragmentHome());
            tabLayout.getTabAt(0).select();
        } else if (tabids == 4) {
            showFragment(new FragmentHome());
            tabLayout.getTabAt(0).select();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("ResourceType")
    public void showDialog_permission(Activity activity) {
        final Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setContentView(R.layout.permission_close);
        ((LinearLayout) dialog2.findViewById(R.id.cem)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        ((LinearLayout) dialog2.findViewById(R.id.gal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                ArrayList arrayList = new ArrayList();
                for (String str : Welcome.this.permissions) {
                    if (ContextCompat.checkSelfPermission(Welcome.this, str) != 0) {
                        arrayList.add(str);
                    }
                }
                if (!arrayList.isEmpty()) {
                    ActivityCompat.requestPermissions(Welcome.this, (String[]) arrayList.toArray(new String[arrayList.size()]), 100);
                }
            }
        });
        dialog2.show();
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NOTIFICATION_POLICY") != 0) {
            ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_NOTIFICATION_POLICY");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_NOTIFICATION_POLICY"}, 123);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
//            showToast("Home Clicked");
        } else if (itemId == R.id.live) {
            if (livechat != null && !livechat.isEmpty()) {
                Intent intent = new Intent(Welcome.this, WebActivity.class);
                intent.putExtra("uelData", livechat);
                startActivity(intent);
            } else {
                Toast.makeText(this, "ভালোভাবে ইন্টারনেট সহকারে অ্যাপ্লিকেশনটি আবার ওপেন করুন..", Toast.LENGTH_SHORT).show();
            }
        } else if (itemId == R.id.profile) {
            startActivity(new Intent(Welcome.this, Myprofile.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.history) {
            startActivity(new Intent(Welcome.this.getApplicationContext(), history_op.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.myshop) {
            startActivity(new Intent(Welcome.this.getApplicationContext(), ActivityHistory.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.membership) {
            showToast("Membership Clicked");
        } else if (itemId == R.id.notic) {
            startActivity(new Intent(Welcome.this.getApplicationContext(), News.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.device) {
            showToast("My Device Clicked");
        } else if (itemId == R.id.device_lock) {
            startActivity(new Intent(Welcome.this.getApplicationContext(), Dlock.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.twostep) {
            startActivity(new Intent(Welcome.this.getApplicationContext(), Twostep.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.api) {
            showToast("API Key Clicked");
        } else if (itemId == R.id.changepin) {
            startActivity(new Intent(Welcome.this, Pinc.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.changepassword) {
            startActivity(new Intent(Welcome.this, Password.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.logout) {
            SavePreferences("phone", "no");
            SavePreferences("pass", "no");
            startActivity(new Intent(Welcome.this.getApplicationContext(), Login_Activity.class));
            overridePendingTransition(0, 0);
            finish();
        } else if (itemId == R.id.lang) {
            showToast("Language Clicked");
        } else if (itemId == R.id.reffer) {
            showToast("Refer Clicked");
        } else if (itemId == R.id.nav_share) {
            Uri parse = Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", String.valueOf((int) R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "Download from this Url " + parse);
            startActivity(Intent.createChooser(intent, "Share using"));
        } else if (itemId == R.id.complain) {
            startActivity(new Intent(Welcome.this.getApplicationContext(), Tricket_main.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.call) {
            if (helpline != null && !helpline.isEmpty()) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", helpline, null));
                startActivity(phoneIntent);
            } else {
                Toast.makeText(this, "ভালোভাবে ইন্টারনেট সহকারে অ্যাপ্লিকেশনটি আবার ওপেন করুন..", Toast.LENGTH_SHORT).show();
            }
        } else if (itemId == R.id.policy) {
            onPrvClickb();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setTabIcon(TabLayout.Tab tab, int iconResId, String text) {
        if (tab != null) {
            tab.setCustomView(R.layout.custom_tab);
            ImageView tabIcon = tab.getCustomView().findViewById(R.id.tabIcon);
            TextView tabText = tab.getCustomView().findViewById(R.id.tabText);
            if (tabIcon != null) {
                tabIcon.setImageResource(iconResId);
            }
            if (tabText != null) {
                tabText.setText(text);
            }
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }


    public void onPrvClickb() {
        String str = getPref("url", getApplicationContext()) + "/policy";
        url = str;
        url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        url = "https://" + url;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    public void fetchData(String lvurl) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, lvurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("liveChat");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        livechat = jsonObject1.getString("livechat");
                        helpline = jsonObject1.getString("helpline");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Handle error
            }
        });
        Volley.newRequestQueue(Welcome.this).add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Dev(String str) {
        String genAuthKey = genAuthKey(str);
        String imsOutD = genAuthKey(ims);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(ImagesContract.URL, genAuthKey);
        edit.putString("twoe", genAuthKey + "/ecommerce");
        edit.putString("threes", genAuthKey + "/store");
        edit.putString("fourth", genAuthKey + imsOutD);
        edit.commit();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String genAuthKey(String str) {
        byte[] decode = Base64.decode(str.getBytes(), 0);
        Log.e("key", new String(decode, StandardCharsets.UTF_8));
        return new String(decode, StandardCharsets.UTF_8);
    }

}
