package com.zys.baiduphoneguard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.adapter.BaiBaoXiangAdapter;
import com.zys.baiduphoneguard.dao.FunctionTableDao;
import com.zys.baiduphoneguard.entity.FunctionTable;
import com.zys.baiduphoneguard.listener.MyItemTouchHelper;
import com.zys.baiduphoneguard.listener.OnRecyclerItemClickListener;
import com.zys.baiduphoneguard.utils.DividerGridItemDecoration;

import java.util.List;

import butterknife.BindView;

public class BaiBaoXiangActivity extends BaseActivity implements MyItemTouchHelper.FinishDragListener {

    @BindView(R.id.rv_functions)
    RecyclerView rv_functions;

    private BaiBaoXiangAdapter adapter;

    private List<FunctionTable> functionTables;

    private ItemTouchHelper itemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bai_bao_xiang;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        functionTables = BDApplication.getFunctionTableDao().queryBuilder().orderAsc(FunctionTableDao.Properties.FuncIndex).build().list();

        adapter = new BaiBaoXiangAdapter(functionTables);

        rv_functions.setLayoutManager(new GridLayoutManager(this, 3));
        rv_functions.addItemDecoration(new DividerGridItemDecoration(this));
        rv_functions.setAdapter(adapter);

        MyItemTouchHelper callback = new MyItemTouchHelper();
        callback.setMoveListener(adapter);
        callback.setFinishDragListener(this);

        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv_functions);

        rv_functions.addOnItemTouchListener(new OnRecyclerItemClickListener(rv_functions){

            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if(!functionTables.get(vh.getLayoutPosition()).getFuncFixed()){
                    itemTouchHelper.startDrag(vh);
                }

            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

                FunctionTable table = functionTables.get(vh.getLayoutPosition());

                if(table.getFuncName().equals("回首页")){
                    finish();
                    overridePendingTransition(R.anim.main_exit_amin, R.anim.down_exit_anim);
                }
                if(table.getFuncName().equals("家人守护")){
                    startActivity(new Intent(BaiBaoXiangActivity.this, FamilyProtectedActivity.class));
                    overridePendingTransition(R.anim.main_exit_amin, R.anim.down_exit_anim);
                }
                if (table.getFuncName().equals("病毒查杀")){
                    startActivity(new Intent(BaiBaoXiangActivity.this, BingDuChaShaActivity.class));
                    overridePendingTransition(R.anim.main_exit_amin, R.anim.down_exit_anim);
                }
                if(table.getFuncName().equals("软件锁")){
                    startActivity(new Intent(BaiBaoXiangActivity.this, RuanJianSuoActivity.class));
                    overridePendingTransition(R.anim.main_exit_amin, R.anim.down_exit_anim);
                }
                if(table.getFuncName().equals("骚扰拦截")){
                    startActivity(new Intent(BaiBaoXiangActivity.this, SaoRaoLanJieActivity.class));
                    overridePendingTransition(R.anim.main_exit_amin, R.anim.down_exit_anim);
                }

            }
        });


    }



    @Override
    public void finidDrag() {
        //当用户停止拖拽的时候，就会触发此事件
        //停止拖拽的时候，要保存用户的数据
        //因为数据保存在数据库中，因此我们需要读取数据库中的数据 functions，然后修改 index
        for(int i = 0;i < functionTables.size();i++){
            FunctionTable table = functionTables.get(i);

            table.setFuncIndex(i);

        }
        //保存到数据库中
        new Thread(new Runnable() {
            @Override
            public void run() {
                BDApplication.getFunctionTableDao().updateInTx(functionTables);
            }
        }).start();
    }

}
