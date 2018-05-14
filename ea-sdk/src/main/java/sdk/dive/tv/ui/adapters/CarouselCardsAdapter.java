package sdk.dive.tv.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.cells.CarouselTvCell;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.fragments.Carousel;
import sdk.dive.tv.ui.interfaces.CarouselInterface;
import sdk.dive.tv.ui.interfaces.PocketAdapterInterface;

/**
 * Created by Tagsonomy S.L. on 05/07/2017.
 */

public class CarouselCardsAdapter extends RecyclerView.Adapter<CarouselCardsAdapter.CarouselViewHolder> implements PocketAdapterInterface {
    private boolean isFiltered;
    private Context context;
    private LayoutInflater mInflater;
    private Carousel.OnCarouselInteractionListener mListener;
    private CarouselInterface mCarouselInterface;
    private ArrayList<CarouselTvCell> carouselItems;
    private boolean canRefreshFocus = true;
    private boolean inSeeMoreFragment = false;
    private ModuleStyle styleCarousel;

    public CarouselCardsAdapter(Context context, ArrayList<CarouselTvCell> carouselItems, boolean isFiltered, ModuleStyle style,
                                Carousel.OnCarouselInteractionListener listener, CarouselInterface carouselInterface) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.carouselItems = carouselItems;
        this.isFiltered = isFiltered;
        this.styleCarousel = style;
        this.mListener = listener;
        this.mCarouselInterface = carouselInterface;
    }

    @Override
    public CarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.carousel_tv_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new CarouselCardsAdapter.CarouselViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CarouselViewHolder holder, int position) {

        final CarouselTvCell cell = (CarouselTvCell) carouselItems.get(position);
        cell.setActivityListener(mListener);
        cell.setCarouselInterface(mCarouselInterface);
        cell.setPocketInterface(this);
        if (styleCarousel!=null)
            cell.setStyleCell(styleCarousel);
        View view = cell.getView(holder.row.getContext());
        if (!isFiltered && cell.hasRelations()) {
            holder.separator.setVisibility(View.VISIBLE);
        } else {
            holder.separator.setVisibility(View.INVISIBLE);
        }
        view.setTag(position);

        holder.row.addView(view);

    }

    @Override
    public void onViewRecycled(CarouselCardsAdapter.CarouselViewHolder holder) {
        super.onViewRecycled(holder);
        holder.row.removeAllViews();
        System.gc();
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    public void setCanRefreshFocus(boolean canRefreshFocus) {
        this.canRefreshFocus = canRefreshFocus;
    }

    public boolean isCanRefreshFocus() {
        return canRefreshFocus;
    }

    @Override
    public void setCurrentInstance(CarouselTvCell instance) {
        if (instance != null) {
            for (int i = 0; i < carouselItems.size(); i++) {
                if (instance.equals(carouselItems.get(i))) {
                    mCarouselInterface.clickedView(((CarouselTvCell) carouselItems.get(i)).getCellLayout());
                    break;
                }
            }
        }
    }

    @Override
    public void setCardDeleted(String cardId) {

    }

    public void inSeeMoreFragment(boolean inSeeMore){
        this.inSeeMoreFragment = inSeeMore;
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView row;
        FrameLayout separator;

        CarouselViewHolder(View itemView) {
            super(itemView);
            row = (CardView) itemView.findViewById(R.id.carousel_item_container);
            separator = (FrameLayout) itemView.findViewById(R.id.carousel_item_separator);
        }

    }


}
