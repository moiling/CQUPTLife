package com.superbug.moi.cquptlife.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.BaseActivity;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.presenter.StudentPresenter;
import com.superbug.moi.cquptlife.ui.adpter.StudentsAdapter;
import com.superbug.moi.cquptlife.ui.view.IStudentView;
import com.superbug.moi.cquptlife.util.Animations.SearchAnimation;
import com.superbug.moi.cquptlife.util.Utils;
import com.superbug.moi.cquptlife.util.listener.OnAnimationEndListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentActivity extends BaseActivity implements View.OnClickListener, IStudentView {

    @InjectView(R.id.ed_search) EditText search;
    @InjectView(R.id.iv_search_close) ImageView searchClose;
    @InjectView(R.id.rl_search) CardView searchLayout;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.fabBtn) FloatingActionButton fabBtn;
    @InjectView(R.id.rootLayout) CoordinatorLayout rootLayout;
    @InjectView(R.id.lv_content) RecyclerView mRecyclerView;

    private ArrayList<Student> studentList = new ArrayList<>();
    private StudentsAdapter adapter;
    private static StudentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.inject(this);
        if (presenter == null) {
            presenter = new StudentPresenter(this);
        }
        initToolbar();
        initContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
        if (isFinishing()) presenter = null;
    }

    private void initContent() {
        adapter = new StudentsAdapter(this, studentList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, "我给你们讲，必须要连内网才可以用", Snackbar.LENGTH_SHORT).setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });
    }

    private void initToolbar() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        //    mToolbar.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setLogo(getResources().getDrawable(R.mipmap.ic_title_logo));
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener());
        search.setOnKeyListener(new OnSearchKey());
        searchClose.setOnClickListener(this);
    }

    private void openSearchLayout() {
        searchLayout.setVisibility(View.VISIBLE);
        SearchAnimation.start(searchLayout, SearchAnimation.SEARCH_OPEN, null);
        Utils.editShowSoftInput(search);
    }

    private void closeSearchLayout() {
        search.setText("");
        SearchAnimation
                .start(searchLayout, SearchAnimation.SEARCH_CLOSE, new OnAnimationEndListener() {
                    @Override
                    public void onEnd() {
                        searchLayout.setVisibility(View.GONE);
                    }
                });
        Utils.editHideSoftInput(search);
    }

    private void searchEvent() {
        presenter.searchStudent(getStudentInfo());
        closeSearchLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_close:
                closeSearchLayout();
                break;
        }
    }

    @Override
    public String getStudentInfo() {
        return search.getText().toString();
    }

    @Override
    public void setStudents(ArrayList<Student> arr) {
        studentList.clear();
        studentList.addAll(arr);
        adapter.notifyDataSetChanged();
    }

    /**
     * toolbar按钮
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    //toolbar右边按钮的点击事件
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

    //重写了搜索的回车键
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
