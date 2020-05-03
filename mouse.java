package assignment1;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class mouse {
	board b;
	Point mouse;
	
	public void setBoard(board board) {
		b = board;
	}
	
	public void move() {
		Point target = new Point();
		target = getTarget();
		if(target.x > mouse.x && target.y == mouse.y) {
			b.setMousePosition(moveRight());
		} else if(target.x < mouse.x && target.y == mouse.y) {
			b.setMousePosition(moveLeft());
		} else if(target.x == mouse.x && target.y > mouse.y) {
			b.setMousePosition(moveDown());
		} else if(target.x == mouse.x && target.y < mouse.y) {
			b.setMousePosition(moveUp());
		} else if(target.x > mouse.x && target.y > mouse.y) {
			b.setMousePosition(moveDownRight());
		} else if(target.x < mouse.x && target.y < mouse.y) {
			b.setMousePosition(moveUpLeft());
		} else if(target.x < mouse.x && target.y > mouse.y) {
			b.setMousePosition(moveDownLeft());
		} else if(target.x > mouse.x && target.y < mouse.y) {
			b.setMousePosition(moveUpRight());
		}
	}
	
	public Point nextMove() {
		Point target = new Point();
		target = getTarget();
		if(target == null) {
			return null;
		}
		if(target.x > mouse.x && target.y == mouse.y) {
			return moveRight();
		} else if(target.x < mouse.x && target.y == mouse.y) {
			return moveLeft();
		} else if(target.x == mouse.x && target.y > mouse.y) {
			return moveDown();
		} else if(target.x == mouse.x && target.y < mouse.y) {
			return moveUp();
		} else if(target.x > mouse.x && target.y > mouse.y) {
			return moveDownRight();
		} else if(target.x < mouse.x && target.y < mouse.y) {
			return moveUpLeft();
		} else if(target.x < mouse.x && target.y > mouse.y) {
			return moveDownLeft();
		} else if(target.x > mouse.x && target.y < mouse.y) {
			return moveUpRight();
		} 
		return mouse;
	}
	
	public List<Point> getMousePath(){
		List<Point> moves = new ArrayList<>();
		Point original = b.getMousePosition();
		List<Point> cheese = new ArrayList<>();
		for(int i = 0; i < b.getCheesePosition().size(); i++) {
			cheese.add(b.getCheesePosition().get(i));
		}
		moves.add(original);
		while(true) {
			Point m = nextMove();
			if(m == null) {
				break;
			}
			moves.add(m);
			b.setMousePosition(m);
		}
		b.setMousePosition(original);
		b.setCheesePosition(cheese);
		return moves;
	}
	
	public Point getTarget() {
		mouse = b.getMousePosition();
		int index = 0;
		int shortest = 0;
		for(int i = 0; i < b.getCheesePosition().size(); i++) {   
		    int distance = (int) Math.sqrt((mouse.y - b.getCheesePosition().get(i).y)*(mouse.y - b.getCheesePosition().get(i).y) + (mouse.x - b.getCheesePosition().get(i).x)*(mouse.x - b.getCheesePosition().get(i).x));
		    if(distance < shortest || shortest == 0) {
		    	shortest = distance;
		    	index = i;
		    }
		}
		if(b.getCheesePosition().size() > 0) {
			return b.getCheesePosition().get(index);
		} else {
			return null;
		}
	}
	
	public Point moveUp() {
		Point p = new Point();
		p.x = mouse.x;
		p.y = mouse.y-1;
		return p;
	}
	
	public Point moveDown() {
		Point p = new Point();
		p.x = mouse.x;
		p.y = mouse.y+1;
		return p;
	}
	
	public Point moveRight() {
		Point p = new Point();
		p.x = mouse.x+1;
		p.y = mouse.y;
		return p;
	}
	
	public Point moveLeft() {
		Point p = new Point();
		p.x = mouse.x-1;
		p.y = mouse.y;
		return p;
	}
	
	public Point moveUpRight() {
		Point p = new Point();
		p.x = mouse.x+1;
		p.y = mouse.y-1;
		return p;
	}
	
	public Point moveDownRight() {
		Point p = new Point();
		p.x = mouse.x+1;
		p.y = mouse.y+1;
		return p;
	}
	
	public Point moveUpLeft() {
		Point p = new Point();
		p.x = mouse.x-1;
		p.y = mouse.y-1;
		return p;
	}
	
	public Point moveDownLeft() {
		Point p = new Point();
		p.x = mouse.x-1;
		p.y = mouse.y+1;
		return p;
	}
}
