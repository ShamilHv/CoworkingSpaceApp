package com.shamil.repository;

import com.shamil.model.Space;

import java.util.ArrayList;
import java.util.List;

public class SpaceRepository {
    List<Space> spaces =  new ArrayList<>();

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
        return spaces.stream()
                .filter(Space::isAvailable)
                .toList();
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
