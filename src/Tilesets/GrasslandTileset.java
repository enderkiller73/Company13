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

// This class represents a "common" tileset of standard tiles defined in the GrasslandTileset.png file
public class GrasslandTileset extends Tileset {

    public GrasslandTileset() {
        super(ImageLoader.load("GrasslandTileset.png"), 32, 32, 1);
    }
    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        Frame grass1Frame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grass1Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(grassTile);


        //Background Tile
        Frame blackFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder blackTile = new MapTileBuilder(blackFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(blackTile);

        Frame grassbgFrame = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassbgTile = new MapTileBuilder(grassbgFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(grassbgTile);

        Frame stoneFrame = new FrameBuilder(getSubImage(0, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder stoneTile = new MapTileBuilder(stoneFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stoneTile);

        Frame grassDirtFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassDirtTile = new MapTileBuilder(grassDirtFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(grassDirtTile);

        Frame shrubFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder shrubTile = new MapTileBuilder(shrubFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(shrubTile);

        Frame woodFrame = new FrameBuilder(getSubImage(1, 2))
                .withScale(tileScale)
                .build();
        
        MapTileBuilder woodTile = new MapTileBuilder(woodFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(woodTile);

        Frame dirtFrame = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder dirtTile = new MapTileBuilder(dirtFrame)
                .withTileType(TileType.NOT_PASSABLE);
            mapTiles.add(dirtTile);

        Frame leftGrassSlopeFrame = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftGrassSlopeTile = new MapTileBuilder(leftGrassSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createLeft45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftGrassSlopeTile);

        Frame rightGrassSlopeFrame = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightGrassSlopeTile = new MapTileBuilder(rightGrassSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createRight45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(rightGrassSlopeTile);

        Frame topShrubFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder topShrubTile = new MapTileBuilder(topShrubFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(topShrubTile);

        return mapTiles;
    }
}