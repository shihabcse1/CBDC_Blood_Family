package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.adapters.HelplineAdapter;
import com.shihabcse.cbdcbloodfamily.models.HelpLine;

import java.util.ArrayList;
import java.util.List;

public class HelplineShow extends AppCompatActivity {

    RecyclerView helplineRecyclerView;
    HelplineAdapter helplineAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<HelpLine> helpLineList;

    EditText editTextSearchInputHelpline;

    @Override
    protected void onStart() {
        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                helpLineList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    HelpLine helpLine = postSnap.getValue(HelpLine.class);
                    helpLineList.add(helpLine);

                }

                //progressDialog.dismiss();
                helplineAdapter = new HelplineAdapter(getApplicationContext(), helpLineList);
                helplineRecyclerView.setAdapter(helplineAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Check your internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline_show);

        editTextSearchInputHelpline = findViewById(R.id.editText_search_input_helpline);
        helplineRecyclerView = findViewById(R.id.recyclerView_item_show_helpline);
        helplineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        helplineRecyclerView.setHasFixedSize(true);
        helplineRecyclerView.setAdapter(helplineAdapter);//this line added for checking

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("help_lines");

        editTextSearchInputHelpline.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                helplineAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}