package t32games.viruswarfare;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;

public class MenuFragment extends Fragment implements EventReceiver, View.OnClickListener{
    Button buttonResume,buttonNewGame, buttonOptions, buttonExit;
    EventBroadcaster eventManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        buttonResume = (Button) view.findViewById(R.id._buttonResume);
        buttonNewGame = (Button) view.findViewById(R.id._buttonNewGame);
        buttonOptions = (Button) view.findViewById(R.id._buttonTutorial);
        buttonExit = (Button) view.findViewById(R.id._buttonExit);

        buttonResume.setOnClickListener(this);
        buttonNewGame.setOnClickListener(this);
        buttonOptions.setOnClickListener(this);
        buttonExit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view== buttonResume) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_RESUME,null);
        }else if (view== buttonNewGame) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_NEW_GAME,null);
        } else if (view==buttonOptions) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_TUTORIAL,null);
        } else if (view==buttonExit) {
            eventManager.broadcastEvent(EventTag.MENU_BUTTON_EXIT,null);
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
