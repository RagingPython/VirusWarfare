package t32games.viruswarfare;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;
import EDEMVP.HoldingEventBroadcaster;


public class TurnControl implements EventReceiver{
    TurnData turnData= new TurnData();
    int playerTurn;
    EventBroadcaster eventManager;
    HoldingEventBroadcaster viewState;


    public void newTurn(int playerTurn) {
        turnData.semiturnPointer=0;
        this.playerTurn=playerTurn;
        refreshView();
    }

    public void endTurn() {
        if ((playerTurn==GameLogic.PLAYER_1)|(playerTurn==GameLogic.PLAYER_2)) {
            eventManager.broadcastEvent(EventTag.TRY_END_TURN, turnData);
        }
    }

    public void cellPressed(int x, int y) {
        boolean flag=false;
        if ((playerTurn==GameLogic.PLAYER_1)|(playerTurn==GameLogic.PLAYER_2)) {
            for (int i = 0; i < turnData.semiturnPointer; i++) {
                if ((turnData.semiturnX[i] == x) & (turnData.semiturnY[i] == y)) {
                    flag = true;
                    turnData.semiturnPointer = i;
                    i = 3;
                    refreshView();
                }
            }
            if (!flag) {
                AvailabilityRequest aR = new AvailabilityRequest(turnData, x, y);
                eventManager.broadcastEvent(EventTag.REQUEST_AVAILABILITY, aR);
                if ((aR.available) & (turnData.semiturnPointer < 3)) {
                    turnData.semiturnX[turnData.semiturnPointer] = x;
                    turnData.semiturnY[turnData.semiturnPointer] = y;
                    turnData.semiturnPointer = turnData.semiturnPointer + 1;

                    refreshView();
                }
            }
        }
    }


    private void refreshView() {
        FieldStateRequest fSR;

        switch (playerTurn) {
            case GameLogic.IDLE:
                eventManager.broadcastEvent(EventTag.VIEW_UPDATE_FIELD, null);
                break;
            case GameLogic.PLAYER_1:
            case GameLogic.PLAYER_2:
                fSR = new FieldStateRequest(turnData);
                eventManager.broadcastEvent(EventTag.REQUEST_FIELD_DATA, fSR);
                for (int i=0; i<turnData.semiturnPointer; i++){
                    fSR.fSS.setCellSelected(turnData.semiturnX[i],turnData.semiturnY[i]);
                }
                eventManager.broadcastEvent(EventTag.VIEW_UPDATE_FIELD, fSR.fSS);
                break;
            case GameLogic.WINNER_PLAYER_1:
            case GameLogic.WINNER_PLAYER_2:
                fSR = new FieldStateRequest(null);
                eventManager.broadcastEvent(EventTag.REQUEST_FIELD_DATA, fSR);
                eventManager.broadcastEvent(EventTag.VIEW_UPDATE_FIELD, fSR.fSS);
                break;
            case GameLogic.WINNER_DRAW:
                break;
        }
        eventManager.broadcastEvent(EventTag.VIEW_UPDATE_PLAYER_TURN, playerTurn);
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager=(EventBroadcaster) o;
                break;
            case EventTag.INIT_STAGE_VIEW_STATE:
                viewState=(HoldingEventBroadcaster) o;
                break;
            case EventTag.FIELD_CELL_CLICK:
                cellPressed(((int[])o)[0],((int[])o)[1]);
                break;
            case EventTag.PLAYER_TURN_CHANGED:
                newTurn((int) o);
                break;
            case EventTag.BUTTON_END_CLICK:
                endTurn();
                break;
        }
    }
}
