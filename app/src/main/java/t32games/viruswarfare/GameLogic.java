package t32games.viruswarfare;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;

class GameLogic implements EventReceiver {
    static final int IDLE =0;
    static final int PLAYER_1 =1;
    static final int PLAYER_2 =2;
    static final int WINNER_PLAYER_1 =3;
    static final int WINNER_PLAYER_2 =4;
    static final int WINNER_DRAW =5;
    static final int X_FIELD_SIZE = 10;
    static final int Y_FIELD_SIZE = 10;

    private int[][] players = new int[10][10];
    private boolean[][] killed = new boolean[10][10];
    private int playerTurn;
    private EventBroadcaster eventManager;

    GameLogic(){
        reset();
    }

    private void reset() {
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 10;j++){
                players[i][j]=0;
                killed[i][j]=false;
            }
        }
        playerTurn=0;
    }

    private void startNewGame(){
        reset();
        playerTurn=PLAYER_1;
        eventManager.broadcastEvent(EventTag.PLAYER_TURN_CHANGED, playerTurn);
    }

    private void makeTurn(TurnData tD) {
        boolean flag =true;
        if (tD.semiturnPointer<3) {
            flag=false;
        }

        if (!flag){
            int[][] map = getAvailabilityMap(tD);
            int availabilitySum = 0;
            for (int x=0;x<X_FIELD_SIZE;x++) {
                for (int y = 0; y < Y_FIELD_SIZE; y++) {
                    availabilitySum=availabilitySum+map[x][y];
                }
            }
            if (availabilitySum==0){
                flag=true;
            }
        }
        if (flag){
            for(int i=0;i<tD.semiturnPointer;i++) {
                if (players[tD.semiturnX[i]][tD.semiturnY[i]]==0) {
                    players[tD.semiturnX[i]][tD.semiturnY[i]]=playerTurn;
                    killed[tD.semiturnX[i]][tD.semiturnY[i]]=false;
                } else {
                    killed[tD.semiturnX[i]][tD.semiturnY[i]]=true;
                }
            }
            if (playerTurn==PLAYER_1) {
                playerTurn=PLAYER_2;
            } else if (playerTurn==PLAYER_2) {
                playerTurn=PLAYER_1;
            }
            checkGameEnd();
            eventManager.broadcastEvent(EventTag.PLAYER_TURN_CHANGED, playerTurn);
        }
    }

    private void requestFieldData(FieldStateRequest fSR) {
        int[][] av = new int[X_FIELD_SIZE][Y_FIELD_SIZE];
        fSR.fSS = new FieldStateSnapshot();
        fSR.fSS.setPlayers(players,killed);
        fSR.fSS.setAvailability(getAvailabilityMap(fSR.turnData));
        fSR.fSS.setPlayerTurn(playerTurn);

    }


    private boolean getAvailability(AvailabilityRequest aR){
        aR.available = (getAvailabilityMap(aR.turnData)[aR.x][aR.y]==1);
        return aR.available;
    }

    private int[][] getAvailabilityMap(TurnData tD) {
        if (tD==null) {
            tD=new TurnData();
            tD.semiturnPointer=0;
        }
        int[][] map = new int[X_FIELD_SIZE][Y_FIELD_SIZE];
        int[][] p = new int[X_FIELD_SIZE][Y_FIELD_SIZE];
        boolean[][] k = new boolean[X_FIELD_SIZE][Y_FIELD_SIZE];

        for (int i=0;i<X_FIELD_SIZE;i++) {
            for (int j = 0; j < Y_FIELD_SIZE; j++) {
                p[i][j]=players[i][j];
                k[i][j]=killed[i][j];
            }
        }

        for (int s=-1; s<tD.semiturnPointer;s++) {
            if (s!=-1) {
                if((map[tD.semiturnX[s]][tD.semiturnY[s]] == 0)&(p[0][0]!=0)&(p[X_FIELD_SIZE-1][Y_FIELD_SIZE-1]!=0)) {
                    return null;
                } else {
                    if (p[tD.semiturnX[s]][tD.semiturnY[s]] == 0) {
                        p[tD.semiturnX[s]][tD.semiturnY[s]] = playerTurn;
                        k[tD.semiturnX[s]][tD.semiturnY[s]]=false;
                    } else {
                        k[tD.semiturnX[s]][tD.semiturnY[s]]=true;
                    }
                }
            }

            for (int i = 0; i < X_FIELD_SIZE; i++) {
                for (int j = 0; j < Y_FIELD_SIZE; j++) {
                    if ((p[i][j] == playerTurn) & (!k[i][j])) {
                        map[i][j] = 1;
                    } else {
                        map[i][j] = 0;
                    }
                }
            }

            boolean flag = true;
            while (flag) {
                flag = false;
                for (int x = 0; x < X_FIELD_SIZE; x++) {
                    for (int y = 0; y < Y_FIELD_SIZE; y++) {
                        if ((map[x][y] == 0) & (p[x][y] != playerTurn)) {
                            for (int dx = -1; dx < 2; dx++) {
                                for (int dy = -1; dy < 2; dy++) {
                                    if (!((dx == 0) & (dy == 0))) {
                                        if ((x + dx >= 0) & (x + dx < X_FIELD_SIZE) & (y + dy >= 0) & (y + dy < Y_FIELD_SIZE)) {
                                            if (((map[x + dx][y + dy] == 1) & (p[x + dx][y + dy] != 0)) & (((p[x + dx][y + dy] == playerTurn) & (!k[x + dx][y + dy])) | ((p[x + dx][y + dy] != playerTurn) & (k[x + dx][y + dy])))) {
                                                map[x][y] = 1;
                                                flag = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        for (int i=0;i<X_FIELD_SIZE;i++) {
            for (int j = 0; j < Y_FIELD_SIZE; j++) {
                if(((map[i][j]==1)&(p[i][j]==playerTurn))|(k[i][j])) {
                    map[i][j]=0;
                }
            }
        }


        if ((playerTurn==PLAYER_1)&(players[0][Y_FIELD_SIZE-1]!=PLAYER_1)) {
            map[0][Y_FIELD_SIZE-1]=1;
        }
        if ((playerTurn==PLAYER_2)&(players[X_FIELD_SIZE-1][0]!=PLAYER_2)) {
            map[X_FIELD_SIZE-1][0]=1;
        }

        return map;
    }

    private void checkGameEnd(){
        boolean flag1 = false, flag2= false;
        for (int i=0;i<X_FIELD_SIZE;i++) {
            for (int j = 0; j < Y_FIELD_SIZE; j++) {
                if ((players[i][j]==PLAYER_1)&(!killed[i][j])) {
                    flag1=true;
                }
                if ((players[i][j]==PLAYER_2)&(!killed[i][j])) {
                    flag2=true;
                }
            }
        }

        if((players[X_FIELD_SIZE-1][0]==0)|(players[0][Y_FIELD_SIZE-1]==0)){
            flag1=true;
            flag2=true;
        }

        if ((!flag1)&(!flag2)) {
            playerTurn=0;
        } else if ((flag1)&(!flag2)) {
            playerTurn=WINNER_PLAYER_1;
        } else if ((!flag1)&(flag2)) {
            playerTurn = WINNER_PLAYER_2;
        } else {
            if ((checkPlayerCantWin(PLAYER_1))&(checkPlayerCantWin(PLAYER_2))) {
                playerTurn=WINNER_DRAW;
            }
        }
    }

    private boolean checkPlayerCantWin(int p) {
        int opposite=0;
        boolean flag=true;
        int[][] map = new int[X_FIELD_SIZE][Y_FIELD_SIZE];

        if(p==PLAYER_1) {opposite=PLAYER_2;}
        else if(p==PLAYER_2){opposite=PLAYER_1;}

        for (int x = 0; x < X_FIELD_SIZE; x++) {
            for (int y = 0; y < Y_FIELD_SIZE; y++) {
                if (!killed[x][y]) {
                    map[x][y] = players[x][y];
                } else {
                    map[x][y] = 0;
                }
            }
        }

        while (flag) {
            flag=false;
            for (int x = 0; x < X_FIELD_SIZE; x++) {
                for (int y = 0; y < Y_FIELD_SIZE; y++) {
                    if (map[x][y] == p) {
                        for (int dx = -1; dx < 2; dx++) {
                            for (int dy = -1; dy < 2; dy++) {
                                if ((x + dx >= 0) & (x + dx < X_FIELD_SIZE) & (y + dy >= 0) & (y + dy < Y_FIELD_SIZE)) {
                                    if (map[x+dx][y+dy] != p) {
                                        if (!((players[x + dx][y + dy] == p) & (killed[x + dx][y + dy]))) {
                                            map[x + dx][y + dy] = p;
                                            flag = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int x = 0; x < X_FIELD_SIZE; x++) {
            for (int y = 0; y < Y_FIELD_SIZE; y++) {
                if (map[x][y]==opposite) {return true;}
            }
        }

        return false;
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager = (EventBroadcaster) o;
                break;
            case EventTag.MENU_BUTTON_NEW_GAME:
                startNewGame();
                break;
            case EventTag.TRY_END_TURN:
                makeTurn((TurnData) o);
                break;
            case EventTag.REQUEST_FIELD_DATA:
                requestFieldData((FieldStateRequest) o);
                break;
            case EventTag.REQUEST_AVAILABILITY:
                getAvailability((AvailabilityRequest) o);
                break;
        }
    }
}
