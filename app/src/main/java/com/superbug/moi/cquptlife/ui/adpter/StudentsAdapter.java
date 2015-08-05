package com.superbug.moi.cquptlife.ui.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.presenter.StudentPresenter;
import com.superbug.moi.cquptlife.ui.activity.StudentInfoActivity;

import java.util.ArrayList;

/**
 * Created by moi on 2015/8/5.
 */
public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private Context mContext;
    private StudentPresenter presenter;

    public StudentsAdapter(Context context, StudentPresenter presenter) {
        mContext = context;
        this.presenter = presenter;
    }

    @Override
    public StudentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        StudentsViewHolder holder = new StudentsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_student, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(StudentsViewHolder holder, int i) {
        ArrayList<Student> students = presenter.getStudent();
        final Student student = students.get(i);
        final String studentName = student.getStudentName();
        String studentSex = student.getStudentSex();
        String studentMajor = student.getStudentMajor();
        String studentGrade = student.getStudentGrade();

        holder.name.setText(studentName);
        if (studentSex.equals("男")) {
            holder.sex.setText("♂");
            holder.sex.setTextColor(mContext.getResources().getColor(R.color.blue_primary_color));
        } else {
            holder.sex.setText("♀");
            holder.sex.setTextColor(mContext.getResources().getColor(R.color.red_primary_color));
        }

        holder.ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentInfoActivity.actionStart(mContext, student);
            }
        });
        holder.major.setText(studentMajor);
        holder.grade.setText(studentGrade);
    }

    @Override
    public int getItemCount() {
        return presenter.getStudent().size();
    }

    class StudentsViewHolder extends RecyclerView.ViewHolder {

        MaterialRippleLayout ripple;
        TextView name;
        TextView sex;
        TextView major;
        TextView grade;

        public StudentsViewHolder(View itemView) {
            super(itemView);
            grade = (TextView) itemView.findViewById(R.id.tv_grade);
            major = (TextView) itemView.findViewById(R.id.tv_major);
            sex = (TextView) itemView.findViewById(R.id.tv_sex);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        }
    }
}
