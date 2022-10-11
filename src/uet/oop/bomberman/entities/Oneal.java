package uet.oop.bomberman.entities;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity{

	private int desX;
	private int desY;
	private int xUnit;
	private int yUnit;
	
	public Oneal(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
		this.setxUnit(xUnit);
		this.setyUnit(yUnit);
	}
    
	@Override
	public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
		if (this.x % Sprite.SCALED_SIZE == 0) xUnit= (this.x) / Sprite.SCALED_SIZE;
		if (this.y % Sprite.SCALED_SIZE == 0) yUnit = (this.y) / Sprite.SCALED_SIZE;
		//System.out.println(xUnit + " "+ yUnit);
		System.out.println(x +" "+ y);
		List<Pair<Integer, Integer>> path = Tile.findPath(tile, yUnit, xUnit, desY, desX);
		if (path.size() >= 1) {
			moveToCell(path.get(0).getValue(), path.get(0).getKey());
		}
	}

	public void moveToCell(int x, int y) {
		if (x * Sprite.SCALED_SIZE < this.x) {
			this.x -= 2;
			return;
		}
		if (x * Sprite.SCALED_SIZE > this.x) {
			this.x +=2;
			return;
		}
		
		if (y * Sprite.SCALED_SIZE> this.y) {
			this.y +=2;
			return;
		}
		if (y * Sprite.SCALED_SIZE< this.y) {
			this.y -=2;
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
	
}
