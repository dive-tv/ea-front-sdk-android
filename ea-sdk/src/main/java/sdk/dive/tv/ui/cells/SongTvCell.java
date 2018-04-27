package sdk.dive.tv.ui.cells;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.views.CarouselTVCellLinear;

/**
 * Created by Tagsonomy S.L. on 04/07/2017.
 */

public class SongTvCell extends CarouselTvCell {

    public SongTvCell() {

    }

    @Override
    public LinearLayout getView(final Context context) {
        Typeface latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        Typeface latoSemi = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        Typeface latoHeavy = Utils.getFont(context, Utils.TypeFaces.LATO_HEAVY);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View baseView = inflater.inflate(R.layout.tv_cell_song, null);

        cellLayout = (CarouselTVCellLinear) baseView.findViewById(R.id.song_celltv_container);

        mImageBorder = (FrameLayout) baseView.findViewById(R.id.song_tv_cell_image_border);

        final ImageView mSongIcon = (ImageView) baseView.findViewById(R.id.song_tv_cell_image);

        final TextView title = (TextView) baseView.findViewById(R.id.song_tv_cell_title);
        title.setTypeface(latoHeavy);
        if (getCard().getTitle() != null) {
            title.setText(getCard().getTitle());
        } else {
            title.setText("");
        }

        final TextView subtitle = (TextView) baseView.findViewById(R.id.song_tv_cell_subtitle);
        subtitle.setTypeface(latoRegular);
        if (getCard().getSubtitle() != null) {
            subtitle.setText(getCard().getSubtitle());
        } else {
            subtitle.setText("");
        }

        mTypeBox = (TextView) baseView.findViewById(R.id.song_tv_cell_type);
        mTypeBox.setTypeface(latoSemi);
        if (getCard().getType() != null) {
            mTypeBox.setText(Utils.getType(getCard().getType().getValue(), context));
        } else {
            mTypeBox.setText("");
        }

        mInfoContainer = (RelativeLayout) baseView.findViewById(R.id.song_celltv_info_container);
        openWhenFocused = new OpenWhenFocused(context, mInfoContainer);
        mInfoBorder = (FrameLayout) baseView.findViewById(R.id.song_cell_info_border);

     /*   mLikeContainer = (CarouselTVCellLinear) baseView.findViewById(R.id.song_cell_info_liked);
        mLikeIcon = (ImageView) baseView.findViewById(R.id.song_cell_info_liked_icon);
        mLikeTxt = (TextView) baseView.findViewById(R.id.song_cell_info_liked_txt);
        mLikeTxt.setTypeface(latoSemi);

        if (getCard().getUser() != null && getCard().getUser().isLiked()) {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
        } else {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
        }


        mLikeContainer.setOnClickListener(new View.OnClickListener() {
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
                            getCard().getMiniCard().getUser().setIsLiked(true);
                        } else {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
                            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
                            getCard().getMiniCard().getUser().setIsLiked(false);
                            if (mPocketInterface != null)
                                mPocketInterface.setCardDeleted(getCard().getMiniCard().getCardId());
                        }
                        HashMap<String, Boolean> likesHashMap = new HashMap<String, Boolean>();
                        likesHashMap.put(getCard().getMiniCard().getCardId(), isLiked);
                        if (mCarouselInterface != null)
                            mCarouselInterface.setCardsAreLiked(likesHashMap);
                    }


                    @Override
                    public void onSetLikeError(NetworkData response) {

                    }
                };
                if (getCard().getMiniCard().getUser() != null && getCard().getMiniCard().getUser().isLiked()) {
                    RestManager.getInstance().deleteLike(getCard().getMiniCard().getCardId(), mListener);
                } else if (getCard().getMiniCard().getUser() != null && !getCard().getMiniCard().getUser().isLiked()) {
                    RestManager.getInstance().setLike(getCard().getMiniCard().getCardId(), mListener);

                }
            }
        });*/


        cellLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    onCellFocusedSetColors(context);
//                    onCellFocused();

                    mSongIcon.setColorFilter(Utils.getColor(context, R.color.white));
                    title.setTextColor(Utils.getColor(context, R.color.white));
                    subtitle.setTextColor(Utils.getColor(context, R.color.pale_grey));
                } else {
//                    if (!isLikeBtnFocused) {
                    onCellUnFocusedColors(context);
                    onCellUnfocused(context);

                    mSongIcon.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    title.setTextColor(Utils.getColor(context, R.color.warm_grey));
                    subtitle.setTextColor(Utils.getColor(context, R.color.warm_grey));
//                    }
                }
            }
        });

/*
        mLikeContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                isLikeBtnFocused = hasfocus;
                if (hasfocus) {
                    cellLayout.setFocusable(false);
                    cellLayout.setFocusableInTouchMode(false);
                    onCellFocusedSetColors(context);
                    mSongIcon.setColorFilter(Utils.getColor(context, R.color.white));
                    title.setTextColor(Utils.getColor(context, R.color.white));
                    subtitle.setTextColor(Utils.getColor(context, R.color.pale_grey));
                    mInfoContainer.setVisibility(View.VISIBLE);
                    mInfoContainer.animate()
                            .translationX(0);
                } else {
                    cellLayout.setFocusable(true);
                    cellLayout.setFocusableInTouchMode(true);

                    onCellUnfocused(context);
                    mSongIcon.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    title.setTextColor(Utils.getColor(context, R.color.warm_grey));
                    subtitle.setTextColor(Utils.getColor(context, R.color.warm_grey));
                }
            }
        });
*/

        return cellLayout;
    }

/*
    @Override
    public void setNextFocusUp(int id) {
        if (mLikeContainer != null) {
            mLikeContainer.setNextFocusUpId(id);
        }
    }
*/

}
