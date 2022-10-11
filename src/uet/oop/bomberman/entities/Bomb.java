package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.constants.Const.Tile_Code;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity{
	private int xUnit;
	private int yUnit;
	private boolean isPlaced;
	private int normal_frame;
	private int exploding_frame;
	private Sprite[] bomb_frame = {Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2};
	private Sprite[] bomb_explode = {Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2};
	private Sprite[] last_left = {Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2};
	private Sprite[] last_right = {Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2};
	private Sprite[] last_top = {Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2};
	private Sprite[] last_down = {Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2};;
	private int explosion_range;
	private boolean isExploded;
	private int countToExplode;
	private int intervalToExplode;
	
    public Bomb(int x, int y, Image img, boolean isPlaced) {
    	super(x,y,img);
    	this.xUnit = x;
    	this.yUnit = y;
    	this.isPlaced = isPlaced;
    	this.isExploded = false;
    	this.countToExplode = 0;
    	this.intervalToExplode = 40;
    	this.normal_frame = -1;
    	this.exploding_frame = -1;
    	this.explosion_range = 1;
    }
    
    public void setImg(Image img) {
    	this.img = img;
    }
    public boolean getIsPlaced() {
    	return isPlaced;
    }
    
    public void setIsPlaced(boolean isPlaced) {
    	this.isPlaced = isPlaced;
    }
    
    public void setIsExplode(boolean isExploded) {
    	this.isExploded = isExploded;
    }
    
    public boolean getIsExplode() {
    	return this.isExploded;
    }

	@Override
	public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
		if (tile[yUnit][xUnit].getCode() == Tile_Code.FLAME) {
			countToExplode = intervalToExplode;
		}
		tile[yUnit][xUnit].setCode(Const.Tile_Code.BOMB);
		countToExplode++;
		if (countToExplode < intervalToExplode) {
			normal_frame++;
			if (normal_frame > 2) {
			normal_frame = 0;
		    }
		    setImg(bomb_frame[normal_frame].getFxImage());
		} else {
			tile[yUnit][xUnit].setCode(Const.Tile_Code.FLAME);
			exploding_frame++;
			if (exploding_frame >2) {
				exploding_frame = 0;
				isExploded = true;
			}
			setImg(bomb_explode[exploding_frame].getFxImage());
			for (int i = 1; i <= explosion_range; i++) {
				if (xUnit + i < Const.WIDTH && tile[yUnit][xUnit+i].getCode() != Const.Tile_Code.WALL && tile[yUnit][xUnit+i].getCode() != Tile_Code.BRICK) {
					gc.drawImage(last_right[exploding_frame].getFxImage(), (xUnit+i) * Sprite.SCALED_SIZE, (yUnit) * Sprite.SCALED_SIZE);
					tile[yUnit][xUnit+i].setCode(Const.Tile_Code.FLAME);
				} else break;
			}
			for (int i = 1; i <= explosion_range; i++) {
				if (xUnit - i >= 0 && tile[yUnit][xUnit-i].getCode() != Const.Tile_Code.WALL && tile[yUnit][xUnit-i].getCode() != Tile_Code.BRICK) {
					gc.drawImage(last_left[exploding_frame].getFxImage(), (xUnit-i) * Sprite.SCALED_SIZE, (yUnit) * Sprite.SCALED_SIZE);
					tile[yUnit][xUnit-i].setCode(Const.Tile_Code.FLAME);
				} else break;
			}
			for (int i = 1; i <= explosion_range; i++) {
				if (yUnit + i < Const.HEIGHT && tile[yUnit+i][xUnit].getCode() != Const.Tile_Code.WALL && tile[yUnit + i][xUnit].getCode() != Tile_Code.BRICK) {
					gc.drawImage(last_top[exploding_frame].getFxImage(), (xUnit) * Sprite.SCALED_SIZE, (yUnit + i) * Sprite.SCALED_SIZE);
					tile[yUnit+i][xUnit].setCode(Const.Tile_Code.FLAME);
				} else break;
			}
			for (int i = 1; i <= explosion_range; i++) {
				if (yUnit - i >= 0 && tile[yUnit-i][xUnit].getCode() != Const.Tile_Code.WALL && tile[yUnit - i][xUnit].getCode() != Tile_Code.BRICK) {
					gc.drawImage(last_down[exploding_frame].getFxImage(), (xUnit) * Sprite.SCALED_SIZE, (yUnit - i) * Sprite.SCALED_SIZE);
					tile[yUnit-i][xUnit].setCode(Const.Tile_Code.FLAME);
				} else break;
			}
			
		}
		if (isExploded) {
			tile[yUnit][xUnit].setCode(Const.Tile_Code.GRASS);
			for (int i = 1; i <= explosion_range; i++) {
				if (xUnit + i < Const.WIDTH && tile[yUnit][xUnit + i].getCode() != Const.Tile_Code.WALL) {
					if (tile[yUnit][xUnit + i].getCode() == Tile_Code.BRICK) {
						tile[yUnit][xUnit + i].setCode(Const.Tile_Code.GRASS);
						break;
					} else tile[yUnit][xUnit + i].setCode(Const.Tile_Code.GRASS);
					
				} else break;
			}
			for (int i = 1; i <= explosion_range; i++) {
				if (xUnit - i >= 0 && tile[yUnit][xUnit - i].getCode() != Const.Tile_Code.WALL) {
					if (tile[yUnit][xUnit - i].getCode() == Tile_Code.BRICK) {
						tile[yUnit][xUnit - i].setCode(Const.Tile_Code.GRASS);
						break;
					} else tile[yUnit][xUnit - i].setCode(Const.Tile_Code.GRASS);
				} else break;
			}
			for (int i = 1; i <= explosion_range; i++) {
				if (yUnit + i < Const.HEIGHT && tile[yUnit + i][xUnit].getCode() != Const.Tile_Code.WALL) {
					if (tile[yUnit + i][xUnit].getCode() == Tile_Code.BRICK) {
						tile[yUnit + i][xUnit].setCode(Const.Tile_Code.GRASS);
						break;
					} else tile[yUnit + i][xUnit].setCode(Const.Tile_Code.GRASS);
				} else break;
			}
			for (int i = 1; i <= explosion_range; i++) {
				if (yUnit - i >= 0 && tile[yUnit - i][xUnit].getCode() != Const.Tile_Code.WALL) {
					if (tile[yUnit - i][xUnit].getCode() == Tile_Code.BRICK) {
						tile[yUnit - i][xUnit].setCode(Const.Tile_Code.GRASS);
						break;
					} else tile[yUnit - i][xUnit].setCode(Const.Tile_Code.GRASS);
				} else break;
			}	
		}
		
		
		
		
	}

	public int getCountToExplode() {
		return countToExplode;
	}

	public void setCountToExplode(int countToExplode) {
		this.countToExplode = countToExplode;
	}

	public int getIntervalToExplode() {
		return intervalToExplode;
	}

	public void setIntervalToExplode(int intervalToExplode) {
		this.intervalToExplode = intervalToExplode;
	}
	
	public int getXUnit() {
		return xUnit;
	}
	
	public int getYUnit() {
		return yUnit;
	}

	public int getExplosion_range() {
		return explosion_range;
	}

	public void setExplosion_range(int explosion_range) {
		this.explosion_range = explosion_range;
	}

}
