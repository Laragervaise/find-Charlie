package main;

import java.util.ArrayList;

public class Collector {

	/**
	 * Find the row, column coordinates of the best element (biggest or smallest) for the given matrix
	 * @param matrix : an 2D array of doubles. Requirement : contain at least one pixel
	 * @param smallestFirst : a boolean, indicates if the smallest element is the best or not (biggest is then the best)
	 * @return an array of two integer coordinates, row first and then column
	 */
	public static int[] findBest(double[][] matrix, boolean smallestFirst) {
		
		assert matrix != null && matrix[0] != null && matrix.length > 0 && matrix[0].length > 0;
		
		int coord[] = new int[2];
		double smallest = Double.POSITIVE_INFINITY;
		double biggest = Double.NEGATIVE_INFINITY;
		
		for(int m=0; m<matrix.length; m++) {
			for(int n=0; n<matrix[0].length; n++) {	
				if(smallestFirst && matrix[m][n]<smallest) {
						smallest = matrix[m][n];
						coord[0] = m;
						coord[1] = n;
				} else if(!smallestFirst && matrix[m][n]>biggest) {
						biggest = matrix[m][n];
						coord[0] = m;
						coord[1] = n;
				}	
			}
		}

		return coord;
	}

	
	/**
	 * Find the row, column coordinate-pairs of the n best (biggest or smallest) elements of the given matrix
	 * @param n : an integer, the number of best elements we want to find 
	 * @param matrix : an 2D array of doubles. Requirement : contain at least one pixel
	 * @param smallestFirst : a boolean,  indicates if the smallest element is the best or not (biggest is the best)
	 * @return an array of size n containing row, column-coordinate pairs
	 */
	public static int[][] findNBest(int n, double[][] matrix, boolean smallestFirst) {
		
		assert matrix != null && matrix[0] != null && matrix.length > 0 && matrix[0].length > 0;
		
		int ncoord[][] = new int[n][2];
		double matrixCopy[][] = new double[matrix.length][matrix[0].length];
		
		for(int r=0; r<matrix.length; r++) {
			for(int c=0; c<matrix[0].length; c++) {
				matrixCopy[r][c] = matrix[r][c];
			}
		}

		for(int i=0; i<n; i++) {
			int coordtmp[] = new int[2];
			coordtmp = findBest(matrixCopy, smallestFirst);
			ncoord[i][0] = coordtmp[0];
			ncoord[i][1] = coordtmp[1];
					
			if(smallestFirst) {
				matrixCopy[ncoord[i][0]][ncoord[i][1]] = Double.POSITIVE_INFINITY;
			} else {
				matrixCopy[ncoord[i][0]][ncoord[i][1]] = Double.NEGATIVE_INFINITY;
			}
		}
    	
		return ncoord;
	}
	
	
	/**
	 * BONUS 
	 * Notice : Bonus points are underpriced ! 
	 * 
	 * Sorts all the row, column coordinates based on their pixel value
	 * recursive function
	 * @param matrix : an 2D array of doubles
	 * @return A list of points, each point is an array of length 2.
	 */
	public static ArrayList<int[]> quicksort(double[][] matrix, ArrayList<int[]> coordinates){
		// 
		double pivot = matrix[0][0];
		
		ArrayList<int[]> plusPetit = new ArrayList<int[]>();
		ArrayList<int[]> egal = new ArrayList<int[]>();
		ArrayList<int[]> plusGrand = new ArrayList<int[]>();
		
		// remplissage des 3 ArrayLists
		for(int m=0; m<matrix.length; m++) {
			for(int n=0; n<matrix[0].length; n++) {
				int coordtmp[] = {m,n};
				
				if(matrix[m][n]<pivot) {
					plusPetit.add(coordtmp);
				} else if(matrix[m][n]>pivot) {
					plusGrand.add(coordtmp);
				} else if(matrix[m][n] == pivot) {
					egal.add(coordtmp);
				}
			}
		}
		
		// appel recursif incorrect
		if(plusPetit.size()>1){
			quicksort(matrix, plusPetit);
		}
		if(plusPetit.size()>1){
			quicksort(matrix, plusGrand);
		}
		
		// fusionnage des listes
		coordinates = plusPetit;
		coordinates.addAll(egal);
		coordinates.addAll(plusGrand); 

		return coordinates;	
		
	}

	/**
	 * BONUS 
	 * Notice : Bonus points are underpriced ! 
	 * 
	 * Sorts all the row, column coordinates based on their pixel value
	 * Hint : Use recursion !
	 * @param matrix : an 2D array of doubles
	 * @return A list of points, each point is an array of length 2.
	 */
	public static ArrayList<int[]> quicksortPixelCoordinates(double[][] matrix) {
		
		ArrayList<int[]> coordinates = new ArrayList<int[]>();

		for(int m=0; m<matrix.length; m++) {
			for(int n=0; n<matrix[0].length; n++) {
				int[] coordtmp = {m,n};
				coordinates.add(coordtmp); 
			}
		}

		if(!coordinates.isEmpty()) {
			return quicksort(matrix,coordinates);
		} else {
		return coordinates;
		}

	}

	
	/**
	 * BONUS
	 * Notice : Bonus points are underpriced !
	 * 
	 * Use a quick sort to find the row, column coordinate-pairs of the n best (biggest or smallest) elements of the given matrix
	 * Hint : return the n first or n last elements of a sorted ArrayList  
	 * @param n : an integer, the number of best elements we want to find 
	 * @param matrix : an 2D array of doubles. Requirement : contain at least one pixel
	 * @param smallestFirst : a boolean, indicate if the smallest element is the best or not (biggest is the best)
	 * @return an array of size n containing row, column-coordinate pairs
	 */
	public static int[][] findNBestQuickSort(int n, double[][] matrix, boolean smallestFirst) {
		
		assert matrix != null && matrix[0] != null && matrix.length > 0 && matrix[0].length > 0;

    		int coords[][] = new int[n][2];
    		
    		ArrayList<int[]> listeTriee = new ArrayList<int[]>();
    		
    		listeTriee = quicksortPixelCoordinates(matrix);
    		
    		if(smallestFirst) {
    			for(int m=0; m<n; m++) {
    				coords[m][0] = listeTriee.get(m)[0];
    				coords[m][1] = listeTriee.get(m)[1];
    			}	
    		}
    		else {
    			for(int m=matrix.length*matrix[0].length; m>matrix.length*matrix[0].length-n; m--) {
    				coords[m][0] = listeTriee.get(m)[0];
    				coords[m][1] = listeTriee.get(m)[1];
    			}
    		}
		
		return coords;
	}
}
