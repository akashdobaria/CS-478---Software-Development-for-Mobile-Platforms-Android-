package com.cs478.project3app2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/******************************************************************************
 *   Custom adaptor for the gridview that is used in the MainActivity.java   *
 ******************************************************************************/

public class GalleryAdapter extends BaseAdapter {

    //declare variables needed to inflate the view, fetch details of each cell of gridview and the context for the adaptor
    private Context mContext;
    private List<Integer> dataSource;
    private LayoutInflater mInflater;

    //constructor
    public GalleryAdapter(Context c, List<Integer> ids) {
        mContext = c;
        dataSource = ids;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //get view for each cell
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //viewholder to avoid repetitive calls to the findViewById()
        //reference https://developer.android.com/training/improving-layouts/smooth-scrolling.html
        ViewHolder holder;

        if(convertView == null) {

            //inflate the view
            convertView = mInflater.inflate(R.layout.landmark_view, parent, false);

            //populate the ViewHolder and store it inside the layout.
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.landmark_thumbnail);
            convertView.setTag(holder);
        }
        else{
            // get the holder if convertview is not empty
            holder = (ViewHolder) convertView.getTag();
        }

        //initialize imageview needed to show on gridview
        ImageView thumbnailImageView = holder.thumbnailImageView;

        //get image id and set the image
        final Integer landmark = (Integer) getItem(position);
        thumbnailImageView.setImageResource(landmark);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
    }
}
