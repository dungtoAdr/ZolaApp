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

import com.bumptech.glide.Glide;
import com.example.zolaapp.R;
import com.example.zolaapp.model.User;
import com.example.zolaapp.retrofit.ApiZola;
import com.example.zolaapp.retrofit.RetrofitClient;
import com.example.zolaapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.MyViewHolder> {
    private Context context;
    private List<User> list; // Danh sách người dùng

    public AllUserAdapter(Context context, List<User> list) {
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
        User currentUser = list.get(position); // Lấy thông tin user tại vị trí position
        holder.text_name.setText(currentUser.getUsername());
        Glide.with(context).load("https://cdn.pixabay.com/photo/2023/09/22/03/51/beautiful-8267949_1280.jpg").circleCrop().into(holder.profile_image);
        holder.profile_image.setImageResource(R.drawable.ic_profile);
        holder.setUser(currentUser); // Truyền thông tin user vào ViewHolder
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private User user; // Lưu trữ thông tin user
        private TextView text_name;
        private ImageView img_add,profile_image;
        private CompositeDisposable compositeDisposable = new CompositeDisposable();
        private ApiZola apiZola;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            apiZola = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiZola.class);
            text_name = itemView.findViewById(R.id.friend_name);
            img_add = itemView.findViewById(R.id.bt_accept);
            profile_image=itemView.findViewById(R.id.image);
            img_add.setOnClickListener(v -> {
                if (user == null) return;

                compositeDisposable.add(apiZola.sendFriendRequest(Utils.current_user.getId(), user.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()) {
                                        Toast.makeText(context, "Lời mời kết bạn đã được gửi!", Toast.LENGTH_SHORT).show();
                                        Log.d("TAG_user_id", user.getId() + "");
                                    } else {
                                        Toast.makeText(context, "Không thể gửi lời mời kết bạn.", Toast.LENGTH_SHORT).show();
                                        Log.e("TAG_request_fail", "Server response: " + userModel.getMessage());
                                        Log.e("TAG_request_fail", "Server response: " + Utils.current_user.getId());
                                        Log.e("TAG_request_fail", "Server response: " + user.getId());
                                    }
                                },
                                throwable -> {
                                    Log.e("TAGgggg_request", "Error sending request: " + throwable.getMessage(), throwable);
                                    Toast.makeText(context, "Có lỗi xảy ra khi gửi lời mời kết bạn.", Toast.LENGTH_SHORT).show();
                                }
                        ));
            });
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
