package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * class to help javascript show aler messages as toast
 */

public class WebInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    void ebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
