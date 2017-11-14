package com.dmersianov.githubclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.dmersianov.githubclient.api.GitApp;
import com.dmersianov.githubclient.pojo.ReposResponse;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);


        final Flowable<List<ReposResponse>> reposObservable = GitApp.getApi().getRepos();


        ReposRepository.getAllRepos()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        ReposResponse repo = (ReposResponse) o;
                        Toast.makeText(getApplicationContext(), repo.getName(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    static class ReposRepository {

        static Flowable<Object> getAllRepos() {
            Flowable<List<ReposResponse>> reposObservable = GitApp.getApi().getRepos();
            return reposObservable
                    .flatMap(new Function<List<ReposResponse>, Publisher<?>>() {
                        @Override
                        public Publisher<?> apply(List<ReposResponse> reposResponses) throws Exception {
                            return Flowable.fromIterable(reposResponses);
                        }
                    });
        }

    }




}
