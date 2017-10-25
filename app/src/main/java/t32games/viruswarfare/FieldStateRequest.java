package t32games.viruswarfare;

/**
 * Created by nuzhdin on 25.10.2017.
 */

public class FieldStateRequest {
    public TurnData turnData;
    FieldStateSnapshot fSS;

    public FieldStateRequest(TurnData turnData){
        this.turnData=turnData;
    }
}
