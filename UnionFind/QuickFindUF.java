package UnionFind;

//eager approach
public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int n) {
        id = new int[n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        // Worst 2N+2 accesses:
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid)
                id[i] = qid; // sets q as 'owner'
        }
    }
}