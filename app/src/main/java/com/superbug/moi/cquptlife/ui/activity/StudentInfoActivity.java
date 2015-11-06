package com.superbug.moi.cquptlife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.APP;
import com.superbug.moi.cquptlife.app.BaseActivity;
import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.bean.Student;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentInfoActivity extends BaseActivity {

    public static void actionStart(Context context, Student student) {
        Intent intent = new Intent(context, StudentInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @InjectView(R.id.tv_name) TextView tvName;
    @InjectView(R.id.tv_id) TextView tvId;
    @InjectView(R.id.tv_grade) TextView tvGrade;
    @InjectView(R.id.tv_faculty) TextView tvFaculty;
    @InjectView(R.id.tv_major) TextView tvMajor;
    @InjectView(R.id.tv_class) TextView tvClass;
    @InjectView(R.id.iv_pic) ImageView mImageView;
    @InjectView(R.id.toolbar) Toolbar mToolbar;

    private final int CET = 0;
    private final int TEC = 1;
    private int type = CET;
    private Student student;
    private String id;
    private ImageLoader.ImageListener listener;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.inject(this);

        initToolbar();
        initPic();
        initStudentInfo();

    }

    void initStudentInfo() {
        tvName.setText(APP.getContext().getResources().getString(R.string.name) + "：" + student.getStudentName());
        tvId.setText(APP.getContext().getResources().getString(R.string.std_id) + "：" + student.getStudentId());
        tvClass.setText(APP.getContext().getResources().getString(R.string.class_) + "：" + student.getStudentClass());
        tvGrade.setText(APP.getContext().getResources().getString(R.string.grade) + "：" + student.getStudentGrade());
        tvFaculty.setText(APP.getContext().getResources().getString(R.string.faculty) + "：" + student.getStudentFaculty());
        tvMajor.setText(APP.getContext().getResources().getString(R.string.major) + "：" + student.getStudentMajor());
    }

    private void initPic() {
        student = (Student) getIntent().getSerializableExtra("student");
        id = student.getStudentId();
        showPic();
    }

    private void showPic() {
        if (type == CET) {
            Picasso.with(this)
                    .load(API.URL.studentCETPic + id + API.URL.studentCETPicEnd)
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.error)
                    .into(mImageView);
        } else {
            Picasso.with(this)
                    .load(API.URL.studentPic + id)
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.error)
                    .into(mImageView);
        }
    }

    private void initToolbar() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        //    mToolbar.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        mToolbar.setTitle(" " + getResources().getString(R.string.student_info));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_info, menu);
        return true;
    }

    private class OnMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_switch:
                    type = type == CET ? TEC : CET;
                    showPic();
            }
            return true;
        }
    }
}
