/**
 * 
 * @author Lara Gervaise
 *
 *	Main.java dépourvu des méthodes de tests intermédiaires
 *
 */
package main;

public class Program {
	
	public static void main(String[] args) {

	int[][] beach = Helper.read("images/charlie_beach.png");
    	int[][] charlie = Helper.read("images/charlie.png");
    	
    	double[][] beachGray = ImageProcessing.toGray(beach);
    	double[][] charlieGray = ImageProcessing.toGray(charlie);    	

    	double[][] similarity = SimilarityBasedSearch.similarityMatrix(charlieGray, beachGray);

    	int[] best = Collector.findBest(similarity, false);   
    	
    	show(best, charlie[0].length, charlie.length, beach);	
	}
	
	public static void show(int[] best, int width, int height, int[][] beach) {
	 	Helper.drawBox(best[0], best[1], width, height, beach);
	 	Helper.show(beach, "Found !");
	}
}
