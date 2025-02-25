package com.shamil.service;

import com.shamil.enums.SpaceType;
import com.shamil.model.Space;
import com.shamil.repository.SpaceRepository;

import java.util.List;
import java.util.UUID;

public class SpaceService {
    private SpaceRepository spaceRepository;

    public SpaceService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    public Space addSpace(String name, SpaceType type, double pricePerHour) {
        String id = UUID.randomUUID().toString();
        Space space = new Space(id, name, type, pricePerHour);
        return spaceRepository.addSpace(space);
    }

    public List<Space> getAllSpaces() {
        return spaceRepository.getAllSpaces();
    }

    public List<Space> getAvailableSpaces() {
        return spaceRepository.getAvailableSpaces();
    }

    public Space getSpaceByName(String spaceName) {
        return spaceRepository.getSpaceByName(spaceName);
    }

    public Space updateSpace(Space space) {
        return spaceRepository.updateSpace(space);
    }

    public boolean removeSpace(String spaceId) {
        return spaceRepository.removeSpaceById(spaceId);
    }

}
