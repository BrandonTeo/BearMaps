import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 * @author Alan Yao
 */
public class GraphDB {

    private HashMap<Long, MapNode> nodeMap;
    /**
     * Example constructor shows how to create and start an XML parser.
     * @param dbpath Path to the XML file to be parsed.
     */
    public GraphDB(String dbpath) {
        try {
            this.nodeMap = new HashMap<Long, MapNode>();
            File inputFile = new File(dbpath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    public void addNode(MapNode n) {
        this.nodeMap.put(n.getId(), n);
    }

    public MapNode getNode(long k) {
        return this.nodeMap.get(k);
    }

    public boolean containsNode(long k) {
        return this.nodeMap.containsKey(k);
    }

    public MapNode getClosestNode(double lon, double lat) {
        long currClosestID = 0;
        double currClosestDistance = -1.0;
        Set entrySet = this.nodeMap.entrySet();
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            MapNode curr = (MapNode) me.getValue();
            double value = curr.distanceFromPt(lon, lat);
            if (currClosestDistance == -1.0) {
                currClosestDistance = value;
                currClosestID = curr.getId();
            } else if (value < currClosestDistance) {
                currClosestDistance = value;
                currClosestID = curr.getId();
            }
        }
        return getNode(currClosestID);
    }
    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        ArrayList<Long> lst = new ArrayList<Long>();
        Set entrySet = this.nodeMap.entrySet();
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            MapNode curr = (MapNode) me.getValue();
            if (!curr.isConnected()) {
                lst.add(curr.getId());
            }
        }
        for (long element : lst) {
            this.nodeMap.remove(element);
        }
    }
}
