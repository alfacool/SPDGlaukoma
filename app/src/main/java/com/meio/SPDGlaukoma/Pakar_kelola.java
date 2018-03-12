package com.meio.SPDGlaukoma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Pakar_kelola extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pakar_kelola);
        webview = (WebView) findViewById(R.id.view1);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(getString(R.string.url_kelola));

    }

    public class myWebClient extends WebViewClient
    {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }



    public void exit(View view) {

        Intent intent = new Intent(Pakar_kelola.this, MainActivity.class);
        startActivity(intent);
        Pakar_kelola.this.finish();

    }
}
