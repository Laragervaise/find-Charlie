package main;

/**
 * 
 * @author Lara Gervaise
 *
 *	Where is Charlie Project 
 *
 */
public final class Main {

	/* 
	 * This class is incomplete!!
	 * 
	 * You are expected to write at least one testcase for each required method.
	 * You will find some examples of testcases below.
	 */
	
    public static void main(String[] args) {
    	
    	testGetRed();
    	testGrayscale();
    testFindNBest();
    testDistanceBasedSearch();
    testSimilarityBasedSearch();   
    	findCharlie();
    	
    	testFindBest();
    	testMatrixToRGB();
    	
    testLogoCanon();
    testLogoCanonDark();
    	testLogoCanonLight();
    	
    	testNCCPatternEqualImage();
    	testSimilarityPatternEqualImage();
    	testSimilaritySimple();
    	
    	testDistanceBasedSearchWrapping();
    testDistanceBasedSearchMirroring();
    	
    	//testQuicksort();
    	
    }
    
    /*
     * Tests for Class ImageProcessing
     */
    public static void testGetRed() { 
    	int color = 0b11110000_00001111_01010101;
    	int ref = 0b11110000;
    	int red = ImageProcessing.getRed(color);
    	if (red == ref) {
    		System.out.println("Test passed");
    	} else {
    		System.out.println("Test failed. Returned value = " + red + " Expected value = " + ref);
    	}
    }
    
    public static void testGrayscale() {
    	System.out.println("Test Grayscale");
    int[][] image = Helper.read("images/food.png");
    	double[][] gray = ImageProcessing.toGray(image);
    	Helper.show(ImageProcessing.toRGB(gray), "test bw");
    }
    
    
    /*
     * Tests for Class Collector
     */
    
    public static void testFindBest() {
    	System.out.println("Test findBest");
    	double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    	int[] coord = Collector.findBest(t, true);    			
    System.out.println("Row=" + coord[0] + " Col=" + coord[1] + " Val=" + t[coord[0]][coord[1]]);
    	}    
    
    public static void testFindNBest() {
    	System.out.println("Test findNBest");
    	double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    	int[][] coords = Collector.findNBest(10, t, true);    			
    	for (int[] a : coords) {
    		int r = a[0];
    		int c = a[1];
    		System.out.println("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
    	}    
    }
    
    
    public static void testQuicksort() {
    	
    	double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    int[][]coord = Collector.findNBestQuickSort(1, t, true);
    System.out.println("Row=" + coord[0] + " Col=" + coord[1] + " Val=" + t[coord[0][0]][coord[0][1]]);
    }

    /*
     * Tests for Class DistanceBasedSearch
     */
    
    public static void testDistanceBasedSearch() {
    	System.out.println("Test DistanceBasedSearch");
    	int[][] food = Helper.read("images/food.png");
    	int[][] onions = Helper.read("images/onions.png");
    	double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food);
    	int[] p = Collector.findBest(distance, true);
    	Helper.drawBox(p[0], p[1], onions[0].length, onions.length, food);
    	Helper.show(food, "Found!");
    }
    
    public static void testDistanceBasedSearchMirroring() {
    	System.out.println("Test DistanceBasedSearchMirroring");
    	int[][] food = Helper.read("images/food.png");
    	int[][] onions = Helper.read("images/onions.png");
    	double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food, "mirroring");
    	int[] p = Collector.findBest(distance, true);
    	Helper.drawBox(p[0], p[1], onions[0].length, onions.length, food);
    	Helper.show(food, "Found!");
    }
    
    public static void testDistanceBasedSearchWrapping() {
    	System.out.println("Test DistanceBasedSearchWrapping");
    	int[][] food = Helper.read("images/food.png");
    	int[][] onions = Helper.read("images/onions.png");
    	double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food, "wrapping");
    	int[] p = Collector.findBest(distance, true);
    	Helper.drawBox(p[0], p[1], onions[0].length, onions.length, food);
    	Helper.show(food, "Found!");
    }
    
    
    public static void testMatrixToRGB() {
    	System.out.println("Test MatrixToRGB");
    	int[][] food = Helper.read("images/food.png");
    	int[][] onions = Helper.read("images/onions.png");
    	double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food);
    	int[][] imageDesDistances = ImageProcessing.matrixToRGBImage(distance, 0, 255);
    	Helper.show(imageDesDistances, "Found!");
    }
    
    
    
    
    /*
     * Tests for Class SimilarityBasedSearch
     */

    public static void testSimilarityBasedSearch() {
    	System.out.println("Test SimilarityBasedSearch");
	int[][] food = Helper.read("images/food.png");
    	int[][] onions = Helper.read("images/onions.png");
    	double[][] foodGray = ImageProcessing.toGray(food);
    	double[][] onionsGray = ImageProcessing.toGray(onions);    	
    	double[][] similarity = SimilarityBasedSearch.similarityMatrix(onionsGray, foodGray);
    int[][] best = Collector.findNBest(8, similarity, false);    			
    	for (int[] a : best) {
    		int r = a[0];
    		int c = a[1];
        	Helper.drawBox(r, c, onions[0].length, onions.length, food);
    	}
    	Helper.show(food, "Found again!");
    }
    
    
	public static void testLogoCanon(){
	 	System.out.println("Test SimilarityBasedSearch VS DistanceBasedSearch");
		int[][] image = Helper.read("images/image.png");
	    	int[][] pattern = Helper.read("images/pattern.png");
	    	
	    	double[][] distance = DistanceBasedSearch.distanceMatrix(pattern, image);
	    	int[] p = Collector.findBest(distance, true);
	    	Helper.drawBox(p[0], p[1], pattern[0].length, pattern.length, image);
	    	Helper.show(image, "Found!");
	    	
	    	
	    	double[][] imageGray = ImageProcessing.toGray(image);
	    	double[][] patternGray = ImageProcessing.toGray(pattern);    	
	    	double[][] similarity = SimilarityBasedSearch.similarityMatrix(patternGray, imageGray);
	    int[][] best = Collector.findNBest(2, similarity, false);    			
	    	for (int[] a : best) {
	    		int r = a[0];
	    		int c = a[1];
	        	Helper.drawBox(r, c, pattern[0].length, pattern.length, image);
	    	}
	    	Helper.show(image, "Found again!");
	}
    
	public static void testLogoCanonDark(){
	 	System.out.println("Test SimilarityBasedSearch VS DistanceBasedSearch");
		int[][] image = Helper.read("images/image-dark.png");
	    	int[][] pattern = Helper.read("images/pattern.png");
	    	
	    	double[][] distance = DistanceBasedSearch.distanceMatrix(pattern, image);
	    	int[] p = Collector.findBest(distance, true);
	    	Helper.drawBox(p[0], p[1], pattern[0].length, pattern.length, image);
	    	Helper.show(image, "Found on dark!");
	    	
	    	
	    	double[][] imageGray = ImageProcessing.toGray(image);
	    	double[][] patternGray = ImageProcessing.toGray(pattern);    	
	    	double[][] similarity = SimilarityBasedSearch.similarityMatrix(patternGray, imageGray);  			
	    int[] best = Collector.findBest(similarity, false);    			
	    Helper.drawBox(best[0], best[1], pattern[0].length, pattern.length, image);
	    	Helper.show(image, "Found on dark again!");
	}
	
	public static void testLogoCanonLight(){
	 	System.out.println("Test SimilarityBasedSearch VS DistanceBasedSearch");
		int[][] image = Helper.read("images/image-light.png");
	    	int[][] pattern = Helper.read("images/pattern.png");
	    	
	    	double[][] distance = DistanceBasedSearch.distanceMatrix(pattern, image);
	    	int[] p = Collector.findBest(distance, true);
	    	Helper.drawBox(p[0], p[1], pattern[0].length, pattern.length, image);
	    	Helper.show(image, "Found on light!");
	    	
	    	
	    	double[][] imageGray = ImageProcessing.toGray(image);
	    	double[][] patternGray = ImageProcessing.toGray(pattern);    	
	    	double[][] similarity = SimilarityBasedSearch.similarityMatrix(patternGray, imageGray);
	    int[] best = Collector.findBest(similarity, false);    			
	    Helper.drawBox(best[0], best[1], pattern[0].length, pattern.length, image);
	    	Helper.show(image, "Found on light again!");
	}
	
    
    public static void testNCCPatternEqualImage() {     
    	  double[][] pattern = {{ 0,   0, 0 },
    	                       { 0, 255, 0 },
    	                       { 0,   0, 0 }};
    	  double similarity = SimilarityBasedSearch.normalizedCrossCorrelation(0, 0, pattern, pattern);
    	  if (similarity == 1.0) {
    	    System.out.println("PASSED");      
    	  } else {
    	    System.out.println("ERROR: expected value 1.0 but was " + similarity);
    	  }
    	}
    
    	public static void testSimilarityPatternEqualImage() {     
    	  double[][] pattern = {{ 0, 255}};
    	  double[][] similarity = SimilarityBasedSearch.similarityMatrix(pattern, pattern);
    	  if (similarity.length == 1) {
    	    if (similarity[0][0] == 1.0) {
    	      System.out.println("PASSED");
    	    } else {
    	      System.out.println("ERROR: expected value 1.0 but was " + similarity[0][0]);       
    	    }
    	  } else {
    	    System.out.println("ERROR: expected length 1 but was " + similarity.length);       
    	  }
    	}
    	
    	public static void testSimilaritySimple() {
    	  double[][] image = {{ 3, 2, 2, 2 },
    	                      { 0, 3, 0, 0 }};
    	  double[][] pattern = {{ 0, 3, 0}};
    	  double[][] similarity = SimilarityBasedSearch.similarityMatrix(pattern, image);
    	 
    	  if (similarity.length == 2 && similarity[0].length == 2) {
    	    if (similarity[0][0] == -0.5 && similarity[0][1] == -1.0 &&
    	        similarity[1][0] ==  1.0 && similarity[1][1] == -0.5) {
    	      System.out.println("PASSED");
    	    } else {
    	      System.out.println("ERROR: wrong values");  
    	    }
    	  } else {
    	    System.out.println("ERROR: incorrect size");       
    	  }
    	}
    	
    	 public static void findCharlie() {
    	    	System.out.println("Find Charlie");
    		int[][] beach = Helper.read("images/charlie_beach.png");
    	    	int[][] charlie = Helper.read("images/charlie.png");
    	    	double[][] beachGray = ImageProcessing.toGray(beach);
    	    	double[][] charlieGray = ImageProcessing.toGray(charlie);    	

    	    	System.out.println("Compute Similarity Matrix: expected time about 2 min");
    	    	double[][] similarity = SimilarityBasedSearch.similarityMatrix(charlieGray, beachGray);

    	    	System.out.println("Find N Best");
    	    	int[] best = Collector.findBest(similarity, false);   
    	    	double max = similarity[best[0]][best[1]];
    	    	
    	    	Helper.show(ImageProcessing.matrixToRGBImage(similarity, -1, max), "Similarity");
    	    	
    	    	Helper.drawBox(best[0], best[1], charlie[0].length, charlie.length, beach);
    	    	System.out.println("drawBox at (" + best[0] + "," + best[1] + ")");
    	    	Helper.show(beach, "Found again!");    	
    	    }
}
