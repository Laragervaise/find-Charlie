package main;

public class SimilarityBasedSearch {

	/**
	 * Computes the mean value of a gray-scale image given as a 2D array 
	 * @param image : a 2D double array, the gray-scale Image
	 * @return a double value between 0 and 255 which is the mean value
	 */
	public static double mean(double[][] image) {
		// pas besoin de verif qu'il y a au moins un pixel car fonction uniquement appelee dans une fonction le faisant déjà
		double sum = 0;
		
		for(int m=0; m<image.length; m++) {
			for(int n=0; n<image[0].length; n++) {
				sum += image[m][n];
			}
		}
		
		return sum/(image.length*image[0].length); 
	}

	
	static double windowMean(double[][] matrix, int row, int col, int width, int height) {
		// pas besoin de verif qu'il y a au moins un pixel car fonction uniquement appelee dans une fonction le faisant déjà
		double sum = 0;
		
		for(int m=0; m<height; m++) {
			for(int n=0; n<width; n++) {
				sum += matrix[row+m][col+n];
			}
		}
		
		return sum/(width*height);
	}
	
	
	/**
	 * Computes the Normalized Cross Correlation of a gray-scale pattern if positioned
	 * at the provided row, column-coordinate in a gray-scale image
	 * @param row : a integer, the row-coordinate of the upper left corner of the pattern in the image. Requirement : contained in the image
	 * @param column : a integer, the column-coordinate of the upper left corner of the pattern in the image. Requirement: contained in the image
	 * @param pattern : an 2D array of doubles, the gray-scale pattern to find
	 * @param image : an 2D array of double, the gray-scale image where to look for the pattern
	 * @return a double, the Normalized Cross Correlation value at position (row, col) between the pattern and the part of
	 * the base image that is covered by the pattern, if the pattern is shifted by x and y.
	 * should return -1 if the denominator is 0
	 */
	public static double normalizedCrossCorrelation(int row, int col, double[][] pattern, double[][] image) {
		
		assert row<image.length && row>=0 && col<image[0].length && col>=0; // verifie que row et col ne provoquent pas d'erreur d'acces au tableau
		// pas besoin de verif qu'il y a au moins un pixel car fonction uniquement appelee dans une fonction le faisant déjà
		double patternAverage = mean(pattern); // moyenne des niveaux de gris	du motif
		double grayAverage = windowMean(image, row, col, pattern[0].length, pattern.length); // moyenne des niveaux de gris d'une portion d'image
		double numerator = 0;
		double denominatorI = 0;
		double denominatorM = 0;
		
		for(int m=0; m<pattern.length; m++) {
			for(int n=0; n<pattern[0].length; n++) {
				double calculimage = image[row+m][col+n]-grayAverage;
				double calculpattern = pattern[m][n]-patternAverage;
				numerator += calculimage*calculpattern;
				denominatorI += calculimage*calculimage;
				denominatorM += calculpattern*calculpattern;
			}
		}
				
		double denominator = Math.sqrt(denominatorI * denominatorM);
		
		if(denominator == 0) {
			return -1;
		}
		else {
			return numerator/denominator;
		}
	}

	
	/**
	 * Compute the similarityMatrix between a gray-scale image and a gray-scale pattern
	 * @param pattern : an 2D array of doubles, the gray-scale pattern to find. Requirement : contain at least one pixel and can be completely contained
	 * in the image
	 * @param image : an 2D array of doubles, the gray-scale image where to look for the pattern. Requirement : contain at least one pixel
	 * @return a 2D array of doubles, containing for each pixel of a original gray-scale image, 
	 * the similarity (normalized cross-correlation) between the image's window and the pattern
	 * placed over this pixel (upper-left corner)
	 */
	public static double[][] similarityMatrix(double[][] pattern, double[][] image) {
		
		assert image.length > 0 && image[0].length > 0 && pattern.length > 0 && pattern[0].length > 0; // verif qu'il y a au moins un pixel 

		assert pattern.length <= image.length && pattern[0].length <= image[0].length; // verif que motif peut etre completement contenu dans l'image
		
		double matrix[][] = new double[image.length - pattern.length + 1][image[0].length - pattern[0].length + 1];
		
		for(int m=0; m<=(image.length - pattern.length); m++) {
			for(int n=0; n<=(image[0].length - pattern[0].length); n++) {
				matrix[m][n] = normalizedCrossCorrelation(m, n, pattern, image);
			}
		}
		
		return matrix; 
	}

}
