package com.example.minu.demoapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minu.demoapp.Activity.UserProfileActivity;
import com.example.minu.demoapp.CalculatewindowSize.DisplaySize;
import com.example.minu.demoapp.Model.FeedDataModel;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog.ShowLog;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 6/7/2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.viewholder> {


    public List<FeedDataModel.FeedsBean> list = new ArrayList<>();

    Context context;


    public RecyclerviewAdapter(Context context, List<FeedDataModel.FeedsBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_row, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        holder.user_name.setText(list.get(position).getUserName());
        holder.user_status.setText(list.get(position).getStatus());

        picassoload(list.get(position).getUserImage(), holder.profile_img, holder);
        picassoload(list.get(position).getFeedImage(), holder.feed_image, holder);
        //  holder.profile_img.s
        Log.e("url", list.get(position).getFeedImage());
    }

    //To Overcome out of memory exception, use resize

    public void picassoload(final String ivimage, final ImageView holderImg, final viewholder holder) {
        holder.progressView.setVisibility(View.VISIBLE);
        holder.progressView.start();
        final Point displySize = DisplaySize.getDisplaySize(context);
        int size = (int) Math.ceil(Math.sqrt(displySize.x * displySize.y));
        if (holderImg == holder.profile_img) {
            size = 70;
        }
        Picasso.with(context).load(ivimage).resize(size,size).placeholder(R.drawable.leapfroglogo).into(holderImg, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressView.stop();
                holder.progressView.setVisibility(View.GONE);
                holderImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                ShowLog.log("image", ivimage + " error");
                holder.progressView.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public int getItemCount() {

        ShowLog.log("listsize", String.valueOf(list.size()));
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_name, user_status;
        ImageView feed_image;
        PorterShapeImageView profile_img;
        private final ProgressView progressView;

        public viewholder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name);
            user_status = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            progressView = (ProgressView) itemView.findViewById(R.id.progressview);
            profile_img = itemView.findViewById(R.id.profilePic);
            feed_image = itemView.findViewById(R.id.feedImage1);
            user_name.setOnClickListener(this);
            profile_img.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.name || view.getId() == R.id.profilePic) {
                Intent profile = new Intent(context, UserProfileActivity.class);
                context.startActivity(profile);
            }
        }
    }


}
