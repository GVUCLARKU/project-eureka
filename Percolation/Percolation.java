import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * Write a description of class Percolation here.
 * 
 * @Giang Vu
 * @16 Sept 2016
 */
public class Percolation
{
    private int top = 0;
    private int bottom;
    private int size;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF Backwash;
    private boolean [][] grid;
    private int numberOfOpen;

    //Constructor create N-by-N grid, with all sites initially blocked
    //throw exception if N < 0
    public Percolation(int N){
        if (N <= 0){
            throw new java.lang.IllegalArgumentException("N must > 0");}
        wqu = new WeightedQuickUnionUF(N*N+1);//create extra top node
        Backwash = new WeightedQuickUnionUF (N*N+2);//create extra top&bottom node
        size = N;//N*N is number of Sites
        bottom = N*N+1;
        grid = new boolean [N][N];
        numberOfOpen = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col){
        check(row,col);
        if (!isOpen(row, col)){
            grid[row-1][col-1] = true;
            numberOfOpen++;
        }
        
        if (col > 1 && isOpen(row, col -1)){
            wqu.union(Index(row,col), Index(row, col -1));
            Backwash.union(Index(row,col), Index(row, col -1));
        }  //up side of grid

        if (col < size && isOpen(row, col+1)){
            wqu.union(Index(row, col), Index(row, col +1));
            Backwash.union(Index(row, col), Index(row, col +1));
        }   // bottom side of grid

        if (row > 1 && isOpen(row-1, col)){
            wqu.union(Index(row,col), Index(row -1, col));
            Backwash.union(Index(row,col), Index(row -1, col));
        }   //left side of grid

        if (row < size && isOpen(row +1, col)){
            wqu.union(Index(row,col), Index(row+ 1, col));
            Backwash.union(Index(row,col), Index(row+ 1, col));
        }   //right side of grid

        // special case
        if (row ==1){
            wqu.union(Index(row,col),top);
            Backwash.union(Index(row,col),top);
        }
        if (row == size){
            Backwash.union(Index(row,col),bottom);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        check(row,col);
        return grid[row - 1][col - 1]; 
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
       check(row, col);
       return wqu.connected(top, Index(row,col));
    }

    // number of open sites
    public int numberOfOpenSites(){
        return numberOfOpen;
    }

    // does the system percolate?
    public boolean percolates(){
        return Backwash.connected(top,bottom);
    }

    private int Index(int row, int col){
        return size*(row -1) +col;
    }
    
    private void check(int row, int col){
        if (row <= 0 || row > size || col <= 0 ||  col > size){
            throw new IndexOutOfBoundsException();
        }
    }
}
