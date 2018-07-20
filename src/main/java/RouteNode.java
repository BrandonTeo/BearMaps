/**
 * Created by Brandon on 4/18/16.
 */
public class RouteNode  implements Comparable {
    private MapNode myNode;
    private double endlon;
    private double endlat;
    private double distFromSource;
    private double distToDest;
    private double myPriority;
    private RouteNode prev;

    public RouteNode(MapNode myNode, double endlon, double endlat, 
                     double distFromSource, RouteNode prev) {
        this.myNode = myNode;
        this.endlon = endlon;
        this.endlat = endlat;
        this.distFromSource = distFromSource;
        this.prev = prev;
        this.distToDest = myNode.distanceFromPt(this.endlon, this.endlat);
        this.myPriority = this.distFromSource + this.distToDest;
    }


    @Override
    public int compareTo(Object o) {
        RouteNode other = (RouteNode) o;
        if (this.priority() < other.priority()) {
            return -1;
        } else if (this.priority() == other.priority()) {
            return 0;
        } else {
            return 1;
        }
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
        RouteNode that = (RouteNode) other;
        return that.getNode() == this.getNode();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.myNode.getId());
    }

    public double priority() {
        return this.myPriority;
    }

    public MapNode getNode() {
        return this.myNode;
    }

    public RouteNode getPrev() {
        return this.prev;
    }

    public double getDistFromSource() {
        return this.distFromSource;
    }

    public double getDistToDest() {
        return this.distToDest;
    }
}
