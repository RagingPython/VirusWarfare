package t32games.viruswarfare;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;

class DBController implements EventReceiver{
    private static final String WIPE_FIELD_STATE =  "delete from FIELD_STATE";

    private EventBroadcaster eventManager;
    private SQLiteDatabase database;

    DBController(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        database=dbOpenHelper.getWritableDatabase();
    }

    private void saveState(FieldStateSnapshot fSS) {
        database.execSQL(WIPE_FIELD_STATE);
        String s;
        for (int x=0; x<fSS.getXCount(); x++) {
            for (int y=0; y<fSS.getYCount(); y++) {
                if (fSS.getKilled(x,y)) {
                    s="1";
                } else {
                    s="0";
                }
                database.execSQL("insert into FIELD_STATE values ("+ String.valueOf(x)+", "+String.valueOf(y)+", "+String.valueOf(fSS.getPlayer(x,y))+", "+s+")");
            }
        }
        database.execSQL("insert into FIELD_STATE values (-1,-1, "+String.valueOf(fSS.getPlayerTurn())+", 0)");
    }

    private FieldStateSnapshot loadState() {
        FieldStateSnapshot fSS;
        String[] columns = {"max(x)","max(y)"};
        Cursor cursor = database.query("FIELD_STATE", null, null, null, null, null, null);
        if (cursor.getCount()>0) {
            cursor = database.query("FIELD_STATE",columns,null,null,null,null,null);
            cursor.moveToFirst();
            int[][] players = new int[cursor.getInt(0)+1][cursor.getInt(1)+1];
            boolean[][] killed = new boolean[cursor.getInt(0)+1][cursor.getInt(1)+1];
            int playerTurn=0;
            cursor.close();
            cursor = database.query("FIELD_STATE", null, null, null, null, null, null);
            fSS = new FieldStateSnapshot();
            cursor.moveToFirst();
            do {
                if ((cursor.getInt(0) == -1) & (cursor.getInt(1)) == -1) {
                    playerTurn = cursor.getInt(2);
                } else {
                    players[cursor.getInt(0)][cursor.getInt(1)] = cursor.getInt(2);
                    killed[cursor.getInt(0)][cursor.getInt(1)] = (cursor.getInt(3) == 1);
                }
            } while (cursor.moveToNext());
            fSS.setPlayers(players, killed);
            fSS.setPlayerTurn(playerTurn);
        } else {
            fSS = null;
        }
        cursor.close();
        return fSS;
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager=(EventBroadcaster) o;
                break;
            case EventTag.SAVE_STATE:
                saveState((FieldStateSnapshot) o);
                break;
            case EventTag.LOAD_INITIATION:
                eventManager.broadcastEvent(EventTag.SET_MODEL_STATE,loadState());
                break;
        }
    }
}
