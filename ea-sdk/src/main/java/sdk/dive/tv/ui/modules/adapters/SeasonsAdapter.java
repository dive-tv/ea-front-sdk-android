package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.SeasonsChapters;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.data.SeasonRowData;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class SeasonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SeasonRowData> rows;
    private Picasso mPicasso;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private SeasonsListener seasonsListener;


    public SeasonsAdapter(Context context, ArrayList<SeasonRowData> rows, SeasonsListener seasonsListener) {
        super();
        this.rows = rows;
        mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        this.seasonsListener = seasonsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item_row, parent, false);
        return new sdk.dive.tv.ui.modules.adapters.SeasonsAdapter.SeasonsItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        mPicasso.load(rows.get(holder.getAdapterPosition()).getImage())
                .into(((sdk.dive.tv.ui.modules.adapters.SeasonsAdapter.SeasonsItemHolder) holder).image);
        final SeasonRowData row = rows.get(holder.getAdapterPosition());
        ((sdk.dive.tv.ui.modules.adapters.SeasonsAdapter.SeasonsItemHolder) holder).year.setText(String.valueOf(row.getYear()));
        ((sdk.dive.tv.ui.modules.adapters.SeasonsAdapter.SeasonsItemHolder) holder).year.setTypeface(latoRegular);
        ((SeasonsItemHolder) holder).season.setText(row.getSeason());
        ((sdk.dive.tv.ui.modules.adapters.SeasonsAdapter.SeasonsItemHolder) holder).season.setTypeface(latoSemibold);


/*
        final MiniCardListener minicardsListener = new MiniCardListener() {
            @Override
            public void onMiniCardReceived(MiniCard miniCard) {

            }

            @Override
            public void onMiniCardError(NetworkData response) {

            }

            @Override
            public void onMiniCardsReceived(MiniCard[] miniCards) {

                if (miniCards == null || miniCards.length <= 0) {
                    return;
                }

                ArrayList<MiniCard> chaptersToSee = new ArrayList<>();

                HashMap<String, MiniCard> idRetrievedMinicards = new HashMap<>();
                for (MiniCard miniCard : miniCards) {
                    idRetrievedMinicards.put(miniCard.getCardId(), miniCard);
                }

                for (Chapter chapter : rows.get(holder.getAdapterPosition()).getChapters()) {

                    MiniCard miniCard = idRetrievedMinicards.get(chapter.getCard_id());
                    if (miniCard != null) {
                        chaptersToSee.add(miniCard);
                    }
                }

                seasonsListener.openChapters(chaptersToSee, row.getSeasonNr());
            }

            @Override
            public void onMiniCardsError(NetworkData response) {

            }
        };
*/

     /*   ((SeasonsItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               ArrayList<String> chapterIds = new ArrayList<>();
                for (SeasonsChapters chapter : rows.get(holder.getAdapterPosition()).getChapters()) {
                    chapterIds.add(chapter.getCard_id());
                }
                if (FrontBlackboard.getInstance().getSdkFrontInterface() != null)
                    FrontBlackboard.getInstance().getSdkFrontInterface().getFrontMiniCard(chapterIds, "false", null, minicardsListener);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }


    private static class SeasonsItemHolder extends RecyclerView.ViewHolder {

        ImageView image;

        TextView year;

        TextView season;

        LinearLayout container;


        SeasonsItemHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.season_image);
            year = (TextView) v.findViewById(R.id.season_year);
            season = (TextView) v.findViewById(R.id.season_season);
            container = (LinearLayout) v.findViewById(R.id.rimageitems_container);
        }
    }

    public interface SeasonsListener {
        void openChapters(ArrayList<Card> chapters, String season);
    }


}



