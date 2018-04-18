package com.l.lookandtake.entity;

/**
 * Created by L on 2018/4/13.
 * Description:
 */
public class Exif {

    /**
     * make : Canon
     * model : Canon EOS 5D Mark III
     * exposure_time : 1/25
     * aperture : 5.0
     * focal_length : 35
     * iso : 800
     */

    private String make;
    private String model;
    private String exposure_time;
    private String aperture;
    private String focal_length;
    private String iso;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getExposure_time() {
        return exposure_time;
    }

    public void setExposure_time(String exposure_time) {
        this.exposure_time = exposure_time;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getFocal_length() {
        return focal_length;
    }

    public void setFocal_length(String focal_length) {
        this.focal_length = focal_length;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
}
