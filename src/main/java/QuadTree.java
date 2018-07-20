/**
 * Created by Brandon on 4/15/16.
 */
public class QuadTree {

    private QTreeNode root;

    public QuadTree(double ulx, double uly, double lrx, double lry, int depth) {
        this.root = new QTreeNode(ulx, uly, lrx, lry, "", 0);
        completeQuadTree(root, depth, 0);
    }

    public void completeQuadTree(QTreeNode curr, int depth, int count) {
        count++;
        if (depth == 0) {
            return;
        } else {
            QTreeNode q1 = new QTreeNode(curr.getULeft().getX(), curr.getULeft().getY(), 
                                         curr.getMid().getX(), curr.getMid().getY(), 
                                         curr.getValue() + "1", count);
            curr.updateChild(q1, 1);
            QTreeNode q2 = new QTreeNode(curr.getMid().getX(), curr.getULeft().getY(), 
                                         curr.getLRight().getX(), curr.getMid().getY(), 
                                         curr.getValue() + "2", count);
            curr.updateChild(q2, 2);
            QTreeNode q3 = new QTreeNode(curr.getULeft().getX(), curr.getMid().getY(), 
                                         curr.getMid().getX(), curr.getLRight().getY(), 
                                         curr.getValue() + "3", count);
            curr.updateChild(q3, 3);
            QTreeNode q4 = new QTreeNode(curr.getMid().getX(), curr.getMid().getY(), 
                                         curr.getLRight().getX(), curr.getLRight().getY(), 
                                         curr.getValue() + "4", count);
            curr.updateChild(q4, 4);

            completeQuadTree(curr.getChild(1), depth - 1, count);
            completeQuadTree(curr.getChild(2), depth - 1, count);
            completeQuadTree(curr.getChild(3), depth - 1, count);
            completeQuadTree(curr.getChild(4), depth - 1, count);
        }
    }

    public QTreeNode getRoot() {
        return root;
    }

}
