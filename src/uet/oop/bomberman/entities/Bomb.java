package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.scene.*;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.graphics.*;

public class Bomb extends Entity{
	private int xUnit;
	private int yUnit;
	private boolean isPlaced;
	private int normal_frame;
	private int exploding_frame;
	private Sprite[] bomb_frame = {Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2};
	private Sprite[] bomb_explode = {Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2};
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
	public void update(Scene scene, GraphicsContext gc, int[][] tile) {
		System.out.println(xUnit + " " + yUnit);
		tile[yUnit][xUnit] = Const.BOMB;
		countToExplode++;
		if (countToExplode < intervalToExplode) {
			normal_frame++;
			if (normal_frame > 2) {
			normal_frame = 0;
		    }
		    setImg(bomb_frame[normal_frame].getFxImage());
		} else {
			exploding_frame++;
			if (exploding_frame >2) {
				exploding_frame = 0;
				isExploded = true;
				tile[yUnit][xUnit] = Const.GRASS;
			}
			setImg(bomb_explode[exploding_frame].getFxImage());
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

}
