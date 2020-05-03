package assignment1;

import java.awt.Point;
import java.util.*;

public class cat {
	board b;
	Point cat;
	Point mouse;
	mouse m;
	boolean mouseAlive;
	List<Point> mousePath;
	int dx[] = {-2, -1, 1, 2, -2, -1, 1, 2};  
	int dy[] = {-1, -2, -2, -1, 1, 2, 2, 1};  
	List<Point> catPaths = new ArrayList<>();
	int visited[][];  
	int dist[][]; 
	int dest;
	
	public void setBoard(board board) {
		b = board;
	}
	
	public void setMouse(mouse mouse) {
		m = mouse;
	}
	
	public void getPosition() {
		catPaths.clear();
		mouse = b.getMousePosition();
		cat = b.getCatPosition();
		mousePath = m.getMousePath();
		setUp();
		//System.out.println(mousePath);
		//System.out.println(cat);
	}
	
	public void setUp() {
		mouseAlive = true;
		visited = new int[b.getRows()+1][b.getColumns()+1];
		dist = new int[b.getRows()+1][b.getColumns()+1];
		dest = b.getRows();
	}
	
	public void moves() {
		if(catPaths.size() > 0) {
			b.setCatPosition(catPaths.remove(0));
		}
	}
	
	public void bfs() {
		getPosition();
		Map<Point, Integer> catPath = new HashMap<>();
		Map<List<Point>, Point> catMoves = new HashMap<>();
		Queue<Point> q = new LinkedList<>();
		List<Integer> num = new ArrayList<>();
		Point p = new Point();
		boolean first = true;
		int index = 1;
		int count = 0;
		q.add(cat);

	    for (int i = 0; i < b.getRows() ; i++) {
	        for (int j = 0; j < b.getColumns(); j++) { 
	            visited[i][j] = 0;  
	        }
	    }
	    visited[cat.x][cat.y] = 1;
	    
	    while(!q.isEmpty()) {
	    	List<Point> moves = new ArrayList<>();
	    	p = q.peek();
	    	q.remove();
	    		    	
	    	for(int i = 0; i < 8; i++) {
	    		int x = p.x + dx[i];
	    		int y = p.y + dy[i];
	    		if(validMove(x, y) && visited[x][y] == 0) {
	    			visited[x][y] = 1;
	    			Point v = new Point(x,y);
	    			q.add(v);
	    			moves.add(v);
	    			catPath.put(v, index);
	    		}
	    	}
	    	catMoves.put(moves, p);
	    	num.add(catPath.size());

	    	for(Point key : catPath.keySet()) {
	    		if(key.equals(mousePath.get(index)) && index == catPath.get(key)) {
	    			p = mousePath.get(index);
	    			eatMouse();
	    			q.clear();
	    			break;
	    		}
	    	}
	    	count++;
	    	
	    	if(first || count == num.get(0)) {
	    		if(!first) {
		    		int total = 0;
		    		while(count != 0) {
		    			total += num.get(1);
		    			num.remove(1);
		    			count--;
		    		}
		    		num.set(0, total);
	    		}
	    		first = false;
	    		count = 0;
	    		index++;
	    	}
	    	catPath.clear();
	    	if(index == mousePath.size()) {
	    		break;
	    	}
	    }

	    if(mouseAlive) {
	    	catPaths.clear();
	    } else {
	    	catPaths.add(p);
	    	while(true) {
				Set<List<Point>> keys = catMoves.keySet();
				for(List<Point> key : keys) {
					for(int i = 0; i < key.size(); i++) {
						if(key.get(i).equals(p)) {
							catPaths.add(0, catMoves.get(key));
							p = catMoves.get(key);
							break;
						}
					}
					if(p.equals(cat)) {
						catPaths.remove(0);
						break;
					}
				}
				if(p.equals(cat)) {
					break;
				}
			}
	    }
	}
	
	public void dfsHelper(Point p, Map<Point, Point> prevPoint, Map<Point, Integer> steps) {
		if(p.equals(cat)) {
			steps.put(p, 0);
			visited[p.x][p.y] = 1;
		}
		
		for(int i = 0; i < 8; i++) {
			int x = p.x + dx[i];
			int y = p.y + dy[i];
			Point v = new Point(x,y);
			if(validMove(x,y) && visited[x][y] == 0) {
				visited[x][y] = 1;
				prevPoint.put(v, p);
				int cost = steps.get(p);
				steps.put(v, cost+1);
				dfsHelper(v, prevPoint, steps);
			} else if(validMove(x,y) && steps.get(v) > steps.get(p)+1) {
				int cost = steps.get(p);
				steps.put(v, cost+1);
				prevPoint.replace(v, p);
				dfsHelper(v, prevPoint, steps);
			}
		}
	}
	
	
	public void dfs() {
		getPosition();
		Map<Point, Point> prevPoint = new HashMap<>();
		Map<Point, Integer> steps = new HashMap<>();
		dfsHelper(cat, prevPoint, steps);
		Point p = new Point();
		for(int i = 1; i < mousePath.size(); i++) {
			if(steps.containsKey(mousePath.get(i))){
				if(steps.get(mousePath.get(i)) == i) {
					p = mousePath.get(i);
					catPaths.add(p);
					mouseAlive = false;
					break;
				}
			}
		}
		if(mouseAlive) {
			catPaths.clear();
		} else {
			while(true) {
				p = prevPoint.get(p);
				if(p.equals(cat)){
					break;
				}
				catPaths.add(0,p);
			}
		}
	}

	public void astar(){
		getPosition();
		Queue<Point> q = new LinkedList<>();
		Map<Point, Integer> catPath = new HashMap<>();
		Map<List<Point>, Point> catMoves = new HashMap<>();
		List<Integer> num = new ArrayList<>();
		q.add(cat);
		Point p = new Point();
		int index = 1;
		boolean first = true;
		int count = 0;
		Map <Point, Integer> g = new HashMap<>();
		Map <Point, Integer> h = new HashMap<>();
		Map <Point, Integer> f = new HashMap<>();
		g.put(cat, 0);
		h.put(cat, 0);
		f.put(cat, 0);
		for (int i = 0; i < b.getRows() ; i++) {
	        for (int j = 0; j < b.getColumns(); j++) { 
	            visited[i][j] = 0;  
	        }
	    }
		visited[cat.x][cat.y] = 1;
		
		while(!q.isEmpty()) {
			List<Point> moves = new ArrayList<>();
			p = q.remove();
					
			for(int i = 0; i < 8; i++) {
				int x = p.x + dx[i];
				int y = p.y + dy[i];
				if(validMove(x,y) && visited[x][y] == 0) {
					Point v = new Point(x,y);
					visited[x][y] = 1;
					int cost = g.get(p)+23; //√5*10
					g.put(v, cost);
					int estimatedCost = heuristicOne(v,mousePath.get(index));
					h.put(v, estimatedCost);
					f.put(v, cost+estimatedCost);
					q.add(v);
					moves.add(v);
	    			catPath.put(v, index);
				}
			}
			catMoves.put(moves, p);
	    	num.add(catPath.size());
	    	
	    	for(Point key : catPath.keySet()) {
	    		if(key.equals(mousePath.get(index)) && index == catPath.get(key)) {
	    			p = mousePath.get(index);
	    			eatMouse();
	    			q.clear();
	    			break;
	    		}
	    	}
	    	count++;
	    	
	    	if(first || count == num.get(0)) {
	    		if(!first) {
		    		int total = 0;
		    		while(count != 0) {
		    			total += num.get(1);
		    			num.remove(1);
		    			count--;
		    		}
		    		num.set(0, total);
	    		}
	    		first = false;
	    		count = 0;
	    		index++;
	    	}
	    	catPath.clear();
	    	if(index == mousePath.size()) {
	    		break;
	    	}
	    }

	    if(mouseAlive) {
	    	catPaths.clear();
	    } else {
	    	catPaths.add(p);
	    	while(true) {
				Set<List<Point>> keys = catMoves.keySet();
				for(List<Point> key : keys) {
					for(int i = 0; i < key.size(); i++) {
						if(key.get(i).equals(p)) {
							catPaths.add(0, catMoves.get(key));
							p = catMoves.get(key);
							break;
						}
					}
					if(p.equals(cat)) {
						catPaths.remove(0);
						break;
					}
				}
				if(p.equals(cat)) {
					break;
				}
			}
		}
	}
	
	public int heuristicOne(Point c, Point m) {
		return (Math.abs(c.x-m.x) + Math.abs(c.y-m.y)) * 10; //Manhattan distance
	}
	
	public int heuristicTwo(Point c, Point m) {
		return (int) Math.sqrt((c.y - m.y) * (c.y - m.y) + (c.x - m.x) * (c.x - m.x)); //Euclidean distance
	}
	
	public int heuristicThree(Point c, Point m) {
		int one = heuristicOne(c,m);
		int two = heuristicTwo(c,m);
		return (int) (one + two) / 2; //The average of Manhattan distance and Euclidean distance
	}
	
	public void eatMouse() {
		mouseAlive = false;
	}
	
	public boolean validMove(int x, int y) {
		if((x >= 0 && x < b.getRows()) && (y >= 0) && y < b.getColumns()) {
			return true;
		} 
		return false;
	}
}
