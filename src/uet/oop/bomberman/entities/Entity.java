package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }
    
    public int getX() {
    	return this.x;
    }
    public void setX(int xUnit) {
    	this.x = xUnit * Sprite.SCALED_SIZE;
    }
    public void setY(int yUnit) {
    	this.y = yUnit * Sprite.SCALED_SIZE;
    }
    public int getY() {
    	return this.y;
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update(Scene scene, GraphicsContext gc, Tile[][] tile);
}
