import java.io.Serializable;

public abstract class Driver implements Serializable {
    private static final long SerialVersionUID = 30L;
    private String name;
    private String location;

    public Driver(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
