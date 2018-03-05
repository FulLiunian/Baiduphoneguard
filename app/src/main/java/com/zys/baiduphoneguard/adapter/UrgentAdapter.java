package com.zys.baiduphoneguard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.listener.OnCancelClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/13.
 */

public class UrgentAdapter extends RecyclerView.Adapter<UrgentAdapter.ViewHolder>{

    private OnCancelClickListener onCancelClickListener;

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    private List<ContactInfo> contactInfos;

    public UrgentAdapter(List<ContactInfo> contactInfos) {
        this.contactInfos = contactInfos;
    }

    //添加联系人，并更新
    public void addContact(ContactInfo contactInfo){
        contactInfos.add(contactInfo);

        notifyDataSetChanged();
    }
    //移除联系人
    public void removeContact(ContactInfo contactInfo){
        int position = contactInfos.indexOf(contactInfo);
        if(position != -1){
            contactInfos.remove(contactInfo);
        }
        notifyDataSetChanged();
    }
    //清空所有联系人

    public void clearContact(){
        contactInfos.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.urgent_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ContactInfo contactInfo = contactInfos.get(position);

        holder.tvName.setText(contactInfo.getName());
        holder.tvPhone.setText(contactInfo.getPhone());
        if(onCancelClickListener != null){
            holder.ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCancelClickListener.onCancelClick(holder);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contactInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_cancel)
        ImageView ivCancel;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

