package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/******************************************************************************
 *   Custom adaptor for the Dealers.java that is used for the listview        *
 ******************************************************************************/

public class DealerAdapter extends BaseAdapter {

    //declare variables needed to inflate the view, fetch details of each row of listview and the context for the adaptor
    private Context mContext;
    private List<CarDetails.Dealers_details> dataSource;
    private LayoutInflater mInflater;

    //constructor
    public DealerAdapter(Context c, List<CarDetails.Dealers_details> ids) {
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

    //get view for each row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //viewholder to avoid repetitive calls to the findViewById()
        //reference https://developer.android.com/training/improving-layouts/smooth-scrolling.html
        DealerAdapter.ViewHolder holder;

        if(convertView == null) {

            //inflate the view
            convertView = mInflater.inflate(R.layout.list_dealers, parent, false);

            //populate the ViewHolder and store it inside the layout.
            holder = new DealerAdapter.ViewHolder();
            holder.dealerName = (TextView) convertView.findViewById(R.id.tv_dealerName);
            holder.dealerAddress = (TextView) convertView.findViewById(R.id.tv_dealerAddress);
            holder.dealerNumber = (TextView) convertView.findViewById(R.id.tv_dealerNumber);
            convertView.setTag(holder);
        }
        else{
            // get the holder if convertview is not empty
            holder = (DealerAdapter.ViewHolder) convertView.getTag();
        }

        //initialize all textviews needed to show on listview
        TextView dealer_name = holder.dealerName;
        TextView dealer_address = holder.dealerAddress;
        TextView dealer_number = holder.dealerNumber;


        //get details of dealer object and set all textviews
        final CarDetails.Dealers_details dealer = (CarDetails.Dealers_details) getItem(position);
        dealer_name.setText(dealer.name);
        dealer_address.setText(dealer.address);
        dealer_number.setText("Call:  " + dealer.number);

        /******************************************************************************
         *        following listeners are implemented just for the curiosity          *
         ******************************************************************************/

        //onClick listener to open location of the dealer on map when user taps on dealer's name
        dealer_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent location = new Intent(Intent.ACTION_VIEW);
                String address_request = "http://maps.google.co.in/maps?q=" + dealer.name + " " +dealer.address;
                location.setData(Uri.parse(address_request));
                mContext.startActivity(location);
            }
        });

        //onClick listener to open location of the dealer on map when user taps on dealer's address
        dealer_address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent location = new Intent(Intent.ACTION_VIEW);
                String address_request = "http://maps.google.co.in/maps?q=" + dealer.name + " " +dealer.address;
                location.setData(Uri.parse(address_request));
                mContext.startActivity(location);
            }
        });

        //onClick listener to dial the number of the dealer when user taps on dealer's number
        dealer_number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent location = new Intent(Intent.ACTION_DIAL);
                String address_request = "tel:" + dealer.number;
                location.setData(Uri.parse(address_request));
                location.putExtra("finishActivityOnSaveCompleted", true);
                mContext.startActivity(location);
            }
        });

        return convertView;

    }

    private static class ViewHolder {
        public TextView dealerName;
        public TextView dealerAddress;
        public TextView dealerNumber;
    }
}
