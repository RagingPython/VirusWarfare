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


public class TutorialFragment extends Fragment implements EventReceiver, View.OnClickListener {
    EventBroadcaster eventManager;
    Button buttonOk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.tutorial_fragmetn, container, false);
        buttonOk=view.findViewById(R.id._buttonOk);
        buttonOk.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        eventManager.broadcastEvent(EventTag.TUTORIAL_BUTTON_OK,null);
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager=(EventBroadcaster) o;
                break;
        }
    }
}
