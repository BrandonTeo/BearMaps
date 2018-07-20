/**
 * Created by Brandon on 4/16/16.
 */
import java.util.Comparator;

public class TileComparator implements Comparator<QTreeNode> {
    public int compare(QTreeNode q1, QTreeNode q2) {
        if (q1.getMid().getX() == q2.getMid().getX() 
            && q1.getMid().getY() == q2.getMid().getY()) {
            return 0;
        } else if (q1.getMid().getX() == q2.getMid().getX() 
                && q1.getMid().getY() < q2.getMid().getY()) {
            return 1;
        } else if (q1.getMid().getY() == q2.getMid().getY() 
                   && q1.getMid().getX() > q2.getMid().getX()) {
            return 1;
        } else if (q1.getMid().getY() < q2.getMid().getY()) {
            return 1;
        } else {
            return -1;
        }
    }
}
