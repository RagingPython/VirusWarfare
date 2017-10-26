package t32games.viruswarfare;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;


class GameField extends View implements View.OnTouchListener, EventReceiver{
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private static final float GAME_FIELD_RATIO = 0.95f;

    private CellArtist cellArtist;
    private EventBroadcaster eventManager;
    private FieldStateSnapshot fieldState = null;

    private int lastHeight = -1;
    private int lastWidth = -1;
    private int clickX = 0;
    private int clickY = 0;
    private float cS =0;
    private float leftSpacing=0;
    private float topSpacing=0;



    GameField(Context context, AttributeSet attrs) {
        super(context, attrs);
        cellArtist = new CellArtist(context);
        this.setBackgroundColor(BACKGROUND_COLOR);
        this.setOnTouchListener(this);
    }

    private void initialize(EventBroadcaster eventBroadcaster) {
        eventManager=eventBroadcaster;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if ((lastHeight!=canvas.getHeight())|(lastWidth!=canvas.getWidth())) {
            recalculateCellSize(canvas.getWidth(), canvas.getHeight());
            lastHeight=canvas.getHeight();
            lastWidth=canvas.getWidth();
            cellArtist.initialize(cS);
        }

        if (fieldState==null) {
            canvas.drawColor(BACKGROUND_COLOR);
        } else {

            for (int x = 0; x < GameLogic.X_FIELD_SIZE; x++) {
                for (int y = 0; y < GameLogic.Y_FIELD_SIZE; y++) {
                    cellArtist.drawCell(canvas, leftSpacing + x * cS, topSpacing + y * cS, fieldState.getPlayerTurn(), fieldState.getPlayer(x,y), fieldState.getKilled(x, y), fieldState.getAvailability(x,y));
                }
            }
        }
    }

    private void recalculateCellSize(int x, int y) {
        cS = (float) Math.floor(Math.min(x,y)*GAME_FIELD_RATIO/((double) Math.max(GameLogic.X_FIELD_SIZE,GameLogic.Y_FIELD_SIZE)));
        leftSpacing= ((x-GameLogic.X_FIELD_SIZE*cS)/2f);
        topSpacing= ((y-GameLogic.Y_FIELD_SIZE*cS)/2f);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //TODO: visual touch pointer
                break;
            case MotionEvent.ACTION_UP:
                resolveClick(motionEvent);
                eventManager.broadcastEvent(EventTag.GAME_CELL_CLICK, new int[] {clickX, clickY} );
                break;
        }
        return true;
    }

    private boolean resolveClick(MotionEvent motionEvent) {
        float cx = motionEvent.getX() - leftSpacing;
        float cy = motionEvent.getY() - topSpacing;
        if((cx<cS*GameLogic.X_FIELD_SIZE)&(cy<cS*GameLogic.Y_FIELD_SIZE)) {
            clickX=(int) Math.floor(cx/cS);
            clickY=(int) Math.floor(cy/cS);
            return true;
        }
        return false;
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                initialize((EventBroadcaster) o);
                break;
            case EventTag.VIEW_UPDATE_FIELD:
                fieldState = (FieldStateSnapshot) o;
                invalidate();
                break;
        }
    }
}
