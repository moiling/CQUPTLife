package com.superbug.moi.cquptlife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.squareup.picasso.Picasso;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.BaseActivity;
import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StudentInfoActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    public static void actionStart(Context context, Student student) {
        Intent intent = new Intent(context, StudentInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Bind(R.id.tv_name) TextView tvName;
    @Bind(R.id.tv_id) TextView tvId;
    @Bind(R.id.tv_grade) TextView tvGrade;
    @Bind(R.id.tv_faculty) TextView tvFaculty;
    @Bind(R.id.tv_major) TextView tvMajor;
    @Bind(R.id.tv_class) TextView tvClass;
    @Bind(R.id.iv_pic) ImageView mImageView;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    private final int CET = 0;
    private final int TEC = 1;
    private int type = CET;
    private Student student;
    private String id;
    private boolean hasCET;
    private boolean hasTEC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.bind(this);
        initToolbar();
        initPic();
        initStudentInfo();
        checkColor();
        mImageView.setOnClickListener(this);
        mImageView.setOnLongClickListener(this);
    }

    private void checkColor() {
        int color = R.color.primary_color;
        String colorName = (String) SPUtils.get(StudentInfoActivity.this, "color", "ORANGE");
        if (colorName != null) {
            switch (colorName) {
                case "ORANGE":
                    color = R.color.primary_color;
                    break;
                case "BLUE":
                    color = R.color.blue_primary_color;
                    break;
            }
        }
        mToolbar.setBackgroundColor(getResources().getColor(color));
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) setBarTintColor(getResources().getColor(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

    void initStudentInfo() {
/*        Typeface face = Typeface.createFromAsset(this.getAssets(), "fonts/square_fonts.TTF");
        tvName.setTypeface(face);*/

        tvName.setText(student.getStudentName());
        tvId.setText(student.getStudentId());
        tvClass.setText(student.getStudentClass());
        tvGrade.setText(student.getStudentGrade());
        tvFaculty.setText(student.getStudentFaculty());
        tvMajor.setText(student.getStudentMajor());
    }

    private void initPic() {
        student = (Student) getIntent().getSerializableExtra("student");
        id = student.getStudentId();
        showPic();
    }

    private void showPic() {
        hasCET = (boolean) SPUtils.get(this, "hasCET", false);
        hasTEC = (boolean) SPUtils.get(this, "hasTEC", false);
        if (type == CET) {
            if (hasCET) {
                Picasso.with(this)
                        .load(API.URL.studentCETPic + id + API.URL.studentCETPicEnd)
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.error)
                        .into(mImageView);
            } else {
                Picasso.with(this).load(R.mipmap.ic_pic_no_use).into(mImageView);
            }
        } else {
            if (hasTEC) {
                Picasso.with(this)
                        .load(API.URL.studentPic + id)
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.error)
                        .into(mImageView);
            } else {
                Picasso.with(this).load(R.mipmap.ic_pic_no_use).into(mImageView);
            }
        }
    }

    private void initToolbar() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        //    mToolbar.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        mToolbar.setTitle(" " + getResources().getString(R.string.student_info));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_info, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_pic:
                if (type == CET && !hasCET) {
                    new MaterialDialog.Builder(this)
                            .title("请输入密钥")
                            .content("没有密钥的请贿赂管理员~")
                            .input("密钥", null, (materialDialog, charSequence) -> {
                                String input = charSequence.toString();
                                if (input.equals(API.KEY.CET_PIC_KEY)) {
                                    Toast.makeText(StudentInfoActivity.this, "~", Toast.LENGTH_SHORT).show();
                                    SPUtils.put(StudentInfoActivity.this, "hasCET", true);
                                    showPic();
                                } else {
                                    Toast.makeText(StudentInfoActivity.this, "没有密钥的不要乱试！", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .theme(Theme.LIGHT)
                            .show();
                }
                if (type == TEC && !hasTEC) {
                    new MaterialDialog.Builder(this)
                            .title("请输入密钥")
                            .content("没有密钥的请贿赂管理员~")
                            .input("密钥", null, (materialDialog, charSequence) -> {
                                String input = charSequence.toString();
                                if (input.equals(API.KEY.NORMAL_PIC_KEY)) {
                                    Toast.makeText(StudentInfoActivity.this, "~", Toast.LENGTH_SHORT).show();
                                    SPUtils.put(StudentInfoActivity.this, "hasTEC", true);
                                    showPic();
                                } else {
                                    Toast.makeText(StudentInfoActivity.this, "没有密钥的不要乱试！" + input, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .theme(Theme.LIGHT)
                            .show();
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "孩子，你是想下载么→_→", Toast.LENGTH_SHORT).show();
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
