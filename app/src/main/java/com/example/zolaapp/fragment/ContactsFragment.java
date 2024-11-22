package com.example.zolaapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolaapp.R;
import com.example.zolaapp.activity.FriendRequestActivity;
import com.example.zolaapp.activity.ZolaActivity;
import com.example.zolaapp.adapter.FriendAdapter;
import com.example.zolaapp.model.Friend;
import com.example.zolaapp.retrofit.ApiZola;
import com.example.zolaapp.retrofit.RetrofitClient;
import com.example.zolaapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsFragment extends Fragment {
    private FriendAdapter adapter;
    private RecyclerView recyclerView;
    private Button bt_friendrequest,bt_call,bt_videocall;
    private List<Friend> friends;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private ApiZola apiZola;
    private ZolaActivity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_listfriend);
        bt_friendrequest=view.findViewById(R.id.bt_friend_request);
        apiZola= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiZola.class);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        Call<List<Friend>> call=apiZola.getFriends(Utils.current_user.getId());
        call.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    friends = response.body(); // Lấy danh sách bạn bè từ API
                    Utils.list_friend=response.body();
                    for (Friend friend : friends) {
                        Log.d("TAG", "Friend: " + friend.getUsername());
                    }
                } else {
                    Log.e("TAG", "Response Error: " + response.code());
                }
                adapter=new FriendAdapter(getContext(),friends);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.e("TAG", "API Call Failed", t);
            }
        });
        bt_friendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), FriendRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
