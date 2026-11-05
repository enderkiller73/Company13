package Enemies;

import Builders.FrameBuilder;
import Enemies.DinosaurEnemy.DinosaurState;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntity;
import Level.Player;
import Level.PlayerState;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;
import java.util.Random;

// This class is for the black bug enemy

public class BuddingFlower extends Enemy {
    private float gravity = .5f;
    private float fallAmount = 1.0f;
    private float movementSpeed = 2f;
    private int walkingTime;
    private int idleTime;
    private int coinFlip;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private Random rand = new Random();
    protected Point startLocation;
    protected Point endLocation;
    protected BuddingState buddingState;

    public BuddingFlower(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("BuddingFlower.png"), 63, 63), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;
        float moveAmountY = 0;
        walkingTime ++;

        moveAmountY += gravity + fallAmount;
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
                moveXHandleCollision(movementSpeed);
                buddingState = BuddingState.WALKING;
            } else {
                currentAnimationName = "WALK_LEFT";
                moveXHandleCollision(-movementSpeed);
                buddingState = BuddingState.WALKING;
            }

            if (getX1() + getWidth() >= endBound) {
                float difference = endBound - (getX2());
                moveXHandleCollision(-difference);
                facingDirection = Direction.LEFT;
            } else if (getX1() <= startBound) {
                float difference = startBound - getX1();
                moveXHandleCollision(difference);
                facingDirection = Direction.RIGHT;
            }
        
        if (walkingTime >= 120) {
            coinFlip = rand.nextInt(2);
            if (coinFlip == 0) {
                buddingState = BuddingState.IDLE;
            }
            walkingTime = 0;
        }

        moveYHandleCollision(moveAmountY);
        super.update(player);
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction,  MapEntity entityCollidedWith) {
        // if bug has collided into something while walking forward,
        // it turns around (changes facing direction)
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if bug is colliding with the ground, change its air ground state to GROUND
        // if it is not colliding with the ground, it means that it's currently in the air, so its air ground state is changed to AIR
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                airGroundState = AirGroundState.GROUND;
            } else {
                airGroundState = AirGroundState.AIR;
            }
        }
    }

    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("IDLE_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build()
            
            });

            put("IDLE_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });

            put("SPROUT_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 1), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 2), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 4), 4)
                            .withScale(2)
                            .withBounds(25, 30, 20, 33)
                            .build()
            });
        }};
    }

    public enum BuddingState {
        WALKING, IDLE, SPROUT
    }

     public void touchedPlayer(Player player) {
        if (buddingState == BuddingState.WALKING) {
            player.hurtPlayer(this);
        }
    }
}