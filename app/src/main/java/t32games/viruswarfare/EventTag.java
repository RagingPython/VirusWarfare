package t32games.viruswarfare;


public class EventTag {
    //INIT
    public static final int INIT_STAGE_VIEW_STATE=100;          //HoldingEventBroadcaster
    public static final int INIT_STAGE_EVENT_MANAGER=101;       //EventBroadcaster
    //VIEW STATE CHANGE
    public static final int VIEW_UPDATE_FIELD=1000;             //FieldStateSnapshot
    public static final int VIEW_UPDATE_PLAYER_TURN=1001;       //int (gameStatus)
    public static final int VIEW_UPDATE_BUTTON_END=1002;        //?
    //INTERFACE DRIVEN ACTIONS
    public static final int FIELD_CELL_CLICK=2000;              //int[2] (Coordinates)
    public static final int BUTTON_END_CLICK=2001;              //null
    public static final int BUTTON_NEW_CLICK=2002;              //null
    //MODEL REQUEST
    public static final int TRY_END_TURN=3000;                  //TurnData
    public static final int REQUEST_FIELD_DATA=3001;            //FieldStateRequest
    public static final int REQUEST_AVAILABILITY =3002;         //AvailabilityRequest
    //MODEL SIGNALS
    public static final int PLAYER_TURN_CHANGED=4000;           //int
}
