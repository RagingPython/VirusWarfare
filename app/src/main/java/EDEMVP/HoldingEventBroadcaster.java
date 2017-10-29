package EDEMVP;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;


public class HoldingEventBroadcaster extends EventBroadcaster{
    private HashMap<Integer, Object> heldEvents = new HashMap<Integer, Object>();

    @Override
    public void broadcastEvent(int eventTag, Object o) {
        heldEvents.put(eventTag, o);
        super.broadcastEvent(eventTag, o);
    }

    public Object getEvent(int eventTag) {
        for (int i:heldEvents.keySet()) {
            Log.d("ViewState", String.valueOf(i));
        }
        return heldEvents.get(eventTag);
    }

    public void removeHoldedEvent(int eventTag) {
        heldEvents.remove(eventTag);
    }

    public void reBroadcastEvent(int eventTag){
        if (heldEvents.containsKey(eventTag)){
            broadcastEvent(eventTag, heldEvents.get(eventTag));
        }
    }

    @Override
    public void registerReceiver(EventReceiver receiver) {
        super.registerReceiver(receiver);
        for(int i:heldEvents.keySet()) {
            receiver.eventMapping(i, heldEvents.get(i));
        }
    }
}
