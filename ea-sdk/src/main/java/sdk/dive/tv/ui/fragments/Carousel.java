package sdk.dive.tv.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import org.json.JSONArray;
import org.json.JSONException;

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
import sdk.dive.tv.ui.adapters.CategoriesAdapter;
import sdk.dive.tv.ui.builders.CardDetailJson;
import sdk.dive.tv.ui.cells.CarouselTvCell;
import sdk.dive.tv.ui.cells.SeeMoreTvCell;
import sdk.dive.tv.ui.data.ModuleStyle;
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
import static sdk.dive.tv.ui.Utils.STYLE;
import static sdk.dive.tv.ui.Utils.UPDATE_CURRENT_TIME;
import static sdk.dive.tv.ui.datawrappers.CardTypes.CHARACTER;
import static sdk.dive.tv.ui.datawrappers.CardTypes.FASHION;
import static sdk.dive.tv.ui.datawrappers.CardTypes.LOOK;
import static sdk.dive.tv.ui.datawrappers.CardTypes.PERSON;

public class Carousel extends Fragment implements Handler.Callback, CarouselFragmentListener, CarouselListener, CarouselInterface {

    private static OnCarouselInteractionListener mListener;

    protected Handler mUIHandler = null;

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

    private boolean newScene;
    private boolean isCommercialPending = false;
    private boolean isCommercialAdded = false;
    private boolean isPaused = false;
    private boolean isRewind = false;
    private boolean isPlaying = false;

    private Carousel instance;

    private SparseArray<ArrayList<CarouselTvCell>> allCarouselItems = null;
    private ArrayList<CarouselTvCell> carouselItems = null;
    private ArrayList<CarouselTvCell> carouselSceneItems = null;

    private TextView mTimeText = null;

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
    private FrameLayout mCloseLayout = null;
    private FrameLayout mMinimizeLayout = null;

    private ImageView mCloseImage = null;

    private Handler carouselHandler = null;

    private CarouselSpinner mCategories = null;

    private FrameLayout mSeparator1 = null;
    private FrameLayout mSeparator2 = null;

    private ArrayList<String> categories;
    private View lastClickedView = null;

    protected boolean allReadyCalled = false;
    private boolean sceneHasCommercials = false;
    private String style;
    private JSONArray styleConfig;
    private ModuleStyle styleCarousel;

    public Carousel() {
        // Required empty public constructor

    }

    public static Carousel newInstance(String apiKey, String movieId, String channelId, Boolean isMovie, int movieTime, String previousScreen, String movieName, String deviceId, String style, OnCarouselInteractionListener listener) {
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
        extras.putString(STYLE, style);
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
            style = extras.getString(Utils.STYLE);
        }
        if (SdkClient.getInstance() == null) {
            SdkClient.getOrCreateInstance(getContext(), apiKey, deviceId);
        } else {
            super.onCreate(savedInstanceState);
        }
        if (style!=null){
            if (style != null) {
                try {
                    styleConfig = new JSONArray(style);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            styleCarousel = loadStyleCarousel(styleConfig);
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
//                onCallEndMovie();
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
        carouselSceneItems = new ArrayList<>();
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

        if (styleCarousel!=null && styleCarousel.getIdModuleStyleData().get("backgroundColor")!=null){
            int backgroundColor = Color.parseColor(String.valueOf(styleCarousel.getIdModuleStyleData().get("backgroundColor")));
            carouselContainer.setBackgroundColor(backgroundColor);
        } else if (Utils.getCardDetailStyleconfiguration(getContext())!=null){
            int backgroundDefaultColor = Color.parseColor(String.valueOf(loadStyleCarousel(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("backgroundColor")));
            carouselContainer.setBackgroundColor(backgroundDefaultColor);
        }
        mLoadingLayer = (FrameLayout) view.findViewById(R.id.carousel_loading_layer);
        mLoadingLayer.setVisibility(View.VISIBLE);

        mCloseLayout = (FrameLayout) view.findViewById(R.id.carousel_button_close);
        mCloseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCarouselClose();
            }
        });

        mMinimizeLayout = (FrameLayout) view.findViewById(R.id.carousel_button_minimize);
        mMinimizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener instanceof DiveInterface)
                    ((DiveInterface) mListener).minimizeDive();
            }
        });

        mCloseImage = (ImageView) view.findViewById(R.id.carousel_image_close);

        final String[] arraySpinner = new String[]{
                getString(R.string.SELECTOR_ALL_CATEGORIES),
                getString(R.string.SELECTOR_CAST_CHARACTER),
                getString(R.string.SELECTOR_FASHION_BEAUTY),
                getString(R.string.SELECTOR_MUSIC),
                getString(R.string.SELECTOR_PLACES_TRAVEL),
                getString(R.string.SELECTOR_CARS_MORE),
                getString(R.string.SELECTOR_FUN_FACTS),
                getString(R.string.SELECTOR_OTHER_CATEGORIES)
        };
        mCategories = (CarouselSpinner) view.findViewById(R.id.carousel_categories_selector);

        CategoriesAdapter adapter = new CategoriesAdapter(getContext(), R.layout.category_row, android.R.id.text1, arraySpinner);
        adapter.setDropDownViewResource(R.layout.category_dropdown_row);

        mCategories.setAdapter(adapter);
        mCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO call FilterCardsBy Category
                categories.clear();
                String selected = arraySpinner[position];
                isFiltered = true;
                if (selected.equals(getString(R.string.SELECTOR_ALL_CATEGORIES))) {
                    isFiltered = false;
                    categories.clear();
                    filterCardsByCategory();
                    return;
                } else if (selected.equals(getString(R.string.SELECTOR_CAST_CHARACTER))) {
                    categories.add(Card.TypeEnum.CHARACTER.getValue());
                    categories.add(Card.TypeEnum.PERSON.getValue());
                } else if (selected.equals(getString(R.string.SELECTOR_FASHION_BEAUTY))) {
                    isFiltered = false;
                    categories.add(Card.TypeEnum.CHARACTER.getValue());
                    categories.add(Card.TypeEnum.PERSON.getValue());
                    categories.add(Card.TypeEnum.FASHION.getValue());
                    categories.add(Card.TypeEnum.LOOK.getValue());
                } else if (selected.equals(getString(R.string.SELECTOR_MUSIC))) {
                    categories.add(Card.TypeEnum.SONG.getValue());
                    categories.add(Card.TypeEnum.OST.getValue());
                } else if (selected.equals(getString(R.string.SELECTOR_PLACES_TRAVEL))) {
                    categories.add(Card.TypeEnum.LOCATION.getValue());
                } else if (selected.equals(getString(R.string.SELECTOR_CARS_MORE))) {
                    categories.add(Card.TypeEnum.VEHICLE.getValue());
                } else if (selected.equals(getString(R.string.SELECTOR_FUN_FACTS))) {
                    categories.add(Card.TypeEnum.TRIVIA.getValue());
                    categories.add(Card.TypeEnum.REFERENCE.getValue());
                    categories.add(Card.TypeEnum.QUOTE.getValue());
                } else if (selected.equals(getString(R.string.SELECTOR_OTHER_CATEGORIES))) {
//                    categories.add(Card.TypeEnum.ACTION_EMOTION.getValue());
                    categories.add(Card.TypeEnum.ART.getValue());
                    categories.add(Card.TypeEnum.BUSINESS.getValue());
                    categories.add(Card.TypeEnum.FAUNA_FLORA.getValue());
                    categories.add(Card.TypeEnum.FOOD_DRINK.getValue());
                    categories.add(Card.TypeEnum.HEALTH_BEAUTY.getValue());
                    categories.add(Card.TypeEnum.HISTORIC.getValue());
                    categories.add(Card.TypeEnum.HOME.getValue());
                    categories.add(Card.TypeEnum.LEISURE_SPORT.getValue());
                    categories.add(Card.TypeEnum.TECHNOLOGY.getValue());
                    categories.add(Card.TypeEnum.WEAPON.getValue());
                }
                if (categories != null && categories.size() > 0)
                    filterCardsByCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    public ModuleStyle loadStyleCarousel(JSONArray styleConfig) {
        ModuleStyle[] styles;
        ModuleStyle idStyle = null;
        if (styleConfig == null) {
            return null;
        } else {
            styles = new GsonBuilder().create().fromJson(styleConfig.toString(), ModuleStyle[].class); //TODO: test!!!
            if (styles == null) {
                return null;
            } else {
                if (styles.length > 0) {
                    for (ModuleStyle style : styles) {
                        if (style.getModuleName().equals("carousel")) {
                            idStyle = style;
                        }
                    }
                }
            }
            return idStyle;
        }
    }



    private void filterCardsByCategory() {
        if (!isAdded())
            return;

        mAdapter.notifyItemRangeRemoved(0, carouselItems.size());
        carouselItems.clear();

        ArrayList<CarouselTvCell> sceneItems = applyFilters(carouselSceneItems);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        resume = true;

        if (SdkClient.getInstance() != null) {
            SdkClient.getInstance().streamDisconnect();
        }
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
//                onCallEndMovie();
                break;
            case GO_TO_OFF_MOVIE:
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
                if (card != null && categoryName.equals(card.getCard().getType().getValue())) {
                    if (!isFiltered) {
                        if (card instanceof SeeMoreTvCell) {
                            filteredCells.add(card);
                        } else if ((categoryName.equals(CHARACTER.toLowerCase()) || categoryName.equals(PERSON.toLowerCase()))) {
                            if (card.getCard().getRelations().size() > 0 && i < (cellsToFilter.size() - 1)) {
                                CarouselTvCell nextCard = cellsToFilter.get(i + 1);
                                if (nextCard != null &&
                                        (FASHION.equals(nextCard.getCard().getType().getValue().toUpperCase()) || LOOK.equals(nextCard.getCard().getType().getValue().toUpperCase()) ||
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
            /*for (int i = 0; i < carouselCells.size(); i++) {
                CarouselTvCell tempCell = carouselCells.get(i);
                filteredCarouselCells.add(tempCell);
            }*/
            filteredCarouselCells.addAll(carouselCells);

            boolean firstCardsInScene = true;

            if (newScene) {
                int sceneSize = carouselItems.size();
                carouselItems.clear();
                carouselSceneItems.clear();
                mAdapter.notifyItemRangeRemoved(0, sceneSize);
                newScene = false;
            }

            ArrayList<CarouselTvCell> temp;

            temp = new ArrayList<>();

            temp.addAll(0, filteredCarouselCells);

            if (carouselItems.size() != 0)
                firstCardsInScene = false;


            carouselItems.addAll(0, applyFilters(filteredCarouselCells));
            carouselSceneItems.addAll(0, applyFilters(filteredCarouselCells));
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
            carouselSceneItems.clear();
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
