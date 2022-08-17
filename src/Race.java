import java.io.Serializable;
import java.time.LocalDate;

public class Race implements Serializable{
    private int participantsAmount;
    private LocalDate raceCompletedDate;
    private ConstructorTeam [] positions = new ConstructorTeam [participantsAmount];

    public Race(int participantsAmount, LocalDate raceCompletedDate, ConstructorTeam[] positions) {
        this.participantsAmount = participantsAmount;
        this.raceCompletedDate = raceCompletedDate;
        this.positions = positions;
    }

    public int getParticipantsAmount() {
        return participantsAmount;
    }

    public void setParticipantsAmount(int participantsAmount) {
        this.participantsAmount = participantsAmount;
    }

    public LocalDate getRaceCompletedDate() {
        return raceCompletedDate;
    }

    public void setRaceCompletedDate(LocalDate raceCompletedDate) {
        this.raceCompletedDate = raceCompletedDate;
    }

    public ConstructorTeam[] getPositions() {
        return positions;
    }

    public void setPositions(ConstructorTeam[] positions) {
        this.positions = positions;
    }
}