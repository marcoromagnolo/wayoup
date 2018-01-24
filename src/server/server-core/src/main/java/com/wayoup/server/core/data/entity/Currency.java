package com.wayoup.server.core.data.entity;

/**
 * Created by Marco Romagnolo on 23/03/2015.
 */
public class Currency extends AbstractEntity {

    private String name;
    private String symbol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
