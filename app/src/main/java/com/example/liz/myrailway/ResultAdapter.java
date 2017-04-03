package com.example.liz.myrailway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/3/28.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ListViewHolder> {
    //声明holder中view的数量
    private String[] mTrainData;

    private int mNumberOfTrains;
    private final ResultAdapterOnclickHandler mClickHandler;
    //接口负责接受点击的信息
    public interface ResultAdapterOnclickHandler{
        void onClick(String clickedItemIndex);
    }
    //构造器
    public ResultAdapter(ResultAdapterOnclickHandler clickHandler){
        mClickHandler = clickHandler;
    }
    //viewholder类继承onclicklistener接口
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView trainMember;
        //构造器
        public ListViewHolder(View view) {
            super(view);
            trainMember = (TextView) view.findViewById(R.id.train_item_member);
            // 为view调用监听器
            view.setOnClickListener(this);
        }
        //通过onclick方法将点击的信息传递给handler
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String trainSingle = mTrainData[adapterPosition];
            mClickHandler.onClick(trainSingle);
            Log.v("onclick","result adapter");
        }
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
        String trainForThisItem = mTrainData[position];
        holder.trainMember.setText(trainForThisItem);
    }
    @Override
    public int getItemCount() {
        if (null == mTrainData) return 0;
        return mTrainData.length;
    }
    public void setTrainData(String[] trainData) {
        mTrainData = trainData;
        notifyDataSetChanged();
    }
}
