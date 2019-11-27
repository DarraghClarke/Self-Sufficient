package com.acdos.comp41690;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.acdos.comp41690.data.UserDataDbHelper;
import com.acdos.comp41690.data.WaterUsageContract;
import com.acdos.comp41690.ui.rain.RainSectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.time.Instant;

public class RainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        //Add Tab stuff
        RainSectionsPagerAdapter sectionsPagerAdapter = new RainSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_rain);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_rain);
        tabs.setupWithViewPager(viewPager);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_rain);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_rain);
        NavigationView navigationView = findViewById(R.id.nav_view_rain);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_rain, R.id.nav_solar,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_rain);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final FloatingActionButton addData = findViewById(R.id.addDataWater);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog addDataAlert = new Dialog(RainActivity.this);

                addDataAlert.setContentView(R.layout.input_data_dialog);
                RadioGroup radioGroup = addDataAlert.findViewById(R.id.radioGroup);
                radioGroup.setVisibility(View.INVISIBLE);

                final EditText inputField = addDataAlert.findViewById(R.id.dataInputField);
                inputField.setHint(R.string.water_usage_input_dialog);
                TextView Title = addDataAlert.findViewById(R.id.Title);
                Title.setText("Water Input");
                final Button submitButton = addDataAlert.findViewById(R.id.submitButton);

                final Button cancelButton = addDataAlert.findViewById(R.id.cancelButton);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (inputField.getText().length() == 0) {
                            Toast.makeText(RainActivity.this, "Input can not be empty", Toast.LENGTH_SHORT).show();
                        }  else {
                            addToDatabase( Integer.valueOf(inputField.getText().toString()));
                        }
                        addDataAlert.cancel();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addDataAlert.cancel();
                    }
                });

                addDataAlert.show();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(menuItem.isChecked()) {
                    assert mAppBarConfiguration.getDrawerLayout() != null;
                    mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                    return false;
                }

                if(id == R.id.nav_home) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    assert mAppBarConfiguration.getDrawerLayout() != null;
                    mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                    startActivity(i);
                    return true;
                }

                if(id == R.id.nav_solar) {
                    Intent i = new Intent(getApplicationContext(), ElectricityActivity.class);
                    assert mAppBarConfiguration.getDrawerLayout() != null;
                    mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                    startActivity(i);
                    return true;
                }

                return false;
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String solar_panel_output = prefs.getString(Constants.SharedPrefKeys.SOLAR_PANEL_OUTPUT, "");
        Toast.makeText(this,"yes, Solar output "+ solar_panel_output,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rain, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_rain);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void addToDatabase(int input) {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(RainActivity.this);
        ContentValues value = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();

        value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME, input);
        value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond());
        userDb.insert(WaterUsageContract.WaterUsageEntry.TABLE_NAME, null, value);
    }
}
