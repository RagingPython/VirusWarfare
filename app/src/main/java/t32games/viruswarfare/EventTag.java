package t32games.viruswarfare;


class EventTag {
    //INIT
    static final int INIT_STAGE_VIEW_STATE=100;          //HoldingEventBroadcaster
    static final int INIT_STAGE_EVENT_MANAGER=101;       //EventBroadcaster
    static final int INIT_FINAL_STAGE=102;               //null
    //VIEW STATE CHANGE
    static final int VIEW_UPDATE_FIELD=1000;             //FieldStateSnapshot
    static final int VIEW_UPDATE_PLAYER_TURN=1001;       //int (gameStatus)
    static final int VIEW_UPDATE_BUTTON_END=1002;        //?
    //INTERFACE DRIVEN ACTIONS
    static final int GAME_CELL_CLICK =2000;              //int[2] (Coordinates)
    static final int GAME_BUTTON_END_CLICK =2001;        //null
    static final int GAME_BUTTON_NEW_CLICK =2002;        //null
    static final int MENU_BUTTON_PLAY_CLICK=2003;        //null
    static final int MENU_BUTTON_OPTIONS_CLICK=2004;     //null
    static final int MENU_BUTTON_EXIT_CLICK=2005;        //null
    //MODEL REQUEST
    static final int TRY_END_TURN=3000;                  //TurnData
    static final int REQUEST_FIELD_DATA=3001;            //FieldStateRequest
    static final int REQUEST_AVAILABILITY =3002;         //AvailabilityRequest
    //MODEL SIGNALS
    static final int PLAYER_TURN_CHANGED=4000;           //int
}
