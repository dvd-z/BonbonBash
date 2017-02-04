package BonbonBash;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState{
    private static StateBasedGame game;
    Image menuBackground,musicChoice;
    Music musicMenu;
    
    int musicX=411;
    
    @SuppressWarnings("empty-statement")
    public Menu(int stateID) throws SlickException{
        this.menuBackground=new Image("data\\images\\menuBackground.png");
        this.musicMenu=new Music("data\\music\\musicMenu.ogg");
        this.musicChoice=new Image("data\\images\\musicChoice.png");
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sg) throws SlickException {
       this.init(gc, sg);
    }
    
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        game=sbg;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        menuBackground.draw(0,0);
        musicChoice.draw(musicX,480);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input=gc.getInput();
        
        // loops music
        if(gv.music==false){
            musicMenu.pause();
        }else if(!musicMenu.playing()){
            musicMenu.loop();
        }
        
        // play
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>410&&input.getMouseX()<540&&input.getMouseY()>130&&input.getMouseY()<185){
            musicMenu.stop();
            game.enterState(1,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
        }
        
        // high scores
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>383&&input.getMouseX()<573&&input.getMouseY()>230&&input.getMouseY()<333){
            game.enterState(2,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
        }
        
        //music toggle
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>414&&input.getMouseX()<470&&input.getMouseY()>439&&input.getMouseY()<483){
            musicX=411;
            if(!musicMenu.playing()){
                musicMenu.resume();
            }
            gv.music=true;
        }
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>502&&input.getMouseX()<532&&input.getMouseY()>439&&input.getMouseY()<483){
            musicX=484;
            gv.music=false;
        }
        
        // exit
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>780&&input.getMouseX()<880&&input.getMouseY()>460&&input.getMouseY()<520){
            gc.exit();
        }
        
        gv.mousePress=input.isMouseButtonDown(0);
    }
}