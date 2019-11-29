package com.acdos.comp41690;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

import com.acdos.comp41690.setup.InitialSetupActivity;
import com.acdos.comp41690.ui.home.ActivationDialogFragment;
import com.google.android.material.navigation.NavigationView;

/**
 * Main Activity used when the lap is launched, showing the dashboard
 */
public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Code taken from Android Studio's navigation drawer starting code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_rain, R.id.nav_solar,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();

        // Code taken from Android Studio's navigation drawer starting code
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Controller to tell app which Activity to start
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                //Make sure another instance of the same Activity isn't opened on top of itself
                if (menuItem.isChecked()) {
                    assert mAppBarConfiguration.getDrawerLayout() != null;
                    mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                    return false;
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

                if (id == R.id.nav_rain) {
                    if(prefs.getBoolean(Constants.SharedPrefKeys.USING_WATER, false)) {
                        Intent i = new Intent(getApplicationContext(), RainActivity.class);
                        assert mAppBarConfiguration.getDrawerLayout() != null;
                        mAppBarConfiguration.getDrawerLayout().closeDrawer(GravityCompat.START);
                        startActivity(i);
                        return true;
                    }
                    else {
                        ActivationDialogFragment dialogFragment = new ActivationDialogFragment("water");
                        dialogFragment.show(getSupportFragmentManager(), "water_activation");
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Code taken from Android Studio's navigation drawer starting code
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

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

    @Override
    protected void onResume() {
        super.onResume();

        // If the app has never been launched before, start up the setup activity
        if (prefs.getBoolean(Constants.SharedPrefKeys.FIRST_RUN, true)) {
            // Launch set-up view
            Intent intent = new Intent(this, InitialSetupActivity.class);
            startActivity(intent);
        }

        // Otherwise, continue with the dashboard view
    }

    public void solarTransition(View view) {
        Intent myIntent = new Intent(this, ElectricityActivity.class);
        startActivity(myIntent);
    }

    public void rainTransition(View view) {
        Intent myIntent = new Intent(this, RainActivity.class);
        startActivity(myIntent);
    }


}