package BonbonBash;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

public class HighScores extends BasicGameState{
    private static StateBasedGame game;
    BufferedReader Read;
    BufferedWriter WriteFile;
    TrueTypeFont font;
    
    Image highScoresBackground,resetscoresConfirmation,enterNameText;
    
    int c1=0,place=-1,keyPressDelay=0;
    boolean resetscoresConfirmationOpen=false;
    String line="",name="";
    String[] nameSaved=new String[10];
    
    String mouse="";
    
    @SuppressWarnings("empty-statement")
    public HighScores(int stateID) throws SlickException, FileNotFoundException, IOException{
        this.highScoresBackground=new Image("data\\images\\highScoresBackground.png");
        this.resetscoresConfirmation=new Image("data\\images\\resetScoresConfirmation.png");
        this.enterNameText=new Image("data\\images\\enterNameText.png");
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sg) throws SlickException {
       this.init(gc, sg);
    }
    
    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        game=sbg;
        
        // load font file
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("data\\VCR_OSD_MONO.ttf");
            Font awtFont=Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont=awtFont.deriveFont(28f);
            font=new TrueTypeFont(awtFont, false);
        } catch (FontFormatException | IOException e) {
        }
        
        // read scores
        try {
            this.Read=new BufferedReader(new FileReader("data\\highScores.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(c1=0;c1<10;c1++){
            try {
                line=Read.readLine();
                nameSaved[c1]=line.substring(0,line.indexOf(","));
                gv.scoreSaved[c1]=Integer.parseInt(line.substring(line.indexOf(",")+1,line.length()));
            } catch (IOException ex) {
                Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Read.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        place=-1;
        if(gv.highScore==true){
            for(c1=0;c1<gv.scoreSaved.length;c1++){
                if(gv.score>gv.scoreSaved[c1]){
                    place=c1;
                    break;
                }
            }
        }
        
        name="";
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        highScoresBackground.draw(0,0);
        
        for(c1=0;c1<10;c1++){
            font.drawString(378,129+39*c1,nameSaved[c1],Color.yellow);
            font.drawString(545,129+39*c1,String.valueOf(gv.scoreSaved[c1]),Color.yellow);
        }
        
        if(resetscoresConfirmationOpen==true&&gv.highScore==false){
            resetscoresConfirmation.draw(25,85);
        }
        
        if(gv.highScore==true){
            enterNameText.draw(308,101);
            font.drawString(358,322,name,Color.yellow);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input=gc.getInput();
        
        // menu button
        if(gv.mousePress==false&&resetscoresConfirmationOpen==false&&gv.highScore==false&&input.isMouseButtonDown(0)&&input.getMouseX()>780&&input.getMouseX()<880&&input.getMouseY()>460&&input.getMouseY()<505){
            game.enterState(0,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
        }
        
        // opens reset scores confirmation box
        if(gv.mousePress==false&&resetscoresConfirmationOpen==false&&gv.highScore==false&&input.isMouseButtonDown(0)&&input.getMouseX()>92&&input.getMouseX()<222&&input.getMouseY()>410&&input.getMouseY()<495){
            resetscoresConfirmationOpen=true;
        }
        
        // reset scores
        if(gv.mousePress==false&&resetscoresConfirmationOpen==true&&input.isMouseButtonDown(0)&&input.getMouseX()>39&&input.getMouseX()<85&&input.getMouseY()>199&&input.getMouseY()<223){
            try {
                this.WriteFile=new BufferedWriter(new FileWriter("data\\highScores.txt"));
                WriteFile.write("DAVID0,30000");WriteFile.newLine();
                WriteFile.write("DAVID1,25000");WriteFile.newLine();
                WriteFile.write("DAVID2,20000");WriteFile.newLine();
                WriteFile.write("DAVID3,17500");WriteFile.newLine();
                WriteFile.write("DAVID4,15000");WriteFile.newLine();
                WriteFile.write("DAVID5,12500");WriteFile.newLine();
                WriteFile.write("DAVID6,10000");WriteFile.newLine();
                WriteFile.write("DAVID7,7500");WriteFile.newLine();
                WriteFile.write("DAVID8,5000");WriteFile.newLine();
                WriteFile.write("DAVID9,2500");
                WriteFile.close();
                game.enterState(2,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
            } catch (IOException ex) {
                Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
            }
            resetscoresConfirmationOpen=false;
        }
        
        // cancel reset scores
        if(gv.mousePress==false&&resetscoresConfirmationOpen==true&&input.isMouseButtonDown(0)&&input.getMouseX()>170&&input.getMouseX()<259&&input.getMouseY()>199&&input.getMouseY()<223){
            resetscoresConfirmationOpen=false;
        }
        
        // type name for high scores
        if(gv.highScore==true&&(keyPressDelay==10||gv.keyPress==false)){
            keyPressDelay=0;
            
            if(input.isKeyDown(Input.KEY_0)&&gv.keyPress==false&&name.length()<8){name+="0";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_1)&&gv.keyPress==false&&name.length()<8){name+="1";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_2)&&gv.keyPress==false&&name.length()<8){name+="2";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_3)&&gv.keyPress==false&&name.length()<8){name+="3";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_4)&&gv.keyPress==false&&name.length()<8){name+="4";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_5)&&gv.keyPress==false&&name.length()<8){name+="5";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_6)&&gv.keyPress==false&&name.length()<8){name+="6";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_7)&&gv.keyPress==false&&name.length()<8){name+="7";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_8)&&gv.keyPress==false&&name.length()<8){name+="8";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_9)&&gv.keyPress==false&&name.length()<8){name+="9";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_A)&&gv.keyPress==false&&name.length()<8){name+="A";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_B)&&gv.keyPress==false&&name.length()<8){name+="B";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_C)&&gv.keyPress==false&&name.length()<8){name+="C";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_D)&&gv.keyPress==false&&name.length()<8){name+="D";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_E)&&gv.keyPress==false&&name.length()<8){name+="E";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_F)&&gv.keyPress==false&&name.length()<8){name+="F";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_G)&&gv.keyPress==false&&name.length()<8){name+="G";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_H)&&gv.keyPress==false&&name.length()<8){name+="H";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_I)&&gv.keyPress==false&&name.length()<8){name+="I";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_J)&&gv.keyPress==false&&name.length()<8){name+="J";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_K)&&gv.keyPress==false&&name.length()<8){name+="K";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_L)&&gv.keyPress==false&&name.length()<8){name+="L";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_M)&&gv.keyPress==false&&name.length()<8){name+="M";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_N)&&gv.keyPress==false&&name.length()<8){name+="N";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_O)&&gv.keyPress==false&&name.length()<8){name+="O";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_P)&&gv.keyPress==false&&name.length()<8){name+="P";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_Q)&&gv.keyPress==false&&name.length()<8){name+="Q";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_R)&&gv.keyPress==false&&name.length()<8){name+="R";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_S)&&gv.keyPress==false&&name.length()<8){name+="S";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_T)&&gv.keyPress==false&&name.length()<8){name+="T";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_U)&&gv.keyPress==false&&name.length()<8){name+="U";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_V)&&gv.keyPress==false&&name.length()<8){name+="V";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_W)&&gv.keyPress==false&&name.length()<8){name+="W";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_X)&&gv.keyPress==false&&name.length()<8){name+="X";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_Y)&&gv.keyPress==false&&name.length()<8){name+="Y";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_Z)&&gv.keyPress==false&&name.length()<8){name+="Z";gv.keyPress=true;}
            else if(input.isKeyDown(Input.KEY_BACK)&&name.length()>0&&gv.keyPress==false){
                name=name.substring(0,name.length()-1);
                gv.keyPress=true;
            }
            // enter name, writes in file
            else if(input.isKeyDown(Input.KEY_INSERT)||input.isKeyDown(Input.KEY_ENTER)&&name.length()>=1){
                gv.highScore=false;
                try {
                    this.WriteFile=new BufferedWriter(new FileWriter("data\\highScores.txt"));
                    // writes name and scores that are higher than player's
                    for(c1=0;c1<place;c1++){
                        WriteFile.write(nameSaved[c1]+","+gv.scoreSaved[c1]);WriteFile.newLine();
                    }
                    // writes player's name and score
                    WriteFile.write(name+","+gv.score);WriteFile.newLine();
                    // writes name and scores that are lower than player's
                    for(c1=place;c1<9;c1++){
                        WriteFile.write(nameSaved[c1]+","+gv.scoreSaved[c1]);WriteFile.newLine();
                    }
                    WriteFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // refresh high scores
                game.enterState(2,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
            }else{
                gv.keyPress=false;
            }
        }
        
        // delay used for pressing keys
        if(gv.keyPress==true){
            keyPressDelay++;
        }
        
        gv.mousePress=input.isMouseButtonDown(0);
    }
}