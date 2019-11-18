package com.acdos.comp41690;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.acdos.comp41690.data.WaterTrackingContract.WaterTrackingEntry;
import com.acdos.comp41690.data.WaterTrackingDbHelper;
import com.acdos.comp41690.setup.SetupPagerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.time.Instant;

public class MainActivity extends Activity implements View.OnClickListener {

    SharedPreferences prefs = null;
    Button elecButton;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefs = getSharedPreferences(
                getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        elecButton = findViewById(R.id.elec_button);
        elecButton.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_rain, R.id.nav_solar,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Launch set-up view
            Intent intent = new Intent(this, SetupPagerActivity.class);

            prefs.edit().putBoolean("firstrun", false).apply();

            startActivity(intent);
        }
        // Launch dashboard view
    }

    private void testDb() {
        WaterTrackingDbHelper dbHelper = new WaterTrackingDbHelper(this);

        ContentValues values = new ContentValues();
        values.put(WaterTrackingEntry.COLUMN_NAME_VOLUME, 53302.33);
        values.put(WaterTrackingEntry.COLUMN_NAME_TIMESTAMP, Instant.now().getEpochSecond());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(WaterTrackingEntry.TABLE_NAME, null, values);

        String[] projection = {
                WaterTrackingEntry._ID,
                WaterTrackingEntry.COLUMN_NAME_TIMESTAMP,
                WaterTrackingEntry.COLUMN_NAME_VOLUME };

        Cursor c = db.query(WaterTrackingEntry.TABLE_NAME, projection, null, null, null, null, null);

        while(c.moveToNext()) {
            Log.d("MainActivity", c.getLong(0) + ", " + c.getLong(1) + ", " + c.getDouble(2));
        }
        c.close();
    }

    @Override
    public void onClick(View v) {
        if(v == elecButton) {
            Intent i = new Intent(this, ElectricityActivity.class);
            startActivity(i);
        }
    }
}