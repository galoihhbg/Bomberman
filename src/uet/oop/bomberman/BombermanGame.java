package uet.oop.bomberman;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.constants.Const;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {



    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    //private List<Entity> bomblist = new ArrayList<>();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
    
    public int[][] tile = new int[HEIGHT][WIDTH];


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

        Text fps = new Text();
        fps.setX(1);
        fps.setY(1);
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
                if (tile[1][1] == 1) {
                	System.out.println("Check");
                }

                //Show fps
                long[] frameTimes = new long[100];
                int frameTimeIndex = 0;
                boolean check = false;

                long oldFrameTimes = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = l;
                frameTimeIndex = (frameTimeIndex + 1) / 100;
                if (frameTimeIndex == 0) {
                    check = true;
                }
                if (check) {
                    check = false;
                    long nanosPassed = l - oldFrameTimes;
                    long nanosPerFrame = nanosPassed / 100;
                    double frameRate = 1000000000.0 / nanosPerFrame;
                    fps.setText(String.valueOf(frameRate));
                    // Cần thêm text fps vào scene.
                }
           }
                
        };
        timer.start();

        root.getChildren().add(fps);


        
        createMap();
        //Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
        entities.add(bomberman);
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
                    tile[j][i] = Const.WALL;
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                    tile[j][i] = Const.GRASS;
                }
                stillObjects.add(object);
            }
        }
    }

    public void update(Scene scene, GraphicsContext gc, int[][] tile) {
    	for (Entity e:entities) {
    		e.update(scene,gc, tile);
    	}
//    	for (Entity e:bomblist) {
//    		e.update(scene, gc, tile);
//    	}
    }

    
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        //bomblist.forEach(g -> g.render(gc));
        bomberman.bombs.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
