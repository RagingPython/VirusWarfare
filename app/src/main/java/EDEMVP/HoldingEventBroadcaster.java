package EDEMVP;

import java.util.HashMap;

/**
 * Created by nuzhdin on 24.10.2017.
 */

public class HoldingEventBroadcaster extends EventBroadcaster{
    HashMap<Integer, Object> holdedEvents = new HashMap<Integer, Object>();

    @Override
    public void broadcastEvent(int eventTag, Object o) {
        holdedEvents.put(eventTag, o);
        super.broadcastEvent(eventTag, o);
    }

    public Object getEvent(int eventTag) {
        return holdedEvents.get(eventTag);
    }

    public void removeHoldedEvent(int eventTag) {
        holdedEvents.remove(eventTag);
    }

    public void reBroadcastEvent(int eventTag){
        if (holdedEvents.containsKey(eventTag)){
            broadcastEvent(eventTag, holdedEvents.get(eventTag));
        }
    }
}
