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
public class SecretTileset2 extends Tileset {

    public SecretTileset2() {
        //super(ImageLoader.load("GrasslandTileset.png"), 31, 31, 1);
        super(ImageLoader.load("SecretLevelTileset.png"), 47, 47, 1);
    }
    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();
        Frame green1Frame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder green1Tile = new MapTileBuilder(green1Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green1Tile);

        Frame green2Frame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder green2Tile = new MapTileBuilder(green2Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green2Tile);

        Frame green3Frame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder green3Tile = new MapTileBuilder(green3Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green3Tile);

        Frame red1Frame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder red1Tile = new MapTileBuilder(red1Frame)
                .withTileType(TileType.KILL);
    
        mapTiles.add(red1Tile);

        Frame red2Frame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder red2Tile = new MapTileBuilder(red2Frame)
                .withTileType(TileType.KILL);
    
        mapTiles.add(red2Tile);


        Frame red3Frame = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder red3Tile = new MapTileBuilder(red3Frame)
                .withTileType(TileType.KILL);
    
        mapTiles.add(red3Tile);

        Frame green4Frame = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder green4Tile = new MapTileBuilder(green4Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green4Tile);

        Frame green5Frame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder green5Tile = new MapTileBuilder(green5Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green5Tile);

        Frame green6Frame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder green6Tile = new MapTileBuilder(green6Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green6Tile);

        Frame red4Frame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder red4Tile = new MapTileBuilder(red4Frame)
                .withTileType(TileType.KILL);
    
        mapTiles.add(red4Tile);

        Frame red5Frame = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder red5Tile = new MapTileBuilder(red5Frame)
                .withTileType(TileType.KILL);
    
        mapTiles.add(red5Tile);

        Frame red6Frame = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder red6Tile = new MapTileBuilder(red6Frame)
                .withTileType(TileType.KILL);
    
        mapTiles.add(red6Tile);


    Frame green7Frame = new FrameBuilder(getSubImage(5, 0))
             .withScale(tileScale)
             .build();

        MapTileBuilder green7Tile = new MapTileBuilder(green7Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green7Tile);

    Frame green8Frame = new FrameBuilder(getSubImage(5, 1))
             .withScale(tileScale)
             .build();

        MapTileBuilder green8Tile = new MapTileBuilder(green8Frame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(green8Tile);

        Frame green9Frame = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder green9Tile = new MapTileBuilder(green9Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(green9Tile);

        Frame red7Frame = new FrameBuilder(getSubImage(5, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder red7Tile = new MapTileBuilder(red7Frame)
                .withTileType(TileType.KILL);

        mapTiles.add(red7Tile);

    Frame red8Tile = new FrameBuilder(getSubImage(5, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder red8Frame = new MapTileBuilder(red8Tile)
                .withTileType(TileType.KILL);

        mapTiles.add(red8Frame);

    Frame red9Frame = new FrameBuilder(getSubImage(5,5))
                .withScale(tileScale)
                .build();
            
        MapTileBuilder red9Tile = new MapTileBuilder(red9Frame)
                .withTileType(TileType.KILL);

        mapTiles.add(red9Tile);

        return mapTiles;
    }
}