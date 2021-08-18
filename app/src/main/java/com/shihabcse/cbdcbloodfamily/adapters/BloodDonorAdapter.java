package com.shihabcse.cbdcbloodfamily.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.activities.SearchBloodDonorConditionActivity;
import com.shihabcse.cbdcbloodfamily.models.BloodDonor;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BloodDonorAdapter extends RecyclerView.Adapter<BloodDonorAdapter.MyViewHolder> implements Filterable {

    Context myContext;
    List<BloodDonor> mData;
    List<BloodDonor> mDataFiltered;

    public BloodDonorAdapter(Context myContext, List<BloodDonor> mData) {
        this.myContext = myContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }


    @NonNull
    @Override
    public BloodDonorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(myContext).inflate(R.layout.model_layout_for_blood_donor, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodDonorAdapter.MyViewHolder holder, int position) {

        //making the whole cardView Animated
        holder.sampleCardView.setAnimation(AnimationUtils.loadAnimation(myContext, R.anim.fade_scale_animation));

        holder.textViewBloodGroup.setText(mDataFiltered.get(position).getBloodGroupPatient());
        holder.textViewBloodDonorName.setText(mDataFiltered.get(position).getBloodDonorName());
        holder.textViewBloodDonorAddress.setText(mDataFiltered.get(position).getBloodDonorAddress());
        holder.textViewDonarNumber.setText(mDataFiltered.get(position).getBloodDonorContactNumber());
        holder.textViewLastDonatioinDate.setText("Last Blood Donation : " + mDataFiltered.get(position).getLastBloodDonationDate());
        holder.textViewBloodDonorStatus.setText("Ready to Donate : " + mDataFiltered.get(position).getBloodDonationStatus());

    }

    @Override
    public int getItemCount() {
        if(mDataFiltered.size() == 0){
            Toast.makeText(myContext, "No Data Found", Toast.LENGTH_LONG).show();
            return 0;
        }else{
            return mDataFiltered.size();
        }
    }

    // filtering items by blood group
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();

                if (key.isEmpty()) {
                    mDataFiltered = mData;

                } else {

                    String[] splitValue = key.split(":");

                    List<BloodDonor> listFiltered = new ArrayList<>();

                    for (BloodDonor row : mData) {

                        if (row.getBloodGroupPatient().toLowerCase().contains(splitValue[0].toLowerCase()) && row.getBloodDonorAddress().toLowerCase().contains(splitValue[1].toLowerCase())) {
                            listFiltered.add(row);
                        }

                    }
                    mDataFiltered = listFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (List<BloodDonor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView sampleCardView;
        TextView textViewBloodGroup;
        TextView textViewBloodDonorName;
        TextView textViewBloodDonorAddress;
        TextView textViewLastDonatioinDate;
        TextView textViewDonarNumber;
        TextView textViewBloodDonorStatus;
        ImageView imageViewPhoneCall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sampleCardView = itemView.findViewById(R.id.cardView_sample_item_blood_donor);
            textViewBloodGroup = itemView.findViewById(R.id.textView_blood_group_blood_donor);
            textViewBloodDonorName = itemView.findViewById(R.id.text_view_blood_donor_name);
            textViewBloodDonorAddress = itemView.findViewById(R.id.textView_blood_donor_address);
            textViewLastDonatioinDate = itemView.findViewById(R.id.text_view_last_donation_date);
            textViewDonarNumber = itemView.findViewById(R.id.text_view_donor_phone_number);
            textViewBloodDonorStatus = itemView.findViewById(R.id.textView_donation_status);
            imageViewPhoneCall = itemView.findViewById(R.id.imageView_call);

            imageViewPhoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getBloodDonorContactNumber();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));
                    myContext.startActivity(callIntent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}