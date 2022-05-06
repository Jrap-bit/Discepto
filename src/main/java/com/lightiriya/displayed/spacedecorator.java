package com.lightiriya.displayed;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class spacedecorator extends RecyclerView.ItemDecoration{
    private final int verticalSpaceheight;


    public spacedecorator(int verticalSpaceheight) {
        this.verticalSpaceheight = verticalSpaceheight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom=verticalSpaceheight;
    }
}


