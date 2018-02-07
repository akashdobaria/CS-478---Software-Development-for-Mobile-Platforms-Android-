package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/******************************************************************************
 *   Custom adaptor for the MainActivity.java that is used for the gridview   *
 ******************************************************************************/

public class GalleryAdapter extends BaseAdapter {

    //declare variables needed to inflate the view, fetch details of each cell of gridview and the context for the adaptor
    private Context mContext;
    private List<CarDetails> dataSource;
    private LayoutInflater mInflater;

    //constructor
    public GalleryAdapter(Context c, List<CarDetails> ids) {
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
            convertView = mInflater.inflate(R.layout.list_cars, parent, false);

            //populate the ViewHolder and store it inside the layout.
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.list_cars_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.list_cars_company_name);
            convertView.setTag(holder);
        }
        else{
            // get the holder if convertview is not empty
            holder = (ViewHolder) convertView.getTag();
        }

        //initialize textview and imageview needed to show on gridview
        TextView titleTextView = holder.titleTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        //get details of car object and set the image and company name
        final CarDetails car = (CarDetails) getItem(position);
        titleTextView.setText(car.company_name);
        thumbnailImageView.setImageResource(car.image_path);

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public ImageView thumbnailImageView;
    }
}
