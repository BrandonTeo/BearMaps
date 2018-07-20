import java.util.HashSet;

/**
 * Created by Brandon on 4/17/16.
 */
public class MapNode {
    private long id;
    private double lon;
    private double lat;
    private HashSet<MapNode> connections;

    public MapNode(String id, String lon, String lat) {
        this.id = Long.parseLong(id);
        this.lon = Double.parseDouble(lon);
        this.lat = Double.parseDouble(lat);
        this.connections = new HashSet<MapNode>();
    }

    public double distanceFromPt(double longi, double lati) {
        double pt1 = (this.lon - longi) * (this.lon - longi);
        double pt2 = (this.lat - lati) * (this.lat - lati);
        return Math.sqrt(pt1 + pt2);
    }

    public double distanceFromNd(MapNode m) {
        double pt1 = (this.lon - m.getLon()) * (this.lon - m.getLon());
        double pt2 = (this.lat - m.getLat()) * (this.lat - m.getLat());
        return Math.sqrt(pt1 + pt2);
    }

    public boolean isConnected() {
        return !connections.isEmpty();
    }

    public long getId() {
        return id;
    }

    public double getLon() {
        return this.lon;
    }

    public double getLat() {
        return this.lat;
    }

    public Iterable<MapNode> connectionsIter() {
        return this.connections;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { 
            return true; 
        }
        if (other == null) { 
            return false; 
        }
        if (other.getClass() != this.getClass()) { 
            return false; 
        }
        MapNode that = (MapNode) other;
        return that.getId() == this.getId();
    }

    public void addConnection(MapNode other) {
        this.connections.add(other);
    }

}
