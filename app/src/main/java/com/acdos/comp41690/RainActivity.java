package com.acdos.comp41690;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.acdos.comp41690.ui.home.ActivationDialogFragment;
import com.acdos.comp41690.ui.rain.RainSectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class RainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

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

                addDataAlert.setContentView(R.layout.input_data_dialog_rain);
                final EditText inputField = addDataAlert.findViewById(R.id.dataInputField);
                inputField.setHint(R.string.water_usage_input_dialog);
                final Button submitButton = addDataAlert.findViewById(R.id.submitButton);
                final Button cancelButton = addDataAlert.findViewById(R.id.cancelButton);
                final RadioButton addButton = addDataAlert.findViewById(R.id.addButton);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (inputField.getText().length() == 0) {
                            Toast.makeText(RainActivity.this, "Input can not be empty", Toast.LENGTH_SHORT).show();
                        }  else if (addButton.isChecked() ){
                            addToDatabase(true, Integer.valueOf(inputField.getText().toString()));
                        } else {
                            addToDatabase(false, Integer.valueOf(inputField.getText().toString()));

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

                if (id == R.id.nav_solar) {

                    if(prefs.getBoolean(Constants.SharedPrefKeys.USING_SOLAR, false)) {
                        Intent i = new Intent(getApplicationContext(), ElectricityActivity.class);
                        assert mAppBarConfiguration.getDrawerLayout() != null;
                        mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                        startActivity(i);
                        return true;
                    }
                    else {
                        ActivationDialogFragment dialogFragment = new ActivationDialogFragment("solar");
                        dialogFragment.show(getSupportFragmentManager(), "solar_activation");
                        return false;
                    }
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

    // Code taken from Android Studio's navigation drawer starting code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rain, menu);
        return true;
    }

    // Code taken from Android Studio's navigation drawer starting code
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_rain);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Open a new activity to send an email via an installed email client
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EmailActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Add new volume to database
    private void addToDatabase(boolean addSub, int input) {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(RainActivity.this);
        ContentValues value = new ContentValues();
        SQLiteDatabase userDb = userDataDbHelper.getWritableDatabase();
        Date timestamp = getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        int currentVolume = getCurrLitre();

        if(addSub == true) {
            value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME, input+currentVolume);
            value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_TIMESTAMP, sdf.format(timestamp) );
            userDb.insert(WaterUsageContract.WaterUsageEntry.TABLE_NAME, null, value);
        }
        else {
            value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME, currentVolume-input);
            value.put(WaterUsageContract.WaterUsageEntry.COLUMN_NAME_TIMESTAMP, sdf.format(timestamp) );
            userDb.insert(WaterUsageContract.WaterUsageEntry.TABLE_NAME, null, value);
        }
    }

    //Get the most recent volume from the database
    private int getCurrLitre() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(this);
        SQLiteDatabase userDb = userDataDbHelper.getReadableDatabase();

        String[] projectionUsage = { WaterUsageContract.WaterUsageEntry.COLUMN_NAME_VOLUME };

        Cursor cursor = userDb.query(WaterUsageContract.WaterUsageEntry.TABLE_NAME, projectionUsage, null, null, null, null, null);
        int val;
        if(cursor.moveToLast()) {
            val = cursor.getInt(0);
        }
        else {
            val = 0;
        }
        cursor.close();
        return val;
    }

    public static Date getTime(){

        Date timestamp = new Date((System.currentTimeMillis()));


        return timestamp;
    }
}
