import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numberOfSegments;

    // 생성자: 점들의 배열을 받아 collinear한 점들을 찾습니다
    public BruteCollinearPoints(Point[] points) {
        // 입력값 검증
        validateInput(points);

        // 입력 배열의 복사본 생성
        Point[] localPoints = points.clone();

        // segments 배열 초기화
        segments = new LineSegment[1];  // 시작은 크기 1로, 필요시 크기를 늘립니다
        numberOfSegments = 0;

        // 네 점이 한 직선 위에 있는 경우를 모두 찾습니다
        findCollinearPoints(localPoints);
    }

    // 입력값 검증을 위한 private 메서드
    private void validateInput(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input array is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point at index " + i + " is null");
            }

            // 중복 점 체크
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate points detected");
                }
            }
        }
    }

    // collinear한 점들을 찾는 private 메서드
    private void findCollinearPoints(Point[] points) {
        int N = points.length;

        // 네 점을 모두 검사
        for (int p = 0; p < N-3; p++) {
            for (int q = p+1; q < N-2; q++) {
                for (int r = q+1; r < N-1; r++) {
                    for (int s = r+1; s < N; s++) {
                        // p를 기준으로 다른 세 점과의 기울기를 계산
                        double slope1 = points[p].slopeTo(points[q]);
                        double slope2 = points[p].slopeTo(points[r]);
                        double slope3 = points[p].slopeTo(points[s]);

                        // 세 기울기가 같다면 네 점은 한 직선 위에 있습니다
                        if (slope1 == slope2 && slope2 == slope3) {
                            // 네 점을 담은 배열을 만들어 정렬
                            Point[] collinear = {points[p], points[q],
                                               points[r], points[s]};
                            Arrays.sort(collinear);

                            // 시작점과 끝점으로 선분 생성
                            addSegment(new LineSegment(collinear[0], collinear[3]));
                        }
                    }
                }
            }
        }
    }

    // segments 배열에 새로운 선분을 추가하는 private 메서드
    private void addSegment(LineSegment segment) {
        // 배열이 가득 찼다면 크기를 두 배로 늘립니다
        if (numberOfSegments == segments.length) {
            LineSegment[] newSegments = new LineSegment[segments.length * 2];
            System.arraycopy(segments, 0, newSegments, 0, segments.length);
            segments = newSegments;
        }
        segments[numberOfSegments++] = segment;
    }

    // 발견된 선분의 개수를 반환
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // 발견된 선분들의 배열을 반환
    public LineSegment[] segments() {
        // 정확한 크기의 새 배열을 만들어 반환
        LineSegment[] result = new LineSegment[numberOfSegments];
        System.arraycopy(segments, 0, result, 0, numberOfSegments);
        return result;
    }
}