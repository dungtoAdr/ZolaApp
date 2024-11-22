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
import com.example.zolaapp.adapter.AllUserAdapter;
import com.example.zolaapp.model.User;
import com.example.zolaapp.retrofit.ApiZola;
import com.example.zolaapp.retrofit.RetrofitClient;
import com.example.zolaapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private ApiZola apiZola;
    private AllUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        apiZola= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiZola.class);
        recyclerView=findViewById(R.id.recycler_alluser);
        compositeDisposable.add(apiZola.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                List<User> list=userModel.getResult();
                                for(int i=0;i<list.size();i++){
                                    if(list.get(i).getId()==Utils.current_user.getId()){
                                        list.remove(i);
                                    }
                                }
                                for (int i=0;i<list.size();i++){
                                    for(int j=0;j<Utils.list_friend.size();j++){
                                        if(list.get(i).getId()==Utils.list_friend.get(j).getId()){
                                            list.remove(i);
                                        }
                                    }
                                }
                                adapter=new AllUserAdapter(getApplicationContext(),list);
                                recyclerView.setAdapter(adapter);
                            }
                        },throwable -> {
                            Log.d("TAGgggg", throwable.getMessage());
                        }
                ));
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
    }
}