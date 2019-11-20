package com.acdos.comp41690;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.acdos.comp41690.ui.rain.RainSectionsPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

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
}
