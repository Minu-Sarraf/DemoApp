package com.example.minu.demoapp.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minu.demoapp.AlertUtils.ProgressDialog;
import com.example.minu.demoapp.Model.FeedDataModel;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 6/7/2017.
 */

public class Recyclerview_adapter extends RecyclerView.Adapter<Recyclerview_adapter.viewholder> {


    public List<FeedDataModel.FeedsBean> list = new ArrayList<>();

    Context context;


    public Recyclerview_adapter(Context context, List<FeedDataModel.FeedsBean> list) {
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
        picassoload(list.get(position).getUserImage());
        //  holder.profile_img.s
    }

    public void picassoload(String ivimage) {
//ProgressDialog.displayMaterialProgressDialog(context,"Loading...","Please Wait");
        Picasso.with(context).load(ivimage).fit().centerInside().into(ivimage, new Callback() {
            @Override
            public void onSuccess() {
              /*  if (progressdialog != null) {
                    progressdialog.hide();
                }
                Log.e("sucess", "sucesss");
            }*/


            @Override
            public void onError() {
                /*if (progressdialog != null) {
                    progressdialog.hide();
                }*/
              //  AlertUtils.displayDialog(Documents.this, "ERROR", "You may have poor internet connection OR Your file size is Greater than 3MB", null, "OK", null);
                ivimage.setImageResource(R.drawable.show);
            }
        });
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
