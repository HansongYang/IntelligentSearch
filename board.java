package assignment1;

import java.awt.Point;
import java.util.*;

public class board {
	private int row = 12;
	private int column = 12;
	private int numberOfCheese = 5;
	private List<Point> cheese = new ArrayList<>();
	private Point mouse;
	private Point cat;
	private boolean mouseAlive = true;
	public static final String BLUE = "\033[0;34m";
	public static final String RESET = "\033[0m";
	public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m"; 
	
	public void mainBoard() {
		checkMouse();
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				System.out.print("---");
			}
			System.out.println();
			for(int j = 0; j < column; j++) {
				if(i == cat.x && j == cat.y) {
					System.out.print("| " + BLUE + "C" + RESET);
				} else if(i == mouse.x && j == mouse.y) {
					System.out.print("| " + GREEN + "M" + RESET);
				} else {
					boolean print = false;
					for(int k = 0; k < cheese.size(); k++) {
						if(i == cheese.get(k).x && j == cheese.get(k).y) {
							System.out.print("| " + YELLOW + "E"+ RESET);
							print = true;
							break;
						}
					}
					if(!print) {
						System.out.print("|  ");
					}
				}
				if(j == column-1) {
					System.out.print("|");
				}
			}
			System.out.println();
		}
		for(int j = 0; j < column; j++) {
			System.out.print("---");
		}
		System.out.println();
	}
	
	public void placeCheese() {
		Random rand = new Random();
		for(int i = 0; i < numberOfCheese; i++) {
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			Point ch = new Point(x,y);
			if(cheese.size() > 0) {
				for(int j = 0; j < cheese.size(); j++) {
					if(ch.equals(cheese.get(j))) {
						i--;
						continue;
					}
				}
			}
			cheese.add(ch);
		}
	}
	
	public void placeMousePosition() {
		Random rand = new Random();
		boolean duplicate;
		while(true) {
			duplicate = false;
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			mouse = new Point(x,y);
			if(cheese.size() > 0) {
				for(int i = 0; i < cheese.size(); i++) {
					if(mouse.equals(cheese.get(i))) {
						duplicate = true;
					}
				}
			}
			if(!duplicate) {
				break;
			}
		}
	}
	
	public void checkMouse() {
		if(cat.equals(mouse)) {
			mouseAlive = false;
		}
	}
	
	public boolean gameOver() {
		if(mouseAlive) {
			return false;
		} else {
			return true;
		}
	}
	
	public void placeCatPosition() {
		Random rand = new Random();
		boolean duplicate;
		while(true) {
			duplicate = false;
			int x = rand.nextInt(12);
			int y = rand.nextInt(12);
			cat = new Point(x,y);
			if(cheese.size() > 0) {
				for(int i = 0; i < cheese.size(); i++) {
					if(cat.equals(cheese.get(i))) {
						duplicate = true;
					}
				}
			}
			if(mouse.equals(cat)) {
				duplicate = true;
			}
			if(!duplicate) {
				break;
			}
		}
	}
	
	public List<Point> getCheesePosition() {
		return cheese;
	}
	
	public Point getMousePosition() {
		return mouse;
	}
	
	public Point getCatPosition() {
		return cat;
	}
	
	public int getRows() {
		return row;
	}
	
	public int getColumns() {
		return column;
	}
	
	public void setMousePosition(Point mouse) {
		for(int i = 0; i < cheese.size(); i++) {
			if(mouse.equals(cheese.get(i))) {
				eatCheese(cheese.get(i));
			}
		}
		this.mouse = mouse;
	}
	
	public void setCatPosition(Point cat) {
		this.cat = cat;
	}
	
	public void setCheesePosition(List<Point> cheese) {
		for(int i = 0; i < cheese.size(); i++) {
			this.cheese.add(cheese.get(i));
		}
	}
	
	public void eatCheese(Point c) {
		for(int i = 0; i < cheese.size(); i++) {
			if(cheese.get(i).equals(c)) {
				cheese.remove(i);
			}
		}
	}
}
