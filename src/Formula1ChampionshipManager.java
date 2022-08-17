import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {
    private final int[] POINTS_SCHEME = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};
    private ArrayList<ConstructorTeam> constructorTeams = new ArrayList<>();
    private int noOfDriversPlaying;
    private ArrayList<Race> races = new ArrayList<>();

    Scanner input = new Scanner(System.in);

    public void showConsoleMenu() {
        while (true) {
            System.out.println("*****MENU*****\n" +
                    "1: Create a new driver\n" +
                    "2: Delete a driver and the team\n" +
                    "3: Change the driver for an existing constructor team\n" +
                    "4: Display statistics for an existing driver\n" +
                    "5: Display the Formula1 Driver Table\n" +
                    "6: Add a race completed with its date and the positions that all the drivers achieved\n" +
                    "7: Save all information entered\n" +
                    "8: resume/recover the previous state of the program\n" +
                    "9: Open GUI\n" +
                    "10: Exit Program\n" +
                    "Enter Menu No:"
            );
            int usrInput = input.nextInt();
            input.nextLine();// todo handle input mismatch

            switch (usrInput) {
                case 1:
                    System.out.print("Enter Driver's Name : ");
                    String dName = input.nextLine();
                    if (!isDriverExist(dName)) {
                        System.out.print("Enter Driver's Location : ");
                        String dLocation = input.nextLine();
                        System.out.print("Enter Driver's Team : ");
                        String dTeamName = input.nextLine();
                        if (!isTeamExist(dTeamName)) {
                            createDriverWithATeam(dName, dLocation, dTeamName);
                        } else {
                            System.out.println("Team " + dTeamName + " already has a driver");
                        }
                    } else {
                        System.out.println("Driver " + dName + " is already playing in the championship");
                    }
                    break;

                case 2:
                    System.out.print("Enter driver's Name : ");
                    String driverName = input.nextLine();
                    if (isDriverExist(driverName)) {
                        deleteDriverNTeam(driverName);
                    } else {
                        System.out.println("Driver does not exist");
                    }
                    break;

                case 3:
                    System.out.print("Enter the existing constructor team's name : ");
                    String cTName = input.nextLine();
                    if (isTeamExist(cTName)) {
                        System.out.print("Enter new driver's Name : ");
                        String drName = input.nextLine();
                        if (!isDriverExist(drName)) {
                            System.out.print("Enter Driver's Location : ");
                            String dLocation = input.nextLine();
                            Formula1Driver f1driver = new Formula1Driver(drName, dLocation);
                            changeDriverForATeam(cTName, f1driver);
                        }
                    } else {
                        System.out.println("Team " + cTName + "does not exist");
                    }
                    break;

                case 4:
                    System.out.println("Enter Driver's Name");
                    String drName = input.nextLine();
                    if (isDriverExist(drName)) {
                        displayDriverStats(drName);
                    } else {
                        System.out.println("Driver does not exist");
                    }
                    break;

                case 5:
                    displayDriverTable();
                    break;

                case 6:
                    addRace();
                    break;

                case 7:
                    saveState();
                    break;
                case 8:
                    loadState();
                    break;
                case 9:
                    loadGui();
                    break;
                case 10:
                    System.out.println("Exiting Program.....");
                    input.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("This is not a valid Menu Option! Please Select Another");
                    break;
            }
        }
    }

    //checks if a driver is already in the championship
    private boolean isDriverExist(String name) {
        for (ConstructorTeam team : constructorTeams) {
            if (team.getF1Driver().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    //checks if a team is already in the championship
    private boolean isTeamExist(String name) {
        for (ConstructorTeam team : constructorTeams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    //creates a new driver with a team
    @Override
    public void createDriverWithATeam(String dName, String dLocation, String teamName) {
        Formula1Driver f1Driver = new Formula1Driver(dName, dLocation);
        ++noOfDriversPlaying;
        ConstructorTeam cTeam = new ConstructorTeam(teamName, f1Driver);
        constructorTeams.add(cTeam);
        System.out.println("Driver " + dName + " was added to team " + teamName + " successfully.");
    }


    //deletes a driver with the associated team
    @Override
    public void deleteDriverNTeam(String name) {
        for (ConstructorTeam team : constructorTeams) {
            if (team.getF1Driver().getName().equalsIgnoreCase(name)) {
                constructorTeams.remove(team);
                --noOfDriversPlaying;
                break;
            }
        }
        System.out.println("Driver " + name + " has removed successfully with the constructor team.");
    }

    //changes the driver for an existing team
    @Override
    public void changeDriverForATeam(String teamName, Formula1Driver driver) {
        for (ConstructorTeam team : constructorTeams) {
            if (team.getName().equalsIgnoreCase(teamName)) {
                team.setF1Driver(driver);
                break;
            }
        }
        System.out.println("Team " + teamName + "'s driver has changed successfully.");
    }

    //displays various statistics for a specific driver
    public void displayDriverStats(String name) {
        for (ConstructorTeam team : constructorTeams) {
            if (team.getF1Driver().getName().equalsIgnoreCase(name)) {
                System.out.println("Driver's name : " + team.getF1Driver().getName());
                System.out.println("Driver's constructor team : " + team.getName());
                System.out.println("Driver's location : " + team.getF1Driver().getLocation());
                System.out.println("Number of points that the driver currently has : " + team.getF1Driver().getPoints());
                System.out.println("Number of races participated so far in the season : " + team.getF1Driver().getRacesParticipated());
                for (int i = 0; i < team.getF1Driver().getNoOfPodiumPositions().length; i++) {
                    System.out.println("Number of " + (i + 1) + " positions " + team.getF1Driver().getNoOfPodiumPositions()[i]);
                }
                break;
            }
        }

    }

    public void displayDriverTable() {
        Collections.sort(constructorTeams);
        System.out.format("+------+--------------+----------------+------------------+-------------+---------------+---------------+---------------+------------+%n");
        System.out.format("| RANK |    DRIVER    |    LOCATION    | CONSTRUCTOR TEAM | NO OF RACES | 1ST POSITIONS | 2ND POSITIONS | 3RD POSITIONS |** POINTS **|%n");
        System.out.format("+------+--------------+----------------+------------------+-------------+---------------+---------------+---------------+------------+%n");

        for (int i = 0; i < constructorTeams.size(); i++) {
            ConstructorTeam ct = constructorTeams.get(i);
            System.out.format("| %-4d | %-12.12s | %-14.14s | %-16.16s | %-11d |", i + 1, ct.getF1Driver().getName(), ct.getF1Driver().getLocation(), ct.getName(), ct.getF1Driver().getRacesParticipated());
            for (int j : ct.getF1Driver().getNoOfPodiumPositions()) {
                System.out.format(" %-13d |", j);
            }
            System.out.format(" %-10d |%n", ct.getF1Driver().getPoints());
        }
        System.out.format("+------+--------------+----------------+------------------+-------------+---------------+---------------+---------------+------------+%n");
    }

    @Override
    public void addRace(){
        while (true) {
            try {
                if (noOfDriversPlaying != 0) {
                    System.out.println("Enter the date in (yyyy-MM-dd) Format: ");
                    LocalDate date = LocalDate.parse(input.nextLine());
                    ConstructorTeam[] positions = new ConstructorTeam[noOfDriversPlaying];
                    for (ConstructorTeam team : constructorTeams) {
                        while (true) {
                            try {
                                System.out.print("Position achieved by driver " + team.getF1Driver().getName() + " is : ");
                                int position = input.nextInt();
                                if (positions[position - 1] == null) {
                                    positions[position - 1] = team;
                                    if (position <= POINTS_SCHEME.length) {
                                        team.getF1Driver().setPoints(POINTS_SCHEME[position - 1]);
                                        if (position <= team.getF1Driver().getNoOfPodiumPositions().length) {
                                            team.getF1Driver().setNoOfPodiumPositions(position);
                                        }
                                    }
                                    team.getF1Driver().setRacesParticipated(1);
                                    break;
                                } else {
                                    System.out.println("Driver " + positions[position - 1].getF1Driver().getName() + " has already archived position " + position);
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Try Again !!!, Incorrect input (integer is required)");
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Try Again !!!, Position must be between (1 - " + noOfDriversPlaying + ")");
                            } finally {
                                input.nextLine();
                            }
                        }
                    }
                    Race r = new Race(noOfDriversPlaying, date, positions);
                    races.add(r);
                } else {
                    System.out.println("Add some Drivers First to add a Race !");
                }
                break;
            } catch (Exception e) {
                System.out.println("Wrong Date Format Try Again!!");
            }
        }
    }

    private void saveState() {
        try {
            FileOutputStream fileOut = new FileOutputStream("file.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(constructorTeams);
            out.writeObject(races);
            out.flush();
            out.close();
            fileOut.close();
            System.out.println("State Saved Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadState() {
        try {
            FileInputStream file = new FileInputStream("file.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            constructorTeams = (ArrayList<ConstructorTeam>) in.readObject();
            races = (ArrayList<Race>) in.readObject();
            noOfDriversPlaying = constructorTeams.size();
            in.close();
            file.close();
            System.out.println("loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("!!!! FAILED TO RECOVER THE STATE !!!!");
        }
    }

    private void loadGui() {
        Formula1ChampionshipManagerGui gui = new Formula1ChampionshipManagerGui(POINTS_SCHEME, races, constructorTeams);
    }
}