package com.zys.baiduphoneguard.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.AddContactsActivity;
import com.zys.baiduphoneguard.activity.OnekeyForHelpActivity;
import com.zys.baiduphoneguard.adapter.UrgentAdapter;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.fragments.base.BaseFragment;
import com.zys.baiduphoneguard.listener.OnCancelClickListener;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SecondHelpFragment extends BaseFragment {


    Unbinder unbinder;
    //使用recyclerview显示用户选择的联系人

    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.cb_agree)
    CheckBox cbAgree;

    private List<ContactInfo> contactInfos;

    private UrgentAdapter adapter;

    private List<Integer> checkedItems;

    private List<ContactInfo> originContacts;

    @Override
    protected int getContentView() {
        return R.layout.fragment_second_help;
    }

    @OnClick(R.id.iv_addContact)
    public void addContacts(View view){
        startActivity(new Intent(getActivity(), AddContactsActivity.class));
        getActivity().overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
    }


    @Override
    protected void initViewsAndData(View view) {
        contactInfos = new ArrayList<>();

        adapter = new UrgentAdapter(contactInfos);

        rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvContacts.setAdapter(adapter);

        //设置取消按钮的点击事件
        adapter.setOnCancelClickListener(new OnCancelClickListener() {
            @Override
            public void onCancelClick(RecyclerView.ViewHolder viewHolder) {
                //取消按钮点击，删除当前点击的条目
                //从用户选中的条目中(checkedItems)，移除点击的条目


                //发现没有及时的刷新，因为适配器中保存的是list<ContactInfo>，我们删除的是用户点击位置的contactInfo
                int clickPosition = viewHolder.getLayoutPosition();
                ContactInfo contactInfo = contactInfos.get(clickPosition);

                adapter.removeContact(contactInfo);

                checkedItems.remove(clickPosition);


                if(checkedItems.size() < 3){
                    llAdd.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        //我们在fragment可见的时候（onResume）方法中添加用户选中的条目
        //判断里面有没有，如果有的话，不需要添加，如果没有的话，我们就添加   ?  可行
        //在添加之前首先清空 然后再去添加
        adapter.clearContact();

        checkedItems = OnekeyForHelpActivity.getCheckedItems();

        originContacts = BDApplication.getContactInfos();


        //在添加之前判断是否已经达到三个联系人
        if(checkedItems.size() == 3){
            //invisible  gone 的区别：invisible 不可见 隐藏   gone 不可见  滚蛋
            llAdd.setVisibility(View.GONE);
        }



        if(checkedItems.size() > 0){
            //开始遍历，
            for(int i = 0;i < checkedItems.size();i++){
                ContactInfo contactInfo = originContacts.get(checkedItems.get(i));

                adapter.addContact(contactInfo);
            }
        }

    }


    //点击完成按钮
    @OnClick(R.id.btn_complete)
    public void complete(View view){

        //1、首先判断用户是否输入或者选择紧急联系人，只有输入或者选择了紧急联系人，才能点击完成按钮
        if(checkedItems.size() == 0){
            Toast.makeText(getActivity(), "请选择紧急联系人", Toast.LENGTH_SHORT).show();
            return;
        }
        //2、将求救的短信内容保存到数据库（因为只有一段话，保存到数据库中有点小题大作）当中；保存共享首选项当中；
        String msg = etMsg.getText().toString();

        if(msg == null || "".equals(msg)){
            Toast.makeText(getActivity(), "请输入求救短信内容", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = BaiduUtils.getSharedPreferences();

        sp.edit().putString("urgentContent",msg).commit();
        //3、将紧急联系人保存至数据库，因为联系人数据我们已经保存到数据库当中，只需要添加一个字段boolean 标记此联系人是否为紧急联系人
        for(int i = 0;i < checkedItems.size();i++){
            int checkedPosition = checkedItems.get(i);

            ContactInfo contactInfo = originContacts.get(checkedPosition);
            contactInfo.setIsUrgent(true);

            BDApplication.getContactInfoDao().insertOrReplace(contactInfo); //update   insertOrReplace的区别？
        }

        //4、当用户选中复选框的时候，才发送短信；
        if(cbAgree.isChecked()){
            //发送短信
            for(int i = 0;i < checkedItems.size();i++){
                ContactInfo contactInfo = originContacts.get(checkedItems.get(i));

                BaiduUtils.sendMessage(contactInfo.getPhone(), msg);
            }
        }else {
            Toast.makeText(getActivity(), "请开启短信通知紧急联系人!!!", Toast.LENGTH_SHORT).show();
        }
        //5、跳转至另一个fragment
        if(getActivity() instanceof OnekeyForHelpActivity){
            OnekeyForHelpActivity activity = (OnekeyForHelpActivity) getActivity();

            activity.replaceFragment();
        }

        //6、已经设置了紧急联系人，如果第二次进来的话，就不需要设置了
        sp.edit().putBoolean("hasSetUrgent", true).commit();

    }

}
