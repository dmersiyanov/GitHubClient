package com.dmersianov.githubclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Toast;

import com.dmersianov.githubclient.api.GitApp;
import com.dmersianov.githubclient.pojo.LoginResponse;

import java.io.UnsupportedEncodingException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class LoginActivity extends AppCompatActivity {

    public static String getAuthToken(String name, String pass) {
        byte[] data = new byte[0];
        try {
            data = (name + ":" + pass).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Flowable<LoginResponse> loginObservable = GitApp.getApi().login(getAuthToken("xx", "xx"));


        loginObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        Toast.makeText(getApplicationContext(),loginResponse.getEmail(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Ошибка авторизации " + t.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplicationContext(), "Успешная авторизация", Toast.LENGTH_LONG).show();

                    }
                });

    }

}
