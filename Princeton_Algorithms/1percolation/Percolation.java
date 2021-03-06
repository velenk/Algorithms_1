import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private WeightedQuickUnionUF uf;
   private WeightedQuickUnionUF uf_top;
   private boolean []site;
   private int N;
   private int number_open;
   
   public Percolation(int n) {               // create n-by-n grid, with all sites blocked
	if(n <= 0) throw new IllegalArgumentException("Input should be positif!\n");
	 this.N = n;
	 this.site = new boolean[N*N+2];
	 for(int i=1; i<n*n+1; i++) {
		 site[i] = false;
	 }
	 this.site[0] = this.site[N*N+1] = true;
	 this.uf = new  WeightedQuickUnionUF(N*N+2);
	 this.uf_top = new WeightedQuickUnionUF(n*n+1);
   }
   private void checkBounds(int row, int col){
		if(row < 1 || row > this.N || col < 1 || col > this.N){
			throw new IllegalArgumentException("Index out of bounds!\n");
		}
	}
   private void union(int aPos, int bPos, WeightedQuickUnionUF wq){
		if(!wq.connected(aPos, bPos)){
			wq.union(aPos, bPos);
		}
	}
   public    void open(int row, int col) {   // open site (row, col) if it is not open already
	   checkBounds(row, col);
	   int pos = (row - 1) * N + col;
	 if (site[pos]) {
		 return;
	 }
	 number_open++;
		 site[pos] = true;
	 int row_pre = pos - N, row_next = pos + N,
				col_pre = pos - 1, col_next = pos + 1;
	 if (row == 1){
			union(0, pos, uf);
			union(0, pos, uf_top);
		} else if (isOpen(row_pre)){
			union(row_pre, pos, uf);
			union(row_pre, pos, uf_top);
	}
	 if(row == N){
			union(N * N + 1, pos, uf);
		}else if(isOpen(row_next)){
			union(row_next, pos, uf);
			union(row_next, pos, uf_top);
	}
	 if (col != 1 && site[col_pre]) {
		 union(pos, col_pre, uf);
		 union(pos, col_pre, uf_top);
	 }
	 if (col != N && site[col_next]) {
		 union(pos, col_next, uf);
		 union(pos, col_next, uf_top);
	 }
   }
   public boolean isOpen(int row, int col) {
		checkBounds(row, col);
		return site[(row-1)*N+col];
	}
   private boolean isOpen(int pos) {
		return site[pos];
	}
   public boolean isFull(int row, int col) {  // is site (row, col) full?
	 checkBounds(row, col);
	 return uf_top.connected((row-1)*N+col, 0);
   }
   public int numberOfOpenSites() {      // number of open sites
	 return this.number_open;
   }
   public boolean percolates() {              // does the system percolate?
	 return uf.connected(0, N*N+1);
   }
}