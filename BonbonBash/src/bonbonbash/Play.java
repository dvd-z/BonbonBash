package BonbonBash;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

public class Play extends BasicGameState{
    private static StateBasedGame game;
    Random r=new Random();
    
    Image playBackground;
    Image candy1,candy2,candy3,candy4,candy5,candy6;
    Image candy1x,candy1y,candy2x,candy2y,candy3x,candy3y,candy4x,candy4y,candy5x,candy5y,candy6x,candy6y,candy1w,candy2w,candy3w,candy4w,candy5w,candy6w,candy7c;
    Image selector;
    Image exitConfirmation,shuffleText;
    Music musicPlay,musicHighScore;
    TrueTypeFont fontMoves,fontScore,fontBoardScore1,fontBoardScore2;
    
    int c1=0,c2=0,c3=0,c4=0,c5=0,x1=0,y1=0,x2=0,y2=0;
    int moves=30,combo=1,consecutive=0,animationCounter=0,animationMax=0,removeDelay=0,shuffleDelay=0,soundRandom=0;
    boolean select=false,move=false,movePossible=false,connection=false,animation=false,noneAbove=true,intersect=false,gameFinish=false,exitConfirmationOpen=false;
    String hold="",colourHold="";
    String[] moveCoordinates=new String[2];
    int[][] boardFallTimer=new int[8][8];
    int[][] boardScore=new int[8][8];
    int[][] boardScoreColour=new int[8][8];
    int[][] boardScoreTimer=new int[8][8];
    boolean[][] boardSelect=new boolean[8][8];
    String[][] board=new String[8][8];
    String[][] boardCopy=new String[8][8];
    String[][] boardRemove=new String[8][8];
    String[][] boardFall=new String[8][8];
    String[][] boardFallCopy=new String[8][8];
    ArrayList<String> removeCoordinates=new ArrayList();
    ArrayList<String> specialRemoveCoordinates=new ArrayList();
    ArrayList<String> specialCreationCoordinates=new ArrayList();
    ArrayList<String> intersectCoordinates=new ArrayList();
    ArrayList<String> wvCoordinates=new ArrayList();
    
    BufferedReader Read;
    String line="";
    String[] boardLine=new String[10];
    
    @SuppressWarnings("empty-statement")
    public Play(int stateID) throws SlickException{
        this.playBackground=new Image("data\\images\\playBackground.png");
        this.candy1=new Image("data\\images\\candy1.png");
        this.candy2=new Image("data\\images\\candy2.png");
        this.candy3=new Image("data\\images\\candy3.png");
        this.candy4=new Image("data\\images\\candy4.png");
        this.candy5=new Image("data\\images\\candy5.png");
        this.candy6=new Image("data\\images\\candy6.png");
        this.candy1x=new Image("data\\images\\candy1x.png");
        this.candy1y=new Image("data\\images\\candy1y.png");
        this.candy2x=new Image("data\\images\\candy2x.png");
        this.candy2y=new Image("data\\images\\candy2y.png");
        this.candy3x=new Image("data\\images\\candy3x.png");
        this.candy3y=new Image("data\\images\\candy3y.png");
        this.candy4x=new Image("data\\images\\candy4x.png");
        this.candy4y=new Image("data\\images\\candy4y.png");
        this.candy5x=new Image("data\\images\\candy5x.png");
        this.candy5y=new Image("data\\images\\candy5y.png");
        this.candy6x=new Image("data\\images\\candy6x.png");
        this.candy6y=new Image("data\\images\\candy6y.png");
        this.candy1w=new Image("data\\images\\candy1w.png");
        this.candy2w=new Image("data\\images\\candy2w.png");
        this.candy3w=new Image("data\\images\\candy3w.png");
        this.candy4w=new Image("data\\images\\candy4w.png");
        this.candy5w=new Image("data\\images\\candy5w.png");
        this.candy6w=new Image("data\\images\\candy6w.png");
        this.candy7c=new Image("data\\images\\colourBomb.png");
        this.selector=new Image("data\\images\\select.png");
        this.exitConfirmation=new Image("data\\images\\exitConfirmation.png");
        this.shuffleText=new Image("data\\images\\shuffleText.png");
        this.musicPlay=new Music("data\\music\\musicPlay.ogg");
        this.musicHighScore=new Music("data\\music\\musicHighScore.ogg");
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
        
        // load font file
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("data\\VCR_OSD_MONO.ttf");
            Font awtFont=Font.createFont(Font.TRUETYPE_FONT,inputStream);
            awtFont=awtFont.deriveFont(36f);
            fontMoves=new TrueTypeFont(awtFont,false);
            awtFont=awtFont.deriveFont(48f);
            fontScore=new TrueTypeFont(awtFont,false);
            awtFont=awtFont.deriveFont(34f);
            fontBoardScore1=new TrueTypeFont(awtFont,false);
            awtFont=awtFont.deriveFont(28f);
            fontBoardScore2=new TrueTypeFont(awtFont,false);
        } catch (FontFormatException | IOException e) {
        }
        
        // generates board and initializes all other arrays
        for(c1=0;c1<8;c1++){
            for(c2=0;c2<8;c2++){
                c3=(r.nextInt(6)+1);
                board[c1][c2]=String.valueOf(c3);
                boardSelect[c1][c2]=false;
                boardRemove[c1][c2]="0";
                boardFall[c1][c2]="-1";
                boardFallCopy[c1][c2]="-1";
                boardFallTimer[c1][c2]=0;
                boardScore[c1][c2]=0;
                boardScoreColour[c1][c2]=0;
                boardScoreTimer[c1][c2]=0;
            }
        }
        moveCoordinates[0]="";
        moveCoordinates[1]="";
        
        /* OPTIONAL: read board from a file
        try {
            this.Read=new BufferedReader(new FileReader("data\\board1.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(c1=0;c1<8;c1++){
            try {
                line=Read.readLine();
                boardLine=line.split(",");
                for(c2=0;c2<8;c2++){
                    board[c1][c2]=boardLine[c2];
                }
            } catch (IOException ex) {
                Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Read.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        // removes pre-generated combinations
        c4=0;
        while(true){
            for(c1=0;c1<8;c1++){
                for(c2=0;c2<6;c2++){
                    if(board[c1][c2].equals(board[c1][c2+1])&&board[c1][c2+1].equals(board[c1][c2+2])){
                        c3=(r.nextInt(6)+1);
                        board[c1][c2+1]=String.valueOf(c3);
                        c4++;
                        break;
                    }
                }
            }
            
            for(c1=0;c1<8;c1++){
                for(c2=0;c2<6;c2++){
                    if(board[c2][c1].equals(board[c2+1][c1])&&board[c2+1][c1].equals(board[c2+2][c1])){
                        c3=(r.nextInt(6)+1);
                        board[c2+1][c1]=String.valueOf(c3);
                        c4++;
                        break;
                    }
                }
            }
            
            if(c4==0){
                break;
            }else{
                c4=0;
            }
        }
        
        for(c1=0;c1<8;c1++){
            for(c2=0;c2<8;c2++){
                boardCopy[c1][c2]=board[c1][c2];
            }
        }
        
        gv.score=0;
        moves=30;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        playBackground.draw(0,0);
        
        fontMoves.drawString(935-fontMoves.getWidth(String.valueOf(moves)),62,String.valueOf(moves),Color.yellow);
        fontScore.drawString(935-fontScore.getWidth(String.valueOf(gv.score)),162,String.valueOf(gv.score),Color.yellow);
        
        // draw falling candies
        if(animation==true){
            
            // set falling times for each candy (i.e. some will fall longer than others)
            if(animationCounter==0){
                for(c1=0;c1<8;c1++){
                    for(c2=0;c2<8;c2++){
                        if(boardFallTimer[c1][c2]*60>animationMax){
                            animationMax=boardFallTimer[c1][c2]*60;
                        }
                    }
                }
            }
            
            for(c1=0;c1<8;c1++){
                for(c2=0;c2<8;c2++){
                    
                    //stops falling candies and scores
                    if(boardFallTimer[c1][c2]*60-animationCounter<=0){
                        boardFall[c1][c2]="-1";
                    }
                    if(boardScoreTimer[c1][c2]==0){
                        boardScore[c1][c2]=0;
                        boardScoreColour[c1][c2]=0;
                    }
                    
                    //draw falling candies
                    if(boardFall[c1][c2].equals("1")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy1.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("2")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy2.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("3")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy3.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("4")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy4.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("5")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy5.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("6")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy6.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("1x")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy1x.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("1y")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy1y.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("2x")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy2x.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("2y")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy2y.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("3x")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy3x.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("3y")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy3y.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("4x")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy4x.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("4y")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy4y.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("5x")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy5x.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("5y")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy5y.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("6x")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy6x.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("6y")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy6y.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].equals("7c")&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy7c.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    else if(boardFall[c1][c2].length()==2){
                        if(boardFall[c1][c2].substring(0,1).equals("1")&&(boardFall[c1][c2].substring(1,2).equals("w")||boardFall[c1][c2].substring(1,2).equals("v")||boardFall[c1][c2].substring(1,2).equals("u"))&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy1w.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                        else if(boardFall[c1][c2].substring(0,1).equals("2")&&(boardFall[c1][c2].substring(1,2).equals("w")||boardFall[c1][c2].substring(1,2).equals("v")||boardFall[c1][c2].substring(1,2).equals("u"))&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy2w.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                        else if(boardFall[c1][c2].substring(0,1).equals("3")&&(boardFall[c1][c2].substring(1,2).equals("w")||boardFall[c1][c2].substring(1,2).equals("v")||boardFall[c1][c2].substring(1,2).equals("u"))&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy3w.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                        else if(boardFall[c1][c2].substring(0,1).equals("4")&&(boardFall[c1][c2].substring(1,2).equals("w")||boardFall[c1][c2].substring(1,2).equals("v")||boardFall[c1][c2].substring(1,2).equals("u"))&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy4w.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                        else if(boardFall[c1][c2].substring(0,1).equals("5")&&(boardFall[c1][c2].substring(1,2).equals("w")||boardFall[c1][c2].substring(1,2).equals("v")||boardFall[c1][c2].substring(1,2).equals("u"))&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy5w.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                        else if(boardFall[c1][c2].substring(0,1).equals("6")&&(boardFall[c1][c2].substring(1,2).equals("w")||boardFall[c1][c2].substring(1,2).equals("v")||boardFall[c1][c2].substring(1,2).equals("u"))&&24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter)>0){candy6w.draw(103+63*c2,24+60*c1-(boardFallTimer[c1][c2]*60-animationCounter));}
                    }
                    
                    // draw board scores
                    if(boardScore[c1][c2]!=0){
                        switch (boardScoreColour[c1][c2]) {
                            case 1:
                                // if score is >=1000, draw score in smaller font
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.red);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.red);
                                }
                                break;
                            case 2:
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.orange);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.orange);
                                }
                                break;
                            case 3:
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.yellow);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.yellow);
                                }
                                break;
                            case 4:
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.green);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.green);
                                }
                                break;
                            case 5:
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.blue);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.blue);
                                }
                                break;
                            case 6:
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.magenta);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.magenta);
                                }
                                break;
                            case 7:
                                if(boardScore[c1][c2]>=1000){
                                    fontBoardScore2.drawString(130+63*c2-fontBoardScore2.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.black);
                                }else{
                                    fontBoardScore1.drawString(132+63*c2-fontBoardScore1.getWidth(String.valueOf(boardScore[c1][c2]))/2,30+60*c1,String.valueOf(boardScore[c1][c2]),Color.black);
                                }
                                break;
                            default:
                                break;
                        }
                        boardScoreTimer[c1][c2]--;
                    }
                }
            }
            
            animationCounter+=2*Math.pow(1.008,c3);
            c3++;
        }
        
        // draw stationary candies
        for(c1=0;c1<8;c1++){
            for(c2=0;c2<8;c2++){
                if(board[c1][c2].equals("1")&&boardFall[c1][c2].equals("-1")){candy1.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("2")&&boardFall[c1][c2].equals("-1")){candy2.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("3")&&boardFall[c1][c2].equals("-1")){candy3.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("4")&&boardFall[c1][c2].equals("-1")){candy4.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("5")&&boardFall[c1][c2].equals("-1")){candy5.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("6")&&boardFall[c1][c2].equals("-1")){candy6.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("1x")&&boardFall[c1][c2].equals("-1")){candy1x.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("1y")&&boardFall[c1][c2].equals("-1")){candy1y.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("2x")&&boardFall[c1][c2].equals("-1")){candy2x.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("2y")&&boardFall[c1][c2].equals("-1")){candy2y.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("3x")&&boardFall[c1][c2].equals("-1")){candy3x.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("3y")&&boardFall[c1][c2].equals("-1")){candy3y.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("4x")&&boardFall[c1][c2].equals("-1")){candy4x.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("4y")&&boardFall[c1][c2].equals("-1")){candy4y.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("5x")&&boardFall[c1][c2].equals("-1")){candy5x.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("5y")&&boardFall[c1][c2].equals("-1")){candy5y.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("6x")&&boardFall[c1][c2].equals("-1")){candy6x.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("6y")&&boardFall[c1][c2].equals("-1")){candy6y.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].equals("7c")&&boardFall[c1][c2].equals("-1")){candy7c.draw(103+63*c2,24+60*c1);}
                else if(board[c1][c2].length()==2){
                    if(board[c1][c2].substring(0,1).equals("1")&&(board[c1][c2].substring(1,2).equals("w")||board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u"))&&boardFall[c1][c2].equals("-1")){candy1w.draw(103+63*c2,24+60*c1);}
                    else if(board[c1][c2].substring(0,1).equals("2")&&(board[c1][c2].substring(1,2).equals("w")||board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u"))&&boardFall[c1][c2].equals("-1")){candy2w.draw(103+63*c2,24+60*c1);}
                    else if(board[c1][c2].substring(0,1).equals("3")&&(board[c1][c2].substring(1,2).equals("w")||board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u"))&&boardFall[c1][c2].equals("-1")){candy3w.draw(103+63*c2,24+60*c1);}
                    else if(board[c1][c2].substring(0,1).equals("4")&&(board[c1][c2].substring(1,2).equals("w")||board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u"))&&boardFall[c1][c2].equals("-1")){candy4w.draw(103+63*c2,24+60*c1);}
                    else if(board[c1][c2].substring(0,1).equals("5")&&(board[c1][c2].substring(1,2).equals("w")||board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u"))&&boardFall[c1][c2].equals("-1")){candy5w.draw(103+63*c2,24+60*c1);}
                    else if(board[c1][c2].substring(0,1).equals("6")&&(board[c1][c2].substring(1,2).equals("w")||board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u"))&&boardFall[c1][c2].equals("-1")){candy6w.draw(103+63*c2,24+60*c1);}
                }
                if(boardSelect[c1][c2]==true){selector.draw(100+63*c2,21+60*c1);}
            }
        }
        
        // stops animation to allow for player move
        if(animationCounter>=animationMax){
            for(c1=0;c1<8;c1++){
                for(c2=0;c2<8;c2++){
                    if(!boardFall[c1][c2].equals("-1")){
                        board[c1][c2]=boardFall[c1][c2];
                        boardFall[c1][c2]="-1";
                    }
                }
            }
            c3=0;
            animationCounter=0;
            animationMax=0;
            animation=false;
        }
        
        if(shuffleDelay>0){
            shuffleText.draw(156,141);
        }
        
        if(exitConfirmationOpen==true){
            exitConfirmation.draw(279,141);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input=gc.getInput();
        
        // loops music
        if(gv.music==true&&!musicPlay.playing()){
            musicPlay.play();
        }
        
        // menu button
        if(gv.mousePress==false&&input.isMouseButtonDown(0)&&input.getMouseX()>780&&input.getMouseX()<880&&input.getMouseY()>460&&input.getMouseY()<505){
            gv.mousePress=true;
            exitConfirmationOpen=true;
        }
        if(gv.mousePress==false&&exitConfirmationOpen==true&&input.isMouseButtonDown(0)&&input.getMouseX()>328&&input.getMouseX()<390&&input.getMouseY()>320&&input.getMouseY()<351){
            gv.mousePress=true;
            exitConfirmationOpen=false;
            game.enterState(0,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
        }
        if(gv.mousePress==false&&exitConfirmationOpen==true&&input.isMouseButtonDown(0)&&input.getMouseX()>511&&input.getMouseX()<635&&input.getMouseY()>321&&input.getMouseY()<351){
            gv.mousePress=true;
            exitConfirmationOpen=false;
        }
        
        
        // will run if not animating and no exit confirmation box open
        if(animation==false&&exitConfirmationOpen==false){
            
            // checks if falling candies created a consecutive connection
            connection=checkConnection(c1,c2,c3,c4,x1,y1,x2,y2,connection,moveCoordinates,board);
            
            if(connection==true){
                
                // 20 frame delay before combinations are removed
                removeDelay++;
                if(removeDelay==20){
                    removeConnection(c1,c2,c3,c4,x1,y1,x2,y2,consecutive,combo,intersect,hold,colourHold,moveCoordinates,boardScore,boardScoreColour,boardScoreTimer,board,boardCopy,boardRemove,boardFallCopy,removeCoordinates,specialRemoveCoordinates,specialCreationCoordinates,intersectCoordinates,wvCoordinates);
                    fall(c1,c2,c3,c4,noneAbove,boardFallTimer,board,boardFall,removeCoordinates);
                    generate(c1,c2,c3,c4,c5,board,boardFall,boardFallCopy);
                    combo++;
                    colourHold="0";
                    animation=true;
                    removeDelay=0;
                }
            }
            
            if(connection==false){
                // checks after any move is finished for end of game or no possible moves on board
                combo=1;
                
                // if no more moves
                if(moves==0){
                    gameFinish=true;
                    
                    // checks for remaining special candies
                    for(c1=0;c1<8;c1++){
                        for(c2=0;c2<8;c2++){
                            if(board[c1][c2].length()==2){
                                gameFinish=false;
                            }
                        }
                    }
                    
                    // detects leftover special candies and adds them to queue
                    if(removeCoordinates.isEmpty()){
                        for(c1=0;c1<3;c1++){
                            for(c2=0;c2<8;c2++){
                                for(c3=0;c3<8;c3++){
                                    if(board[c2][c3].length()==2){

                                        // adds special candies one by one, striped is first, wrapped is second, colour bomb is last
                                        if(c1==0){
                                            if(board[c2][c3].substring(1,2).equals("x")||board[c2][c3].substring(1,2).equals("y")){
                                                removeCoordinates.add(c2+","+c3);
                                                boardRemove[c2][c3]=board[c2][c3].substring(0,1);
                                                break;
                                            }
                                        }else if(c1==1){
                                            if(board[c2][c3].substring(1,2).equals("w")){
                                                removeCoordinates.add(c2+","+c3);
                                                break;
                                            }
                                        }else if(c1==2){
                                            if(board[c2][c3].substring(1,2).equals("c")){
                                                removeCoordinates.add(c2+","+c3);
                                                boardRemove[c2][c3]=board[c2][c3].substring(0,1);
                                                break;
                                            }
                                        }
                                    }
                                }
                                if(removeCoordinates.size()==1){
                                    break;
                                }
                            }
                            if(removeCoordinates.size()==1){
                                break;
                            }
                        }
                    }
                    
                    // ends game
                    if(gameFinish==true){
                        // checks if high score achieved
                        if(gv.score>=gv.scoreSaved[9]){
                            gv.highScore=true;
                        }
                        if(gv.music==true){
                            musicPlay.stop();
                            musicHighScore.play();
                        }
                        game.enterState(2,new FadeOutTransition(Color.black),new FadeInTransition(Color.black));
                    }
                    // if game isn't finished, delay until leftover special candies are removed
                    else{
                        removeDelay++;

                        if(removeDelay==20){
                            removeConnection(c1,c2,c3,c4,x1,y1,x2,y2,consecutive,combo,intersect,hold,colourHold,moveCoordinates,boardScore,boardScoreColour,boardScoreTimer,board,boardCopy,boardRemove,boardFallCopy,removeCoordinates,specialRemoveCoordinates,specialCreationCoordinates,intersectCoordinates,wvCoordinates);
                            fall(c1,c2,c3,c4,noneAbove,boardFallTimer,board,boardFall,removeCoordinates);
                            generate(c1,c2,c3,c4,c5,board,boardFall,boardFallCopy);
                            combo++;
                            colourHold="0";
                            animation=true;
                            removeDelay=0;
                        }
                    }
                }

                // checks for no possible moves on board
                movePossible=true;
                movePossible=checkMove(c1,c2,c3,c4,movePossible,connection,hold,moveCoordinates,board);
                if(movePossible==false){
                    shuffleDelay++;
                    if(shuffleDelay==60){
                        shuffle(c1,y1,x1,y2,x2,connection,hold,moveCoordinates,board);
                        shuffleDelay=0;
                    }
                }
                
                // player selection of candy
                for(c1=0;c1<8;c1++){
                    for(c2=0;c2<8;c2++){
                        
                        // initial selection
                        if(gv.mousePress==false&&select==false&&input.isMouseButtonDown(0)&&input.getMouseX()>103+63*c2&&input.getMouseX()<166+63*c2&&input.getMouseY()>24+60*c1&&input.getMouseY()<84+60*c1){
                            boardSelect[c1][c2]=true;
                            y1=c1;
                            x1=c2;
                            select=true;
                        }
                        // second selection
                        else if(gv.mousePress==false&&select==true&&input.isMouseButtonDown(0)&&input.getMouseX()>103+63*c2&&input.getMouseX()<166+63*c2&&input.getMouseY()>24+60*c1&&input.getMouseY()<84+60*c1){
                            
                            // if adjacent candy has already been selected, then swap candies regardless of whether or not valid move
                            y2=c1;
                            x2=c2;
                            hold=board[c1][c2];
                            
                            if(c1<7){
                                if(boardSelect[c1+1][c2]==true){
                                    moveCoordinates[0]=String.valueOf(c1+1)+","+c2;
                                    board[c1][c2]=board[c1+1][c2];
                                    board[c1+1][c2]=hold;
                                    move=true;
                                }
                            }
                            if(c1>0){
                                if(boardSelect[c1-1][c2]==true){
                                    moveCoordinates[0]=String.valueOf(c1-1)+","+c2;
                                    board[c1][c2]=board[c1-1][c2];
                                    board[c1-1][c2]=hold;
                                    move=true;
                                }
                            }
                            if(c2<7){
                                if(boardSelect[c1][c2+1]==true){
                                    moveCoordinates[0]=c1+","+String.valueOf(c2+1);
                                    board[c1][c2]=board[c1][c2+1];
                                    board[c1][c2+1]=hold;
                                    move=true;
                                }
                            }
                            if(c2>0){
                                if(boardSelect[c1][c2-1]==true){
                                    moveCoordinates[0]=c1+","+String.valueOf(c2-1);
                                    board[c1][c2]=board[c1][c2-1];
                                    board[c1][c2-1]=hold;
                                    move=true;
                                }
                            }
                            
                            moveCoordinates[1]=y2+","+x2;
                            boardSelect[y1][x1]=false;
                            select=false;
                            
                            connection=checkConnection(c1,c2,c3,c4,x1,y1,x2,y2,connection,moveCoordinates,board);
                            
                            // if move made
                            if(move==true){
                                // if not valid move, swap candies back, else candies stay
                                if(connection==false){
                                    hold=board[y2][x2];
                                    board[y2][x2]=board[y1][x1];
                                    board[y1][x1]=hold;
                                }else{
                                    moves--;
                                }
                                move=false;
                            }
                        }
                    }
                }
            }
        }
        
        connection=false;
        
        gv.mousePress=input.isMouseButtonDown(0);
    }

public static boolean checkConnection(int c1,int c2,int c3,int c4,int x1,int y1,int x2,int y2,boolean connection,String[] moveCoordinates,String[][] board){
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            
            // checks horizontal connection
            if(c2<6&&board[c1][c2].substring(0,1).equals(board[c1][c2+1].substring(0,1))&&board[c1][c2+1].substring(0,1).equals(board[c1][c2+2].substring(0,1))){
                connection=true;
            }
            
            // checks vertical connection
            if(c2<6&&board[c2][c1].substring(0,1).equals(board[c2+1][c1].substring(0,1))&&board[c2+1][c1].substring(0,1).equals(board[c2+2][c1].substring(0,1))){
                connection=true;
            }
            
            // if there are wrapped candies that have already exploded once
            if(board[c1][c2].length()>1){
                if(board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u")){
                    connection=true;
                }
            }
        }
    }
    
    // if move involved colour bomb or two special candies
    if(!moveCoordinates[0].equals("")&&!moveCoordinates[1].equals("")){
        y1=Integer.parseInt(moveCoordinates[0].substring(0,1));
        x1=Integer.parseInt(moveCoordinates[0].substring(2,3));
        y2=Integer.parseInt(moveCoordinates[1].substring(0,1));
        x2=Integer.parseInt(moveCoordinates[1].substring(2,3));
        if(board[y1][x1].equals("7c")||board[y2][x2].equals("7c")){
            connection=true;
        }
        if(board[y1][x1].length()==2&&board[y2][x2].length()==2){
            connection=true;
        }
    }
    
    return connection;
}

// removes connections, activates special candies, and creates new special candies
public static void removeConnection(int c1,int c2,int c3,int c4,int x1,int y1,int x2,int y2,int consecutive,int combo,boolean intersect,String hold,String colourHold,String[] moveCoordinates,int[][] boardScore,int[][] boardScoreColour,int[][] boardScoreTimer,String[][] board,String[][] boardCopy,String[][] boardRemove,String[][] boardFallCopy,ArrayList<String> removeCoordinates,ArrayList<String> specialRemoveCoordinates,ArrayList<String> specialCreationCoordinates,ArrayList<String> intersectCoordinates,ArrayList<String> wvCoordinates){
    int holdSize=0;
    
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            boardCopy[c1][c2]=board[c1][c2];
        }
    }
    
    // horizontal marking of combinations
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<6;c2++){
            hold=board[c1][c2].substring(0,1);
            for(c3=c2;c3<8;c3++){
                if(board[c1][c3].substring(0,1).equals(hold)||boardRemove[c1][c3].equals(hold)){
                    removeCoordinates.add(c1+","+c3);
                    consecutive++;
                }else{
                    break;
                }
            }
            
            // if connection is valid (3 or more)
            if(consecutive>=3){
                c2+=consecutive-1;
                
                // adds coordinates to boardRemove
                for(c3=removeCoordinates.size()-consecutive;c3<removeCoordinates.size();c3++){
                    y1=Integer.parseInt(removeCoordinates.get(c3).substring(0,1));
                    x1=Integer.parseInt(removeCoordinates.get(c3).substring(2,3));
                    boardRemove[y1][x1]=board[y1][x1].substring(0,1);
                    if(board[y1][x1].length()>1){
                        boardScore[y1][x1]=300*combo;
                    }else{
                        boardScore[y1][x1]=60*combo;
                    }
                }
            }
            // else remove the coordinates
            else{
                holdSize=removeCoordinates.size();
                for(c3=removeCoordinates.size()-1;c3>holdSize-1-consecutive;c3--){
                    removeCoordinates.remove(c3);
                }
            }
            
            consecutive=0;
        }
    }
    
    // vertical marking of combinations
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<6;c2++){
            hold=board[c2][c1].substring(0,1);
            for(c3=c2;c3<8;c3++){
                if(board[c3][c1].substring(0,1).equals(hold)||boardRemove[c3][c1].equals(hold)){
                    // if boardRemove coordinate equals hold, an intersection may occur
                    if(boardRemove[c3][c1].substring(0,1).equals(hold)){
                        y2=c3;
                        x2=c1;
                        intersect=true;
                    }else{
                        removeCoordinates.add(c3+","+c1);
                        consecutive++;
                    }
                }else{
                    break;
                }
            }
            
            // if connection is valid (3 or more) or there is an intersecting shape (2 or more)
            if(consecutive>=3||(intersect==true&&consecutive>=2)){
                c2+=consecutive-1;
                
                // adds intersecting coordinates (to be used to create wrapped candy)
                if(intersect==true){
                    // if statement is used to ensure no secondary wrapped candies can create another wrapped candy
                    if(board[y2][x2].length()==2){
                        if(!board[y2][x2].substring(1,2).equals("v")){
                            intersectCoordinates.add(y2+","+x2);
                        }
                    }else{
                        intersectCoordinates.add(y2+","+x2);
                    }
                }
                
                // adds coordinates to boardRemove
                for(c3=removeCoordinates.size()-consecutive;c3<removeCoordinates.size();c3++){
                    y1=Integer.parseInt(removeCoordinates.get(c3).substring(0,1));
                    x1=Integer.parseInt(removeCoordinates.get(c3).substring(2,3));
                    boardRemove[y1][x1]=board[y1][x1].substring(0,1);
                    if(board[y1][x1].length()>1){
                        boardScore[y1][x1]=300*combo;
                    }else{
                        boardScore[y1][x1]=60*combo;
                    }
                }
            }
            // else remove the coordinates
            else{
                holdSize=removeCoordinates.size();
                for(c3=removeCoordinates.size()-1;c3>holdSize-1-consecutive;c3--){
                    removeCoordinates.remove(c3);
                }
            }
            
            intersect=false;
            consecutive=0;
        }
    }
    
    // adds wrapped candies that have already exploded once to queue
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            if(board[c1][c2].length()>1){
                if(board[c1][c2].substring(1,2).equals("v")||board[c1][c2].substring(1,2).equals("u")){
                    removeCoordinates.add(c1+","+c2);
                    boardRemove[c1][c2]=board[c1][c2].substring(0,1);
                    boardScore[c1][c2]=300*(combo-1);
                }
            }
        }
    }
    
    // checks for colour bomb used, or special candy swapped with special candy
    // if not a collateral combination (i.e. in collateral combinations no colour bombs are used and no special candies are used with another special candy)
    if(!moveCoordinates[0].equals("")&&!moveCoordinates[1].equals("")){
        
        // coordinates that the player used
        y1=Integer.parseInt(moveCoordinates[0].substring(0,1));
        x1=Integer.parseInt(moveCoordinates[0].substring(2,3));
        y2=Integer.parseInt(moveCoordinates[1].substring(0,1));
        x2=Integer.parseInt(moveCoordinates[1].substring(2,3));
        
        // colour bomb swapped with a regular candy
        if((board[y1][x1].equals("7c")&&board[y2][x2].length()==1)|(board[y2][x2].equals("7c")&&board[y1][x1].length()==1)){
            
            // adds colour bomb to remove queue, holds colour that colour bomb was swapped with
            if(board[y1][x1].equals("7c")){
                removeCoordinates.add(y1+","+x1);
                boardRemove[y1][x1]=board[y1][x1].substring(0,1);
                colourHold=board[y2][x2].substring(0,1);
            }else{
                removeCoordinates.add(y2+","+x2);
                boardRemove[y2][x2]=board[y2][x2].substring(0,1);
                colourHold=board[y1][x1].substring(0,1);
            }
        }
        // else if special candy swapped with special candy
        else if(board[y1][x1].length()>1&&board[y2][x2].length()>1){
            // colour bomb swapped with colour bomb
            if(board[y1][x1].equals("7c")&&board[y2][x2].equals("7c")){
                
                // adds every candy on board to queue
                for(c1=0;c1<8;c1++){
                    for(c2=0;c2<8;c2++){
                        specialRemoveCoordinates.add(c1+","+c2);
                    }
                }
            }
            // wrapped candy swapped with wrapped candy
            else if(board[y1][x1].substring(1,2).equals("w")&&board[y2][x2].substring(1,2).equals("w")){
                
                // changes wrapped candy ("w" suffix) to "u" suffix to indicate a 5x5 explosion
                board[y1][x1]=board[y1][x1].substring(0,1)+"u";
                board[y2][x2]=board[y2][x2].substring(0,1)+"u";
                
                boardScore[y1][x1]=300;
                boardScore[y2][x2]=300;
                
                removeCoordinates.add(y1+","+x1);
                removeCoordinates.add(y2+","+x2);
            }
            // striped candy swapped with striped candy
            else if((board[y1][x1].substring(1,2).equals("x")||board[y1][x1].substring(1,2).equals("y"))&&(board[y2][x2].substring(1,2).equals("x")||board[y2][x2].substring(1,2).equals("y"))){
                
                // if the stripes are the same, change one to another orientation
                if(board[y1][x1].substring(1,2).equals("x")&&board[y2][x2].substring(1,2).equals("x")){
                    board[y2][x2]=board[y2][x2].substring(0,1)+"y";
                }else if(board[y1][x1].substring(1,2).equals("y")&&board[y2][x2].substring(1,2).equals("y")){
                    board[y1][x1]=board[y1][x1].substring(0,1)+"x";
                }
                
                removeCoordinates.add(y1+","+x1);
                removeCoordinates.add(y2+","+x2);
                boardRemove[y1][x1]=board[y1][x1].substring(0,1);
                boardRemove[y2][x2]=board[y2][x2].substring(0,1);
            }
            // colour bomb swapped with striped candy
            else if((board[y1][x1].equals("7c")&&(board[y2][x2].substring(1,2).equals("x")||board[y2][x2].substring(1,2).equals("y")))||(board[y2][x2].equals("7c")&&(board[y1][x1].substring(1,2).equals("x")||board[y1][x1].substring(1,2).equals("y")))){
                
                // holds colour that colour bomb was swapped with
                if(board[y1][x1].equals("7c")){
                    colourHold=board[y2][x2].substring(0,1);
                }else{
                    colourHold=board[y1][x1].substring(0,1);
                }
                
                specialRemoveCoordinates.add(y1+","+x1);
                specialRemoveCoordinates.add(y2+","+x2);
                
                // changes all normal candies of same colour to striped candy
                for(c1=0;c1<8;c1++){
                    for(c2=0;c2<8;c2++){
                        // checks for normal candy, correct colour, and not the candy it was swapped with
                        if(board[c1][c2].length()==1&&board[c1][c2].substring(0,1).equals(colourHold)&&!(c1==y1&&c2==x1||c1==y2&&c2==x2)){
                            // alternates stripes
                            if(c3%2==0){
                                board[c1][c2]+="x";
                            }else{
                                board[c1][c2]+="y";
                            }
                            removeCoordinates.add(c1+","+c2);
                            boardRemove[c1][c2]=board[c1][c2].substring(0,1);
                            c3++;
                        }
                        // else if correct colour but not normal candy, adds to remove queue
                        else if(board[c1][c2].substring(0,1).equals(colourHold)){
                            removeCoordinates.add(c1+","+c2);
                        }
                    }
                }
                c3=0;
                colourHold="0";
            }
            // colour bomb with wrapped candy
            else if((board[y1][x1].equals("7c")&&board[y2][x2].substring(1,2).equals("w"))||(board[y2][x2].equals("7c")&&board[y1][x1].substring(1,2).equals("w"))){
                
                // adds colour bomb to remove queue, holds colour that colour bomb was swapped with
                if(board[y1][x1].equals("7c")){
                    colourHold=board[y2][x2].substring(0,1);
                    board[y2][x2]="0";
                    boardRemove[y2][x2]=board[y2][x2].substring(0,1);
                    boardScore[y2][x2]=300;
                    specialRemoveCoordinates.add(y1+","+x1);
                }else{
                    colourHold=board[y1][x1].substring(0,1);
                    board[y1][x1]="0";
                    boardRemove[y1][x1]=board[y1][x1].substring(0,1);
                    boardScore[y1][x1]=300;
                    specialRemoveCoordinates.add(y2+","+x2);
                }
                
                // changes all normal candies of same colour to wrapped candy, then adds to remove queue
                for(c1=0;c1<8;c1++){
                    for(c2=0;c2<8;c2++){
                        // checks for normal candy, correct colour, and not the candy it was swapped with
                        if(board[c1][c2].length()==1&&board[c1][c2].substring(0,1).equals(colourHold)&&!(c1==y1&&c2==x1||c1==y2&&c2==x2)){
                            board[c1][c2]+="w";
                            removeCoordinates.add(c1+","+c2);
                            boardRemove[c1][c2]=board[c1][c2].substring(0,1);
                        }
                        // else if correct colour but not normal candy, adds to remove queue
                        else if(board[c1][c2].substring(0,1).equals(colourHold)){
                            removeCoordinates.add(c1+","+c2);
                        }
                    }
                }
                colourHold="0";
            }
            // wrapped candy with striped candy
            else if((board[y1][x1].substring(1,2).equals("w")&&(board[y2][x2].substring(1,2).equals("x")||board[y2][x2].substring(1,2).equals("y")))||(board[y2][x2].substring(1,2).equals("w")&&(board[y1][x1].substring(1,2).equals("x")||board[y1][x1].substring(1,2).equals("y")))){
                specialRemoveCoordinates.add(y1+","+x1);
                specialRemoveCoordinates.add(y2+","+x2);
                
                for(c1=0;c1<8;c1++){
                    for(c2=0;c2<8;c2++){
                        
                        // checks for which coordinate the player swapped with actually has the striped candy
                        if(board[y1][x1].substring(1,2).equals("x")||board[y1][x1].substring(1,2).equals("y")){
                            
                            // removes 3 cell wide column and row centering on striped candy
                            if((c1>=y1-1&&c1<=y1+1)||(c2>=x1-1&&c2<=x1+1)&&!(c1==y1&&c2==x1||c1==y2&&c2==x2)){
                                removeCoordinates.add(c1+","+c2);
                                boardRemove[c1][c2]=board[c1][c2].substring(0,1);
                            }
                        }else{
                            if((c1>=y2-1&&c1<=y2+1)||(c2>=x2-1&&c2<=x2+1)&&!(c1==y1&&c2==x1||c1==y2&&c2==x2)){
                                removeCoordinates.add(c1+","+c2);
                                boardRemove[c1][c2]=board[c1][c2].substring(0,1);
                            }
                        }
                    }
                }
            }
        }
    }
    
    // goes through candies to be removed and checks for special candies
    for(c1=0;c1<removeCoordinates.size();c1++){
        y1=Integer.parseInt(removeCoordinates.get(c1).substring(0,1));
        x1=Integer.parseInt(removeCoordinates.get(c1).substring(2,3));
        if(board[y1][x1].length()>1){
            switch (board[y1][x1].substring(1,2)) {
                case "x":
                case "y":
                    xyCandy(c2,c3,y1,x1,board,specialRemoveCoordinates,wvCoordinates);
                    break;
                case "w":
                    wvCoordinates.add(y1+","+x1);
                    wCandy(c2,c3,y1,x1,board,specialRemoveCoordinates,wvCoordinates,false);
                    break;
                case "v":
                    wCandy(c2,c3,y1,x1,board,specialRemoveCoordinates,wvCoordinates,false);
                    break;
                case "u":
                    wCandy(c2,c3,y1,x1,board,specialRemoveCoordinates,wvCoordinates,true);
                    break;
                case "c":
                    cCandy(c2,c3,y1,x1,colourHold,board,specialRemoveCoordinates,wvCoordinates);
                    break;
            }
        }
    }
    
    // removes candies affected by special candies
    for(c1=specialRemoveCoordinates.size()-1;c1>=0;c1--){
        y1=Integer.parseInt(specialRemoveCoordinates.get(c1).substring(0,1));
        x1=Integer.parseInt(specialRemoveCoordinates.get(c1).substring(2,3));
        boardRemove[y1][x1]=board[y1][x1].substring(0,1);
        specialRemoveCoordinates.remove(c1);
    }
    
    // creates colour bomb
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            
            // checks horizontal connection
            if(c2<6&&!boardRemove[c1][c2].equals("0")){
                hold=boardRemove[c1][c2];
                for(c3=c2;c3<8;c3++){
                    if(boardRemove[c1][c3].equals(hold)){
                        // if statement is used to ensure no secondary wrapped candies can create a colour bomb
                        if(board[c1][c3].length()==2){
                            if(!board[c1][c3].substring(1,2).equals("v")){
                                specialCreationCoordinates.add(c1+","+c3);
                                consecutive++;
                            }
                        }else{
                            specialCreationCoordinates.add(c1+","+c3);
                            consecutive++;
                        }
                    }else{
                        break;
                    }
                }
                
                // creates colour bomb in middle
                if(consecutive>=5){
                    y1=Integer.parseInt(specialCreationCoordinates.get(2).substring(0,1));
                    x1=Integer.parseInt(specialCreationCoordinates.get(2).substring(2,3));
                    board[y1][x1]="7c";
                    boardRemove[y1][x1]="0";
                }

                for(c3=specialCreationCoordinates.size()-1;c3>=0;c3--){
                    specialCreationCoordinates.remove(c3);
                }

                consecutive=0;
            }
            
            // checks vertical connection
            if(c2<6&&!boardRemove[c2][c1].equals("0")){
                hold=boardRemove[c2][c1];
                for(c3=c2;c3<8;c3++){
                    if(boardRemove[c3][c1].equals(hold)){
                        // if statement is used to ensure no secondary wrapped candies can create a colour bomb
                        if(board[c3][c1].length()==2){
                            if(!board[c3][c1].substring(1,2).equals("v")){
                                specialCreationCoordinates.add(c3+","+c1);
                                consecutive++;
                            }
                        }else{
                            specialCreationCoordinates.add(c3+","+c1);
                            consecutive++;
                        }
                    }else{
                        break;
                    }
                }

                if(consecutive>=5){
                    y1=Integer.parseInt(specialCreationCoordinates.get(2).substring(0,1));
                    x1=Integer.parseInt(specialCreationCoordinates.get(2).substring(2,3));
                    board[y1][x1]="7c";
                    boardRemove[y1][x1]="0";
                }

                for(c3=specialCreationCoordinates.size()-1;c3>=0;c3--){
                    specialCreationCoordinates.remove(c3);
                }

                consecutive=0;
            }
        }
    }
    
    // creates wrapped candy
    c1=0;
    while(c1<intersectCoordinates.size()){
        y1=Integer.parseInt(intersectCoordinates.get(c1).substring(0,1));
        x1=Integer.parseInt(intersectCoordinates.get(c1).substring(2,3));
        if(!board[y1][x1].equals("7c")){
            board[y1][x1]=board[y1][x1].substring(0,1)+"w";
            boardRemove[y1][x1]="0";
        }
        intersectCoordinates.remove(c1);
    }
    
    // creates striped candy
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            
            // checks horizontal connections
            if(c2<6&&!boardRemove[c1][c2].equals("0")){
                hold=boardRemove[c1][c2];
                for(c3=c2;c3<8;c3++){
                    if(boardRemove[c1][c3].equals(hold)){
                        // if statement is used to ensure no secondary wrapped candies can create a striped candy
                        if(board[c1][c3].length()==2){
                            if(!board[c1][c3].substring(1,2).equals("v")){
                                specialCreationCoordinates.add(c1+","+c3);
                                consecutive++;
                            }
                        }else{
                            specialCreationCoordinates.add(c1+","+c3);
                            consecutive++;
                        }
                    }else{
                        break;
                    }
                }
                
                if(consecutive==4){
                    y1=Integer.parseInt(specialCreationCoordinates.get(1).substring(0,1));
                    x1=Integer.parseInt(specialCreationCoordinates.get(1).substring(2,3));
                    y2=Integer.parseInt(specialCreationCoordinates.get(2).substring(0,1));
                    x2=Integer.parseInt(specialCreationCoordinates.get(2).substring(2,3));
                    
                    // creates a striped candy where the player swapped 2 candies
                    if(specialCreationCoordinates.get(1).equals(moveCoordinates[0])||specialCreationCoordinates.get(1).equals(moveCoordinates[1])){
                        board[y1][x1]=board[y1][x1].substring(0,1)+"y";
                        boardRemove[y1][x1]="0";
                    }else if(specialCreationCoordinates.get(2).equals(moveCoordinates[0])||specialCreationCoordinates.get(2).equals(moveCoordinates[1])){
                        board[y2][x2]=board[y2][x2].substring(0,1)+"y";
                        boardRemove[y2][x2]="0";
                    }
                    // else in case of a collateral combination, creates a striped candy where the candies were falling
                    else if(!boardFallCopy[y1][x1].equals("-1")){
                        board[y1][x1]=board[y1][x1].substring(0,1)+"y";
                        boardRemove[y1][x1]="0";
                    }else if(!boardFallCopy[y2][x2].equals("-1")){
                        board[y2][x2]=board[y2][x2].substring(0,1)+"y";
                        boardRemove[y2][x2]="0";
                    }
                }

                for(c3=specialCreationCoordinates.size()-1;c3>=0;c3--){
                    specialCreationCoordinates.remove(c3);
                }

                consecutive=0;
            }
            
            // checks vertical connections
            if(c2<6&&!boardRemove[c2][c1].equals("0")){
                hold=boardRemove[c2][c1];
                for(c3=c2;c3<8;c3++){
                    if(boardRemove[c3][c1].equals(hold)){
                        // if statement is used to ensure no secondary wrapped candies can create a striped candy
                        if(board[c3][c1].length()==2){
                            if(!board[c3][c1].substring(1,2).equals("v")){
                                specialCreationCoordinates.add(c3+","+c1);
                                consecutive++;
                            }
                        }else{
                            specialCreationCoordinates.add(c3+","+c1);
                            consecutive++;
                        }
                    }else{
                        break;
                    }
                }
                
                if(consecutive==4){
                    y1=Integer.parseInt(specialCreationCoordinates.get(1).substring(0,1));
                    x1=Integer.parseInt(specialCreationCoordinates.get(1).substring(2,3));
                    y2=Integer.parseInt(specialCreationCoordinates.get(2).substring(0,1));
                    x2=Integer.parseInt(specialCreationCoordinates.get(2).substring(2,3));
                    if(specialCreationCoordinates.get(1).equals(moveCoordinates[0])||specialCreationCoordinates.get(1).equals(moveCoordinates[1])){
                        board[y1][x1]=board[y1][x1].substring(0,1)+"x";
                        boardRemove[y1][x1]="0";
                    }else if(specialCreationCoordinates.get(2).equals(moveCoordinates[0])||specialCreationCoordinates.get(2).equals(moveCoordinates[1])){
                        board[y2][x2]=board[y2][x2].substring(0,1)+"x";
                        boardRemove[y2][x2]="0";
                    }else if(!boardFallCopy[y1][x1].equals("-1")){
                        board[y1][x1]=board[y1][x1].substring(0,1)+"x";
                        boardRemove[y1][x1]="0";
                    }else if(!boardFallCopy[y2][x2].equals("-1")){
                        board[y2][x2]=board[y2][x2].substring(0,1)+"x";
                        boardRemove[y2][x2]="0";
                    }
                }

                for(c3=specialCreationCoordinates.size()-1;c3>=0;c3--){
                    specialCreationCoordinates.remove(c3);
                }

                consecutive=0;
            }
        }
    }
    
    // changes activated wrapped candy to secondary wrapped candy
    for(c1=wvCoordinates.size()-1;c1>=0;c1--){
        y1=Integer.parseInt(wvCoordinates.get(c1).substring(0,1));
        x1=Integer.parseInt(wvCoordinates.get(c1).substring(2,3));
        board[y1][x1]=board[y1][x1].substring(0,1)+"v";
        boardRemove[y1][x1]="0";
        boardScore[y1][x1]=300*combo;
        wvCoordinates.remove(c1);
    }
    
    moveCoordinates[0]="";
    moveCoordinates[1]="";
    
    for(c1=removeCoordinates.size()-1;c1>=0;c1--){
        removeCoordinates.remove(c1);
    }
    
    // sets scores and removes combinations on board
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            if(boardRemove[c1][c2]!="0"){
                if(boardCopy[c1][c2].length()==1){
                    boardScore[c1][c2]=60*combo;
                }else if(boardCopy[c1][c2].substring(1,2).equals("v")||boardCopy[c1][c2].substring(1,2).equals("u")){
                    boardScore[c1][c2]=300*(combo-1);
                }else{
                    boardScore[c1][c2]=300*combo;
                }
            }
            if(boardScore[c1][c2]!=0){
                boardScoreColour[c1][c2]=Integer.parseInt(boardCopy[c1][c2].substring(0,1));
                boardScoreTimer[c1][c2]=20;
            }
            if(!boardRemove[c1][c2].equals("0")){
                board[c1][c2]="0";
                boardRemove[c1][c2]="0";
            }
            
            gv.score+=boardScore[c1][c2];
        }
    }
}

// striped candy effect
public static void xyCandy(int c2,int c3,int y1,int x1,String[][] board,ArrayList<String> specialRemoveCoordinates,ArrayList<String> wvCoordinates){
    Random r=new Random();
    
    // horizontal stripe
    if(board[y1][x1].substring(1,2).equals("x")){
        for(c2=0;c2<8;c2++){
            if(c2!=x1&&specialRemoveCoordinates.indexOf(y1+","+c2)==-1&&wvCoordinates.indexOf(c2+","+c3)==-1){
                specialRemoveCoordinates.add(y1+","+c2);
                if(board[y1][c2].length()>1){
                    
                    // if stripe sets off another special candy
                    switch (board[y1][c2].substring(1,2)) {
                        case "x":
                        case "y":
                            xyCandy(c2,0,y1,c2,board,specialRemoveCoordinates,wvCoordinates);
                            break;
                        case "w":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                            wvCoordinates.add(y1+","+c2);
                            wCandy(c2,c3,y1,c2,board,specialRemoveCoordinates,wvCoordinates,false);
                            break;
                        case "c":
                            cCandy(c2,c3,y1,c2,String.valueOf(r.nextInt(6)+1),board,specialRemoveCoordinates,wvCoordinates);
                            break;
                        case "v":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                        case "u":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                    }
                }
            }
        }
    }
    
    // vertical stripe
    else{
        for(c2=0;c2<8;c2++){
            if(c2!=y1&&specialRemoveCoordinates.indexOf(c2+","+x1)==-1&&wvCoordinates.indexOf(c2+","+c3)==-1){
                specialRemoveCoordinates.add(c2+","+x1);
                if(board[c2][x1].length()>1){
                    switch (board[c2][x1].substring(1,2)) {
                        case "x":
                        case "y":
                            xyCandy(c2,0,c2,x1,board,specialRemoveCoordinates,wvCoordinates);
                            break;
                        case "w":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                            wvCoordinates.add(c2+","+x1);
                            wCandy(c2,c3,c2,x1,board,specialRemoveCoordinates,wvCoordinates,false);
                            break;
                        case "c":
                            cCandy(c2,c3,c2,x1,String.valueOf(r.nextInt(6)+1),board,specialRemoveCoordinates,wvCoordinates);
                            break;
                        case "v":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                            break;
                        case "u":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                            break;
                    }
                }
            }
        }
    }
}

// wrapped candy effect
public static void wCandy(int c2,int c3,int y1,int x1,String[][] board,ArrayList<String> specialRemoveCoordinates,ArrayList<String> wvCoordinates,boolean ww){
    Random r=new Random();
    
    for(c2=y1-2;c2<=y1+2;c2++){
        
        // adjusts c2 to a 3x3 block if a wrapped candy was not swapped with a wrapped candy
        if(ww==false){
            if(c2==y1-2){
                c2++;
            }
            if(c2==y1+2){
                break;
            }
        }
        
        if(c2<0){
            c2=0;
        }
        
        for(c3=x1-2;c3<=x1+2;c3++){
            
            // adjusts c3 to a 3x3 block if a wrapped candy was not swapped with a wrapped candy
            if(ww==false){
                if(c3==x1-2){
                    c3++;
                }
                if(c3==x1+2){
                    break;
                }
            }

            if(c3<0){
                c3=0;
            }
            
            if(c2<8&&c3<8){
                if(!(c2+","+c3).equals(y1+","+x1)&&specialRemoveCoordinates.indexOf(c2+","+c3)==-1&&wvCoordinates.indexOf(c2+","+c3)==-1){
                    specialRemoveCoordinates.add(c2+","+c3);
                    if(board[c2][c3].length()>1){
                        switch (board[c2][c3].substring(1,2)) {
                            case "x":
                            case "y":
                                xyCandy(c2,c3,c2,c3,board,specialRemoveCoordinates,wvCoordinates);
                                break;
                            case "w":
                                specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                                wvCoordinates.add(c2+","+c3);
                                wCandy(c2,c3,c2,c3,board,specialRemoveCoordinates,wvCoordinates,false);
                                break;
                            case "c":
                                cCandy(c2,c3,c2,c3,String.valueOf(r.nextInt(6)+1),board,specialRemoveCoordinates,wvCoordinates);
                                break;
                            case "v":
                                specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                                break;
                            case "u":
                                specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                                break;
                        }
                    }
                }
            }
        }
    }
}

// colour bomb effect
public static void cCandy(int c2,int c3,int y1,int x1,String colourHold,String[][] board,ArrayList<String> specialRemoveCoordinates,ArrayList<String> wvCoordinates){
    Random r=new Random();
    
    // if colour bomb was activated as collateral, choose a random colour to eliminate
    if(colourHold.equals("0")){
        colourHold=String.valueOf(r.nextInt(6)+1);
    }
    
    for(c2=0;c2<8;c2++){
        for(c3=0;c3<8;c3++){
            if(board[c2][c3].substring(0,1).equals(colourHold)&&!(c2+","+c3).equals(y1+","+x1)&&specialRemoveCoordinates.indexOf(c2+","+c3)==-1){
                specialRemoveCoordinates.add(c2+","+c3);
                if(board[c2][c3].length()>1){
                    switch (board[c2][c3].substring(1,2)){
                        case "x":
                        case "y":
                            xyCandy(c2,c3,c2,c3,board,specialRemoveCoordinates,wvCoordinates);
                            break;
                        case "w":
                            specialRemoveCoordinates.remove(specialRemoveCoordinates.size()-1);
                            wvCoordinates.add(c2+","+c3);
                            wCandy(c2,c3,c2,c3,board,specialRemoveCoordinates,wvCoordinates,false);
                            break;
                    }
                }
            }
        }
    }
}

// candies fall
public static void fall(int c1,int c2,int c3,int c4,boolean noneAbove,int[][] boardFallTimer,String[][] board,String[][] boardFall,ArrayList<String> removeCoordinates){
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            boardFallTimer[c1][c2]=0;
        }
    }
    
    noneAbove=false;
    for(c1=0;c1<8;c1++){
        for(c2=7;c2>0;c2--){
            
            // moves up cells in a column
            while(board[c2][c1].equals("0")){
                noneAbove=true;
                
                // if there is an empty space in the column, therefore candies will fall
                for(c3=c2;c3>=0;c3--){
                    if(!board[c3][c1].equals("0")){
                        noneAbove=false;
                    }
                }
                
                // counts the number of spaces every candy will fall
                if(noneAbove==true){
                    for(c3=c2;c3>=0;c3--){
                        if(board[c3][c1].equals("0")){
                            c4++;
                        }
                    }
                    for(c3=c2;c3>=0;c3--){
                        boardFallTimer[c3][c1]=c4;
                    }
                    c4=0;
                    break;
                }
                
                // candies fall in column c3
                for(c3=c2;c3>0;c3--){
                    board[c3][c1]=board[c3-1][c1];
                    board[c3-1][c1]="0";
                    boardFall[c3][c1]=board[c3][c1];
                    boardFallTimer[c3][c1]++;
                }
            }
            if(noneAbove==true){
                noneAbove=false;
                break;
            }
        }
        
        // counts number of spaces the candy at the top row will fall
        boardFallTimer[0][c1]=boardFallTimer[1][c1];
        if(boardFallTimer[0][c1]==0&&board[0][c1].equals("0")){
            boardFallTimer[0][c1]=1;
        }
    }
    
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            if(board[c1][c2].equals("0")){
                boardFall[c1][c2]="0";
            }
        }
    }
}

public static void generate(int c1,int c2,int c3,int c4,int c5,String[][] board,String[][] boardFall,String[][] boardFallCopy){
    Random r=new Random();
    
    // generates candies in missing spaces
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            if(board[c1][c2].equals("0")){
                c3=(r.nextInt(6)+1);
                board[c1][c2]=String.valueOf(c3);
            }
        }
    }
    
    // removes pre-generated combinations
    c5=0;
    while(true){
        for(c1=0;c1<8;c1++){
            for(c2=0;c2<6;c2++){
                if(board[c1][c2].equals(board[c1][c2+1])&&board[c1][c2+1].equals(board[c1][c2+2])){
                    for(c4=c2;c4<c2+2;c4++){
                        if(boardFall[c1][c4].equals("0")){
                            c3=(r.nextInt(6)+1);
                            board[c1][c4]=String.valueOf(c3);
                            c5++;
                            break;
                        }
                    }
                }
            }
        }
        
        for(c1=0;c1<8;c1++){
            for(c2=0;c2<6;c2++){
                if(board[c2][c1].equals(board[c2+1][c1])&&board[c2+1][c1].equals(board[c2+2][c1])){
                    for(c4=c2;c4<c2+2;c4++){
                        if(boardFall[c4][c1].equals("0")){
                            c3=(r.nextInt(6)+1);
                            board[c4][c1]=String.valueOf(c3);
                            c5++;
                            break;
                        }
                    }
                }
            }
        }
        
        if(c5==0){
            break;
        }else{
            c5=0;
        }
    }
    
    // records candies that will fall
    
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            if(boardFall[c1][c2].equals("0")){
                boardFall[c1][c2]=board[c1][c2];
            }
        }
    }
    
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            boardFallCopy[c1][c2]=boardFall[c1][c2];
        }
    }
}

// checks for possible moves on board
public static boolean checkMove(int c1,int c2,int c3,int c4,boolean movePossible,boolean connection,String hold,String[] moveCoordinates,String[][] board){
    for(c1=0;c1<8;c1++){
        for(c2=0;c2<8;c2++){
            for(c3=0;c3<4;c3++){
                hold=board[c1][c2];
                
                // if there is a colour bomb
                if(board[c1][c2].equals("7c")){
                    movePossible=true;
                    break;
                }
                
                // moves candy in one of four directions, then checks for a connection
                if(c3==0&&c1<7){
                    board[c1][c2]=board[c1+1][c2];
                    board[c1+1][c2]=hold;
                    movePossible=checkConnection(c1,c2,c3,c4,0,0,0,0,connection,moveCoordinates,board);
                    hold=board[c1][c2];
                    board[c1][c2]=board[c1+1][c2];
                    board[c1+1][c2]=hold;
                }
                if(c3==1&&c1>0){
                    board[c1][c2]=board[c1-1][c2];
                    board[c1-1][c2]=hold;
                    movePossible=checkConnection(c1,c2,c3,c4,0,0,0,0,connection,moveCoordinates,board);
                    hold=board[c1][c2];
                    board[c1][c2]=board[c1-1][c2];
                    board[c1-1][c2]=hold;
                }
                if(c3==2&&c2<7){
                    board[c1][c2]=board[c1][c2+1];
                    board[c1][c2+1]=hold;
                    movePossible=checkConnection(c1,c2,c3,c4,0,0,0,0,connection,moveCoordinates,board);
                    hold=board[c1][c2];
                    board[c1][c2]=board[c1][c2+1];
                    board[c1][c2+1]=hold;
                }
                if(c3==3&&c2>0){
                    board[c1][c2]=board[c1][c2-1];
                    board[c1][c2-1]=hold;
                    movePossible=checkConnection(c1,c2,c3,c4,0,0,0,0,connection,moveCoordinates,board);
                    hold=board[c1][c2];
                    board[c1][c2]=board[c1][c2-1];
                    board[c1][c2-1]=hold;
                }
                
                if(movePossible==true){
                    break;
                }
            }
            if(movePossible==true){
                break;
            }
        }
        if(movePossible==true){
            break;
        }
    }
    
    return movePossible;
}

public static void shuffle(int c1,int x1,int y1,int x2,int y2,boolean connection,String hold,String[] moveCoordinates,String[][] board){
    Random r=new Random();
    
    // makes 100 random moves
    c1=100;
    while(c1!=0){
        y1=r.nextInt(8);
        x1=r.nextInt(8);
        y2=r.nextInt(8);
        x2=r.nextInt(8);
        
        if(!(y1==y2&&x1==x2)){
            hold=board[y1][x1];
            board[y1][x1]=board[y2][x2];
            board[y2][x2]=hold;
            connection=checkConnection(0,0,0,0,0,0,0,0,false,moveCoordinates,board);
            
            // if move does not create a new combination
            if(connection==true){
                hold=board[y1][x1];
                board[y1][x1]=board[y2][x2];
                board[y2][x2]=hold;
            }else{
                c1--;
            }
        }
    }
}
}