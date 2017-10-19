package t32games.viruswarfare;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by nuzhdin on 19.10.2017.
 */

public class GameStatus extends AppCompatTextView {
    int gameStatus=0;

    public GameStatus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGameStatus(int gs) {
        gameStatus=gs;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (gameStatus) {
            case GameLogic.IDLE:
                setText("Press START GAME");
                break;
            case GameLogic.PLAYER_1:
                setText("Blue turn");
                break;
            case GameLogic.PLAYER_2:
                setText("Red turn");
                break;
            case GameLogic.WINNER_PLAYER_1:
                setText("BLUE WINS!");
                break;
            case GameLogic.WINNER_PLAYER_2:
                setText("RED WINS!");
                break;
            case GameLogic.WINNER_DRAW:
                setText("GAME DRAW!");
                break;
        }
        super.onDraw(canvas);
    }
}