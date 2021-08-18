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
import com.shihabcse.cbdcbloodfamily.models.Organization;

import java.util.ArrayList;
import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.MyViewHolder> implements Filterable {

    Context myContext;
    List<Organization> mData;
    List<Organization> mDataFiltered;

    public OrganizationAdapter(Context myContext, List<Organization> mData) {
        this.myContext = myContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public OrganizationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(myContext).inflate(R.layout.model_layout_for_organization_recycler_view, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationAdapter.MyViewHolder holder, int position) {

        //making the whole cardView Animated
        holder.cardViewSampleOrganization.setAnimation(AnimationUtils.loadAnimation(myContext, R.anim.fade_scale_animation));
        //TODO here
        holder.textViewOrganizationName.setText(mDataFiltered.get(position).getOrganizationName());
        holder.textViewOrganizationAdminName.setText("Admin/Director : " +mDataFiltered.get(position).getAdminName());
        holder.textViewOrganizationAdminPN.setText(mDataFiltered.get(position).getAdminContactNumber());

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

                    List<Organization> listFiltered = new ArrayList<>();

                    for (Organization row : mData) {

                        if (row.getOrganizationName().toLowerCase().contains(key.toLowerCase())) {
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
                mDataFiltered = (List<Organization>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewSampleOrganization;
        TextView textViewOrganizationName;
        TextView textViewOrganizationAdminName;
        TextView textViewOrganizationAdminPN;
        ImageView imageViewPhoneCallAdminOrganization;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewSampleOrganization = itemView.findViewById(R.id.cardView_sample_item_organization);
            textViewOrganizationName = itemView.findViewById(R.id.text_view_organization_name_recylclerview);
            textViewOrganizationAdminName = itemView.findViewById(R.id.textView_admin_name_organization);
            textViewOrganizationAdminPN = itemView.findViewById(R.id.text_view_Admin_phone_number);
            imageViewPhoneCallAdminOrganization = itemView.findViewById(R.id.imageView_call_organization_admin);

            imageViewPhoneCallAdminOrganization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String phoneNo = mDataFiltered.get(position).getAdminContactNumber();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNo));
                    myContext.startActivity(callIntent);
                }
            });

        }
    }

}