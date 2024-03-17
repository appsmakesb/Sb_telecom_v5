package com.v5foradnroid.userapp.activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.multidex.MultiDex;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.v5foradnroid.userapp.Welcome;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;

public class MyApplication extends Application {

    String var_twoe;
    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;
    String message = "";
    String bigPicture = "";
    String title = "";
    String link = "";
    String postId = "";
    String uniqueId = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        var_twoe = getPreftwoe("twoe", getApplicationContext());
        initNotification();
    }

    public void initNotification() {
        OneSignal.disablePush(false);
        Log.d(TAG, "OneSignal Notification is enabled");
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        requestConfig();

        OneSignal.setNotificationOpenedHandler(result -> {
            title = result.getNotification().getTitle();
            message = result.getNotification().getBody();
            bigPicture = result.getNotification().getBigPicture();
            Log.d(TAG, title + ", " + message + ", " + bigPicture);
            try {
                JSONObject additionalData = result.getNotification().getAdditionalData();
                JSONObject additionalDataObject = new JSONObject(additionalData.toString());
                String externalLink = additionalDataObject.optString("external_link", "");
                URL url = new URL(externalLink);
                link = url.getHost();
                uniqueId = additionalData.getString("unique_id");
                postId = additionalData.getString("post_id");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getApplicationContext(), Welcome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("unique_id", uniqueId);
            intent.putExtra("post_id", postId);
            intent.putExtra("link", link);
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            intent.putExtra("bigPicture", bigPicture);
            startActivity(intent);
        });
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true);
    }

    private void requestConfig() {

        Volley.newRequestQueue(this).add(new JsonObjectRequest(Request.Method.GET,
                var_twoe + "/api/api.php?one_sig", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String onesignalAppId = response.getString("onesignal_app_id");
                    FirebaseMessaging.getInstance().subscribeToTopic("sbtlc");
                    OneSignal.setAppId(onesignalAppId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY_ERROR", "Error occurred: " + error.getMessage());
            }
        }));

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String getPreftwoe(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

}