package com.superbug.moi.cquptlife.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.BaseActivity;
import com.superbug.moi.cquptlife.presenter.LifePresenter;
import com.superbug.moi.cquptlife.ui.adapter.LifeAdapter;
import com.superbug.moi.cquptlife.ui.vu.ILifeVu;
import com.superbug.moi.cquptlife.util.Animations.SearchAnimation;
import com.superbug.moi.cquptlife.util.KeyboardUtil;
import com.superbug.moi.cquptlife.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifeActivity extends BaseActivity implements ILifeVu, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.tv_type)
    TextView mSearchType;
    private LifePresenter presenter;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.tv_content)
    TextView mEmptyView;
    @Bind(R.id.ed_search)
    EditText search;
    @Bind(R.id.rl_search)
    CardView searchLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.lv_content)
    RecyclerView mRecyclerView;

    private LifeAdapter adapter;
    private String searchInfo;

    private String[] type = new String[3];
    private int currentType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new LifePresenter(this);

        type[0] = getResources().getString(R.string.all);
        type[1] = getResources().getString(R.string.student);
        type[2] = getResources().getString(R.string.teacher);
        initToolbar();
        initContent();
    }

    /**
     * 解除关联，very very very important! ((‵□′))
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onRelieveView();
        if (isFinishing()) presenter = null;
    }

    private void initContent() {
        adapter = new LifeAdapter(this, presenter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.blue_primary_color, R.color.primary_color);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setVisibility(View.GONE);
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener());
        search.setOnKeyListener(new OnSearchKey());
    }

    private void openSearchLayout() {
        searchLayout.setVisibility(View.VISIBLE);
        SearchAnimation.start(searchLayout, SearchAnimation.SEARCH_OPEN, null);
        KeyboardUtil.showInput(search);
    }

    private void closeSearchLayout() {
        search.setText("");
        SearchAnimation.start(searchLayout, SearchAnimation.SEARCH_CLOSE, () -> searchLayout.setVisibility(View.GONE));
        KeyboardUtil.hideInput(search);
    }

    private void searchEvent() {
        String info = getLifeInfo();
        info = info.replaceAll(" ", "");
        if (!info.isEmpty()) {
            presenter.searchLives(info, 1, currentType);
            closeSearchLayout();
        }
    }

    @OnClick(R.id.iv_search_close)
    void onSearchClick() {
        closeSearchLayout();
    }

    @OnClick(R.id.tv_type)
    void onSearchTypeClick() {
        currentType = ++currentType % 3;
        Log.d("TAG", currentType + "");
        mSearchType.setText(type[currentType]);
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if (searchLayout.getVisibility() == View.GONE) {
            openSearchLayout();
        } else {
            searchEvent();
        }
    }

    @Override
    public String getLifeInfo() {
        searchInfo = search.getText().toString();
        return searchInfo;
    }

    @Override
    public void setLives() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showList() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView(String str) {
        mEmptyView.setText(str);
        mEmptyView.setVisibility(View.VISIBLE);
        mSwipeRefreshWidget.setVisibility(View.GONE); // 这里要把下拉刷新给关了，否则会卡顿！
    }

    @Override
    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mSwipeRefreshWidget.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if (!mSwipeRefreshWidget.isRefreshing())
            showProgress(getResources().getString(R.string.loading));
    }

    @Override
    public void hideLoading() {
        Utils.Log("请收起你的小陀螺");
        mSwipeRefreshWidget.setRefreshing(false);
        dismissProgress();
    }

    /* toolbar按钮 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_life, menu);
        return true;
    }

    @Override
    public void onRefresh() {
        if (searchInfo != null) {
            searchInfo = searchInfo.replaceAll(" ", "");
            if (!searchInfo.isEmpty()) {
                presenter.searchLives(searchInfo, 1, currentType);
            }
        } else {
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    /* toolbar右边按钮的点击事件 */
    private class OnMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_search:
                    if (searchLayout.getVisibility() == View.GONE) {
                        openSearchLayout();
                    } else {
                        searchEvent();
                    }
                    break;
            }
            return true;
        }
    }

    /* 重写了搜索的回车键 */
    private class OnSearchKey implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchEvent();
            }
            return false;
        }
    }
}
