package com.zys.baiduphoneguard.listener;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017/11/13.
 */

public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
            if(viewHolder.getLayoutPosition() >= 0 && viewHolder.getLayoutPosition() <= 2){
                return 0;
            }

            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, 0);
        }
        return 0;
    }

    @Override //移动
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getLayoutPosition();
        int toPosition = target.getLayoutPosition();
        moveListener.move(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override //松开
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        finishDragListener.finidDrag();
        super.clearView(recyclerView, viewHolder);
    }

    @Override //选中的时候
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){ //idle 空闲
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }
    //***********************************松开的监听
    public interface FinishDragListener{
        void finidDrag();
    }
    private FinishDragListener finishDragListener;

    public void setFinishDragListener(FinishDragListener finishDragListener) {
        this.finishDragListener = finishDragListener;
    }

    //************************************移动的监听
    public interface MoveListener{
        void move(int fromPosition, int toPosition);
    }

    private MoveListener moveListener;

    public void setMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
    }
}
