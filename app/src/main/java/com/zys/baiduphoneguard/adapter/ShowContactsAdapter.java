package com.zys.baiduphoneguard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkcool.circletextimageview.CircleTextImageView;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.OnekeyForHelpActivity;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ShowContactsAdapter extends RecyclerView.Adapter<ShowContactsAdapter.ViewHolder>{

    private List<ContactInfo> contactInfos;

    private OnItemClickListener onItemClickListener;

    private int count = 0;


    private List<Integer> checkedItems;

    public ShowContactsAdapter(List<ContactInfo> contactInfos) {
        this.contactInfos = contactInfos;

        checkedItems = OnekeyForHelpActivity.getCheckedItems();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_contacts_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(Integer.valueOf(position));

        final ContactInfo contactInfo = contactInfos.get(position);

        String name = contactInfo.getName();
        if(name.length() == 1){
            holder.rtv_head.setText(name);
        }else if(name.length() == 2 || name.length() == 3 ){
            holder.rtv_head.setText(name.substring(1));
        }else{
            holder.rtv_head.setText(name.substring(2));
        }
        holder.rtv_head.setFillColor(contactInfo.getHeadColor());

        holder.contact_name.setText(contactInfo.getName());
        holder.contact_phone.setText(contactInfo.getPhone());
        holder.tv_address.setText(contactInfo.getAttribute());

        //方法一：将checkbox的选中状态保存到map<Integer,boolean>  http://blog.csdn.net/qq_16265959/article/details/53399466
        //方法二：定义一个boolean类型的数组  http://blog.csdn.net/jiang547860818/article/details/53126990
        //方法三：给条目设置tag标签 http://www.cnblogs.com/CharlesGrant/p/5171133.html
        //方法四：定义一个list集合，用于保存用户点击的位置，如果用户点击了，则将点击位置保存到list集合当中，如果取消点击，则从list集合当中移除



        holder.cb_agree.setChecked(checkedItems.contains(Integer.valueOf(position)));

        //绑定点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //当点击条目的时候，复选框被选中
                // 1、所有的都会被选中   不能是非手机号码
                // 2、一旦选中无法取消  应该是如果是选中，点击取消，如果是不选中的话，点击选中
                // 3、不超过三个？
                if(!contactInfo.getPhone().matches("^1[34578]\\d{9}$")){
                    Toast.makeText(v.getContext(), "请选择正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(checkedItems.size() == 3){

                    //只能移除，不能添加
                    //如果你点的那个条目已经被选中，那我们就移除
                    //如果你点击的那个条目没有被选中，那么我们就不允许选中
                    if(holder.cb_agree.isChecked()){
                        checkedItems.remove(Integer.valueOf(position));
                    }else{
                        Toast.makeText(v.getContext(), "您选择的联系人已经超过上限啦", Toast.LENGTH_SHORT).show();

                        return;
                    }
                }

                holder.cb_agree.setChecked(!holder.cb_agree.isChecked());

                if(holder.cb_agree.isChecked()){
                    checkedItems.add(position);
                }else{
                    checkedItems.remove(Integer.valueOf(position));
                }

                //当你点击条目的时候，那么就会触发条目点击事件
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(holder);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactInfos == null ? 0 : contactInfos.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rtv_head)
        CircleTextImageView rtv_head;
        @BindView(R.id.contact_name)
        TextView contact_name;
        @BindView(R.id.contact_phone)
        TextView contact_phone;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.cb_agree)
        CheckBox cb_agree;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

