package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;

import static android.view.View.GONE;
import static com.touchvie.sdk.model.Card.TypeEnum.TRIVIA;


/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public class HeaderHolder extends TvModuleHolder {

    protected RelativeLayout container;
    protected TextView title;
    protected TextView subtitle;
    protected TextView type;
    protected ImageView image;
    protected ImageView noimage;
    protected LinearLayout mLikeContainer;
    protected TextView mLikeTxt;
    protected TextView trivia;
    protected ImageView mLikeIcon;
    protected FrameLayout imageBorder;
    protected TextView txtvType;
    private HashMap<String, ModuleStyleData> genericStyles;

    /**
     * Default constructor
     */
    public HeaderHolder(View view, String simpleName) {
        super(view);
        this.moduleName = simpleName;

        container = (RelativeLayout) view.findViewById(R.id.tv_module_header_container_full);
        title = (TextView) view.findViewById(R.id.tv_module_header_info_title);
        subtitle = (TextView) view.findViewById(R.id.tv_module_header_info_subtitle);
        type = (TextView) view.findViewById(R.id.tv_module_header_type);
        image = (ImageView) view.findViewById(R.id.tv_module_header_image);
        noimage = (ImageView) view.findViewById(R.id.tv_module_header_noimage);
        trivia = (TextView)view.findViewById(R.id.tv_module_header_trivia);
        imageBorder = (FrameLayout) view.findViewById(R.id.tv_module_header_image_border);
        txtvType = (TextView) view.findViewById(R.id.tv_module_header_type);
    }

    @Override
    public void configure(final Card cardData, final Picasso picasso, final Context context, final TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {
        Typeface latoHeavy = sdk.dive.tv.ui.Utils.getFont(context, sdk.dive.tv.ui.Utils.TypeFaces.LATO_HEAVY);

        if (cardData.getTitle() != null) {
            title.setText(cardData.getTitle());
            title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD));
        } else {
            title.setText("");
            title.setVisibility(GONE);
        }
        if (cardData.getSubtitle() != null) {
            subtitle.setText(cardData.getSubtitle());
            title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD));
        } else {
            subtitle.setText("");
        }

        if (cardData.getType().equals(TRIVIA.getValue())){
            image.setVisibility(GONE);
            noimage.setVisibility(GONE);
            trivia.setVisibility(View.VISIBLE);
            trivia.setText(cardData.getTitle());
            trivia.setTypeface(latoHeavy);
        } else {
            trivia.setVisibility(GONE);
            if (cardData.getImage() != null && cardData.getImage().getThumb() != null) {
                noimage.setVisibility(GONE);
                image.setVisibility(View.VISIBLE);
                image.post(new Runnable() {
                    @Override
                    public void run() {
                        picasso.load(cardData.getImage().getThumb())
                                .transform(new CropSquareTransformation2((int) context.getResources().getDimension(R.dimen.genericcell_image_width), image.getMeasuredHeight(),
                                        cardData.getImage().getAnchorX().intValueExact(), cardData.getImage().getAnchorY().intValueExact()))
                                .priority(Picasso.Priority.HIGH)
                                .into(image);
                    }
                });
            } else {
                noimage.setVisibility(View.VISIBLE);
                image.setVisibility(GONE);
            }
        }

        type.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
        if (cardData.getType() != null) {
            type.setText(sdk.dive.tv.ui.Utils.getType(cardData.getType().getValue(), context));
        } else {
            type.setText("");
        }

        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            imageBorder.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue())));
            txtvType.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue())));
        }



/*        mLikeTxt.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
        if (cardData.getUser() != null && cardData.getUser().isLiked()) {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
        } else {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
        }*/

/*
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikesListener mListener = new LikesListener() {
                    @Override
                    public void onLikesReceived(MiniCard[] miniCards, String paginateKey) {

                    }

                    @Override
                    public void onLikesError(NetworkData response) {

                    }

                    @Override
                    public void onsetLike(boolean isLiked) {
                        if (isLiked) {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
                            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
                            cardData.getUser().setIsLiked(true);
                        } else {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
                            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
                            cardData.getUser().setIsLiked(false);
                        }
//                        HashMap<String, Boolean> likesHashMap= new HashMap<String, Boolean>();
//                        likesHashMap.put(cardData.getCardId(), isLiked);
//                        mCarouselInterface.setCardsAreLiked(likesHashMap);
                    }


                    @Override
                    public void onSetLikeError(NetworkData response) {

                    }
                };

                if (cardData.getUser() != null && cardData.getUser().isLiked()) {
                    RestManager.getInstance().deleteLike(cardData.getCardId(), mListener);
                } else if (cardData.getUser() != null && !cardData.getUser().isLiked()) {
                    RestManager.getInstance().setLike(cardData.getCardId(), mListener);
                }
            }
        });
*/

    }

}