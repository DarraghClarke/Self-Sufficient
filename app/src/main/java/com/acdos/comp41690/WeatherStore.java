package com.acdos.comp41690;



public class WeatherStore {

    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public SunTimes sunTimes = new SunTimes();
    public Alerts alerts = new Alerts();

    public class CurrentCondition {

        private float pressure;
        private float humidity;

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }


    }

    public class SunTimes{
        private int sunrise;
        private int sunset;

        public float getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public float getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }
    }


    public class Temperature {
        private float temp;
        private float minTemp;
        private float maxTemp;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }

        public float getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }

    }

    public class Wind {
        private float speed;
        private float deg;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }


    }

    public class Rain {
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }


    }
    public class Alerts{
        private String[] alerts;
        public String[] getAlerts() {
            return alerts;
        }

        public void setAlerts(String[] alerts) {
            this.alerts = alerts;
        }
    }
}
