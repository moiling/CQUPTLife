package com.superbug.moi.cquptlife.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.presenter.StudentPresenter;
import com.superbug.moi.cquptlife.ui.adpter.StudentInformationAdapter;
import com.superbug.moi.cquptlife.ui.view.IStudentView;
import com.superbug.moi.cquptlife.util.AnimationEndCallbackListener;
import com.superbug.moi.cquptlife.util.EditKeyboardUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentActivity extends BaseActivity implements View.OnClickListener,
        IStudentView {

    private final int SEARCH_OPEN = 0;
    private final int SEARCH_CLOSE = 1;

    @InjectView(R.id.ed_search) EditText search;
    @InjectView(R.id.iv_search_close) ImageView searchClose;
    @InjectView(R.id.rl_search) CardView searchLayout;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.lv_content) ListView mListView;

    private ArrayList<Student> studentList = new ArrayList<>();
    private MyHandler myHandler = new MyHandler(this);
    private static StudentInformationAdapter adapter;
    private static StudentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.inject(this);
        initToolbar();
        initContent();
        if (presenter == null) {
            presenter = new StudentPresenter(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
        if (isFinishing())
            presenter = null;
    }

    private void initContent() {
        adapter = new StudentInformationAdapter(this, R.layout.item_student, studentList);
        mListView.setAdapter(adapter);
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setLogo(getResources().getDrawable(R.mipmap.ic_title_logo));
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener());
        search.setOnKeyListener(new OnSearchKey());
        searchClose.setOnClickListener(this);
    }

    private void openSearchLayout() {
        searchLayout.setVisibility(View.VISIBLE);
        SearchLayoutAnimation(SEARCH_OPEN, null);
        EditKeyboardUtils.showSoftInput(search);
    }

    private void closeSearchLayout() {
        search.setText("");
        SearchLayoutAnimation(SEARCH_CLOSE, new AnimationEndCallbackListener() {
            @Override
            public void onEnd() {
                searchLayout.setVisibility(View.GONE);
            }
        });
        EditKeyboardUtils.hideSoftInput(search);
    }

    /**
     * 搜索的动画效果
     *
     * @param type     类型，分为打开（SEARCH_OPEN）和关闭（SEARCH_CLOSE）
     * @param listener 动画结束后的回调，只有关闭的时候才需要，打开的时候传null就可以了
     */
    private void SearchLayoutAnimation(int type, final AnimationEndCallbackListener listener) {
        switch (type) {
            case SEARCH_OPEN:
                ValueAnimator showSearchEdit = ObjectAnimator.ofFloat(searchLayout, "ScaleX", 0f, 1f);
                showSearchEdit.setDuration(500).setInterpolator(new AccelerateDecelerateInterpolator());
                showSearchEdit.start();
                break;
            case SEARCH_CLOSE:
                ValueAnimator removeSearchEdit = ObjectAnimator.ofFloat(searchLayout, "ScaleX", 1f, 0f);
                removeSearchEdit.setDuration(500);
                removeSearchEdit.setInterpolator(new AccelerateInterpolator());
                removeSearchEdit.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (listener != null) {
                            listener.onEnd();
                        }
                    }
                });
                removeSearchEdit.start();
                break;
            default:
                break;
        }
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
    public void setStudents(ArrayList<Student> students) {
        studentList.clear();
        studentList.addAll(students);
        Message message = new Message();
        message.obj = studentList;
        message.what = 0;
        myHandler.sendMessage(message);
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

    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivityReference;

        public MyHandler(Activity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
