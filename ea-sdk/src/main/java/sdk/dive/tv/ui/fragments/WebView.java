package sdk.dive.tv.ui.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import sdk.dive.tv.R;


public class WebView extends Fragment {

    //Web
    private String url;
    private android.webkit.WebView webView;
    private FrameLayout mCloseButton;
    private ProgressBar loadingBar;

    private OnWebViewInteractionListener mListener;

    public WebView() {
        // Required empty public constructor
    }

    public static WebView newInstance() {
        WebView fragment = new WebView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        Bundle extras = getArguments();
        url = extras.getString(sdk.dive.tv.ui.Utils.URL);

        TextView urlText = (TextView) view.findViewById(R.id.webview_text);

        urlText.setText(url);

        loadingBar = (ProgressBar) view.findViewById(R.id.webview_loading);

        //Webview
        webView = (android.webkit.WebView) view.findViewById(R.id.webview);

        webView.setInitialScale(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportMultipleWindows(true); // This forces ChromeClient enabled.
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSupportZoom(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(android.webkit.WebView view, String title) {
                //getSupportActionBar().setTitle(title);
                // toolbar.setSubtitle(view.getUrl());
            }

            public boolean onJsAlert(WebView view, String url, String message, JsResult jsResult) {
                return true;
            }

        });

        webView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                loadingBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                loadingBar.setVisibility(View.INVISIBLE);
            }
        });

        webView.loadUrl(url);

        mCloseButton = (FrameLayout) view.findViewById(R.id.webview_button_close);

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onWebViewClose();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWebViewInteractionListener) {
            mListener = (OnWebViewInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWebViewInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCloseButton.requestFocus();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnWebViewInteractionListener {

        void onWebViewClose();
    }
}
