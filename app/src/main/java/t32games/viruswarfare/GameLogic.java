package t32games.viruswarfare;

import android.util.Log;

public class GameLogic {
    public static final int IDLE =0;
    public static final int PLAYER_1 =1;
    public static final int PLAYER_2 =2;
    public static final int WINNER_PLAYER_1 =3;
    public static final int WINNER_PLAYER_2 =4;
    public static final int X_FIELD_SIZE = 10;
    public static final int Y_FIELD_SIZE = 10;

    private int[][] players = new int[10][10];
    private boolean[][] killed = new boolean[10][10];
    private int playerTurn;

    public GameLogic(){
        reset();
    }

    public void reset() {
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 10;j++){
                players[i][j]=0;
                killed[i][j]=false;
            }
        }
        playerTurn=0;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void startNewGame(){
        reset();
        playerTurn=PLAYER_1;
    }

    public boolean makeTurn(int semiturnPointer, int[] semiturnX, int[] semiturnY) {
        boolean flag =true;
        if (semiturnPointer<3) {
            flag=false;
        }
        for(int i=0;i<semiturnPointer;i++) {
            if (!getAvailability(semiturnX[i],semiturnY[i],i, semiturnX, semiturnY)){
                flag = false;
            }
        }
        if (!flag){
            int[][] map = getAvailabilityMap(semiturnPointer,semiturnX,semiturnY);
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
            for(int i=0;i<semiturnPointer;i++) {
                if (players[semiturnX[i]][semiturnY[i]]==0) {
                    players[semiturnX[i]][semiturnY[i]]=playerTurn;
                    killed[semiturnX[i]][semiturnY[i]]=false;
                } else {
                    killed[semiturnX[i]][semiturnY[i]]=true;
                }
            }
            if (playerTurn==PLAYER_1) {
                playerTurn=PLAYER_2;
            } else if (playerTurn==PLAYER_2) {
                playerTurn=PLAYER_1;
            }
            checkGameEnd();
        }
        return flag;
    }

    public FieldStateSnapshot getFieldData() {
        return getFieldData(0, null, null);
    }

    public FieldStateSnapshot getFieldData(int semiturnPointer,int[] semiturnX, int[] semiturnY) {
        int[][] av = new int[X_FIELD_SIZE][Y_FIELD_SIZE];
        FieldStateSnapshot ans = new FieldStateSnapshot();
        ans.setPlayers(players,killed);
        ans.setAvailability(getAvailabilityMap(semiturnPointer,semiturnX,semiturnY));
        return ans;
    }

    public boolean getAvailability(int x, int y, int semiturnPointer, int[] semiturnX, int[] semiturnY){
        return getAvailabilityMap(semiturnPointer,semiturnX,semiturnY)[x][y]==1;
    }

    public boolean getAvailability(int x, int y){
        return getAvailabilityMap()[x][y]==1;
    }

    public int[][] getAvailabilityMap() {
        int[][] map=getAvailabilityMap(0, null, null);
        return null;
    }

    public int[][] getAvailabilityMap(int semiturnPointer, int[] semiturnX, int[] semiturnY) {
        int[][] map = new int[X_FIELD_SIZE][Y_FIELD_SIZE];
        int[][] p = new int[X_FIELD_SIZE][Y_FIELD_SIZE];
        boolean[][] k = new boolean[X_FIELD_SIZE][Y_FIELD_SIZE];

        for (int i=0;i<X_FIELD_SIZE;i++) {
            for (int j = 0; j < Y_FIELD_SIZE; j++) {
                p[i][j]=players[i][j];
                k[i][j]=killed[i][j];
            }
        }

        for (int s=-1; s<semiturnPointer;s++) {
            if (s!=-1) {
                if(map[semiturnX[s]][semiturnY[s]] == 0) {
                    return null;
                } else {
                    if (p[semiturnX[s]][semiturnY[s]] == 0) {
                        p[semiturnX[s]][semiturnY[s]] = playerTurn;
                        k[semiturnX[s]][semiturnY[s]]=false;
                    } else {
                        k[semiturnX[s]][semiturnY[s]]=true;
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

    }
}
