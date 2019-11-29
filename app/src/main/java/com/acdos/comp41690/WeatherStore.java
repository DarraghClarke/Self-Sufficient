package com.acdos.comp41690;
/* This class is used to store the data taken from the OpenWeather and HERE api after it has
been through the JsonParser class. This data will then be called by the Homefragment to set
them on their respective text views
 */


public class WeatherStore {

    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Alerts alerts = new Alerts();

    public class CurrentCondition {

        private float pressure;
        private float humidity;
        private String descr;

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

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }


    }
    public class Alerts{
        private String alerts;
        public String getAlerts() {
            return alerts;
        }

        public void setAlerts(String alerts) {
            this.alerts = alerts;
        }
    }
}
