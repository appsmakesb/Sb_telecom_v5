package com.v5foradnroid.userapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;

public class  web extends Activity {
    Dialog dialog;
    /* Foysal Tech && Ict Foysal */
    public WebView mWeb;
    String source;
    String url;
    WebView webView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.webl);
        this.source = getIntent().getStringExtra(FirebaseAnalytics.Param.SOURCE);
        this.webView = (WebView) findViewById(R.id.webView);
        this.mWeb = (WebView) findViewById(R.id.webView);
        String pref = Welcome.getPref("phone", getApplicationContext());
        Welcome.getPref("pass", getApplicationContext());
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.custom_progress);
        this.dialog.show();
        String str = Welcome.getPref(ImagesContract.URL, getApplicationContext()) + "/Paymentgatway/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        this.url = "https://" + this.url + "index/" + pref + "/" + this.source;
        this.mWeb.setWebViewClient(new WebViewClient());
        WebSettings settings = this.mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        this.mWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWeb.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        this.mWeb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (URLUtil.isNetworkUrl(str)) {
                    return false;
                }
                if (!web.this.appInstalledOrNot(str)) {
                    return true;
                }
                web.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            }

            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                web.this.dialog.show();
            }

            public void onPageFinished(WebView webView, String str) {
                web.this.mWeb.loadUrl("javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                super.onPageFinished(webView, str);
                web.this.dialog.dismiss();
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                web.this.loadErrorPage(webView);
                web.this.dialog.dismiss();
            }
        });
        this.mWeb.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                web.this.dialog.dismiss();
            }
        });
        HashMap hashMap = new HashMap();
        hashMap.put("user", pref);
        hashMap.put(FirebaseAnalytics.Param.SOURCE, this.source);
        this.mWeb.loadUrl(this.url, hashMap);
    }

    public void processHTML(String str) {
        Log.e("result", str);
    }

    public void onBackPressed() {
        if (this.mWeb.canGoBack()) {
            this.mWeb.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /* Foysal Tech && Ict Foysal */
    public boolean appInstalledOrNot(String str) {
        try {
            getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public void loadErrorPage(WebView webView2) {
        if (webView2 != null) {
            webView2.loadUrl("about:blank");
            webView2.loadDataWithBaseURL((String) null, "<html> <style type=\"text/css\">\n        html\n        {\n            width: 100%;\n            height: 100%;\n        }\n\n        body\n        {\n            display: flex;\n            justify-content: center;\n            align-items: center;\n        }\n    </style><body><center><strong> Something Went Wrong, Please Try Again</strong></center></body></html>", "text/html", "UTF-8", (String) null);
            webView2.invalidate();
        }
    }

    class MyJavaScriptInterface {
        private Context ctx;

        MyJavaScriptInterface(Context context) {
            this.ctx = context;
        }

        @JavascriptInterface
        public void showHTML(String str) {
            if (str != null && str.equals("<html><head></head><body>no_gateway_active</body></html>")) {
                web.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(web.this, "Payment gateway off try by trnx id:", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(web.this, makepay.class);
                        intent.putExtra(FirebaseAnalytics.Param.SOURCE, web.this.source);
                        web.this.startActivity(intent);
                        web.this.finish();
                    }
                });
            }
            if (str != null && str.equals("<html><head></head><body>balnce_added_success_full</body></html>")) {
                web.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(web.this, "Balance added Successful :", Toast.LENGTH_LONG).show();
                        web.this.startActivity(new Intent(web.this.getApplicationContext(), Welcome.class));
                        web.this.finish();
                    }
                });
            }
            Log.d("WebView", "your current url when webpage loading.. finish" + str);
        }
    }
}
