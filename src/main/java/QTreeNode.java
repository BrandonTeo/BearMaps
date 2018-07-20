/**
 * Created by Brandon on 4/15/16.
 */
public class QTreeNode {

    private Pair uLeft;
    private Pair lRight;
    private Pair midPt;
    private String value;
    private int depth;
    private QTreeNode NE;
    private QTreeNode NW;
    private QTreeNode SE;
    private QTreeNode SW;

    public QTreeNode(double ulLon, double ulLat, double lrLon, 
                     double lrLat, String value, int depth) {
        this.uLeft = new Pair(ulLon, ulLat);
        this.lRight = new Pair(lrLon, lrLat);
        this.midPt = new Pair((ulLon + lrLon) / 2, (ulLat + lrLat) / 2);
        this.value = value;
        this.depth = depth;
        this.NE = null;
        this.NW = null;
        this.SE = null;
        this.SW = null;
    }

    public void updateChild(QTreeNode toBeAdded, int quadrant) {
        if (quadrant == 1) {
            this.NW = toBeAdded;
        } else if (quadrant == 2) {
            this.NE = toBeAdded;
        } else if (quadrant == 3) {
            this.SW = toBeAdded;
        } else {
            this.SE = toBeAdded;
        }
    }

    public String getValue() {
        return this.value;
    }

    public int getDepth() {
        return this.depth;
    }

    public double getDPP() {
        return (getLRight().getX() - getULeft().getX()) * 288200 / 256;
    }

    public QTreeNode getChild(int quadrant) {
        if (quadrant == 1) {
            return this.NW;
        } else if (quadrant == 2) {
            return this.NE;
        } else if (quadrant == 3) {
            return this.SW;
        } else {
            return this.SE;
        }
    }

    public Pair getULeft() {
        return this.uLeft;
    }

    public Pair getLRight() {
        return this.lRight;
    }

    public Pair getMid() {
        return this.midPt;
    }

    public boolean isLeaf() { 
        return this.SE == null; 
    }

    public boolean intersectsTile(double queryullon, double querylrlon, 
                                  double queryullat, double querylrlat) {

        double myullon = this.getULeft().getX();
        double mylrlon = this.getLRight().getX();
        double myullat = this.getULeft().getY();
        double mylrlat = this.getLRight().getY();

        return !(myullon > querylrlon || mylrlon < queryullon 
                 || myullat < querylrlat || mylrlat > queryullat);
    }

    public boolean satisfiesDepthOrIsLeaf(double querydpp) {
        return (getDPP() < querydpp) || isLeaf();
    }
}
