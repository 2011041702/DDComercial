package com.example.chris.ddcomercial.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.chris.ddcomercial.R;

/**
 * Created by Christian on 02/07/2016.
 */
public class FragmentAdministrativo extends Fragment {

    private static String DIRECCION = "http://192.168.160.220:8080/moviles/index.php";
    private WebView web_mail;
    public FragmentAdministrativo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragmento_administrativo, container, false);

        web_mail = (WebView)v.findViewById(R.id.web_mail);
        web_mail.setWebViewClient(new MyWebViewClient());
        web_mail.loadUrl(DIRECCION);

        return v;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_mail.canGoBack()) {
            web_mail.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }*/





}
