public interface ChampionshipManager {
    void createDriverWithATeam(String name, String location, String team);
    void deleteDriverNTeam(String name);
    void changeDriverForATeam(String teamName, Formula1Driver driver);
    void addRace();
}
