package com.mrelshoddev.contactmanager.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrelshoddev.contactmanager.utils.Utils;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int marginPx;
    private Context context;

    public ItemDecoration(Context context, int marginPx) {
        this.context = context;
        this.marginPx = marginPx;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();
        outRect.top = Utils.toPx(context, marginPx);
        outRect.left = Utils.toPx(context, marginPx);
        outRect.right = Utils.toPx(context, marginPx);
        if (position == itemCount - 1) {
            outRect.bottom = Utils.toPx(context, marginPx);
        }
    }
}
