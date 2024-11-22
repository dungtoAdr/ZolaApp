package com.example.zolaapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolaapp.R;
import com.example.zolaapp.model.User;
import com.example.zolaapp.retrofit.ApiZola;
import com.example.zolaapp.retrofit.RetrofitClient;
import com.example.zolaapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RequestFriendAdapter extends RecyclerView.Adapter<RequestFriendAdapter.MyViewHolder> {
    private Context context;
    private List<User> list; // Danh sách người dùng
    private User user;
    public RequestFriendAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_requset_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        user = list.get(position); // Lấy thông tin user tại vị trí position
        holder.text_name.setText(user.getUsername());
        Log.d("TAGgggggggg", user.getId()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text_name;
        private ImageView img_add;
        private CompositeDisposable compositeDisposable = new CompositeDisposable();
        private ApiZola apiZola;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            apiZola = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiZola.class);
            text_name = itemView.findViewById(R.id.friend_name);
            img_add = itemView.findViewById(R.id.bt_accept);
            img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compositeDisposable.add(apiZola.handleFriendRequest(user.getId(),"accept")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    users -> {
                                        if (users.isSuccess()){
                                            Toast.makeText(context, "Bạn đã chấp nhận lời mời kết bạn!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(context, users.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },throwable -> {
                                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d("TAGg_request_friend", throwable.getMessage());
                                        Log.d("TAGg_request_friend_1", user.getId()+"");
                                    }
                            ));
                }
            });
        }

    }
}
