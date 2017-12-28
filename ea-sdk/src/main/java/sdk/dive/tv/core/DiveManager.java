package sdk.dive.tv.core;

import android.content.Context;
import android.provider.Settings;

import org.greenrobot.eventbus.EventBus;

import sdk.client.dive.tv.SdkClient;
import sdk.dive.tv.BuildConfig;
import sdk.dive.tv.eventbus.EventBusManager;

/**
 * The DiveManager class implements all the management complexity of the entire application. It holds all the specific managers in the application.
 */
public class DiveManager {

    private static DiveManager instance;
    private static EventBus mEventBus;
    private Context context;
    private static SdkClient mSdkClient;

    /**
     * Touchvie manager initializer.
     *
     * @param {Context} c. Context of the current Touchvie manager.
     */
    private DiveManager(final Context c, String environment) {

        context = c;
//        String deviceId = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
//        mSdkClient = SdkClient.getOrCreateInstance(c, null ,deviceId);

        mEventBus = EventBusManager.getInstance();
    }

    /**
     * DiveSdk manager constructor.
     *
     * @param c the context
     * @return the dive manager
     * @returns {DiveManager} instance. DiveManager created instance.
     */
    public static DiveManager createAndStart(Context c) {

        if (instance != null)
            throw new RuntimeException("Manager is already initialized");

        instance = new DiveManager(c, "pro");
        return instance;

    }


    /**
     * DiveSdk manager destroyer. Here must be destroyed every single manager of the application.
     */
    public static void destroy() {

        instance = null;

    }


}
