package uet.oop.bomberman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Balloom> enemies = new ArrayList<>();
    private List<Oneal> oneals = new ArrayList<>();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
    
    public Tile[][] tile = new Tile[HEIGHT][WIDTH];

    // fps counting purpose
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean check = false;

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

        // Text fps
        Text fps = new Text();
        fps.setX(1);
        fps.setY(1);



        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //Show fps
                long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = l ;
                frameTimeIndex = (frameTimeIndex + 1) % 100 ;
                if (frameTimeIndex == 0) {
                    check = true ;
                }
                if (check) {
                    long nanosPassed = l - oldFrameTime ;
                    long nanosPerFrame = nanosPassed / 100 ;
                    double frameRate = 1_000_000_000.0 / nanosPerFrame;
                    fps.setText(String.valueOf(frameRate));
                    // fps là dạng text show fps
                    // chưa được render vào scene hay stage.
                }

                try {
                	render();
                    update(scene, gc, tile);
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


           }
                
        };
        timer.start();
        
        createMap();

        playSound();
    }
  
    public void createMap() {
        FileReader fr = null;
        String line;
        char[][] map = new char[HEIGHT][WIDTH];
        int row;
        int column;
        try {
            fr = new FileReader("res\\levels\\Level1.txt"); // Cần đổi đường dẫn khi chuyển level
            BufferedReader br = new BufferedReader(fr);
            line = br.readLine();
            String parts[] = line.split(" ");
            row = Integer.parseInt(parts[1]);
            column = Integer.parseInt(parts[2]);
            for (int i = 0; i < row; i++) {
                line = br.readLine();
                for (int j = 0; j < column; j++) {
                    map[i][j] = line.charAt(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || (j % 2 == 0 && i % 2 == 0)) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                    tile[j][i] = new Tile(Tile_Code.WALL, object);
                }
                else {
                		object = new Grass(i, j, Sprite.grass.getFxImage());
                        tile[j][i] = new Tile(Tile_Code.GRASS, object); 
                }
            }
        }
        tile[2][1] = new Tile(Tile_Code.BRICK, new Brick(1,2,Sprite.brick.getFxImage()));
        tile[1][2] = new Tile(Tile_Code.BRICK, new Brick(2,1,Sprite.brick.getFxImage()));
        Balloom enemy = new Balloom(1, 5, Sprite.balloom_right1.getFxImage());
        Oneal oneal = new Oneal(5,6, Sprite.oneal_right1.getFxImage());
        enemies.add(enemy);
        enemies.add(new Balloom(6,9, Sprite.balloom_left1.getFxImage()));
        oneals.add(new Oneal(8, 9, Sprite.oneal_left1.getFxImage()));
        entities.add(bomberman);
        oneals.add(oneal);
    }

    public void update(Scene scene, GraphicsContext gc, Tile[][] tile) {
    	
    	for (int i = 0; i < HEIGHT; i++) {
        	for (int j = 0; j < WIDTH; j++) {
        		tile[i][j].getType().update(scene, gc, tile);
        	}
        }
    	for (Entity e:entities) {
    		e.update(scene,gc, tile);
    	}
    	for (Balloom e:enemies) {
    		e.update(scene, gc, tile);
    	}
    	enemies.removeIf(e -> e.getIsAlive() == false);
    	for (Oneal o:oneals) {
    		o.setDesX(bomberman.getxUnit());
    		o.setDesY(bomberman.getyUnit());
    		o.update(scene, gc, tile);
    	}
    	oneals.removeIf(o -> o.getIsAlive() == false);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < HEIGHT; i++) {
        	for (int j = 0; j < WIDTH; j++) {
        		tile[i][j].getType().render(gc);
        	}
        }
        bomberman.bombs.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        oneals.forEach(g -> g.render(gc));
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/Music/Theme.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
