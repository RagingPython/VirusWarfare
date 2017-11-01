package t32games.viruswarfare;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

class CellArtist {
    private float cS;

    private Bitmap empty, player1, player2, player1Selected, player2Selected, player1Available, player2Available, player1Dead, player2Dead, emptyAvailableForPlayer1, emptyAvailableForPlayer2, emptySelectedForPlayer1, emptySelectedForPlayer2;


    private Context context;

    CellArtist(Context ctx){
        context=ctx;
    }

    void initialize(float cellSize) {
        cS=cellSize;
        empty = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.e),(int)cS,(int)cS,true);
        player1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p1),(int)cS,(int)cS,true);
        player2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p2),(int)cS,(int)cS,true);
        player1Selected = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p1_s),(int)cS,(int)cS,true);
        player2Selected = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p2_s),(int)cS,(int)cS,true);
        player1Available = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p1_a),(int)cS,(int)cS,true);
        player2Available = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p2_a),(int)cS,(int)cS,true);
        player1Dead = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p1_k),(int)cS,(int)cS,true);
        player2Dead = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.p2_k),(int)cS,(int)cS,true);
        emptyAvailableForPlayer1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.e_a),(int)cS,(int)cS,true);
        emptyAvailableForPlayer2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.e_a),(int)cS,(int)cS,true);
        emptySelectedForPlayer1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.e_s),(int)cS,(int)cS,true);
        emptySelectedForPlayer2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.e_s),(int)cS,(int)cS,true);

    }

    void drawCell(Canvas canvas, float x, float y, int playerTurn, int player, boolean killed, int selected) {
        Bitmap toDraw=null;

        if (player==0){
            if (selected==FieldStateSnapshot.CELL_NOT_AVAILABLE) {
                toDraw=empty;
            } else if (selected==FieldStateSnapshot.CELL_AVAILABLE) {
                if (playerTurn==GameLogic.PLAYER_1) {
                    toDraw=emptyAvailableForPlayer1;
                } else if (playerTurn==GameLogic.PLAYER_2) {
                    toDraw=emptyAvailableForPlayer2;
                }
            } else if (selected==FieldStateSnapshot.CELL_SELECTED) {
                if (playerTurn==GameLogic.PLAYER_1) {
                    toDraw=emptySelectedForPlayer1;
                } else if (playerTurn==GameLogic.PLAYER_2) {
                    toDraw=emptySelectedForPlayer2;
                }
            }
        } else if(player==GameLogic.PLAYER_1) {
            if (killed) {
                toDraw=player1Dead;
            } else {
                if (selected==FieldStateSnapshot.CELL_NOT_AVAILABLE) {
                    toDraw=player1;
                } else if (selected==FieldStateSnapshot.CELL_AVAILABLE){
                    toDraw=player1Available;
                } else if (selected==FieldStateSnapshot.CELL_SELECTED){
                    toDraw=player1Selected;
                }
            }
        } else if(player==GameLogic.PLAYER_2) {
            if (killed) {
                toDraw=player2Dead;
            } else {
                if (selected==FieldStateSnapshot.CELL_NOT_AVAILABLE) {
                    toDraw=player2;
                } else if (selected==FieldStateSnapshot.CELL_AVAILABLE){
                    toDraw=player2Available;
                } else if (selected==FieldStateSnapshot.CELL_SELECTED){
                    toDraw=player2Selected;
                }
            }
        }
        canvas.drawBitmap(toDraw,x,y,null);
    }
}
