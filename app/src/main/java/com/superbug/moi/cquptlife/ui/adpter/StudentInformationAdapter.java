package com.superbug.moi.cquptlife.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.model.bean.Student;
import com.superbug.moi.cquptlife.ui.activity.StudentInfoActivity;

import java.util.List;

public class StudentInformationAdapter extends ArrayAdapter<Student> {

    private int resourceId;
    private Context mContext;

    public StudentInformationAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Student student = getItem(position);
        String studentName = student.getStudentName();
        String studentSex = student.getStudentSex();
        String studentMajor = student.getStudentMajor();
        String studentGrade = student.getStudentGrade();

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.grade = (TextView) convertView.findViewById(R.id.tv_grade);
            viewHolder.major = (TextView) convertView.findViewById(R.id.tv_major);
            viewHolder.sex = (TextView) convertView.findViewById(R.id.tv_sex);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.ripple = (MaterialRippleLayout) convertView.findViewById(R.id.ripple);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(studentName);
        if (studentSex.equals("男")) {
            viewHolder.sex.setText("♂");
            viewHolder.sex.setTextColor(mContext.getResources().getColor(R.color.blue_primary_color));
        } else {
            viewHolder.sex.setText("♀");
            viewHolder.sex.setTextColor(mContext.getResources().getColor(R.color.red_primary_color));
        }

        viewHolder.ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentInfoActivity.actionStart(getContext(), student);
            }
        });
        viewHolder.major.setText(studentMajor);
        viewHolder.grade.setText(studentGrade);
        return convertView;
    }

    private class ViewHolder {
        MaterialRippleLayout ripple;
        TextView name;
        TextView sex;
        TextView major;
        TextView grade;
    }
}
