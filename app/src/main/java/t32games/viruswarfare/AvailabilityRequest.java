package t32games.viruswarfare;


class AvailabilityRequest {
    boolean available;
    int x,y;
    TurnData turnData;

    AvailabilityRequest(TurnData turnData, int x, int y){
        this.turnData=turnData;
        this.x=x;
        this.y=y;
    }
}
