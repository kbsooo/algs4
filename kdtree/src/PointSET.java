import java.util.TreeSet;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private TreeSet<Point2D> set;

    // construct an empty set of point
    public PointSET() { set = new TreeSet<>(); }

    // is the set empty?
    public boolean isEmpty() { return set.isEmpty(); }

    // number of points in the set
    public int size() { return set.size(); }

    // add the point to the set (if it is not already in the set
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point is null");
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point is null");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point2D p : set) p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("rect is null");

        ArrayList<Point2D> rangePoints = new ArrayList<>();

        for (Point2D p : set)
            if (rect.contains(p)) rangePoints.add(p);

        return rangePoints;
    }

    // a nearest neighbor int the set to point p; null if the set is empty
    public  Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("point is null");

        if (set.isEmpty()) return null;
        Point2D nearest = null;
        double nearestDistanceSquared = Double.POSITIVE_INFINITY;
        for (Point2D point : set) {
            double distanceSquared = p.distanceSquaredTo(point);
            if (distanceSquared < nearestDistanceSquared) {
                nearestDistanceSquared = distanceSquared;
                nearest = point;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
//    public static void main(String[] args)
}