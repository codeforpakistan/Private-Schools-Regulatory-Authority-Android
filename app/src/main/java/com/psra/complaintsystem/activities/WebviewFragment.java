package com.psra.complaintsystem.activities;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.psra.complaintsystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebviewFragment extends Fragment {

    WebView webView;
    ProgressDialog progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.psra.complaintsystem.R.layout.fragment_webview, container, false);

        webView = view.findViewById(com.psra.complaintsystem.R.id.simpleWebView);
// specify the url of the web page in loadUrl function
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Please wait...");
        progressBar.setCanceledOnTouchOutside(false);
        if (savedInstanceState == null) {
            webView.loadUrl("https://psra.gkp.pk/");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setPadding(0, 0, 0, 0);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webView.getSettings().setDomStorageEnabled(true);
            webView.setWebViewClient(new MyBrowser());
        }

        return view;

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /*public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You are proceeding to an unsafe website");
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }*/

        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String mensaje;
            switch (error.getPrimaryError()) {
                case SslError.SSL_DATE_INVALID:
                    mensaje = "SSL date invalid";
                    break;
                case SslError.SSL_EXPIRED:
                    mensaje = "SSL expired";
                    break;
                case SslError.SSL_IDMISMATCH:
                    mensaje = "SSL Id mismatch";
                    break;
                case SslError.SSL_INVALID:
                    mensaje = "SSL invalid";
                    break;
                case SslError.SSL_NOTYETVALID:
                    mensaje = "SSL not yet valid";
                    break;
                case SslError.SSL_UNTRUSTED:
                    mensaje = "SSL untrusted";
                    break;
                default:
                    mensaje = String.valueOf(R.string.notification_error_ssl_cert_invalid);
            }


            builder.setTitle("SSL Certificate Error");
            builder.setMessage(mensaje);
            builder.setPositiveButton("Continue anyway", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressBar.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.dismiss();

        }

    }




}

