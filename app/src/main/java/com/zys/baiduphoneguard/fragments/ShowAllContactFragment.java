package com.zys.baiduphoneguard.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.AddContactsActivity;
import com.zys.baiduphoneguard.activity.OnekeyForHelpActivity;
import com.zys.baiduphoneguard.adapter.ShowContactsAdapter;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.fragments.base.BaseFragment;
import com.zys.baiduphoneguard.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;


public class ShowAllContactFragment extends BaseFragment {


    @BindView(R.id.rv_contacts)
    RecyclerView rv_contacts;

    private List<ContactInfo> contactInfos;

    private ShowContactsAdapter adapter;

    private List<Integer> checkedItems;

    private TextView tvCount;

    @Override
    protected int getContentView() {
        return R.layout.fragment_show_all_contact;
    }

    @Override
    protected void initViewsAndData(View view) {
        checkedItems = OnekeyForHelpActivity.getCheckedItems();
        contactInfos = BDApplication.getContactInfos();

        adapter = new ShowContactsAdapter(contactInfos);

        rv_contacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_contacts.setAdapter(adapter);
        //选择联系人 显示
        if(getActivity() instanceof AddContactsActivity){
            AddContactsActivity addContactsActivity = (AddContactsActivity)getActivity();
            tvCount = (TextView) addContactsActivity.findViewById(R.id.tv_count);

        }

        //设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {

                tvCount.setText("(" + checkedItems.size() + ")");

            }
        });
    }
//此fragment从不可见到可见会调用哪个方法，然后我们就可以在可见的生命周期中，将用户选中的那个条目给勾上


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden){
            //当显示联系人的界面可见时，选中用户点击的联系人
            //因为当用户点击搜索结果时，我们已经将用户单击的联系人保存到checkedItems
            if(checkedItems.size() == 0){
                Toast.makeText(getActivity(), "请选择联系人", Toast.LENGTH_SHORT).show();
                return;
            }


            int checkPosition = checkedItems.get(checkedItems.size() - 1);
            rv_contacts.smoothScrollToPosition(checkPosition);

            tvCount.setText("(" + checkedItems.size() + ")");

            //显示联系人的时候，其实什么事情都没有做    刷新当前界面
            adapter.notifyDataSetChanged();
        }

    }
}

