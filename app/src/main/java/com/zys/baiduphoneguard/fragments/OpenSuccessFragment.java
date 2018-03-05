package com.zys.baiduphoneguard.fragments;


import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.dao.ContactInfoDao;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.fragments.base.BaseFragment;

import java.util.List;

import butterknife.BindView;


public class OpenSuccessFragment extends BaseFragment {

    @BindView(R.id.iv_animation)
    ImageView ivAnimation;
    @BindView(R.id.ll_contacts)
    LinearLayout llContacts;

    AnimationDrawable shakeAnimation;

    //显示紧急联系人，因为紧急联系人已经保存到数据库当中，因此我们只需要从数据库中读取
    //读取紧急联系人有一个字段boolean :isurgent  true是紧急联系人  false 非紧急联系人
    private List<ContactInfo> urgentContacts;

    @Override
    protected int getContentView() {
        return R.layout.fragment_open_success;
    }

    @Override
    protected void initViewsAndData(View view) {
        ivAnimation.setImageResource(R.drawable.phone_shake_anim);
        shakeAnimation = (AnimationDrawable) ivAnimation.getDrawable();

        shakeAnimation.start();

        //从数据库中读取紧急联系人
        urgentContacts = BDApplication
                .getContactInfoDao()
                .queryBuilder()
                .where(ContactInfoDao.Properties.IsUrgent.eq(true))
                .build().list();

        // 在实现此界面之后，我们需要结合百度定位实现位置的发送给紧急联系人?
        //百度定位的使用：
        //  1、打开百度地图的api地址：http://lbsyun.baidu.com/ 跳转至百度定位，下载相关的文件：依赖包与案例
        //  2、申请密钥：http://lbsyun.baidu.com/apiconsole/key
        //  3、将下载的依赖库添加至module的libs目录下，然后配置清单文件(AndroidManifest.xml)；
        //  4、配置定位；在服务中进行定位；因为我只需要获取一次位置，用完之后就直接销毁；stopSelf;
        //  5、在mainactivity中启动服务
        //  问题：服务启动成功，但是并没有获取指定的位置，而且位置信息不对？
        //      解决办法：根据官方提供的demo来解决问题
        //  问题：使用逍遥及原生模拟机都无法实现定位功能？
        //      解决办法：直接用真机


        //动态添加至界面上 new TextView（this）
        for(int i = 0;i < urgentContacts.size();i++){
            ContactInfo contactInfo = urgentContacts.get(i);

            TextView tv_contact = new TextView(getActivity());
            tv_contact.setText(contactInfo.getName() + "           " + contactInfo.getPhone());
            llContacts.addView(tv_contact);
        }


    }


}
