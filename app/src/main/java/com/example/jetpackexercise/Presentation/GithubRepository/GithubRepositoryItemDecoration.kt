package com.example.jetpackexercise.Presentation.GithubRepository

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/* 리싸이클러뷰 여백 설정 */

class GithubRepositoryItemDecoration(private val top: Int, private val bottom: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = top
        outRect.bottom = bottom
    }
}

