package com.example.webview2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView wv;  //https://seerviks.000webhostapp.com/

    String url;

    {
        url = "https://seerviks.000webhostapp.com/";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv=(WebView)findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.addJavascriptInterface(new WebAppInterface(this), "Android");
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.setWebViewClient(new myWebClient());

        wv.loadUrl(url);

    }
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if(Uri.parse(url).getHost().equals(url)){
                return false;
            }
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));

           // view.loadUrl(url);
            return true;

        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if(wv.canGoBack()){
                        wv.goBack();
                    }
                    else
                    {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode,event);
    }
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
