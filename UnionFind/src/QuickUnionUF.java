public class QuickUnionUF {
    private int[] id;
    private int[] sz; // Weighted quick-union

    public QuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
        for (int i = 0; i < N; i++) sz[i] = 1; // Weighted quick-union
    }

    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public boolean connected(int p, int q) { return root(p) == root(q); }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
//        id[i] = j; // old
        if (i == j) return; // Weighted quick-union
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else { id[j] = i; sz[i] += sz[j]; }
    }
}

// defect: Trees can get tall -> Find too expensive