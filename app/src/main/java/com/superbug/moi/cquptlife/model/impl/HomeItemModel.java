package com.superbug.moi.cquptlife.model.impl;

import com.superbug.moi.cquptlife.R;
import com.superbug.moi.cquptlife.app.APP;
import com.superbug.moi.cquptlife.model.IHomeItemModel;
import com.superbug.moi.cquptlife.model.bean.HomeItem;

import java.util.ArrayList;

/**
 * Created by moi on 2015/8/5.
 */
public class HomeItemModel implements IHomeItemModel {
    @Override
    public ArrayList<HomeItem> getItems() {
        ArrayList<HomeItem> homeItems = new ArrayList<>();
        HomeItem student = new HomeItem(APP.getContext().getResources().getString(R.string.search_student), R.mipmap.ic_search);
        HomeItem teacher = new HomeItem(APP.getContext().getResources().getString(R.string.search_teacher), R.mipmap.ic_search);
        HomeItem classRoom = new HomeItem(APP.getContext().getResources().getString(R.string.search_class_room), R.mipmap.ic_search);
        HomeItem lesson = new HomeItem(APP.getContext().getResources().getString(R.string.search_lesson), R.mipmap.ic_search);
        homeItems.add(student);
        homeItems.add(teacher);
        homeItems.add(classRoom);
        homeItems.add(lesson);
        return homeItems;
    }
}
