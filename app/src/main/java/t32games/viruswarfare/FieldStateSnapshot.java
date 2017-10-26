package t32games.viruswarfare;


class FieldStateSnapshot {
    static final int CELL_NOT_AVAILABLE=0;
    static final int CELL_AVAILABLE=1;
    static final int CELL_SELECTED=2;

    private int playerTurn;
    private int[][] players;
    private boolean[][] killed;
    private int[][] availability;

    void setPlayers(int[][] pl, boolean[][] k) {
        players = pl;
        killed = k;
    }

    void setAvailability(int[][] av) {
        availability = av;
    }

    void setCellSelected(int x, int y) {
        availability[x][y]=CELL_SELECTED;
    }

    int getPlayer(int x, int y) {
        return players[x][y];
    }

    boolean getKilled(int x, int y) {
        return killed[x][y];
    }

    int getAvailability(int x, int y) {
        return availability[x][y];
    }

    void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    int getPlayerTurn() {
        return playerTurn;
    }
}
