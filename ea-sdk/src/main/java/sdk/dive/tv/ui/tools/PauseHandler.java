package sdk.dive.tv.ui.tools;

/**
 * Created by Tagsonomy S.L. on 13/10/2015.
 */

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Message Handler class that supports buffering up of messages when the activity is paused i.e. in the background.
 */
public abstract class PauseHandler extends Handler {


    /**
     * Message Queue Buffer
     */
    private final List<Message> messageQueueBuffer = Collections.synchronizedList(new ArrayList<Message>());

    /**
     * Flag indicating the pause state
     */
    protected Activity activity;

    /**
     * Resume the handler.
     *
     * @param activity the activity
     */
    public final synchronized void resume(Activity activity) {
        this.activity = activity;

        while (messageQueueBuffer.size() > 0) {
            final Message msg = messageQueueBuffer.get(0);
            messageQueueBuffer.remove(0);
            sendMessage(msg);
        }
    }

    /**
     * Pause the handler.
     */
    public final synchronized void pause() {
        activity = null;
    }

    /**
     * Store the message if we have been paused, otherwise handle it now.
     *
     * @param msg Message to handle.
     */
    @Override
    public final synchronized void handleMessage(Message msg) {
        if (activity == null) {
            final Message msgCopy = new Message();
            msgCopy.copyFrom(msg);
            messageQueueBuffer.add(msgCopy);
        } else {
            processMessage(activity, msg);
        }
    }

    /**
     * Is handler paused boolean.
     *
     * @return the boolean
     */
    public boolean isHandlerPaused() {
        if (activity != null) {
            return false;
        }
        return true;
    }

    /**
     * Notification message to be processed. This will either be directly from
     * handleMessage or played back from a saved message when the activity was
     * paused.
     *
     * @param activity Activity owning this Handler that isn't currently paused.
     * @param message  Message to be handled
     */
    protected abstract void processMessage(Activity activity, Message message);

}