package uet.oop.bomberman.entities;

import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.constants.Const.Tile_Code;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity{

	private int desX;
	private int desY;
	private int xUnit;
	private int yUnit;
	private Sprite[] oneal_left = {Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3};
	private Sprite[] oneal_right = {Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3};
	private int orient;
	private boolean isAlive;
	private int frame;
	private int speed;
	private boolean[] choice;
	private Random random = new Random();
	
	public Oneal(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
		this.setxUnit(xUnit);
		this.setyUnit(yUnit);
		this.setSpeed(2);
		setOrient(random.nextInt(4));
		this.frame = 0;
		this.isAlive = true;
	}
    
	@Override
	public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
		if (this.x % Sprite.SCALED_SIZE == 0) xUnit= (this.x) / Sprite.SCALED_SIZE;
		if (this.y % Sprite.SCALED_SIZE == 0) yUnit = (this.y) / Sprite.SCALED_SIZE;
		boolean[][] found = new boolean[Const.HEIGHT][Const.WIDTH];
    	for (int i = 0; i < Const.HEIGHT; i++) {
    		for (int j = 0; j < Const.WIDTH; j++) {
    			found[i][j] = false;
    		}
    	}
    	if (tile[yUnit][xUnit].getCode() == Tile_Code.FLAME) {
    		setIsAlive(false);
    	}
		boolean isconnected = Tile.isConnected(tile, yUnit, xUnit, desY, desX, found);
		if (!isconnected) {
			moveNoBrain(tile);
		} else {
				if (yUnit != desY || xUnit != desX) {
					List<Pair<Integer, Integer>> path = Tile.findPath(tile, yUnit, xUnit, desY, desX);
					moveToCell(path.get(0).getValue(), path.get(0).getKey());
				}
			} 
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
								if (!choice[1] && !choice[2]) {
									canMove = false;
								} else {
									if (choice[1] && choice[2]) {
										orient = random.nextInt(2) + 1;
										canMove = true;
									} else {
										if (choice[1]) {
											orient = 1;
											canMove = true;
										} else {
											orient = 2;
											canMove = true;
										}
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
								if (!choice[0] && !choice[3]) {
									canMove = false;
								} else {
									if (choice[0] && choice[3]) {
										if (random.nextInt(2) %2 == 0) {
											orient = 0;
										} else {
											orient = 3;
										}
										canMove = true;
									} else {
										if (choice[0]) {
											orient = 0;
											canMove = true;
										} else {
											orient = 3;
											canMove = true;
										}
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
	
	public void move(Tile[][] tile) {
		if (isAlive) {
			frame++;
    		if (frame > 2) frame = 0;
			switch(orient) {
			case 0: 
        		setImg(oneal_right[frame]);
        			x = x + speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		break;
			case 3:
				setImg(oneal_left[frame]);
        			x = x - speed;
        			xUnit = x / Sprite.SCALED_SIZE;
        			yUnit = y / Sprite.SCALED_SIZE;
        		break;
			case 1:
				setImg(oneal_right[frame]);
				y = y - speed;
    			xUnit = x / Sprite.SCALED_SIZE;
    			yUnit = y / Sprite.SCALED_SIZE;
    			break;
			case 2:
				setImg(oneal_left[frame]);
				y = y + speed;
				xUnit = x / Sprite.SCALED_SIZE;
    			yUnit = y / Sprite.SCALED_SIZE;
    			break;
    		default:
    			break;
			}
		}
		
	}
	public void moveToCell(int x, int y) {
		if (x * Sprite.SCALED_SIZE < this.x && this.y % Sprite.SCALED_SIZE == 0) {
			this.x -= 4;
			return;
		}
		if (x * Sprite.SCALED_SIZE > this.x && this.y % Sprite.SCALED_SIZE == 0) {
			this.x +=4;
			return;
		}
		
		if (y * Sprite.SCALED_SIZE> this.y) {
			this.y +=4;
			return;
		}
		if (y * Sprite.SCALED_SIZE< this.y) {
			this.y -=4;
			return;
		}
	}
	public int getDesX() {
		return desX;
	}

	public void setDesX(int desX) {
		this.desX = desX;
	}

	public int getDesY() {
		return desY;
	}

	public void setDesY(int desY) {
		this.desY = desY;
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
	
	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}

	public int getSpeed() {
		return speed;
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
	public void setImg(Sprite sprite) {
		this.img = sprite.getFxImage();
	}
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public boolean getIsAlive() {
		return this.isAlive;
	}
}
