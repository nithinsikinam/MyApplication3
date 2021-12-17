package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Joinadapter extends RecyclerView.Adapter<Joinadapter.ViewHolder>{
private join json;
private Context context;

    public Joinadapter(join json,Context context) {
        this.json = json;
        this.context=context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_join , parent , false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull Joinadapter.ViewHolder holder, int position) {

        holder.type1.setText(json.joined.get(position));

        holder.liner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context,MainActivity.class);
               intent.putExtra("id",json.joined.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return json.joined.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout liner ;
        private TextView type1;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            liner = itemView.findViewById(R.id.abcdef);

            type1 = itemView.findViewById(R.id.abcd);


        }
    }
}
