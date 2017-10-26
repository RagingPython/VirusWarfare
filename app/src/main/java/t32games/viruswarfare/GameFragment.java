package t32games.viruswarfare;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;


public class GameFragment extends Fragment implements View.OnClickListener, EventReceiver{
    Button buttonNewGame, buttonEndTurn;
    GameStatus gameStatus;
    GameField gameField;
    EventBroadcaster eventManager;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.game_fragment, container);
        buttonNewGame = (Button) view.findViewById(R.id._buttonNewGame);
        buttonEndTurn = (Button) view.findViewById(R.id._buttonEndTurn);
        gameStatus = (GameStatus) view.findViewById(R.id._textViewGameStatus);
        gameField = (GameField) view.findViewById(R.id._gameField);

        buttonNewGame.setOnClickListener(this);
        buttonEndTurn.setOnClickListener(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (view==buttonNewGame){
            eventManager.broadcastEvent(EventTag.GAME_BUTTON_NEW_CLICK,null);
        } else if (view==buttonEndTurn){
            eventManager.broadcastEvent(EventTag.GAME_BUTTON_END_CLICK,null);
        }
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager = (EventBroadcaster) o;
                break;
        }
        gameField.eventMapping(eventTag,o);
        gameStatus.eventMapping(eventTag,o);
    }
}
