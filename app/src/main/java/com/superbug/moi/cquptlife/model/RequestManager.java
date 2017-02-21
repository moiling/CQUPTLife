package com.superbug.moi.cquptlife.model;

import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.bean.StudentWrapper;
import com.superbug.moi.cquptlife.model.bean.TeacherWrapper;
import com.superbug.moi.cquptlife.model.service.ApiService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public enum RequestManager {

    INSTANCE;

    private static final int DEFAULT_TIMEOUT = 30;
    private ApiService mApiService;

    RequestManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.URL.END)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    public static RequestManager getInstance() {
        return INSTANCE;
    }


    private <T> Subscription emitObservable(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public Subscription searchStudents(Subscriber<List<StudentWrapper.Student>> subscriber, String id, int page) {
        Observable<List<StudentWrapper.Student>> observable = mApiService.getStudents(id, page)
                .map(StudentWrapper::getRows);
        return emitObservable(observable, subscriber);
    }

    public Subscription searchTeachers(Subscriber<List<TeacherWrapper.Teacher>> subscriber, String id, int page) {
        Observable<List<TeacherWrapper.Teacher>> observable = mApiService.getTeachers(id, page)
                .map(TeacherWrapper::getRows);
        return emitObservable(observable, subscriber);
    }
}
