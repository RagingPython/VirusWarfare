package t32games.viruswarfare;

/**
 * Created by nuzhdin on 25.10.2017.
 */

public class AvailabilityRequest {
    public boolean available;
    public int x,y;
    TurnData turnData;

    public AvailabilityRequest(TurnData turnData, int x, int y){
        this.turnData=turnData;
        this.x=x;
        this.y=y;
    }
}
