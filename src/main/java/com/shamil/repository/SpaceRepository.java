package com.shamil.repository;

import com.shamil.enums.SpaceType;
import com.shamil.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceRepository {
    List<Space> spaces =  new ArrayList<>();


    public SpaceRepository() {
       initializeSampleSpaces();
    }
    public void initializeSampleSpaces() {
        if (spaces.isEmpty()) {
            addSpace(new Space(UUID.randomUUID().toString(), "Open Space A", SpaceType.OPEN_SPACE, 10.0));
            addSpace(new Space(UUID.randomUUID().toString(), "Private Office 1", SpaceType.OFFICE, 35.0));
            addSpace(new Space(UUID.randomUUID().toString(), "Meeting Room Large", SpaceType.MEETING_ROOM, 45.0));
            addSpace(new Space(UUID.randomUUID().toString(), "Desk 101", SpaceType.PRIVATE_DESK, 15.0));
            addSpace(new Space(UUID.randomUUID().toString(), "Open Space B", SpaceType.OPEN_SPACE, 12.0));
        }
    }


    public Space addSpace(Space space) {
        spaces.add(space);
        return space;
    }

    public Space getSpaceByName(String spaceName) {
        return spaces.stream().filter(s -> s.getSpaceName().
                        equals(spaceName)).findFirst().
                        orElse(null);
    }
    public List<Space> getAllSpaces() {
        return spaces;
    }

    public List<Space> getAvailableSpaces() {
        return new  ArrayList<>(spaces);
    }

    public boolean removeSpaceById(String spaceId) {
        Space space = spaces.stream().filter(s -> s.getSpaceName().
                        equals(spaceId)).findFirst().
                orElse(null);
        if(space != null) {
            spaces.remove(space);
            return true;
        }else{
            return false;
        }
    }
    public Space updateSpace(Space updatedSpace) {
        for (int i = 0; i < spaces.size(); i++) {
            if (spaces.get(i).getSpaceId().equals(updatedSpace.getSpaceId())) {
                spaces.set(i, updatedSpace);
                return updatedSpace;
            }
        }
        return null;
    }



}
