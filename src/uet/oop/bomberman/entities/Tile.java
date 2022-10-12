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
    
    public static boolean isConnected(Tile[][] tile, int srcX, int srcY, int desX, int desY, boolean[][] found) {
    	if (srcX == desX && srcY == desY) {
    		return true;
    	}
    	found[srcX][srcY] = true;
    	if (!found[srcX][srcY-1] && srcX >= 1 && srcX <= Const.HEIGHT-2 && srcY-1 >=1 && srcY-1 <= Const.WIDTH-2 && tile[srcX][srcY-1].code  != Tile_Code.WALL && tile[srcX][srcY-1].code != Tile_Code.BRICK && tile[srcX][srcY-1].code != Tile_Code.BOMB) {
    		if (isConnected(tile, srcX, srcY-1, desX, desY, found)) return true;
    	} 
    	if (!found[srcX][srcY+1] && srcX >= 1 && srcX <= Const.HEIGHT-2 && srcY+1 >=1 && srcY+1 <= Const.WIDTH-2 && tile[srcX][srcY+1].code  != Tile_Code.WALL && tile[srcX][srcY+1].code != Tile_Code.BRICK && tile[srcX][srcY+1].code != Tile_Code.BOMB) {
    		if (isConnected(tile, srcX, srcY+1, desX, desY, found)) return true;
    	} 
    	if (!found[srcX + 1][srcY] && srcX + 1 >= 1 && srcX + 1<= Const.HEIGHT-2 && srcY>=1 && srcY-1 <= Const.WIDTH-2 && tile[srcX + 1][srcY].code  != Tile_Code.WALL && tile[srcX + 1][srcY].code != Tile_Code.BRICK && tile[srcX + 1][srcY].code != Tile_Code.BOMB) {
    		if (isConnected(tile, srcX + 1, srcY, desX, desY, found)) return true;;
    	} 
    	if (!found[srcX - 1][srcY] && srcX -1 >= 1 && srcX - 1 <= Const.HEIGHT-2 && srcY>=1 && srcY <= Const.WIDTH-2 && tile[srcX - 1][srcY].code  != Tile_Code.WALL && tile[srcX - 1][srcY].code != Tile_Code.BRICK && tile[srcX - 1][srcY].code != Tile_Code.BOMB) {
    		if (isConnected(tile, srcX - 1, srcY, desX, desY, found)) return true;
    	} 
    	
    	return false;
    }
    
    public static List<Pair<Integer, Integer>> findPath(Tile[][] tile, int srcX, int srcY, int desX, int desY) {
    	LinkedList<Pair<Integer, Integer>> path = new LinkedList<Pair<Integer, Integer>>();
        Pair<Integer, Integer>[][] prev = new Pair[Const.HEIGHT][Const.WIDTH];
		boolean[][] found = new boolean[Const.HEIGHT][Const.WIDTH];
		for (int i = 0; i < Const.HEIGHT-1 ; i++) {
			for (int j = 0; j < Const.WIDTH-1; j++) {
                found[i][j] = false;
			}
		}
		boolean isConnected = true;
		int[][] minDist = new int[Const.HEIGHT][Const.WIDTH];
		for (int i = 1; i < Const.HEIGHT -1; i++) {
			for (int j = 1; j < Const.WIDTH -1; j++) {
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
					if (tile[i][j].code  != Tile_Code.WALL && tile[i][j].code != Tile_Code.BRICK && tile[i][j].code != Tile_Code.BOMB && !found[i][j] && minDist[i][j] < dist) {
						dist = minDist[i][j];
						x = i;
						y = j;
					}
				}
			}
			found[x][y] = true;
			if (x == desX && y == desY) {
				break;
			}
			if (tile[x][y-1].code  != Tile_Code.WALL && tile[x][y-1].code != Tile_Code.BRICK && tile[x][y-1].code != Tile_Code.BOMB) {
				if (minDist[x][y-1] > minDist[x][y] + 1) {
					minDist[x][y-1] = minDist[x][y] + 1;
					prev[x][y-1] = new Pair<Integer, Integer>(x,y);
 				}
			} 
			if (tile[x-1][y].code  != Tile_Code.WALL && tile[x-1][y].code != Tile_Code.BRICK && tile[x-1][y].code != Tile_Code.BOMB) {
				if (minDist[x-1][y] > minDist[x][y] + 1) {
					minDist[x-1][y] = minDist[x][y] + 1;
					prev[x-1][y] = new Pair<Integer, Integer>(x,y);
					
 				}
			}
			if (tile[x][y+1].code  != Tile_Code.WALL && tile[x][y+1].code != Tile_Code.BRICK && tile[x][y + 1].code != Tile_Code.BOMB) {
				if (minDist[x][y+1] > minDist[x][y] + 1) {
					minDist[x][y+1] = minDist[x][y] + 1;
					prev[x][y+1] = new Pair<Integer, Integer>(x,y);
					
 				}
			}
			if (tile[x+1][y].code  != Tile_Code.WALL && tile[x +1][y].code != Tile_Code.BRICK && tile[x+1][y].code != Tile_Code.BOMB) {
				if (minDist[x+1][y] > minDist[x][y] + 1) {
					minDist[x+1][y] = minDist[x][y] + 1;
					prev[x+1][y] = new Pair<Integer, Integer>(x,y);
					
 				}
			}
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
		if (!path.isEmpty()) path.removeFirst();
		return path;
    }
}
