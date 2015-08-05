package com.superbug.moi.cquptlife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.superbug.moi.cquptlife.R;
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

    @InjectView(R.id.iv_pic) ImageView mImageView;
    @InjectView(R.id.toolbar) Toolbar mToolbar;

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

    }

    private void initPic() {
        student = (Student) getIntent().getSerializableExtra("student");
        id = student.getStudentId();

        RequestQueue mQueue = Volley.newRequestQueue(this);

        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        listener = ImageLoader.getImageListener(mImageView, R.mipmap.loading, R.mipmap.error);
        imageLoader.get(API.URL.studentPic + id, listener);
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
    }
}
