package sdk.dive.tv.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sdk.client.dive.tv.SdkClient;
import sdk.client.dive.tv.socket.SocketListener;
import sdk.client.dive.tv.socket.model.StreamError;
import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.activities.DiveActivity;
import sdk.dive.tv.ui.adapters.CarouselCardsAdapter;
import sdk.dive.tv.ui.cells.CarouselTvCell;
import sdk.dive.tv.ui.cells.SeeMoreTvCell;
import sdk.dive.tv.ui.interfaces.CarouselInterface;
import sdk.dive.tv.ui.interfaces.DiveInterface;
import sdk.dive.tv.ui.listeners.CarouselFragmentListener;
import sdk.dive.tv.ui.listeners.CarouselListener;
import sdk.dive.tv.ui.logic.DiveCarouselLogic;
import sdk.dive.tv.ui.managers.CarouselPromptManager;
import sdk.dive.tv.ui.views.CarouselSpinner;
import sdk.dive.tv.ui.views.CarouselTVCellButton;
import sdk.dive.tv.ui.views.CarouselTVCellLinear;
import sdk.dive.tv.ui.views.CommercialView;
import sdk.dive.tv.ui.views.CustomLinearLayoutManagerCarousel;
import sdk.dive.tv.ui.views.EndView;
import sdk.dive.tv.ui.views.OffView;
import sdk.dive.tv.ui.views.ReadyView;

import static android.view.View.GONE;
import static sdk.dive.tv.ui.Utils.ALL_READY;
import static sdk.dive.tv.ui.Utils.API_KEY;
import static sdk.dive.tv.ui.Utils.CHANNEL_ID;
import static sdk.dive.tv.ui.Utils.CLOSE_CAROUSEL_ACTIVITY;
import static sdk.dive.tv.ui.Utils.DEVICE_ID;
import static sdk.dive.tv.ui.Utils.GO_TO_END_MOVIE;
import static sdk.dive.tv.ui.Utils.GO_TO_NEW_SCENE;
import static sdk.dive.tv.ui.Utils.GO_TO_OFF_MOVIE;
import static sdk.dive.tv.ui.Utils.GO_TO_PAUSE_MOVIE;
import static sdk.dive.tv.ui.Utils.GO_TO_PLAYING_MOVIE;
import static sdk.dive.tv.ui.Utils.IS_MOVIE;
import static sdk.dive.tv.ui.Utils.MOVIE_ID;
import static sdk.dive.tv.ui.Utils.MOVIE_NAME;
import static sdk.dive.tv.ui.Utils.MOVIE_SCENE_NR_RECEIVED;
import static sdk.dive.tv.ui.Utils.MOVIE_TIMESTAMP;
import static sdk.dive.tv.ui.Utils.MSG_CHECKED_CATEGORY;
import static sdk.dive.tv.ui.Utils.PREVIOUS_SCREEN;
import static sdk.dive.tv.ui.Utils.PUSH;
import static sdk.dive.tv.ui.Utils.SCENE_START_TIME;
import static sdk.dive.tv.ui.Utils.SEND_CARDLISTENER;
import static sdk.dive.tv.ui.Utils.SEND_EMPTY_SCENE;
import static sdk.dive.tv.ui.Utils.SEND_FEEDBACK;
import static sdk.dive.tv.ui.Utils.SEND_INITIAL_FEEDBACK;
import static sdk.dive.tv.ui.Utils.SEND_MENU_CARDS;
import static sdk.dive.tv.ui.Utils.SHOW_CAROUSEL_ERROR;
import static sdk.dive.tv.ui.Utils.UPDATE_CURRENT_TIME;
import static sdk.dive.tv.ui.datawrappers.CardTypes.CHARACTER;
import static sdk.dive.tv.ui.datawrappers.CardTypes.FASHION;
import static sdk.dive.tv.ui.datawrappers.CardTypes.LOOK;
import static sdk.dive.tv.ui.datawrappers.CardTypes.PERSON;

public class Carousel extends Fragment implements Handler.Callback, CarouselFragmentListener, CarouselListener, CarouselInterface {

    private static OnCarouselInteractionListener mListener;

    protected Handler mUIHandler = null;

    private Handler mWrapperHandler = null;

    private Handler carouselHandler = null;

    boolean resume = false;

    private boolean isWaitingMovie = false;

    boolean flowBlocked = false;

    protected String movieId;
    protected String apiKey;
    protected String deviceId;
    protected String channelId;
    private boolean isMovie;
    private int movieTime;
    private String previousScreen;
    protected DiveCarouselLogic carouselLogic;
    protected String movieName;

    private int userSceneNr;
    private int currentSceneNr;
    private boolean newScene;
    private boolean isCommercialPending = false;
    private boolean isCommercialAdded = false;
    private boolean isPaused = false;
    private boolean isRewind = false;
    private boolean isPlaying = false;

    private Carousel instance;

    private SparseArray<ArrayList<CarouselTvCell>> allCarouselItems = null;
    private ArrayList<CarouselTvCell> carouselItems = null;

//    private SceneNavigation lastSceneNavigation;

//    private CarouselThread mCarouselThread = null;

    //    private FrameLayout mMinimizeLayout = null;
    private FrameLayout mPreviousLayout = null;
    private FrameLayout mNextLayout = null;
    private FrameLayout mCurrentLayout = null;
    private FrameLayout mTimeLayout = null;
    private TextView mCurrentScene = null;

//    private ImageView mCloseImage = null;
//    private ImageView mPreviousImage = null;
//    private ImageView mNextImage = null;

    private TextView mTimeText = null;

    private CarouselSpinner mCategories = null;

    private FrameLayout mSeparator1 = null;
    private FrameLayout mSeparator2 = null;

    private RecyclerView mCarouselList = null;
    private LinearLayoutManager carouselLayoutManager;
    private CarouselCardsAdapter mAdapter = null;
    private boolean isFiltered = false;

    private CarouselPromptManager mPromptManager = null;
    private CommercialView mCommercialView = null;
    private ReadyView mReadyView = null;
    private EndView mEndView = null;
    private OffView mOffView = null;
    private TextView mCarouselBottomMsg;
    private ViewGroup mainPromptContainer;
    private FrameLayout mLoadingLayer;
    private ArrayList<String> categories;
    private View lastClickedView = null;

    protected boolean allReadyCalled = false;
    private boolean sceneHasCommercials = false;

    public Carousel() {
        // Required empty public constructor

    }

    public static Carousel newInstance(String apiKey, String movieId, String channelId, Boolean isMovie, int movieTime, String previousScreen, String movieName, String deviceId, OnCarouselInteractionListener listener) {
        Carousel fragment = new Carousel();
        mListener = listener;
        Bundle extras = new Bundle();
        extras.putString(API_KEY, apiKey);
        extras.putString(DEVICE_ID, deviceId);
        extras.putString(MOVIE_ID, movieId);
        extras.putString(CHANNEL_ID, channelId);
        extras.putBoolean(IS_MOVIE, isMovie);
        extras.putInt(MOVIE_TIMESTAMP, movieTime);
        extras.putString(PREVIOUS_SCREEN, previousScreen);
        extras.putString(MOVIE_NAME, movieName);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Typeface latoSemibold = Utils.getFont(getContext(), Utils.TypeFaces.LATO_SEMIBOLD);
        Bundle extras = getArguments();
        if (extras != null) {
            apiKey = extras.getString(API_KEY);
            deviceId = extras.getString(DEVICE_ID);
            movieId = extras.getString(MOVIE_ID);
            isMovie = extras.getBoolean(Utils.IS_MOVIE);
            movieTime = extras.getInt(Utils.MOVIE_TIMESTAMP);
            channelId = extras.getString(CHANNEL_ID);
            previousScreen = extras.getString(Utils.PREVIOUS_SCREEN);
            movieName = extras.getString(Utils.MOVIE_NAME);
        }
        if (SdkClient.getInstance() == null) {
            SdkClient.getOrCreateInstance(getContext(), apiKey, deviceId);
        } else {
            super.onCreate(savedInstanceState);
        }

        mUIHandler = new Handler(this);
        SocketListener mSocketListener = new SocketListener() {
            @Override
            public void onMovieStartEventReceived(String s) {
                newScene = true;
                setPlayingMovie(true);
            }

            @Override
            public void onMovieEndEventReceived() {
                DiveActivity.getInstance().getListener().onDiveClose();
            }

            @Override
            public void onSceneStartEventReceived(List<Card> cards) {
                setPlayingMovie(true);
                Message msg = new Message();
                msg.what = Utils.GO_TO_PLAYING_MOVIE;
                mUIHandler.sendMessage(msg);
                newScene = true;
                Message msg2 = new Message();
                msg.what = Utils.PUSH;
                msg.obj = new ArrayList<>(cards);
                mUIHandler.sendMessage(msg2);

//                onRowsToDraw(carouselLogic.processData(new ArrayList<>(cards)));
            }

            @Override
            public void onSceneUpdateEventReceived(List<Card> cards) {
                Message msg = new Message();
                msg.what = Utils.PUSH;
                msg.obj = new ArrayList<>(cards);
                mUIHandler.sendMessage(msg);
            }

            @Override
            public void onSceneEndEventReceived() {
                newScene = true;
                setPlayingMovie(true);
                Message msg = new Message();
                msg.what = GO_TO_NEW_SCENE;
                mUIHandler.sendMessage(msg);

            }

            @Override
            public void onPausedStartEventReceived() {
                setPlayingMovie(true);
                Message msg = new Message();
                msg.what = Utils.GO_TO_PAUSE_MOVIE;
                mUIHandler.sendMessage(msg);
            }

            @Override
            public void onPausedEndEventReceived() {
                setPlayingMovie(true);
                Message msg = new Message();
                msg.what = Utils.GO_TO_PLAYING_MOVIE;
                mUIHandler.sendMessage(msg);
            }

            @Override
            public void onErrorReceived(StreamError error) {
            }
        };
        if (channelId != null)
            SdkClient.getInstance().tvChannelStreamConnect(channelId, mSocketListener);

        else if (movieId != null)
            SdkClient.getInstance().vodStreamConnect(movieId, movieTime, mSocketListener);

        resume = true;

        mUIHandler = new Handler(this);

        isPlaying = true;
        instance = this;

        allCarouselItems = new SparseArray<>();
        carouselItems = new ArrayList<>();
//        lastSceneNavigation = new SceneNavigation();

        categories = new ArrayList<>();
        /*FrontBlackboard.init();
        FrontBlackboard.getInstance().setInterfaces(RestManager.getInstance(), AnalyticsManager.getInstance());
        FrontBlackboard.getInstance().setMovieId(movieId);

        mCarouselThread = new CarouselThread("CarouselThread", Thread.MAX_PRIORITY);
        mCarouselThread.setCallback(mUIHandler);

        mCarouselThread.init(getContext(), this, FrontBlackboard.getInstance().getSdkFrontInterface(), DiveSceneManager.class.getPackage().getName() + "." + DiveSceneManager.class.getSimpleName());
        mCarouselThread.start();*/
        carouselLogic = new DiveCarouselLogic(getContext());
        onCallTDTPlayingMovie();

        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        RelativeLayout carouselContainer = (RelativeLayout) view.findViewById(R.id.carousel_container);
        carouselContainer.getViewTreeObserver().addOnGlobalFocusChangeListener(
                new ViewTreeObserver.OnGlobalFocusChangeListener() {
                    @Override
                    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                        flowBlocked = ((newFocus instanceof CarouselTVCellLinear) || newFocus instanceof CarouselTVCellButton);
                    }
                });

        mLoadingLayer = (FrameLayout) view.findViewById(R.id.carousel_loading_layer);
        mLoadingLayer.setVisibility(View.VISIBLE);

        mCarouselList = (RecyclerView) view.findViewById(R.id.carousel_card_list);

        ViewGroup promptContainer = (ViewGroup) view.findViewById(R.id.socket_events_layout);
        mainPromptContainer = (ViewGroup) view.findViewById(R.id.socket_events_main_layout);
        mCommercialView = (CommercialView) getActivity().getLayoutInflater().inflate(R.layout.commercial, promptContainer, false);
        mCommercialView.setListener(this);
        mReadyView = (ReadyView) getActivity().getLayoutInflater().inflate(R.layout.ready, promptContainer, false);
        mReadyView.setListener(this);

        mEndView = (EndView) getActivity().getLayoutInflater().inflate(R.layout.end, promptContainer, false);
        mEndView.setListener(this);

        mOffView = (OffView) getActivity().getLayoutInflater().inflate(R.layout.off, promptContainer, false);
        mOffView.setListener(this);

        carouselLayoutManager = new CustomLinearLayoutManagerCarousel(getContext(), CustomLinearLayoutManagerCarousel.HORIZONTAL, false);
        mCarouselList.setLayoutManager(carouselLayoutManager);

        mAdapter = new CarouselCardsAdapter(getContext(), carouselItems, isFiltered, mListener, instance);
        mCarouselList.setAdapter(mAdapter);

        mCarouselList.setItemViewCacheSize(0);

        mCarouselBottomMsg = (TextView) view.findViewById(R.id.carousel_bottom_msg);
        mCarouselBottomMsg.setTypeface(latoSemibold);

        mPromptManager = new CarouselPromptManager(getActivity(), mainPromptContainer, mCarouselList, promptContainer, mCommercialView, mReadyView, mEndView, mOffView);

//        hideNotReadyButtons();

        return view;
    }


/*
    private void filterCardsByCategory() {
        if (!isAdded())
            return;

        if (allCarouselItems.get(userSceneNr) == null || allCarouselItems.get(userSceneNr).size() == 0)
            return;

        mAdapter.notifyItemRangeRemoved(0, carouselItems.size());
        carouselItems.clear();

        ArrayList<CarouselTvCell> sceneItems = applyFilters(allCarouselItems.get(userSceneNr));

        carouselItems.addAll(sceneItems);
        mAdapter = null;
        mCarouselList.setAdapter(null);
        mAdapter = new CarouselCardsAdapter(getContext(), carouselItems, isFiltered, mListener, instance);
        mCarouselList.setAdapter(mAdapter);

        if (carouselItems.size() > 0) {
            mCarouselList.requestFocus();
        } else {
            mCategories.requestFocus();
        }


    }
*/

    private void showNewCardsMsg(int number) {
        if (!isAdded())
            return;

        if (number == 1) {
            mCarouselBottomMsg.setText(getString(R.string.CAROUSEL_NEW_CARD_ADDED));
        } else {
            mCarouselBottomMsg.setText(getString(R.string.CAROUSEL_NEW_CARDS_ADDED, number));
        }
        mCarouselBottomMsg.setVisibility(View.VISIBLE);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCarouselBottomMsg.setVisibility(GONE);
            }
        };
        mUIHandler.postDelayed(runnable, 4000);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        resume = true;

        if (SdkClient.getInstance() != null) {
            SdkClient.getInstance().streamDisconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /*private void hideNotReadyButtons() {
        if (!isAdded())
            return;

        mPreviousLayout.setVisibility(GONE);
        mNextLayout.setVisibility(GONE);
        mCurrentLayout.setVisibility(GONE);
        mTimeLayout.setVisibility(GONE);
        mCategories.setVisibility(GONE);
        mSeparator1.setVisibility(View.INVISIBLE);
        mSeparator2.setVisibility(GONE);
        mLoadingLayer.setVisibility(View.VISIBLE);
    }*/

   /* protected void showReadyButtons() {
        if (!isAdded())
            return;

        if (currentSceneNr > 0) {
            mPreviousLayout.setVisibility(View.VISIBLE);
        }

        mTimeLayout.setVisibility(View.VISIBLE);
        mTimeText.setVisibility(View.VISIBLE);
        mCategories.setVisibility(View.VISIBLE);
        mSeparator1.setVisibility(View.VISIBLE);
        mSeparator2.setVisibility(View.VISIBLE);
        mLoadingLayer.setVisibility(View.GONE);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        resume = true;

        if (SdkClient.getInstance() != null) {
            SdkClient.getInstance().streamDisconnect();
        }
        //  destroyCarousel();
    }

    @Override
    public boolean handleMessage(Message msg) {

        if (!isAdded() && msg.what != CLOSE_CAROUSEL_ACTIVITY) {
            return false;
        }
        switch (msg.what) {
            case PUSH:
                if (mLoadingLayer.getVisibility() == View.VISIBLE)
                    mLoadingLayer.setVisibility(View.GONE);
                onRowsToDraw(carouselLogic.processData((ArrayList<Card>) msg.obj));
                break;
            case SEND_CARDLISTENER:
//                onSendCarouselCardListenerReceived();
                break;
            case GO_TO_PAUSE_MOVIE:
                isPaused = true;
                setPlayingMovie(true);
                onCommercialCellsReceived();
                break;
            case GO_TO_PLAYING_MOVIE:
                if (isPaused) {
                    if (mCommercialView.isAttachedToWindow()) {
                        deleteCommercialScene();
                    }
                    isPaused = false;
                } else if (isWaitingMovie) {
                    deleteWaitingScene();
                    isWaitingMovie = false;
                }
                setPlayingMovie(true);
                break;
            case GO_TO_NEW_SCENE:
                onRowsToDraw(new ArrayList<CarouselTvCell>());
                break;
            case GO_TO_END_MOVIE:
                DiveActivity.getInstance().getListener().onDiveClose();
                break;
            case GO_TO_OFF_MOVIE:
                destroyCarousel();
                onCallOffMovie();
                break;
            case MOVIE_SCENE_NR_RECEIVED:
                break;
            case UPDATE_CURRENT_TIME:
                movieTime = (int) msg.obj;
                updateTimeLineCurrent((float) msg.obj);
                if (!isRewind) {
                    updateTimeLineUser((float) msg.obj, isPaused);
                }
                break;
            case SEND_FEEDBACK:
                break;
            case SEND_INITIAL_FEEDBACK:
                break;
            case SEND_EMPTY_SCENE:
                break;
            case SHOW_CAROUSEL_ERROR:
                onCarouselError();
                break;
            case SEND_MENU_CARDS:
                break;
            case MSG_CHECKED_CATEGORY:
                break;
            case SCENE_START_TIME:
                float startTime = (float) msg.obj;
                sendNewSceneEvent(startTime);
                break;
            case CLOSE_CAROUSEL_ACTIVITY:
                onDestroyAndCloseCarousel();
                break;
            case ALL_READY:
                allReady();
                break;
        }
        return false;

    }


    @Override
    public void onCallReadyMovie() {
        if (!isAdded())
            return;

        isWaitingMovie = true;
        mPromptManager.showReady();
    }

    @Override
    public void onCallEndMovie() {
        if (!isAdded())
            return;

        mPromptManager.showEnd();
    }

    @Override
    public void onCallTDTPlayingMovie() {
        if (!isAdded())
            return;

        isWaitingMovie = true;
    }

    @Override
    public void onCallPlayingMovie() {
        if (!isAdded())
            return;

        if (isPlaying) {
            isWaitingMovie = false;
        }
    }

    @Override
    public void setPlayingMovie(boolean playing) {
        if (!isAdded())
            return;

        isPlaying = playing;
    }

    @Override
    public void setFeedbackIsOpen(boolean feedbackIsOpen) {

    }

    @Override
    public void onCallFeedBack(int feedbackOpts, String cardId, String movieId, double timestamp) {

    }

    @Override
    public void onCallExitCarousel() {
        //TODO: manage this case.
    }

    @Override
    public void onDestroyAndCloseCarousel() {
        if (!isAdded())
            return;

        //destroyCarousel();
        if (SdkClient.getInstance() != null)
            SdkClient.getInstance().streamDisconnect();

    }

    @Override
    public void onSetMovieLength(float movieLength) {

    }

    @Override
    public void updateTimeLineUser(float time, boolean isPaused) {
        if (!isAdded())
            return;


        this.isPaused = isPaused;
        if (time >= 0) {
            mTimeText.setText(Utils.convertSecondstoHHMMSS((long) time));
        } else {
            mTimeText.setText("");
        }

    }

    @Override
    public void updateTimeLineCurrent(float time) {

    }

    @Override
    public void updateCarouselMsgBar(int newCards, boolean showBar, boolean isCurrentScene) {

    }

    @Override
    public void onShowCarouselError() {
        //TODO
    }

    @Override
    public void setCarouselBtnWithCurrentState() {

    }

    @Override
    public void showBottomButton() {

    }


    @Override
    public void onCallOffMovie() {
        if (!isAdded())
            return;

        mPromptManager.showOff();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (SdkClient.getInstance() != null) {
            SdkClient.getInstance().streamDisconnect();
        }
    }

    private ArrayList<CarouselTvCell> applyFilters(ArrayList<CarouselTvCell> cellsToFilter) {

        ArrayList<CarouselTvCell> filteredCells = new ArrayList<>();

        for (int i = 0; i < cellsToFilter.size(); i++) {
            CarouselTvCell card = cellsToFilter.get(i);

            for (String categoryName : categories) {
                if (card != null && categoryName.equals(card.getCard().getType())) {
                    if (!isFiltered) {
                        if (card instanceof SeeMoreTvCell) {
                            filteredCells.add(card);
                        } else if ((categoryName.equals(CHARACTER) || categoryName.equals(PERSON))) {
                            if (card.getCard().getRelations().size() > 0 && i < (cellsToFilter.size() - 1)) {
                                CarouselTvCell nextCard = cellsToFilter.get(i + 1);
                                if (nextCard != null &&
                                        (FASHION.equals(nextCard.getCard().getType()) || LOOK.equals(nextCard.getCard().getType()) ||
                                                nextCard instanceof SeeMoreTvCell)) {
                                    filteredCells.add(card);
                                }
                            }
                        } else {
                            filteredCells.add(card);
                        }
                    } else if (!(card instanceof SeeMoreTvCell)) {
                        filteredCells.add(card);
                    }
                    break;
                }
            }
        }

        if (categories.size() == 0) {
            filteredCells.addAll(cellsToFilter);
        }

        return filteredCells;
    }

    @Override
    public void onRowsToDraw(ArrayList<CarouselTvCell> carouselCells) {
        if (!isAdded())
            return;

        if (carouselCells.size() > 0) {
            if (carouselItems.size() == 0)
                sceneHasCommercials = false;

            ArrayList<CarouselTvCell> filteredCarouselCells = new ArrayList<>();
            for (int i = 0; i < carouselCells.size(); i++) {
                CarouselTvCell tempCell = carouselCells.get(i);
                filteredCarouselCells.add(tempCell);
            }

            boolean firstCardsInScene = true;

            if (newScene) {
                int sceneSize = carouselItems.size();
                carouselItems.clear();
                mAdapter.notifyItemRangeRemoved(0, sceneSize);
                newScene = false;
            }

            ArrayList<CarouselTvCell> temp;

            temp = new ArrayList<>();

            temp.addAll(0, filteredCarouselCells);

            if (carouselItems.size() != 0)
                firstCardsInScene = false;


            carouselItems.addAll(0, applyFilters(filteredCarouselCells));
            final int size = applyFilters(filteredCarouselCells).size();
            int pos = carouselLayoutManager.findFirstVisibleItemPosition();
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyItemRangeInserted(0, size);
                }
            });
            if (pos > 0) {
                final int position = pos;
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCarouselList.smoothScrollToPosition(position);
                    }
                });
            }
            if (carouselLayoutManager.findFirstCompletelyVisibleItemPosition() >= 0) {
                if (carouselCells.size() > 0 && !firstCardsInScene) {
                    if (flowBlocked) {
                        final int filteredCarouselCellsSize = filteredCarouselCells.size();
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCarouselList.stopScroll();
                                showNewCardsMsg(filteredCarouselCellsSize);
                            }
                        });

                    } else {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCarouselList.scrollToPosition(0);
                            }
                        });
                    }
                }
            } else {
                if (firstCardsInScene) {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCarouselList.scrollToPosition(0);
                        }
                    });
                }

            }
        } else if (newScene) {
            int sceneSize = carouselItems.size();
            carouselItems.clear();
            mAdapter.notifyItemRangeRemoved(0, sceneSize);
            newScene = false;
        }

    }


    @Override
    public void onCommercialCellsReceived() {
        if (!isAdded())
            return;


        this.isPaused = true;

        if (this.isRewind) {
            return;
        }

        mPromptManager.showCommercial();
    }

    protected void allReady() {
        if (!isAdded())
            return;
        allReadyCalled = true;
    }

    @Override
    public void setHandler(Handler handler) {
        carouselHandler = handler;
    }

    private void deleteCommercialScene() {
        if (!isAdded())
            return;
        mPromptManager.hideOverlays(CarouselPromptManager.FLAG_HIDE_OVERLAYS_DEFAULT);
    }

    private void deleteWaitingScene() {
        if (!isAdded())
            return;
        mPromptManager.hideOverlays(CarouselPromptManager.FLAG_HIDE_OVERLAYS_DEFAULT);
    }

    private void destroyCarousel() {
        if (!isAdded())
            return;
    }

    private void onCarouselError() {
        onShowCarouselError();
    }

    private void sendNewSceneEvent(float sceneStartTime) {
        //TODO: analytics
    }

    @Override
    public void setCardsAreLiked(HashMap<String, Boolean> likedCards) {
        if (!isAdded())
            return;

        if (allCarouselItems != null) {
            for (int i = 0; i < allCarouselItems.size(); i++) {
                int key = allCarouselItems.keyAt(i);
                // get the object by the key.
                ArrayList<CarouselTvCell> cells = allCarouselItems.get(key);
                if (cells == null || cells.size() == 0) {
                    continue;
                }
                for (int j = 0; j < cells.size(); j++) {
                    if (cells.get(j) != null && cells.get(j).getCard().getCardId() != null) {

                        if (likedCards.containsKey(cells.get(j).getCard().getCardId())) {
                            if (cells.get(j).getCard().getUser() != null) {
                                cells.get(j).getCard().getUser().setIsLiked(likedCards.get(cells.get(j).getCard().getCardId()));
                            }
                        }

                        if (cells.get(j).getCard().getRelations() != null && cells.get(j).getCard().getRelations().size() != 0) {

                            for (RelationModule relation : cells.get(j).getCard().getRelations()) {

                                if (relation instanceof Duple) {
                                    for (DupleData dupleRelData : ((Duple) relation).getData()) {
                                        if (dupleRelData.getFrom() != null && dupleRelData.getFrom().getCardId() != null) {
                                            if (likedCards.containsKey(dupleRelData.getFrom().getCardId())) {
                                                if (dupleRelData.getFrom().getUser() != null) {
                                                    dupleRelData.getFrom().getUser().setIsLiked(likedCards.get(dupleRelData.getFrom().getCardId()));
                                                }
                                            }
                                        }

                                        if (dupleRelData.getTo() != null && dupleRelData.getTo().getCardId() != null) {
                                            if (likedCards.containsKey(dupleRelData.getTo().getCardId())) {
                                                if (dupleRelData.getTo().getUser() != null) {
                                                    dupleRelData.getTo().getUser().setIsLiked(likedCards.get(dupleRelData.getTo().getCardId()));
                                                }
                                            }
                                        }
                                    }
                                } else if (relation instanceof Single) {
                                    for (Card singleRelData : ((Single) relation).getData()) {
                                        if (singleRelData.getCardId() != null) {
                                            if (likedCards.containsKey(singleRelData.getCardId())) {
                                                if (singleRelData.getUser() != null) {
                                                    singleRelData.getUser().setIsLiked(likedCards.get(singleRelData.getCardId()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void closeOverlay() {
        mPromptManager.hideOverlays(CarouselPromptManager.FLAG_HIDE_OVERLAYS_DEFAULT);

        if (lastClickedView != null && lastClickedView.isShown()) {
            lastClickedView.requestFocus();
        } else {
            mCarouselList.requestFocus();
        }
    }

    @Override
    public void clickedView(View view) {
        lastClickedView = view;
        mListener.setCurrentCarouselView(lastClickedView);
    }

    @Override
    public CarouselPromptManager getOverlayManager() {
        if (!isAdded())
            return null;

        return mPromptManager;
    }

    @Override
    public void onOKMovieEnd() {
        if (!isAdded())
            return;
        mListener.onCarouselClose();
    }

    @Override
    public void onOKMovieOff() {
        if (!isAdded())
            return;
        mListener.onCarouselClose();
    }

    public void getFocus() {
        if (!isAdded())
            return;

        if (mCommercialView.isAttachedToWindow()) {

            mCommercialView.getFocus();
            return;
        }

        if (mEndView.isAttachedToWindow()) {
            mEndView.requestFocus();
            mEndView.getFocus();
            return;
        }

        if (mOffView.isAttachedToWindow()) {
            mOffView.getFocus();
            return;
        }

        if (mReadyView.isAttachedToWindow()) {
            mReadyView.getFocus();
            return;
        }

        if (lastClickedView != null && lastClickedView.isShown()) {
            lastClickedView.requestFocus();
        } else if (mCarouselList.getAdapter().getItemCount() > 0) {
            mCarouselList.requestFocus();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCarouselInteractionListener {
        void onCarouselClose();

        void onCallCardDetail(String cardId, String versionId, Card.TypeEnum type);

        void onShowMoreRelations(Card card);

        void setOuterFocus(boolean b);

        void setCurrentCarouselView(View view);
    }
}
