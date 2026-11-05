package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Enemies.DinosaurEnemy.DinosaurState;
import Enemies.SporeFlower.SporeState;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntity;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

public class SporeFlower extends Enemy {
    private float gravity = 100f;
    private float movementSpeed = 0f;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;

    protected int shootWaitTimer;
    protected int shootTimer;

    protected SporeState sporeState;
    protected SporeState prevSporeState;

    public SporeFlower(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("SporeFlower.png"), 63, 63), "IDLE");
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        sporeState = SporeState.SHOOT_WAIT;
        prevSporeState = sporeState;
        facingDirection = startFacingDirection;
        currentAnimationName = "IDLE";

        if (sporeState == SporeState.SHOOT) {
            currentAnimationName = "SPORE_SHOOT";
        }
    
        airGroundState = AirGroundState.GROUND;

        // every certain number of frames, the fireball will be shot out
        shootWaitTimer = 60;
    }

    @Override
    public void update(Player player) {
        float moveAmountY = 0;

        moveAmountY += gravity;

        moveYHandleCollision(moveAmountY);

        if (sporeState == SporeState.SHOOT_WAIT) {
            // currentAnimationName = "SPORE_SHOOT";
            if (prevSporeState == SporeState.IDLE) {
                shootTimer = 65;
            } else if (shootTimer == 0) {
                sporeState = SporeState.SHOOT;
            }
            else {
                shootTimer--;
            }
        }

        // this is for actually having the dinosaur spit out the fireball
        if (sporeState == SporeState.SHOOT) {
            // define where fireball will spawn on map (x location) relative to dinosaur enemy's location
            // and define its movement speed
            int sporeY;
            float movementSpeed;
            
            sporeY = Math.round(getY()) + getHeight();
            movementSpeed = -2f;
            

            // define where fireball will spawn on the map (y location) relative to dinosaur enemy's location
            int sporeX = Math.round(getX()) + 4;

            // create Fireball enemy
            SporeCloud sporeCloud = new SporeCloud(new Point(sporeX, sporeY), movementSpeed, 120);


            map.addEnemy(sporeCloud);

            // change dinosaur back to its WALK state after shooting, reset shootTimer to wait a certain number of frames before shooting again
            sporeState = SporeState.IDLE;

            // reset shoot wait timer so the process can happen again (dino walks around, then waits, then shoots)
            shootWaitTimer = 130;
        }

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
            put("IDLE", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(2)
                            .withBounds(7, 48, 56, 16)
                            .build()
            });

            put("SPORE_SHOOT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 10)
                            .withScale(2)
                            .withBounds(7, 48, 56, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 20)
                            .withScale(2)
                            .withBounds(7, 48, 56, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 2), 10)
                            .withScale(2)
                            .withBounds(7, 48, 56, 16)
                            .build()
            });
        }};
    }

    public enum SporeState {
        IDLE, SHOOT_WAIT, SHOOT
    }
}