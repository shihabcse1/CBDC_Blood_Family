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
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.BloodBank;
import java.util.ArrayList;
import java.util.List;

public class BloodBankAdapter extends RecyclerView.Adapter<BloodBankAdapter.MyViewHolder> implements Filterable {

    Context myContext;
    List<BloodBank> mData;
    List<BloodBank> mDataFiltered;

    public BloodBankAdapter(Context myContext, List<BloodBank> mData) {
        this.myContext = myContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public BloodBankAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(myContext).inflate(R.layout.model_layout_for_blood_bank, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodBankAdapter.MyViewHolder holder, int position) {

        //making the whole cardView Animated
        holder.cardViewSampleBloodBank.setAnimation(AnimationUtils.loadAnimation(myContext, R.anim.fade_scale_animation));
        holder.textViewBloodBankName.setText(mDataFiltered.get(position).getBloodBankName());
        holder.textViewBloodBankAddress.setText("Address: " +mDataFiltered.get(position).getBloodBankAddress());
        holder.textViewBloodBankPhoneNo.setText("Phone: " +mDataFiltered.get(position).getBloodBankContactNumber());

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

                    List<BloodBank> listFiltered = new ArrayList<>();

                    for (BloodBank row : mData) {

                        if (row.getBloodBankName().toLowerCase().contains(key.toLowerCase())) {
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
                mDataFiltered = (List<BloodBank>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewSampleBloodBank;
        TextView textViewBloodBankName;
        TextView textViewBloodBankPhoneNo;
        TextView textViewBloodBankAddress;
        ImageView imageViewPhoneCallBloodBank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewSampleBloodBank = itemView.findViewById(R.id.model_card_view_item_blood_bank);
            textViewBloodBankName = itemView.findViewById(R.id.text_view_blood_bank_name);
            textViewBloodBankPhoneNo = itemView.findViewById(R.id.textView_blood_bank_phone_number);
            textViewBloodBankAddress = itemView.findViewById(R.id.textView_blood_bank_address);
            imageViewPhoneCallBloodBank = itemView.findViewById(R.id.imageView_call_blood_bank);

            imageViewPhoneCallBloodBank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getBloodBankContactNumber();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));
                    myContext.startActivity(callIntent);
                }
            });

        }
    }

}
