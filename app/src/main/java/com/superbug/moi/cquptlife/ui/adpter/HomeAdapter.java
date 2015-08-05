package com.superbug.moi.cquptlife.ui.adpter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.model.bean.HomeItem;
import com.superbug.moi.cquptlife.presenter.HomePresenter;
import com.superbug.moi.cquptlife.ui.activity.StudentActivity;

import java.util.ArrayList;

/**
 * Created by moi on 2015/8/5.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context mContext;
    private HomePresenter presenter;

    public void whereGo(int pos) {
        switch (pos) {
            case 0:
                StudentActivity.actionStart(mContext);
                break;
            default:
                break;
        }
    }

    public HomeAdapter(Context context, HomePresenter presenter) {
        mContext = context;
        this.presenter = presenter;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeViewHolder holder = new HomeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        ArrayList<HomeItem> homeItems = presenter.getItems();
        HomeItem homeItem = homeItems.get(position);
        String text = homeItem.getText();
        int picId = homeItem.getPicId();
        holder.textView.setText(text);
        holder.imageView.setImageResource(picId);
        holder.imageView.setColorFilter(mContext.getResources().getColor(R.color.primary_color), PorterDuff.Mode.SRC_IN);
        holder.rippleLayout.setRippleColor(mContext.getResources().getColor(R.color.divider_color));
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whereGo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.getItems().size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        MaterialRippleLayout rippleLayout;

        public HomeViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_item);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        }
    }
}
