package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.scene.*;
import uet.oop.bomberman.graphics.*;

public class Bomb extends Entity{
	
	private boolean isPlaced;
	private int frame;
	private Sprite[] bomb_frame = {Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2};
	
    public Bomb(int x, int y, Image img, boolean isPlaced) {
    	super(x,y,img);
    	this.isPlaced = isPlaced;
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

	@Override
	public void update(Scene scene, GraphicsContext gc, int[][] tile) {
		frame++;
		if (frame > 2) {
			frame = 0;
		}
		setImg(bomb_frame[frame].getFxImage());
	}
}
