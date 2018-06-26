package sdk.dive.tv.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.gson.GsonBuilder;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.adapters.CarouselCardsAdapter;
import sdk.dive.tv.ui.cells.CarouselTvCell;
import sdk.dive.tv.ui.cells.GenericTvCell;
import sdk.dive.tv.ui.cells.PersonTvCell;
import sdk.dive.tv.ui.cells.SeeMoreTvCell;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.interfaces.CarouselInterface;
import sdk.dive.tv.ui.managers.CarouselPromptManager;
import sdk.dive.tv.ui.relations.LocalContentTypes;

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.FEATURED_IN;
import static sdk.dive.tv.ui.Utils.CAROUSEL_CARD;
import static sdk.dive.tv.ui.Utils.JSONSTYLE;
import static sdk.dive.tv.ui.Utils.STYLE;
import static sdk.dive.tv.ui.relations.LocalContentTypes.HOME_OFF_RELATION;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Carousel.OnCarouselInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeeMoreRelations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeeMoreRelations extends Fragment implements CarouselInterface {

    SeeMoreRelations instance;
    private Carousel.OnCarouselInteractionListener mCarouselListener;
    private OnSeeMoreRelationsListener mListener;
    private Card carouselCard;
    private ArrayList<CarouselTvCell> carouselItems;
    private CarouselCardsAdapter mAdapter;

    private RelativeLayout mSeeMoreContainer;
    private FrameLayout mCloseLayout;
    private RecyclerView mCarouselList;
    private View lastClickedView = null;
    private ModuleStyle style;
    private JSONArray styleConfig;
    private ModuleStyle styleCarousel;
    private String jsonStyle;

    public SeeMoreRelations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardDetail.
     */
    public static SeeMoreRelations newInstance(ModuleStyle style, String jsonstyle) {

        SeeMoreRelations fragment = new SeeMoreRelations();
        Bundle extras = new Bundle();
        extras.putSerializable(STYLE, style);
        extras.putSerializable(JSONSTYLE, jsonstyle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Bundle extras = getArguments();
        if (extras != null) {
            style = (ModuleStyle) extras.getSerializable(STYLE);
            carouselCard = (Card) extras.getSerializable(CAROUSEL_CARD);
            jsonStyle = (String) extras.getSerializable(JSONSTYLE);
        }
        carouselItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_more_relations, container, false);

        mCloseLayout = (FrameLayout) view.findViewById(R.id.fragment_see_more_button_close);
        mCloseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSeeMoreRelationsClose();
            }
        });
        if (jsonStyle != null) {
            if (jsonStyle != null) {
                try {
                    styleConfig = new JSONArray(jsonStyle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            styleCarousel = loadStyleCarousel(styleConfig);
        }
        mSeeMoreContainer = (RelativeLayout) view.findViewById(R.id.seemore_container);
        mCarouselList = (RecyclerView) view.findViewById(R.id.fragment_see_more_card_list);
        LinearLayoutManager carouselLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mCarouselList.setLayoutManager(carouselLayoutManager);

        mAdapter = new CarouselCardsAdapter(getContext(), carouselItems, false, style, mCarouselListener, instance);
        mAdapter.inSeeMoreFragment(true);
        mCarouselList.setAdapter(mAdapter);
        if (styleCarousel != null && styleCarousel.getIdModuleStyleData().get("backgroundColor") != null) {
            Log.e("1backgroundColor  ", styleCarousel.getIdModuleStyleData().get("backgroundColor").getValue());
            Log.e("1selectedColor  ", styleCarousel.getIdModuleStyleData().get("selectedColor").getValue());
            Log.e("1unselectedColor  ", styleCarousel.getIdModuleStyleData().get("unselectedColor").getValue());
            int backgroundColor = Color.parseColor(styleCarousel.getIdModuleStyleData().get("backgroundColor").getValue());
            mSeeMoreContainer.setBackgroundColor(backgroundColor);
//            mMinimizeLayout.setBackground(Utils.makeButtonSelector(Color.parseColor(styleCarousel.getIdModuleStyleData().get("selectedColor").getValue()), Color.parseColor(styleCarousel.getIdModuleStyleData().get("unselectedColor").getValue()), styleCarousel.getIdModuleStyleData().get("selectedColor").getValue()));
            mCloseLayout.setBackground(Utils.makeButtonSelector(Color.parseColor(styleCarousel.getIdModuleStyleData().get("selectedColor").getValue()), Color.parseColor(styleCarousel.getIdModuleStyleData().get("unselectedColor").getValue()), styleCarousel.getIdModuleStyleData().get("selectedColor").getValue()));
//            mCategories.setBackground(Utils.makeButtonSelector(Color.parseColor(styleCarousel.getIdModuleStyleData().get("selectedColor").getValue()), Color.parseColor(styleCarousel.getIdModuleStyleData().get("unselectedColor").getValue()), styleCarousel.getIdModuleStyleData().get("selectedColor").getValue()));
        } else if (Utils.getCardDetailStyleconfiguration(getContext()) != null) {
            Log.e("2backgroundColor  ", styleCarousel.getIdModuleStyleData().get("backgroundColor").getValue());
            Log.e("2selectedColor  ", styleCarousel.getIdModuleStyleData().get("selectedColor").getValue());
            Log.e("2unselectedColor  ", styleCarousel.getIdModuleStyleData().get("unselectedColor").getValue());
            int backgroundDefaultColor = Color.parseColor(loadStyleCarousel(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("backgroundColor").getValue());
            int selectedDefaultColor = Color.parseColor(loadStyleCarousel(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("selectedColor").getValue());
            String strSelectedDefaultColor = loadStyleCarousel(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("selectedColor").getValue();
            int unselectedDefaultColor = Color.parseColor(loadStyleCarousel(Utils.getCardDetailStyleconfiguration(getContext())).getIdModuleStyleData().get("unselectedColor").getValue());
            mSeeMoreContainer.setBackgroundColor(backgroundDefaultColor);
//            mMinimizeLayout.setBackground(Utils.makeButtonSelector(selectedDefaultColor, unselectedDefaultColor, strSelectedDefaultColor));
            mCloseLayout.setBackground(Utils.makeButtonSelector(selectedDefaultColor, unselectedDefaultColor, strSelectedDefaultColor));
//            mCategories.setBackground(Utils.makeButtonSelector(selectedDefaultColor, unselectedDefaultColor, strSelectedDefaultColor));
        }

        getRelationsFromCarouselCard();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Carousel.OnCarouselInteractionListener) {
            mCarouselListener = (Carousel.OnCarouselInteractionListener) context;
            mListener = (OnSeeMoreRelationsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCarouselInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCarouselListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lastClickedView != null ) {
            lastClickedView.requestFocus();
        } else {
            mCarouselList.requestFocus();
        }
    }

    private void getRelationsFromCarouselCard() {

        if (!isAdded() || carouselItems.size() > 0)
            return;

        carouselItems.clear();

        CarouselTvCell cell;

        switch (carouselCard.getType()) {

            case PERSON:
            case CHARACTER:
                List<CarouselTvCell> personCells = new ArrayList<>();
                cell = new PersonTvCell();
                cell.setCard(carouselCard);

                List<RelationModule> relationsToProcess = Utils.getRels(carouselCard, true);
                if (relationsToProcess.size() > 0)
                    cell.setHasRelations(true);
                personCells.add(cell);

                Iterator<RelationModule> relationsToProcessIter = relationsToProcess.iterator();
                while(relationsToProcessIter.hasNext()) {
                    Single fashionRelation = (Single) relationsToProcessIter.next();
                    List<Card> fashionCards = fashionRelation.getData();
                    Iterator<Card> fashionCardsIter = fashionCards.iterator();
                    while(fashionCardsIter.hasNext()) {
                        Card fashionCard = fashionCardsIter.next();
                        cell = new GenericTvCell();
                        cell.setCard(fashionCard);
                        if(fashionCardsIter.hasNext())
                            cell.setHasRelations(true);
                        personCells.add(cell);
                    }
                }
                carouselItems.addAll(personCells);
                break;
            case LOCATION:

                List<CarouselTvCell> locationCells = new ArrayList<>();
                cell = new GenericTvCell();
                cell.setCard(carouselCard);

                relationsToProcess = Utils.getRels(carouselCard,true);
                if (relationsToProcess.size() > 0)
                    cell.setHasRelations(true);
                locationCells.add(cell);

                relationsToProcessIter = relationsToProcess.iterator();
                while(relationsToProcessIter.hasNext()) {
                    Duple locationRelation = (Duple) relationsToProcessIter.next();
                    List<DupleData> locationDuples = locationRelation.getData();
                    Iterator<DupleData> locationDuplesIter = locationDuples.iterator();
                    while(locationDuplesIter.hasNext()) {
                        DupleData locationDuple = locationDuplesIter.next();
                        cell = new GenericTvCell();
                        cell.setCard(locationDuple.getTo());
                        if(locationDuplesIter.hasNext())
                            cell.setHasRelations(true);
                        locationCells.add(cell);
                    }
                }
                carouselItems.addAll(locationCells);
                break;
            case HOME:

                List<CarouselTvCell> decoCells = new ArrayList<>();
                cell = new GenericTvCell();
                cell.setCard(carouselCard);

                relationsToProcess = Utils.getRels(carouselCard, true);
                if (relationsToProcess.size() > 0)
                    cell.setHasRelations(true);
                decoCells.add(cell);

                relationsToProcessIter = relationsToProcess.iterator();
                while(relationsToProcessIter.hasNext()) {
                    Single decoRelation = (Single) relationsToProcessIter.next();
                    List<Card> decoCards = decoRelation.getData();
                    Iterator<Card> decoCardsIter = decoCards.iterator();
                    while(decoCardsIter.hasNext()) {
                        Card decoCard = decoCardsIter.next();
                        cell = new GenericTvCell();
                        cell.setCard(decoCard);
                        if(decoCardsIter.hasNext())
                            cell.setHasRelations(true);
                        decoCells.add(cell);
                    }
                }
                carouselItems.addAll(decoCells);
                break;
        }

        if (mAdapter != null) {
            mAdapter.notifyItemRangeInserted(0, carouselItems.size());
        }
    }

    @Override
    public CarouselPromptManager getOverlayManager() {
        return null;
    }

    @Override
    public void onOKMovieEnd() {

    }

    @Override
    public void onOKMovieOff() {

    }

    @Override
    public void setCardsAreLiked(HashMap<String, Boolean> likedCards) {

    }

    @Override
    public void closeOverlay() {
        mCloseLayout.requestFocus();

        if (lastClickedView != null && lastClickedView.isShown()) {
            lastClickedView.requestFocus();
        } else {
            mCarouselList.requestFocus();
        }
    }

    @Override
    public void clickedView(View view) {
        lastClickedView = view;
    }

    public interface OnSeeMoreRelationsListener {
        void onSeeMoreRelationsClose();
    }

    public void getFocus() {
        if (!isAdded())
            return;

        mCloseLayout.requestFocus();
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

}
