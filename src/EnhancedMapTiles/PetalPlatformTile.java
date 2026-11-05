package EnhancedMapTiles;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.TileType;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class PetalPlatformTile extends EnhancedMapTile {

    private static final int SPRITE_SIZE = 32;
    private static int tileSize = 32;

    public static void configure(int mapTileSize) {
        tileSize = mapTileSize;
    }

    public PetalPlatformTile(BufferedImage image, float x, float y, TileType tileType) {
        super(x, y, new SpriteSheet(image, SPRITE_SIZE, SPRITE_SIZE), tileType);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        float petalscale = tileSize / (float) SPRITE_SIZE;
        float boundsX = Math.round(2 * petalscale) / petalscale;
        float boundsY = Math.round(11 * petalscale) / petalscale;

        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(petalscale)
                            .withBounds(new Rectangle(boundsX, boundsY, 28, 8))
                            .build()
            });
        }};
    }

    public void remove() {
        setMapEntityStatus(MapEntityStatus.REMOVED);
    }
}
