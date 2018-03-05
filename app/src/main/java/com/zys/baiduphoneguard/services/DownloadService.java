package com.zys.baiduphoneguard.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.UpdateActivity;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/13.
 */

public class DownloadService extends Service {
    private int id =0;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;
    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String apkUrl = intent.getStringExtra("downloadUrl");
            File downloadPath = BaiduUtils.getPath();
            createNotification();
            downloadAndInstall(apkUrl,downloadPath);
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void createNotification(){


        mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("正在更新...")
                .setContentText("下载进度" + "0%")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setAutoCancel(false);
        mNotifyManager.notify(id, mBuilder.build());
    }
    public void downloadAndInstall(String url,File path) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(path.getAbsolutePath(), "baidu.apk") {
                    @Override  //下载出错
                    public void onError(Call call, Exception e, int id) {
                        mBuilder.setContentTitle("下载失败")
                                .setContentText("请重新下载");
                        mNotifyManager.notify(id,mBuilder.build());
                    }

                    @Override  //下载完成
                    public void onResponse(File response, int id) {
                        mBuilder.setOngoing(false)
                                .setAutoCancel(true);
                        mNotifyManager.notify(id,mBuilder.build());
                        Intent intent = getInstallIntent(DownloadService.this,response);
                        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(DownloadService.this);
                        taskStackBuilder.addParentStack(UpdateActivity.class);
                        taskStackBuilder.addNextIntent(intent);
                        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
                        mBuilder.setContentIntent(pendingIntent);
                        mBuilder.setContentTitle("下载完成")
                                .setContentText("点击安装");
                        mBuilder.setProgress(0,0,false);
                        mNotifyManager.notify(id,mBuilder.build());

                    }

                    @Override  //正在下载
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);

                        mBuilder.setProgress(100, (int) (progress * 100), false);
                        mBuilder.setContentText((int) (progress * 100) + "%");
                        mNotifyManager.notify(id, mBuilder.build());
                    }
                });
    }


    public Intent getInstallIntent(Context context, File file){
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }
}
