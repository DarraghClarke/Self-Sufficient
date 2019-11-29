package com.acdos.comp41690;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.acdos.comp41690.ui.home.ActivationDialogFragment;
import com.google.android.material.navigation.NavigationView;

/**
 * Settings Activity used to let the user change their SharedPreferences
 *
 * Based off code from https://developer.android.com/guide/topics/ui/settings
 */
public class SettingsActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Code taken from Android Studio's navigation drawer set-up starter code
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_settings);
        NavigationView navigationView = findViewById(R.id.nav_view_settings);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_rain, R.id.nav_solar,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_settings);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Code taken from Android Studio's navigation drawer set-up starter code
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

                return false;
            }
        });
    }

    // Code taken from Android Studio's navigation drawer set-up starter code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);

        return true;
    }

    // Code taken from Android Studio's navigation drawer set-up starter code
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_settings);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Fragment used to display preferences to the user
    // Uses the androidx Preference library to do this
    public static class AppPreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

        SwitchPreference usingWater;
        SwitchPreference usingSolar;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_main, rootKey);

            EditTextPreference roofArea = findPreference(Constants.SharedPrefKeys.ROOF_AREA);
            usingWater = findPreference(Constants.SharedPrefKeys.USING_WATER);
            usingSolar = findPreference(Constants.SharedPrefKeys.USING_SOLAR);

            usingSolar.setOnPreferenceChangeListener(this);
            usingWater.setOnPreferenceChangeListener(this);

            if (roofArea != null) {
//                float value = getPreferenceManager().getSharedPreferences().getFloat(Constants.SharedPrefKeys.ROOF_AREA, 0.0f);
//                roofArea.setText(String.valueOf(value));
//                roofArea.setOnBindEditTextListener(
//                        new EditTextPreference.OnBindEditTextListener() {
//                            @Override
//                            public void onBindEditText(@NonNull EditText editText) {
//                                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//                            }
//                        });
            }

        }

        //Used to unlock the solar and rain sections of the app
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference == usingWater) {
                if(!usingWater.isChecked()) {
                    ActivationDialogFragment dialogFragment = new ActivationDialogFragment("water");
                    dialogFragment.show(this.getFragmentManager(), "water_activation");
                }
                else {
                    Toast.makeText(getContext(), "Can't lock a section once it is in use.", Toast.LENGTH_LONG).show();
                }
            }
            else if(preference == usingSolar){
                if(!usingSolar.isChecked()) {
                    ActivationDialogFragment dialogFragment = new ActivationDialogFragment("solar");
                    dialogFragment.show(this.getFragmentManager(), "solar_activation");
                }
                else {
                    Toast.makeText(getContext(), "Can't lock a section once it is in use.", Toast.LENGTH_LONG).show();
                }
            }
            return false;
        }
    }
}