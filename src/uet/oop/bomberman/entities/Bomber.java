package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.scene.*;
import uet.oop.bomberman.graphics.*;

public class Bomber extends Entity {
	Sprite[] player_down = {Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2};
	Sprite[] player_up = {Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2};
	Sprite[] player_right = {Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
	Sprite[] player_left = {Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2};
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    /*
     * Doi trang thai cho Bomber
     */
    public void ChangeImg(Sprite sprite) {
    	img = sprite.getFxImage();
    }
    
    public void move(Scene scene) {
    	scene.setOnKeyPressed(event-> {
        	switch(event.getCode()) {
        	case S:
        		y = y + 10;
        		for (Sprite sprite:player_down) {
        			ChangeImg(sprite);
        		}
        		break;
        	case A:
        		x = x - 10;
        		for (Sprite sprite:player_left) {
        			ChangeImg(sprite);
        		}
        		break;
        	case W:
        		y = y - 10;
        		for (Sprite sprite:player_up) {
        			ChangeImg(sprite);
        		}
        		break;
        	case D:
        		x = x + 10;
        		for (Sprite sprite:player_right) {
        			ChangeImg(sprite);
        		}
        		break;
        	default:
        		break;
        	}
        }
        );
        
        scene.setOnKeyReleased(event-> {
        	switch(event.getCode()) {
        	case W:
        		ChangeImg(Sprite.player_up);
        		break;
        	case D:
        		ChangeImg(Sprite.player_right);
        		break;
        	case S:
        		ChangeImg(Sprite.player_down);
        		break;
        	case A:
        		ChangeImg(Sprite.player_left);
        		break;
        	default:
        		break;
        	}
        });
    }
    @Override
    public void update(Scene scene) {
        move(scene);
    }
}
