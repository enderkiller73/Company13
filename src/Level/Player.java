package Level;

import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.MapEntity;
import MapEditor.TileBuilder;
import Tilesets.CommonTileset;
import Tilesets.GrasslandTileset;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.AirWallState;
import Tilesets.PetalPlatformTileset;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Arrays;

import Builders.MapTileBuilder;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected float dashSpeed = 0;
    protected float currentDashSpeed = 0;
    protected float dashDestinationX = 0f;
    protected float gravity = 0;
    protected float jumpHeight = 0;
    protected float jumpDegrade = 0;
    protected float terminalVelocityY = 0;
    protected float momentumYIncrease = 0;
    protected float momentumXdecrease = 0;
    protected float maxMomentum = 0f;
    protected float prevMoveAmountY;
    protected float dashDegrade = 0;
    protected float floatFallCounter = 0;
    protected float floatFallMax = 0;
    protected float floatSpeed = 0;

    // values used to handle player movement
    protected float jumpForce = 0;
    protected float momentumX = 0;
    protected float momentumY = 0;
    protected float velocity = 0;
    protected float maxVelocity = 0;
    protected float moveAmountX, moveAmountY;
    protected float lastAmountMovedX, lastAmountMovedY;
    protected int dashAmount;
    protected int dashCap;
    protected float airSpeed;
    

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    protected AirGroundState previousAirGroundState;
    protected AirWallState airWallState;
    protected AirWallState previousAirWallState;
    protected LevelState levelState;

    // classes that listen to player events can be added to this list
    protected ArrayList<PlayerListener> listeners = new ArrayList<>();

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key JUMP_KEY = Key.W;
    protected Key MOVE_LEFT_KEY = Key.A;
    protected Key MOVE_RIGHT_KEY = Key.D;
    protected Key CROUCH_KEY = Key.S;
    protected Key CLIMB_KEY = Key.L;
    protected Key DASH_KEY = Key.J;
    protected Key PLACE_KEY = Key.K;

    // flags
    protected boolean isInvincible = false; // if true, player cannot be hurt by enemies (good for testing)
    protected boolean reducedFallSpeed = false;
    protected boolean isDashing = false;


    //frame counts
    int climbFrameCount = 0;
    int climbStartFrame = -1;
    int velocityStartFrame = 0;
    int framecount = 0;

    // Resources for Placeable Tiles 
    CommonTileset commonTileset = new CommonTileset();
    ArrayList<MapTile> placedTiles = new ArrayList<>();
    ArrayList<Integer> placedAtFrame =  new ArrayList<>();
    PetalPlatformTileset petalPlatformTileset = new PetalPlatformTileset();

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        levelState = LevelState.RUNNING;
        airWallState = AirWallState.AIR;
        previousAirWallState = airWallState;
    }

    public void update() {
        moveAmountX = 0;
        moveAmountY = 0;

        // if player is currently playing through level (has not won or lost)
        if (levelState == LevelState.RUNNING) {
            applyGravity();

            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
                handleDash();
                slip(); 
            } while (previousPlayerState != playerState);
            framecount++;
            playerClimbing();
            climbFrameCount ++;
            placePlatform();
            unPlacePlatform();
            previousAirGroundState = airGroundState;
            previousAirWallState = airWallState;

            // move player with respect to map collisions based on how much player needs to move this frame
            lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
            lastAmountMovedY = super.moveYHandleCollision(moveAmountY);

            handlePlayerAnimation();

            updateLockedKeys();

            // update player's animation
            super.update();
        }

        // if player has beaten level
        else if (levelState == LevelState.LEVEL_COMPLETED) {
            updateLevelCompleted();
        }

        // if player has lost level
        else if (levelState == LevelState.PLAYER_DEAD) {
            updatePlayerDead();
        }
    }

    // add gravity to player, which is a downward force
    protected void applyGravity() {
        moveAmountY += gravity + momentumY;
    }

    protected void handleDash() {
        if(airGroundState == AirGroundState.GROUND) {
            dashAmount = 0;
        }
        if (Keyboard.isKeyDown(DASH_KEY) && !keyLocker.isKeyLocked(DASH_KEY)) {
            keyLocker.lockKey(DASH_KEY);
            //if (dashAmount != 0) {
            if (airGroundState == AirGroundState.AIR) {
               if (dashAmount <= dashCap) {
                    velocity += facingDirection == Direction.RIGHT ?  dashSpeed : -dashSpeed;
                    dashAmount++;
                }
            }
            else if (airGroundState == AirGroundState.GROUND) {
                if (dashAmount <= dashCap) {
                    velocity += facingDirection == Direction.RIGHT ? dashSpeed : -dashSpeed;
                    dashAmount++;
                }
            }
        }
    }


    protected void fallGravity() {
        if (reducedFallSpeed) {
            gravity = gravity / 2;
            terminalVelocityY = terminalVelocityY / 2;
        }
        else {
            gravity *= 2;
            terminalVelocityY *= 2;
        }
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case CROUCHING:
                playerCrouching();
                break;
            case JUMPING:
                playerJumping();
                break;
            case CLIMBING:
                playerClimbing();
                break;
            case THROWING:
                placePlatform();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        // if walk left or walk right key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.WALKING;
        }

        // if jump key is pressed, player enters JUMPING state
        else if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed, player enters CROUCHING state
        else if (Keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
    }

    // player WALKING state logic
    protected void playerWalking() {
        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            velocityStartFrame = framecount;
            velocity -= walkSpeed;
            if (velocity < -maxVelocity) {
                velocity = -maxVelocity;
            }
            facingDirection = Direction.LEFT;
        }
        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            velocityStartFrame = framecount;
            velocity += walkSpeed;
            if (velocity > maxVelocity) {
                velocity = maxVelocity;
            }
            facingDirection = Direction.RIGHT;
        } else if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.STANDING;
        }
        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }
        // if crouch key is pressed,
        else if (Keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
        else if (Keyboard.isKeyDown(PLACE_KEY)) {
            playerState = PlayerState.THROWING;
        }
        moveAmountX += velocity;
    }

    protected void slip() {
        if (framecount - velocityStartFrame > 2 && playerState != PlayerState.WALKING) {
            velocity *= momentumXdecrease;
            moveAmountX += velocity;
            if (Math.abs(velocity) < 0.01f) {
                velocity = 0;
            }
        }
    }
    // player CROUCHING state logic
    protected void playerCrouching() {
        // if crouch key is released, player enters STANDING state
        if (Keyboard.isKeyUp(CROUCH_KEY)) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }
    }

    // player JUMPING state logic
    protected void playerJumping() {
        // if last frame player was on ground and this frame player is still on ground, the jump needs to be setup
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {

            // sets animation to a JUMP animation based on which way player is facing
            currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";

            // player is set to be in air and then player is sent into the air
            airGroundState = AirGroundState.AIR;
            jumpForce = jumpHeight;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                    if (Keyboard.isKeyDown(CLIMB_KEY) && airWallState == AirWallState.WALL) {
                        playerState = PlayerState.CLIMBING;
                    }
                }
            }
            if (Keyboard.isKeyDown(CLIMB_KEY)) {
                playerState = PlayerState.CLIMBING;
            }
        }
       

    

        // if player is in air (currently in a jump) and has more jumpForce, continue sending player upwards
        else if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            // allows you to move left and right while in the air
            if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
                facingDirection = Direction.LEFT;
                moveAmountX -= airSpeed;
                if (Keyboard.isKeyDown(CLIMB_KEY)) {
                    moveAmountX = -floatSpeed;
                }
            } else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
                facingDirection = Direction.RIGHT;
                moveAmountX += airSpeed;
                if (Keyboard.isKeyDown(CLIMB_KEY)) {
                    moveAmountX = floatSpeed;
                }
            }
            // if player is falling, increases momentum as player falls so it falls faster over time
            if (moveAmountY > 0) {
                increaseMomentumY();
            }
        }

        

        // if player last frame was in air and this frame is now on ground, player enters STANDING state
        else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }
    }

    

    protected void playerClimbing() {
       
        if (Keyboard.isKeyDown(CLIMB_KEY)) {

            if (climbStartFrame == -1) {
                climbStartFrame = climbFrameCount;
            }

            int heldFrames = climbFrameCount - climbStartFrame;

            if (heldFrames < 180) {
                playerState = PlayerState.JUMPING;
                moveAmountY = gravity; 
            } else {
  
                playerState = PlayerState.JUMPING;
            }
        }
        else {
   
            climbStartFrame = -1;
        }
        
        if (airGroundState == AirGroundState.GROUND && moveAmountX == 0) {
            playerState = PlayerState.STANDING;
        }
    }

    // while player is in air, this is called, and will increase momentumY by a set amount until player reaches terminal velocity
    protected void increaseMomentumY() {
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }
    }

    protected void increaseMomentumX() {
        momentumX += momentumX;
        if (momentumX < maxMomentum) {
            momentumX = 4;
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(JUMP_KEY)) {
            keyLocker.unlockKey(JUMP_KEY);
        }
        if (Keyboard.isKeyUp(DASH_KEY)) {
            keyLocker.unlockKey(DASH_KEY);
        }
        if(Keyboard.isKeyUp(PLACE_KEY)) {
            keyLocker.unlockKey(PLACE_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void handlePlayerAnimation() {
        int centerX = Math.round(getBounds().getX1()) + Math.round(getBounds().getWidth() / 2f);
        int centerY = Math.round(getBounds().getY1()) + Math.round(getBounds().getHeight() / 2f);
        MapTile currentMapTile = map.getTileByPosition(centerX, centerY);

        if (playerState == PlayerState.STANDING) {
            // sets animation to a STAND animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";

            // handles putting goggles on when standing in water
            // checks if the center of the player is currently touching a water tile
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.WATER) {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "SWIM_STAND_RIGHT" : "SWIM_STAND_LEFT";
            }
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.KILL) {
                levelState = LevelState.PLAYER_DEAD;
                System.out.println("Bro Should be Dead");
            }
        }
        else if (playerState == PlayerState.WALKING) {
            // sets animation to a WALK animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.KILL) {
                levelState = LevelState.PLAYER_DEAD;
                System.out.println("Bro Should be Dead");
            }
            
        }
        else if (playerState == PlayerState.CROUCHING) {
            // sets animation to a CROUCH animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.KILL) {
                levelState = LevelState.PLAYER_DEAD;
                System.out.println("Bro Should be Dead");
            }
        }
        else if (playerState == PlayerState.JUMPING) {
            // if player is moving upwards, set player's animation to jump. if player moving downwards, set player's animation to fall
            if (lastAmountMovedY <= 0) {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            } else {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
            }
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.KILL) {
                levelState = LevelState.PLAYER_DEAD;
                System.out.println("Bro Should be Dead");
            }
        }
        else if (playerState == PlayerState.CLIMBING) {
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "CLIMB_RIGHT" : "CLIMB_LEFT";
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.KILL) {
                levelState = LevelState.PLAYER_DEAD;
                System.out.println("Bro Should be Dead");
            }
        }
        else if (playerState == PlayerState.THROWING) {
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.KILL) {
                levelState = LevelState.PLAYER_DEAD;
                System.out.println("Bro Should be Dead");
            }
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (direction == Direction.LEFT) {
            if ((hasCollided) && (entityCollidedWith.getMapEntityStatus() == MapEntityStatus.ACTIVE)) {
                moveAmountX = 0;
                momentumX = 0;
                airWallState = AirWallState.WALL;
                System.out.println("Player collided with wall on left");
            }
        } else if (direction == Direction.RIGHT) {
                if ((hasCollided) && (entityCollidedWith.getMapEntityStatus() == MapEntityStatus.ACTIVE)) {
                moveAmountX = 0;
                momentumX = 0;
                airWallState = AirWallState.WALL;
                System.out.println("Player collided with wall on right");
            }
        } else {
            airWallState = AirWallState.AIR;
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if player collides with a map tile below it, it is now on the ground
        // if player does not collide with a map tile below, it is in air
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
            } else {
                playerState = PlayerState.JUMPING;
                airGroundState = AirGroundState.AIR;
            }
        }

        // if player collides with map tile upwards, it means it was jumping and then hit into a ceiling -- immediately stop upwards jump velocity
        else if (direction == Direction.UP) {
            if (hasCollided) {
                jumpForce = 0;
            }
        }
    }

    // other entities can call this method to hurt the player
    public void hurtPlayer(MapEntity mapEntity) {
        if (!isInvincible) {
            // if map entity is an enemy, kill player on touch
            if (mapEntity instanceof Enemy) {
                levelState = LevelState.PLAYER_DEAD;
            }
        }
    }

    // other entities can call this to tell the player they beat a level
    public void completeLevel() {
        levelState = LevelState.LEVEL_COMPLETED;
    }

    // if player has beaten level, this will be the update cycle
    public void updateLevelCompleted() {
        // if player is not on ground, player should fall until it touches the ground
        if (airGroundState != AirGroundState.GROUND && map.getCamera().containsDraw(this)) {
            currentAnimationName = "FALL_RIGHT";
            applyGravity();
            increaseMomentumY();
            super.update();
            moveYHandleCollision(moveAmountY);
        }
        // move player to the right until it walks off screen
        else if (map.getCamera().containsDraw(this)) {
            currentAnimationName = "WALK_RIGHT";
            super.update();
            moveXHandleCollision(walkSpeed);
        } else {
            // tell all player listeners that the player has finished the level
            for (PlayerListener listener : listeners) {
                listener.onLevelCompleted();
            }
        }
    }

    // if player has died, this will be the update cycle
    public void updatePlayerDead() {
        // change player animation to DEATH
        if (!currentAnimationName.startsWith("DEATH")) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "DEATH_RIGHT";
            } else {
                currentAnimationName = "DEATH_LEFT";
            }
            super.update();
        }
        // if death animation not on last frame yet, continue to play out death animation
        else if (currentFrameIndex != getCurrentAnimation().length - 1) {
          super.update();
        }
        // if death animation on last frame (it is set up not to loop back to start), player should continually fall until it goes off screen
        else if (currentFrameIndex == getCurrentAnimation().length - 1) {
            if (map.getCamera().containsDraw(this)) {
                moveY(3);
            } else {
                // tell all player listeners that the player has died in the level
                for (PlayerListener listener : listeners) {
                    listener.onDeath();
                }
            }
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public AirGroundState getAirGroundState() {
        return airGroundState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    // Uncomment this to have game draw player's bounds to make it easier to visualize
    /*
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
    */
    protected void placePlatform() {
        if (Keyboard.isKeyDown(PLACE_KEY)) {
            keyLocker.lockKey(PLACE_KEY);
            int targetX;
            int targetX2; 
            if (this.facingDirection == Direction.RIGHT) {
                targetX = (Math.round(this.x) + 96) / 48 * 48;
            }
            else {
                targetX = (Math.round(this.x) - 96) / 48 * 48;
            }
            int targetY = (Math.round(this.y)-32) / 48 * 48;

            if (this.facingDirection == Direction.RIGHT) {
                targetX2 = (Math.round(this.x) + 144) / 48 * 48;
            }
            else {
                targetX2 = (Math.round(this.x) - 144) / 48 * 48;
            }
            int targetY2 = (Math.round(this.y)-32) / 48 * 48;

            try {
                map.getTileByPosition(targetX, targetY).getTileType();
                map.getTileByPosition(targetX2, targetY2).getTileType();
            } catch (Exception e) {
                System.out.println("placing out of bounds");
                return;
            }
            if (map.getTileByPosition(targetX, targetY).getTileType() == TileType.PASSABLE && this.x != targetX && map.getTileByPosition(targetX2, targetY).getTileType() == TileType.PASSABLE && this.x != targetX2 && placedAtFrame.size() == 0) {
                MapTile newTile = petalPlatformTileset.defineTiles().get(0).build(targetX, targetY);
                newTile.setMap(map);
                map.setMapTile(targetX/48, targetY/48, newTile);
                placedTiles.add(map.getTileByPosition(targetX, targetY));
                System.out.println("placed");
                placedAtFrame.add(framecount);

                MapTile newTile2 = petalPlatformTileset.defineTiles().get(0).build(targetX2, targetY);
                newTile2.setMap(map);
                map.setMapTile(targetX2/48, targetY/48, newTile2);
                placedTiles.add(map.getTileByPosition(targetX2, targetY));
                System.out.println("placed");
                placedAtFrame.add(framecount);

            }
            else if (placedAtFrame.size() > 0 && framecount - placedAtFrame.get(0) >= 150 && placedTiles.size() <= 4){
                MapTile newTile = petalPlatformTileset.defineTiles().get(0).build(targetX, targetY);
                newTile.setMap(map);
                map.setMapTile(targetX/48, targetY/48, newTile);
                placedTiles.add(map.getTileByPosition(targetX, targetY));
                System.out.println("placed");
                placedAtFrame.add(framecount);

                MapTile newTile2 = petalPlatformTileset.defineTiles().get(0).build(targetX2, targetY);
                newTile2.setMap(map);
                map.setMapTile(targetX2/48, targetY/48, newTile2);
                placedTiles.add(map.getTileByPosition(targetX2, targetY));
                System.out.println("placed");
                placedAtFrame.add(framecount);
            }
            else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
                playerState = PlayerState.STANDING;
            }
        }
    }
    protected void unPlacePlatform() {
        if (placedTiles.size() > 0 && framecount - placedAtFrame.get(0)>= 180){
            MapTile oldTile = commonTileset.defineTiles().get(1).build(placedTiles.get(0).getX(), placedTiles.get(0).getY());
            oldTile.setMap(map);
            map.setMapTile(Math.round(placedTiles.get(0).getX())/48, Math.round(placedTiles.get(0).getY())/48, oldTile);
            System.out.println("unplaced");
            placedTiles.remove(0);
            placedAtFrame.remove(0);
        }
        if (placedTiles.size() > 0 && framecount - placedAtFrame.get(0)>= 180){
            MapTile oldTile2 = commonTileset.defineTiles().get(1).build(placedTiles.get(0).getX(), placedTiles.get(0).getY());
            oldTile2.setMap(map);
            map.setMapTile(Math.round(placedTiles.get(0).getX())/48, Math.round(placedTiles.get(0).getY())/48, oldTile2);
            System.out.println("unplaced");
            placedTiles.remove(0);
            placedAtFrame.remove(0);
        }
    }
} 
