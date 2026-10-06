package Maps;

import EnhancedMapTiles.EndLevelBox;
import Level.*;
import Tilesets.GrasslandTileset;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TutorialMap extends Map {

    public TutorialMap() {
        super("tutorial_map.txt", new GrasslandTileset());
        this.playerStartPosition = getMapTile(1, 9).getLocation();
    }
    @Override
    public ArrayList<Enemy> loadEnemies() {
         ArrayList<Enemy> enemies = new ArrayList<>();

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

                enhancedMapTiles.add(new EndLevelBox(getMapTile(49, 10).getLocation()));
            return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        return npcs;
    }
}