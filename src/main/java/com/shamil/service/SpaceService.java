package com.shamil.service;

import com.shamil.enums.SpaceType;
import com.shamil.exception.ResourceNotFoundException;
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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Space name cannot be empty");
        }
        if (pricePerHour <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

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
        Space space = spaceRepository.getSpaceByName(spaceName);
        if (space == null) {
            throw new ResourceNotFoundException("Space with name '" + spaceName + "' not found");
        }
        return space;
    }

    public Space updateSpace(Space space) {
        Space updatedSpace = spaceRepository.updateSpace(space);
        if (updatedSpace == null) {
            throw new ResourceNotFoundException("Space with ID '" + space.getSpaceId() + "' not found");
        }
        return updatedSpace;
    }

    public boolean removeSpaceByName(String spaceName) {
        boolean removed = spaceRepository.removeSpaceByName(spaceName);
        if (!removed) {
            throw new ResourceNotFoundException("Space with name '" + spaceName + "' not found");
        }
        return true;
    }

}
