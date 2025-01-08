import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Input array is null");

        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Point is null");
            pointsCopy[i] = points[i];
        }

        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }

        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        Point[] sortedPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                sortedPoints[j] = pointsCopy[j];
            }

            Point p = pointsCopy[i];

            Arrays.sort(sortedPoints, p.slopeOrder());

            int count = 1;
            double currentSlope = p.slopeTo(sortedPoints[0]);

            ArrayList<Point> collinearPoints = new ArrayList<>();
            collinearPoints.add(p);
            collinearPoints.add(sortedPoints[0]);

            for (int j = 1; j < sortedPoints.length; j++) {
                double slope = p.slopeTo(sortedPoints[j]);

                if (Double.compare(slope, currentSlope) == 0) {
                    count++;
                    collinearPoints.add(sortedPoints[j]);
                } else {
                    if (count >= 3 && p.compareTo(findMin(collinearPoints)) == 0) {
                        foundSegments.add(new LineSegment(p, findMax(collinearPoints)));
                    }

                    count = 1;
                    currentSlope = slope;
                    collinearPoints.clear();
                    collinearPoints.add(p);
                    collinearPoints.add(sortedPoints[j]);
                }
            }

            if (count >= 3 && p.compareTo(findMin(collinearPoints)) == 0) {
                foundSegments.add(new LineSegment(p, findMax(collinearPoints)));
            }
        }

        segments = foundSegments.toArray(new LineSegment[0]);
    }

    private Point findMin(ArrayList<Point> points) {
        Point min = points.get(0);
        for (Point p : points) {
            if (p.compareTo(min) < 0) min = p;
        }
        return min;
    }

    private Point findMax(ArrayList<Point> points) {
        Point max = points.get(0);
        for (Point p : points) {
            if (p.compareTo(max) > 0) max = p;
        }
        return max;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
}