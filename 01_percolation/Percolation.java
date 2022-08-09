public class Percolation {
    private boolean[] state; // open/closed
    private int[] tree; // idx of r-c values
    private int[] tree_size; // n-children in tree
    private int N; // N of NxN matrix
    private int nOpen = 0; // count of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n<=0");
        N = n;
        state = new boolean[n * n + 2];
        tree = new int[n * n + 2];
        tree_size = new int[n * n + 2];
        // last two sites are virtual above-below:
        for (int i = 0; i < n * n + 2; i++) {
            state[i] = false;
            tree[i] = i;
            tree_size[i] = 1;
        }
        // top virtual node:
        state[n * n + 1 - 1] = true;
        for (int i = 0; i < n; i++) {
            union(n * n + 1 - 1, i);
        }
        // bottom virtual node:
        state[n * n + 2 - 1] = true;
        for (int i = n * n - n; i < n * n; i++) {

            union(n * n + 2 - 1, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = rcToIdx(row, col);
        if (state[idx])
            return;

        state[idx] = true;
        nOpen++;
        // below
        if (row > 1 && isOpen(row - 1, col)) {
            union(idx, rcToIdx(row - 1, col));
        }
        // above
        if (row - 1 < N - 1 && isOpen(row + 1, col)) {
            union(idx, rcToIdx(row + 1, col));
        }
        // left
        if (col > 1 && isOpen(row, col - 1)) {
            union(idx, rcToIdx(row, col - 1));
        }
        // right
        if (col - 1 < N - 1 && isOpen(row, col + 1)) {
            union(idx, rcToIdx(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int idx = rcToIdx(row, col);
        return state[idx];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return connected(N * N + 1, rcToIdx(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(N * N, N * N + 1);
    }

    private int rcToIdx(int r, int c) {
        if (r < 1 || c < 1) {
            throw new IllegalArgumentException("RC is 1-indexed");
        } else if (r > N || c > N) {
            throw new IllegalArgumentException("RC exceed arr-len");
        }

        int row_offset = (r - 1) * N;
        return row_offset + c;
    }

    private int[] idxToRc(int idx) {
        int[] rc = new int[2];
        int c = idx % N;
        int r = idx / N;
        rc[0] = r;
        rc[1] = c;
        return rc;
    }

    // quick union:
    private int root(int i) {
        while (i != tree[i]) {
            tree[i] = tree[tree[i]]; // path compression enhancement - flattens tree
            i = tree[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j)
            return;
        // weighting logic: smaller tree always is linked to larger tree
        if (tree_size[i] < tree_size[j]) {
            tree[i] = j;
            tree_size[j] += tree_size[i];
        } else {
            tree[j] = i;
            tree_size[i] += tree_size[j];
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 10;
        Percolation p = new Percolation(n);
        for (int i = 1; i < n + 1; i++) {
            p.open(i, 1);
            System.out.println(p.percolates());
        }
    }
}