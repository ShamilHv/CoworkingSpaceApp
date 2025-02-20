package com.shamil.enums;

public enum SpaceType {
    OPEN_SPACE("Open Space"),
    PRIVATE_DESK("Private Desk"),
    MEETING_ROOM("Meeting Room"),
    OFFICE("Office");

    private final String spaceName;

    public String getSpaceName() {
        return spaceName;
    }

    SpaceType(String spaceName) {
        this.spaceName = spaceName;
    }
}
