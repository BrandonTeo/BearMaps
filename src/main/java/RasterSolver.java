/**
 * Created by Brandon on 4/16/16.
 */

import java.util.ArrayList;
import java.util.Collections;

public class RasterSolver {
    private QuadTree tree;
    private RasterSolution solution;
    private ArrayList<QTreeNode> tileList;

    public class RasterSolution {
        private double rasterullon;
        private double rasterullat;
        private double rasterlrlon;
        private double rasterlrlat;
        private int rasterwidth;
        private int rasterheight;
        private int rasterdepth;
        private boolean querySuccess;

        public RasterSolution() {
            this.rasterullon = 0.0;
            this.rasterullat = 0.0;
            this.rasterlrlon = 0.0;
            this.rasterlrlat = 0.0;
            this.rasterwidth = 0;
            this.rasterheight = 0;
            this.rasterdepth = 0;
            this.querySuccess = false;
        }

        public double getRasterUllon() { 
            return this.rasterullon; 
        }
        public double getRasterUllat() { 
            return this.rasterullat; 
        }
        public double getRasterLrlon() { 
            return this.rasterlrlon; 
        }
        public double getRasterLrlat() { 
            return this.rasterlrlat; 
        }
        public int getRasterWidth() { 
            return this.rasterwidth; 
        }
        public int getRasterHeight() { 
            return rasterheight; 
        }
        public int getRasterDepth() { 
            return this.rasterdepth; 
        }
        public boolean getQuerySuccess() { 
            return this.querySuccess; 
        }
    }

    public RasterSolver(QuadTree tree, double queryullon, double queryullat, double querylrlon, 
                        double querylrlat, double querywidth, double queryheight) {
        this.tree = tree;
        this.solution = new RasterSolution();
        this.tileList = new ArrayList<QTreeNode>();
        traverse(queryullon, querylrlon, querywidth, queryullat, querylrlat, queryheight);
        Collections.sort(this.tileList, new TileComparator());

        /*
        for (QTreeNode element : this.tileList) {
            System.out.println(element.getValue());
        }
        */

        int count = 0;
        int col = 0;
        //System.out.println(this.tileList.size());
        while (count + 1 < this.tileList.size()) {
            if (this.tileList.get(count).getMid().getY() 
                == this.tileList.get(count + 1).getMid().getY()) {
                col++;
            } else {
                col++;
                break;
            }
            count++;
        }
        if (count + 1 >= this.tileList.size()) {
            col++;
        }
        //System.out.println("hahaha" + this.tileList.size());
        //System.out.println("fuck" + col);
        int row = this.tileList.size() / col;
        this.solution.rasterwidth = col * 256;
        this.solution.rasterheight = row * 256;


        this.solution.rasterullon = this.tileList.get(0).getULeft().getX();
        this.solution.rasterullat = this.tileList.get(0).getULeft().getY();
        this.solution.rasterlrlon = this.tileList.get(this.tileList.size() - 1).getLRight().getX();
        this.solution.rasterlrlat = this.tileList.get(this.tileList.size() - 1).getLRight().getY();
        this.solution.querySuccess = true;
        //this.solution.printAll();
    }


    public void traverse(double ullon, double lrlon, double width, 
                         double ullat, double lrlat, double height) {
        double dpp = (lrlon - ullon) * 288200 / width;
        traverseHelper(ullon, lrlon, ullat, lrlat, tree.getRoot(), dpp);
    }

    public void traverseHelper(double ullon, double lrlon, double ullat, 
                               double lrlat, QTreeNode ptr, double dpp) {
        if (ptr.intersectsTile(ullon, lrlon, ullat, lrlat)) {
            if (ptr.satisfiesDepthOrIsLeaf(dpp)) {
                this.solution.rasterdepth = ptr.getDepth();
                tileList.add(ptr);
            } else {
                traverseHelper(ullon, lrlon, ullat, lrlat, ptr.getChild(1), dpp);
                traverseHelper(ullon, lrlon, ullat, lrlat, ptr.getChild(2), dpp);
                traverseHelper(ullon, lrlon, ullat, lrlat, ptr.getChild(3), dpp);
                traverseHelper(ullon, lrlon, ullat, lrlat, ptr.getChild(4), dpp);
            }
        }
    }

    public ArrayList<QTreeNode> getTileList() {
        return this.tileList;
    }

    public RasterSolution getSolution() {
        return this.solution;
    }
}
