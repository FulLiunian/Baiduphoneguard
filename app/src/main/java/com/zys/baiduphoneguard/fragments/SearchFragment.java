package com.zys.baiduphoneguard.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.AddContactsActivity;
import com.zys.baiduphoneguard.activity.OnekeyForHelpActivity;
import com.zys.baiduphoneguard.adapter.SearchAdapter;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.fragments.base.BaseFragment;
import com.zys.baiduphoneguard.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SearchFragment extends BaseFragment {

    @BindView(R.id.rv_search)
    RecyclerView rv_search;

    //搜索结果：从原来的数据集合中找到符合条件的联系人信息
    //搜索结果,我需要找到符合条件的搜索结果，那么我就需要知道用户输入了什么东西
    private List<ContactInfo> contactInfos;

    private SearchAdapter searchAdapter;



    @Override
    protected int getContentView() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViewsAndData(View view) {
        contactInfos = new ArrayList<>();

        searchAdapter = new SearchAdapter(contactInfos);

        rv_search.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_search.setAdapter(searchAdapter);

        //添加点击事件   搜索结果 点击条目
        searchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                //当用户点击搜索结果时，跳到ShowAllContactFragment,当勾选上点击的联系人
                //移开当前的fragment,选中showAllContactFragment中的联系人

                //勾上用户点击的联系人
                //首先判断是否为手机号码，如果是手机号码才让勾选，否则提示用户请选择手机号码
                int clickPosition = viewHolder.getLayoutPosition();

                //java.lang.IndexOutOfBoundsException: Invalid index 3, size is 0
                //数组越界异常，当前的索引是3，而集合在大小是0
                ContactInfo clickContact = contactInfos.get(clickPosition);

                if(clickContact.getPhone().matches("^1[34578]\\d{9}$")){
                    //说明是手机号码,将手机号码保存到checkItems(保存是所有选中条目在查询结果的集合中位置)
                    //在保存之前，首先要判断用户点击的联系人是否已经选中过，如果选中过，则不需要做任何操作
                    //如果没有选中过，则将点击的联系人保存到checkedItems
                    List<ContactInfo> queryResult = BDApplication.getContactInfos();

                    int position = queryResult.indexOf(clickContact);

                    List<Integer> checkedItems = OnekeyForHelpActivity.getCheckedItems();

                    if(!checkedItems.contains(Integer.valueOf(position))){
                        checkedItems.add(position); //在集合中，通过add添加的数据会在集合的末尾
                    }

                }else{
                    Toast.makeText(getActivity(), "请选择正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                //用户点击条目后，应该清空搜索框
                if(getActivity() instanceof AddContactsActivity){

                    AddContactsActivity addContactsActivity = (AddContactsActivity) getActivity();
                    EditText etSearch = (EditText) addContactsActivity.findViewById(R.id.et_search);
                    etSearch.setText(null);
                }

                if(getFragmentManager().getBackStackEntryCount() > 1){
                    getFragmentManager().popBackStack();
                }


            }
        });
    }

    //通过适配器更新fragment
    //因为我们的fragment当中只有一个recyclerview
    public void updateFragment(List<ContactInfo> contactInfos){
        if(searchAdapter != null){
            //this.contactInfos.clear();

            this.contactInfos = contactInfos;

            searchAdapter.addData(contactInfos);
        }
    }

}
