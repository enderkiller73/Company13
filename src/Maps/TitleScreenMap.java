package Maps;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Sprite;
import Level.Map;
import Tilesets.CommonTileset;
import Tilesets.GrasslandTileset;
import Utils.Colors;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Sprite player;

    public TitleScreenMap() {
        super("title_screen_map.txt", new GrasslandTileset());
        Point playerLocation = getMapTile(7, 10).getLocation().subtractX(24).subtractY(6);
        player = new Sprite(ImageLoader.loadSubImage("Flahli.png", Colors.MAGENTA, 0, 0, 31, 33));
        player.setScale(4);
        player.setLocation(playerLocation.x, playerLocation.y);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        player.draw(graphicsHandler);
    }

}
