import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Formula1ChampionshipManagerGui extends JFrame {

    private final ArrayList<ConstructorTeam> constructorTeams;
    private final ArrayList<Race> races;
    private final int[] POINTS_SCHEME;

    private final JPanel raceHistoryJp = new JPanel(new BorderLayout());
    private final JTextArea log = new JTextArea(0, 35);
    private final JTabbedPane tabbedPane = new JTabbedPane();


    public Formula1ChampionshipManagerGui(int[] POINTS_SCHEME, ArrayList<Race> races, ArrayList<ConstructorTeam> constructorTeams) {
        super("Formula1 Championship Manager | GUI");

        this.races = races;
        this.constructorTeams = constructorTeams;
        this.POINTS_SCHEME = POINTS_SCHEME;

        JTable driverStandingsJT = new JTable();
        JTable raceHistoryJt = new JTable();
        log.setEditable(false);

        DefaultTableModel driverStandingsTModel = new DefaultTableModel();
        String[] dTHeaders = {"RANK", "DRIVER", "LOCATION", "TEAM", "NO OF RACES", "1ST POSITIONS", "2ND POSITIONS", "3RD POSITIONS", "POINTS"};
        driverStandingsTModel.setColumnIdentifiers(dTHeaders);
        Collections.sort(constructorTeams);
        driverStandingsJT.setModel(populateDSTableModel(driverStandingsTModel, constructorTeams));

        TableRowSorter sorter = new TableRowSorter();
        driverStandingsJT.setRowSorter(sorter);
        sorter.setModel(driverStandingsJT.getModel());


        JPanel driverStandingsJp = new JPanel((new BorderLayout()));
        tabbedPane.addTab("Driver Standings", driverStandingsJp);

        DefaultTableModel rHistoryTModel = new DefaultTableModel();
        String[] rTHeaders = {"Date", "Participants Amount", "1st Pos", "2nd Pos", "3rd Pos"};
        rHistoryTModel.setColumnIdentifiers(rTHeaders);

        raceHistoryJt.setModel(populateRaceHTModel(rHistoryTModel));
        driverStandingsJp.add(new JScrollPane(driverStandingsJT), "Center");
        raceHistoryJp.add(new JScrollPane(raceHistoryJt), "Center");

        tabbedPane.addTab("Race History", raceHistoryJp);

        JButton jb1 = new JButton("Generate Random Race");
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                generateRandomRace();
                DefaultTableModel driverStandingsJTModel = (DefaultTableModel) driverStandingsJT.getModel();
                driverStandingsJTModel.setRowCount(0);
                Collections.sort(constructorTeams);
                populateDSTableModel(driverStandingsJTModel,constructorTeams);
                driverStandingsJT.setAutoCreateRowSorter(true);
                DefaultTableModel raceHistoryJtModel = (DefaultTableModel) raceHistoryJt.getModel();
                raceHistoryJtModel.setRowCount(0);
                populateRaceHTModel(raceHistoryJtModel);
            }
        });

        JButton jb3 = new JButton("Show Completed Races");
        jb3.addActionListener(actionEvent -> tabbedPane.setSelectedComponent(raceHistoryJp));

        JPanel footerPane = new JPanel(new FlowLayout());
        footerPane.add(jb1);
        footerPane.add(jb3);

        JLabel titleLbl = new JLabel("Formula1 Championship Manager", SwingConstants.CENTER);
        add(titleLbl, "North");
        add(tabbedPane, "Center");
        JScrollPane logJSp = new JScrollPane(log);
        add(logJSp, "East");
        add(footerPane, "South");
        setSize(1268, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    private DefaultTableModel populateDSTableModel(DefaultTableModel driverSTModel, ArrayList<ConstructorTeam> constructorTeams) {
        for (int i = 0; i < constructorTeams.size(); i++) {
            Object[] rowData = new Object[driverSTModel.getColumnCount()];
            rowData[0] = i + 1;
            rowData[1] = constructorTeams.get(i).getF1Driver().getName();
            rowData[2] = constructorTeams.get(i).getF1Driver().getLocation();
            rowData[3] = constructorTeams.get(i).getName();
            rowData[4] = constructorTeams.get(i).getF1Driver().getRacesParticipated();
            rowData[5] = constructorTeams.get(i).getF1Driver().getNoOfPodiumPositions(0);
            rowData[6] = constructorTeams.get(i).getF1Driver().getNoOfPodiumPositions(1);
            rowData[7] = constructorTeams.get(i).getF1Driver().getNoOfPodiumPositions(2);
            rowData[8] = constructorTeams.get(i).getF1Driver().getPoints();
            driverSTModel.addRow(rowData);
        }
        return driverSTModel;
    }


    private DefaultTableModel populateRaceHTModel(DefaultTableModel rHistTModel) {
        Collections.sort(races, new Comparator<Race>(){
            @Override
            public int compare(Race r1, Race r2) {
                return r1.getRaceCompletedDate().compareTo(r2.getRaceCompletedDate());
            }
        });
        for (Race race : races) {
            Object[] rowData = new Object[rHistTModel.getColumnCount()];
            rowData[0] = race.getRaceCompletedDate();
            rowData[1] = race.getParticipantsAmount();
            rowData[2] = race.getPositions()[0].getF1Driver().getName();
            rowData[3] = race.getPositions()[1].getF1Driver().getName();
            rowData[4] = race.getPositions()[2].getF1Driver().getName();
            rHistTModel.addRow(rowData);
        }
        return rHistTModel;
    }

    private void generateRandomRace() {
        Random random = new Random();
        while (true) {
            try {
                int year = 2021;
                int month = 1 + random.nextInt(12);
                int dayOfMonth = 1 + random.nextInt(31);
                LocalDate date = LocalDate.of(year, month, dayOfMonth);
                Collections.shuffle(constructorTeams);

                //assigns random positions for all drivers
                ConstructorTeam[] positions = constructorTeams.toArray(new ConstructorTeam[constructorTeams.size()]);

                for (int i = 0; i < constructorTeams.size(); i++) {
                    constructorTeams.get(i).getF1Driver().setRacesParticipated(1);
                    if (i < POINTS_SCHEME.length) {
                        constructorTeams.get(i).getF1Driver().setPoints(POINTS_SCHEME[i]);
                        if (i < constructorTeams.get(i).getF1Driver().getNoOfPodiumPositions().length) {
                            constructorTeams.get(i).getF1Driver().setNoOfPodiumPositions(i + 1);
                        }
                    }

                }
                Race race = new Race(constructorTeams.size(), date, positions);

                log.append("Race Completed Date : " + race.getRaceCompletedDate().toString());
                for (int i = 0; i < positions.length; i++) {
                    log.append("\n\nPosition : " + (i + 1) + " Driver : " + positions[i].getF1Driver().getName());
                }
                log.append("\n\n++++++++++++++++++++++\n\n");
                races.add(race);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}