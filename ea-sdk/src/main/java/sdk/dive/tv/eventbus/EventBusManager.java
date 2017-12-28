package sdk.dive.tv.eventbus;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by Tagsonomy S.L. on 03/12/2015.
 */
public class EventBusManager {

    private static volatile EventBus instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized final EventBus getInstance() {
        if (instance == null)
            instance = EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();

        return instance;
    }
}
