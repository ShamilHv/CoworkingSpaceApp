package com.shamil;


import com.shamil.consoleinterface.MainMenu;
import com.shamil.repository.ReservationRepository;
import com.shamil.repository.SpaceRepository;
import com.shamil.repository.UserRepository;
import com.shamil.service.ReservationService;
import com.shamil.service.SpaceService;
import com.shamil.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        SpaceRepository spaceRepository = new SpaceRepository();
        ReservationRepository reservationRepository = new ReservationRepository();

        UserService userService = new UserService(userRepository);
        SpaceService spaceService = new SpaceService(spaceRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, spaceService);

        MainMenu mainMenu = new MainMenu(userService, spaceService, reservationService);
        mainMenu.display();
}
}