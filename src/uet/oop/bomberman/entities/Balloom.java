package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.constants.Const.Tile_Code;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Entity{
	
	protected int xUnit;
	protected int yUnit;
	private Sprite[] balloom_left = {Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3};
	private Sprite[] balloom_right = {Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3};
	private int orient;
	private boolean isAlive;
	private int frame;
	private int speed;
	private boolean[] choice;

	public Balloom(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
		setxUnit(xUnit);
		setyUnit(yUnit);
		setOrient(0);
		this.frame = 0;
		this.isAlive = true;
		this.speed = 3;
	}
	
	 public boolean isMovedto(Tile[][] tile, int nextX, int nextY) {
	    	int size = Sprite.SCALED_SIZE;

	        int nextX_1 = (nextX) / size;
	        int nextY_1 = (nextY + 1) / size;

	        int nextX_2 = (nextX + size - 2) / size;
	        int nextY_2 = (nextY + 1) / size;

	        int nextX_3 = nextX / size;
	        int nextY_3 = (nextY + size - 1) / size;

	        int nextX_4 = (nextX + size - 2) / size;
	        int nextY_4 = (nextY + size - 1) / size;
	        
	        return !((tile[nextY_1][nextX_1].getCode() == Const.Tile_Code.WALL || tile[nextY_1][nextX_1].getCode() == Const.Tile_Code.BOMB) ||
	                (tile[nextY_2][nextX_2].getCode() == Const.Tile_Code.WALL || tile[nextY_2][nextX_2].getCode() == Const.Tile_Code.BOMB) ||
	                (tile[nextY_3][nextX_3].getCode() == Const.Tile_Code.WALL|| tile[nextY_3][nextX_3].getCode() == Const.Tile_Code.BOMB) ||
	                (tile[nextY_4][nextX_4].getCode() == Const.Tile_Code.WALL || tile[nextY_4][nextX_4].getCode() == Const.Tile_Code.BOMB) ||
	                tile[nextY_1][nextX_1].getCode() == Tile_Code.BRICK || tile[nextY_2][nextX_2].getCode() == Tile_Code.BRICK ||
	                tile[nextY_3][nextX_3].getCode() == Tile_Code.BRICK || tile[nextY_4][nextX_4].getCode() == Tile_Code.BRICK);
	}
	 
	public void move(Tile[][] tile) {
		if (isAlive) {
			frame++;
    		if (frame > 2) frame = 0;
			switch(orient) {
			case 0: 
        		setImg(balloom_right[frame]);
        			x = x + speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		break;
			case 3:
				setImg(balloom_left[frame]);
        			x = x - speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		break;
			case 1:
				setImg(balloom_right[frame]);
				y = y - speed;
    			xUnit = x / Sprite.SCALED_SIZE;
    			yUnit = y / Sprite.SCALED_SIZE;
    			break;
			case 2:
				setImg(balloom_left[frame]);
				y = y + speed;
				xUnit = x / Sprite.SCALED_SIZE;
    			yUnit = y / Sprite.SCALED_SIZE;
    			break;
    		default:
    			break;
			}
		}
		
	}
	
	public void moveNoBrain(Tile[][] tile) {
		boolean canMove = true;
		if (isAlive) {
			frame++;
			if (frame > 0) frame = 0;
			setChoice(tile);
			if (!choice[orient]) {
				if (orient == 0 || orient == 3) {
					if (choice[1] && !choice[2]) {
						orient = 1;
						canMove = true;
					} else {
						if (choice[2] && !choice[1]) {
							orient = 2;
							canMove = true;
						} else {
							if (choice[3 - orient]) {
								orient = 3 - orient;
								canMove = true;
							} else {
								if (choice[1]) {
									orient = 1;
									canMove = true;
								} else {
									if (choice[2]) {
										orient = 2;
										canMove = true;
									} else {
										canMove= false;
									}
								}
							}
						}
					}
				} else {
					if (choice[0] && !choice[3]) {
						orient = 0;
						canMove = true;
					} else {
						if (choice[3] && !choice[0]) {
							orient = 3;
							canMove = true;
						} else {
							if (choice[3 - orient]) {
								orient = 3 - orient;
								canMove = true;
							} else {
								if (choice[0]) {
									orient = 0;
									canMove = true;
								} else {
									if (choice[3]) {
										orient = 3;
										canMove = true;
									} else {
										canMove = false;
									}
								}
							}
						}
					}
				}
			} 
			if (canMove) move(tile);
		}
	}
	@Override
	public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
		moveNoBrain(tile);
		if (tile[yUnit][xUnit].getCode() == Tile_Code.FLAME) {
			setIsAlive(false);
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
	
	public void setImg(Sprite sprite) {
		this.img = sprite.getFxImage();
	}

	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean[] getChoice() {
		return choice;
	}

	public void setChoice(Tile[][] tile) {
		boolean[] choice = {isMovedto(tile, x + speed, y), isMovedto(tile, x, y - speed), isMovedto(tile, x, y + speed), isMovedto(tile, x - speed, y)};
		this.choice = choice;
	}
	
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public boolean getIsAlive() {
		return this.isAlive;
	}
}
