package t32games.viruswarfare;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import EDEMVP.EventReceiver;


class GameStatus extends AppCompatTextView implements EventReceiver{
    int gameStatus=0;

    GameStatus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (gameStatus) {
            case GameLogic.IDLE:
                setText("Press START GAME");
                break;
            case GameLogic.PLAYER_1:
                setText("Red turn");
                break;
            case GameLogic.PLAYER_2:
                setText("Blue turn");
                break;
            case GameLogic.WINNER_PLAYER_1:
                setText("RED WINS!");
                break;
            case GameLogic.WINNER_PLAYER_2:
                setText("BLUE WINS!");
                break;
            case GameLogic.WINNER_DRAW:
                setText("GAME DRAW!");
                break;
        }
        super.onDraw(canvas);
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        if (eventTag==EventTag.VIEW_UPDATE_PLAYER_TURN){
            gameStatus=(int) o;
            invalidate();
        }
    }
}
