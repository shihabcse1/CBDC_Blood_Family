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
import com.shihabcse.cbdcbloodfamily.activities.SearchBloodRequestActivity;
import com.shihabcse.cbdcbloodfamily.activities.SearchCoordinatorActivity;
import com.shihabcse.cbdcbloodfamily.adapters.BloodRequestAdapter;
import com.shihabcse.cbdcbloodfamily.adapters.CoordinatorAdapter;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;
import com.shihabcse.cbdcbloodfamily.models.Coordinator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoOrdinatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoOrdinatorFragment extends Fragment {

    RecyclerView coordinatorRecyclerView;
    CoordinatorAdapter coordinatorAdapter;

    FloatingActionButton fabCoordinator;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Coordinator> coordinatorList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CoOrdinatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                coordinatorList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    Coordinator coordinator = postSnap.getValue(Coordinator.class);
                    coordinatorList.add(coordinator);

                }

                //progressDialog.dismiss();

                coordinatorAdapter = new CoordinatorAdapter(getActivity(), coordinatorList);
                coordinatorRecyclerView.setAdapter(coordinatorAdapter);

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
     * @return A new instance of fragment CoOrdinatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoOrdinatorFragment newInstance(String param1, String param2) {
        CoOrdinatorFragment fragment = new CoOrdinatorFragment();
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
        //return inflater.inflate(R.layout.fragment_co_ordinator, container, false);
        View root = inflater.inflate(R.layout.fragment_co_ordinator, container, false);

        fabCoordinator = root.findViewById(R.id.floating_action_button_coordinator);

        coordinatorRecyclerView = root.findViewById(R.id.recyclerView_item_coordinator);
        coordinatorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        coordinatorRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("coordinators_info");

        fabCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchCoordinatorActivity = new Intent(getContext(), SearchCoordinatorActivity.class);
                startActivity(searchCoordinatorActivity);
            }
        });

        return root;
    }
}