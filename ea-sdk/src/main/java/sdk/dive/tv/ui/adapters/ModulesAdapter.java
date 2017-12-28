package sdk.dive.tv.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.ui.builders.ConfigModule;
import sdk.dive.tv.ui.listeners.CardDetailListener;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.modules.viewholders.ModuleHolder;
import sdk.dive.tv.ui.views.Module;

/**
 * Created by Tagsonomy S.L. on 15/09/2016.
 */
public class ModulesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final Context context;
    protected final CardDetailListener mListener;
    protected Card cardData;
    protected boolean isCarousel;
    protected final ArrayList<ConfigModule> configModules;
    HashMap<String, Integer> classIndex = new HashMap<>();
    protected HashMap<Integer, String> indexClass = new HashMap<>();
    protected Picasso mPicasso = null;
    protected SectionListener sectionListener=null;


    public static final String DEFAULT_MODULE_PACKAGE = "com.touchvie.touchvie_front.ui.modules.";

    /**
     * Constructor
     *  @param context
     * @param cardData
     * @param configModules
     * @param mListener
     */

    public ModulesAdapter(Context context, Card cardData, ArrayList<ConfigModule> configModules, boolean isCarousel, CardDetailListener mListener, SectionListener sectionListener) {

        this.context = context;
        this.cardData = cardData;
        this.configModules = configModules;
        this.mListener = mListener;
        this.mPicasso = Picasso.with(context);
        this.sectionListener=sectionListener;
        this.isCarousel=isCarousel;
        getDifferentModulesNumber();
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.configModules.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {

        String key = configModules.get(position).getType();
        return (classIndex.containsKey(key) ? classIndex.get(key) : 0);
    }

    protected void getDifferentModulesNumber() {
        if (configModules == null || configModules.size() == 0) {
            return;
        } else {
            int index = 0;

            for (ConfigModule configModule : configModules) {
                String type = configModule.getType();
                if (classIndex.get(type) == null) {
                    classIndex.put(type, index);
                    indexClass.put(index, type);
                    index++;
                }
            }
        }
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType  type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (indexClass.size() == 0) {
            return null;
        }
        String moduleName = indexClass.get(viewType);
        if (!moduleName.contains(".")) {
            moduleName = DEFAULT_MODULE_PACKAGE + moduleName;
        }
        try {
            viewHolder = ((Module) (Class.forName(moduleName).newInstance())).getViewHolder(inflater, viewGroup);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //TODO: can't happen
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace(); //TODO: can't happen
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace(); //TODO: can't happen
            return null;
        }
        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param viewHolder The type of RecyclerView.ViewHolder to populate
     * @param position   Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ModuleHolder) {
            ((ModuleHolder) viewHolder).configure(cardData, mPicasso, context, mListener, sectionListener);
            String moduleName = indexClass.get((((ModuleHolder) viewHolder)).getItemViewType());
        }
    }

}
