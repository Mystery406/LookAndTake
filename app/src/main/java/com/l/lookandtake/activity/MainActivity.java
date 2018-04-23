package com.l.lookandtake.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.l.lookandtake.R;
import com.l.lookandtake.adapter.PhotoAdapter;
import com.l.lookandtake.api.ApiManager;
import com.l.lookandtake.callback.PhotoDiffCallback;
import com.l.lookandtake.entity.PhotoInfo;
import com.l.lookandtake.util.BarUtils;
import com.l.lookandtake.util.DownloadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private static final String TAG = "RxJava";
    private int page = 1;
    private int perPage = 15;
    private boolean needCleanList;

    private List<PhotoInfo> photoInfoList = new ArrayList<>();
    private PhotoAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private LinearLayoutManager layoutManager;
    private DiffUtil.DiffResult diffResult;
    private int currentRetryCount = 0;
    private int maxRetryCount = 3;
    private int waitRetryTime = 5;
    private boolean isLoadingMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        setupRecyclerView();
        initSplashData();
    }

    private void initView() {
        //设置状态栏颜色
        BarUtils.setStatusBarColor(this, Color.BLACK, 0);
        //设置Toolbar
        setSupportActionBar(toolbar);
        //设置侧滑toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //设置侧滑菜单
        navigationView.setNavigationItemSelectedListener(this);
        //开启刷新栏
        refreshLayout.setRefreshing(true);
        //设置刷新栏下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh: 321");
                page = 1;
                needCleanList = true;
                initSplashData();
            }
        });

    }

    private void setupRecyclerView() {
        rvPhotos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvPhotos.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(this);
        rvPhotos.setAdapter(adapter);
        //点击照片事件
        adapter.setOnPhotoClickListener(new PhotoAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(View view, int position) {
                //共享元素动画效果
                Intent intent = new Intent(MainActivity.this, PhotoDetailActivity.class);
                intent.putExtra("photoId", photoInfoList.get(position).getId());
                intent.putExtra("photoUrl", photoInfoList.get(position).getUrls().getSmall());
                intent.putExtra("downloadLink", photoInfoList.get(position).getLinks().getDownload());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MainActivity.this, view, getString(R.string.transition_photo));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        //点击下载事件
        adapter.setOnDownloadClickListener(new PhotoAdapter.OnDownloadClickListener() {
            @Override
            public void onDownloadClick(int position) {
                DownloadUtils.showDownloadDialog(MainActivity.this,
                        photoInfoList.get(position).getLinks().getDownload(), compositeDisposable);
            }
        });
        rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == photoInfoList.size() && !refreshLayout.isRefreshing()) {
                    //滑动到footerView时加载更多
                    if (!isLoadingMore && page != 1) {
                        Log.e(TAG, "onScrollStateChanged: 123");
                        initSplashData();
                    }
                }
            }
        });
    }

    private void initSplashData() {
        isLoadingMore = true;
        //获取Splash接口数据
        ApiManager.getInstance().getUnsplashApi()
                .getUnsplashData(page, perPage)
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) {
                                Log.e(TAG, "发生异常 = " + throwable.toString());
                                if (throwable instanceof IOException) {
                                    if (currentRetryCount < maxRetryCount) {
                                        // 记录重试次数
                                        currentRetryCount++;
                                        Log.e(TAG, "重试次数 = " + currentRetryCount);
                                        return Observable.just(1).delay(waitRetryTime, TimeUnit.SECONDS);
                                    } else {
                                        // 若重试次数已 > 设置重试次数，则不重试
                                        isLoadingMore = false;
                                        currentRetryCount = 0;
                                        return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));
                                    }
                                }
                                // 若发生的异常不属于I/O异常，则不重试
                                else {
                                    isLoadingMore = false;
                                    return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                                }
                            }
                        });
                    }
                })
                .doOnNext(new Consumer<List<PhotoInfo>>() {
                    @Override
                    public void accept(List<PhotoInfo> photoInfos) {
                        if (needCleanList) {
                            photoInfoList.clear();
                        }
                        photoInfoList.addAll(photoInfos);
                        PhotoDiffCallback callback = new PhotoDiffCallback(adapter.getPhotoInfoList(), photoInfoList);
                        diffResult = DiffUtil.calculateDiff(callback);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PhotoInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (compositeDisposable == null) {
                            compositeDisposable = new CompositeDisposable();
                        }
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<PhotoInfo> photoInfos) {
                        diffResult.dispatchUpdatesTo(adapter);
                        adapter.setPhotoInfoList(photoInfoList);
                        rvPhotos.scrollToPosition(adapter.getItemCount() - perPage - 1);
                        page++;
                        needCleanList = false;
                        refreshLayout.setRefreshing(false);
                        isLoadingMore = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
