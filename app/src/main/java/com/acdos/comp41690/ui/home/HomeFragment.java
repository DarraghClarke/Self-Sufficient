package com.acdos.comp41690.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.acdos.comp41690.Constants;
import com.acdos.comp41690.R;
import com.acdos.comp41690.WeatherJSONParser;
import com.acdos.comp41690.WeatherStore;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  This file uses code referenced from the open weather api recommended tutorial
 *  https://www.survivingwithandroid.com/android-openweathermap-app-weather-app/?fbclid=IwAR3_pkIO6kAqLgg5H63m43-QPRGUw7J-7jv7rPVZktDAVrkTBpFZv2eCn90
 *  and the android request/volley tutorial
 *  https://developer.android.com/training/volley/simple?fbclid=IwAR26_405eNCLPrpiodtiqnuuA_LnrLijsw_dDLNy_CqvMmQ2kdL_lAlNdn4*/
/* This file is the implementation of the home screen. It will make http requests to get the data for the weather display
* then set the appropriate text views with the received data */

public class HomeFragment extends Fragment {

    private TextView rainFall;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView hum;
    private TextView alerts;

    //Sets the appropriate textviews, checks if you are using water and or solar, makes the calls to other methods that
    //Get your weather data
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        windSpeed = root.findViewById(R.id.wind);
        temp = root.findViewById(R.id.temp);
        hum = root.findViewById(R.id.humidity);
        press = root.findViewById(R.id.pressure);
        rainFall= root.findViewById(R.id.rainFall);
        condDescr = root.findViewById(R.id.weather_status);
        minTemp = root.findViewById(R.id.temp_min);
        alerts = root.findViewById(R.id.alert);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final boolean using_solar = prefs.getBoolean(Constants.SharedPrefKeys.USING_SOLAR, false);
        final boolean using_water = prefs.getBoolean(Constants.SharedPrefKeys.USING_WATER, false);

        if (!using_solar) {
            ImageView solar = root.findViewById(R.id.solar_image);
            solar.setImageAlpha(50);
            solar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivationDialogFragment dialogFragment = new ActivationDialogFragment("solar");
                    dialogFragment.show(getFragmentManager(), "solar_activation");
                }
            });
        }
        if(!using_water) {
            ImageView water = root.findViewById(R.id.water_image);
            water.setImageAlpha(50);
            water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivationDialogFragment dialogFragment = new ActivationDialogFragment("water");
                    dialogFragment.show(getFragmentManager(), "water_activation");
                }
            });
        }

        maxTemp = root.findViewById(R.id.temp_max);

        double longitude = prefs.getFloat(Constants.SharedPrefKeys.LONGITUDE, 0.0f);
        double latitude = prefs.getFloat(Constants.SharedPrefKeys.LATITUDE, 0.0f);

        getWeatherData(longitude, latitude);
        getAlertData(longitude, latitude);
        return root;
    }



        private  final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
        private  final String APPID = "&APPID=e0af00f6b30b672fbc3058d39d79c3ee";
        public WeatherStore weather = new WeatherStore();
        private final String HERE_BASE_URL = "https://weather.cit.api.here.com/weather/1.0/report.json?product=alerts";
        private final String HERE_BASE_ID = "&app_id=SCLblvFIwikj6SHdpwab&app_code=TvzcBTnBKM-Sh_wH-pqX3w";

        //Makes a http request to the HERE api and sets the alerts into the weather store
        private void getAlertData(double longitude, double latitude) {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//            double longitude = 2.364286;
//            double latitude = 48.891784;
            //Android tutorial volley request code
            StringRequest alertRequest = new StringRequest(Request.Method.GET, HERE_BASE_URL + "&longitude=" + longitude + "&latitude=" + latitude +HERE_BASE_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray alerts = jObj.getJSONArray("alerts");

                                if(alerts.length()>0) {
                                    JSONObject ob =alerts.getJSONObject(0);
                                    weather.alerts.setAlerts(ob.getString("description"));

                                    if(alerts == null || alerts.length() == 0){
                                        return;
                                    }
                                    setAlerts(weather);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(alertRequest);
        }
        //Gets the weather data from Openweather api through volley and sends it to the parser
        private void getWeatherData(double longitude, double latitude) {

            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + "&lon=" + longitude + "&lat=" + latitude + APPID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                weather = WeatherJSONParser.getWeather(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            setWeather(weather);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });
            queue.add(stringRequest);
        }

        //Sets the appropriate weather data to their text views
        private void setWeather(WeatherStore weather) {

            temp.setText("" + Math.round((weather.temperature.getTemp() -273.15 )) + "°C");
            temp.setTextSize(90);
            rainFall.setText(""+weather.rain.getAmount());
            minTemp.setText("Minimum temperature: "+Math.round((weather.temperature.getMinTemp() -273.15 ))+"°C");
            maxTemp.setText("Maximum temperature:"+Math.round((weather.temperature.getMaxTemp() -273.15 ))+"°C");
            condDescr.setText(weather.currentCondition.getDescr());
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
        }

        private void setAlerts(WeatherStore weather){
            if(weather.alerts.getAlerts()!=null){
                alerts.setText(weather.alerts.getAlerts());
            }
        }

    }
