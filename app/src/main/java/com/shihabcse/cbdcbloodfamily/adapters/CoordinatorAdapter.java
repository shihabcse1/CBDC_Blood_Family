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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;
import com.shihabcse.cbdcbloodfamily.models.Coordinator;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorAdapter extends RecyclerView.Adapter<CoordinatorAdapter.MyViewHolder> implements Filterable {

    Context myContext;
    List<Coordinator> mData;
    List<Coordinator> mDataFiltered;

    public CoordinatorAdapter(Context myContext, List<Coordinator> mData) {
        this.myContext = myContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public CoordinatorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(myContext).inflate(R.layout.model_layout_for_coordinator_recyclerview, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinatorAdapter.MyViewHolder holder, int position) {

        //making the whole cardView Animated
        holder.sampleCardViewCoordinator.setAnimation(AnimationUtils.loadAnimation(myContext, R.anim.fade_scale_animation));

        holder.textViewCoOrdinatorName.setText("Name : " + mDataFiltered.get(position).getCoordinatorName());
        holder.textViewCoordinatorPhoneNumber.setText("Phone Number : " +mDataFiltered.get(position).getCoordinatorContactNumber());

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

                    List<Coordinator> listFiltered = new ArrayList<>();

                    for (Coordinator row : mData) {

                        if (row.getCoordinatorName().toLowerCase().contains(key.toLowerCase())) {
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
                mDataFiltered = (List<Coordinator>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView sampleCardViewCoordinator;
        TextView textViewCoOrdinatorName;
        TextView textViewCoordinatorPhoneNumber;
        ImageView imageViewPhoneCallCoordinator;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sampleCardViewCoordinator = itemView.findViewById(R.id.card_view_sample_coordinator);
            textViewCoOrdinatorName = itemView.findViewById(R.id.text_view_coornator_name_recylclerview);
            textViewCoordinatorPhoneNumber = itemView.findViewById(R.id.textView_coordinator_phone_number);
            imageViewPhoneCallCoordinator = itemView.findViewById(R.id.imageView_call_coordinator);

            imageViewPhoneCallCoordinator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getCoordinatorContactNumber();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));
                    myContext.startActivity(callIntent);
                }
            });

        }
    }

}