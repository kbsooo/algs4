public class LineSegment {
    private final Point p;  
    private final Point q;

    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("Arguments to LineSegment constructor cannot be null");
        }
        this.p = p;
        this.q = q;
    }

    public String toString() {
        return p + " -> " + q;
    }

    public void draw() {
        p.drawTo(q);
    }
}