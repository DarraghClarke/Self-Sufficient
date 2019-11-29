package com.acdos.comp41690;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import androidx.viewpager.widget.ViewPager;

import com.acdos.comp41690.data.SolarGenerationContract;
import com.acdos.comp41690.data.SolarUsageContract;
import com.acdos.comp41690.data.UserDataDbHelper;
import com.acdos.comp41690.ui.solar.SolarSectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ElectricityActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        //Add Tab stuff
        SolarSectionsPagerAdapter sectionsPagerAdapter = new SolarSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_solar);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_solar);
        tabs.setupWithViewPager(viewPager);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_solar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_solar);
        NavigationView navigationView = findViewById(R.id.nav_view_solar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_rain, R.id.nav_solar,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_solar);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        final FloatingActionButton addData = findViewById(R.id.addDataSolar);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog addDataAlert = new Dialog(ElectricityActivity.this);
                addDataAlert.setTitle("Current: " + "kWh");

                addDataAlert.setContentView(R.layout.input_data_dialog);
                final RadioButton usageButton = addDataAlert.findViewById(R.id.usageButton);
                final RadioButton outputButton = addDataAlert.findViewById(R.id.outputButton);
                final EditText inputField = addDataAlert.findViewById(R.id.dataInputField);

                final Button submitButton = addDataAlert.findViewById(R.id.submitButton);

                final Button cancelButton = addDataAlert.findViewById(R.id.cancelButton);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (inputField.getText().length() == 0) {
                            Toast.makeText(ElectricityActivity.this, "Input can not be empty", Toast.LENGTH_SHORT).show();
                        } else if (usageButton.isChecked()) {

                            addToDatabase(true, Integer.valueOf(inputField.getText().toString()));
                        } else {
                            addToDatabase(false, Integer.valueOf(inputField.getText().toString()));
                        }
                        addDataAlert.cancel();
                        finish();
                        overridePendingTransition(0, 0);
                        //startActivity(getIntent());
                        startActivity(new Intent(getApplicationContext(), ElectricityActivity.class));
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

                if(id == R.id.nav_rain) {
                    Intent i = new Intent(getApplicationContext(), RainActivity.class);
                    assert mAppBarConfiguration.getDrawerLayout() != null;
                    mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                    startActivity(i);
                    return true;
                }

                if(id == R.id.nav_settings) {
                    Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                    assert mAppBarConfiguration.getDrawerLayout() != null;
                    mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                    startActivity(i);
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.solar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_solar);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * @param dataType if true then solar usage should be used, if false then solar generation contract should be used
     */
    private void addToDatabase(boolean dataType, int input) {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(ElectricityActivity.this);
        ContentValues value = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();
        Timestamp timestamp = getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (dataType == true) {
            value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_USAGE, input);
            value.put(SolarUsageContract.SolarUsageEntry.COLUMN_NAME_TIMESTAMP,sdf.format(timestamp) );
            userDb.insert(SolarUsageContract.SolarUsageEntry.TABLE_NAME, null, value);
        } else {
            value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_GENERATED_ENERGY, input);
            value.put(SolarGenerationContract.SolarGenerationEntry.COLUMN_NAME_TIMESTAMP,sdf.format(timestamp) );
            userDb.insert(SolarGenerationContract.SolarGenerationEntry.TABLE_NAME, null, value);
        }
    }
    public static Timestamp getTime(){

        Timestamp timestamp = new Timestamp((System.currentTimeMillis()));

        
        return timestamp;
    }
}