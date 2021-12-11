package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
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
    private List<String> URL= new ArrayList<>();
private  Context context;
    private List<String> type=new ArrayList<>();
    private List<String> Giver=new ArrayList<>();
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
        Intent intent = new Intent(context, website.class);
        @Override
        public void onClick(View v) {
switch (type.get(holder.getAdapterPosition())){
    case "ppt":


        intent.putExtra("url","http://docs.google.com/gview?embedded=true&url="+ URL.get(holder.getAdapterPosition()));
        context.startActivity(intent);

    default:
        intent.putExtra("url", URL.get(holder.getAdapterPosition()));
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
