package t32games.viruswarfare;

import android.view.View;
import android.widget.TextView;


public class TurnControl implements View.OnClickListener{
    int[] semiturnX = new int[3];
    int[] semiturnY = new int[3];
    int semiturnPointer=0;
    GameLogic gL;
    GameField gF;
    GameStatus gS;

    public TurnControl(){}

    public void setGameLogic(GameLogic gameLogic) {
        gL=gameLogic;
    }

    public void setGameField(GameField gameField) {
        gF=gameField;
    }

    public void setGameStatus(GameStatus t){
        gS=t;
    }

    public void newTurn() {
        semiturnPointer=0;
        refreshView();
    }

    public void endTurn() {
        if ((gL.getPlayerTurn()==GameLogic.PLAYER_1)|(gL.getPlayerTurn()==GameLogic.PLAYER_2)) {
            if (gL.makeTurn(semiturnPointer, semiturnX, semiturnY)) {
                newTurn();
            }
        }
    }

    public void cellPressed(int x, int y) {
        boolean flag=false;
        if ((gL.getPlayerTurn()==GameLogic.PLAYER_1)|(gL.getPlayerTurn()==GameLogic.PLAYER_2)) {
            for (int i = 0; i < semiturnPointer; i++) {
                if ((semiturnX[i] == x) & (semiturnY[i] == y)) {
                    flag = true;
                    semiturnPointer = i;
                    i = 3;
                    refreshView();
                }
            }
            if (!flag) {
                if ((gL.getAvailability(x, y, semiturnPointer,semiturnX,semiturnY)) & (semiturnPointer < 3)) {
                    semiturnX[semiturnPointer] = x;
                    semiturnY[semiturnPointer] = y;
                    semiturnPointer = semiturnPointer + 1;

                    refreshView();
                }
            }
        }
    }

    public void startNewGame(){
        gL.startNewGame();
        newTurn();
    };

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id._buttonEndTurn){
            endTurn();
        }
        if (view.getId()==R.id._buttonNewGame){
            startNewGame();
        }
    }

    private void refreshView() {

        switch (gL.getPlayerTurn()) {
            case GameLogic.IDLE:
                gF.setFieldData(null);
                break;
            case GameLogic.PLAYER_1:
            case GameLogic.PLAYER_2:
                FieldStateSnapshot fSS = gL.getFieldData(semiturnPointer,semiturnX,semiturnY);
                for (int i=0; i<semiturnPointer; i++){
                    fSS.setCellSelected(semiturnX[i],semiturnY[i]);
                }
                gF.setFieldData(fSS);
                break;
            case GameLogic.WINNER_PLAYER_1:
            case GameLogic.WINNER_PLAYER_2:
                gF.setFieldData(gL.getFieldData());
                break;
            case GameLogic.WINNER_DRAW:
                break;
        }
        gS.setGameStatus(gL.getPlayerTurn());
        gS.invalidate();
        gF.invalidate();
    }
}
