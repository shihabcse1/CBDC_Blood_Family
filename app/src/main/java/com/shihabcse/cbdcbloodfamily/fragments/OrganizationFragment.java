package com.shihabcse.cbdcbloodfamily.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.activities.SearchCoordinatorActivity;
import com.shihabcse.cbdcbloodfamily.activities.SearchOrganizationActivity;
import com.shihabcse.cbdcbloodfamily.adapters.CoordinatorAdapter;
import com.shihabcse.cbdcbloodfamily.adapters.OrganizationAdapter;
import com.shihabcse.cbdcbloodfamily.models.Coordinator;
import com.shihabcse.cbdcbloodfamily.models.Organization;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizationFragment extends Fragment {

    RecyclerView organizationRecyclerView;
    OrganizationAdapter organizationAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Organization> organizationsList;

    FloatingActionButton fabOrganization;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrganizationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                organizationsList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    Organization organization = postSnap.getValue(Organization.class);
                    organizationsList.add(organization);

                }

                //progressDialog.dismiss();

                organizationAdapter = new OrganizationAdapter(getActivity(), organizationsList);
                organizationRecyclerView.setAdapter(organizationAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "আপনার ইন্টারনেট সংযোগ চেক করুন!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrganizationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizationFragment newInstance(String param1, String param2) {
        OrganizationFragment fragment = new OrganizationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_organization, container, false);

        View root = inflater.inflate(R.layout.fragment_organization, container, false);

        fabOrganization = root.findViewById(R.id.floating_action_button_organization);

        organizationRecyclerView = root.findViewById(R.id.recyclerView_item_organization);
        organizationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        organizationRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("organization_list");

        fabOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchOrganization = new Intent(getContext(), SearchOrganizationActivity.class);
                startActivity(searchOrganization);
            }
        });

        return root;

    }
}