package com.l.lookandtake.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.l.lookandtake.R;
import com.l.lookandtake.api.ApiManager;
import com.l.lookandtake.entity.PhotoDetail;
import com.l.lookandtake.util.FileUtils;
import com.l.lookandtake.widget.ParallaxScrollView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by L on 2018/4/13.
 * Description:
 */
public class PhotoDetailActivity extends BaseActivity {
    @BindView(R.id.scroll_parallax)
    ParallaxScrollView parallaxScrollView;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.avatar)
    CircleImageView civAvatar;
    @BindView(R.id.nickname)
    TextView tvNickname;
    @BindView(R.id.photoTime)
    TextView tvPhotoTime;
    @BindView(R.id.btn_download)
    LinearLayout llDownload;
    @BindView(R.id.btn_share)
    LinearLayout llShare;
    @BindView(R.id.btn_wallpaper)
    LinearLayout llWallpaper;
    @BindView(R.id.detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.detail_size)
    TextView tvDetailSize;
    @BindView(R.id.detail_exposure_time)
    TextView tvDetailExposureTime;
    @BindView(R.id.detail_color)
    TextView tvDetailColor;
    @BindView(R.id.view_color)
    View vColor;
    @BindView(R.id.detail_aperture)
    TextView tvDetailAperture;
    @BindView(R.id.detail_location)
    TextView tvDetailLocation;
    @BindView(R.id.detail_focal_length)
    TextView tvDetailFocalLength;
    @BindView(R.id.detail_camera)
    TextView tvDetailCamera;
    @BindView(R.id.detail_iso)
    TextView tvDetailIso;
    @BindView(R.id.statistics_title)
    TextView tvStatisticsTitle;
    @BindView(R.id.detail_likes)
    TextView tvDetailLikes;
    @BindView(R.id.detail_likes_text)
    TextView tvDetailLikesText;
    @BindView(R.id.detail_views)
    TextView tvDetailViews;
    @BindView(R.id.detail_views_text)
    TextView tvDetailViewsText;
    @BindView(R.id.detail_download)
    TextView tvDetailDownload;
    @BindView(R.id.detail_download_text)
    TextView tvDetailDownloadText;
    private String photoId;
    private String photoUrl;
    private CompositeDisposable compositeDisposable;
    private WallpaperManager wallpaperManager;
    private PhotoDetail photoDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        Intent intent = getIntent();
        photoId = intent.getStringExtra("photoId");
        photoUrl = intent.getStringExtra("photoUrl");
        //加载图片
        Glide.with(this).load(photoUrl).into(ivPhoto);
        //设置滚动图片视差效果
        parallaxScrollView.setOnScrollChangedListener(new ParallaxScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ParallaxScrollView parallaxScrollView, int l, int t, int oldl, int oldt) {
                ivPhoto.scrollTo(l, -t / 3);
            }
        });

    }

    private void initData() {
        //获取照片具体信息
        Disposable d = ApiManager.getInstance().getUnsplashApi()
                .getPhotoDetial(photoId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PhotoDetail>() {
                    @Override
                    public void accept(PhotoDetail p) {
                        photoDetail = p;
                        loadDetail();
                    }
                });
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    private void loadDetail() {
        //头像
        Glide.with(this).load(photoDetail.getUser().getProfile_image().getLarge()).into(civAvatar);
        //作者信息
        tvNickname.setText(String.format("来自%s", photoDetail.getUser().getName()));
        //拍摄日期
        tvPhotoTime.setText(String.format("拍摄于%s", photoDetail.getCreated_at()
                .substring(0, photoDetail.getCreated_at().indexOf("T"))));
        //照片尺寸
        tvDetailSize.setText(String.format("%s×%s", photoDetail.getWidth(), photoDetail.getHeight()));
        //照片颜色
        tvDetailColor.setText(photoDetail.getColor());
        vColor.setBackgroundColor(Color.parseColor(photoDetail.getColor()));
        //拍摄位置
        tvDetailLocation.setText(NotNull(photoDetail.getLocation().getTitle()));
        //设备
        tvDetailCamera.setText(NotNull(photoDetail.getExif().getModel()));
        //曝光时间
        tvDetailExposureTime.setText(NotNull(photoDetail.getExif().getExposure_time()));
        //光圈
        tvDetailAperture.setText(NotNull(photoDetail.getExif().getAperture()));
        //焦距
        tvDetailFocalLength.setText(NotNull(photoDetail.getExif().getFocal_length()));
        //感光度
        tvDetailIso.setText(NotNull(String.valueOf(photoDetail.getExif().getIso())));
        //喜欢数
        tvDetailLikes.setText(String.valueOf(photoDetail.getLikes()));
        //浏览数
        tvDetailViews.setText(String.valueOf(photoDetail.getViews()));
        //下载数
        tvDetailDownload.setText(String.valueOf(photoDetail.getDownloads()));
    }

    private String NotNull(String s) {
        return s == null ? "Unknown" : s;
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.btn_download, R.id.btn_share, R.id.btn_wallpaper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_download:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    downloadPhoto();
                                } else {
                                    Toast.makeText(PhotoDetailActivity.this, "请授予必要权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.btn_share:
                if (photoDetail != null) {
                    sharePhoto();
                }
                break;
            case R.id.btn_wallpaper:
                setWallpaper();
                break;
        }
    }

    private void downloadPhoto() {
        Disposable d = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File file = Glide.with(PhotoDetailActivity.this)
                        .downloadOnly()
                        .load(photoUrl)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if (file == null) {
                    emitter.onError(new Exception("下载出错！"));
                }
                emitter.onNext(file);
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        savePhoto(file);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("saveImg", "throwable:" + throwable.getMessage());
                        Toast.makeText(PhotoDetailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    private void savePhoto(File file) throws Exception {
        String fileName = FileUtils.getFileMD5ToString(file) + ".png";
        File dir = new File(Environment.getExternalStorageDirectory(), "LookAndTake");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File finalFile = new File(dir, fileName);
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(finalFile);
        byte[] buffer = new byte[2048];
        int length = 0;
        while ((length = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, length);
            fos.flush();
        }
        fis.close();
        fos.close();
        //通知图库更新
        Uri uri = Uri.fromFile(finalFile);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(intent);
        String msg = String.format("图片已保存至 %s 文件夹", finalFile.getAbsolutePath());
        Toast.makeText(PhotoDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void sharePhoto() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String html = photoDetail.getUser().getLinks().getHtml();
        String shareLink = html.contains("https") ? html : html.replace("http", "https");
        intent.putExtra(Intent.EXTRA_TEXT,
                "分享自" + getString(R.string.app_name) + "\n拍摄自" + photoDetail.getUser().getName() + "\n于 " + shareLink);
        startActivity(Intent.createChooser(intent, "分享"));
    }

    private void setWallpaper() {
        wallpaperManager = WallpaperManager.getInstance(this);
        Glide.with(this).load(photoUrl).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(PhotoDetailActivity.this, "设置墙纸成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(PhotoDetailActivity.this, "设置壁纸失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

}
