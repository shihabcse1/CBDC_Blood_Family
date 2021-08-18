package com.shihabcse.cbdcbloodfamily.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;

import java.util.ArrayList;
import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.MyViewHolder> implements Filterable {

    Context myContext;
    List<BloodRequestPatient> mData;
    List<BloodRequestPatient> mDataFiltered;

    public BloodRequestAdapter(Context myContext, List<BloodRequestPatient> mData) {
        this.myContext = myContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }


    @NonNull
    @Override
    public BloodRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(myContext).inflate(R.layout.model_layout_for_blood_request_page, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodRequestAdapter.MyViewHolder holder, int position) {

        //making the whole cardView Animated
        holder.sampleCardView.setAnimation(AnimationUtils.loadAnimation(myContext, R.anim.fade_scale_animation));

        holder.textViewBloodGroup.setText(mDataFiltered.get(position).getBloodGroupPatient());
        holder.textViewQuantity.setText("Quantity(Bag) : " + mDataFiltered.get(position).getQuantityOfBlood());
        holder.textViewAddress.setText("Address : " + mDataFiltered.get(position).getDistrictPatient() + ", " + mDataFiltered.get(position).getCityPatient());
        holder.textViewDate.setText("Date : " + mDataFiltered.get(position).getBloodNeededDate());
        holder.textViewPatientType.setText("Patient type : " + mDataFiltered.get(position).getPatientType());
        holder.textViewContactWith.setText("Contact with : " + mDataFiltered.get(position).getPatientName());
        holder.textViewContactWithPhoneNumber.setText(mDataFiltered.get(position).getPatientPhoneNumber());

    }

    @Override
    public int getItemCount() {
        if(mDataFiltered.size() == 0){
            //Toast.makeText(myContext, "No Data Found", Toast.LENGTH_SHORT).show();
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

                    List<BloodRequestPatient> listFiltered = new ArrayList<>();

                    for (BloodRequestPatient row : mData) {

                        if (row.getBloodGroupPatient().toLowerCase().contains(key.toLowerCase())) {
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
                mDataFiltered = (List<BloodRequestPatient>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView sampleCardView;
        TextView textViewBloodGroup;
        TextView textViewQuantity;
        TextView textViewAddress;
        TextView textViewDate;
        TextView textViewPatientType;
        TextView textViewContactWith;
        TextView textViewContactWithPhoneNumber;
        ImageView imageViewPhoneCall, imageViewMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sampleCardView = itemView.findViewById(R.id.cardView_sample_item);
            textViewBloodGroup = itemView.findViewById(R.id.textView_blood_group);
            textViewQuantity = itemView.findViewById(R.id.text_view_blood_quantity);
            textViewAddress = itemView.findViewById(R.id.textView_patient_address);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewPatientType = itemView.findViewById(R.id.text_view_patient_type);
            textViewContactWith = itemView.findViewById(R.id.text_view_contact_with);
            textViewContactWithPhoneNumber = itemView.findViewById(R.id.text_view_contact_with_phone_number);
            imageViewPhoneCall = itemView.findViewById(R.id.imageView_call);
            imageViewMessage = itemView.findViewById(R.id.imageView_message);

            imageViewPhoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getPatientPhoneNumber();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));
                    myContext.startActivity(callIntent);
                }
            });

            imageViewMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getPatientPhoneNumber();
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo));
                    myContext.startActivity(intent);

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

