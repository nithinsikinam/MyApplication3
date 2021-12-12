package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class resourceadapter extends RecyclerView.Adapter<resourceadapter.ViewHolder> {
    private List<String> URL;
private  Context context;
    private List<String> type;
    private List<String> Giver;
    public resourceadapter(DataModel2 data,Context context){
        this.URL=data.URL;
        this.type=data.type;
        this.Giver=data.Giver;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_resource , parent , false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    URL.get(position);
    holder.type1.setText(type.get(position));
    holder.name1.setText(Giver.get(position));
    holder.liner.setOnClickListener(new View.OnClickListener() {
        Intent intent = new Intent(context, website1.class);
        @Override
        public void onClick(View v) {
            String t= type.get(holder.getAdapterPosition()) ;
            if(t.equals("ppt")){
                Log.d("test1",t+"/");
                intent.putExtra("url","http://docs.google.com/gview?embedded=true&url="+URL.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
            else{
                intent.putExtra("url",URL.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }


        }
    });
    }

    @Override
    public int getItemCount() {
        return Giver.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout liner ;
        private TextView type1;
        private TextView name1;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            liner = itemView.findViewById(R.id.liner);
            name1 = itemView.findViewById(R.id.textView6);
            type1 = itemView.findViewById(R.id.textView7);


        }
    }
}
