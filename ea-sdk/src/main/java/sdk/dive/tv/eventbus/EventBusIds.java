package sdk.dive.tv.eventbus;

/**
 * Created by Tagsonomy S.L. on 24/10/2016.
 */

public enum EventBusIds {

    OPEN_CARD("open_card"),
    SHARE("share"),
    OPEN_TRAVEL_FRAGMENT("open_travel_fragment"),
    OPEN_SHOP_FRAGMENT("open_shop_fragment"),
    OPEN_DECO_SHOP_FRAGMENT("open_deco_shop_fragment"),
    OPEN_WEARSHOP_FRAGMENT("open_wearshop_fragment"),
    OPEN_WEB("open_web"),
    TOGGLE_TOOLBAR("toggle_toolbar"),
    OPEN_SYCHRO("open_synchro"),
    OPEN_SYCHRO_TDT("open_synchro_tdt"),
    GO_TO_HOME("go_to_home"),
    LIKE_CARD("like_card"), ;

    private final String name;

    private EventBusIds(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }
}
