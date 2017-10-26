package t32games.viruswarfare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;
import EDEMVP.HoldingEventBroadcaster;

public class MainActivity extends Activity{

    HoldingEventBroadcaster viewState;
    EventBroadcaster eventManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeEnvironment();
    }

    private void initializeEnvironment() {
        viewState = new HoldingEventBroadcaster();
        eventManager = new EventBroadcaster();

        GameLogic gL = new GameLogic();
        TurnControl tC = new TurnControl();
        RelativeLayout fragmentContainer = findViewById(R.id._relativeLayoutFragmentContainer);
        FragmentControl fC = new FragmentControl(getFragmentManager(),fragmentContainer);

        eventManager.registerReceiver(gL);
        eventManager.registerReceiver(tC);
        eventManager.registerReceiver(fC);

        eventManager.broadcastEvent(EventTag.INIT_STAGE_EVENT_MANAGER, eventManager);
        eventManager.broadcastEvent(EventTag.INIT_STAGE_VIEW_STATE, viewState);
        viewState.broadcastEvent(EventTag.INIT_STAGE_EVENT_MANAGER, eventManager);
        viewState.broadcastEvent(EventTag.INIT_STAGE_VIEW_STATE, viewState);


    }

    @Override
    public void onBackPressed() {

    }
}
