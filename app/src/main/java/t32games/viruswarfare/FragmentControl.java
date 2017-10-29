package t32games.viruswarfare;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;
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
        transaction.replace(fragmentContainer.getId(),fragment);
        transaction.commit();
        fragmentManager.executePendingTransactions();
        currentFragment=fragment;
        viewState.registerReceiver((EventReceiver) fragment);
    }

    private void onMenuButtonResume(){
        Object o = viewState.getEvent(EventTag.VIEW_UPDATE_PLAYER_TURN);
        if (o!=null){
            if(((int) o)!=0) {
                goToFragment(gameFragment);
            }
        }
    }

    private void  onBackButton() {
        if (currentFragment==gameFragment){
            goToFragment(menuFragment);
        } else  if (currentFragment==menuFragment) {
            goToFragment(gameFragment);
        }
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
            case EventTag.MENU_BUTTON_NEW_GAME:
                goToFragment(gameFragment);
                break;
            case EventTag.MENU_BUTTON_RESUME:
                onMenuButtonResume();
                break;
            case EventTag.GAME_BUTTON_MENU_CLICK:
                goToFragment(menuFragment);
                break;
            case EventTag.BACK_BUTTON:
                onBackButton();
                break;
        }
    }
}
