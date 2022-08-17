import java.io.Serializable;

public class ConstructorTeam implements Serializable, Comparable<ConstructorTeam> {
    private String name;
    private Formula1Driver f1Driver;
    private static final long SerialVersionUID = 40L;

    public ConstructorTeam(String name, Formula1Driver f1Driver) {
        this.name = name;
        this.f1Driver = f1Driver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Formula1Driver getF1Driver() {
        return f1Driver;
    }

    public void setF1Driver(Formula1Driver f1Driver) {
        this.f1Driver = f1Driver;
    }

    @Override
    public int compareTo(ConstructorTeam constructorTeam) {
        if (this.getF1Driver().getPoints() == constructorTeam.getF1Driver().getPoints()) {
            if (this.getF1Driver().getNoOfPodiumPositions()[0] < constructorTeam.getF1Driver().getNoOfPodiumPositions()[0]) {
                return 1;
            }
        } else if (this.getF1Driver().getPoints() < constructorTeam.getF1Driver().getPoints()) {
            return 1;
        } else {
            return -1;
        }
        return 0;
    }
}
