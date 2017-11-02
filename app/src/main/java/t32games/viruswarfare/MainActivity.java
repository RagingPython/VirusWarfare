package t32games.viruswarfare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;
import EDEMVP.HoldingEventBroadcaster;

public class MainActivity extends Activity implements EventReceiver{

    HoldingEventBroadcaster viewState;
    EventBroadcaster eventManager;
    FrameLayout fragmentContainer;


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
        fragmentContainer = findViewById(R.id._frameLayoutFragmentContainer);
        FragmentControl fC = new FragmentControl(getFragmentManager(),fragmentContainer);
        DBController dbController = new DBController(getApplicationContext());

        eventManager.registerReceiver(this);
        eventManager.registerReceiver(gL);
        eventManager.registerReceiver(tC);
        eventManager.registerReceiver(fC);
        eventManager.registerReceiver(dbController);

        eventManager.broadcastEvent(EventTag.INIT_STAGE_EVENT_MANAGER, eventManager);
        eventManager.broadcastEvent(EventTag.INIT_STAGE_VIEW_STATE, viewState);
        viewState.broadcastEvent(EventTag.INIT_STAGE_EVENT_MANAGER, eventManager);
        viewState.broadcastEvent(EventTag.INIT_STAGE_VIEW_STATE, viewState);

        eventManager.broadcastEvent(EventTag.INIT_FINAL_STAGE,null);
    }

    @Override
    public void onBackPressed() {
        eventManager.broadcastEvent(EventTag.BACK_BUTTON,null);
    }

    @Override
    protected void onResume() {
        eventManager.broadcastEvent(EventTag.LOAD_INITIATION, null);
        super.onResume();
    }

    @Override
    protected void onPause() {
        eventManager.broadcastEvent(EventTag.SAVE_INITIATION, null);
        super.onPause();
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.MENU_BUTTON_EXIT_CLICK:
                fragmentContainer.removeAllViews();
                finish();
                break;
        }
    }
}
