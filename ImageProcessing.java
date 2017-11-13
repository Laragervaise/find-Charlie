package main;
public final class ImageProcessing {
	
	
	 /**
     * handle input parameters that are outside of the range 0 to 255
     * @param rgb : a 32-bits RGB color
     * @return an integer, between 0 and 255
     */
    public static int verifColor(int color) {
    		if(color > 255) {
			return 255;
		}
		else if(color < 0) {
			return 0;
		}
		else {
			return color;
		}
    }
    
    /**
     * Returns red component from given packed color. 
     * @param rgb : a 32-bits RGB color
     * @return an integer, between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getRed(int rgb) { 
    	
    		int red = rgb >> 16;
   		red = red & 0b11111111;
    	
    		return verifColor(red);
    }

    /**
     * Returns green component from given packed color.
     * @param rgb : a 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {

    		int green = rgb >> 8;
    		green = green & 0b11111111;
    	
    		return verifColor(green);
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb : a 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {

    		int blue = rgb & 0b11111111;
    	
    		return verifColor(blue);
    }

   
    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb : 32-bits RGB color
     * @return a double between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int)
     */
    public static double getGray(int rgb) {
    	
    		return (getRed(rgb) + getGreen(rgb) + getBlue(rgb))/3d;
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red : an integer 
     * @param green : an integer 
     * @param blue : an integer
     * @return a 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(int red, int green, int blue) {
    	
    		red = verifColor(red);
    		
    		green =verifColor(green);
    		
    		blue = verifColor(blue);
    	
    		int rgb = (red << 16) | (green  << 8) | blue;
    	
    		return rgb;
    }

    /**
     * Returns packed RGB components from given gray-scale value.
     * @param gray : a double 
     * @return a 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(double gray) {
    		
    		if(gray > 255) {
			gray = 255;
		}
		else if(gray < 0) {
			gray = 0;
		}
    		
    		double grayRound = Math.round(gray);
    	
    		return getRGB((int)grayRound, (int)grayRound, (int)grayRound);
    }

    /**
     * Converts packed RGB image to gray-scale image.
     * @param image : a HxW integer array
     * @return a HxW double array
     * @see #encode
     * @see #getGray
     */
    public static double[][] toGray(int[][] image) {

    		double [][] gray = new double[image.length][image[0].length];
    		
    		for(int m=0; m<image.length; m++) {
    			for(int n=0; n<image[0].length; n++) {
    				gray[m][n] = getGray(image[m][n]);
    			}
    		}
    		
    		return gray;
    }

    /**
     * Converts gray-scale image to packed RGB image.
     * @param channels : a HxW double array
     * @return a HxW integer array
     * @see #decode
     * @see #getRGB(double)
     */
    public static int[][] toRGB(double[][] gray) {

    		int [][] back = new int[gray.length][gray[0].length];
    		
    		for(int m=0; m<gray.length; m++) {
    			for(int n=0; n<gray[0].length; n++) {
    				back[m][n] = getRGB(gray[m][n]);
    			}
    		}
    	
    		return back;
    }

    
    /**
     * Convert an arbitrary 2D double matrix into a 2D integer matrix 
     * which can be used as RGB image
     * @param matrix : the arbitrary 2D double array to convert into integer. Requirement : contain at least one pixel
     * @param min : a double, the minimum value the matrix could theoretically contains
     * @param max : a double, the maximum value the matrix could theoretically contains
     * @return an 2D integer array, containing a RGB mapping of the matrix 
     */
    public static int[][] matrixToRGBImage(double[][] matrix, double min, double max) {

    		assert matrix != null && matrix[0] != null && matrix.length > 0 && matrix[0].length > 0;

    		int g[][] = new int[matrix.length][matrix[0].length];
    		
    		if(min > max) { // inversion de min et max si min<max
    			int tmp=0;
    			max = tmp;
    			max = min;
    			min = tmp;
    		}
    		
    		for(int m=0; m<matrix.length; m++) {
    			for(int n=0; n<matrix[0].length; n++) {
				g[m][n] = (int)((matrix[m][n] - min)/(max-min)*255);
    			}
    		}
    		
    		return g;
    }

}
