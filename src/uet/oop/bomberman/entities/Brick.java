package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.constants.Const.Tile_Code;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Grass;

public class Brick extends Entity{

	private int xUnit;
	private int yUnit;
	private int frame;
	private boolean isExploded;
	private Sprite[] brick_exploded = {Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2};
	public Brick(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
		this.setxUnit(xUnit);
		this.setyUnit(yUnit);
		this.setFrame(-1);
		this.setExploded(false);
	}
    private void setImg(Sprite sprite) {
    	this.img = sprite.getFxImage();
    }
	@Override
	public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
	     if (tile[yUnit][xUnit].getCode() == Tile_Code.FLAME) {
	    	 frame++;
	    	 if (frame < 2) {
	    		 setImg(brick_exploded[frame]);
	    	 } else {
	    		 isExploded = true;
	    	 }
	    	 
	     }
	     
	     if (isExploded) {
	    	 tile[yUnit][xUnit].setCode(Tile_Code.GRASS);
	    	 tile[yUnit][xUnit].setType(new Grass(xUnit, yUnit, Sprite.grass.getFxImage()));
	     }
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}
	public boolean isExploded() {
		return isExploded;
	}
	public void setExploded(boolean isExploded) {
		this.isExploded = isExploded;
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
    public void isBroken() {
    	frame++;
    	if (frame > 2) {
    		isExploded = true;
    	}
    	setImg(brick_exploded[frame]);
    }
}
