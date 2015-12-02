package view;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import simulation.World;
import simulation.HexCoord;

public class WorldController {
	private static double HEX_LENGTH = 30;
	private static int ROW = 20;
	private static int COL = 10;
	private HashMap<Position,HexCoord> PosiToHex = new HashMap<Position,HexCoord>();
	private HashMap<HexCoord,Position> HexToPosi = new HashMap<HexCoord,Position>();
	private ArrayList<Position> positions = new ArrayList<Position>();
	private HexCoord selectedHex = null;
	
	@FXML
	private VBox root_vbox;
	@FXML
	private Canvas map_canvas;
	@FXML
	private Canvas object_canvas;
	@FXML
	private ScrollPane map_scrollpane;
	@FXML
	private Pane pane;
	
	private World world;
	@FXML
	void initialize(){
		System.out.println("initialization");
		map_scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		map_scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		map_scrollpane.setStyle("-fx-background: #FFFFFF;");
		System.out.print(pane);
		map_scrollpane.setContent(pane);
		drawHex();
		drawObject();
		
	}
	
	private void drawObject() {
		// TODO Auto-generated method stub
		drawOneObj(0,0,0);
		drawOneObj(4,2,1);
		drawOneObj(5,6,2);
	}

	private void drawOneObj(int i, int j, int dir) {
		// TODO Auto-generated method stub
		//when col is even number
		double x = HEX_LENGTH + i * 1.5 * HEX_LENGTH;
		int drawRol = ROW / 2;
		double intervalY = HEX_LENGTH * Math.sqrt(3) / 2;
		double offsetY = i % 2==0? 2 * intervalY:intervalY;
		if(COL % 2 == 1){
			drawRol -= i %2 == 0?0:1;
			offsetY = i % 2==1? 2 * intervalY:intervalY;
		}
		double bottomY = offsetY + (drawRol)* intervalY * 2;
		double y = bottomY - (j -((i + 1) / 2)) * intervalY * 2;
		double radius = 20;
		GraphicsContext gc = object_canvas.getGraphicsContext2D();
//		System.out.println("x: " + x + "y:" + y);
//		System.out.println("width: " + object_canvas.getWidth());
//		System.out.println("height: " + object_canvas.getHeight());	
		
		gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
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
					int hexC = i;
					int hexR = (i+1)/2 +drawRol-j;
//					System.out.println("i: " + (i) + "j: " + ((i+1)/2 +drawRol-j));
					Position posi = new Position(x,y);
					HexCoord hex = new HexCoord(hexC, hexR);
					this.PosiToHex.put(posi, hex);
					this.HexToPosi.put(hex, posi);
					this.positions.add(posi);
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
					int hexC = i;
					int hexR = (i+1)/2 +drawRol-j-1;
					Position posi = new Position(x,y);
					HexCoord hex = new HexCoord(hexC, hexR);
					this.PosiToHex.put(posi, hex);
					this.HexToPosi.put(hex, posi);
					this.positions.add(posi);
					y += offsetY;
//					System.out.println("i: " + (i) + "j: " + ((i+1)/2 +drawRol-j-1));
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
					System.out.println(j);
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y,HEX_LENGTH);
					int hexC = i;
					int hexR = (i+1)/2 +drawRol-j;
					System.out.println("i: " + (i) + "j: " + ((i+1)/2 +drawRol-j));
					Position posi = new Position(x,y);
					HexCoord hex = new HexCoord(hexC, hexR);
					this.PosiToHex.put(posi, hex);
					this.HexToPosi.put(hex, posi);
					this.positions.add(posi);
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
					System.out.println("i: " + (i) + "j: " + ((i+1)/2 +drawRol-j));
					int hexC = i;
					int hexR = (i+1)/2 +drawRol-j;
					Position posi = new Position(x,y);
					HexCoord hex = new HexCoord(hexC, hexR);
					this.PosiToHex.put(posi, hex);
					this.HexToPosi.put(hex, posi);
					this.positions.add(posi);
					y += offsetY;
				}
				
			}
		}
	}

	/**
	 * draw one hex give x, y, and side length of hexagon
	 * @param x the x position of center of the hexagon
	 * @param y the y position of the center of the hexagon
	 * @param length the side length of hexagon
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
		if(map_canvas.getWidth() <= x4){
			map_canvas.setWidth(2 * x4);
			object_canvas.setWidth(2 * x4);
		}
		if(map_canvas.getHeight() <= y2){
			map_canvas.setHeight(2 * y2);
			object_canvas.setHeight(2 * y2);
		}
		
//		System.out.println(map_canvas.getWidth());
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
		gc.strokePolygon(new double[]{x1,x2,x3,x4,x5,x6},
				new double[]{y1,y2,y3,y4,y5,y6}, 6);
//		System.out.println(x + ":" + y);
	}
	
	@FXML
	void mapClicked(MouseEvent e){
		System.out.println(e.getX() + ":    " + e.getY());
		this.selectedHex = getSelectedHex(e.getX(), e.getY());
		System.out.println(this.selectedHex);
		System.out.println("mapclicked");
	}

	private HexCoord getSelectedHex(double x, double y) {
		// TODO Auto-generated method stub
		double length = Double.MAX_VALUE;
		Position minPosi = null;
		for(Position posi:this.positions){
			double distance = posi.dist(x, y);
			if(distance <= HEX_LENGTH * Math.sqrt(3) / 2){
				return this.PosiToHex.get(posi);
			}
			if(distance <= HEX_LENGTH && distance <=length){
				minPosi = posi;
			}
		}
		if(minPosi == null) return null;
		return this.PosiToHex.get(minPosi);
	}
	

}
