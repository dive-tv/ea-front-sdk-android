package sdk.dive.tv.ui.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.GsonBuilder;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import sdk.client.dive.tv.SdkClient;
import sdk.client.dive.tv.rest.callbacks.ClientCallback;
import sdk.client.dive.tv.rest.enums.RestAPIError;
import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.builders.DiveTvCardDetailJson;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.interfaces.DiveInterface;
import sdk.dive.tv.ui.managers.DiveTVTvCardDetailManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CardDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardDetail extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LISTENER = "listener";
    private static final String ARG_CARDID = "cardId";
    private static final String ARG_VERSIONID = "versionId";
    private static final String ARG_TYPEOFCARD = "typeOfCard";
    private static final String ARG_STYLE = "style";
    private static final String ARG_ISCAROUSEL = "iscarousel";

    // TODO: Rename and change types of parameters
    private String cardId;
    private String versionId;
    private Card.TypeEnum typeOfCard;
    private String style;
    private boolean isCardLiked;
    private boolean isCarousel;

    private static FragmentManager mManager = null;
    private DiveTvCardDetailJson cardDetail;
    private CardDetail instance;
    private LinearLayout mButtons;
    private FrameLayout mExitButton;
    private FrameLayout mMinimizeButton;
    private RelativeLayout mRelativeContainer ;
    private LinearLayout mContainer, mUpperContainer;
    private OnCardDetailInteractionListener mListener;
    private JSONArray styleConfig;
    private ModuleStyle styleCarddetail;


    public CardDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cardId     Parameter cardId.
     * @param typeOfCard Parameter typeOfCard.
     * @return A new instance of fragment CardDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static CardDetail newInstance(String cardId, String versionId, Card.TypeEnum typeOfCard, String style, boolean isCarousel, FragmentManager manager) {
        CardDetail fragment = new CardDetail();
        mManager = manager;
        Bundle args = new Bundle();
        args.putString(ARG_CARDID, cardId);
        args.putString(ARG_VERSIONID, versionId);
        args.putSerializable(ARG_TYPEOFCARD, typeOfCard);
        args.putString(ARG_STYLE, style);
        args.putBoolean(ARG_ISCAROUSEL, isCarousel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardId = getArguments().getString(ARG_CARDID);
            versionId = getArguments().getString(ARG_VERSIONID);
            typeOfCard = (Card.TypeEnum) getArguments().getSerializable(ARG_TYPEOFCARD);
            style = getArguments().getString(ARG_STYLE, null);
            isCarousel = getArguments().getBoolean(ARG_ISCAROUSEL, false);
        }
//        mManager = getActivity().getSupportFragmentManager();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_detail, container, false);

        mButtons = (LinearLayout) view.findViewById(R.id.carddetail_exit_btn);

        mButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCloseCardDetail(cardId, false);
            }
        });
        mExitButton = (FrameLayout) view.findViewById(R.id.carddetail_button_close);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCloseCardDetail(cardId, false);
            }
        });
        mMinimizeButton = (FrameLayout) view.findViewById(R.id.carddetail_button_minimize);
        mMinimizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener instanceof DiveInterface)
                    ((DiveInterface) mListener).minimizeDive();
            }
        });
        if (style!=null){
            if (style != null) {
                try {
                    styleConfig = new JSONArray(style);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            styleCarddetail = loadStyleCarddetail(styleConfig);
        }

        mContainer = (LinearLayout) view.findViewById(R.id.card_detail_container);
        mRelativeContainer = (RelativeLayout) view.findViewById(R.id.main_carddetail_layout);

        mUpperContainer = (LinearLayout) view.findViewById(R.id.card_detail_upper_container);
        if (styleCarddetail!=null && styleCarddetail.getIdModuleStyleData().get("backgroundColor")!=null){
            int backgroundColor = Color.parseColor(styleCarddetail.getIdModuleStyleData().get("backgroundColor").getValue());
            mRelativeContainer .setBackgroundColor(backgroundColor);
            mUpperContainer.setBackgroundColor(backgroundColor);
            mMinimizeButton.setBackground(Utils.makeButtonSelector(Color.parseColor(styleCarddetail.getIdModuleStyleData().get("selectedColor").getValue()),Color.parseColor(styleCarddetail.getIdModuleStyleData().get("unselectedColor").getValue()), styleCarddetail.getIdModuleStyleData().get("selectedColor").getValue()));
            mExitButton.setBackground(Utils.makeButtonSelector(Color.parseColor(styleCarddetail.getIdModuleStyleData().get("selectedColor").getValue()),Color.parseColor(styleCarddetail.getIdModuleStyleData().get("unselectedColor").getValue()), styleCarddetail.getIdModuleStyleData().get("selectedColor").getValue()));
        } else if (Utils.getCardDetailStyleconfiguration(getContext())!=null){
            int backgroundDefaultColor = Color.parseColor(loadStyleCarddetail(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("backgroundColor").getValue());
            int selectedDefaultColor = Color.parseColor(loadStyleCarddetail(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("selectedColor").getValue());
            String strSelectedDefaultColor = loadStyleCarddetail(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("selectedColor").getValue();
            int unselectedDefaultColor = Color.parseColor(loadStyleCarddetail(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("unselectedColor").getValue());
            mRelativeContainer .setBackgroundColor(backgroundDefaultColor);
            mUpperContainer.setBackgroundColor(backgroundDefaultColor);
            mMinimizeButton.setBackground(Utils.makeButtonSelector(selectedDefaultColor,unselectedDefaultColor, strSelectedDefaultColor));
            mExitButton.setBackground(Utils.makeButtonSelector(selectedDefaultColor,unselectedDefaultColor, strSelectedDefaultColor));
        }

        ClientCallback<Card> callback = new ClientCallback<Card>() {
            @Override
            public void onFailure(RestAPIError restAPIError) {

            }

            @Override
            public void onSuccess(Card card) {
                if (!isAdded())
                    return;

                if (card != null) {
                    Card.TypeEnum cardType = card.getType();
                    if (style!=null){
                        try {
                            styleConfig = new JSONArray(style);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        styleConfig = null;
                    }
                    JSONObject configJson = getConfigJSON(cardType);
                    cardDetail = new DiveTvCardDetailJson(getContext(), DiveTVTvCardDetailManager.class.getPackage().getName() + "." + DiveTVTvCardDetailManager.class.getSimpleName());
                    cardDetail.loadStyleConfig(styleConfig).loadDataConfig(configJson).buildAll(cardId, versionId, new ArrayList<RelationModule>(card.getRelations()), mManager, mContainer, mUpperContainer, null, !isCarousel, isCarousel, getActivity());
                }
            }
        };
        if (versionId!=null)
            SdkClient.getInstance().getCardVersion(cardId, versionId, "es-ES", callback);
        else
            SdkClient.getInstance().getCard(cardId, "es-ES", callback);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCardDetailInteractionListener) {
            mListener = (OnCardDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCardDetailInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private JSONObject getConfigJSON(Card.TypeEnum card) {
        String jsonString = null;

        int configFile;

        switch (card) {
            case MOVIE:
                configFile = R.raw.movie_tv;
                break;
            case SERIE:
                configFile = R.raw.serie_tv;
                break;
            case CHAPTER:
                configFile = R.raw.chapter_tv;
                break;
            case PERSON:
                configFile = R.raw.person_tv;
                break;
            case CHARACTER:
                configFile = R.raw.character_tv;
                break;
            case VEHICLE:
                configFile = R.raw.vehicle_tv;
                break;
            case FASHION:
                configFile = R.raw.fashion_tv;
                break;
            case HOME:
                configFile = R.raw.home_tv;
                break;
            case TECHNOLOGY:
                configFile = R.raw.technology_tv;
                break;
            case ART:
                configFile = R.raw.art_tv;
                break;
            case LOOK:
                configFile = R.raw.look_tv;
                break;
            case WEAPON:
                configFile = R.raw.weapon_tv;
                break;
            case LEISURE_SPORT:
                configFile = R.raw.leisure_sport_tv;
                break;
            case HEALTH_BEAUTY:
                configFile = R.raw.health_beauty_tv;
                break;
            case FOOD_DRINK:
                configFile = R.raw.food_drink_tv;
                break;
            case FAUNA_FLORA:
                configFile = R.raw.fauna_flora_tv;
                break;
            case BUSINESS:
                configFile = R.raw.business_tv;
                break;
            case LOCATION:
                configFile = R.raw.location_tv;
                break;
            case HISTORIC:
                configFile = R.raw.historic_tv;
                break;
            case OST:
                configFile = R.raw.ost_tv;
                break;
            case SONG:
                configFile = R.raw.song_tv;
                break;
            case TRIVIA:
                configFile = R.raw.trivia_tv;
                break;
            case REFERENCE:
                configFile = R.raw.reference_tv;
                break;
            default:
                return null;
        }

        try {
            Resources res = getContext().getResources();
            InputStream in_s = res.openRawResource(configFile);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            jsonString = new String(b);
        } catch (Exception e) {
            return null;
        }

        JSONObject configJson = null;
        try {
            configJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return configJson;

    }

    public void getFocus() {
        if (!isAdded())
            return;
        mButtons.requestFocus();
    }

    public ModuleStyle loadStyleCarddetail(JSONArray styleConfig) {
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
                        if (style.getModuleName().equals("carddetail")) {
                            idStyle = style;
                        }
                    }
                }
            }
            return idStyle;
        }
    }


    public interface OnCardDetailInteractionListener extends Serializable {
        void onCloseCardDetail(String cardId, boolean isLiked);
    }
}
