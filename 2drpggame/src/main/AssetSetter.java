package main;

import object.OBJ_Boot;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
		gp.obj[0] = new OBJ_Key();
		gp.obj[0].worldX = 25*gp.tileSize;
		gp.obj[0].worldY = 25*gp.tileSize;
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].worldX = 24*gp.tileSize;
		gp.obj[1].worldY = 43*gp.tileSize;
		
		gp.obj[2] = new OBJ_Door();
		gp.obj[2].worldX = 10*gp.tileSize;
		gp.obj[2].worldY = 16*gp.tileSize;
		
		gp.obj[3] = new OBJ_Door();
		gp.obj[3].worldX = 10*gp.tileSize;
		gp.obj[3].worldY = 18*gp.tileSize;
		
		gp.obj[4] = new OBJ_Chest();
		gp.obj[4].worldX = 10*gp.tileSize;
		gp.obj[4].worldY = 13*gp.tileSize;
		
		gp.obj[5] = new OBJ_Boot();
		gp.obj[5].worldX = 10*gp.tileSize;
		gp.obj[5].worldY = 19*gp.tileSize;
	}

}
