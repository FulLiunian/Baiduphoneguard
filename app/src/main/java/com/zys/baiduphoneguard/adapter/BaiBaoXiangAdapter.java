package com.zys.baiduphoneguard.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.entity.FunctionTable;
import com.zys.baiduphoneguard.listener.MyItemTouchHelper;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BaiBaoXiangAdapter extends RecyclerView.Adapter<BaiBaoXiangAdapter.ViewHolder> implements MyItemTouchHelper.MoveListener {

    public List<FunctionTable> functionTableList;
    public BaiBaoXiangAdapter(List<FunctionTable> functionTableList){
        this.functionTableList = functionTableList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View iteView = layoutInflater.inflate(R.layout.bbx_item_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(iteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FunctionTable functionTable = functionTableList.get(position);

        Drawable icon = BaiduUtils.getFunctionIcon(functionTable.getFuncPic());

        holder.functionIcon.setImageDrawable(icon);

        holder.functionName.setText(functionTable.getFuncName());

    }

    @Override
    public int getItemCount() {
        return functionTableList.size();
    }

    @Override
    public void move(int fromPosition, int toPosition) {
        //当用户在MyItemHelper类中拖拽的时候，就会触发此事件
        boolean isFromPostionDrag = functionTableList.get(fromPosition).getFuncFixed();
        boolean isToPostionDrag = functionTableList.get(toPosition).getFuncFixed();

        if(isFromPostionDrag || isToPostionDrag){
            return;
        }
        //往右进行拖拽
        if(fromPosition < toPosition){
            for(int i = fromPosition;i < toPosition;i++){
                Collections.swap(functionTableList,i,i+1);
            }
        }
        //往左进行拖拽
        if(fromPosition > toPosition){
            for(int i = fromPosition;i > toPosition;i--){
                Collections.swap(functionTableList, i, i-1);
            }
        }
        //通知适配器发生改变
        notifyItemMoved(fromPosition, toPosition);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.function_icon)
        ImageView functionIcon;
        @BindView(R.id.function_name)
        TextView functionName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

