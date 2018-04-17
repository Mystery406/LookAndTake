package com.l.lookandtake.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.l.lookandtake.R;
import com.l.lookandtake.entity.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by L on 2018/4/11.
 * Description:
 */
public class PhotoAdapter extends RecyclerView.Adapter {
    private static final int TYPE_LOAD_MORE = 0;
    private static final int TYPE_NORMAL = 1;
    private Context context;
    private List<PhotoInfo> photoInfoList = new ArrayList<>();
    private OnPhotoClickListener onPhotoClickListener;

    public PhotoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE) {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false));
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder myHolder = (ViewHolder) holder;
            PhotoInfo photoInfo = photoInfoList.get(position);
            myHolder.frameBg.setBackgroundColor(Color.parseColor(photoInfo.getColor()));
            Glide.with(context)
                    .load(photoInfo.getUrls().getSmall())
                    .into(myHolder.ivPhoto);
            myHolder.tvUsername.setText(photoInfo.getUser().getName());
            myHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPhotoClickListener.onPhotoClick(v, holder.getAdapterPosition());
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder myHolder = (FooterViewHolder) holder;
            //设置ProgressBar颜色
            myHolder.progressBar.getIndeterminateDrawable().setColorFilter(
                    context.getResources().getColor(R.color.black_alpha_60),
                    PorterDuff.Mode.SRC_IN);
            if (photoInfoList.size() == 0) {
                myHolder.llLoadMore.setVisibility(View.GONE);
            } else {
                myHolder.llLoadMore.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return photoInfoList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == photoInfoList.size()) {
            return TYPE_LOAD_MORE;
        }
        return TYPE_NORMAL;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.iv_download)
        ImageView ivDownload;
        @BindView(R.id.frame_bg)
        FrameLayout frameBg;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressbar)
        ProgressBar progressBar;
        @BindView(R.id.ll_load_more)
        LinearLayout llLoadMore;

        FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(View view, int position);
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void setPhotoInfoList(List<PhotoInfo> photoInfoList) {
        this.photoInfoList = photoInfoList;
    }

    public List<PhotoInfo> getPhotoInfoList() {
        return photoInfoList;
    }
}
