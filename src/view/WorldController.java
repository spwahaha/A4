package view;

import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simulation.World;
import simulation.Critter;
import simulation.Food;
import simulation.HexCoord;
import simulation.Placeable;
import simulation.Rock;

public class WorldController {
	private static double HEX_LENGTH = 30;
	private static int ROW = 20;
	private static int COL = 10;
	private HashMap<Position,HexCoord> PosiToHex = new HashMap<Position,HexCoord>();
	private HashMap<HexCoord,Position> HexToPosi = new HashMap<HexCoord,Position>();
	private ArrayList<Position> positions = new ArrayList<Position>();
	private HexCoord selectedHex = null;
	private FileChooser fileChooser;
	@FXML
	private VBox root_vbox;
	@FXML
	private Canvas map_canvas;
	@FXML
	private ScrollPane map_scrollpane;
	@FXML
	private Pane pane;
	@FXML
	private TextArea rules_area;
	@FXML
	private TextArea lastExecutedRule_area;
	@FXML
	private Label critterName_Label;
	@FXML
	private TextArea critterMem_area;
	@FXML
	private Label foodValue_label;
	
	private World world;
	@FXML
	void initialize(){
		fileChooser = new FileChooser();
		System.out.println("initialization");
		map_scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		map_scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		map_scrollpane.setStyle("-fx-background: #FFFFFF;");
		System.out.print(pane);
		map_scrollpane.setContent(pane);
//		drawHex();
//		drawObject();
		
	}
	
	/**
	 * draw object in the world
	 */
	private void drawObject() {
		// TODO Auto-generated method stub
//		drawOneObj(new HexCoord(0,0),null);
//		drawOneObj(new HexCoord(4,2),null);
//		drawOneObj(new HexCoord(5,6),null);
	}

	/**
	 * Draw one object in the world given hexcoord 
	 * 
	 * @param c the column of hexcoord
	 * @param j the row of hexcoord
	 * @param dir the direction of the object, specularly for critter
	 */
	private void drawOneObj(HexCoord hex, Placeable obj) {
		// TODO Auto-generated method stub
		//when col is even number
		int c = hex.getCol();
		int r = hex.getRow();
		double x = HEX_LENGTH + c * 1.5 * HEX_LENGTH;
		int drawRol = ROW / 2;
		double intervalY = HEX_LENGTH * Math.sqrt(3) / 2;
		double offsetY = c % 2==0? 2 * intervalY:intervalY;
		if(COL % 2 == 1){
			drawRol -= c %2 == 0?0:1;
			offsetY = c % 2==1? 2 * intervalY:intervalY;
		}
		double bottomY = offsetY + (drawRol)* intervalY * 2;
		double y = bottomY - (r -((c + 1) / 2)) * intervalY * 2;
		double radius = 20;
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
//		System.out.println("x: " + x + "y:" + y);
//		System.out.println("width: " + object_canvas.getWidth());
//		System.out.println("height: " + object_canvas.getHeight());	
		if(obj == null){
			drawOneHex(x,y);
		}else if(obj instanceof Critter){
			drawOneObj(hex,null);
			drawCritter(x,y,(Critter)obj);
		}
		else if(obj instanceof Food){
			drawOneObj(hex,null);
			drawFood(x,y);
		}
		else{
			drawOneObj(hex,null);
			drawRock(x,y);
		}
	}

	/**
	 * draw a rock to a specific location
	 * 
	 * @param x the x coordinate of the location
	 * @param y the y coordinate of the location
	 */
	private void drawRock(double x, double y) {
		// TODO Auto-generated method stub
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
		gc.setFill(Color.RED);
		double side = HEX_LENGTH * 1.5;
		double x1 = x - side / 2;
		double x2 = x + side / 2;
		double x3 = x;
		double y1 = y + side / 2 / Math.sqrt(3);
		double y2 = y1;
		double y3 = y1 - side / 2 * Math.sqrt(3);
		gc.fillPolygon(new double[]{x1,x2,x3}, new double[]{y1,y2,y3}, 3);
	}

	/**
	 * Draw food to a specific location
	 * 
	 * @param x the x coordinate of the location
	 * @param y the y coordinate of the location
	 */
	private void drawFood(double x, double y) {
		// TODO Auto-generated method stub
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		double x1 = x - HEX_LENGTH/2;
		double x2 = x + HEX_LENGTH/2;
		double y1 = y - HEX_LENGTH/2;
		double y2 = y + HEX_LENGTH/2;
		gc.fillPolygon(new double[]{x1,x2,x2,x1}, new double[]{y1,y1,y2,y2}, 4);
	}

	/**
	 * Draw a critter to a specific location
	 * 
	 * @param x the x coordinate of location
	 * @param y the y coordinate of location
	 * @param cri the critter to be drawn
	 */
	private void drawCritter(double x, double y, Critter cri) {
		// TODO Auto-generated method stub
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
		int posture = cri.getMem(7);
		Color color = getPostureColor(posture);
		gc.setFill(color);
		int size = cri.getMem(3);
		double r = HEX_LENGTH / 2;
		r = r + size / 10 * HEX_LENGTH / 30;
		double x1 = x - r;
		double y1 = y - r;
		gc.fillOval(x1, y1, 2*r, 2*r);
		int dir = cri.getDirection();
		double theta = 2 * Math.PI / 6 * (3 - dir);
		double delta = 2 * Math.PI / 10;
		double theta2 = theta + delta;
		double eyeR = r / 3;
		double eye1x = x + r * Math.sin(theta + delta/2);
		double eye1y = y + r * Math.cos(theta + delta/2);
		double eye1x1 = eye1x - eyeR;
		double eye1y1 = eye1y - eyeR;
		gc.strokeOval(eye1x1, eye1y1, 2 * eyeR, 2 * eyeR);
		
		double eye2x = x + r * Math.sin(theta - delta/2);
		double eye2y = y + r * Math.cos(theta - delta/2);
		double eye2x1 = eye2x - eyeR;
		double eye2y1 = eye2y - eyeR;
		gc.strokeOval(eye2x1, eye2y1, 2 * eyeR, 2 * eyeR);
		
	}
	
	
	/**
	 * Get the color according to critter's posture
	 * 
	 * @param posture critter's posture
	 * @return color that corresponding to critter
	 */
	private Color getPostureColor(int posture) {
		// TODO Auto-generated method stub
		double n1 = posture/10;
		double n2 = posture%10;
		double n3 = n1 + n2 / 2;
		return Color.color(n1/10, n2/10, n3/10);
	}

	/**
	 * draw the hex map of map size ROW * COL
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
	 * draw the hex map of odd column number
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
					drawOneHex(x,y);
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
					drawOneHex(x,y);
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
	 * draw the hex map of even column number
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
//					System.out.println(j);
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y);
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
				double y = dy;
				double x = HEX_LENGTH;
				for(int j = 0; j <= drawRol; j++){
					x = (i+1) * 1.5 * HEX_LENGTH - 0.5 * HEX_LENGTH;
					drawOneHex(x,y);
//					System.out.println("i: " + (i) + "j: " + ((i+1)/2 +drawRol-j));
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
	 * 
	 * @param x the x position of center of the hexagon
	 * @param y the y position of the center of the hexagon
	 * @param length the side length of hexagon
	 */
	private void drawOneHex(double x, double y){
//		System.out.println("x: " + x + "y: " + y);
		double x1 = x - HEX_LENGTH;
		double x2 = x - HEX_LENGTH/2;
		double x3 = x + HEX_LENGTH/2;
		double x4 = x + HEX_LENGTH;
		double x5 = x + HEX_LENGTH/2;
		double x6 = x - HEX_LENGTH/2;
		double y1 = y;
		double y2 = y - HEX_LENGTH / 2 * Math.sqrt(3);
		double y3 = y2;
		double y4 = y;
		double y5 = y + HEX_LENGTH / 2 * Math.sqrt(3);
		double y6 = y5;
		if(map_canvas.getWidth() <= x4){
			map_canvas.setWidth(2 * x4);
		}
		if(map_canvas.getHeight() <= y2){
			map_canvas.setHeight(2 * y2);
		}
		
//		System.out.println(map_canvas.getWidth());
		GraphicsContext gc = map_canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.fillPolygon(new double[]{x1,x2,x3,x4,x5,x6},
				new double[]{y1,y2,y3,y4,y5,y6}, 6);
		gc.strokePolygon(new double[]{x1,x2,x3,x4,x5,x6},
				new double[]{y1,y2,y3,y4,y5,y6}, 6);
//		System.out.println(x + ":" + y);
	}
	
	/**
	 * handle event of clicking map
	 * 
	 * @param e
	 * @throws IOException 
	 */
	@FXML
	void mapClicked(MouseEvent e) throws IOException{
		if(e.getButton() == MouseButton.SECONDARY){
			System.out.println("right click");
			loadAcritter(e.getX(),e.getY());
		}else{
			System.out.println(e.getX() + ":    " + e.getY());
			this.selectedHex = getSelectedHex(e.getX(), e.getY());
			showHexInfo(this.selectedHex);
			System.out.println(this.selectedHex);
			System.out.println("mapclicked");
		}

	}
	/**
	 * Show the information of the selected hex
	 * @param Hex the selected hex
	 */
	private void showHexInfo(HexCoord Hex) {
		// TODO Auto-generated method stub
		Hashtable<HexCoord,Placeable> hs = this.world.getMap();
		Placeable obj = hs.get(Hex);
		if(obj == null || obj instanceof Rock){
			rules_area.setText("");
			lastExecutedRule_area.setText("");
			critterName_Label.setText("");
			critterMem_area.setText("");
			foodValue_label.setText("");
			return;
		} 
		if(obj instanceof Food){
			showFoodInfo((Food)obj);
		}else if(obj instanceof Critter){
			showCritterInfo((Critter)obj);
		}
	}

	/**
	 * Show critter information
	 * 
	 * @param cri
	 */
	private void showCritterInfo(Critter cri) {
		// TODO Auto-generated method stub
		foodValue_label.setText("");
		StringBuilder sb = new StringBuilder();
		cri.getRules().prettyPrint(sb);
//		System.out.println(rules_area);
		rules_area.setText(sb.toString());
		sb.delete(0, sb.length());
		cri.getRules().getRule(cri.getLastRuleIndex()).prettyPrint(sb);
		lastExecutedRule_area.setText(sb.toString());
		System.out.println(critterName_Label);
		critterName_Label.setText(cri.getName());
		sb.delete(0, sb.length());
		sb = cri.printMem();
		critterMem_area.setText(sb.toString());
	}

	/**
	 * show food information
	 * 
	 * @param food 
	 */
	private void showFoodInfo(Food food) {
		// TODO Auto-generated method stub
		rules_area.setText("");
		lastExecutedRule_area.setText("");
		critterName_Label.setText("");
		critterMem_area.setText("");
		this.foodValue_label.setText(""+food.getFoodValue());
	}

	/**
	 * load critter to a given location
	 * 
	 * @param x the x position of location
	 * @param y the y position of the location
	 * @throws IOException
	 */
	private void loadAcritter(double x, double y) throws IOException {
		// TODO Auto-generated method stub

		this.selectedHex = getSelectedHex(x, y);
		if(this.selectedHex == null){
			AlertInfo.invalidPositionAlert();
			return;
		}
		fileChooser.setTitle("Open World file");
		System.out.println(root_vbox);
		File critterFile = fileChooser.showOpenDialog(root_vbox.getScene().getWindow());
		if(critterFile == null) return;
		String absolutePath = critterFile.getAbsolutePath();
		Critter critter = new Critter(absolutePath);
		boolean succ = world.addObj(critter, selectedHex);
		System.out.println(succ);
		System.out.println(this.world.getCritterNumber());
		if(succ){
			drawOneObj(selectedHex, critter);
			System.out.println(this.world.getCritterNumber());
		}
	}
	
	/**
	 * Start world execution 
	 * 
	 * @param e
	 */
	@FXML
	void startExecute(MouseEvent e){
		boolean validWorld = checkWorld();
		if(validWorld){
			while(true){
				
			}
		}

		System.out.println("start");
		
	}
	
	/**
	 * Stop world execution
	 * 
	 * @param e
	 */
	@FXML
	void stopExecute(MouseEvent e){
		System.out.println("stop");
	}
	
	/**
	 * Check the world
	 * @return true if world exists
	 */
	private boolean checkWorld(){
		if(this.world == null){
			AlertInfo.noWolrdAlert();
			return false;
		}
		return true;
	}
	
	/**
	 * Execute step action
	 * 
	 * @param e
	 */
	@FXML
	void stepExecute(MouseEvent e){
		System.out.println("step");
		world.execute(1);
		Hashtable<HexCoord, Placeable> map = this.world.getMap();
		HashSet<HexCoord> changes = this.world.getChange();
		System.out.println(changes);
		for(HexCoord hex : changes){
			drawOneObj(hex,map.get(hex));
		}
	}
	
	/**
	 * Execute load world action,user can choose a world file
	 * 
	 * @param e mouse Event
	 */
	@FXML
	void loadWorld(MouseEvent e){
		fileChooser.setTitle("Open World file");
		System.out.println(root_vbox);
		File worldFile = fileChooser.showOpenDialog(root_vbox.getScene().getWindow());
		String absolutePath = worldFile.getAbsolutePath();
//		String absolutePath = "D:\workspace\eclipse\12A41\examples\attackworld.txt";
		try {
			this.world = new World(absolutePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ROW = this.world.getRow();
		COL = this.world.getCol();
		drawHex();
		Hashtable<HexCoord, Placeable> map = this.world.getMap();
		Set<HexCoord> keys = map.keySet();
		for(HexCoord hex : keys){
			drawOneObj(hex,map.get(hex));
		}
		
		System.out.println("load world successfully ");
	}
	
	/**
	 * Load a random world
	 * @param e
	 * @throws IOException
	 */
	@FXML
	void randomWorld(MouseEvent e) throws IOException{
		Hashtable<HexCoord, Placeable> map;
		Set<HexCoord> keys;
		if(this.world!=null){
			GraphicsContext gc = this.map_canvas.getGraphicsContext2D();
			gc.fillRect(0, 0, this.map_canvas.getWidth(), this.map_canvas.getHeight());
			map = this.world.getMap();
			keys = map.keySet();
			for(HexCoord hex : keys){
				drawOneObj(hex,null);
			}
			
		}

		
		this.world = new World();
		ROW = this.world.getRow();
		COL = this.world.getCol();
		drawHex();
		map = this.world.getMap();
		keys = map.keySet();
		for(HexCoord hex : keys){
			drawOneObj(hex,map.get(hex));
		}
		
		System.out.println("random world");
	}
	
	@FXML
	void loadCritter(MouseEvent e){
		
		System.out.println("load critter");
	}
	
	
	/**
	 * Get the corresponding Hexcoord by the x,y position
	 * 
	 * @param x the x position of the point
	 * @param y the y position of the point
	 * @return the corresponding hexcoord
	 */
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
