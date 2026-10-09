package com.ridewise;

import com.ridewise.model.Driver;
import com.ridewise.model.FareReceipt;
import com.ridewise.model.Location;
import com.ridewise.model.Ride;
import com.ridewise.model.Rider;
import com.ridewise.Enums.VehicleType;
import com.ridewise.service.DriverService;
import com.ridewise.service.RideService;
import com.ridewise.service.RiderService;
import com.ridewise.strategy.DefaultFareStrategy;
import com.ridewise.strategy.FareStrategy;
import com.ridewise.strategy.NearestDriverStrategy;
import com.ridewise.strategy.PeakHourFareStrategy;
import com.ridewise.strategy.RideMatchingStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        RideMatchingStrategy matching = new NearestDriverStrategy();
        FareStrategy fare = new DefaultFareStrategy();

        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();
        RideService rideService = new RideService(riderService, driverService, matching, fare);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1:
                        addRider(riderService);
                        break;
                    case 2:
                        addDriver(driverService);
                        break;
                    case 3:
                        viewAvailableDrivers(driverService);
                        break;
                    case 4:
                        requestRide(rideService);
                        break;
                    case 5:
                        completeRide(rideService);
                        break;
                    case 6:
                        viewRides(rideService);
                        break;
                    case 7:
                        running = false;
                        System.out.println("Thanks for using RideWise!");
                        break;
                    default: System.out.println("Invalid option. Pick option 1-7.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }



    private static void addRider(RiderService riderService) {
        String name = readString("Rider name: ");
        Location location = readLocation("Rider location");
        Rider rider = riderService.registerRider(name, location);
        System.out.println("Registered: " + rider);
    }

    private static void addDriver(DriverService driverService) {
        String name = readString("Driver name: ");
        Location location = readLocation("Driver location");
        VehicleType type = readVehicleType();
        Driver driver = driverService.registerDriver(name, location, type);
        System.out.println("Registered: " + driver);
    }

    private static void viewAvailableDrivers(DriverService driverService) {
        List<Driver> available = driverService.listAvailableDrivers();
        if (available.isEmpty()) {
            System.out.println("No drivers available.");
            return;
        }
        available.forEach(System.out::println);
    }

    private static void requestRide(RideService rideService) {
        int riderId = readInt("Rider id: ");
        double distance = readDouble("Trip distance (km): ");
        Ride ride = rideService.requestRide(riderId, distance);
        System.out.println("Ride booked: " + ride);
    }

    private static void completeRide(RideService rideService) {
        int rideId = readInt("Ride id: ");
        FareReceipt receipt = rideService.completeRide(rideId);
        System.out.println("Ride completed. " + receipt);
    }

    private static void viewRides(RideService rideService) {
        List<Ride> rides = rideService.getAllRides();
        if (rides.isEmpty()) {
            System.out.println("No rides yet.");
            return;
        }
        rides.forEach(System.out::println);
    }



    private static void printMenu() {
        System.out.println("\n");
        System.out.println("===== Ride Wise =====");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View Rides");
        System.out.println("7. Exit");
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = nextLineOrExit().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(nextLineOrExit().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(nextLineOrExit().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Location readLocation(String label) {
        double x = readDouble(label + " x: ");
        double y = readDouble(label + " y: ");
        return new Location(x, y);
    }

    private static VehicleType readVehicleType() {
        while (true) {
            int choice = readInt("Vehicle type (1=BIKE, 2=AUTO, 3=CAR): ");
            if (choice >= 1 && choice <= VehicleType.values().length) {
                return VehicleType.values()[choice - 1];
            }
            System.out.println("Pick 1, 2 or 3.");
        }
    }

    private static String nextLineOrExit() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Exiting.");
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
