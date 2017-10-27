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
            Log.d("UNREGISTER", currentFragment.getClass().getName());
        }
        fragmentManager.popBackStack();
        Log.d("BS", "pop");
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer.getId(),fragment);
        transaction.addToBackStack(null);
        Log.d("BS", "add");
        transaction.commit();
        fragmentManager.executePendingTransactions();
        currentFragment=fragment;
        viewState.registerReceiver((EventReceiver) fragment);
        Log.d("REGISTER", fragment.getClass().getName());
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
