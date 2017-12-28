package sdk.dive.tv.ui.activities;

/**
 * Created by Emilio on 26/12/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.touchvie.sdk.model.Card;

import java.util.HashMap;

import sdk.client.dive.tv.utils.SharedPreferencesHelper;
import sdk.dive.tv.R;
import sdk.dive.tv.eventbus.OpenWeb;
import sdk.dive.tv.ui.DiveSdk;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.fragments.CardDetail;
import sdk.dive.tv.ui.fragments.Carousel;
import sdk.dive.tv.ui.fragments.DiveFragment;
import sdk.dive.tv.ui.fragments.FragmentError;
import sdk.dive.tv.ui.fragments.Section;
import sdk.dive.tv.ui.fragments.SeeMoreRelations;
import sdk.dive.tv.ui.fragments.WebView;
import sdk.dive.tv.ui.interfaces.ComponentsInterface;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

import static android.view.View.GONE;
import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;
import static sdk.dive.tv.ui.Utils.CAROUSEL_CARD;
import static sdk.dive.tv.ui.Utils.ERROR_TYPE;
import static sdk.dive.tv.ui.Utils.NETWORK_ERROR;

/**
 * Created by Emilio on 26/12/2017.
 */

public class DiveActivity extends FragmentActivity implements ComponentsInterface, Carousel.OnCarouselInteractionListener, CardDetail.OnCardDetailInteractionListener, SeeMoreRelations.OnSeeMoreRelationsListener {
    private String API_KEY;
    private String MOVIE_ID;
    private int MOVIE_TIME;
    private String CHANNEL_ID;
    private DiveActivity instance = null;
    private FrameLayout mBottomContainerLayout;
    private FrameLayout mBottomLayout;
    private FrameLayout mBottomOverlay;
    private FrameLayout mBottomError;
    private FrameLayout mProductLayout;
    private Fragment carouselFragment = null;
    private boolean isCarousel = false;
    private FragmentManager mManager = null;
    private DiveSdk dive = null;
    private boolean isApplicationClosing = false;
    private FragmentError networkError = null;

    private enum LayoutType {BOTTOM, OVERLAY, ERROR, PRODUCTS}

    private View lastFocusedView;
    private SharedPreferencesHelper settings;

    private String deviceId;
    private ComponentsInterface mListener;

    /**
     * Instantiates a new Video activity.
     */
    public DiveActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Privacy.
     */
    public static DiveActivity newInstance() {
        DiveActivity fragment = new DiveActivity();
        return fragment;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public DiveActivity getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.dive_activity);
        mManager = getSupportFragmentManager();
        Intent i = getIntent();
        if (i.getStringExtra(Utils.API_KEY)!=null) {
            API_KEY = i.getStringExtra(Utils.API_KEY);
        }
        if (i.getStringExtra(Utils.MOVIE_ID) != null) {
            MOVIE_ID = i.getStringExtra(Utils.MOVIE_ID);
        }
        if (i.getIntExtra(Utils.MOVIE_TIMESTAMP, 0) > 0) {
            MOVIE_TIME = i.getIntExtra(Utils.MOVIE_TIMESTAMP, 0);
        }
        if (i.getStringExtra(Utils.CHANNEL_ID) != null) {
            CHANNEL_ID = i.getStringExtra(Utils.CHANNEL_ID);
        }
        mBottomContainerLayout = (FrameLayout) findViewById(R.id.fragment_bottom_container);
        mBottomLayout = (FrameLayout) findViewById(R.id.fragment_bottom);
        mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
        mBottomError = (FrameLayout) findViewById(R.id.fragment_bottom_errors);
        mProductLayout = (FrameLayout) findViewById(R.id.product_container);
        settings = new SharedPreferencesHelper(getApplicationContext());
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        settings.storeDeviceId(deviceId);

        mListener = this;

        dive = new DiveSdk();
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        dive.initialize(deviceId, API_KEY, getApplicationContext());
        if (MOVIE_ID != null && MOVIE_TIME > -1) {
            carouselFragment = dive.VODStart(MOVIE_ID, MOVIE_TIME);
        } else if (CHANNEL_ID != null) {
            carouselFragment = dive.tvStart(CHANNEL_ID);
        }

        isCarousel = true;

        mManager.beginTransaction()
                .replace(R.id.fragment_container, carouselFragment, Utils.FragmentNames.DIVE.name())
                .addToBackStack(Utils.FragmentNames.DIVE.name())
                .commit();


    }


    @Override
    public void onBackPressed() {
        Fragment lastFragment = getTopFragment();

        if (lastFragment != null) {
            if (lastFragment instanceof Carousel) {
                isApplicationClosing = true;
                finish();
            } else if (lastFragment instanceof FragmentError) {
                if (mBottomError==null)
                    mBottomError = (FrameLayout) findViewById(R.id.fragment_bottom_errors);
                mBottomError.setVisibility(GONE);
                Fragment fragment = checkNextFragment();
                if (fragment != null) {
                    if (fragment instanceof Carousel) {
                        enableBottomLayout(true);
                        ((Carousel) fragment).getFocus();
                    } else if (fragment instanceof CardDetail) {
                        enableBottomLayout(false);
                        ((CardDetail) fragment).getFocus();
                    }
                }
            } else if (lastFragment instanceof SeeMoreRelations) {
                Fragment fragment = checkNextFragment();
                if (fragment != null) {
                    if (fragment instanceof Carousel) {
                        enableBottomLayout(true);
                        ((Carousel) fragment).getFocus();
                    }
                }
            } else if (lastFragment instanceof WebView) {
                mProductLayout.setVisibility(GONE);
                Fragment fragment = checkNextFragment();
                if (fragment != null) {
                    if (fragment instanceof Carousel) {
                        ((Carousel) fragment).getFocus();
                    } else if (fragment instanceof CardDetail) {
                        ((CardDetail) fragment).getFocus();
                    }
                }
            } else if (lastFragment instanceof CardDetail) {
                Fragment fragment = checkNextFragment();
                if (fragment != null) {
                    if (fragment instanceof Carousel) {
                        enableBottomLayout(true);
                        ((Carousel) fragment).getFocus();
                    }
                }
            }
        }


        int stackSize = mManager.getBackStackEntryCount();
        if (stackSize < 2 && !isApplicationClosing)

        {
            isApplicationClosing = true;
        } else

        {
            isApplicationClosing = false;
            super.onBackPressed();
        }
    }


    @Override
    public void removeCarousel() {
    }



    @Override
    public void removeCardDetail() {

    }

    @Override
    public void addMenu() {

    }

    @Override
    public void showMenu() {

    }

    @Override
    public void hideMenu() {

    }

    @Override
    public void removeMenu() {

    }

    @Override
    public void addPocket() {

    }

    @Override
    public void removePocket() {

    }

    @Override
    public void addTV() {

    }

    @Override
    public void addTVGrid() {

    }

    @Override
    public void removeTVGrid() {

    }

    @Override
    public void addSettings() {

    }

    @Override
    public void removeSettings() {

    }

    @Override
    public void addExitFragment() {

    }

    @Override
    public void removeExitFragment() {

    }

    @Override
    public void addStreamingPlayer(String movieId, String channelId) {

    }

    private Fragment getTopFragment() {

        if (mManager.getBackStackEntryCount() == 0) {
            return null;
        }

        Fragment top = null;
        for (int i = mManager.getBackStackEntryCount() - 1; i >= 0; i--) {
            if (top == null) {
                FragmentManager.BackStackEntry entry = mManager.getBackStackEntryAt(i);
                top = mManager.findFragmentByTag(entry.getName());
            }
            if (top == null) {
                continue;
            }
            return top;
        }
        return top;
    }

    private Fragment checkNextFragment() {

        if (mManager.getBackStackEntryCount() == 0) {
            return null;
        }

        Fragment top = null;
        Fragment next = null;
        for (int i = mManager.getBackStackEntryCount() - 1; i >= 0; i--) {
            if (top == null) {
                FragmentManager.BackStackEntry entry = mManager.getBackStackEntryAt(i);
                top = mManager.findFragmentByTag(entry.getName());
            } else if (next == null) {
                FragmentManager.BackStackEntry entry = mManager.getBackStackEntryAt(i);
                next = mManager.findFragmentByTag(entry.getName());
            }
            if (top == null || next == null) {
                continue;
            }

            return next;

        }
        return null;
    }



    @Override
    public void onCloseCardDetail(String cardId, boolean isLiked) {
        Fragment fragment = checkNextFragment();
        if (fragment != null) {
            if (fragment instanceof Carousel) {
                enableBottomLayout(true);
                ((Carousel) fragment).getFocus();
            } else if (fragment instanceof CardDetail) {
                //cardsLiked.put(cardId, String.valueOf(isLiked));
            } else if (fragment instanceof SeeMoreRelations) {
                enableBottomLayout(true);
            }
        }
//        onBackPressed();
    }

    private void enableBottomLayout(boolean enable) {
        if (mProductLayout==null){
            mProductLayout = (FrameLayout) findViewById(R.id.product_container);
        }
        if (mBottomOverlay==null){
            mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
        }
        if (mBottomLayout==null){
            mBottomLayout = (FrameLayout) findViewById(R.id.fragment_bottom);
        }
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

    private Fragment checkNextFragmentIsCarousel() {

        if (mManager.getBackStackEntryCount() == 0) {
            return null;
        }

        Fragment top = null;
        Fragment next = null;
        for (int i = mManager.getBackStackEntryCount() - 1; i >= 0; i--) {
            if (top == null) {
                FragmentManager.BackStackEntry entry = mManager.getBackStackEntryAt(i);
                top = mManager.findFragmentByTag(entry.getName());
            } else if (next == null) {
                FragmentManager.BackStackEntry entry = mManager.getBackStackEntryAt(i);
                next = mManager.findFragmentByTag(entry.getName());
            }
            if (top == null || next == null) {
                continue;
            }
            if (top instanceof CardDetail && next instanceof Carousel) {
                return next;
            } else {
                return null;
            }
        }
        return null;
    }

/*    public void showError(int errorCode) {
        mBottomError.setVisibility(View.VISIBLE);
        switch (errorCode) {
            case NETWORK_ERROR:
                enableLayout(LayoutType.ERROR);
                networkError = FragmentError.newInstance();

                Bundle args = new Bundle();
                args.putInt(ERROR_TYPE, NETWORK_ERROR);
                networkError.setArguments(args);
                mManager.beginTransaction()
                        .replace(R.id.fragment_bottom_errors, networkError, Utils.FragmentNames.FRAGMENT_ERROR.name())
                        .addToBackStack(Utils.FragmentNames.FRAGMENT_ERROR.name())
                        .commit();
                break;
            case Utils.GENERIC_ERROR:
                enableLayout(LayoutType.ERROR);
                networkError = FragmentError.newInstance();

                Bundle args4 = new Bundle();
                args4.putInt(ERROR_TYPE, Utils.GENERIC_ERROR);
                networkError.setArguments(args4);
                mManager.beginTransaction()
                        .replace(R.id.fragment_bottom_errors, networkError, Utils.FragmentNames.FRAGMENT_ERROR.name())
                        .addToBackStack(Utils.FragmentNames.FRAGMENT_ERROR.name())
                        .commit();
                break;

            default:
                mBottomError.setVisibility(GONE);
                break;
        }
    }*/


    @Override
    public void onCarouselClose() {

    }

    //OnCarouselInteractionListener
    @Override
    public void onCallCardDetail(String cardId, String versionId, Card.TypeEnum type) {
        addCardDetail(cardId, versionId, type);
    }

    @Override
    public void onShowMoreRelations(Card card) {
        enableBottomLayout(false);
        mBottomOverlay.setVisibility(View.VISIBLE);

        SeeMoreRelations seeMoreRelations = SeeMoreRelations.newInstance();

        Bundle args = new Bundle();
        args.putSerializable(CAROUSEL_CARD, card);
        seeMoreRelations.setArguments(args);

        mManager.beginTransaction()
                .add(R.id.fragment_bottom_overlay, seeMoreRelations, Utils.FragmentNames.SEE_MORE_RELATIONS.name())
                .addToBackStack(Utils.FragmentNames.SEE_MORE_RELATIONS.name())
                .commit();
    }

    @Override
    public void setOuterFocus(boolean focused) {

    }


    @Override
    public void setCurrentCarouselView(View view) {
        lastFocusedView = view;
    }

/*
    private void openProduct(OpenWeb web) {
//        if (SdkClient.getInstance().isNetworkOnline()) {
        enableLayout(LayoutType.PRODUCTS);
        WebView webView = new WebView();

        Bundle args = new Bundle();
        args.putString(sdk.dive.tv.ui.Utils.URL, web.getUrl());

        webView.setArguments(args);

        mManager.beginTransaction()
                .replace(R.id.product_container, webView, Utils.FragmentNames.PRODUCT.name())
                .addToBackStack(Utils.FragmentNames.PRODUCT.name())
                .commit();
//        } else {
//            showError(NETWORK_ERROR);
//        }
    }
*/


    @Override
    public void addCarousel(String apiKey, String movieId, String channelId, boolean isMovie, int movieTime, String previousScreen, String movieName) {
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        carouselFragment = Carousel.newInstance(apiKey, movieId, channelId, isMovie, movieTime, previousScreen, movieName, deviceId, this);
        isCarousel = true;

        mManager.beginTransaction()
                .replace(R.id.fragment_bottom, carouselFragment, Utils.FragmentNames.CAROUSEL.name())
                .addToBackStack(Utils.FragmentNames.CAROUSEL.name())
                .commit();

//        enableBottomLayout(true);
    }

    @Override
    public void addCardDetail(String cardId, String versionId, Card.TypeEnum typeOfCard) {
        if (mBottomOverlay==null){
            mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
        }

        enableBottomLayout(false);
        mBottomOverlay.setVisibility(View.VISIBLE);

        CardDetail cardDetail = CardDetail.newInstance(cardId, versionId, typeOfCard, null, isCarousel, mManager);

        mManager.beginTransaction()
                .replace(R.id.fragment_bottom_overlay, cardDetail, Utils.FragmentNames.CARD_DETAIL.name())
                .addToBackStack(Utils.FragmentNames.CARD_DETAIL.name())
                .commit();

    }

    @Override
    public void onSeeMoreRelationsClose() {
        enableBottomLayout(false);
        Fragment fragment = checkNextFragment();
        if (fragment != null) {
            if (fragment instanceof Carousel) {
                enableBottomLayout(true);
                ((Carousel) fragment).getFocus();
            }
        }
        onBackPressed();
    }

}
