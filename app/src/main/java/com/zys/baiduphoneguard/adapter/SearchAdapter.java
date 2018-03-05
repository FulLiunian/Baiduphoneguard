package com.zys.baiduphoneguard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/13.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private List<ContactInfo> contactInfoList;

    //添加条目点击事件
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchAdapter(List<ContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }

    public void addData(List<ContactInfo> contactInfos){
        this.contactInfoList = contactInfos;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ContactInfo contactInfo = contactInfoList.get(position);

        holder.tvName.setText(contactInfo.getName());
        holder.tvPhone.setText(contactInfo.getPhone());
        holder.tvAddress.setText(contactInfo.getAttribute());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(holder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactInfoList == null ? 0 : contactInfoList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

