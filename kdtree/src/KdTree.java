import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private boolean orientation;

        public Node(Point2D point, RectHV rect, boolean orientation) {
            this.point = point;
            this.rect = rect;
            this.orientation = orientation;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("입력된 점이 null입니다.");

        root = insert(root, p, new RectHV(0, 0, 1, 1), VERTICAL);
    }

    private Node insert(Node current, Point2D p, RectHV rect, boolean orientation) {
        if (current == null) {
            size++;
            return new Node(p, rect, orientation);
        }
        if (current.point.equals(p)) return current;

        if (current.orientation == VERTICAL) {
            if (p.x() < current.point.x()) {
                // 왼쪽 사각형 영역 계산
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), current.point.x(), rect.ymax());
                current.lb = insert(current.lb, p, leftRect, HORIZONTAL);
            } else {
                RectHV rightRect = new RectHV(current.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                current.rt = insert(current.rt, p, rightRect, HORIZONTAL);
            }
        } else { // HORIZONTAL
            if (p.y() < current.point.y()) {
                RectHV bottomRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), current.point.y());
                current.lb = insert(current.lb, p, bottomRect, VERTICAL);
            } else {
                RectHV topRect = new RectHV(rect.xmin(), current.point.y(), rect.xmax(), rect.ymax());
                current.rt = insert(current.rt, p, topRect, VERTICAL);
            }
        }
        return current;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("입력된 점이 null입니다.");
        return contains(root, p);
    }

    private boolean contains(Node current, Point2D p) {
        if (current == null) return false;
        if (current.point.equals(p)) return true;
        if (current.orientation == VERTICAL) {
            if (p.x() < current.point.x())
                return contains(current.lb, p);
            else
                return contains(current.rt, p);
        } else {
            if (p.y() < current.point.y())
                return contains(current.lb, p);
            else
                return contains(current.rt, p);
        }
    }

    // draw all points to standard draw with subdivisions
    public void draw() { draw(root); }

    private void draw(Node current) {
        if (current == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        current.point.draw();

        if (current.orientation == VERTICAL) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(current.point.x(), current.rect.ymin(), current.point.x(), current.rect.ymax());
        } else {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(current.rect.xmin(), current.point.y(), current.rect.xmax(), current.point.y());
        }
        draw(current.lb);
        draw(current.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("입력된 직사각형이 null입니다.");
        }
        java.util.ArrayList<Point2D> result = new java.util.ArrayList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node current, RectHV query, java.util.ArrayList<Point2D> result) {
        if (current == null) return;
        if (!current.rect.intersects(query)) return;
        if (query.contains(current.point)) result.add(current.point);
        range(current.lb, query, result);
        range(current.rt, query, result);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("입력된 점이 null입니다.");
        if (root == null) return null;
        return nearest(root, p, root.point, Double.POSITIVE_INFINITY);
    }

    private Point2D nearest(Node current, Point2D query, Point2D nearestPoint, double nearestDistanceSquared) {
        if (current == null) return nearestPoint;

        double d = query.distanceSquaredTo(current.point);
        if (d < nearestDistanceSquared) {
            nearestDistanceSquared = d;
            nearestPoint = current.point;
        }

        if (current.orientation == VERTICAL) {
            if (query.x() < current.point.x()) {
                nearestPoint = nearest(current.lb, query, nearestPoint, nearestDistanceSquared);
                nearestDistanceSquared = query.distanceSquaredTo(nearestPoint);
                if (current.rt != null && current.rt.rect.distanceSquaredTo(query) < nearestDistanceSquared)
                    nearestPoint = nearest(current.rt, query, nearestPoint, query.distanceSquaredTo(nearestPoint));
            } else {
                nearestPoint = nearest(current.rt, query, nearestPoint, nearestDistanceSquared);
                nearestDistanceSquared = query.distanceSquaredTo(nearestPoint);
                if (current.lb != null && current.lb.rect.distanceSquaredTo(query) < nearestDistanceSquared)
                    nearestPoint = nearest(current.lb, query, nearestPoint, query.distanceSquaredTo(nearestPoint));
            }
        } else {
            if (query.y() < current.point.y()) {
                nearestPoint = nearest(current.lb, query, nearestPoint, nearestDistanceSquared);
                nearestDistanceSquared = query.distanceSquaredTo(nearestPoint);
                if (current.rt != null && current.rt.rect.distanceSquaredTo(query) < nearestDistanceSquared)
                    nearestPoint = nearest(current.rt, query, nearestPoint, query.distanceSquaredTo(nearestPoint));
            } else {
                nearestPoint = nearest(current.rt, query, nearestPoint, nearestDistanceSquared);
                nearestDistanceSquared = query.distanceSquaredTo(nearestPoint);
                if (current.lb != null && current.lb.rect.distanceSquaredTo(query) < nearestDistanceSquared)
                    nearestPoint = nearest(current.lb, query, nearestPoint, query.distanceSquaredTo(nearestPoint));
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
//    public static void main(String[] args)
}