public class Formula1Driver extends Driver {
    private int racesParticipated;
    private int points;
    private int [] noOfPodiumPositions = new int[3]; // stores the count of each podium position (1st, 2nd, 3rd places)

    public Formula1Driver(String name, String location) {
        super(name, location);
    }

    public int getRacesParticipated() {
        return racesParticipated;
    }

    public void setRacesParticipated() {
        ++this.racesParticipated;
    }

    public void setRacesParticipated(int racesParticipated) {
        this.racesParticipated += racesParticipated;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public int[] getNoOfPodiumPositions() {
        return noOfPodiumPositions;
    }

    //returns the count of a specific podium position
    public int getNoOfPodiumPositions(int position) {
        return noOfPodiumPositions[position];
    }

    public void setNoOfPodiumPositions(int[] noOfPodiumPositions) {
        this.noOfPodiumPositions = noOfPodiumPositions;
    }

    //increases the count of a specific podium position
    public void setNoOfPodiumPositions(int podiumPosition) {
        ++this.noOfPodiumPositions[podiumPosition - 1];
    }

}
