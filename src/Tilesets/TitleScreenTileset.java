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

public class TitleScreenTileset extends Tileset {
    
    public TitleScreenTileset(){
        super(ImageLoader.load("TitleBackground.png"), 64, 64, 3);
    }
    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        Frame frame1 = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile1 = new MapTileBuilder(frame1)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile1);
        
    Frame frame2 = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile2 = new MapTileBuilder(frame2)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile2);

        Frame frame3 = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile3 = new MapTileBuilder(frame3)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile3);

        Frame frame4 = new FrameBuilder(getSubImage(0, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile4 = new MapTileBuilder(frame4)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile4);

    Frame frame5 = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile5 = new MapTileBuilder(frame5)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile5);
    
    Frame frame6 = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile6 = new MapTileBuilder(frame6)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile6);
    
    Frame frame7 = new FrameBuilder(getSubImage(1, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile7 = new MapTileBuilder(frame7)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile7);
    
    Frame frame8 = new FrameBuilder(getSubImage(1, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile8 = new MapTileBuilder(frame8)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile8);

    Frame frame9 = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile9 = new MapTileBuilder(frame9)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile9);
        
    Frame frame10 = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile10 = new MapTileBuilder(frame10)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile10);

        Frame frame11 = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile11 = new MapTileBuilder(frame11)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile11);

        Frame frame12 = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile12 = new MapTileBuilder(frame12)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile12);

    Frame frame13 = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile13 = new MapTileBuilder(frame13)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile13);
    
    Frame frame14 = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile14 = new MapTileBuilder(frame14)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile14);
    
    Frame frame15 = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile15 = new MapTileBuilder(frame15)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile15);
    
    Frame frame16 = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile16 = new MapTileBuilder(frame16)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile16);

    Frame frame17 = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile17 = new MapTileBuilder(frame17)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile17);
        
    Frame frame18 = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile18 = new MapTileBuilder(frame18)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile18);

        Frame frame19 = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile19 = new MapTileBuilder(frame19)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile19);

        Frame frame20 = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile20 = new MapTileBuilder(frame20)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile20);

        return mapTiles;
    }
}
