package com.dobaria.akash.cs478.project5;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QueryResultFragment extends Fragment {

    private TextView mTextView = null;
    public int mCurrIdx = -1;
    private int queryResultSize;

    //getter for current index shown
    public int getShownIndex() {
            return mCurrIdx;
    }


    //function to show the query result
    public void showResultAt(int index) {
        if (index < 0 || index >= queryResultSize)
            return;

        //update the index and set textview
        mCurrIdx = index;
        mTextView.setText(MainActivity.queryResults.get(mCurrIdx));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the view and get the textview
        View view = inflater.inflate(R.layout.query_result, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //store the textview, length of results when activity is created
        mTextView = (TextView) getActivity().findViewById(R.id.textView_QueryResult);
        queryResultSize = MainActivity.queryResults.size();
        showResultAt(mCurrIdx);
    }


}
