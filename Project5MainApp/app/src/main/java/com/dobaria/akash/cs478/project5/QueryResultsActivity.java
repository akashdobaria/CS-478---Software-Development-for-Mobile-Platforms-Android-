package com.dobaria.akash.cs478.project5;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class QueryResultsActivity extends AppCompatActivity implements QueryNameFragment.ListSelectionListener{

    private FragmentManager fragmentManager;

    private QueryNameFragment queryNameFragment = new QueryNameFragment();
    private QueryResultFragment queryResultFragment = new QueryResultFragment();

    private static final String TAG_QUERY_FRAGMENT = "Queries";
    private static final String TAG_QUERY_RESULT_FRAGMENT = "QueryResults";

    private Context context = null;

    private FrameLayout frameLayout_Queries;
    private FrameLayout frameLayout_QueryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);

        context = this;

        //get framelayouts
        frameLayout_Queries = (FrameLayout) findViewById(R.id.frame_queries);
        frameLayout_QueryResult = (FrameLayout) findViewById(R.id.frame_queryResults);

        fragmentManager = getFragmentManager();

        FragmentTransaction mTransaction = fragmentManager.beginTransaction();

        //replace the title fragment
        mTransaction.replace(R.id.frame_queries, queryNameFragment, TAG_QUERY_FRAGMENT);
        mTransaction.commit();

        if (queryResultFragment == null) {

            queryResultFragment = new QueryResultFragment();

        }
    }

    //onListSelection() implementation
    @Override
    public void onListSelection(int index) {

        //check if result fragment was not added, create it
        if (queryResultFragment == null || !queryResultFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Add the QuoteFragment to the layout
            fragmentTransaction.replace(R.id.frame_queryResults, queryResultFragment,TAG_QUERY_RESULT_FRAGMENT);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }

        if (queryResultFragment.getShownIndex() != index) {

            // Call result
            queryResultFragment.showResultAt(index);

        }

    }
}
