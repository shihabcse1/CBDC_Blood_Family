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

import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.HelpLine;
import com.shihabcse.cbdcbloodfamily.models.Organization;

import java.util.ArrayList;
import java.util.List;

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.MyViewHolder> implements Filterable {

    Context myContext;
    List<HelpLine> mData;
    List<HelpLine> mDataFiltered;

    public HelplineAdapter(Context myContext, List<HelpLine> mData) {
        this.myContext = myContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public HelplineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(myContext).inflate(R.layout.model_layout_for_helpline, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull HelplineAdapter.MyViewHolder holder, int position) {

        //making the whole cardView Animated
        holder.cardViewSampleHelpLine.setAnimation(AnimationUtils.loadAnimation(myContext, R.anim.fade_scale_animation));
        holder.textViewHelpLineName.setText(mDataFiltered.get(position).getAmbulanceName());
        holder.textViewHelpLinePhoneNo.setText(mDataFiltered.get(position).getAmbulanceContactNumber());

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

                    List<HelpLine> listFiltered = new ArrayList<>();

                    for (HelpLine row : mData) {

                        if (row.getAmbulanceName().toLowerCase().contains(key.toLowerCase())) {
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
                mDataFiltered = (List<HelpLine>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewSampleHelpLine;
        TextView textViewHelpLineName;
        TextView textViewHelpLinePhoneNo;
        ImageView imageViewPhoneCallHelpLine;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewSampleHelpLine = itemView.findViewById(R.id.model_card_view_item_helpline);
            textViewHelpLineName = itemView.findViewById(R.id.text_view_ambulance_name);
            textViewHelpLinePhoneNo = itemView.findViewById(R.id.textView_ambulance_phone_number);
            imageViewPhoneCallHelpLine = itemView.findViewById(R.id.imageView_call_ambulance);

            imageViewPhoneCallHelpLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getAmbulanceContactNumber();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));
                    myContext.startActivity(callIntent);
                }
            });

        }
    }

}
