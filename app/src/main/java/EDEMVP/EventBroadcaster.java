package EDEMVP;

import java.util.HashSet;

public class EventBroadcaster {
    private HashSet<EventReceiver> receivers = new HashSet<EventReceiver>();

    public void registerReceiver(EventReceiver receiver){
        receivers.add(receiver);
    }

    public void unRegisterReceiver(EventReceiver receiver){
        receivers.remove(receiver);
    }

    public void broadcastEvent(int eventTag, Object o) {
        for (EventReceiver e:receivers) {
            e.eventMapping(eventTag, o);
        }
    }
}
