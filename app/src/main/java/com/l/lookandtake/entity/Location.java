package com.l.lookandtake.entity;

/**
 * Created by L on 2018/4/16.
 * Description:
 */
public class Location {

    /**
     * title : Isla Holbox, Mexico
     * name : Isla Holbox
     * city : null
     * country : Mexico
     * position : {"latitude":21.5308421,"longitude":-87.2866995}
     */

    private String title;
    private String name;
    private Object city;
    private String country;
    private PositionBean position;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public static class PositionBean {
        /**
         * latitude : 21.5308421
         * longitude : -87.2866995
         */

        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
