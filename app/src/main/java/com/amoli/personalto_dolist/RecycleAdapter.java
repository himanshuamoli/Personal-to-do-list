package com.amoli.personalto_dolist;

/**
 * Created by Himanshu on 5/4/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;



public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    List<Information> data;
    LayoutInflater inflater;
    public RecycleAdapter(Context context, List<Information> data)
    {   this.data=data;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.custom_recycler_view,parent,false);
        MyViewHolder myViewholder=new MyViewHolder(v);
        return myViewholder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current=data.get(position);
        holder.title.setText(current.title);
        holder.desc.setText(current.desc);
        holder.date.setText(current.date+"  "+current.time);
        holder.place.setText("Location : "+current.place);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView title,desc,place,date;

        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.list_title);
            desc= (TextView) itemView.findViewById(R.id.list_desc);
            date= (TextView) itemView.findViewById(R.id.list_date);
            place=(TextView)itemView.findViewById(R.id.list_place);


        }
    }
}
