package pkg2048;

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
    TrueTypeFont fontScore,fontName;
    
    Image highscoresBackground,highscoresBackgroundReset,highscoresBackgroundName;
    
    int c1=0,place=0,keyPressDelay=0;
    boolean resetScoresConfirmation=false;
    String line="",name="";
    String[] nameSaved=new String[10];
    int[] largestSaved=new int[10];
    
    @SuppressWarnings("empty-statement")
    public HighScores(int stateID) throws SlickException{
        this.highscoresBackground=new Image("data\\highscoresBackground.png");
        this.highscoresBackgroundReset=new Image("data\\highscoresBackgroundReset.png");
        this.highscoresBackgroundName=new Image("data\\highscoresBackgroundName.png");
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
            InputStream inputStream = ResourceLoader.getResourceAsStream("data\\Tahoma.ttf");
            Font awtFont=Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont=awtFont.deriveFont(30f);
            fontScore=new TrueTypeFont(awtFont,false);
            awtFont=awtFont.deriveFont(40f);
            fontName=new TrueTypeFont(awtFont,false);

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
                gv.scoreSaved[c1]=Integer.parseInt(line.substring(line.indexOf(",")+1,line.lastIndexOf(",")));
                largestSaved[c1]=Integer.parseInt(line.substring(line.lastIndexOf(",")+1,line.length()));
            } catch (IOException ex) {
                Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Read.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // determines place in scores
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
        highscoresBackground.draw(0,0);
        
        if(resetScoresConfirmation==true){
            highscoresBackgroundReset.draw(0,0);
        }
        
        if(gv.highScore==true){
            highscoresBackgroundName.draw(0,0);
            fontName.drawString(46,340,name,Color.darkGray);
        }
        
        for(c1=0;c1<10;c1++){
            fontScore.drawString(398,175+50*c1,String.valueOf(c1+1),Color.darkGray);
            fontScore.drawString(502,175+50*c1,nameSaved[c1],Color.darkGray);
            fontScore.drawString(700,175+50*c1,String.valueOf(gv.scoreSaved[c1]),Color.darkGray);
            fontScore.drawString(947,175+50*c1,String.valueOf(largestSaved[c1]),Color.darkGray);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input=gc.getInput();
        
        // menu button
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>=50&&input.getMouseX()<=200&&input.getMouseY()>=580&&input.getMouseY()<=620){
            game.enterState(0,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
        }
        
        // opens reset scores confirmation box
        if(gv.mousePress==false&&resetScoresConfirmation==false&&gv.highScore==false&&input.isMouseButtonDown(0)&&input.getMouseX()>47&&input.getMouseX()<345&&input.getMouseY()>211&&input.getMouseY()<251){
            resetScoresConfirmation=true;
        }
        
        // reset scores
        if(gv.mousePress==false&&resetScoresConfirmation==true&&input.isMouseButtonDown(0)&&input.getMouseX()>44&&input.getMouseX()<127&&input.getMouseY()>283&&input.getMouseY()<321){
            try {
                this.WriteFile=new BufferedWriter(new FileWriter("data\\highScores.txt"));
                WriteFile.write("DAVID0,52304,4096");WriteFile.newLine();
                WriteFile.write("DAVID1,31012,2048");WriteFile.newLine();
                WriteFile.write("DAVID2,14428,1024");WriteFile.newLine();
                WriteFile.write("DAVID3,5124,512");WriteFile.newLine();
                WriteFile.write("DAVID4,2294,256");WriteFile.newLine();
                WriteFile.write("DAVID5,668,128");WriteFile.newLine();
                WriteFile.write("DAVID6,396,64");WriteFile.newLine();
                WriteFile.write("DAVID7,226,32");WriteFile.newLine();
                WriteFile.write("DAVID8,78,16");WriteFile.newLine();
                WriteFile.write("DAVID9,40,8");
                WriteFile.close();
                game.enterState(2,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
            } catch (IOException ex) {
                Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
            }
            resetScoresConfirmation=false;
        }
        
        // cancel reset scores
        if(gv.mousePress==false&&resetScoresConfirmation==true&&input.isMouseButtonDown(0)&&input.getMouseX()>212&&input.getMouseX()<363&&input.getMouseY()>283&&input.getMouseY()<321){
            resetScoresConfirmation=false;
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
                    // writes names, scores, and largests that are higher than player's
                    for(c1=0;c1<place;c1++){
                        WriteFile.write(nameSaved[c1]+","+gv.scoreSaved[c1]+","+largestSaved[c1]);WriteFile.newLine();
                    }
                    // writes player's name, score, and largest
                    WriteFile.write(name+","+gv.score+","+gv.largest);WriteFile.newLine();
                    // writes names, scores, and largests that are lower than player's
                    for(c1=place;c1<9;c1++){
                        WriteFile.write(nameSaved[c1]+","+gv.scoreSaved[c1]+","+largestSaved[c1]);WriteFile.newLine();
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