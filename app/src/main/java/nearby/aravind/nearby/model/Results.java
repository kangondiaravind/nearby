package nearby.aravind.nearby.model;

public class Results {

    private String vicinity;
    private Geometry geometry;
    private Location location;
    private String name;

    public String getVicinity() {
        return vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
