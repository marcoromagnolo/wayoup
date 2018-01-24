package com.wayoup.server.core.data.entity;

/**
 * Created by Marco on 01/08/2015.
 */
public abstract class AbstractEntity {

    private String id;
    private Integer version;

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }
}
