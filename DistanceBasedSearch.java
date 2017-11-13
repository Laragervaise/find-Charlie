package main;

public class DistanceBasedSearch {

	/**
	 * Computes the mean absolute error between two RGB pixels, channel by channel.
	 * @param patternPixel : a integer, the second RGB pixel.
	 * @param imagePixel : a integer, the first RGB pixel.
	 * @return a double, the value of the error for the RGB pixel pair. (an integer in [0, 255])
	 */
	public static double pixelAbsoluteError(int patternPixel, int imagePixel) {
		// pas besoin de verif qu'il y a au moins un pixel car fonction uniquement appelee dans une fonction le faisant déjà
		return (double)Math.abs(imagePixel-patternPixel);
	}

	/**
	 * Computes the mean absolute error loss of a RGB pattern if positioned
	 * at the provided row, column-coordinates in a RGB image
	 * @param row : a integer, the row-coordinate of the upper left corner of the pattern in the image.
	 * @param column : a integer, the column-coordinate of the upper left corner of the pattern in the image.
	 * @param pattern : an 2D array of integers, the RGB pattern to find
	 * @param image : an 2D array of integers, the RGB image where to look for the pattern
	 * @return a double, the mean absolute error
	 * @return a double, mean absolute error value at position (row, col) between the pattern and the part of
	 * the base image that is covered by the pattern, if the pattern is shifted by x and y.
	 * should return -1 if the denominator is -1
	 */
	public static double meanAbsoluteError(int row, int col, int[][] pattern, int[][] image) {
		// pas besoin de verif qu'il y a au moins un pixel car fonction uniquement appelee dans une fonction le faisant déjà
		double error = 0;
		double d = pattern.length*pattern[0].length;
		
		if(d==-1)
			return -1;
		
		for(int m=0; m<pattern.length; m++) {
    			for(int n=0; n<pattern[0].length; n++) {
    				error += pixelAbsoluteError(pattern[m][n],image[row+m][col+n])/d;
    			}
		}
		
		return error;
	}

	/**
	 * Compute the distanceMatrix between a RGB image and a RGB pattern
	 * @param pattern : an 2D array of integers, the RGB pattern to find. Requirement : contain at least one pixel and can be completely contained
	 * in the image
	 * @param image : an 2D array of integers, the RGB image where to look for the pattern. Requirement : contain at least one pixel
	 * @return a 2D array of doubles, containing for each pixel of a original RGB image, 
	 * the distance (meanAbsoluteError) between the image's window and the pattern
	 * placed over this pixel (upper-left corner) 
	 */
	public static double[][] distanceMatrix(int[][] pattern, int[][] image) {
		
		assert image != null &&  image[0] != null && pattern != null &&  pattern[0] != null;
		assert image.length > 0 && image[0].length > 0 && pattern.length > 0 && pattern[0].length > 0; // verifie qu'il y a au moins un pixel
		
		assert pattern.length <= image.length && pattern[0].length <= image[0].length; // verifie que motif peut etre completement contenu dans l'image

		double matrix[][] = new double[image.length - pattern.length + 1][image[0].length - pattern[0].length +1];
	
		for(int m=0; m<=(image.length - pattern.length); m++) {
			for(int n=0; n<=(image[0].length - pattern[0].length); n++) {
				matrix[m][n] = meanAbsoluteError(m, n, pattern, image);
			}
		}
				
		return matrix;
	}
	
	/**
	 * Compute the distanceMatrix between a RGB image and a RGB pattern including when the pattern is a little bit out of the image 
	 * @param pattern : an 2D array of integers, the RGB pattern to find. Requirement : contain at least one pixel and can be completely contained
	 * in the image
	 * @param image : an 2D array of integers, the RGB image where to look for the pattern. Requirement : contain at least one pixel
	 * @param strategy : mirroring or wrapping
	 * @return a 2D array of doubles, containing for each pixel of a original RGB image, 
	 * the distance (meanAbsoluteError) between the image's window and the pattern
	 * placed over this pixel (upper-left corner) 
	 */
	public static double[][] distanceMatrix(int[][] pattern, int[][] image, String strategy) {
		
		assert image != null &&  image[0] != null && pattern != null &&  pattern[0] != null;
		assert image.length > 0 && image[0].length > 0 && pattern.length > 0 && pattern[0].length > 0; // verifie qu'il y a au moins un pixel
		
		assert pattern.length <= image.length && pattern[0].length <= image[0].length; // verifie que motif peut etre completement contenu dans l'image
		
		double matrix[][] = new double[image.length][image[0].length];
		
		int imageCopy[][]= new int[image.length + pattern.length-1][image[0].length + pattern[0].length-1]; // -1 car le pattern aura toujours
		// au moins un pixel en commun avec l'image
		
		for(int m=0; m<imageCopy.length; m++) {
			for(int n=0; n<imageCopy[0].length; n++) {
				if(strategy == "wrapping") {
					if(m>=image.length && n<image[0].length) {
						imageCopy[m][n] = image[m%image.length][n];
					}
					else if(m<image.length && n>=image[0].length) {
						imageCopy[m][n] = image[m][n%image[0].length];
					}
					else if(m>=image.length && n>=image[0].length) {
						imageCopy[m][n] = image[m%image.length][n%image[0].length];
					}
					else {
						imageCopy[m][n] = image[m][n];
					}
				}
					
				else if(strategy == "mirroring") {
					if(m>=image.length && n<image[0].length) {
						imageCopy[m][n] = image[image.length - 2 - m%image.length][n];
					}
					else if(m<image.length && n>=image[0].length) {
						imageCopy[m][n] = image[m][image[0].length - 2 - n%image[0].length];
					}
					else if(m>=image.length && n>=image[0].length) {
						imageCopy[m][n] = image[image.length - 2 - m%image.length][image[0].length - 2 - n%image[0].length];
					}
					else {
						imageCopy[m][n] = image[m][n];
					}
				}	
			}
		}
		
		for(int m=0; m<image.length; m++) {
			for(int n=0; n<image[0].length; n++) {
				matrix[m][n] = meanAbsoluteError(m, n, pattern, imageCopy);
			}
		}
			
		return matrix;
	}
}
