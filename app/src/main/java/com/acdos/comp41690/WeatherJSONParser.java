package com.acdos.comp41690;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherJSONParser {

    public static WeatherStore getWeather(String data) throws JSONException  {
        WeatherStore weather = new WeatherStore();

        JSONObject jObj = new JSONObject(data);


        JSONObject sysObj = getObject("sys", jObj);
        weather.sunTimes.setSunrise(getInt("sunrise", sysObj));
        weather.sunTimes.setSunset(getInt("sunset", sysObj));

        JSONArray jArr = jObj.getJSONArray("weather");

        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setDescr(getString("description", JSONWeather));


        JSONObject mainObj = getObject("main", jObj);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        weather.wind.setSpeed(getFloat("speed", wObj));
        return weather;
    }

//    public static WeatherStore getWeather(String data) throws JSONException {
//        WeatherStore weather = new WeatherStore();
//
//        JSONObject jObj = new JSONObject(data);
//
//
//        JSONObject sysObj = getObject("alerts", jObj);
//        weather.alerts.setAlerts(get);
//    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
