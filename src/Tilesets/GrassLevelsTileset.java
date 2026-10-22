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
public class GrassLevelsTileset extends Tileset {

    public GrassLevelsTileset() {
        //super(ImageLoader.load("GrasslandTileset.png"), 31, 31, 1);
        super(ImageLoader.load("GrassLevelsTileset.png"), 47, 47, 1);
    }
    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();
        Frame emptySpaceFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder emptySpaceTile = new MapTileBuilder(emptySpaceFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(emptySpaceTile);

        Frame grassFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grassFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(grassTile);

        Frame grassDirtFrame = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassDirtTile = new MapTileBuilder(grassDirtFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(grassDirtTile);

        Frame dirtFrame = new FrameBuilder(getSubImage(0, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder dirtTile = new MapTileBuilder(dirtFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(dirtTile);

        Frame leftEdgeGrassFrame = new FrameBuilder(getSubImage(0, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftEdgeGrassTile = new MapTileBuilder(leftEdgeGrassFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(leftEdgeGrassTile);

        Frame rightEdgeGrassFrame = new FrameBuilder(getSubImage(0, 4))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEdgeGrassTile = new MapTileBuilder(rightEdgeGrassFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(rightEdgeGrassTile);

        Frame leftSideGrassFrame = new FrameBuilder(getSubImage(0, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftSideGrassTile = new MapTileBuilder(leftSideGrassFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(leftSideGrassTile);

        Frame rightSideGrassFrame = new FrameBuilder(getSubImage(0, 5))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightSideGrassTile = new MapTileBuilder(rightSideGrassFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(rightSideGrassTile);

        Frame bgDirtFrame = new FrameBuilder(getSubImage(0, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder bgDirtTile = new MapTileBuilder(bgDirtFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(bgDirtTile);
        
        Frame leftGrassSlopeFrame = new FrameBuilder(getSubImage(0, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftGrassSlopeTile = new MapTileBuilder(leftGrassSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createLeft45SlopeLayout(spriteWidth, (int) tileScale));
    
        mapTiles.add(leftGrassSlopeTile);

         Frame rightGrassSlopeFrame = new FrameBuilder(getSubImage(0, 7))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightGrassSlopeTile = new MapTileBuilder(rightGrassSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createRight45SlopeLayout(spriteWidth, (int) tileScale));
    
        mapTiles.add(rightGrassSlopeTile);

        Frame endFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder endTile = new MapTileBuilder(endFrame)
                .withTileType(TileType.END);
    
        mapTiles.add(endTile);

        Frame topGrassFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder topGrassTile = new MapTileBuilder(topGrassFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(topGrassTile);

        Frame moonFrame = new FrameBuilder(getSubImage(1, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder moonTile = new MapTileBuilder(moonFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(moonTile);

        Frame threeStarsFrame = new FrameBuilder(getSubImage(1, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder threeStarsTile = new MapTileBuilder(threeStarsFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(threeStarsTile);

        Frame twoStarsFrame = new FrameBuilder(getSubImage(1, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder twoStarsTile = new MapTileBuilder(twoStarsFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(twoStarsTile);

        Frame oneStarFrame = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder oneStarTile = new MapTileBuilder(oneStarFrame)
                .withTileType(TileType.PASSABLE);
    
        mapTiles.add(oneStarTile);

        Frame leftBottomGrassSlopeFrame = new FrameBuilder(getSubImage(1, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBottomGrassSlopeTile = new MapTileBuilder(leftBottomGrassSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createBottomLeft30SlopeLayout(spriteWidth, (int) tileScale));
    
        mapTiles.add(leftBottomGrassSlopeTile);

        // Frame rightBottomGrassSlopeFrame = new FrameBuilder(getSubImage(1, 6))
        //         .withScale(tileScale)
        //         .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        //         .build();

        // MapTileBuilder rightBottomGrassSlopeTile = new MapTileBuilder(rightBottomGrassSlopeFrame)
        //         .withTileType(TileType.SLOPE)
        //         .withTileLayout(SlopeTileLayoutUtils.createBottomRight30SlopeLayout(spriteWidth, (int) tileScale));
    
        // mapTiles.add(rightBottomGrassSlopeTile);

    Frame leftTopGrassSlopeFrame = new FrameBuilder(getSubImage(1, 7))
             .withScale(tileScale)
             .build();

        MapTileBuilder leftTopGrassSlopeTile = new MapTileBuilder(leftTopGrassSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createTopLeft30SlopeLayout(spriteWidth, (int) tileScale));
    
        mapTiles.add(leftTopGrassSlopeTile);

    Frame woodFrame = new FrameBuilder(getSubImage(2, 0))
             .withScale(tileScale)
             .build();

        MapTileBuilder woodTile = new MapTileBuilder(woodFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(woodTile);

    Frame middleWoodFrame = new FrameBuilder(getSubImage(2, 1))
             .withScale(tileScale)
             .build();

        MapTileBuilder middleWoodTile = new MapTileBuilder(middleWoodFrame)
                .withTileType(TileType.NOT_PASSABLE);
    
        mapTiles.add(middleWoodTile);

        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .withBounds(0, 3, 48, 0)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(leftEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .withBounds(0, 3, 48, 0)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(rightEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightEndBranchTile);

        Frame leftBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 3, 48, 0)
                .build();

        MapTileBuilder leftBranchTile = new MapTileBuilder(leftBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftBranchTile);

        // right end branch
        Frame rightBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 3, 48, 0)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightBranchTile = new MapTileBuilder(rightBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightBranchTile);

        Frame[] BugWallFrames = new Frame[] {
                new FrameBuilder(getSubImage(2, 4), 120)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(2, 5), 10)
                        .withScale(tileScale)
                        .build(),
        };

        MapTileBuilder BugWallTile = new MapTileBuilder(BugWallFrames)
                .withTileType(TileType.KILL);

        mapTiles.add(BugWallTile);

    Frame stoneFrame = new FrameBuilder(getSubImage(2, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder stoneTile = new MapTileBuilder(stoneFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stoneTile);

    Frame livingHandsFrame = new FrameBuilder(getSubImage(2,7))
                .withScale(tileScale)
                .build();
            
        MapTileBuilder livingHandsTile = new MapTileBuilder(livingHandsFrame)
                .withTileType(TileType.KILL);

        mapTiles.add(livingHandsTile);

    Frame leftEndShrubFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftEndShrubTile = new MapTileBuilder(leftEndShrubFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftEndShrubTile);

    Frame downEndShrubFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder downEndShrubTile = new MapTileBuilder(downEndShrubFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(downEndShrubTile);

    Frame upEndShrubFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder upEndShrubTile = new MapTileBuilder(upEndShrubFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(upEndShrubTile);
    
    Frame rightEndShrubFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightEndShrubTile = new MapTileBuilder(rightEndShrubFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightEndShrubTile);
    
    Frame shrubFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder shrubTile = new MapTileBuilder(shrubFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(shrubTile);

    Frame waterFrame = new FrameBuilder(getSubImage(3,5))
                .withScale(tileScale)
                .build();

        MapTileBuilder waterTile = new MapTileBuilder(waterFrame)
                .withTileType(TileType.KILL);

        mapTiles.add(waterTile);

        return mapTiles;
    }
}
