package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import simulation.World;

public class WorldController {
	private static double HEX_LENGTH = 30;
	private static int ROW = 20;
	private static int COL = 18;
	
	@FXML
	private VBox root_vbox;
	@FXML
	private Canvas map_canvas;
	@FXML
	private ScrollPane map_scrollpane;
	
	
	private World world;
	@FXML
	void initialize(){
		System.out.println("initialization");
		map_scrollpane.setContent(map_canvas);
		map_scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		map_scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		map_scrollpane.setStyle("-fx-background: #FFFFFF;");
		drawHex();
		
	}
	
	/**
	 * draw the hex map with map size ROW * COL
	 */
	private void drawHex(){
//		int col = this.world.getCol();
		int col = COL;
		if(col % 2 ==0){
			drawEvenColHex();
		}else{
			drawOddColHex();
		}
	}
	
	/**
	 * draw the hex map with odd column number
	 */
	private void drawOddColHex() {
		// TODO Auto-generated method stub
//		int col = this.world.getCol(); // col is odd
//		int row = this.world.getRow();
		int col = COL;
		int row = ROW;
		int drawRol = row / 2;
		for(int i = 0; i < col; i++){
			if(i % 2 ==0){
				// 0, 2, 4,... has one more hex
				double dy = HEX_LENGTH * Math.sqrt(3) / 2;
				double offsetY = 2 * dy;
				double y = dy;
				double x = 0;
				for(int j = 0; j <= drawRol; j++){
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y,HEX_LENGTH);
					y += offsetY;
				}
			}else{
				// 1, 3, 5 has one less hex each column
				double dy = HEX_LENGTH * Math.sqrt(3) / 2;
				double offsetY = 2 * dy;
				double y = dy * 2;
				double x = HEX_LENGTH;
				for(int j = 0; j <= drawRol - 1; j++){
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y,HEX_LENGTH);
					y += offsetY;
				}
				
			}
		}
	}

	/**
	 * draw the hex map with even column number
	 */
	private void drawEvenColHex() {
		// TODO Auto-generated method stub
		int col = COL;
		int row = ROW;
		int drawRol = row / 2;
		for(int i = 0; i < col; i++){
			if(i % 2 ==0){
				// 0, 2, 4,... has one more hex
				double dy = HEX_LENGTH * Math.sqrt(3) / 2;
				double offsetY = 2 * dy;
				double y = 2 * dy;
				double x = 0;
				for(int j = 0; j <= drawRol; j++){
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y,HEX_LENGTH);
					y += offsetY;
				}
			}else{
				// 1, 3, 5 has one less hex each column
				double dy = HEX_LENGTH * Math.sqrt(3) / 2;
				double offsetY = 2 * dy;
				double y = dy;
				double x = HEX_LENGTH;
				for(int j = 0; j <= drawRol; j++){
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y,HEX_LENGTH);
					y += offsetY;
				}
				
			}
		}
	}

	/**
	 * draw one hex give x, y, and side length of hexagon
	 * @param x the x position of center of the hexagon
	 * @param y the y position of the center of the hexagon
	 * @param length the side lenght of hexagon
	 */
	private void drawOneHex(double x, double y, double length){
//		System.out.println("x: " + x + "y: " + y);
		double x1 = x - length;
		double x2 = x - length/2;
		double x3 = x + length/2;
		double x4 = x + length;
		double x5 = x + length/2;
		double x6 = x - length/2;
		double y1 = y;
		double y2 = y - length / 2 * Math.sqrt(3);
		double y3 = y2;
		double y4 = y;
		double y5 = y + length / 2 * Math.sqrt(3);
		double y6 = y5;
		if(map_canvas.getWidth() < x4)
			map_canvas.setWidth(x4 + 1);
		if(map_canvas.getHeight() < y2)
			map_canvas.setHeight(y2 + 1);
		
//		System.out.println(map_canvas.getWidth());
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
		gc.strokePolygon(new double[]{x1,x2,x3,x4,x5,x6},
				new double[]{y1,y2,y3,y4,y5,y6}, 6);
//		System.out.println(x + ":" + y);
	}

}
