package t32games.viruswarfare;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;

public class MenuFragment extends Fragment implements EventReceiver, View.OnClickListener{
    Button buttonPlay, buttonOptions, buttonExit;
    EventBroadcaster eventManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ans = inflater.inflate(R.layout.menu_fragment, container);
        buttonPlay = (Button) ans.findViewById(R.id._buttonPlay);
        buttonOptions = (Button) ans.findViewById(R.id._buttonOptions);
        buttonExit = (Button) ans.findViewById(R.id._buttonExit);

        buttonPlay.setOnClickListener(this);
        buttonOptions.setOnClickListener(this);
        buttonExit.setOnClickListener(this);

        return null;
    }

    @Override
    public void onClick(View view) {
        Log.d("munufragment","CLICK");
        if (view==buttonPlay) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_PLAY_CLICK,null);
        } else if (view==buttonOptions) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_OPTIONS_CLICK,null);
        } else if (view==buttonExit) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_EXIT_CLICK,null);
        }
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager = (EventBroadcaster) o;
                break;
        }
    }
}
