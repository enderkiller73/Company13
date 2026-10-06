package MapEditor;

import Level.Map;
import Maps.LevelTwo;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Maps.TutorialMap;
import Maps.Level_Three;
import Maps.Level_Four;
import Maps.Level_Five;
import Maps.Level_Six;
import Maps.Level_Seven;
import Maps.Level_Eight;
import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("TutorialMap");
            add("LevelTwo");
            add("Level_Three");
            add("Level_Four");
            add("Level_Five");
            add("Level_Six");
            add("Level_Seven");
            add("Level_Eight");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "TutorialMap":
                return new TutorialMap();
            case "LevelTwo":
                return new LevelTwo();
            case "Level_Three":
                return new Level_Three();    
            case "Level_Four":
                return new Level_Four();   
            case "Level_Five":
                return new Level_Five();   
            case "Level_Six":
                return new Level_Six();   
            case "Level_Seven":
                return new Level_Seven();
            case "Level_Eight":
                return new Level_Eight();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
