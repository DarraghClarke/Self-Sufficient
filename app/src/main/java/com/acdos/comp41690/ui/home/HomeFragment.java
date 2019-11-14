package com.acdos.comp41690.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.acdos.comp41690.ui.rain.RainFragment;
import com.acdos.comp41690.ui.solar.SolarFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void showRainTransition(View view) {
        displayToast(getString(R.string.menu_rain));
    }
    public void showSolarTransition(View view) {
        displayToast(getString(R.string.menu_solar));
    }
    public void solarTransition(){
        Fragment solarFragment = new SolarFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_solar, solarFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void rainTransition(){
        Fragment rainFragment = new RainFragment();
        FragmentManager rainFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction rainFragmentTransaction = rainFragmentManager.beginTransaction();
        rainFragmentTransaction.replace(R.id.nav_rain, rainFragment);
        rainFragmentTransaction.addToBackStack(null);
        rainFragmentTransaction.commit();
    }
    public void displayToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }
}