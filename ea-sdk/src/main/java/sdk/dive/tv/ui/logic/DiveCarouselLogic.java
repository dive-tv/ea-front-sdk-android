package sdk.dive.tv.ui.logic;

import android.content.Context;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.cells.CarouselTvCell;
import sdk.dive.tv.ui.cells.GenericTvCell;
import sdk.dive.tv.ui.cells.PersonTvCell;
import sdk.dive.tv.ui.cells.SeeMoreTvCell;
import sdk.dive.tv.ui.cells.SongTvCell;
import sdk.dive.tv.ui.cells.TriviaTvCell;
import sdk.dive.tv.ui.relations.LocalContentTypes;

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.FEATURED_IN;
import static sdk.dive.tv.ui.relations.LocalContentTypes.HOME_OFF_RELATION;

/**
 * Created by Tagsonomy S.L. on 29/06/2017.
 */

public class DiveCarouselLogic {
    private Context context;

    public DiveCarouselLogic(Context context) {
        this.context = context;
    }

    public ArrayList<CarouselTvCell> processData(ArrayList<Card> cards) {
        ArrayList<CarouselTvCell> cells = new ArrayList<>();      //the returned value

        for (Card card : cards) {
            switch (card.getType()) {
                case CHARACTER:

                    List<CarouselTvCell> personCells = new ArrayList<>();
                    CarouselTvCell cell = new PersonTvCell();
                    cell.setCard(card);

                    List<RelationModule> relationsToProcess = Utils.getRels(card, true);
                    if (relationsToProcess.size() > 0)
                        cell.setHasRelations(true);
                    personCells.add(cell);

                    Iterator<RelationModule> relationsToProcessIter = relationsToProcess.iterator();
                    while(relationsToProcessIter.hasNext() && personCells.size() < 4) { // 1 character card + 2 fashion relations + 1 see more = 4
                        Single fashionRelation = (Single) relationsToProcessIter.next();
                        List<Card> fashionCards = fashionRelation.getData();
                        Iterator<Card> fashionCardsIter = fashionCards.iterator();
                        while(fashionCardsIter.hasNext() && personCells.size() < 4) {
                            Card fashionCard = fashionCardsIter.next();
                            if(personCells.size() < 3) { // 1 character card + 2 fashion relations = 3
                                cell = new GenericTvCell();
                                cell.setCard(fashionCard);
                                if (fashionCardsIter.hasNext())
                                    cell.setHasRelations(true);
                                personCells.add(cell);
                            } else {
                                cell = new SeeMoreTvCell();
                                cell.setCard(card);
                                personCells.add(cell);
                            }
                        }
                    }
                    cells.addAll(personCells);
                    break;
                case LOCATION:
                    List<CarouselTvCell> locationCells = new ArrayList<>();
                    cell = new GenericTvCell();
                    cell.setCard(card);

                    relationsToProcess = Utils.getRels(card, true);
                    if (relationsToProcess.size() > 0)
                        cell.setHasRelations(true);
                    locationCells.add(cell);

                    relationsToProcessIter = relationsToProcess.iterator();
                    while(relationsToProcessIter.hasNext() && locationCells.size() < 4) { // 1 location card + 2 location relations + 1 see more = 4
                        RelationModule relation = relationsToProcessIter.next();
                        if (relation instanceof Duple) {
                            Duple locationRelation = (Duple) relation;
                            List<DupleData> locationDuples = locationRelation.getData();
                            Iterator<DupleData> locationDuplesIter = locationDuples.iterator();
                            while (locationDuplesIter.hasNext() && locationCells.size() < 4) {
                                DupleData locationDuple = locationDuplesIter.next();
                                if (locationCells.size() < 3) { // 1 location card + 2 location relations = 3
                                    cell = new GenericTvCell();
                                    cell.setCard(locationDuple.getTo());
                                    if (locationDuplesIter.hasNext())
                                        cell.setHasRelations(true);
                                    locationCells.add(cell);
                                } else {
                                    cell = new SeeMoreTvCell();
                                    cell.setCard(card);
                                    locationCells.add(cell);
                                }
                            }
                        } else if (relation instanceof Single) {
                            Single locationRelation = (Single) relation;
                            List<Card> locationCards = locationRelation.getData();
                            Iterator<Card> locationCardsIter = locationCards.iterator();
                            while(locationCardsIter.hasNext() && locationCells.size() < 4) {
                                Card locationCard = locationCardsIter.next();
                                if(locationCells.size() < 3) { // 1 character card + 2 fashion relations = 3
                                    cell = new GenericTvCell();
                                    cell.setCard(locationCard);
                                    if (locationCardsIter.hasNext())
                                        cell.setHasRelations(true);
                                    locationCells.add(cell);
                                } else {
                                    cell = new SeeMoreTvCell();
                                    cell.setCard(card);
                                    locationCells.add(cell);
                                }
                            }
                        }
                    }
                    cells.addAll(locationCells);
                    break;
                case HOME:

                    List<CarouselTvCell> decoCells = new ArrayList<>();
                    cell = new GenericTvCell();
                    cell.setCard(card);

                    relationsToProcess = Utils.getRels(card, true);
                    if (relationsToProcess.size() > 0)
                        cell.setHasRelations(true);
                    decoCells.add(cell);

                    relationsToProcessIter = relationsToProcess.iterator();
                    while(relationsToProcessIter.hasNext() && decoCells.size() < 4) { // 1 Home card + 2 deco relations + 1 see more = 4
                        Single decoRelation = (Single) relationsToProcessIter.next();
                        List<Card> decoCards = decoRelation.getData();
                        Iterator<Card> decoCardsIter = decoCards.iterator();
                        while(decoCardsIter.hasNext() && decoCells.size() < 4) {
                            Card decoCard = decoCardsIter.next();
                            if(decoCells.size() < 3) { // 1 home card + 2 deco relations = 3
                                cell = new GenericTvCell();
                                cell.setCard(decoCard);
                                if (decoCardsIter.hasNext())
                                    cell.setHasRelations(true);
                                decoCells.add(cell);
                            } else {
                                cell = new SeeMoreTvCell();
                                cell.setCard(card);
                                decoCells.add(cell);
                            }
                        }
                    }
                    cells.addAll(decoCells);
                    break;
                case SONG:
                    cell = new SongTvCell();
                    cell.setCard(card);

                    cells.add(cell);
                    break;
                case TRIVIA:
                case REFERENCE:
                    cell = new TriviaTvCell();
                    cell.setCard(card);

                    cells.add(cell);
                    break;
                default:
                    cell = new GenericTvCell();
                    cell.setCard(card);
                    cells.add(cell);
                    break;
            }
        }
        return cells;
    }
}
