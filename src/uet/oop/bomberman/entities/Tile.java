package uet.oop.bomberman.entities;

import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.constants.Const.Tile_Code;

public class Tile {
	
    private Tile_Code code;
    private Entity type;
    
    public Tile(Tile_Code code, Entity type) {
    	this.code = code;
    	this.type = type;
    }
    
    public Tile_Code getCode() {
    	return this.code;
    }
    
    public void setCode(Tile_Code code) {
    	this.code = code;
    }
    
    public Entity getType() {
    	return type;
    }
    
    public void setType(Entity type) {
    	this.type = type;
    }
    
    public static List<Pair<Integer, Integer>> findPath(Tile[][] tile, int srcX, int srcY, int desX, int desY) {
    	LinkedList<Pair<Integer, Integer>> path = new LinkedList<Pair<Integer, Integer>>();
        Pair<Integer, Integer>[][] prev = new Pair[Const.HEIGHT][Const.WIDTH];
		boolean[][] found = new boolean[Const.HEIGHT][Const.WIDTH];
		for (int i = 0; i < Const.HEIGHT ; i++) {
			for (int j = 0; j < Const.WIDTH; j++) {
                found[i][j] = false;
			}
		}
		boolean isConnected = true;
		int[][] minDist = new int[Const.HEIGHT][Const.WIDTH];
		for (int i = 1; i < Const.HEIGHT; i++) {
			for (int j = 1; j < Const.WIDTH; j++) {
                minDist[i][j] = Integer.MAX_VALUE;
			}
		}
		int isChecked = 0;
		minDist[srcX][srcY] = 0;
		while (true) {
			int x = 0, y = 0;
			int dist = Integer.MAX_VALUE;
			for (int i = 1; i < Const.HEIGHT-1; i++) {
				for (int j = 1; j < Const.WIDTH-1; j++) {
					if (!found[i][j] && minDist[i][j] < dist) {
						dist = minDist[i][j];
						x = i;
						y = j;
					}
				}
			}
			
//			System.out.println(x + " " + y);
			found[x][y] = true;
			isChecked++;
			if (x == desX && y == desY) {
				break;
			}
			if (isChecked == (Const.HEIGHT-1)*(Const.WIDTH-1)) {
				break;
			}
			isConnected = false;
			if (tile[x][y-1].code  != Tile_Code.WALL) {
				if (minDist[x][y-1] > minDist[x][y] + 1) {
					minDist[x][y-1] = minDist[x][y] + 1;
					prev[x][y-1] = new Pair<Integer, Integer>(x,y);
 				}
				isConnected = true;
			} 
			if (tile[x-1][y].code  != Tile_Code.WALL) {
				if (minDist[x-1][y] > minDist[x][y] + 1) {
					minDist[x-1][y] = minDist[x][y] + 1;
					prev[x-1][y] = new Pair<Integer, Integer>(x,y);
					
 				}
				isConnected = true;
			}
			if (tile[x][y+1].code  != Tile_Code.WALL) {
				if (minDist[x][y+1] > minDist[x][y] + 1) {
					minDist[x][y+1] = minDist[x][y] + 1;
					prev[x][y+1] = new Pair<Integer, Integer>(x,y);
					
 				}
				isConnected = true;
			}
			if (tile[x+1][y].code  != Tile_Code.WALL) {
				if (minDist[x+1][y] > minDist[x][y] + 1) {
					minDist[x+1][y] = minDist[x][y] + 1;
					prev[x+1][y] = new Pair<Integer, Integer>(x,y);
					
 				}
				isConnected = true;
			}
			if (!isConnected) break;
		}
		int desXX = desX, desYY = desY;
		path.addFirst(new Pair<Integer, Integer>(desX, desY));
		if (isConnected) {
			while (desXX != srcX || desYY !=srcY) {
				path.addFirst(prev[desXX][desYY]);
				int x = prev[desXX][desYY].getKey();
				int y = prev[desXX][desYY].getValue();
				desXX = x;
				desYY = y;
			}
		}
		path.removeFirst();
		return path;
    }
}
