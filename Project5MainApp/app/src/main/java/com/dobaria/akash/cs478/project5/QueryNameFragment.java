package com.dobaria.akash.cs478.project5;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class QueryNameFragment extends ListFragment {

    private ListSelectionListener mListener = null;
    public int mCurrIdx = -1;

    // interface to communicate between fragments when user clicks on any item from the list
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    //lister for item list view item click
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        //if current index is not same as item clicked, update it.
        if (mCurrIdx != pos) {
            mCurrIdx = pos;
            mListener.onListSelection(pos);
        }
        l.setItemChecked(mCurrIdx, true);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        //retrieve ListSelectionListener() implemented in the activity. If not implemented, throw an error.
        try {
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ " must implement OnArticleSelectedListener");
        }
    }

    // setRetainInstance() to true to retain fragment during configuration change.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        //set adapter view to generate list view for titles
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.querylist, MainActivity.queries));

        //set single choice mode to allow user to select only 1 item.
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //if current index is not -1, show item as checked.
        if (mCurrIdx != -1) {
            getListView().setItemChecked(mCurrIdx, true);
        }
    }
}
