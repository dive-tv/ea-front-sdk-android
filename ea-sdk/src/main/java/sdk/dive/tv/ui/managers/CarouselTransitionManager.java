package sdk.dive.tv.ui.managers;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.HardwareLayerAnimatorListenerAdapter;
import sdk.dive.tv.ui.views.CommercialView;
import sdk.dive.tv.ui.views.EndView;
import sdk.dive.tv.ui.views.OffView;
import sdk.dive.tv.ui.views.ReadyView;

/**
 * Created by Tagsonomy S.L. on 05/07/2017.
 */

public class CarouselTransitionManager extends TransitionManager {

    private CommercialView mCommercialView;
    private ReadyView mReadyView;
    private EndView mEndView;
    private OffView mOffView;

    private ViewGroup mSceneContainer;
    private ViewGroup mParentSceneContainer;

    private boolean mInitialized;

    private ViewGroup mCurrentSceneView;

    private Context ctx;
    private Animator mEnterAnimator;
    private Animator mExitAnimator;

    private Scene mEmptyScene;
    private Scene mCommercialScene;
    private Scene mEndScene;
    private Scene mOffScene;
    private Scene mReadyScene;
    private Scene mCurrentScene;

    private RecyclerView mCarouselList;

    private CarouselSceneListener mListener;

    public @interface CarouselSceneType {
    }

    public static final int SCENE_TYPE_EMPTY = 0;
    public static final int SCENE_TYPE_COMMERCIAL = 1;

    public CarouselTransitionManager(Context ctx, ViewGroup parentContainer, RecyclerView carouselList, ViewGroup carouselContainer, CommercialView commercialView, ReadyView readyView, EndView endView, OffView offView) {

        this.ctx = ctx;

        mCommercialView = commercialView;
        mReadyView = readyView;
        mEndView = endView;
        mOffView = offView;
        mCarouselList = carouselList;

        mSceneContainer = carouselContainer;
        mParentSceneContainer = parentContainer;


    }

    private void showLayer() {

        mParentSceneContainer.setVisibility(View.VISIBLE);
    }

    private void hideLayer() {
        mParentSceneContainer.setVisibility(View.GONE);
        mCarouselList.requestFocus();

    }

    public void goToCommercialScene() {
        initIfNeeded();
        showLayer();

        transitionTo(mCommercialScene);

    }

    public void goToReadyScene() {
        initIfNeeded();
        showLayer();
        transitionTo(mReadyScene);
    }

    public void goToEndScene() {
        initIfNeeded();
        showLayer();
        transitionTo(mEndScene);
    }

    public void goToOffScene() {
        initIfNeeded();
        showLayer();
        transitionTo(mOffScene);
    }

    public void goToEmptyScene(boolean withAnimation) {
        if (mCurrentScene == mEmptyScene) {
            return;
        }
        initIfNeeded();
        if (withAnimation) {
            transitionTo(mEmptyScene);
        } else {
            TransitionManager.go(mEmptyScene, null);
        }
        hideLayer();
    }


    public void initIfNeeded() {
        if (mInitialized) {
            return;
        }

        mEnterAnimator = AnimatorInflater.loadAnimator(ctx,
                R.animator.channel_banner_enter);
        mExitAnimator = AnimatorInflater.loadAnimator(ctx,
                R.animator.channel_banner_exit);

        mCommercialScene = buildScene(mSceneContainer, mCommercialView);
        mEndScene = buildScene(mSceneContainer, mEndView);
        mOffScene = buildScene(mSceneContainer, mOffView);
        mReadyScene = buildScene(mSceneContainer, mReadyView);
        mCurrentScene = mEmptyScene;

        // Enter transitions
        TransitionSet enter = new TransitionSet()
                .addTransition(new SceneTransition(SceneTransition.ENTER))
                .addTransition(new Fade(Fade.IN));
        setTransition(mEmptyScene, mCommercialScene, enter);
        setTransition(mEmptyScene, mEndScene, enter);
        setTransition(mEmptyScene, mOffScene, enter);
        setTransition(mEmptyScene, mReadyScene, enter);


        // Exit transitions
        TransitionSet exit = new TransitionSet()
                .addTransition(new SceneTransition(SceneTransition.EXIT))
                .addTransition(new Fade(Fade.OUT));
        setTransition(mCommercialScene, mEmptyScene, exit);
        setTransition(mCommercialScene, mEndScene, exit);
        setTransition(mCommercialScene, mOffScene, exit);
        setTransition(mCommercialScene, mReadyScene, exit);

        // All other possible transitions between scenes
        TransitionInflater ti = TransitionInflater.from(ctx);
        Transition transition = ti.inflateTransition(R.transition.transition_between_scenes);
        setTransition(mCommercialScene, mEndScene, transition);
        setTransition(mCommercialScene, mOffScene, transition);
        setTransition(mCommercialScene, mReadyScene, transition);
        setTransition(mEndScene, mCommercialScene, transition);
        setTransition(mEndScene, mReadyScene, transition);
        setTransition(mEndScene, mOffScene, transition);
        setTransition(mReadyScene, mCommercialScene, transition);
        setTransition(mReadyScene, mEndScene, transition);
        setTransition(mReadyScene, mOffScene, transition);
        setTransition(mOffScene, mCommercialScene, transition);
        setTransition(mOffScene, mEndScene, transition);
        setTransition(mOffScene, mEndScene, transition);


        mInitialized = true;
    }

    private void removeAllViewsFromOverlay() {
        // Clean up all the animations which can be still running.
        mSceneContainer.getOverlay().remove(mCommercialView);
        mSceneContainer.getOverlay().remove(mEndView);
        mSceneContainer.getOverlay().remove(mOffView);
        mSceneContainer.getOverlay().remove(mReadyView);

    }

    private void setCurrentScene(Scene scene, ViewGroup sceneView) {
        if (mListener != null) {
            mListener.onSceneChanged(getSceneType(mCurrentScene), getSceneType(scene));
        }
        mCurrentScene = scene;
        mCurrentSceneView = sceneView;

    }

    private Scene buildScene(ViewGroup sceneRoot, final TransitionLayout layout) {
        final Scene scene = new Scene(sceneRoot, (View) layout);
        scene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                boolean wasEmptyScene = (mCurrentScene == mEmptyScene);
                setCurrentScene(scene, (ViewGroup) layout);
                layout.onEnterAction(wasEmptyScene);
            }
        });
        scene.setExitAction(new Runnable() {
            @Override
            public void run() {
                removeAllViewsFromOverlay();
                layout.onExitAction();
            }
        });
        return scene;
    }

    /**
     * Returns the type of the given scene.
     */
    @CarouselSceneType
    public int getSceneType(Scene scene) {
        if (scene == mCommercialScene) {
            return SCENE_TYPE_COMMERCIAL;
        }
        return SCENE_TYPE_EMPTY;
    }

    private class SceneTransition extends Transition {
        static final int ENTER = 0;
        static final int EXIT = 1;

        private final Animator mAnimator;

        SceneTransition(int mode) {
            mAnimator = mode == ENTER ? mEnterAnimator : mExitAnimator;
        }

        @Override
        public void captureStartValues(TransitionValues transitionValues) {
        }

        @Override
        public void captureEndValues(TransitionValues transitionValues) {
        }

        @Override
        public Animator createAnimator(
                ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            Animator animator = mAnimator.clone();
            animator.setTarget(sceneRoot);
            animator.addListener(new HardwareLayerAnimatorListenerAdapter(sceneRoot));
            return animator;
        }
    }

    public interface TransitionLayout {

        void onEnterAction(boolean fromEmptyScene);

        void onExitAction();
    }

    /**
     * An interface for notification of the scene transition.
     */
    public interface CarouselSceneListener {
        /**
         * Called when the scene changes. This method is called just before the scene transition.
         */
        void onSceneChanged(@CarouselSceneType int fromSceneType, @CarouselSceneType int toSceneType);
    }
}
