package sabel.com.wegepunkte;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WegePunkt implements Serializable{

    // DATA FIELDS
    private Date timestamp;
    private double latitude;
    private double longitude;

    // CONSTRUCTOR

    public WegePunkt(Date timestamp, double latitude, double longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    // METHODS

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

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

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return simpleDateFormat.format(timestamp) + " " + latitude + " " + longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WegePunkt wegePunkt = (WegePunkt) o;
        return Double.compare(wegePunkt.latitude, latitude) == 0 &&
                Double.compare(wegePunkt.longitude, longitude) == 0 &&
                Objects.equals(timestamp, wegePunkt.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, latitude, longitude);
    }
}
