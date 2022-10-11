package uet.oop.bomberman;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.oop.bomberman.constants.Const.Tile_Code;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Oneal;
import uet.oop.bomberman.entities.Tile;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Balloom;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> enemies = new ArrayList<>();
    private List<Oneal> oneals = new ArrayList<>();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
    
    public Tile[][] tile = new Tile[HEIGHT][WIDTH];


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    
    
    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                	render();
                    update(scene, gc, tile);
                    //System.out.println(bomblist.size());
                    //placeBomb(bomberman);
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
           }
                
        };
        timer.start();
        
        createMap();
        //Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
        Entity enemy = new Balloom(1, 5, Sprite.balloom_right1.getFxImage());
        Oneal oneal = new Oneal(5,6, Sprite.oneal_right1.getFxImage());
        enemies.add(enemy);
        entities.add(bomberman);
        oneals.add(oneal);
    }
  
//    public void placeBomb(Bomber bomber) {
//    	bomblist = bomber.getBombs();
//    }
    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || (j % 2 == 0 && i % 2 == 0)) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                    tile[j][i] = new Tile(Tile_Code.WALL, object);
                }
                else {
//                	if (i == 3) {
//                		object = new Brick(i, j, Sprite.brick.getFxImage());
//                		tile[j][i] = new Tile(Tile_Code.BRICK, object);
//                	} else {
                		object = new Grass(i, j, Sprite.grass.getFxImage());
                        tile[j][i] = new Tile(Tile_Code.GRASS, object);
                	//}
                    
                }
                //stillObjects.add(object);
            }
        }
        tile[2][1] = new Tile(Tile_Code.BRICK, new Brick(1,2,Sprite.brick.getFxImage()));
        tile[1][2] = new Tile(Tile_Code.BRICK, new Brick(2,1,Sprite.brick.getFxImage()));
        List<Pair<Integer, Integer>> path = Tile.findPath(tile, 6, 5, 1, 8);
        path.forEach(g -> {
        	System.out.println(g.getKey() + " " + g.getValue());
        });
    }

    public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
    	
    	for (int i = 0; i < HEIGHT; i++) {
        	for (int j = 0; j < WIDTH; j++) {
        		tile[i][j].getType().update(scene, gc, tile);
//        		if (tile[i][j].getCode() == Tile_Code.GRASS) {
//        			tile[i][j].setType(new Grass(j,i,Sprite.grass.getFxImage()));
//        		}
        	}
        }
    	for (Entity e:entities) {
    		e.update(scene,gc, tile);
    	}
    	for (Entity e:enemies) {
    		e.update(scene, gc, tile);
    	}
    	for (Oneal o:oneals) {
    		o.setDesX(bomberman.getxUnit());
    		o.setDesY(bomberman.getyUnit());
    		o.update(scene, gc, tile);
    	}
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //stillObjects.forEach(g -> g.render(gc));
        for (int i = 0; i < HEIGHT; i++) {
        	for (int j = 0; j < WIDTH; j++) {
        		tile[i][j].getType().render(gc);
        	}
        }
        //bomblist.forEach(g -> g.render(gc));
        bomberman.bombs.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        oneals.forEach(g -> g.render(gc));
    }
}
