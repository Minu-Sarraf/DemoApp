package com.example.minu.demoapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minu.demoapp.Model.FeedDataModel;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 6/7/2017.
 */

public class Recyclerview_adapter extends RecyclerView.Adapter<Recyclerview_adapter.viewholder> {


    public List<FeedDataModel> list = new ArrayList<>();


    public Recyclerview_adapter(List<FeedDataModel> list) {
        this.list = list;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_row, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {


        //Log.d("data1", pos(position).getName());
       // holder.user_name.setText();
       // holder.user_status.setText(pos.getAd());


    }

    @Override
    public int getItemCount() {

        ShowLog.log("listsize", String.valueOf(list.size()));

        return list.size();
    }


   /* public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }*/


    public class viewholder extends RecyclerView.ViewHolder {
        TextView user_name, user_status;
        ImageView feed_image, profile_img;

        public viewholder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name);
            user_status = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            profile_img = itemView.findViewById(R.id.profilePic);
            feed_image = (ImageView) itemView.findViewById(R.id.feedImage1);
        }

    }


}
