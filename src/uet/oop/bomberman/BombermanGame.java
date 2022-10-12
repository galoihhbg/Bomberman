package uet.oop.bomberman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    public static final int HEIGHT = 13;
    public static final int MAX_LEVEL = 2;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Balloom> enemies = new ArrayList<>();
    private List<Oneal> oneals = new ArrayList<>();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
    
    private boolean is_playing = bomberman.isAlive();
    public Tile[][] tile = new Tile[HEIGHT][WIDTH];
    
    private int level = 1;

    // fps counting purpose
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean check = false;
    private boolean is_quit = false;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    
    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));
        gc = canvas.getGraphicsContext2D();
     // Text fps
        Text fps = new Text("00.0");
        fps.setX(0);
        fps.setY((HEIGHT + 1) * Sprite.SCALED_SIZE);
        
        
        Text gLevel = new Text(String.format("Level: %d", level));
        gLevel.setX(4 * Sprite.SCALED_SIZE);
        gLevel.setY((HEIGHT + 1) * Sprite.SCALED_SIZE);

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(fps);
        root.getChildren().add(gLevel);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();

        



        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //Show fps
                try {
                	render();
                	if (is_quit) {
                		stage.close();
                		
                		Platform.exit();
                	}
                	if (is_playing ) {
                		gLevel.setText(String.format("Level %d", level));
                		update(scene, gc, tile);
                		if (!bomberman.isAlive()) {
                			game_over(scene, root);
                		}
                		if (enemies.size() + oneals.size() == 0) {
                			if (level == MAX_LEVEL) game_win(scene, root); else {
                				level++;
                				entities.remove(bomberman);
                				bomberman.setX(1);
                				bomberman.setY(1);
                				createMap();
                			}
                		}
                	}
                    
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
                        fps.setText(String.format("%.2f", frameRate));
                    }
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
    public void game_over(Scene scene, Group root) {
    	is_playing = false;
    	Label game_over = new Label("You Lose");
    	Button new_game = new Button("New Game");
    	Button exit = new Button("Eixt");
    	game_over.setLayoutX(15 * 32);
    	game_over.setLayoutY(5*32);
        new_game.setLayoutX(13 * 32);
        new_game.setLayoutY(8*32);
        exit.setLayoutX(17*32);
        exit.setLayoutY(8*32);
        root.getChildren().add(game_over);
        root.getChildren().add(exit);
        root.getChildren().add(new_game);
        EventHandler<ActionEvent> re_game = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 	
            	entities.remove(0);
            	oneals.removeIf(g -> g !=null);
            	enemies.removeIf(g -> g != null);
            	is_playing = true;
            	bomberman = new Bomber(1,1,Sprite.player_down.getFxImage());
                createMap();
                root.getChildren().remove(game_over);
                root.getChildren().remove(new_game);
                root.getChildren().remove(exit);
            } 
        };
        EventHandler<ActionEvent> exit_game = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 	
            	is_quit = true;
            } 
        };
        new_game.setOnAction(re_game);
        exit.setOnAction(exit_game);
        scene.setRoot(root);
    }
    public void game_win(Scene scene, Group root) {
    	is_playing = false;
    	Label game_over = new Label("You Win");
    	Button new_game = new Button("New Game");
    	Button exit = new Button("Eixt");
    	game_over.setLayoutX(15 * 32);
    	game_over.setLayoutY(5*32);
        new_game.setLayoutX(13 * 32);
        new_game.setLayoutY(8*32);
        exit.setLayoutX(17*32);
        exit.setLayoutY(8*32);
        root.getChildren().add(game_over);
        root.getChildren().add(exit);
        root.getChildren().add(new_game);
        EventHandler<ActionEvent> re_game = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 	
            	level = 1;
            	entities.remove(0);
            	oneals.removeIf(g -> g !=null);
            	enemies.removeIf(g -> g != null);
            	is_playing = true;
            	bomberman = new Bomber(1,1,Sprite.player_down.getFxImage());
                createMap();
                root.getChildren().remove(game_over);
                root.getChildren().remove(new_game);
                root.getChildren().remove(exit);
            } 
        };
        EventHandler<ActionEvent> exit_game = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 	
            	is_quit = true;
            } 
        };
        new_game.setOnAction(re_game);
        exit.setOnAction(exit_game);
        scene.setRoot(root);
    }
    public void createMap() {
    	String path = String.format("res\\levels\\Level%d.txt", level);
    	FileReader fr = null;
        String line;
        char[][] map = new char[HEIGHT][WIDTH];
        int row;
        int column;
        try {
            fr = new FileReader(path);
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
            
            for (int i = 0; i < WIDTH; i++) {
            	for (int j = 0; j < HEIGHT; j++) {
            		switch (map[j][i]) {
            		case '#':
            			tile[j][i] = new Tile(Tile_Code.WALL, new Wall(i,j,Sprite.wall.getFxImage()));
            			break;
            		case '*':
            			tile[j][i] = new Tile(Tile_Code.BRICK, new Brick(i,j,Sprite.brick.getFxImage()));
            			break;
            		case 'x':
            			tile[j][i] = new Tile(Tile_Code.BRICK, new Brick(i,j,Sprite.brick.getFxImage()));
            			break;
            		case '1':
            			tile[j][i] = new Tile(Tile_Code.GRASS, new Grass(i,j,Sprite.grass.getFxImage()));
            			enemies.add(new Balloom(i,j, Sprite.balloom_left1.getFxImage()));
            			break;
            		case '2':
            			tile[j][i] = new Tile(Tile_Code.GRASS, new Grass(i,j,Sprite.grass.getFxImage()));
            			oneals.add(new Oneal(i,j, Sprite.oneal_left1.getFxImage()));
            			break;
            		default: 
            			tile[j][i] = new Tile(Tile_Code.GRASS, new Wall(i,j,Sprite.grass.getFxImage()));
            			break;
            		}
            	}
            }
            entities.add(bomberman);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    		if(bomberman.getX() < e.getX() + Sprite.SCALED_SIZE &&
    			    bomberman.getX() + Sprite.SCALED_SIZE - 10> e.getX() &&
    			    bomberman.getY() < e.getY() + Sprite.SCALED_SIZE &&
    			    bomberman.getY() + Sprite.SCALED_SIZE > e.getY()) {
    			bomberman.setAlive(false);
    		}
    	}
    	enemies.removeIf(e -> e.getIsAlive() == false);
    	for (Oneal o:oneals) {
    		o.setDesX(bomberman.getxUnit());
    		o.setDesY(bomberman.getyUnit());
    		o.update(scene, gc, tile);
    		if(bomberman.getX() < o.getX() + Sprite.SCALED_SIZE &&
    			    bomberman.getX() + Sprite.SCALED_SIZE - 10> o.getX() &&
    			    bomberman.getY() < o.getY() + Sprite.SCALED_SIZE &&
    			    bomberman.getY() + Sprite.SCALED_SIZE > o.getY()) {
    			bomberman.setAlive(false);
    		}
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

	public boolean isIs_playing() {
		return is_playing;
	}

	public void setIs_playing(boolean is_playing) {
		this.is_playing = is_playing;
	}
}