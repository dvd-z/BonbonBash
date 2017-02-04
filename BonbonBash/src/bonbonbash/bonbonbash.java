package BonbonBash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class bonbonbash extends StateBasedGame{
    
    public bonbonbash(String name)
    {
        super(name);
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException 
    {
        this.addState(new Menu(0));
        this.addState(new Play(1));
        try{
            this.addState(new HighScores(2));
        }catch(FileNotFoundException ex){
            Logger.getLogger(bonbonbash.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(bonbonbash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static void main(String[] args) throws SlickException 
    {
        AppGameContainer appgc;
        appgc=new AppGameContainer(new bonbonbash("Bonbon Bash"));
        appgc.setDisplayMode(958,522,false);
        appgc.setTargetFrameRate(59);
        appgc.setShowFPS(true);
        appgc.setVSync(true);
        appgc.start();
    }
}