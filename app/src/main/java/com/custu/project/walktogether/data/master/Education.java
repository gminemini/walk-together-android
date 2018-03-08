package com.custu.project.walktogether.data.master;

/**
 * Created by pannawatnokket on 8/3/2018 AD.
 */

public class Education {
    private Long id;
    private String name;

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

    @Override
    public String toString() {
        return getName();
    }
}
