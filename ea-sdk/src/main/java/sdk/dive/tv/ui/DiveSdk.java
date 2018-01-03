package sdk.dive.tv.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.touchvie.sdk.model.ChannelStatus;
import com.touchvie.sdk.model.MovieStatus;

import java.util.List;

import sdk.client.dive.tv.SdkClient;
import sdk.client.dive.tv.rest.callbacks.ClientCallback;
import sdk.client.dive.tv.utils.SharedPreferencesHelper;
import sdk.dive.tv.BuildConfig;
import sdk.dive.tv.core.DiveService;
import sdk.dive.tv.ui.fragments.DiveFragment;

import static sdk.dive.tv.ui.Utils.API_KEY;
import static sdk.dive.tv.ui.Utils.DEVICE_ID;

/**
 * Created by emilio.alvarez on 21/11/2017.
 */

public class DiveSdk {
    private Handler mHandler;
    private ServiceWaitThread mThread;
    private SharedPreferencesHelper settings;
    private Context ctx;
    private SdkClient sdk;
    private FragmentManager mManager;
    private String apiKey;
    private String deviceId;

    public void initialize(String deviceId, String apiKey, Context ctx) {
        this.deviceId = deviceId;
        this.apiKey = apiKey;
        this.settings = new SharedPreferencesHelper(ctx);
        this.settings.storeApiKey(apiKey);
        this.settings.storeDeviceId(deviceId);
        this.ctx = ctx;
        launchService();
    }

    public boolean VODIsAvailable(List<String> movieId, ClientCallback<List<MovieStatus>> callback){
        sdk.getReadyMovies(movieId, callback);
        return false;
    }

    public Fragment VODStart(String movieId, int timestamp) {
        Fragment dive = DiveFragment.newInstance(apiKey, movieId, timestamp);
        return dive;
    }
    public boolean channelIsAvailable(List<String> channelId, ClientCallback<List<ChannelStatus>> callback){
        sdk.getReadyChannels(channelId, callback);
        return false;
    }

    public void vodPause(int timestamp){
        sdk.vodStreamPauseMessage(timestamp);
    }

    public void vodResume(int timestamp){
        sdk.vodStreamContinueMessage(timestamp);
    }

    public void vodSeek(int timestamp){
        sdk.vodStreamSetMessage(timestamp);
    }

    public void vodEnd(){
        sdk.vodStreamEndMessage();
    }

    public Fragment tvStart(String channelId){
        Fragment dive = DiveFragment.newInstance(apiKey, channelId);
        return dive;
    }

    private void launchService() {
        mHandler = new Handler();
        if (DiveService.isReady()) {
            onServiceReady();
        } else {
            Intent startService = new Intent();
            startService.setClass(ctx, DiveService.class);
            startService.putExtra(API_KEY, apiKey);
            startService.putExtra(DEVICE_ID, deviceId);
            ctx.getApplicationContext().startService(startService);
            mThread = new ServiceWaitThread();
            mThread.start();
        }
    }

    protected void onServiceReady() {
        sdk = SdkClient.getOrCreateInstance(ctx, apiKey ,deviceId);
    }


    private class ServiceWaitThread extends Thread {

        /**
         * The Timer.
         */
        int timer = 1000;

        public void run() {
            while (!DiveService.isReady()) {
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException("waiting thread sleep() has been interrupted");
                }
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onServiceReady();
                }
            }, timer);
            mThread = null;
        }
    }
}
