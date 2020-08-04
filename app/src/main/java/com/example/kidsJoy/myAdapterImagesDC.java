package com.example.kidsJoy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myAdapterImagesDC extends RecyclerView.Adapter<myAdapterImagesDC.MyViewHolder> {


    Context context;
    //imageModel imageModelX;
    ArrayList<imageModel> imageModelXX;



    public myAdapterImagesDC(Context c, ArrayList<imageModel> m){
        context = c;
        imageModelXX = m;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_images_dc,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull myAdapterImagesDC.MyViewHolder holder, int position) {


        String url = imageModelXX.get(position).getUrl().toString();
        Picasso.get().load(url).into(holder.picDC);


    }



    @Override
    public int getItemCount() {
        return imageModelXX.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView picDC;

        public MyViewHolder(View itemView) {
            super(itemView);

            picDC = (ImageView) itemView.findViewById(R.id.picoRView);



        }



    }














    }
