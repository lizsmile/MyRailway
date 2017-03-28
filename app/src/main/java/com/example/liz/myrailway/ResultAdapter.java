package com.example.liz.myrailway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/3/28.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ListViewHolder> {
    private int mNumberOfTrains;
    final private ListItemClickListener mOnClickListner;
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }
    //构造器
    public ResultAdapter(int numberOfTrains,ListItemClickListener listener){
        mNumberOfTrains = numberOfTrains;
        mOnClickListner = listener;
        viewHolderCount = 0;
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.train_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ListViewHolder viewHolder = new ListViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        //Log.d(TAG, "#" + position);
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return mNumberOfTrains;
    }
    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView trainListItemView;
        //构造器
        public ListViewHolder(View view){
            super(view);
            trainListItemView = (TextView)itemView.findViewById(R.id.train_item_member);
            viewHolderIndex = (TextView)itemView.findViewById(R.id.view_holder_instance);
            view.setOnClickListener(this);
        }
        void bind(int listIndex){
            trainListItemView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View v) {
            int clickedPositon = getAdapterPosition();
            mOnClickListner.onListItemClick(clickedPositon);
        }
    }
}
