package com.acdos.comp41690.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private TextView rainFall;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView hum;
    private TextView alerts;

    @Override


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        windSpeed = root.findViewById(R.id.wind);
        temp = root.findViewById(R.id.temp);
        hum = root.findViewById(R.id.humidity);
        press = root.findViewById(R.id.pressure);
        rainFall= root.findViewById(R.id.rainFall);
        condDescr = root.findViewById(R.id.weather_status);
        minTemp = root.findViewById(R.id.temp_min);
        alerts = root.findViewById(R.id.alert);

        maxTemp = root.findViewById(R.id.temp_max);
        getWeatherData("London,UK");
        getAlertData("Paris");
        return root;
    }



        private  String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private  String APPID = "e0af00f6b30b672fbc3058d39d79c3ee";
        public   String data="";
        private WeatherStore weather = new WeatherStore();
        private String HERE_BASE_URL = "https://weather.cit.api.here.com/weather/1.0/report.json?product=alerts&app_id=SCLblvFIwikj6SHdpwab&app_code=TvzcBTnBKM-Sh_wH-pqX3w";

        private WeatherStore getWeatherStore(String data) {


            try {
                weather = WeatherJSONParser.getWeather(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }
        private String getAlertData(String location) {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            double longitude = 2.364286;
            double latitude = 48.891784;

            StringRequest alertRequest = new StringRequest(Request.Method.GET, HERE_BASE_URL + "&longitude=" + longitude + "&latitude=" + latitude,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray alerts = jObj.getJSONObject("alerts").getJSONArray("alerts");
                                JSONObject ob =alerts.getJSONObject(0);

                                weather.alerts.setAlerts(ob.getString("description"));
                                if(alerts == null || alerts.length() == 0){
                                    return;
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

            return data;
        }

        private String getWeatherData(String location) {

            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + location+"&APPID="+APPID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            WeatherStore a = getWeatherStore(response);

                            setWeather(a);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);

            return data;
        }


        private void setWeather(WeatherStore weather) {

            temp.setText("" + Math.round((weather.temperature.getTemp() -273.15 )) + "C");

            rainFall.setText(""+weather.rain.getAmount());
            if(weather.alerts.getAlerts()!=null){
                alerts.setText(weather.alerts.getAlerts());
            }
            minTemp.setText("Minimum temperature: "+Math.round((weather.temperature.getMinTemp() -273.15 ))+"C");
            maxTemp.setText("Maximum temperature:"+Math.round((weather.temperature.getMaxTemp() -273.15 ))+"C");
            condDescr.setText(weather.currentCondition.getDescr() + ")");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
           windSpeed.setText("" + weather.wind.getSpeed() + " mps");
        }
    }
