package com.example.cwiczenie_3_4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.MyViewHolder> {
    
    private List<ItemData> mList;
    private final Lista_f1.ListFragmentInteractionListener mListener;

    
    public MyRVAdapter(Lista_f1.ListFragmentInteractionListener listener){
        mListener = listener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv1.setText(mList.get(position).name);
        holder.tv2.setText("Wiek: "+ mList.get(position).age);

        switch (mList.get(position).gender){
            case 0:
                holder.img.setImageResource(R.drawable.female);
                break;
            case 1:
                holder.img.setImageResource(R.drawable.male);
                break;
            case 2:
                holder.img.setImageResource(R.drawable.agender);
                break;
            case 3:
                holder.img.setImageResource(R.drawable.trap);
                break;
            case 4:
                holder.img.setImageResource(R.drawable.trapreverse);
                break;
            case 5:
                holder.img.setImageResource(R.drawable.nonbinary);
                break;
        }


        int color = android.graphics.Color.rgb(mList.get(position).redProgress,mList.get(position).greenProgress,mList.get(position).blueProgress);
        holder.colorv.setBackgroundColor(color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sentToInputchose(mList.get(position));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onDeleteItem(mList.get(position));
                return true;
            }
        });

    }


    public int getItemCount() {
        if (mList!=null){
            return mList.size();
        }
        else
            return 0;
    }

    void setItemList(List<ItemData>list){
        mList = list;
        notifyDataSetChanged();
    }

    public ItemData getItemAt(int position){
        return mList.get(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        TextView tv1;
        TextView tv2;
        ImageView img;
        View colorv;
        public ItemData mItem;
        
        public MyViewHolder(View view) {
            super(view);

             tv1 = (TextView) view.findViewById(R.id.row_tv1);
             tv2 = (TextView) view.findViewById(R.id.row_tv2);
             img = (ImageView) view.findViewById(R.id.row_image);
             colorv = view.findViewById(R.id.view_Row);
            
        }

    }

}
