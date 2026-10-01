package Tilesets;
import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;
import Utils.SlopeTileLayoutUtils;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class PetalPlatformTileset extends Tileset {

    public PetalPlatformTileset() {
        super(ImageLoader.load("PetalPlatform.png"), 32, 32, 2);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        Frame petalPlatformFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder petalPlatformTile = new MapTileBuilder(petalPlatformFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(petalPlatformTile);

        return mapTiles;
    }
}
