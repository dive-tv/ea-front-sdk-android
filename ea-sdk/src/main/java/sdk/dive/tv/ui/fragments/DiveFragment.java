package sdk.dive.tv.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import sdk.client.dive.tv.utils.SharedPreferencesHelper;
import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.interfaces.ComponentsInterface;

import static android.view.View.GONE;
import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;
import static sdk.dive.tv.ui.Utils.API_KEY;
import static sdk.dive.tv.ui.Utils.CHANNEL_ID;
import static sdk.dive.tv.ui.Utils.MOVIE_ID;
import static sdk.dive.tv.ui.Utils.MOVIE_TIMESTAMP;

/**
 * Created by emilio.alvarez on 22/11/2017.
 */

public class DiveFragment extends Fragment {
    private FrameLayout mBottomContainerLayout;
    private FrameLayout mBottomLayout;
    private FrameLayout mBottomOverlay;
    private FrameLayout mBottomError;
    private FrameLayout mProductLayout;
    private FragmentError networkError = null;
    private boolean isCarousel = false;
    private View lastFocusedView;
    private Carousel carouselFragment = null;
    private ComponentsInterface mListener;
    private static FragmentManager manager;
    private String movieId;
    private String apiKey;
    private String deviceId;
    private String channelId;
    private int movieTime;

    private SharedPreferencesHelper settings;

    private static DiveFragment instance;

    private boolean isApplicationClosing = false;

    private enum LayoutType {BOTTOM, OVERLAY, ERROR, PRODUCTS}

    @IntDef({KEY_EVENT_HANDLER_RESULT_PASSTHROUGH, KEY_EVENT_HANDLER_RESULT_NOT_HANDLED,
            KEY_EVENT_HANDLER_RESULT_HANDLED, KEY_EVENT_HANDLER_RESULT_DISPATCH_TO_OVERLAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyHandlerResultType {
    }

    public static final int KEY_EVENT_HANDLER_RESULT_PASSTHROUGH = 0;
    public static final int KEY_EVENT_HANDLER_RESULT_NOT_HANDLED = 1;
    public static final int KEY_EVENT_HANDLER_RESULT_HANDLED = 2;
    public static final int KEY_EVENT_HANDLER_RESULT_DISPATCH_TO_OVERLAY = 3;

    public DiveFragment() {

    }

    public static Fragment newInstance(String apiKey, String movieId, int timestamp) {

        Fragment fragment = new DiveFragment();
        Bundle extras = new Bundle();
        extras.putString(API_KEY, apiKey);
        extras.putString(MOVIE_ID, movieId);
        extras.putInt(MOVIE_TIMESTAMP, timestamp);
        fragment.setArguments(extras);
        return fragment;
    }

    public static Fragment newInstance(String apiKey, String channelId) {

        Fragment fragment = new DiveFragment();
        Bundle extras = new Bundle();
        extras.putString(API_KEY, apiKey);
        extras.putString(CHANNEL_ID, channelId);
        fragment.setArguments(extras);
        return fragment;
    }

    public static DiveFragment getInstance(){
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getArguments();
        if (extras != null) {
            apiKey = extras.getString(Utils.API_KEY);
            movieId = extras.getString(Utils.MOVIE_ID);
            movieTime = extras.getInt(Utils.MOVIE_TIMESTAMP);
            channelId = extras.getString(CHANNEL_ID);
        }

        View view = inflater.inflate(R.layout.dive_fragment, container, false);
        mBottomContainerLayout = (FrameLayout) view.findViewById(R.id.fragment_bottom_container);
        mBottomLayout = (FrameLayout) view.findViewById(R.id.fragment_bottom);
        mBottomOverlay = (FrameLayout) view.findViewById(R.id.fragment_bottom_overlay);
        mBottomError = (FrameLayout) view.findViewById(R.id.fragment_bottom_errors);
        mProductLayout = (FrameLayout) view.findViewById(R.id.product_container);

        manager = getActivity().getSupportFragmentManager();
        mListener.addCarousel(apiKey, movieId, channelId, true, movieTime, null, null);

        return view;
    }

    private void enableLayout(LayoutType type) {
        switch (type) {
            case BOTTOM:
                //CardDetail
                mBottomError.setVisibility(GONE);
                mBottomOverlay.setVisibility(GONE);
                mProductLayout.setVisibility(GONE);
                mBottomOverlay.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
                //Carousel & TvGrid
                mBottomLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                break;
            case OVERLAY:
                mBottomError.setVisibility(GONE);
                mBottomOverlay.setVisibility(View.VISIBLE);
                mProductLayout.setVisibility(GONE);
                //Carousel & TvGrid
                mBottomLayout.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
                //CardDetail
                mBottomOverlay.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                break;
            case ERROR:
                mBottomError.setVisibility(View.VISIBLE);
                mProductLayout.setVisibility(GONE);
                //Carousel & TvGrid
                mBottomLayout.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
                //CardDetail
                mBottomOverlay.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

                mBottomError.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                break;
            case PRODUCTS:
                mProductLayout.setVisibility(View.VISIBLE);
                //Carousel & TvGrid
                mBottomLayout.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
                //CardDetail
                mBottomOverlay.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

                mBottomError.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

                mProductLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                break;
        }
    }

    public void enableBottomLayout(boolean enable) {

        if (enable) {
            //CardDetail
            mBottomOverlay.setVisibility(GONE);
            mBottomOverlay.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
            //Carousel & TvGrid
            mBottomLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        } else {
            mBottomOverlay.setVisibility(View.VISIBLE);
            //Carousel & TvGrid
            mBottomLayout.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
            //CardDetail
            mBottomOverlay.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComponentsInterface) {
            mListener = (ComponentsInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCarouselInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        instance = this;
    }

}

