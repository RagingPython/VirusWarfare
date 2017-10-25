package t32games.viruswarfare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;
import EDEMVP.HoldingEventBroadcaster;

public class MainActivity extends Activity implements View.OnClickListener, EventReceiver{

    TurnControl turnControl;
    Button buttonNewGame, buttonEndTurn;
    GameStatus gameStatus;
    HoldingEventBroadcaster viewState;
    EventBroadcaster eventManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNewGame = (Button) findViewById(R.id._buttonNewGame);
        buttonEndTurn = (Button) findViewById(R.id._buttonEndTurn);
        gameStatus = (GameStatus) findViewById(R.id._textViewGameStatus);

        buttonNewGame.setOnClickListener(this);
        buttonEndTurn.setOnClickListener(this);

        initializeEnvironment();
    }

    private void initializeEnvironment() {
        viewState = new HoldingEventBroadcaster();
        eventManager = new EventBroadcaster();

        GameField gF = (GameField) findViewById(R.id._gameField);
        GameLogic gL = new GameLogic();
        TurnControl tC = new TurnControl();

        eventManager.registerReceiver(gL);
        eventManager.registerReceiver(tC);

        viewState.registerReceiver(gF);
        viewState.registerReceiver(this);
        viewState.registerReceiver(gameStatus);

        eventManager.broadcastEvent(EventTag.INIT_STAGE_EVENT_MANAGER, eventManager);
        eventManager.broadcastEvent(EventTag.INIT_STAGE_VIEW_STATE, viewState);
        viewState.broadcastEvent(EventTag.INIT_STAGE_EVENT_MANAGER, eventManager);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {
        if (view==buttonNewGame){
            eventManager.broadcastEvent(EventTag.BUTTON_NEW_CLICK,null);
        } else if (view==buttonEndTurn){
            eventManager.broadcastEvent(EventTag.BUTTON_END_CLICK,null);
        }
    }

    @Override
    public void eventMapping(int eventTag, Object o) {

    }
}
