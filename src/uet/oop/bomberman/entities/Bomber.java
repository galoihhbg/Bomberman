package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.constants.Const;

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
        sizeBombStock = 1;
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
    
    public boolean isMovedto(int[][] tile, int nextX, int nextY) {
    	if (tile[yUnit][xUnit] == Const.BOMB) {
    		return true;
    	}
    	int size = Sprite.SCALED_SIZE;

        int nextX_1 = (nextX) / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 10) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 2) / size;

        int nextX_4 = (nextX + size - 10) / size;
        int nextY_4 = (nextY + size - 2) / size;
        
        if (tile[yUnit][xUnit] == Const.BOMB) {
        	return !((tile[nextY_1][nextX_1] == Const.WALL ||
                (tile[nextY_2][nextX_2] == Const.WALL ||
                (tile[nextY_3][nextX_3] == Const.WALL ||
                (tile[nextY_4][nextX_4] == Const.WALL)))));
        }
        return !((tile[nextY_1][nextX_1] == Const.WALL || tile[nextY_1][nextX_1] == Const.BOMB) ||
                (tile[nextY_2][nextX_2] == Const.WALL || tile[nextY_2][nextX_2] == Const.BOMB) ||
                (tile[nextY_3][nextX_3] == Const.WALL|| tile[nextY_3][nextX_3] == Const.BOMB) ||
                (tile[nextY_4][nextX_4] == Const.WALL || tile[nextY_4][nextX_4] == Const.BOMB));
    }
    
    public void KeyPressed(Scene scene) {
    	scene.setOnKeyPressed(event-> {
        	switch(event.getCode()) {
        	case S:
        		up = false;
        		down = true;
        		left = false;
        		right = false;
        		isRunning = true;
        		break;
        	case A:
        		up = false;
        		down = false;
        		left = true;
        		right = false;
        		isRunning = true;
        		break;
        	case W:
        		up = true;
        		down = false;
        		left = false;
        		right = false;
        		isRunning = true;
        		break;
        	case D:
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
    
    void show(Scene scene, int[][] tile) {
    	if (isRunning) {
    		frame++;
    		if (frame > 2) frame = 0;
    		if (right) {
        		ChangeImg(player_right[frame]);
        		if (isMovedto(tile, x + speed, y)) {
        			x = x + speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		}
        	}
        	if (left) {
        		ChangeImg(player_left[frame]);
        		if (isMovedto(tile, x - speed, y)) {
        			x = x - speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		}
        	}
        	if (up) {
        		ChangeImg(player_up[frame]);
        		if (isMovedto(tile, x, y - speed)) {
        			y = y - speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		}
        	}
        	if (down) {
        		ChangeImg(player_down[frame]);
        		if (isMovedto(tile, x, y + speed)) {
        			y = y + speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		}
        	}
    	}
    }
    
    public void KeyReleased(Scene scene) {
        scene.setOnKeyReleased(event-> {
        	switch(event.getCode()) {
        	case W:
        		up = false;
        		break;
        	case D:
        		right = false;
        		break;
        	case S:
        		down = false;
        		break;
        	case A:
        		left = false;
        		break;
        	case SPACE:
        		if (bombs.size() < sizeBombStock) {
        			int bombx =(x) / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE;
        			int bomby =(y + 16) / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE;
        			Bomb bomb = new Bomb(bombx, bomby, Sprite.bomb.getFxImage(), true);
        			bombs.add(bomb);
        		}
        	default:
        		break;
        	}
        });
    }
    
    @Override
    public void update(Scene scene, GraphicsContext gc, int[][] tile) {
        KeyPressed(scene);
        KeyReleased(scene);
        show(scene, tile);
        if (!bombs.isEmpty()) {
        	bombs.get(0).update(scene, gc, tile);
        	if (bombs.get(0).getIsExplode()) {
        		bombs.remove(0);
        	}
        }
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
}
