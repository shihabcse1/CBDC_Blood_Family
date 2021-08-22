package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shihabcse.cbdcbloodfamily.BuildConfig;
import com.shihabcse.cbdcbloodfamily.adapters.PageAdapter;
import com.shihabcse.cbdcbloodfamily.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    ViewPager pager;
    TabLayout myTabLayout;
    TabItem firstItem, secondItem, thirdItem, fourthItem;
    PageAdapter pageAdapter;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    int totalDonors = 0;

    private DatabaseReference totalDonorReference;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view_id);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar();
        //getSupportActionBar().setTitle("Home");

        totalDonorReference = FirebaseDatabase.getInstance().getReference().child("blood_donors");

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if(firstStart){
            //show dialog just for first time
            showStartDialog();
        }

        // initialize
        myDialog = new Dialog(this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        updateNavHeader();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        pager = findViewById(R.id.viewpager);
        myTabLayout = findViewById(R.id.tabLayout);
        firstItem = findViewById(R.id.firstTabItem);
        secondItem = findViewById(R.id.secondTabItem);
        thirdItem = findViewById(R.id.thirdTabItem);
        fourthItem = findViewById(R.id.fourthTabItem);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        pageAdapter = new PageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, myTabLayout.getTabCount());
        pager.setAdapter(pageAdapter);

        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myTabLayout));


    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Welcome to CBDC Blood Family")
                .setMessage("Please go to Add New Donor page to be a Blood Donor.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    private void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view_id);
        View headerView = navigationView.getHeaderView(0);

        TextView navUserName = headerView.findViewById(R.id.nav_user_name);
        TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
        TextView navTotalDonor = headerView.findViewById(R.id.nav_total_donor);
        CircleImageView navUserImage = headerView.findViewById(R.id.nav_user_photo);

        // For Updating total number of Donors
        totalDonorReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    totalDonors = (int) snapshot.getChildrenCount();
                    navTotalDonor.setText("Total Donors : " + Integer.toString(totalDonors));
                } else {
                    totalDonors = 0;
                    navTotalDonor.setText("Total Donors : " + Integer.toString(totalDonors));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navUserName.setText(currentUser.getDisplayName());
        navUserMail.setText(currentUser.getEmail());

        // if user doesn't upload his profile pic then load the default photo
        if (currentUser.getPhotoUrl() != null) {
            //using Glide to load user image
            Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserImage);

        } else {
            Glide.with(this).load(R.drawable.cbdc_app_icon).into(navUserImage);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.nav_menu_edit_information){
            Intent editProfileActivity = new Intent(MainActivity.this, EditProfilePageActivity.class);
            startActivity(editProfileActivity);
        }
        if(item.getItemId() == R.id.nav_menu_add_blood_request){
            Intent addBloodRequestActivity = new Intent(MainActivity.this, AddBloodRequestActivity.class);
            startActivity(addBloodRequestActivity);
        }
        if(item.getItemId() == R.id.nav_menu_add_donor){
            Intent addNewDonorActivity = new Intent(MainActivity.this, AddNewDonorActivity.class);
            startActivity(addNewDonorActivity);
        }
        if(item.getItemId() == R.id.nav_menu_add_coordinator){
            Intent addCoordinatorActivity = new Intent(MainActivity.this, CoordinatorActivity.class);
            startActivity(addCoordinatorActivity);
        }
        if(item.getItemId() == R.id.nav_menu_add_donor){
            Intent addNewDonorActivity = new Intent(MainActivity.this, AddNewDonorActivity.class);
            startActivity(addNewDonorActivity);
        }
        if(item.getItemId() == R.id.nav_menu_add_organization){
            Intent addOrganizationActivity = new Intent(MainActivity.this, AddOrganizationActivity.class);
            startActivity(addOrganizationActivity);
        }

        if(item.getItemId() == R.id.nav_menu_add_blood_bank){
            Intent addBloodBankActivity = new Intent(MainActivity.this, AddBloodBankActivity.class);
            startActivity(addBloodBankActivity);
        }

        if(item.getItemId() == R.id.nav_menu_donate_us){
            Intent donateUsActivity = new Intent(MainActivity.this, DonateUsActivity.class);
            startActivity(donateUsActivity);
        }

        if(item.getItemId() == R.id.nav_menu_add_helpline){
            Intent addHelpLineActivity = new Intent(MainActivity.this, AddHelplineActivity.class);
            startActivity(addHelpLineActivity);
        }

        if(item.getItemId() == R.id.nav_menu_helpline){
            Intent helpLineActivity = new Intent(MainActivity.this, HelplineShow.class);
            startActivity(helpLineActivity);
        }

        if(item.getItemId() == R.id.nav_menu_about_developer){

            myDialog.setContentView(R.layout.activity_about_developer);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
            TextView textViewCloseDialog;
            ImageView imageViewCallMe, imageViewEmailMe, imageViewFacebook;
            Button buttonAboutMe;

            textViewCloseDialog = myDialog.findViewById(R.id.textView_close_dialog);
            imageViewCallMe = myDialog.findViewById(R.id.imageView_call_me);
            imageViewEmailMe = myDialog.findViewById(R.id.imageView_email_me);
            imageViewFacebook = myDialog.findViewById(R.id.imageView_facebook);
            buttonAboutMe = myDialog.findViewById(R.id.button_about_me);

            textViewCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            imageViewCallMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntentMe = new Intent(Intent.ACTION_DIAL);
                    callIntentMe.setData(Uri.parse("tel:01871429133"));
                    startActivity(callIntentMe);
                }
            });

            imageViewEmailMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "tomdshihab@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Send feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Thanks");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });

            imageViewFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://www.facebook.com/shihab.cse";
                    Intent myFacebookIntent = new Intent(Intent.ACTION_VIEW);
                    myFacebookIntent.setData(Uri.parse(url));
                    startActivity(myFacebookIntent);
                }
            });

            buttonAboutMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://www.facebook.com/shihab.cse";
                    Intent myFacebookIntent = new Intent(Intent.ACTION_VIEW);
                    myFacebookIntent.setData(Uri.parse(url));
                    startActivity(myFacebookIntent);
                }
            });

        }

        if(item.getItemId() == R.id.menu_sign_out){
            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.like_us_on_facebook_menu){
            String url = "https://www.facebook.com/cbdc17/";
            Intent myFacebookIntentPage = new Intent(Intent.ACTION_VIEW);
            myFacebookIntentPage.setData(Uri.parse(url));
            startActivity(myFacebookIntentPage);
        }
        if(item.getItemId() == R.id.join_us_on_facebook_group){
            String url = "https://www.facebook.com/groups/CBDC17/";
            Intent myFacebookIntentPage = new Intent(Intent.ACTION_VIEW);
            myFacebookIntentPage.setData(Uri.parse(url));
            startActivity(myFacebookIntentPage);
        }
        if(item.getItemId() == R.id.media){
            String url = "https://www.coxbdc.com";
            Intent myFacebookIntentMedia = new Intent(Intent.ACTION_VIEW);
            myFacebookIntentMedia.setData(Uri.parse(url));
            startActivity(myFacebookIntentMedia);
        }
        if(item.getItemId() == R.id.share_menu){
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CBDC Blood Family");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }
        return true;
    }
}