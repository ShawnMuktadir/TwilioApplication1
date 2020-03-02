package kotlinroom.work.twilioapplication.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import kotlinroom.work.twilioapplication.R;
import kotlinroom.work.twilioapplication.utils.ApplicationUtils;
import kotlinroom.work.twilioapplication.view.HomeView;
import kotlinroom.work.twilioapplication.view.video.VideoBookFragment;
import timber.log.Timber;

/*Created by MiQ0717 on 28-Feb-2020.*/
public class HomePresenterImpl implements HomePresenter {

    private Context context;
    private HomeView homeView;
    private FragmentManager fragmentManager;

    public HomePresenterImpl() {

    }

    public HomePresenterImpl(Context context, HomeView homeView, FragmentManager fragmentManager) {
        this.context = context;
        this.homeView = homeView;
        this.fragmentManager = fragmentManager;
        this.fragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    public void initializePresenter() {
        homeView.initUI();
        homeView.setListeners();
    }

    @Override
    public void openVideoFragment() {
        Timber.e("openVideoFragment");
        if (ApplicationUtils.checkInternet(context)) {
            ApplicationUtils.hideKeyboard((Activity) context);
            ApplicationUtils.removeFragments(fragmentManager, false, "openVideoFragment");
            ApplicationUtils.addFragmentToActivityWithBackStack(fragmentManager, VideoBookFragment.newInstance(), R.id.content_main_frame, context.getString(R.string.video));
        } else {
            ApplicationUtils.showMessageDialog(context.getString(R.string.connect_to_internet), context);
        }
    }

    @Override
    public void onBackStackChanged() {
        Timber.e("++++++++++++++++++++++++++++++++++++++++++++++++++");
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = count - 1; i >= 0; i--) {
            androidx.fragment.app.FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
//            Toast.makeText(this,entry.getName(), Toast.LENGTH_SHORT).show();
            Timber.e("FoundFragment: " + i + " --> " + entry.getName());
        }
        Timber.e("++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
