package assignment1;

import java.awt.*;
import java.util.*;

public class game {
	
	public static void main(String [] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		while(true) {
			board b = new board();
			mouse m = new mouse();
			cat c = new cat();
			b.placeCheese();
			b.placeMousePosition();
			b.placeCatPosition();
			m.setBoard(b);
			c.setBoard(b);
			c.setMouse(m);
			c.astar();
			b.mainBoard();
			if(c.catPaths.size() == 0) {
				System.out.println("Cat cannot catch the mouse in this round. \n");
				System.out.println("Next round? (y or n)");
				char choice = sc.next().charAt(0);
				if(choice != 'y') {
					System.out.println("Bye!!!");
					break;
				}
				continue;
			}
			while(true) {
				m.move();
				b.mainBoard();
				Thread.sleep(2500);
				if(b.getCheesePosition().size() == 0) {
					break;
				}
				if(b.gameOver()) {
					break;
				}
				c.moves();
				b.mainBoard();
				Thread.sleep(2500);
				if(b.gameOver()) {
					break;
				}
			}
			System.out.println("The cat caught the mouse. Game finished.");
			System.out.println("Next round? (y or n)");
			char choice = sc.next().charAt(0);
			if(choice != 'y') {
				System.out.println("Bye!!!");
				break;
			}
		}
	}
}
