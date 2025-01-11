import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ServiceConfigurationError;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int manhattan;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            int f1 = this.manhattan + this.moves;
            int f2 = that.manhattan + that.moves;
            return Integer.compare(f1, f2);
        }
    }

    private final SearchNode solution;
    private final boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();

        pq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode currentNode = null;
        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            currentNode = pq.delMin();
            if (currentNode.board.isGoal()) {
                solvable = true;
                solution = currentNode;
                return;
            }

            SearchNode twinNode = twinPq.delMin();
            if (twinNode.board.isGoal()) {
                solvable = false;
                solution = null;
                return;
            }

            enqueueNeighbors(currentNode, pq);
            enqueueNeighbors(twinNode, twinPq);
        }

        solvable = false;
        solution = null;
    }

    private void enqueueNeighbors(SearchNode node, MinPQ<SearchNode> pq) {
        for (Board neighbor : node.board.neighbors()) {
            if (node.prev == null || !neighbor.equals(node.prev.board)) {
                pq.insert(new SearchNode(neighbor, node.moves + 1, node));
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() { return solvable; }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() { return solvable ? solution.moves : -1; }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) return null;

        Stack<Board> path = new Stack<>();
        SearchNode current = solution;
        while (current != null) {
            path.push(current.board);
            current = current.prev;
        }
        return path;
    }
}