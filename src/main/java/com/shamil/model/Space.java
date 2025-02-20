package com.shamil.model;

import com.shamil.enums.SpaceType;

public class Space {
    private String spaceId;
    private String spaceName;
    private SpaceType spaceType;
    private double pricePerHour;
    private boolean available;

    public Space(String spaceId, String spaceName, SpaceType spaceType, double pricePerHour) {
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.spaceType = spaceType;
        this.pricePerHour = pricePerHour;
        this.available = true;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public SpaceType getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(SpaceType spaceType) {
        this.spaceType = spaceType;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public String toString() {
        return "Space{" +
                "spaceId='" + spaceId + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", spaceType=" + spaceType +
                ", pricePerHour=" + pricePerHour +
                ", available=" + available +
                '}';
    }
}
