package com.superbug.moi.cquptlife.model.service;

import com.superbug.moi.cquptlife.config.API;
import com.superbug.moi.cquptlife.model.bean.StudentWrapper;
import com.superbug.moi.cquptlife.model.bean.TeacherWrapper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author moi
 */

public interface ApiService {

    @GET(API.URL.searchStudent)
    Observable<StudentWrapper> getStudents(
            @Query("searchKey") String id,
            @Query("page") int page
    );

    @GET(API.URL.searchTeacher)
    Observable<TeacherWrapper> getTeachers(
            @Query("searchKey") String id,
            @Query("page") int page
    );

}
