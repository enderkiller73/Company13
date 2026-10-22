# SER225 Platformer Game

## What is this?
This is a game created for Quinnipiac's SER225 Agile Development class.
Students will be paired up in teams for an entire semester, and will utilize agile development to work further on this game.
This usually involves adding features and fixing bugs (I'm sure there are bugs...)

## How to run this project?
You can clone this repo and run it with Java (minimum version 8). I recommend using a tool such as Visual Studio Code or IntelliJ to run the game.

The main method for the game is in the `Game` package > `Game.java` file. The game opens with an intro screen and plays the "2012 New Valve Intro" WAV before transitioning to the main menu.<br>
The main method for the map editor is in the `MapEditor` package > `MapEditor.java` file.

## Playing music
Put `.wav` files in the `Music` folder and create a `MusicPlayer` with the file name. For example:

```java
MusicPlayer music = new MusicPlayer("level.wav");
music.play(true); // loop continuously
music.pause();
music.resume();
music.setVolume(0.5f); // range: 0.0 to 1.0
music.stop();
music.close();
```

The constructor can throw `IOException`, `UnsupportedAudioFileException`, and `LineUnavailableException` if the file cannot be loaded or audio is unavailable.

## Documentation
This project includes a GitHub pages website in the `docs` folder where the documentation resides.<br>
It can be accessed at [https://a-r-t.github.io/SER-225-Game-Platformer/](https://a-r-t.github.io/SER-225-Game-Platformer/).

Changes to content in the `docs` folder will not show up on the website unless they are pushed to the `master` branch.

## Credits
**Alex Thimineur**: Programmed the game, made the art.
