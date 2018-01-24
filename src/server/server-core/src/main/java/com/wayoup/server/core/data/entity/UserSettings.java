package com.wayoup.server.core.data.entity;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Marco on 29/05/2015.
 */
public class UserSettings extends AbstractEntity {

    private Locale locale;
    private TimeZone timezone;
    private boolean showPosition;
    private Date modifyDate;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public boolean isShowPosition() {
        return showPosition;
    }

    public void setShowPosition(boolean showPosition) {
        this.showPosition = showPosition;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
