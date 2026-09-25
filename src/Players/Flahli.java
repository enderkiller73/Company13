package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;

import java.util.HashMap;

public class Flahli extends Player {

    public Flahli(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("Flahli.png"), 31, 33), x, y, "STAND_RIGHT");
        gravity = .5f;
        terminalVelocityY = 10f;
        jumpHeight = 14.5f;
        jumpDegrade = .5f;
        walkSpeed = 5f;
        momentumYIncrease = .5f;
        dashDegrade = .5f;
        dashSpeed = 75f;
    }

    public void update() {
        super.update();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        //drawBounds(graphicsHandler, new Color(255, 0, 0, 170));
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0),9)
                            .withScale(1)
                            .withBounds(8, 16, 14, 16)
                            .build(),

                    new FrameBuilder(spriteSheet.getSprite(0, 1),9)
                            .withScale(1)
                            .withBounds(8, 16, 14, 16)
                            .build(),

                    new FrameBuilder(spriteSheet.getSprite(0, 2),9)
                            .withScale(1)
                            .withBounds(8, 16, 14, 16)
                            .build(),

                    new FrameBuilder(spriteSheet.getSprite(0,3),9)
                            .withScale(1)
                            .withBounds(8, 16, 14, 16)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0),9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),

                new FrameBuilder(spriteSheet.getSprite(0, 1),9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),

                    new FrameBuilder(spriteSheet.getSprite(0, 2),9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),

                    new FrameBuilder(spriteSheet.getSprite(0,3),9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 9)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 9)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 9)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 9)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 9)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("JUMP_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0))
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("JUMP_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0))
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("FALL_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0))
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("FALL_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0))
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("CLIMB_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0))
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0))
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("CLIMB_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0))
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("CROUCH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0))
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("PREPTHROW_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 2), 999)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("PREPTHROW_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 2), 999)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("THROW_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(6, 0))
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build()
            });

            put("THROW_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(6, 0))
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build()
                
            });
            put("DEATH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0),14)
                            .withScale(1)
                            .withBounds(8, 16, 16, 16)
                            .build(),
            });

            put("DEATH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0),14)
                            .withScale(1)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 16, 16, 16)
                            .build(),
            });
        }};
    }
}