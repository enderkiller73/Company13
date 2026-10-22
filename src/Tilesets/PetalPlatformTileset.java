package Tilesets;
import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.Rectangle;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class PetalPlatformTileset extends Tileset {

    public PetalPlatformTileset() {
        super(ImageLoader.load("PetalPlatform.png"), 32, 32, 2);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        return defineTiles(32);
        //TS so clever im so proud of this
    }

    // builds the petal tile scaled to exactly fit the map's tile size
    public ArrayList<MapTileBuilder> defineTiles(int tilesize) {
        ArrayList<MapTileBuilder> maptiles = new ArrayList<>();
        float petalscale = tilesize / 32f;
        float boundsX = Math.round(2 * petalscale) / petalscale;
        float boundsY = Math.round(11 * petalscale) / petalscale;


        Frame petalPlatformFrame = new FrameBuilder(getSubImage(0, 0))
            .withScale(petalscale)
            .withBounds(new Rectangle(boundsX, boundsY, 28, 8))
            .build();

        MapTileBuilder petalPlatformTile = new MapTileBuilder(petalPlatformFrame)
            .withTileType(TileType.NOT_PASSABLE);

        maptiles.add(petalPlatformTile);

        Frame petalPlatformFrame2 = new FrameBuilder(getSubImage(0, 1))
            .withScale(petalscale)
            .withBounds(new Rectangle(boundsX, boundsY, 28, 8))
            .build();

        MapTileBuilder petalPlatformTile2 = new MapTileBuilder(petalPlatformFrame2)
            .withTileType(TileType.NOT_PASSABLE);

        maptiles.add(petalPlatformTile2);

        return maptiles;
    }

}
