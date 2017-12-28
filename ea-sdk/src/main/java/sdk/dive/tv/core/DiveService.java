package sdk.dive.tv.core;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import static sdk.dive.tv.ui.Utils.API_KEY;
import static sdk.dive.tv.ui.Utils.DEVICE_ID;

/**
 * The TouchvieService lasts all the application lifecycle. It creates and destroys the Touchvie manager.
 */
public class DiveService extends Service {

    private static DiveService instance;
    private static DiveManager mManager;

    /**
     * Checks whether the service has created the DiveSdk manager or not by checking if the service is already created.
     *
     * @return the boolean
     * @returns {boolean} true if the service has been created, false otherwise.
     */
    public static boolean isReady() {

        return instance != null;
    }

    /**
     * Overrides the onBind method of the Touchvie service. It avoids creating a communication channel with the service.
     *
     * @param {Intent} intent.
     * @returns {IBinder} null.
     */

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Overrides the onCreate method of the Touchvie service. Creates the Touchvie manager instance.
     */
    @Override
    public void onCreate() {

        super.onCreate();

        mManager = DiveManager.createAndStart(getApplicationContext());

        instance = this; // instance is ready once the manager has been created
    }

    /**
     * Overrides the onDestroy method of the Touchvie service. Destroys the Touchvie manager and destroys himself.
     */

    @Override
    public synchronized void onDestroy() {

        super.onDestroy();
        instance = null;
        DiveManager.destroy();
        super.onDestroy();
    }

}
