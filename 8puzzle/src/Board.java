import java.util.ArrayList;

public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // borad dimension n
    public int dimension() { return n; }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int currentTile = tiles[i][j];
                int correctTile = i * n + j + 1;

                if (currentTile != 0 && currentTile != correctTile)
                    hamming++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distance between tiles and goal
    public int manhattan() {
        int manhattan = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int currentTile = tiles[i][j];

                if (currentTile != 0) {
                    int targetRow = (currentTile - 1) / n;
                    int targetCol = (currentTile - 1) % n;

                    manhattan += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != n-1 || j != n-1) {
                    int correctTile = i * n + j + 1;
                    if (tiles[i][j] != correctTile)
                        return false;
                } else if (tiles[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;

        if (y == null)
            return false;

        if (this.getClass() != y.getClass())
            return false;

        Board that = (Board) y;

        if (this.n != that.n)
            return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        int blankRow = 0, blankCol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir: directions) {
            int newRow = blankRow + dir[0];
            int newCol = blankCol + dir[1];

            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                int[][] newTiles = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        newTiles[i][j] = tiles[i][j];
                    }
                }

                newTiles[blankRow][blankCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                twinTiles[i][j] = tiles[i][j];
        }

        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            int temp = twinTiles[0][0];
            twinTiles[0][0] = twinTiles[0][1];
            twinTiles[0][1] = temp;
        } else {
            int temp = twinTiles[1][0];
            twinTiles[1][0] = twinTiles[1][1];
            twinTiles[1][1] = temp;
        }
        return new Board(twinTiles);
    }
}