package t32games.viruswarfare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity{
    GameField gameField;
    GameLogic gameLogic;
    TurnControl turnControl;
    Button buttonNewGame, buttonEndTurn;
    GameStatus gameStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameField = (GameField) findViewById(R.id._gameField);
        buttonNewGame = (Button) findViewById(R.id._buttonNewGame);
        buttonEndTurn = (Button) findViewById(R.id._buttonEndTurn);
        gameStatus = (GameStatus) findViewById(R.id._textViewGameStatus);
        gameLogic = new GameLogic();
        turnControl = new TurnControl();
        turnControl.setGameLogic(gameLogic);
        turnControl.setGameField(gameField);
        gameField.setTurnControl(turnControl);
        turnControl.setGameStatus(gameStatus);

        buttonNewGame.setOnClickListener(turnControl);
        buttonEndTurn.setOnClickListener(turnControl);
        gameField.setOnTouchListener(gameField);
    }

}
