package com.custu.project.walktogether.data.master;

/**
 * Created by pannawatnokket on 2/2/2018 AD.
 */

public class SubDistrict {

    private Long id;
    private String name;
    private String zipCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return name;
    }
}
