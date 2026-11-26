package Screens;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import Engine.KeyLocker;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.GameObject;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.*;
import Players.Rose;
import Utils.SpeedrunTimer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.Random;

import LevelLopader.*;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
  private static final int SPAWN_INTRO_FRAMES = 150;
  private static final int SPOTLIGHT_HOLD_FRAMES = 45;
  private static final float SPAWN_START_ZOOM = 2.6f;
  private static final String LEVEL_TITLE_TEXT = "haunted hill's";
  private static final Font LEVEL_TITLE_FONT = new Font("Georgia", Font.BOLD, 28);
  private static final int LEVEL_TITLE_HOLD_FRAMES = 60;
  private static final int LEVEL_TITLE_FADE_FRAMES = 30;
  private int levelTitleTimer;
  private static final Font SPEEDRUN_FONT = new Font(Font.MONOSPACED, Font.BOLD, 18);
  private final SpeedrunTimer speedrunTimer = new SpeedrunTimer();
  private final KeyLocker speedrunKeyLocker = new KeyLocker();
  private boolean speedrunTimerVisible;
  private final Random deathOutcomeRandom = new Random();
  private DeathCutsceneScreen deathCutsceneScreen;

  protected ScreenCoordinator screenCoordinator;
  protected Map map;
  protected Player player;
  protected PlayLevelScreenState playLevelScreenState;
  protected int screenTimer;
  protected int currentLevel = 1;
  protected LevelClearedScreen levelClearedScreen;
  protected LevelLoseScreen levelLoseScreen;
  protected boolean levelCompletedStateChangeStart;
  protected int spawnIntroTimer;
  protected GameObject gameObject;
  protected float scaleX = 1f;
  protected float scaleY = 1f;
  // protected float changeScaleX = 0.01f;
  // protected float changeScaleY = 0.01f;
  // protected float prevY;

  public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
    this.screenCoordinator = screenCoordinator;
  }

  public void initialize() {
    speedrunTimer.reset();
    speedrunTimerVisible = false;
    speedrunKeyLocker.lockKey(Key.T);
    if (gameObject != null) {
      gameObject.setScaleX(scaleX);
      gameObject.setScaleY(scaleY);
    }
    // gameObject.getCalibratedYLocation();

    loadLevel();
  }

  private void loadLevel() {
    map = createLevelMap();
    // if (gameObject.compareYChange() > 0) {
    // scaleX = scaleX + changeScaleX;
    // scaleY = scaleY + changeScaleY;
    // } else if (gameObject.compareYChange() < 0) {
    // scaleX = scaleX - changeScaleX;
    // scaleY = scaleY - changeScaleY;
    // } else {
    // while (scaleX != 1 && scaleY != 1) {
    // if (gameObject.compareYChange() > 0) {
    // scaleX -= changeScaleX;
    // scaleY -= changeScaleY;
    // }
    // if (gameObject.compareYChange() < 0) {
    // scaleX += changeScaleX;
    // scaleY += changeScaleY;
    // }
    // }
    // }

    player = new Rose(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, scaleX, scaleY);
    player.setMap(map);
    player.addListener(this);
    map.update(player);
    levelClearedScreen = new LevelClearedScreen();
    levelLoseScreen = new LevelLoseScreen(this);
    spawnIntroTimer = 0;
    levelTitleTimer = 0;
    levelCompletedStateChangeStart = false;
    playLevelScreenState = PlayLevelScreenState.LEVEL_INTRO;
    screenCoordinator.startLevelMusic(currentLevel);
  }

  private Map createLevelMap() {
        switch (currentLevel) {
        case 1:
          ret
         2:
          ret
         3:
          ret
         4:
          ret
         5:
          ret
        ase 6:
      //     ret
      //  7:
      //     ret
      //  8:
      //     ret
      //  9:
      //     ret
      de :
          thro
        
    
  

      if (playLevelScree
        deathCutsceneScreen.update();
      return;
      
    i
        speedrunKeyLocker.unlockKe
      se if (!speedrunKeyLocker.isKeyLock
        speedrunTimerVisible = !speedrunTimerVisible;
      speedrunKeyLocker.lockKey(Key.T);
      
    i
            && levelTitleTimer < LEVEL_TITLE_HOLD_FRAMES + LEVEL_TITLE_FADE_FRAME
        lTitleTimer++;
      
    s
        case LEVEL_INTRO:
          spawnIntroTim
        if (spawnIntroTime
            playLevelScreenState = PlayLevelScreenSt
          speedrunTimer.start();
          
        b
         RUNNI
          player.up
        map.update(playe
        break;
         LEVEL
          if (levelComplete
            screenTimer = 130;
          levelCompletedStat
          se {
            leve
          screenTimer--;
          if (screenTime
              if (currentLevel < 
                currentLevel++;
              loadLevel();
              se {
                goBa
              
            
          
        b
         LEVEL
          levelLoseScr
        break;
        
    
  

      // based on screen state, draw appropriate grap
    switch (playLevelScreenState) {
        case LEVEL_INTRO:
          drawSpawnIntr
        break;
         RUNNI
          graphicsH
        map.draw(graphicsHandler);
            
        player.draw(graphicsHandle
        graphicsHandler.endZoom();
        drawLevelTitle(graphicsHan
        break;
         LEVEL
          levelClearedScree
        break;
         LEVEL
          levelLoseScr
        break;
         DEATH
          deathCutsceneScr
        return;
        
    i
        String text = speedrunT
      int textWidth = graphicsHandler.getGraphics().g
      int x = ScreenManager.getScreenWidth() - textWidth - 20;
      graphicsHandler.drawFilledRectangle(x - 8, 12, textWidth
      graphicsHandler.drawString(text, x, 34, SPEEDRUN_FONT, Color.WHITE);
      
    
  

      if (currentLevel != 1 || spawnIntroTimer < SPAWN_INTRO_F
            || levelTitleTimer >= LEVEL_TITLE_HOLD_FRAMES + LEVEL
        rn;
      
    f
            / (float) LEVEL_TITLE_FADE_FRAMES;
        2D overlay = (Graphics2D) graphics
    overlay.setComposite(AlphaComposite.SrcOver.derive(opacity));
    overlay.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING
    overlay.setFont(LEVEL_TITLE_FONT);
    int textWidth = overlay.getFontMet
    int textX = (ScreenManager.getScreenWidth() - textWidth) / 2;
    int textY = ScreenManager.getScreenHeight() * 2 / 5 + overlay
    overlay.setColor(new Color(245, 233, 213));
    overlay.drawString(LEVEL_TITLE_TEXT, textX,
    overlay.dispose();
    
  

      float progress = Math.max(0f, Math.min(1f,
        (spawnIntroTimer - SPOTLIGHT_HOLD_FRAM
        ress = progress * progress * (3f - 2f * progress);
    

    float focusY = player.getCalibratedYLocation() + player.getHeight() / 2f
    float zoom = map.getZoom() * (SPAWN_START_ZOOM - (SPAWN_START_ZOOM - 1f) 
    

    map.draw(graphicsHandler);
    player.draw(graphicsHandle
    graphicsHandler.endZoom();
    

    int screenHeight = ScreenManager.getScreenHeight(
    float startRadius = Math.max(player.getWidth(), pla
    float screenRadius = (float) Math.hypot(screenWidth, screenHeight);
    float radius = startRadius + (screenRadius - startRadius) * progres
    int darkness = Math.round(220f * (1f - progress));
    

    overlay.setPaint(new RadialGradientPaint(
        new Point2D.Float(focusX, focusY),
        Math.max(radius, 1f),
        new float[] { 0f, 0.3
        new Color[] {
            new Color
            new Color(0, 0, 0, Mat
            new Color(0, 0, 0, darkness)
            
        ve
    overlay.dispose();
    
  

      return playLevelScreenState;
    
  

      return playLevelScreenSt
    
  

      speedrunTimer.pause();
    
  

      speedrunTimer.resume();
    }


  public vo
      if (currentLevel == 5) {
        speedrunTimer.finish
      
    i
        screenCoordinator.stopLevelMusic();
      
    
  

  public vo
      if (playLevelScreenState != 
        if (currentLevel == 5) {
          speedrunTimer.finish
        levelClearedScreen.setS
        
      p
      levelCompletedStateChangeStart = true;
      
    
  

      if (playLevelScreenState != PlayLevelScreenState.RUNNING) {
            return;
        }
        if (deathOutcomeRandom.nextBoolean()) {
            speedrunTimer.finish();
            playLevelScreenState = PlayLevelScreenState.DEATH_CUTSCENE;
            deathCutsceneScreen = new DeathCutsceneScreen(screenCoordinator, this::goBackToMenu);
            deathCutsceneScreen.initialize();
   

        loadLevel();
    
  

      if (deathCutsceneScreen 
        deathCutsceneScreen.close();
      
    s
    screenCoordinator.setGa
    
  

  private enum PlayLevelScreenState {
