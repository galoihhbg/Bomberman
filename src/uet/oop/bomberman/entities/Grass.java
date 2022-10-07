package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.*;
import javafx.scene.canvas.GraphicsContext;

public class Grass extends Entity {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {

    }
}
