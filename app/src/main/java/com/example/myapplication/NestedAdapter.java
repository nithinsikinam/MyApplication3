package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {
    private List<String> mList;
    private List<DataModel2> mList2;
    private Context context;
    private String chname;
    private String chapter;


    public NestedAdapter(List<String> mList, List<DataModel2> mList2, Context context,String chname,String chapter){
        this.mList = mList;
        this.mList2 = mList2;
        this.context = context;
        this.chname=chname;
        this.chapter=chapter;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item , parent , false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletet obj= new deletet(chname,chapter,mList.get(holder.getAdapterPosition()),context);
                obj.delete();

            }
        });
holder.cV.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, resourcetab.class);
        intent.putExtra("channel",chname);
        intent.putExtra("chapter",chapter);
        intent.putExtra("topic",mList.get(holder.getAdapterPosition()));

        intent.putExtra("object", mList2.get(holder.getAdapterPosition()));
context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private TextView mTv;
        private CardView cV;
        private ImageButton button;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.nestedItemTv);
            cV = itemView.findViewById(R.id.cardView);
            button = itemView.findViewById(R.id.imageButton3);

        }
    }
}