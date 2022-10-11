package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.constants.Const.Tile_Code;

public class Bomber extends Entity {
	Sprite[] player_down = {Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2};
	Sprite[] player_up = {Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2};
	Sprite[] player_right = {Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
	Sprite[] player_left = {Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2};
	private int xUnit;
	private int yUnit;
	private boolean right, left, up, down;
	private int frame;
	private boolean isRunning;
	private int speed;
	private int sizeBombStock;
	private boolean isAlive;
	public List<Bomb> bombs = new ArrayList<>();
    
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = true;
        this.setxUnit(x);
        this.setyUnit(y);
        frame = 0;
        isRunning = false;
        speed = 3;
        sizeBombStock = 2;
        setAlive(true);
    }
    /*
     * Doi trang thai cho Bomber
     */
    public void ChangeImg(Sprite sprite) {
    	img = sprite.getFxImage();
    }
    
    /*
     * Getter and Setter Method
     */
    public int getSpeed() {
    	return speed;
    }
    
    public void setSpeed(int speed) {
    	this.speed = speed;
    }
    
    public List<Bomb> getBombs() {
    	return bombs;
    }
    
    public boolean isMovedto(Tile[][] tile, int nextX, int nextY) {
    	int size = Sprite.SCALED_SIZE;
    	
    	int xUnit_1 = x / size;
    	int yUnit_1 = y / size;
    	
    	int xUnit_2 = (x + size -10) / size;
    	int yUnit_2 = y / size;
    	
    	int xUnit_3 = x / size;
    	int yUnit_3 = (y + size -2) / size;
    	
    	int xUnit_4 = (x + size -10) / size;
    	int yUnit_4 = (y + size - 2) / size;

        int nextX_1 = (nextX) / size;
        int nextY_1 = (nextY + 4) / size;

        int nextX_2 = (nextX + size - 10) / size;
        int nextY_2 = (nextY +4) / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 4) / size;

        int nextX_4 = (nextX + size - 10) / size;
        int nextY_4 = (nextY + size - 4) / size;
        
        if (tile[yUnit_1][xUnit_1].getCode().equals(Const.Tile_Code.BOMB) || 
        	tile[yUnit_2][xUnit_2].getCode().equals(Const.Tile_Code.BOMB) || 
        	tile[yUnit_3][xUnit_3].getCode().equals(Const.Tile_Code.BOMB) ||
        	tile[yUnit_4][xUnit_4].getCode().equals(Const.Tile_Code.BOMB)) {
        	return !(tile[nextY_1][nextX_1].getCode().equals(Const.Tile_Code.WALL) || tile[nextY_1][nextX_1].getCode() == Tile_Code.BRICK ||
        			tile[nextY_2][nextX_2].getCode().equals(Const.Tile_Code.WALL) || tile[nextY_2][nextX_2].getCode() == Tile_Code.BRICK ||
        			tile[nextY_3][nextX_3].getCode().equals(Const.Tile_Code.WALL) || tile[nextY_3][nextX_3].getCode() == Tile_Code.BRICK ||
        			tile[nextY_4][nextX_4].getCode().equals(Const.Tile_Code.WALL) || tile[nextY_4][nextX_4].getCode() == Tile_Code.BRICK);
        }
        return !((tile[nextY_1][nextX_1].getCode() == Const.Tile_Code.WALL || tile[nextY_1][nextX_1].getCode() == Const.Tile_Code.BOMB) ||
                (tile[nextY_2][nextX_2].getCode() == Const.Tile_Code.WALL || tile[nextY_2][nextX_2].getCode() == Const.Tile_Code.BOMB) ||
                (tile[nextY_3][nextX_3].getCode() == Const.Tile_Code.WALL|| tile[nextY_3][nextX_3].getCode() == Const.Tile_Code.BOMB) ||
                (tile[nextY_4][nextX_4].getCode() == Const.Tile_Code.WALL || tile[nextY_4][nextX_4].getCode() == Const.Tile_Code.BOMB) ||
                tile[nextY_1][nextX_1].getCode() == Tile_Code.BRICK || tile[nextY_2][nextX_2].getCode() == Tile_Code.BRICK ||
                tile[nextY_3][nextX_3].getCode() == Tile_Code.BRICK || tile[nextY_4][nextX_4].getCode() == Tile_Code.BRICK);
    }
    
    public void KeyPressed(Scene scene) {
    	scene.setOnKeyPressed(event-> {
        	switch(event.getCode()) {
        	case DOWN:
        		up = false;
        		down = true;
        		left = false;
        		right = false;
        		isRunning = true;
        		break;
        	case LEFT:
        		up = false;
        		down = false;
        		left = true;
        		right = false;
        		isRunning = true;
        		break;
        	case UP:
        		up = true;
        		down = false;
        		left = false;
        		right = false;
        		isRunning = true;
        		break;
        	case RIGHT:
        		up = false;
        		down = false;
        		left = false;
        		right = true;
        		isRunning = true;
        		break;
        	case SPACE:
        		if (bombs.size() < sizeBombStock) {
        			int bombx =(x + 10) / Sprite.SCALED_SIZE;
        			int bomby =(y + 10) / Sprite.SCALED_SIZE;
        			Bomb bomb = new Bomb(bombx, bomby, Sprite.bomb.getFxImage(), true);
        			bombs.add(bomb);
        		}
        	default:
        		break;
        	}
        }
        );
    }
    
    void show(Scene scene, Tile[][] tile) {
    	if (isRunning) {
    		frame++;
    		if (frame > 2) frame = 0;
    		if (right) {
        		ChangeImg(player_right[frame]);
        		if (isMovedto(tile, x + speed, y)) {
        			x = x + speed;
        			xUnit = (x + 16) / Sprite.SCALED_SIZE;
        			yUnit = (y + 16) / Sprite.SCALED_SIZE;
        		}
        	}
        	if (left) {
        		ChangeImg(player_left[frame]);
        		if (isMovedto(tile, x - speed, y)) {
        			x = x - speed;
        			xUnit = (x + 16) / Sprite.SCALED_SIZE;
        			yUnit = (y + 16) / Sprite.SCALED_SIZE;
        		}
        	}
        	if (up) {
        		ChangeImg(player_up[frame]);
        		if (isMovedto(tile, x, y - speed)) {
        			y = y - speed;
        			xUnit = (x + 16) / Sprite.SCALED_SIZE;
        			yUnit = (y + 16) / Sprite.SCALED_SIZE;
        		}
        	}
        	if (down) {
        		ChangeImg(player_down[frame]);
        		if (isMovedto(tile, x, y + speed)) {
        			y = y + speed;
        			xUnit = (x + 16) / Sprite.SCALED_SIZE;
        			yUnit = (y + 16) / Sprite.SCALED_SIZE;
        		}
        	}
    	}
    }
    
    public void KeyReleased(Scene scene) {
        scene.setOnKeyReleased(event-> {
        	switch(event.getCode()) {
        	case UP:
        		up = false;
        		break;
        	case RIGHT:
        		right = false;
        		break;
        	case DOWN:
        		down = false;
        		break;
        	case LEFT:
        		left = false;
        		break;
        	case SPACE:
        		break;
        	default:
        		break;
        	}
        });
    }
    
    @Override
    public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
    	if (tile[yUnit][xUnit].getCode() == Tile_Code.FLAME) {
    		isAlive = false;
    		System.out.println("Game Over!");
    	}
        KeyPressed(scene);
        KeyReleased(scene);
        show(scene, tile);
        bombs.forEach(g -> {
            	g.update(scene, gc, tile);
        });
        
        bombs.removeIf(g -> g.getIsExplode() == true);
    }
	public int getxUnit() {
		return xUnit;
	}
	public void setxUnit(int xUnit) {
		this.xUnit = xUnit;
	}
	public int getyUnit() {
		return yUnit;
	}
	public void setyUnit(int yUnit) {
		this.yUnit = yUnit;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
