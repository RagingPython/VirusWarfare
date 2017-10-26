package t32games.viruswarfare;

class FieldStateRequest {
    TurnData turnData;
    FieldStateSnapshot fSS;

    FieldStateRequest(TurnData turnData){
        this.turnData=turnData;
    }
}
