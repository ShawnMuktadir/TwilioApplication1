package kotlinroom.work.twilioapplication.presenter;

import androidx.fragment.app.FragmentManager;

import kotlinroom.work.twilioapplication.presenter.common.CommonActivityPresenter;

/*Created by MiQ0717 on 28-Feb-2020.*/
public interface HomePresenter extends CommonActivityPresenter, FragmentManager.OnBackStackChangedListener {
    void openVideoFragment();
}
