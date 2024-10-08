package vectAddGraph;

//this class is an implementation of the simplex algorithm
//certain ratios of variables and constraints don't work correctly for unkown reasons
public class Simplex {
	
	//the purpose of the main method is to help debug the algorithm
    public static void main(String[] args) {
        double[][] tableau1 = {
            {1, 1, 1, 0, 4},
            {1, 3, 0, 1, 6},
            {-3, -5, 0, 0, 0}
        };
        
        double[][] tableaux = {
                {1, 1, 0, 1, 0, 0, 4},
                {1, 3, 0, 0, 1, 0, 6},
                {0, 0, 1, 0, 0, 1, 4},
                {-3, -5, -2, 0, 0, 0, 0}
            };
        
		//test for three vars
		double[][] tableau2 = {
				{2,3,1,1,0,0,6},
				{3,2,2,0,1,0,5},
				{1,4,3,0,0,1,4},
				{-7,-12,-5,0,0,0,0}
		};
		
		double[][] tableau3 = {
				{1,1,1,1,0,12},
				{2,1,0,0,1,16},
				{-40,-30,-1,0,0,0}
		};
		
		//two vars, one constraint
		double[][] tableau4 = {
				{2,1,1,20},
				{-3,-4,0,0}
		};
		
		//three vars, two constraints
		double[][] tableau5 = {
				{1,1,1,1,0,10},
				{2,1,3,0,1,15},
				{-3,-2,-4,0,0,0,}
		};
		
		//two vars, three const
		double[][] t6 = {
				{1,2,1,0,0,10},
				{2,1,0,1,0,12},
				{1,1,0,0,1,7},
				{-3,-1,0,0,0,0}
		};
		
		//four of both
		double[][] t7 = {
				{1,3,0,0,1,0,0,0,20},
				{0,1,2,0,0,1,0,0,15},
				{0,0,2,1,0,0,1,0,16},
				{2,0,0,2,0,0,0,1,20},
				{-3,-3,-2,-3,0,0,0,0,0}
		};
		
		//four vars, 3 constr
		double[][] t0 = {
				{2,2,0,0,1,0,0,20},
				{0,0,3,1,0,1,0,20},
				{1,1,1,1,0,0,1,12},
				{-1,-1,-3,-1,0,0,0,0}
		};
		
		//3 vars, 4 constr
		double[][] t1 = {
				{1,0,0,1,0,0,0,10},
				{0,1,0,0,1,0,0,10},
				{0,0,1,0,0,1,0,10},
				{2,1,2,0,0,0,1,25},
				{-3,-2,-2,0,0,0,0,0}
		};
		
		//2 var, 3 constr
		double[][] tq = {
				{1,0,1,0,0,10},
				{0,1,0,1,0,10},
				{2,1,0,0,1,17},
				{-3,-2,0,0,0,0}
		};

        double[] nums = executeSimplex(tableau3);
        for (double num : nums) {
            System.out.println(num);
        }
    }
    
    //this is the method to call to preform the algorithm
    //the input is the tableau to work wiht
    //the output is an array containing the final values
    public static double[] executeSimplex(double[][] tableau) {
        int numRows = tableau.length;
        int numCols = tableau[0].length;

        // Array to hold the labels for columns and rows
        int[] colLabels = new int[numCols - 1];
        int[] rowLabels = new int[numRows - 1];
        
        for (int i = 0; i < colLabels.length; i++) {
            colLabels[i] = i;
        }
        for (int i = 0; i < rowLabels.length; i++) {
            rowLabels[i] = i + (numCols - numRows);
        }
        
        //main loop
        int enteringVar;
        while (!isOptimal(tableau[numRows - 1])) {
            int pivCol = getPivCol(tableau[numRows - 1]);
            int pivRow = getPivRow(tableau, pivCol);

            if (pivRow == -1) {
                throw new ArithmeticException("Cannot find pivot row");
            }
            
            tableau[pivRow] = calcNewRow(tableau[pivRow], pivCol);
            enteringVar = colLabels[pivCol];
            colLabels[pivCol] = rowLabels[pivRow];
            rowLabels[pivRow] = enteringVar;
            
            for (int i = 0; i < numRows; i++) {
                if (i != pivRow) {
                    tableau[i] = updateOtherRow(tableau[i], tableau[pivRow], pivCol);
                }
            }
        }//end while
        
        double[] vals = new double[numCols];
        for (int i = 0; i < rowLabels.length; i++){
                vals[rowLabels[i]] = tableau[i][numCols - 1];
        }

        return vals;
    }
    
    //this methos updates the non-pivot rows based on the new row that replaced the pivot row
    //the inputs are the row to update, the new row that replaced the pivot row, and the index of the pivot column
    //the outout is the updated row
    //this method needs to be called separately for each row
    public static double[] updateOtherRow(double[] row, double[] newRow, int pivCol) {
        double[] updated = new double[row.length];
        for (int i = 0; i < row.length; i++) {
            updated[i] = row[i] - row[pivCol] * newRow[i];
        }
        return updated;
    }
    
    //this method calculates the row that replaces the pivot row
    //the inputs are the entire pivot row, and the index of the pivot column
    //the output is an array representing the new row
    public static double[] calcNewRow(double[] row, int pivCol) {
        double[] newRow = new double[row.length];
        double pivotElement = row[pivCol];
        for (int i = 0; i < row.length; i++) {
            newRow[i] = row[i] / pivotElement;
        }
        return newRow;
    }
    
    //this method determines which row is the pivot row
    //the input is the tableau and the index of the pivot column
    //the output is the index of the pivot row
    public static int getPivRow(double[][] tableau, int pivCol) {
        double smallest = Double.MAX_VALUE;
        int row = -1;
        for (int i = 0; i < tableau.length - 1; i++) {
            if (tableau[i][pivCol] > 0) {
                double theta = tableau[i][tableau[i].length - 1] / tableau[i][pivCol];
                if (theta < smallest) {
                    row = i;
                    smallest = theta;
                }
            }
        }
        return row;
    }
    
    //this method figures out which column is the pivot column
    //the expected input is the last row of the tableau
    //the output is the index of the pivot column
    public static int getPivCol(double[] row) {
        int col = -1;
        double smallest = 0;
        for (int i = 0; i < row.length - 1; i++) {
            if (row[i] < smallest) {
                col = i;
                smallest = row[i];
            }
        }
        return col;
    }
    
    //this method determines if the solution is optimal
    //the expected input is the last row of the tableau
    public static boolean isOptimal(double[] row) {
        for (int i = 0; i < row.length - 1; i++) {
            if (row[i] < 0) {
                return false;
            }
        }
        return true;
    }
}