package Enemies;

import java.util.HashMap;
import Builders.FrameBuilder;
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


public class EvilFlower extends Enemy {
    private float gravity = 100f;
    private float movementSpeed = .0f;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;

    public EvilFlower(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("EvilFlower.png"), 63, 63), "IDLE_LEFT");
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "IDLE_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "IDLE_LEFT";
        }
        airGroundState = AirGroundState.GROUND;
    }

    @Override
    public void update(Player player) {
        float moveAmountY = 0;

        moveAmountY += gravity;

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
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 2), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 3), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .build()
            });

            put("IDLE_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 2), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 3), 20)
                            .withScale(2)
                            .withBounds(25, 15, 5, 48)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });
        }};
    }
}

