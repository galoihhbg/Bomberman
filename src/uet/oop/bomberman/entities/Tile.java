package uet.oop.bomberman.entities;

import uet.oop.bomberman.constants.Const.Tile_Code;

public class Tile {
    private Tile_Code code;
    private Entity type;
    
    public Tile(Tile_Code code, Entity type) {
    	this.code = code;
    	this.type = type;
    }
    
    public Tile_Code getCode() {
    	return this.code;
    }
    
    public void setCode(Tile_Code code) {
    	this.code = code;
    }
    
    public Entity getType() {
    	return type;
    }
    
    public void setType(Entity type) {
    	this.type = type;
    }
}
