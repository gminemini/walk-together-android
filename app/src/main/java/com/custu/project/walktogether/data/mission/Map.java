
/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

package com.custu.project.walktogether.data.mission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Map {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("latitude")
    @Expose
    private Long latitude;
    @SerializedName("longitude")
    @Expose
    private Long longitude;
    @SerializedName("namePlace")
    @Expose
    private String namePlace;
    @SerializedName("image")
    @Expose
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
