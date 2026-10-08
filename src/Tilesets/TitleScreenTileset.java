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
        super(ImageLoader.load("TitleBackground.png"), 64, 64, 1);
    }
    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        Frame frame11 = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile11 = new MapTileBuilder(frame11)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile11);
        
        Frame frame12 = new FrameBuilder(getSubImage(0, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile12 = new MapTileBuilder(frame12)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile12);

        Frame frame13 = new FrameBuilder(getSubImage(0, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile13 = new MapTileBuilder(frame13)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile13);

        Frame frame14 = new FrameBuilder(getSubImage(0, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile14 = new MapTileBuilder(frame14)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile14);

        Frame frame15 = new FrameBuilder(getSubImage(0, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile15 = new MapTileBuilder(frame15)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile15);

        Frame frame16 = new FrameBuilder(getSubImage(0, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile16 = new MapTileBuilder(frame16)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile16);

        Frame frame17 = new FrameBuilder(getSubImage(0, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile17 = new MapTileBuilder(frame17)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile17);

        Frame frame21 = new FrameBuilder(getSubImage(1, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile21 = new MapTileBuilder(frame21)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile21);
        
    Frame frame22 = new FrameBuilder(getSubImage(1, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile22 = new MapTileBuilder(frame22)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile22);

        Frame frame23 = new FrameBuilder(getSubImage(1, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile23 = new MapTileBuilder(frame23)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile23);

        Frame frame24 = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile24 = new MapTileBuilder(frame24)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile24);

        Frame frame25 = new FrameBuilder(getSubImage(1, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile25 = new MapTileBuilder(frame25)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile25);

        Frame frame26 = new FrameBuilder(getSubImage(1, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile26 = new MapTileBuilder(frame26)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile26);

        Frame frame27 = new FrameBuilder(getSubImage(1, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile27 = new MapTileBuilder(frame27)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile27);



        Frame frame31 = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile31 = new MapTileBuilder(frame31)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile31);
        
    Frame frame32 = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile32 = new MapTileBuilder(frame32)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile32);

        Frame frame33 = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile33 = new MapTileBuilder(frame33)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile33);

        Frame frame34 = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile34 = new MapTileBuilder(frame34)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile34);

        Frame frame35 = new FrameBuilder(getSubImage(2, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile35 = new MapTileBuilder(frame35)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile35);

        Frame frame36 = new FrameBuilder(getSubImage(2, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile36 = new MapTileBuilder(frame36)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile36);

        Frame frame37 = new FrameBuilder(getSubImage(2, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile37 = new MapTileBuilder(frame37)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile37);


        Frame frame41 = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile41 = new MapTileBuilder(frame41)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile41);
        
        Frame frame42 = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile42 = new MapTileBuilder(frame42)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile42);

        Frame frame43 = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile43 = new MapTileBuilder(frame43)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile43);

        Frame frame44 = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile44 = new MapTileBuilder(frame44)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile44);

        Frame frame45 = new FrameBuilder(getSubImage(3, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile45 = new MapTileBuilder(frame45)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile45);

        Frame frame46 = new FrameBuilder(getSubImage(3, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile46 = new MapTileBuilder(frame46)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile46);

        Frame frame47 = new FrameBuilder(getSubImage(3, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile47 = new MapTileBuilder(frame47)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile47);

        Frame frame51 = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile51 = new MapTileBuilder(frame51)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile51);
        
    Frame frame52 = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile52 = new MapTileBuilder(frame52)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile52);

        Frame frame53 = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile53 = new MapTileBuilder(frame53)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile53);

        Frame frame54 = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile54 = new MapTileBuilder(frame54)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile54);

        Frame frame55 = new FrameBuilder(getSubImage(4, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile55 = new MapTileBuilder(frame55)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile55);

        Frame frame56 = new FrameBuilder(getSubImage(4, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile56 = new MapTileBuilder(frame56)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile56);

        Frame frame57 = new FrameBuilder(getSubImage(4, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile57 = new MapTileBuilder(frame57)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile57);



        Frame frame61 = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile61 = new MapTileBuilder(frame61)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile61);
        
    Frame frame62 = new FrameBuilder(getSubImage(5, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile62 = new MapTileBuilder(frame62)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile62);

        Frame frame63 = new FrameBuilder(getSubImage(5, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile63 = new MapTileBuilder(frame63)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile63);

        Frame frame64 = new FrameBuilder(getSubImage(5, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile64 = new MapTileBuilder(frame64)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile64);

        Frame frame65 = new FrameBuilder(getSubImage(5, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile65 = new MapTileBuilder(frame65)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile65);

        Frame frame66 = new FrameBuilder(getSubImage(5, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile66 = new MapTileBuilder(frame66)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile66);

        Frame frame67 = new FrameBuilder(getSubImage(5, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile67 = new MapTileBuilder(frame67)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile67);

        Frame frame71 = new FrameBuilder(getSubImage(6, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile71 = new MapTileBuilder(frame71)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile71);
        
        Frame frame72 = new FrameBuilder(getSubImage(6, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile72 = new MapTileBuilder(frame72)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile72);

        Frame frame73 = new FrameBuilder(getSubImage(6, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile73 = new MapTileBuilder(frame73)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile73);

        Frame frame74 = new FrameBuilder(getSubImage(6, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile74 = new MapTileBuilder(frame74)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile74);

        Frame frame75 = new FrameBuilder(getSubImage(6, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile75 = new MapTileBuilder(frame75)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile75);

        Frame frame76 = new FrameBuilder(getSubImage(6, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile76 = new MapTileBuilder(frame76)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile76);

        Frame frame77 = new FrameBuilder(getSubImage(6, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile77 = new MapTileBuilder(frame77)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile77);

        Frame frame81 = new FrameBuilder(getSubImage(7, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile81 = new MapTileBuilder(frame81)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile81);
        
    Frame frame82 = new FrameBuilder(getSubImage(7, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile82 = new MapTileBuilder(frame82)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile82);

        Frame frame83 = new FrameBuilder(getSubImage(7, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile83 = new MapTileBuilder(frame83)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile83);

        Frame frame84 = new FrameBuilder(getSubImage(7, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile84 = new MapTileBuilder(frame84)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile84);

        Frame frame85 = new FrameBuilder(getSubImage(7, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile85 = new MapTileBuilder(frame85)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile85);

        Frame frame86 = new FrameBuilder(getSubImage(7, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile86 = new MapTileBuilder(frame86)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile86);

        Frame frame87 = new FrameBuilder(getSubImage(7, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile87 = new MapTileBuilder(frame87)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile87);

        Frame frame91 = new FrameBuilder(getSubImage(8, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile91 = new MapTileBuilder(frame91)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile91);
        
    Frame frame92 = new FrameBuilder(getSubImage(8, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile92 = new MapTileBuilder(frame92)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile92);

        Frame frame93 = new FrameBuilder(getSubImage(8, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile93 = new MapTileBuilder(frame93)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile93);

        Frame frame94 = new FrameBuilder(getSubImage(8, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile94 = new MapTileBuilder(frame94)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile94);

        Frame frame95 = new FrameBuilder(getSubImage(8, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile95 = new MapTileBuilder(frame95)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile95);

        Frame frame96 = new FrameBuilder(getSubImage(8, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile96 = new MapTileBuilder(frame96)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile96);

        Frame frame97 = new FrameBuilder(getSubImage(8, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tile97 = new MapTileBuilder(frame97)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(tile97);

        return mapTiles;
    }
}
