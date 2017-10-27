package t32games.viruswarfare;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import EDEMVP.EventBroadcaster;
import EDEMVP.EventReceiver;
import EDEMVP.HoldingEventBroadcaster;

class FragmentControl implements EventReceiver {
    private FragmentManager fragmentManager;
    private EventBroadcaster eventManager;
    private FrameLayout fragmentContainer;
    private HoldingEventBroadcaster viewState;
    private MenuFragment menuFragment;
    private GameFragment gameFragment;
    private Fragment currentFragment = null;

    FragmentControl(FragmentManager fragmentManager, FrameLayout fragmentContainer) {
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
        transaction.remove(currentFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(fragmentContainer.getId(),fragment);
        transaction.commit();
        fragmentManager.executePendingTransactions();
        currentFragment=fragment;
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
            case EventTag.GAME_BUTTON_MENU_CLICK:
                goToFragment(menuFragment);
                break;
        }
    }
}
