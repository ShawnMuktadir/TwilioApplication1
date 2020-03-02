package kotlinroom.work.twilioapplication.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlinroom.work.twilioapplication.R;
import kotlinroom.work.twilioapplication.presenter.HomePresenter;
import kotlinroom.work.twilioapplication.presenter.HomePresenterImpl;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout rootLayout;
    @BindView(R.id.drawerlayoutMain)
    DrawerLayout drawerlayoutMain;
    @BindView(R.id.mainNavigationView)
    NavigationView mainNavigationView;

    private Context context;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ButterKnife.bind(toolbar, this);
        context = this;

        toolbar = findViewById(R.id.main_toolbar);
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        homePresenter = new HomePresenterImpl(context, this, getSupportFragmentManager());
        setSupportActionBar(toolbar);

        setupNavigationDrawer();
        mainNavigationView.getMenu().getItem(0).setChecked(true);
        Timber.e("MainActivity ");
    }

    private void setupNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayoutMain, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayoutMain.addDrawerListener(actionBarDrawerToggle);
        drawerlayoutMain.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Called when a drawer's position changes.

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Called when a drawer has settled in a completely open state.
                //The drawer is interactive at this point.
                // If you have 2 drawers (left and right) you can distinguish
                // them by using id of the drawerView. int id = drawerView.getId();
                // id will be your layout's id: for example R.id.left_drawer
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Called when a drawer has settled in a completely closed state.
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Called when the drawer motion state changes. The new state will be one of STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(drawerlayoutMain.getWindowToken(), 0);
//                if (fabExpanded) {
//                   closeSubMenusFab();
//                }

            }
        });
        actionBarDrawerToggle.syncState();
        mainNavigationView.setItemIconTintList(null);
        mainNavigationView.setNavigationItemSelectedListener(this);
        setNavMenuItemThemeColors(getResources().getColor(R.color.colorPrimary));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setDrawerState(true);

    }

    public void setNavMenuItemThemeColors(int color) {
//        Setting default colors for menu item Text and Icon
        int navDefaultTextColor = Color.parseColor("#000000");
//        int navDefaultIconColor = Color.parseColor("#25A848");

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
//        ColorStateList navMenuIconList = new ColorStateList(
//                new int[][]{
//                        new int[]{android.R.attr.state_checked},
//                        new int[]{android.R.attr.state_enabled},
//                        new int[]{android.R.attr.state_pressed},
//                        new int[]{android.R.attr.state_focused},
//                        new int[]{android.R.attr.state_pressed}
//                },
//                new int[]{
//                        color,
//                        navDefaultIconColor,
//                        navDefaultIconColor,
//                        navDefaultIconColor,
//                        navDefaultIconColor
//                }
//        );

        mainNavigationView.setItemTextColor(navMenuTextList);
//        mainNavigationView.setItemIconTintList(navMenuIconList);
    }

    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawerlayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
            actionBarDrawerToggle.syncState();
        } else {
            drawerlayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(null);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerlayoutMain.closeDrawers();
        mainNavigationView.setCheckedItem(item.getItemId());
        switch (id) {
            case R.id.menu_video:
                homePresenter.openVideoFragment();
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mainNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initActionbar() {
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (NullPointerException ignore) {
        }
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void setListeners() {

    }
}
