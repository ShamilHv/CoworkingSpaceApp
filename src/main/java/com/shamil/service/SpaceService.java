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
        /// Initializing some spaces for testing
        initializeSampleSpaces();
    }
    private void initializeSampleSpaces() {
        addSpace("Open Space A", SpaceType.OPEN_SPACE, 10.0);
        addSpace("Private Office 1", SpaceType.OFFICE, 35.0);
        addSpace("Meeting Room Large", SpaceType.MEETING_ROOM, 45.0);
        addSpace("Desk 101", SpaceType.PRIVATE_DESK, 15.0);
        addSpace("Open Space B", SpaceType.OPEN_SPACE, 12.0);
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

    public Space getSpaceById(String id) {
        return spaceRepository.getSpaceById(id);
    }

    public Space updateSpace(Space space) {
        return spaceRepository.updateSpace(space);
    }

    public boolean removeSpace(String spaceId) {
        return spaceRepository.removeSpaceById(spaceId);
    }

}
