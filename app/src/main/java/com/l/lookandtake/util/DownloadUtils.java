package com.l.lookandtake.util;

import android.content.Context;
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

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by L on 2018/4/17.
 * Description:
 */
public final class DownloadUtils {
    public static File getDownloadDir() {
        File file = new File(Environment.getExternalStorageDirectory().getPath()
                + File.separator + "LookAndTake" + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String getFilename(String url) {
        return Util.md5(url) + ".png";
    }

    public static void showDownloadDialog(final Context context, String downloadLink) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_download, null, false);
        final ProgressBar pb = view.findViewById(R.id.progress_bar);
        final TextView tvTitle = view.findViewById(R.id.tv_progress_title);
        final TextView tvSize = view.findViewById(R.id.tv_image_size);
        final AlertDialog dialog = new AlertDialog.Builder(context)
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
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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
