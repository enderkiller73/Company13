package Maps;


import Enemies.*;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.GrassLevelsTileset;
import Utils.Direction;
import java.util.ArrayList;


// Represents a test map to be used in a level
public class Level_Five extends Map {


    public Level_Five() {
        super("level_five.txt", new GrassLevelsTileset());
        this.playerStartPosition = getMapTile(1, 12).getLocation();
    }


    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        EvilFlower evFl = new EvilFlower(getMapTile(35, 5).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(evFl);

        BuddingFlower bf = new BuddingFlower(getMapTile(64, 2).getLocation().addY(2), getMapTile(78, 2).getLocation().addY(2), Direction.RIGHT);
        enemies.add(bf);

        SporeFlower spFl = new SporeFlower(getMapTile(84, 10).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(spFl);

        SporeFlower spFl2 = new SporeFlower(getMapTile(16, 12).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(spFl2);

        SporeFlower spFl3 = new SporeFlower(getMapTile(105, 10).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(spFl3);

        EvilFlower evFl2 = new EvilFlower(getMapTile(92, 8).getLocation().subtractY(25), Direction.LEFT);
        enemies.add(evFl2);

        return enemies;
    }


    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();


        // HorizontalMovingPlatform hmp = new HorizontalMovingPlatform(
        //         ImageLoader.load("GreenPlatform.png"),
        //         getMapTile(24, 6).getLocation(),
        //         getMapTile(27, 6).getLocation(),
        //         TileType.JUMP_THROUGH_PLATFORM,
        //         3,
        //         new Rectangle(0, 6,16,4),
        //         Direction.RIGHT
        // );
        // enhancedMapTiles.add(hmp);


        // EndLevelBox endLevelBox = new EndLevelBox(getMapTile(32, 7).getLocation());
        // enhancedMapTiles.add(endLevelBox);


        return enhancedMapTiles;
    }


    @Override
    //
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();


        // Walrus walrus = new Walrus(getMapTile(30, 10).getLocation().subtractY(13));
        // npcs.add(walrus);


        return npcs;
    }


// public static Map getMapByName(String mapName) {
//     switch(mapName) {
//         case "Level_Five":
//             return new TestMap();
//         case "TitleScreen":
//             return new TitleScreenMap();
//         case "MyMap":
//             return new MyMap();
//         default:
//             throw new RuntimeException("Unrecognized map name");
//     }
// }

}