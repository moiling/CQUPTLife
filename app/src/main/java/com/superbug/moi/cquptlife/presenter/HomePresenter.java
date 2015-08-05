package com.superbug.moi.cquptlife.presenter;

import com.superbug.moi.cquptlife.model.IHomeItemModel;
import com.superbug.moi.cquptlife.model.bean.HomeItem;
import com.superbug.moi.cquptlife.model.impl.HomeItemModel;
import com.superbug.moi.cquptlife.ui.vu.IHomeVu;

import java.util.ArrayList;

/**
 * Created by moi on 2015/8/5.
 */
public class HomePresenter {

    private IHomeItemModel model;
    private IHomeVu v;
    private ArrayList<HomeItem> items = new ArrayList<>();

    public HomePresenter(IHomeVu v) {
        this.v = v;
        model = new HomeItemModel();
    }

    public void onRelieveView() {
        v = null;
    }

    public void loadItems() {
        items.clear();
        items.addAll(model.getItems());
        v.setItems();
    }

    public ArrayList<HomeItem> getItems() {
        return items;
    }
}
