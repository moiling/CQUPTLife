package com.superbug.moi.cquptlife.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.model.bean.Life;
import com.superbug.moi.cquptlife.presenter.LifePresenter;
import com.superbug.moi.cquptlife.ui.activity.LifeInfoActivity;

import java.util.List;

/**
 * @author moi
 */

public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.LifeViewHolder> {

    private Context mContext;
    private LifePresenter presenter;

    public LifeAdapter(Context context, LifePresenter presenter) {
        mContext = context;
        this.presenter = presenter;
    }

    @Override
    public LifeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new LifeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_life, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(LifeViewHolder holder, int i) {
        List<Life> lives = presenter.getLives();
        Life life = lives.get(i);
        String name = life.getName();
        String leftBottom, rightBottom;
        if (life.getType() == Life.Type.STUDENT) {
            if (life.getSex() == Life.Sex.MALE) {
                holder.rightTop.setText("♂");
                holder.rightTop.setTextColor(ContextCompat.getColor(mContext, R.color.blue_primary_color));
            } else {
                holder.rightTop.setText("♀");
                holder.rightTop.setTextColor(ContextCompat.getColor(mContext, R.color.red_primary_color));
            }
            leftBottom = life.getInfo3();
            rightBottom = life.getInfo1();
        } else {
            holder.rightTop.setText(life.getInfo1());
            holder.rightTop.setTextColor(ContextCompat.getColor(mContext, R.color.blue_primary_color));
            leftBottom = life.getInfo3();
            rightBottom = life.getInfo2();
        }

        holder.name.setText(name);
        holder.leftBottom.setText(leftBottom);
        holder.rightBottom.setText(rightBottom);
        holder.ripple.setOnClickListener(v -> LifeInfoActivity.jump(mContext, life));
    }

    @Override
    public int getItemCount() {
        return presenter.getLives().size();
    }

    static class LifeViewHolder extends RecyclerView.ViewHolder {

        MaterialRippleLayout ripple;
        TextView name;
        TextView rightTop;
        TextView leftBottom;
        TextView rightBottom;

        public LifeViewHolder(View itemView) {
            super(itemView);
            rightBottom = (TextView) itemView.findViewById(R.id.tv_grade);
            leftBottom = (TextView) itemView.findViewById(R.id.tv_major);
            rightTop = (TextView) itemView.findViewById(R.id.tv_sex);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        }
    }
}
