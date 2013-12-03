package wavedefender;
import entities.Player;
import gameobjects.Entity;
import gameobjects.GameState;
import gameobjects.Pickup;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import managers.EnemyWave;
import managers.WaveManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pickups.Health;

/**
 * WaveDefender game
 * @author craig
 */
public class WaveDefender extends BasicGame {
    
    public static String VERSION = "0.1";
    public Player p;
    public static int baseHealth = 9999999;
    public static GameState gamestate = GameState.MENU;
    
    public WaveManager wm;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new WaveDefender("WaveDefender - " + WaveDefender.VERSION));
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(WaveDefender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public WaveDefender(String title) throws SlickException {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        p = new Player(300, 400);
        wm = new WaveManager(p, 10);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        
    	// MENU
        if(WaveDefender.gamestate == GameState.MENU) {
            if(container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                WaveDefender.gamestate = GameState.PLAYING;
            }
            return;
        }
        
        // PAUSED
        if(WaveDefender.gamestate == GameState.PAUSED) {
        	if(container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                WaveDefender.gamestate = GameState.PLAYING;
            }
        	return;
        }
        
        // GAME OVER
        if(WaveDefender.gamestate == GameState.GAMEOVER || WaveDefender.gamestate == GameState.COMPLETED) {
            return;
        }
        
        
        p.update(container, delta);
        wm.update(container, delta, p);

        
        if(WaveDefender.baseHealth < 0) {
            WaveDefender.gamestate = GameState.GAMEOVER;
        }
        
        if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            WaveDefender.gamestate = GameState.PAUSED;
        }
        
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if(WaveDefender.gamestate == GameState.MENU) {
            g.setColor(new Color(200, 100, 100));
            g.drawString("WAVEDEFENDER", 100, 100);
            g.setColor(new Color(200, 200, 200));
            g.drawString("Press Space to play!", 100, container.getHeight() / 2);
            return;
        }
        
        if(WaveDefender.gamestate == GameState.PAUSED) {
        	g.setColor(new Color(200, 100, 100));
            g.drawString("Game Paused", 100, 100);
            g.setColor(new Color(200, 200, 200));
            g.drawString("Press Space to resume!", 100, container.getHeight() / 2);
            return;
        }
        
        if(WaveDefender.gamestate == GameState.GAMEOVER) {
            g.setColor(new Color(200, 100, 100));
            g.drawString("GAME OVERRRRR!", 100, container.getHeight() / 2);
            return;
        }
        
        if(WaveDefender.gamestate == GameState.COMPLETED) {
        	g.setColor(new Color(100, 100, 200));
            g.drawString("CONGRATULATIONS!!! WELL DONE!!!!", 100, container.getHeight() / 2);
            return;
        }
        
        p.render(g);
        wm.render(g);
        
        g.setColor(new Color(100, 100, 200));
        g.drawString("WAVE:" + (WaveManager.waveNumber + 1), 10, 30);
        g.drawString("Base Health:" + WaveDefender.baseHealth, 10, 50);

    }
    
}
