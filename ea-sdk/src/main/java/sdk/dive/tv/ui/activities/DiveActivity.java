package sdk.dive.tv.ui.activities;

/**
 * Created by Emilio on 26/12/2017.
 */

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.touchvie.sdk.model.Card;

import sdk.client.dive.tv.utils.SharedPreferencesHelper;
import sdk.dive.tv.R;
import sdk.dive.tv.ui.DiveSdk;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.fragments.CardDetail;
import sdk.dive.tv.ui.fragments.Carousel;
import sdk.dive.tv.ui.fragments.DiveFragment;
import sdk.dive.tv.ui.fragments.FragmentError;
import sdk.dive.tv.ui.fragments.SeeMoreRelations;
import sdk.dive.tv.ui.fragments.WebView;
import sdk.dive.tv.ui.interfaces.ComponentsInterface;
import sdk.dive.tv.ui.interfaces.DiveInterface;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;
import static sdk.dive.tv.ui.Utils.CAROUSEL_CARD;

/**
 * Created by Emilio on 26/12/2017.
 */

public class DiveActivity extends FragmentActivity implements ComponentsInterface, Carousel.OnCarouselInteractionListener, CardDetail.OnCardDetailInteractionListener, SeeMoreRelations.OnSeeMoreRelationsListener, DiveInterface {
    private String API_KEY;
    private String MOVIE_ID;
    private int MOVIE_TIME;
    private String CHANNEL_ID;
    private static DiveActivity instance = null;
    private FrameLayout mBottomContainerLayout;
    private FrameLayout mBottomLayout;
    private FrameLayout mBottomOverlay;
    private FrameLayout mBottomError;
    private FrameLayout mProductLayout;
    private Fragment carouselFragment = null;
    private boolean isCarousel = false;
    public FragmentManager mManager = null;
    private DiveSdk dive = null;
    private boolean isApplicationClosing = false;
    private FragmentError networkError = null;
    private OnDiveInteractionListener mMainListener;

    private enum LayoutType {BOTTOM, OVERLAY, ERROR, PRODUCTS}

    private View lastFocusedView;
    private SharedPreferencesHelper settings;

    private String deviceId;
    private ComponentsInterface mListener;

    private String style;
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
    public static DiveActivity newInstance(OnDiveInteractionListener listener) {
        DiveActivity fragment = new DiveActivity();
        return fragment;
    }

    public void setListener(OnDiveInteractionListener listener) {
        mMainListener = listener;
    }

    public OnDiveInteractionListener getListener() {
        return mMainListener;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DiveActivity getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.dive_activity);
        mManager = getSupportFragmentManager();
        mBottomContainerLayout = (FrameLayout) findViewById(R.id.fragment_bottom_container);
        mBottomLayout = (FrameLayout) findViewById(R.id.fragment_bottom);
        mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
        mBottomError = (FrameLayout) findViewById(R.id.fragment_bottom_errors);
        mProductLayout = (FrameLayout) findViewById(R.id.product_container);

        mListener = this;
    }

    @Override
    public void onBackPressed() {
        Fragment lastFragment = getTopFragment();

        if (lastFragment != null) {
            if (lastFragment instanceof Carousel) {
                isApplicationClosing = true;
                mMainListener.onDiveClose();
            } else if (lastFragment instanceof FragmentError) {
                if (mBottomError == null)
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
                enableBottomLayout(false);
            }
        }
        onBackPressed();
    }

    private void enableBottomLayout(boolean enable) {
        if (DiveFragment.getInstance()!=null) {
            DiveFragment.getInstance().enableBottomLayout(enable);
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
    public void minimizeDive() {
        mMainListener.onDiveMinimize();
    }

    @Override
    public void onCarouselClose() {
        mMainListener.onDiveClose();
    }

    //OnCarouselInteractionListener
    @Override
    public void onCallCardDetail(String cardId, String versionId, Card.TypeEnum type) {
        addCardDetail(cardId, versionId, type);
    }

    @Override
    public void onShowMoreRelations(Card card, ModuleStyle style) {
        if (mBottomOverlay == null) {
            mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
            mBottomLayout = (FrameLayout) findViewById(R.id.fragment_bottom);
        }

        enableBottomLayout(false);
        mBottomOverlay.setVisibility(View.VISIBLE);

        SeeMoreRelations seeMoreRelations = SeeMoreRelations.newInstance(style);

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

    @Override
    public void addCarousel(String apiKey, String movieId, String channelId, boolean isMovie, int movieTime, String previousScreen, String movieName, String style) {
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        this.style = style;
        carouselFragment = Carousel.newInstance(apiKey, movieId, channelId, isMovie, movieTime, previousScreen, movieName, deviceId, style, this);
        isCarousel = true;
        enableBottomLayout(true);

        mManager.beginTransaction()
                .replace(R.id.fragment_bottom, carouselFragment, Utils.FragmentNames.CAROUSEL.name())
                .addToBackStack(Utils.FragmentNames.CAROUSEL.name())
                .commit();

    }

    @Override
    public void addCardDetail(String cardId, String versionId, Card.TypeEnum typeOfCard) {
        if (mBottomOverlay == null) {
            mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
            mBottomLayout = (FrameLayout) findViewById(R.id.fragment_bottom);
        }

        enableBottomLayout(false);
        mBottomOverlay.setVisibility(View.VISIBLE);

        CardDetail cardDetail = CardDetail.newInstance(cardId, versionId, typeOfCard, style, isCarousel, mManager);

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

    public void enableLayout(LayoutType type) {
        if (mProductLayout==null){
            mProductLayout = (FrameLayout) findViewById(R.id.product_container);
        }
        if (mBottomError==null){
            mBottomError = (FrameLayout) findViewById(R.id.fragment_bottom_errors);
        }
        if (mBottomOverlay==null){
            mBottomOverlay = (FrameLayout) findViewById(R.id.fragment_bottom_overlay);
        }
        if (mBottomLayout==null){
            mBottomLayout = (FrameLayout) findViewById(R.id.fragment_bottom);
        }
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


    public interface OnDiveInteractionListener {
        void onDiveClose();
        void onDiveMinimize();
    }

}
