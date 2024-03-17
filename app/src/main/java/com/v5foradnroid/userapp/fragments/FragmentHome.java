package com.v5foradnroid.userapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.HighLatitudeRule;
import com.batoulapps.adhan.Madhab;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.bumptech.glide.Glide;
import com.v5foradnroid.userapp.Addres;
import com.v5foradnroid.userapp.Myreseller;
import com.v5foradnroid.userapp.Operator;
import com.v5foradnroid.userapp.Payment_type;
import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.Transfer;
import com.v5foradnroid.userapp.activities.ChangeLocationActivity;
import com.v5foradnroid.userapp.sliderAdapter.AllSliderActivity;
import com.v5foradnroid.userapp.sliderAdapter.FourthAdapter;
import com.v5foradnroid.userapp.sliderAdapter.SliderAdapter;
import com.v5foradnroid.userapp.sliderAdapter.SliderAdapterThird;
import com.v5foradnroid.userapp.sliderAdapter.SliderAdapterTwo;
import com.v5foradnroid.userapp.sliderAdapter.SliderData;
import com.v5foradnroid.userapp.sliderAdapter.SliderOne;
import com.v5foradnroid.userapp.sliderAdapter.SliderThirdModel;
import com.v5foradnroid.userapp.sliderAdapter.WebActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHome extends Fragment {

    ImageView add_user_icon , myuser_icon , addbalance_icon , sendmoney_icon , wp_icon , tel_icon , fb_iconN , yt_iconN , minarcion_IV , minarcionright;
    Drawable person_add , ic_baseline_people_24 , ic_add_money , ic_send_money , ic_icons_whatsapp , ic_icons_telegram , ic_icons_facebook , ic_icons_youtube , ic_header_is_vg , minarcion , print_sha_dr;
    ProgressBar weatherPg;
    String countyValue;
    private final String urln = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");
    TextView weatherTVrt;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    ImageView print_sha;
    String drivedata, bankdata, mainData;
    LinearLayout profile_linear, balance_show, weather;
    TextView textFazarTime, textJaharTime, textAshrTime, textMagribhTime, textEshaTime, textCurrentLocation, Friday_tv , sehriTv , iftarTv;
    String fajrTimeRange, dhuhrTimeRange, asrTimeRange, maghribTimeRange, ishaTimeRange;
    Intent intent;
    Button balanc, confirm_lay;
    public static String TAG = "Welcome";
    private static final String TAG_Balance = "balance";
    String FinalJSonObject, btype, device;
    ArrayList<HashMap<String, String>> arraylist;
    Dialog dialog;
    int flag = 0;
    String img1, img2, img3, img4, number, pwd, shop, telegram, whatsapp, youtube, token, url;
    JSONArray jsonarray;
    RelativeLayout bank_lay, drive_lay;
    private TextView nameTextView, leve, newOffer, hourTextView, secondTextView, Var_date, mainTv, bank_tv, drive_tv;
    private Handler handler;
    private Runnable updateTimeRunnable;
    CircleImageView prof;
    LinearLayout adduser, myusers, addbalance, sendmoney, lay_whatsapp, lay_telegram, lay_facebook, lay_youtube, custom_location_layout;
    View view;
    private static String Json_Url;
    SliderAdapter slideradapter;
    FourthAdapter fourthAdapter;
    RecyclerView recyclerView, thirdRecyclerView;
    ArrayList<SliderOne> list;
    ArrayList<SliderData> list2;
    ArrayList<SliderThirdModel> list3;
    ArrayList<SliderOne> list4;
    SliderAdapterTwo myadapter;
    SliderAdapterThird sliderAdapterThird;
    String anim, gmLink, noticeImg, noticetv;
    LottieAnimationView lottieAnimationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        String pref2 = getPref("profilepic", requireContext());
        balanc = view.findViewById(R.id.pulsatingButton);
        leve = view.findViewById(R.id.lev);
        prof = view.findViewById(R.id.profile);
        myusers = view.findViewById(R.id.myusers);

        nameTextView = view.findViewById(R.id.name);
        hourTextView = view.findViewById(R.id.clock);
        secondTextView = view.findViewById(R.id.sec);
        Var_date = view.findViewById(R.id.date);
        adduser = view.findViewById(R.id.adduser);
        addbalance = view.findViewById(R.id.addbalance);
        sendmoney = view.findViewById(R.id.sendmoney);
        lay_whatsapp = view.findViewById(R.id.whatsapp);
        lay_telegram = view.findViewById(R.id.telegram);
        lay_facebook = view.findViewById(R.id.facebook);
        lay_youtube = view.findViewById(R.id.youtube);
        profile_linear = view.findViewById(R.id.profile_linear);
        balance_show = view.findViewById(R.id.balance_show);
        mainTv = view.findViewById(R.id.main);
        bank_tv = view.findViewById(R.id.bank);
        drive_tv = view.findViewById(R.id.drive_bl);
        bank_lay = view.findViewById(R.id.bankl);
        drive_lay = view.findViewById(R.id.drivel);
        confirm_lay = view.findViewById(R.id.confirm);
        weather = view.findViewById(R.id.weather);
        weatherTVrt = view.findViewById(R.id.weatherTV);
        weatherPg = view.findViewById(R.id.weatherPg);
        print_sha = view.findViewById(R.id.print_sha);

        handler = new Handler();
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentTime();
                handler.postDelayed(this, 1000);
            }
        };
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        Var_date.setText(currentDate);
        handler.post(updateTimeRunnable);

        lottieAnimationView = view.findViewById(R.id.animation_view);
        recyclerView = view.findViewById(R.id.recyclarView);
        thirdRecyclerView = view.findViewById(R.id.ThirdrecyclarView);
        newOffer = view.findViewById(R.id.NewOfferId2);
        item_list();

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(requireContext(), Addres.class);
                startActivity(intent);
            }
        });

        balanc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                balance();
            }
        });
        setButtonAnimation(balanc);

        myusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(requireContext(), Myreseller.class);
                startActivity(intent);
            }
        });

        addbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(requireContext(), Payment_type.class);
                startActivity(intent);
            }
        });

        sendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(requireContext(), Transfer.class);
                startActivity(intent);
            }
        });

        lay_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whatsapp != null && !whatsapp.isEmpty()) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(whatsapp));
                    Patterns.WEB_URL.matcher(whatsapp).matches();
                    startActivity(intent);
                }
            }
        });

        lay_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telegram != null && !telegram.isEmpty()) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(telegram));
                    Patterns.WEB_URL.matcher(telegram).matches();
                    startActivity(intent);
                }
            }
        });

        lay_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youtube != null && !youtube.isEmpty()) {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(youtube));
                    Patterns.WEB_URL.matcher(youtube).matches();
                    startActivity(intent);
                }

            }
        });

        if (!TextUtils.isEmpty(pref2)) {
            File file = new File(pref2);
            if (file.exists()) {
                prof.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(file.getAbsolutePath())));
            }
        }
        prof.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2);
            }
        });

        confirm_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog(getActivity());
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(5000);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationSettings();
                requestLocationPermission();
                weatherPg.setVisibility(View.VISIBLE);
            }
        });

        /////////NTime Start//////////////


        custom_location_layout = view.findViewById(R.id.custom_location_layout);
        textCurrentLocation = view.findViewById(R.id.textCurrentLocation);

        custom_location_layout.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChangeLocationActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        });


        // All Textview
        textFazarTime = view.findViewById(R.id.fajar);
        textJaharTime = view.findViewById(R.id.dhuhr);
        textAshrTime = view.findViewById(R.id.asr);
        textMagribhTime = view.findViewById(R.id.maghrib);
        textEshaTime = view.findViewById(R.id.isha);
        sehriTv = view.findViewById(R.id.Sehri_tv);
        iftarTv = view.findViewById(R.id.Iftar_tv);
        Friday_tv = view.findViewById(R.id.Friday_tv);

        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Friday_tv.setText("Juma'a");
        } else {
            Friday_tv.setText("Dhuhr");
        }

        calculateAndDisplayPrayerTimes();
        loadLocationFromPreferences();

        ///////////N Time End////////////

        add_user_icon = view.findViewById(R.id.add_user_icon);
        myuser_icon = view.findViewById(R.id.myuser_icon);
        addbalance_icon = view.findViewById(R.id.addbalance_icon);
        sendmoney_icon = view.findViewById(R.id.sendmoney_icon);
        wp_icon = view.findViewById(R.id.wp_icon);
        tel_icon = view.findViewById(R.id.tel_icon);
        fb_iconN = view.findViewById(R.id.fb_iconN);
        yt_iconN = view.findViewById(R.id.yt_iconN);
        minarcion_IV = view.findViewById(R.id.minarcion);
        minarcionright = view.findViewById(R.id.minarcionright);
        RelativeLayout head_icon_bg = view.findViewById(R.id.head_icon_bg);
        LinearLayout main_roun_bg = view.findViewById(R.id.main_roun);
        TextView scheduleTv = view.findViewById(R.id.scheduleTv);
        TextView wel_tv = view.findViewById(R.id.wel);
        TextView ss_tv = view.findViewById(R.id.ss);
        TextView app_nameTv = view.findViewById(R.id.app_nameTv);

        if (loadColor() != 0) {
//            balanc.setBackgroundColor(loadColor());
            int color = loadColor();
            person_add = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_baseline_person_add_24);
            ic_baseline_people_24 = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_baseline_people_24);
            ic_add_money = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_add_money);
            ic_send_money = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_send_money);
            ic_icons_whatsapp = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_icons_whatsapp);
            ic_icons_telegram = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_icons_telegram);
            ic_icons_facebook = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_icons_facebook);
            ic_icons_youtube = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_icons_youtube);
            ic_header_is_vg = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_header_is_vg);
            minarcion = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_minar_ok);
            print_sha_dr = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_baseline_print_24);

            person_add = DrawableCompat.wrap(person_add);
            ic_baseline_people_24 = DrawableCompat.wrap(ic_baseline_people_24);
            ic_add_money = DrawableCompat.wrap(ic_add_money);
            ic_send_money = DrawableCompat.wrap(ic_send_money);
            ic_icons_whatsapp = DrawableCompat.wrap(ic_icons_whatsapp);
            ic_icons_telegram = DrawableCompat.wrap(ic_icons_telegram);
            ic_icons_facebook = DrawableCompat.wrap(ic_icons_facebook);
            ic_icons_youtube = DrawableCompat.wrap(ic_icons_youtube);
            ic_header_is_vg = DrawableCompat.wrap(ic_header_is_vg);
            minarcion = DrawableCompat.wrap(minarcion);
            print_sha_dr = DrawableCompat.wrap(print_sha_dr);

            DrawableCompat.setTint(person_add, color);
            DrawableCompat.setTint(ic_baseline_people_24, color);
            DrawableCompat.setTint(ic_add_money, color);
            DrawableCompat.setTint(ic_send_money, color);
            DrawableCompat.setTint(ic_icons_whatsapp, color);
            DrawableCompat.setTint(ic_icons_telegram, color);
            DrawableCompat.setTint(ic_icons_facebook, color);
            DrawableCompat.setTint(ic_icons_youtube, color);
            DrawableCompat.setTint(ic_header_is_vg, color);
            DrawableCompat.setTint(minarcion, color);
            DrawableCompat.setTint(print_sha_dr, color);

            add_user_icon.setImageDrawable(person_add);
            myuser_icon.setImageDrawable(ic_baseline_people_24);
            addbalance_icon.setImageDrawable(ic_add_money);
            sendmoney_icon.setImageDrawable(ic_send_money);
            wp_icon.setImageDrawable(ic_icons_whatsapp);
            tel_icon.setImageDrawable(ic_icons_telegram);
            fb_iconN.setImageDrawable(ic_icons_facebook);
            yt_iconN.setImageDrawable(ic_icons_youtube);
            minarcion_IV.setImageDrawable(minarcion);
            minarcionright.setImageDrawable(minarcion);
            print_sha.setImageDrawable(print_sha_dr);
            head_icon_bg.setBackground(ic_header_is_vg);

            textFazarTime.setTextColor(loadColor());
            textJaharTime.setTextColor(loadColor());
            textAshrTime.setTextColor(loadColor());
            textMagribhTime.setTextColor(loadColor());
            textEshaTime.setTextColor(loadColor());
            hourTextView.setTextColor(loadColor());
            secondTextView.setTextColor(loadColor());
            ss_tv.setTextColor(loadColor());
            app_nameTv.setTextColor(loadColor());
            Var_date.setTextColor(loadColor());
            scheduleTv.setTextColor(loadColor());
            wel_tv.setTextColor(loadColor());
            confirm_lay.setTextColor(loadColor());
            textCurrentLocation.setTextColor(loadColor());
            sehriTv.setTextColor(loadColor());
            iftarTv.setTextColor(loadColor());
            nameTextView.setTextColor(loadColor());
            balanc.setBackground(createPulsatingButtonBackground(loadColor()));

            int colorFrom = loadColor();
            ImageView vectorImageView = view.findViewById(R.id.fajr_icon);
            ImageView dhuhr_iconIv = view.findViewById(R.id.dhuhr_icon);
            ImageView asr_icon = view.findViewById(R.id.asr_icon);
            ImageView maghrib_icon = view.findViewById(R.id.maghrib_icon);
            ImageView isha_icon = view.findViewById(R.id.isha_icon);
            ImageView sh_endTime = view.findViewById(R.id.sh_endTime);
            ImageView iftar_icon = view.findViewById(R.id.iftar_icon);
            vectorImageView.setColorFilter(colorFrom);
            dhuhr_iconIv.setColorFilter(colorFrom);
            asr_icon.setColorFilter(colorFrom);
            maghrib_icon.setColorFilter(colorFrom);
            isha_icon.setColorFilter(colorFrom);
            sh_endTime.setColorFilter(colorFrom);
            iftar_icon.setColorFilter(colorFrom);

            GradientDrawable shapeDrawable = new GradientDrawable();
            shapeDrawable.setShape(GradientDrawable.RECTANGLE);
            shapeDrawable.setCornerRadius(getResources().getDimension(R.dimen.corner_radius_new));
            shapeDrawable.setStroke((int) getResources().getDimension(R.dimen.stroke_width), colorFrom);
            main_roun_bg.setBackground(shapeDrawable);

            String pref = getPref("phone", getActivity());
            nameTextView.setText(pref);
//            add_user_icon.setBackgroundTintList(ColorStateList.valueOf(loadColor()));

        }

        print_sha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri parse = Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", String.valueOf((int) R.string.app_name));
                intent.putExtra("android.intent.extra.TEXT", "Download from this Url " + parse);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        return view;
    }

    private StateListDrawable createPulsatingButtonBackground(int color) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(color);
        normalDrawable.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.corner_radius));
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    private void updateCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh", Locale.getDefault());
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat secondFormat = new SimpleDateFormat("ss", Locale.getDefault());
        SimpleDateFormat amPmFormat = new SimpleDateFormat("a", Locale.getDefault());
        String currentHour = hourFormat.format(calendar.getTime());
        String currentMinute = minuteFormat.format(calendar.getTime());
        String currentSecond = secondFormat.format(calendar.getTime());
        String amPm = amPmFormat.format(calendar.getTime());

        hourTextView.setText(currentHour + ":" + currentMinute);
        secondTextView.setText(currentSecond + " " + amPm);
    }

    public void showDialog(Activity activity) {
        final Dialog dialog2 = new Dialog(activity);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.offer);
        dialog2.findViewById(R.id.pass).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Intent intent = new Intent(requireContext(), Operator.class);
                intent.putExtra("type", "internet");
                intent.putExtra("drive", "drive");
                intent.putExtra("type3", "64");
                requireContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        dialog2.findViewById(R.id.pinm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Intent intent = new Intent(requireContext(), Operator.class);
                intent.putExtra("type", "internet");
                intent.putExtra("type3", "16384");
                requireContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        dialog2.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        edit.putString(str, str2);
        edit.commit();
    }


    public void balance() {
        dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress);
        dialog.show();
        String str = getPref(ImagesContract.URL, requireContext()) + "/apiapp/";
        url = str;
        url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + url;
        url = str2;

        number = getPref("phone", requireContext());
        pwd = getPref("pass", requireContext());
        token = getPref("token", requireContext());
        device = getPref("device", requireContext());

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getBaseContext());
        String urlMain = url + TAG_Balance;
        Map<String, String> paramsMain = new Hashtable<>();
        paramsMain.put("type", "main");
        paramsMain.put("token", token);
        paramsMain.put("username", number);
        paramsMain.put("password", pwd);
        paramsMain.put("deviceid", device);
        StringRequest requestMain = new StringRequest(Request.Method.POST, urlMain, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    mainData = jsonResponse.getString("message");

                    profile_linear.setVisibility(View.VISIBLE);
                    balance_show.setVisibility(View.GONE);
                    mainTv.setText(mainData);

                    new Timer(false).schedule(new TimerTask() {
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    balance_show.setVisibility(View.VISIBLE);
                                    bank_lay.setVisibility(View.GONE);
                                    drive_lay.setVisibility(View.GONE);
                                    profile_linear.setVisibility(View.GONE);
                                    mainTv.setText("0.00");
                                    drive_tv.setText("0.00");
                                    bank_tv.setText("0.00");
                                }
                            });
                        }
                    }, 10000);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsMain;
            }
        };
        requestQueue.add(requestMain);


        String urlDrive = url + TAG_Balance;
        Map<String, String> paramsDrive = new Hashtable<>();
        paramsDrive.put("type", "drive");
        paramsDrive.put("token", token);
        paramsDrive.put("username", number);
        paramsDrive.put("password", pwd);
        paramsDrive.put("deviceid", device);
        StringRequest requestDrive = new StringRequest(Request.Method.POST, urlDrive, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    drivedata = jsonResponse.getString("message");
                    drive_tv.setText(drivedata);
                    drive_lay.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsDrive;
            }
        };
        requestQueue.add(requestDrive);

        String urlBank = url + TAG_Balance;
        Map<String, String> paramsBank = new Hashtable<>();
        paramsBank.put("type", "bank");
        paramsBank.put("token", token);
        paramsBank.put("username", number);
        paramsBank.put("password", pwd);
        paramsBank.put("deviceid", device);
        StringRequest requestBank = new StringRequest(Request.Method.POST, urlBank, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    bankdata = jsonResponse.getString("message");
                    bank_tv.setText(bankdata);
                    bank_lay.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsBank;
            }
        };
        requestQueue.add(requestBank);
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2 && i2 == -1) {
            String[] strArr = {"_data"};
            Cursor query = getActivity().getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
            query.moveToFirst();
            @SuppressLint("Range") String string = query.getString(query.getColumnIndex(strArr[0]));
            query.close();
            BitmapFactory.decodeFile(string);
            File file = new File(string);
            if (file.exists()) {
                prof.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                SavePreferences("profilepic", string);
            }
        }
        if (i == REQUEST_CHECK_SETTINGS) {
            if (i2 == RESULT_OK) {
                startLocationUpdates();
            } else {
                Toast.makeText(getActivity(), "Location services not enabled", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void item_list() {
        try {
            Dialog dialog2 = new Dialog(requireContext());
            dialog = dialog2;
            dialog2.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_progress);
            dialog.show();
            String str = getPref("url", requireContext()) + "/apiapp/";
            url = str;
            url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
            String str2 = "https://" + url;
            url = str2;
            Log.d("osman", str2);
            number = getPref("phone", requireContext());
            pwd = getPref("pass", requireContext());
            token = getPref("token", requireContext());
            device = getPref("device", requireContext());
            StringRequest r1 = new StringRequest(1, url + "/role?token=" + URLEncoder.encode(token) + "&deviceid=" + URLEncoder.encode(device), new Response.Listener<String>() {
                public void onResponse(String str) {
                    FinalJSonObject = str;
                    new ItemParseJSonDataClass(getActivity()).execute(new Void[0]);
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    dialog.dismiss();
                    Toast.makeText(getActivity().getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                public Map<String, String> getParams() throws AuthFailureError {
                    Hashtable hashtable = new Hashtable();
                    hashtable.put("goto", "ok");
                    return hashtable;
                }
            };
            r1.setRetryPolicy(new DefaultRetryPolicy() {
                public int getCurrentRetryCount() {
                    return 50000;
                }

                public int getCurrentTimeout() {
                    return 50000;
                }

                public void retry(VolleyError volleyError) throws VolleyError {
                }
            });
            Volley.newRequestQueue(requireContext()).add(r1);


            Json_Url = getPref23("fourth", requireContext().getApplicationContext());
            GetData getData = new GetData();
            getData.execute();
            list = new ArrayList<>();
            list2 = new ArrayList<>();
            list3 = new ArrayList<>();
            list4 = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            thirdRecyclerView.setLayoutManager(layoutManager2);
            newOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newOfferintent = new Intent(requireContext(), AllSliderActivity.class);
                    startActivity(newOfferintent);
                }
            });
            lottieAnimationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gmLink.equals("")) {
                        Toast.makeText(requireContext(), "এখানে কোন ঠিকানা দেওয়া হয়নি", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(requireContext(), WebActivity.class);
                        intent.putExtra("uelData", gmLink);
                        startActivity(intent);
                    }
                }
            });

        } catch (IllegalStateException e) {

        }
    }

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ItemParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(FinalJSonObject);
                jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = jsonarray.getJSONObject(i);
                    if (i == 0) {
                        jSONObject2.getString("message");
                        final String string = jSONObject2.getString("level");
                        final String string2 = jSONObject2.getString("notice");
                        whatsapp = jSONObject2.getString("whatsapp");
                        telegram = jSONObject2.getString("telegram");
                        youtube = jSONObject2.getString("youtube");
                        shop = jSONObject2.getString("shop");
                        img1 = jSONObject2.getString("img1");
                        img2 = jSONObject2.getString("img2");
                        img3 = jSONObject2.getString("img3");
                        img4 = jSONObject2.getString("img4");
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
//                                slide(img1, img2, img3, img4);
                                String str = string2;
                                if (str != null && !str.isEmpty()) {
                                    noticetv = string2;
                                    showAlert(noticetv, noticeImg);
                                }
                                setTextInTextViewl(string);
                                SavePreferences("level", string);
                            }
                        });
                    }
                    String string3 = jSONObject2.getString("act");
                    if (!string3.toString().toLowerCase().equals("addres") && !string3.toString().toLowerCase().equals("transfer") && !string3.toString().toLowerCase().equals("make") && !string3.toString().toLowerCase().equals("myres")) {
                        hashMap.put("service", jSONObject2.getString("service"));
                        hashMap.put("name", jSONObject2.getString("name"));
                        hashMap.put("img", jSONObject2.getString("img"));
                        hashMap.put("act", jSONObject2.getString("act"));
                        arraylist.add(hashMap);
                    }
                }
                return null;
            } catch (NullPointerException | JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            /*ExpandableHeightGridView expandableHeightGridView = (ExpandableHeightGridView) view.findViewById(R.id.atachviewx);
            expandableHeightGridView.setExpanded(true);
            adapter = new service_adafter(requireContext(), arraylist);
            expandableHeightGridView.setAdapter(adapter);*/
            dialog.dismiss();
        }
    }

    public void showAlert(String noticetv, String noticeImg) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.popup_notice, null);
        TextView noticeTextView = customView.findViewById(R.id.main_tv);
        ImageView notIview = customView.findViewById(R.id.Notice_Img);
        noticeTextView.setText(noticetv);
        try {
            Glide.with(this).load(noticeImg).into(notIview);
        } catch (NullPointerException e) {

        }
        PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(requireView(), Gravity.CENTER, 0, 0);
        ImageButton closeButton = customView.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void setTextInTextViewl(String str) {
        leve.setText(str);
    }

    private void calculateAndDisplayPrayerTimes() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NamazTimerPreferences", getActivity().MODE_PRIVATE);
        String savedCalcMethod = sharedPreferences.getString("selectedCalcMethod", null);
        String savedMadhab = sharedPreferences.getString("selectedMadhab", null);
        String selectedLatitude = sharedPreferences.getString("selectedLatitude", null);
        String selectedLongitude = sharedPreferences.getString("selectedLongitude", null);
        double latitude = 23.7104; // default Dhaka
        double longitude = 90.40744; // default Dhaka
        try {
            if (selectedLatitude != null && selectedLongitude != null) {
                latitude = Double.parseDouble(selectedLatitude);
                longitude = Double.parseDouble(selectedLongitude);
            }
        } catch (NumberFormatException e) {
        }

        final Coordinates coordinates = new Coordinates(latitude, longitude);
        final DateComponents dateComponents = DateComponents.from(new Date());
        //CalculationParameters calculationMethod = CalculationMethod.KARACHI.getParameters();

        CalculationParameters calculationMethod = null;
        if (savedCalcMethod == null || savedCalcMethod.isEmpty()) {
            calculationMethod = CalculationMethod.KARACHI.getParameters();
        } else {
            if (savedCalcMethod.equals("ইউনিভার্সিটি অফ ইসলামিক স্টাডিস, করাচী")) {
                calculationMethod = CalculationMethod.KARACHI.getParameters();
            } else if (savedCalcMethod.equals("ইসলামিক সোসাইটি অফ নর্থ আমেরিকা")) {
                calculationMethod = CalculationMethod.NORTH_AMERICA.getParameters();
            } else if (savedCalcMethod.equals("মুসলিম ওয়ার্ল্ড লীগ")) {
                calculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();
            } else if (savedCalcMethod.equals("উম্মুল কুরা ইউনিভার্সিটি, মক্কা")) {
                calculationMethod = CalculationMethod.UMM_AL_QURA.getParameters();
            } else if (savedCalcMethod.equals("ইজিপ্সিয়ান জেনারেল অথরিটি অফ সার্ভে")) {
                calculationMethod = CalculationMethod.EGYPTIAN.getParameters();
            } else if (savedCalcMethod.equals("ইন্সটিটিউট অফ জিওফিজিক্স, তেহরান ইউনিভার্সিটি")) {
                calculationMethod = CalculationMethod.QATAR.getParameters();
            }
        }

        if (savedMadhab == null || savedMadhab.isEmpty()) {
            calculationMethod.madhab = Madhab.HANAFI;
        } else {
            if (savedMadhab.equals("হানাফি")) {
                calculationMethod.madhab = Madhab.HANAFI;
            } else if (savedMadhab.equals("শাফেয়ী, হাম্বলী, মালিকী")) {
                calculationMethod.madhab = Madhab.SHAFI;
            }
        }

        calculationMethod.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE;
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm", new Locale("en"));
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        CalculationParameters calculationParams = calculationMethod;
        PrayerTimes prayerTimes = new PrayerTimes(coordinates, dateComponents, calculationParams);

        String fajrTime = formatter.format(prayerTimes.fajr);
        String dhuhrTime = formatter.format(prayerTimes.dhuhr);
        String asrTimeString = formatter.format(prayerTimes.asr);
        String maghribTime = formatter.format(prayerTimes.maghrib);
        String ishaTime = formatter.format(prayerTimes.isha);
        String sunriseTime = formatter.format(prayerTimes.sunrise);

        // Tahazzud
        String tahazzudStartTime = calculateOneThirdTimes(maghribTime, fajrTime);
        Calendar tahazzudEnd = Calendar.getInstance();
        tahazzudEnd.setTime(prayerTimes.fajr);
        tahazzudEnd.add(Calendar.MINUTE, -1);
        String tahazzudEndTime = formatter.format(tahazzudEnd.getTime());

        /*String tahazzudTimeRange = tahazzudStartTime + " - " + tahazzudEndTime;
        textTahajjudTime.setText(tahazzudTimeRange.toLowerCase());*/

        // FAJR
        Calendar fajrStartTime = Calendar.getInstance();
        fajrStartTime.setTime(prayerTimes.sunrise);
        fajrStartTime.add(Calendar.MINUTE, -5);
        String fajrEndTime = formatter.format(fajrStartTime.getTime());

        fajrTimeRange = fajrTime;
        textFazarTime.setText(fajrTimeRange.toLowerCase());

        // DHUHR
        Calendar dhuhurTime = Calendar.getInstance();
        dhuhurTime.setTime(prayerTimes.asr);
        dhuhurTime.add(Calendar.MINUTE, -1);
        String DhuhurEndTime = formatter.format(dhuhurTime.getTime());

        dhuhrTimeRange = dhuhrTime;
        textJaharTime.setText(dhuhrTimeRange.toLowerCase());

        // ASR
        Calendar asrTime = Calendar.getInstance();
        asrTime.setTime(prayerTimes.maghrib);
        asrTime.add(Calendar.MINUTE, -15);
        String AsrEndTime = formatter.format(asrTime.getTime());

        asrTimeRange = asrTimeString;
        textAshrTime.setText(asrTimeRange.toLowerCase());

        // MAGHRIB
        Calendar maghribEnd = Calendar.getInstance();
        maghribEnd.setTime(prayerTimes.isha);
        maghribEnd.add(Calendar.MINUTE, -1);
        String maghribStartTime = maghribTime;
        String maghribEndTime = formatter.format(maghribEnd.getTime());

        maghribTimeRange = maghribStartTime;
        textMagribhTime.setText(maghribTimeRange.toLowerCase());

        // ISHA
        Calendar ishaTimeEnd = Calendar.getInstance();
        ishaTimeEnd.setTime(prayerTimes.isha);
        ishaTimeEnd.add(Calendar.HOUR, 4);
        String IshaEndTime = formatter.format(ishaTimeEnd.getTime());

        ishaTimeRange = ishaTime;
        textEshaTime.setText(ishaTimeRange.toLowerCase());

        // Sunrise and Sunset
        /*textSunriseTime.setText(sunriseTime.toLowerCase());
        textSunsetTime.setText(maghribTime.toLowerCase());*/

        // Ishrak Time
        Calendar ishrakTime = Calendar.getInstance();
        ishrakTime.setTime(prayerTimes.sunrise);
        ishrakTime.add(Calendar.MINUTE, 20);
        String IshrakStartTime = formatter.format(ishrakTime.getTime());
        Calendar ishrakEnd = Calendar.getInstance();
        ishrakEnd.setTime(ishrakTime.getTime());
        ishrakEnd.add(Calendar.HOUR, 2);
        String IshrakEndTime = formatter.format(ishrakEnd.getTime());
        String ishrakPrayerTime = IshrakStartTime + " - " + IshrakEndTime;
//        textIshrakTime.setText(ishrakPrayerTime.toLowerCase());

        sehriTv.setText(fajrTime);
        iftarTv.setText(maghribTime);

    }

    public static String calculateOneThirdTimes(String maghribTime, String fajrTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa");
        try {
            Date maghrib = sdf.parse(maghribTime);
            Date fajr = sdf.parse(fajrTime);
            long difference = fajr.getTime() - maghrib.getTime();
            if (difference < 0) {
                difference += 24 * 60 * 60 * 1000;
            }
            long thirdOfTheDifference = difference / 3;
            Date lastThirdOfTheNight = new Date(fajr.getTime() - thirdOfTheDifference);
            String lastThirdOfTheNightFormatted = sdf.format(lastThirdOfTheNight);
            return lastThirdOfTheNightFormatted;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void loadLocationFromPreferences() {
        // Load Location from preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NamazTimerPreferences", getActivity().MODE_PRIVATE);
        String selectedDistrict = sharedPreferences.getString("selectedDistrict", null);
        if (selectedDistrict == null || selectedDistrict.isEmpty()) {
            textCurrentLocation.setText("ঢাকা");
        } else {
            textCurrentLocation.setText(selectedDistrict);
        }
    }

    public class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(Json_Url);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    int data = inputStreamReader.read();
                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("HomeNotice");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    noticeImg = jsonObject1.getString("NoticeImg");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            list.clear();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("FirstSlider");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    SliderOne sliderOne = new SliderOne();
                    sliderOne.setSliderText(jsonObject1.getString("sliderText"));
                    sliderOne.setSliderUrl(jsonObject1.getString("sliderUrl"));
                    sliderOne.setSliderimg(jsonObject1.getString("sliderimg"));
                    list.add(sliderOne);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            SliderView sliderView = view.findViewById(R.id.imageSlider);
            slideradapter = new SliderAdapter(getActivity(), list);
            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
            sliderView.setSliderAdapter(slideradapter);
            sliderView.setScrollTimeInSec(5);
            sliderView.setAutoCycle(true);
            sliderView.startAutoCycle();

            list2.clear();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("SecondSlider");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    SliderData sliderData = new SliderData();
                    sliderData.setSliderText(jsonObject1.getString("sliderText"));
                    sliderData.setSliderUrl(jsonObject1.getString("sliderUrl"));
                    sliderData.setSliderimg(jsonObject1.getString("sliderimg"));
                    sliderData.setOnly(jsonObject1.getString("Only"));
                    list2.add(sliderData);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            myadapter = new SliderAdapterTwo(getActivity(), list2);
            recyclerView.setAdapter(myadapter);
            myadapter.notifyDataSetChanged();
            myadapter.getFilter().filter("A");

            list3.clear();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("ThirdSlider");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    SliderThirdModel sliderThirdModel = new SliderThirdModel();
                    sliderThirdModel.setThirdSliderText(jsonObject1.getString("ThirdSliderText"));
                    sliderThirdModel.setThirdSliderUrl(jsonObject1.getString("ThirdSliderUrl"));
                    sliderThirdModel.setThirdSliderimg(jsonObject1.getString("ThirdSliderimg"));
                    list3.add(sliderThirdModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            sliderAdapterThird = new SliderAdapterThird(getActivity(), list3);
            thirdRecyclerView.setAdapter(sliderAdapterThird);
            sliderAdapterThird.notifyDataSetChanged();

            list4.clear();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("FourthSlider");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    SliderOne sliderOne = new SliderOne();
                    sliderOne.setSliderText(jsonObject1.getString("sliderText"));
                    sliderOne.setSliderUrl(jsonObject1.getString("sliderUrl"));
                    sliderOne.setSliderimg(jsonObject1.getString("sliderimg"));
                    list4.add(sliderOne);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            SliderView fourthslider = view.findViewById(R.id.fourthSlider);
            fourthAdapter = new FourthAdapter(getActivity(), list4);
            fourthslider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
            fourthslider.setSliderAdapter(fourthAdapter);
            fourthslider.setIndicatorAnimation(IndicatorAnimationType.FILL);
            fourthslider.setSliderTransformAnimation(SliderAnimations.CUBEOUTSCALINGTRANSFORMATION);
            fourthslider.setScrollTimeInSec(3);
            fourthslider.setAutoCycle(true);
            fourthslider.startAutoCycle();

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Gamesfiles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    anim = jsonObject1.getString("Anim");
                    gmLink = jsonObject1.getString("gmLink");
                    if (anim != null) {
                        lottieAnimationView.setAnimationFromUrl(anim);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static String getPref23(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

    private void setButtonAnimation(Button balanc) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(balanc, "translationX", -dpToPx(8), dpToPx(8), -dpToPx(8));
        translationX.setDuration(1000);
        translationX.setRepeatMode(ValueAnimator.REVERSE);
        translationX.setRepeatCount(ValueAnimator.INFINITE);
        translationX.start();
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        if (String.valueOf(latitude) != null && !String.valueOf(longitude).isEmpty()) {
                            getAddressFromLocation(latitude, longitude);
                        }

                        stopLocationUpdates();
                    }
                }
            };
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            Toast.makeText(getActivity(), "Location permission not granted", Toast.LENGTH_SHORT).show();

        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        new AsyncTask<Double, Void, String>() {
            @Override
            protected String doInBackground(Double... params) {
                double lat = params[0];
                double lon = params[1];
                String apiKey = "0a64bf32eb9d4135b06966be53b8a062";
                String urlString = "https://api.opencagedata.com/geocode/v1/json?key=" + apiKey + "&q=" + lat + "+" + lon;
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        InputStream in = urlConnection.getInputStream();
                        Scanner scanner = new Scanner(in);
                        scanner.useDelimiter("\\A");
                        boolean hasInput = scanner.hasNext();
                        if (hasInput) {
                            return scanner.next();
                        } else {
                            return null;
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    parseOpenCageResponse(latitude, longitude, response);
                } else {
                    // Handle the case when no response is available
                    Toast.makeText(getActivity(), "No address found for the location", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(latitude, longitude);
    }

    private void parseOpenCageResponse(double latitude, double longitude, String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONArray resultsArray = json.getJSONArray("results");
            if (resultsArray.length() > 0) {
                JSONObject firstResult = resultsArray.getJSONObject(0);
                JSONObject componentsObject = firstResult.getJSONObject("components");
                countyValue = componentsObject.getString("county");
                if (countyValue != null && !countyValue.isEmpty() && String.valueOf(latitude) != null) {
                    getWeatherDetails(String.valueOf(latitude), String.valueOf(longitude));
                    stopLocationUpdates();
                }
            } else {
                Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(getActivity(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. You can now request location updates.
                startLocationUpdates();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    private void getWeatherDetails(String latitude, String longitude) {
        try {
            String tempUrl = urln + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + appid;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Parse the JSON response and update the UI
                    String output = parseWeatherResponse(response);
                    weatherTVrt.setText(output);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (NullPointerException e) {

        }
    }

    private String parseWeatherResponse(String response) {
        // Parse the JSON response and extract relevant information
        String output = "";
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
            String description = jsonObjectWeather.getString("description");
            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
            double temp = jsonObjectMain.getDouble("temp") - 273.15;
            double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
            float pressure = jsonObjectMain.getInt("pressure");
            int humidity = jsonObjectMain.getInt("humidity");
            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
            String wind = jsonObjectWind.getString("speed");
            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
            String clouds = jsonObjectClouds.getString("all");
            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
            String countryName = jsonObjectSys.getString("country");
            String cityName = jsonResponse.getString("name");

            output += " বর্তমান আবহাওয়া স্থান: " + countyValue + "\n তাপমাত্রা: " + df.format(temp) + " °C" + "\n অনুভব হচ্ছে: " + df.format(feelsLike) + " °C" + "\n আর্দ্রতা: " + humidity + "%" + "\n বর্ণনা: " + description + "\n বাতাসের গতি: " + wind + "m/s (meters per second)" + "\n মেঘলা: " + clouds + "%" + "\n চাপ: " + pressure + " hPa";
            weatherPg.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    private int loadColor() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FT", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("AppColorCode", getResources().getColor(R.color.primary));
        return selectedColor;
    }

}