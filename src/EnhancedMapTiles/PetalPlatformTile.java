package EnhancedMapTiles;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.TileType;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class PetalPlatformTile extends EnhancedMapTile {

  private static final int SPRITE_SIZE = 32;
  private static int tileSize = 32;
  private static BufferedImage currentImage;

  public static void configure(int mapTileSize) {
    tileSize = mapTileSize;
  }

  private static BufferedImage clearRightColumn(BufferedImage image) {
    BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = copy.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    for (int y = 0; y < copy.getHeight(); y++) {
      copy.setRGB(copy.getWidth() - 1, y, 0);
    }
    return copy;
  }

  private static SpriteSheet prepare(BufferedImage image, boolean isRightEnd) {
    currentImage = isRightEnd ? clearRightColumn(image) : image;
    return new SpriteSheet(currentImage, SPRITE_SIZE, SPRITE_SIZE);
  }

  public PetalPlatformTile(BufferedImage image, float x, float y, TileType tileType, boolean isRightEnd) {
    super(x, y, prepare(image, isRightEnd), tileType);
  }

  @Override
  public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    float petalscale = tileSize / (float) SPRITE_SIZE;
    float boundsX = Math.round(2 * petalscale) / petalscale;
    float boundsY = Math.round(11 * petalscale) / petalscale;
    BufferedImage image = currentImage;

    return new HashMap<String, Frame[]>() {
      {
        put("DEFAULT", new Frame[] {
            new FrameBuilder(image)
                .withScale(petalscale)
                .withBounds(new Rectangle(boundsX, boundsY, 28, 8))
                .build()
        });
      }
    };
  }

  public void remove() {
    setMapEntityStatus(MapEntityStatus.REMOVED);
  }
}
