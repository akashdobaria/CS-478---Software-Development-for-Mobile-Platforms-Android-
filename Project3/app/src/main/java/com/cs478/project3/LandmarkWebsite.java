package com.cs478.project3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LandmarkWebsite extends Fragment {

    private WebView mWebView = null;
    public int mCurrIdx = -1;
    private int webArrLength;

    //getter for index of the webpage shown
    int getShownIndex() {
        return mCurrIdx;
    }

    //Show the website at given index.
    public void showWebViewIndex(int newIndex) {

        //if index is out of the bound for array, return
        if (newIndex < 0 || newIndex >= webArrLength)
            return;

        //update the index and load the url accordingly
        mCurrIdx = newIndex;
        mWebView.loadUrl(MainActivity.weblinkArray[mCurrIdx]);
    }

    //setRetainInstance() to true to retain the fragment during configuration change.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the view and get the webview
        View view = inflater.inflate(R.layout.landmark_webview, container, false);
        ((WebView)view.findViewById(R.id.landmarkWebview)).setWebViewClient(new WebViewClient());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //store the webview, length of website link array when activity is created
        mWebView = (WebView) getActivity().findViewById(R.id.landmarkWebview);
        webArrLength = MainActivity.weblinkArray.length;
        showWebViewIndex(mCurrIdx);
    }
}
