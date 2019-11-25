package com.acdos.comp41690.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import org.json.JSONException;


import com.acdos.comp41690.HttpClient;
import com.acdos.comp41690.JSONParser;
import com.acdos.comp41690.WeatherStore;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.ViewModelProviders;

import com.acdos.comp41690.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;

    private TextView hum;
    private ImageView imgView;

    @Override


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String city = "London,UK";
        temp = root.findViewById(R.id.temp);

       //  JSONWeatherTask test =new JSONWeatherTask();


        String result = getWeatherData(city);

        System.out.println("anseo"+ result);


//        task.execute(new String[]{city});



        return root;
    }



        private  String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private  String APPID = "e0af00f6b30b672fbc3058d39d79c3ee";
        public   String data="";

        protected WeatherStore getWeatherStore(String data) {
            WeatherStore weather = new WeatherStore();

            try {
                weather = JSONParser.getWeather(data);

                // Let's retrieve the icon
                System.out.println();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }
        public String getWeatherData(String location) {

            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + location+"&APPID="+APPID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            System.out.println("Response is: "+ response.substring(0,400));
                            //returnMyString(response);
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
        private void returnMyString(String respond){
            System.out.println("THE response IS "+respond);
            //data = respond;
            System.out.println("THE DATA IS "+data);
        }

        protected void setWeather(WeatherStore weather) {


            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }


            temp.setText("" + Math.round((weather.temperature.getTemp() -273.15 )) + "C");



//            windDeg.setText("" + weather.wind.getDeg() + "");
//            condDescr.setText(weather.currentCondition.getCondition() + "("
//                    + weather.currentCondition.getDescr() + ")");
//            hum.setText("" + weather.currentCondition.getHumidity() + "%");
//            press.setText("" + weather.currentCondition.getPressure() + " hPa");
//            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
        }
    }
