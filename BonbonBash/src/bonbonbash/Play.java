package pkg2048;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
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

public class Play extends BasicGameState{
    private static StateBasedGame game;
    
    Image playBackground,playBackgroundExit,playHighscoreBackground,playNoHighscoreBackground;
    Image x2,x4,x8,x16,x32,x64,x128,x256,x512,x1024,x2048,x4096,x8192,x16384,x32768,x65536;
    TrueTypeFont fontScore;
    
    int c1=0,c2=0,c3=0,holdNum=0,endCounter=0;
    boolean spawn=false,end=true,keyPress=false,keyPressUp=false,keyPressDown=false,keyPressRight=false,keyPressLeft=false,exitConfirmation=false;
    int[][] board=new int[4][4];
    int[][] boardHold=new int[4][4];
    
    @SuppressWarnings("empty-statement")
    public Play(int stateID) throws SlickException{
        this.playBackground=new Image("data\\playBackground.png");
        this.playBackgroundExit=new Image("data\\playBackgroundExit.png");
        this.playHighscoreBackground=new Image("data\\playHighscoreBackground.png");
        this.playNoHighscoreBackground=new Image("data\\playNoHighscoreBackground.png");
        this.x2=new Image("data\\2.png");this.x4=new Image("data\\4.png");this.x8=new Image("data\\8.png");this.x16=new Image("data\\16.png");
        this.x32=new Image("data\\32.png");this.x64=new Image("data\\64.png");this.x128=new Image("data\\128.png");
        this.x256=new Image("data\\256.png");this.x512=new Image("data\\512.png");this.x1024=new Image("data\\1024.png");
        this.x2048=new Image("data\\2048.png");this.x4096=new Image("data\\4096.png");this.x8192=new Image("data\\8192.png");
        this.x16384=new Image("data\\16384.png");this.x32768=new Image("data\\32768.png");this.x65536=new Image("data\\65536.png");
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sg) throws SlickException {
       this.init(gc, sg);
    }
    
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        game=sbg;
        
        Random r=new Random();
        
        // load font file
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("data\\Tahoma.ttf");
            Font awtFont=Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont=awtFont.deriveFont(48f);
            fontScore=new TrueTypeFont(awtFont, false);

        } catch (FontFormatException | IOException e) {
        }
        
        for(c1=0;c1<4;c1++){
            for(c2=0;c2<4;c2++){
                board[c1][c2]=0;
            }
        }
        
        gv.score=0;
        gv.largest=0;
        endCounter=0;
        end=true;
        
        // generates initial two blocks
        c1=r.nextInt(3-0+1)+0;
        c2=r.nextInt(3-0+1)+0;
        c3=r.nextInt(10-1+1)+1;
        
        if(c3==1){
            board[c1][c2]=4;
        }else{
            board[c1][c2]=2;
        }
        
        spawn=true;
        while(spawn==true){
            c1=r.nextInt(3-0+1)+0;
            c2=r.nextInt(3-0+1)+0;
            if(board[c1][c2]==0){
                c3=r.nextInt(10-1+1)+1;
                
                if(c3==1){
                    board[c1][c2]=4;
                }else{
                    board[c1][c2]=2;
                }
                spawn=false;
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        playBackground.draw(0,0);
        
        if(exitConfirmation==true){
            playBackgroundExit.draw(0,0);
        }
        
        for(c1=0;c1<4;c1++){
            for(c2=0;c2<4;c2++){
                if(board[c1][c2]==2){x2.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==4){x4.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==8){x8.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==16){x16.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==32){x32.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==64){x64.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==128){x128.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==256){x256.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==512){x512.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==1024){x1024.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==2048){x2048.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==4096){x4096.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==8192){x8192.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==16384){x16384.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==32768){x32768.draw(505+148*c2,103+150*c1);}
                else if(board[c1][c2]==65536){x65536.draw(505+148*c2,103+150*c1);}
            }
        }
        
        fontScore.drawString(45,270,String.valueOf(gv.score),Color.darkGray);
        
        // draws overlaying text when game is over
        if(gv.highScore==true&&endCounter>=60){
            playHighscoreBackground.draw(0,0);
        }else if(end==true&&endCounter>=60){
            playNoHighscoreBackground.draw(0,0);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input=gc.getInput();
        Random r=new Random();
        
        // menu button
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>=54&&input.getMouseX()<=200&&input.getMouseY()>=585&&input.getMouseY()<=632){
            gv.mousePress=true;
            exitConfirmation=true;
        }
        if(gv.mousePress==false&&exitConfirmation==true&&input.isMouseButtonDown(0)&&input.getMouseX()>=50&&input.getMouseX()<=134&&input.getMouseY()>=592&&input.getMouseY()<=633){
            gv.mousePress=true;
            exitConfirmation=false;
            game.enterState(0,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
        }
        if(gv.mousePress==false&&exitConfirmation==true&&input.isMouseButtonDown(0)&&input.getMouseX()>=218&&input.getMouseX()<=368&&input.getMouseY()>=591&&input.getMouseY()<=633){
            gv.mousePress=true;
            exitConfirmation=false;
        }
        
        // buttons for when game ends
        // high score
        if(gv.highScore==true){
            if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>=678&&input.getMouseX()<=925&&input.getMouseY()>=590&&input.getMouseY()<=640){
                game.enterState(2,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
            }
            endCounter++;
        }
        // play again
        else if(end==true){
            if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>=660&&input.getMouseX()<=941&&input.getMouseY()>=590&&input.getMouseY()<=640){
                game.enterState(1,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
            }
            endCounter++;
        }
        
        for(c1=0;c1<4;c1++){
            for(c2=0;c2<4;c2++){
                boardHold[c1][c2]=board[c1][c2];
            }
        }
        
        // arrow key controls
        if(input.isKeyDown(Input.KEY_UP)&&keyPress==false){
            keyPressUp=true;
            keyPress=true;
            
            // moves blocks
            for(c1=0;c1<4;c1++){
                // moves blocks in direction first
                while(board[0][c1]==0&&(board[1][c1]!=0||board[2][c1]!=0||board[3][c1]!=0)){
                    board[0][c1]=board[1][c1];board[1][c1]=0;
                    board[1][c1]=board[2][c1];board[2][c1]=0;
                    board[2][c1]=board[3][c1];board[3][c1]=0;
                }
                while(board[1][c1]==0&&(board[2][c1]!=0||board[3][c1]!=0)){
                    board[1][c1]=board[2][c1];board[2][c1]=0;
                    board[2][c1]=board[3][c1];board[3][c1]=0;
                }
                if(board[2][c1]==0){
                    board[2][c1]=board[3][c1];board[3][c1]=0;
                }
                
                // combines similar blocks, adds to score, and moves in direction
                if(board[0][c1]==board[1][c1]){
                    board[0][c1]*=2;gv.score+=board[0][c1];
                    board[1][c1]=board[2][c1];board[2][c1]=0;
                    board[2][c1]=board[3][c1];board[3][c1]=0;
                }
                if(board[1][c1]==board[2][c1]){
                    board[1][c1]*=2;gv.score+=board[1][c1];
                    board[2][c1]=board[3][c1];board[3][c1]=0;
                }
                if(board[2][c1]==board[3][c1]){
                    board[2][c1]*=2;gv.score+=board[2][c1];
                    board[3][c1]=0;
                }
            }
        }else if(input.isKeyDown(Input.KEY_DOWN)&&keyPress==false){
            keyPressDown=true;
            keyPress=true;
            
            for(c1=0;c1<4;c1++){
                while(board[3][c1]==0&&(board[2][c1]!=0||board[1][c1]!=0||board[0][c1]!=0)){
                    board[3][c1]=board[2][c1];board[2][c1]=0;
                    board[2][c1]=board[1][c1];board[1][c1]=0;
                    board[1][c1]=board[0][c1];board[0][c1]=0;
                }
                while(board[2][c1]==0&&(board[1][c1]!=0||board[0][c1]!=0)){
                    board[2][c1]=board[1][c1];board[1][c1]=0;
                    board[1][c1]=board[0][c1];board[0][c1]=0;
                }
                if(board[1][c1]==0){
                    board[1][c1]=board[0][c1];board[0][c1]=0;
                }
                
                if(board[3][c1]==board[2][c1]){
                    board[3][c1]*=2;gv.score+=board[3][c1];
                    board[2][c1]=board[1][c1];board[1][c1]=0;
                    board[1][c1]=board[0][c1];board[0][c1]=0;
                }
                if(board[2][c1]==board[1][c1]){
                    board[2][c1]*=2;gv.score+=board[2][c1];
                    board[1][c1]=board[0][c1];board[0][c1]=0;
                }
                if(board[1][c1]==board[0][c1]){
                    board[1][c1]*=2;gv.score+=board[1][c1];
                    board[0][c1]=0;
                }
            }
        }else if(input.isKeyDown(Input.KEY_RIGHT)&&keyPress==false){
            keyPressRight=true;
            keyPress=true;
            
            for(c1=0;c1<4;c1++){
                while(board[c1][3]==0&&(board[c1][2]!=0||board[c1][1]!=0||board[c1][0]!=0)){
                    board[c1][3]=board[c1][2];board[c1][2]=0;
                    board[c1][2]=board[c1][1];board[c1][1]=0;
                    board[c1][1]=board[c1][0];board[c1][0]=0;
                }
                while(board[c1][2]==0&&(board[c1][1]!=0||board[c1][0]!=0)){
                    board[c1][2]=board[c1][1];board[c1][1]=0;
                    board[c1][1]=board[c1][0];board[c1][0]=0;
                }
                if(board[c1][1]==0){
                    board[c1][1]=board[c1][0];board[c1][0]=0;
                }
                
                if(board[c1][3]==board[c1][2]){
                    board[c1][3]*=2;gv.score+=board[c1][3];
                    board[c1][2]=board[c1][1];board[c1][1]=0;
                    board[c1][1]=board[c1][0];board[c1][0]=0;
                }
                if(board[c1][2]==board[c1][1]){
                    board[c1][2]*=2;gv.score+=board[c1][2];
                    board[c1][1]=board[c1][0];board[c1][0]=0;
                }
                if(board[c1][1]==board[c1][0]){
                    board[c1][1]*=2;gv.score+=board[c1][1];
                    board[c1][0]=0;
                }
            }
        }else if(input.isKeyDown(Input.KEY_LEFT)&&keyPress==false){
            keyPressLeft=true;
            keyPress=true;
            
            for(c1=0;c1<4;c1++){
                while(board[c1][0]==0&&(board[c1][1]!=0||board[c1][2]!=0||board[c1][3]!=0)){
                    board[c1][0]=board[c1][1];board[c1][1]=0;
                    board[c1][1]=board[c1][2];board[c1][2]=0;
                    board[c1][2]=board[c1][3];board[c1][3]=0;
                }
                while(board[c1][1]==0&&(board[c1][2]!=0||board[c1][3]!=0)){
                    board[c1][1]=board[c1][2];board[c1][2]=0;
                    board[c1][2]=board[c1][3];board[c1][3]=0;
                }
                if(board[c1][2]==0){
                    board[c1][2]=board[c1][3];board[c1][3]=0;
                }
                
                if(board[c1][0]==board[c1][1]){
                    board[c1][0]*=2;gv.score+=board[c1][0];
                    board[c1][1]=board[c1][2];board[c1][2]=0;
                    board[c1][2]=board[c1][3];board[c1][3]=0;
                }
                if(board[c1][1]==board[c1][2]){
                    board[c1][1]*=2;gv.score+=board[c1][1];
                    board[c1][2]=board[c1][3];board[c1][3]=0;
                }
                if(board[c1][2]==board[c1][3]){
                    board[c1][2]*=2;gv.score+=board[c1][2];
                    board[c1][3]=0;
                }
            }
        }
        
        end=true;
        for(c1=0;c1<4;c1++){
            for(c2=0;c2<4;c2++){
                // checks if move was made
                if(boardHold[c1][c2]!=board[c1][c2]){
                    // set to spawn new block
                    spawn=true;
                }
                
                // checks for new largest block
                if(board[c1][c2]>gv.largest){
                    gv.largest=board[c1][c2];
                }
                
                // if there is an empty space (therefore game is not over)
                if(board[c1][c2]==0){
                    end=false;
                }
                
                // if there are two similar blocks next to each other (therefore game is not over)
                if(c2<3){
                    if(board[c1][c2]==board[c1][c2+1]){
                        end=false;
                    }
                    if(board[c2][c1]==board[c2+1][c1]){
                        end=false;
                    }
                }
            }
        }
        
        // spawns new block
        while(spawn==true){
            c1=r.nextInt(3-0+1)+0;
            c2=r.nextInt(3-0+1)+0;
            if(board[c1][c2]==0){
                c3=r.nextInt(10-1+1)+1;
                if(c3==1){
                    board[c1][c2]=4;
                }else{
                    board[c1][c2]=2;
                }
                spawn=false;
            }
        }
        
        // checks for high score at end of game
        if(end==true){
            if(gv.score>gv.scoreSaved[9]){
                gv.highScore=true;
            }
        }
        
        // prevents held down arrow keys from repeatedly playing
        if(keyPressUp==true&&!input.isKeyDown(Input.KEY_UP)){
            keyPressUp=false;
            keyPress=false;
        }
        if(keyPressDown==true&&!input.isKeyDown(Input.KEY_DOWN)){
            keyPressDown=false;
            keyPress=false;
        }
        if(keyPressRight==true&&!input.isKeyDown(Input.KEY_RIGHT)){
            keyPressRight=false;
            keyPress=false;
        }
        if(keyPressLeft==true&&!input.isKeyDown(Input.KEY_LEFT)){
            keyPressLeft=false;
            keyPress=false;
        }
        
        gv.mousePress=input.isMouseButtonDown(0);
    }
}