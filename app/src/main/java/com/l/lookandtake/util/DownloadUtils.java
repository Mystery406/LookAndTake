package com.l.lookandtake.util;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.l.lookandtake.R;
import com.l.lookandtake.constant.MemoryConstants;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.DecimalFormat;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by L on 2018/4/17.
 * Description:
 */
public final class DownloadUtils {
    public static void showDownloadDialog(final Activity activity, final String downloadLink,
                                          CompositeDisposable compositeDisposable) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        Disposable disposable = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            startDownload(activity, downloadLink);
                        } else {
                            Toast.makeText(activity, "请授予必要权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private static void startDownload(final Activity activity, final String downloadLink) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_download, null, false);
        final ProgressBar pb = view.findViewById(R.id.progress_bar);
        final TextView tvTitle = view.findViewById(R.id.tv_progress_title);
        final TextView tvSize = view.findViewById(R.id.tv_image_size);
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setCancelable(false)
                .show();
        File parentFile = DownloadUtils.getDownloadDir();
        DownloadTask task = new DownloadTask.Builder(downloadLink, parentFile)
                .setFilename(DownloadUtils.getFilename(downloadLink))
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .build();
        task.enqueue(new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
                pb.setIndeterminate(true);
                tvTitle.setText("准备中...");
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                tvTitle.setText(String.format("下载中：%s", getProgress(currentOffset, totalLength)));
                tvSize.setText(String.format("大小:%s", getFormatSize(totalLength)));
                pb.setIndeterminate(false);
                pb.setMax((int) totalLength);
                pb.setProgress((int) currentOffset);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
                String msg = realCause == null ? String.format("已下载至%s文件夹", "../LookAndTake") : "出错啦:" + realCause.getMessage();
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private static File getDownloadDir() {
        File file = new File(Environment.getExternalStorageDirectory().getPath()
                + File.separator + "LookAndTake" + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static String getFilename(String url) {
        return Util.md5(url) + ".png";
    }

    private static String getProgress(long currentOffset, long totalLength) {
        double progress = currentOffset / (double) totalLength * 100;
        return Math.round(progress) + "%";
    }

    private static String getFormatSize(long totalLength) {
        double size = ConvertUtils.byte2MemorySize(totalLength, MemoryConstants.MB);
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(size) + "M";
    }
}
