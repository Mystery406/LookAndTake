package com.l.lookandtake.callback;

import android.support.v7.util.DiffUtil;

import com.l.lookandtake.entity.PhotoInfo;

import java.util.List;

/**
 * Created by L on 2018/4/13.
 * Description:
 */
public class PhotoDiffCallback extends DiffUtil.Callback {
    private List<PhotoInfo> oldList, newList;

    public PhotoDiffCallback(List<PhotoInfo> oldList, List<PhotoInfo> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
