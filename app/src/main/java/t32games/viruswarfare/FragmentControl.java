package t32games.viruswarfare;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.RelativeLayout;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;
import EDEMVP.HoldingEventBroadcaster;

class FragmentControl implements EventReceiver {
    private FragmentManager fragmentManager;
    private EventBroadcaster eventManager;
    private RelativeLayout fragmentContainer;
    private HoldingEventBroadcaster viewState;
    private MenuFragment menuFragment;
    private GameFragment gameFragment;
    private Fragment currentFragment = null;

    FragmentControl(FragmentManager fragmentManager, RelativeLayout fragmentContainer) {
        this.fragmentManager=fragmentManager;
        this.fragmentContainer=fragmentContainer;
        menuFragment=new MenuFragment();
        gameFragment=new GameFragment();
    }

    private void goToFragment(Fragment fragment) {
        if (currentFragment!=null) {
            viewState.unRegisterReceiver((EventReceiver) currentFragment);
        }
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer.getId(),fragment);
        transaction.commit();
        viewState.registerReceiver((EventReceiver) fragment);
    }

    @Override
    public void eventMapping(int eventTag, Object o) {
        switch (eventTag) {
            case EventTag.INIT_STAGE_EVENT_MANAGER:
                eventManager=(EventBroadcaster) o;
                break;
            case EventTag.INIT_STAGE_VIEW_STATE:
                viewState=(HoldingEventBroadcaster) o;
                break;
            case EventTag.INIT_FINAL_STAGE:
                goToFragment(menuFragment);
                break;
            case EventTag.MENU_BUTTON_PLAY_CLICK:
                goToFragment(gameFragment);
                break;
        }
    }
}
