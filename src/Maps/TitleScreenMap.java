package Maps;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Sprite;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Colors;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Sprite playerCharacter;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        Point platformLocation = getMapTile(6, 9).getLocation();
        playerCharacter = new Sprite(ImageLoader.loadSubImage("Flahli.png", Colors.MAGENTA, 0, 0, 31, 33));
        playerCharacter.setScale(2);
        playerCharacter.setLocation(
            platformLocation.x + (getTileset().getScaledSpriteWidth() - playerCharacter.getWidth()) / 2f,
            platformLocation.y - playerCharacter.getHeight()
        );
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        playerCharacter.draw(graphicsHandler);
    }

}
