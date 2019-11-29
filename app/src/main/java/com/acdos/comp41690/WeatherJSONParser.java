/*  This file is used to take in the data from the http request and process it. This
* data will then be stored into a weather object which will later be accessed by the
* home fragment for setting the text views*/
package com.acdos.comp41690;
/* This file uses code referenced from the open weather api recommended tutorial
"https://www.survivingwithandroid.com/android-openweathermap-app-weather-app/?fbclid=IwAR3_pkIO6kAqLgg5H63m43-QPRGUw7J-7jv7rPVZktDAVrkTBpFZv2eCn90"*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherJSONParser {
//This file is used to take in the data from the http request and process it. This
//* data will then be stored into a weather object which will later be accessed by the
//* home fragment for setting the text views
    public static WeatherStore getWeather(String data) throws JSONException  {
        WeatherStore weather = new WeatherStore();

        JSONObject jObj = new JSONObject(data);
        JSONArray jArr = jObj.getJSONArray("weather");

        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setDescr(JSONWeather.getString("description"));

        JSONObject mainObj = jObj.optJSONObject("main");


        weather.currentCondition.setHumidity(mainObj.getInt("humidity"));
        weather.currentCondition.setPressure(mainObj.getInt("pressure"));
        weather.temperature.setMaxTemp((float) mainObj.getDouble("temp_max"));
        weather.temperature.setMinTemp((float) mainObj.getDouble("temp_min"));
        weather.temperature.setTemp((float) mainObj.getDouble("temp"));

        // Wind
        JSONObject wObj = jObj.optJSONObject("wind");
        if (wObj != null) {

            weather.wind.setSpeed((float) wObj.getDouble("speed"));
        }

        JSONObject rObj = jObj.optJSONObject("rain");

        if (rObj != null) {
            double rainAmount = rObj.optDouble("1h", rObj.optDouble("3h"));
            weather.rain.setAmount((float) rainAmount);
        }

        return weather;
    }
}
