package com.shihabcse.cbdcbloodfamily.fragments;

import android.app.ProgressDialog;
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
import com.shihabcse.cbdcbloodfamily.activities.SearchBloodRequestActivity;
import com.shihabcse.cbdcbloodfamily.adapters.BloodRequestAdapter;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BloodRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BloodRequestFragment extends Fragment {

    FloatingActionButton fab;

    RecyclerView donorRecyclerView;
    BloodRequestAdapter bloodRequestAdapter;

    private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BloodRequestPatient> bloodRequestPatientList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BloodRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {

        // progress Dialog
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bloodRequestPatientList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    BloodRequestPatient bloodRequestPatient = postSnap.getValue(BloodRequestPatient.class);
                    bloodRequestPatientList.add(bloodRequestPatient);

                }

                progressDialog.dismiss();

                bloodRequestAdapter = new BloodRequestAdapter(getActivity(), bloodRequestPatientList);
                donorRecyclerView.setAdapter(bloodRequestAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Check your internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BloodRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BloodRequestFragment newInstance(String param1, String param2) {
        BloodRequestFragment fragment = new BloodRequestFragment();
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
        //return inflater.inflate(R.layout.fragment_blood_request, container, false);
        View root = inflater.inflate(R.layout.fragment_blood_request, container, false);

        fab = root.findViewById(R.id.floating_action_button);

        donorRecyclerView = root.findViewById(R.id.recyclerView_item);
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        donorRecyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(getContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("blood_request");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchBloodRequestActivity = new Intent(getContext(), SearchBloodRequestActivity.class);
                startActivity(searchBloodRequestActivity);
            }
        });

        return root;
    }
}