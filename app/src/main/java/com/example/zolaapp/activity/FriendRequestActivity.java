package com.example.zolaapp.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolaapp.R;
import com.example.zolaapp.adapter.RequestFriendAdapter;
import com.example.zolaapp.model.User;
import com.example.zolaapp.retrofit.ApiZola;
import com.example.zolaapp.retrofit.RetrofitClient;
import com.example.zolaapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FriendRequestActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private ApiZola apiZola;
    private RequestFriendAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        apiZola= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiZola.class);
        recyclerView=findViewById(R.id.recycler_friendrequest);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        compositeDisposable.add(apiZola.getFriendRequest(Utils.current_user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> {
                            if (users.isSuccess()){
                                List<User> list=users.getResult();
                                adapter=new RequestFriendAdapter(getApplicationContext(),list);
                                recyclerView.setAdapter(adapter);
                            }
                        },throwable -> {
                            Log.d("TAGgggg", throwable.getMessage());
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}