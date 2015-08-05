package com.superbug.moi.cquptlife.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.BaseActivity;
import com.superbug.moi.cquptlife.presenter.HomePresenter;
import com.superbug.moi.cquptlife.ui.adpter.HomeAdapter;
import com.superbug.moi.cquptlife.ui.vu.IHomeVu;
import com.superbug.moi.cquptlife.ui.widget.DividerItemDecoration;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends BaseActivity implements IHomeVu, View.OnClickListener {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.lv_content) RecyclerView mRecyclerView;
    @InjectView(R.id.fabBtn) FloatingActionButton fabBtn;
    @InjectView(R.id.rootLayout) CoordinatorLayout rootLayout;
    @InjectView(R.id.collapsingToolbarLayout) CollapsingToolbarLayout collapsingToolbarLayout;

    private HomeAdapter adapter;
    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        if (presenter == null) {
            presenter = new HomePresenter(this);
        }

        initToolbar();
        initContent();
    }

    private void initContent() {
        adapter = new HomeAdapter(this, presenter);
        getItems();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        fabBtn.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        collapsingToolbarLayout.setTitle(" " + getResources().getString(R.string.app_name));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.secondary_text));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtn:
                Snackbar.make(rootLayout, "我给你们讲，必须要连内网才可以用", Snackbar.LENGTH_SHORT).setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    @Override
    public void setItems() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getItems() {
        presenter.loadItems();
    }
}
