package pokercc.android.timeline;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView 时间轴的装饰器
 */
public class TimeLineDecorator extends RecyclerView.ItemDecoration {
    /**
     * 小圆头的drawable
     */
    private final Drawable headDrawable;
    /**
     * 下面的线的drawable
     */
    private final Drawable lineDrawable;

    private final Rect padding;

    private final int width;
    /**
     * 小圆头和下面的线的间距
     */
    private final int centerMargin;
    private final Rect mBounds = new Rect();

    /**
     * @param headDrawable 小圆头的drawable
     * @param lineDrawable 下面的线的drawable
     * @param padding      padding
     * @param centerMargin 小圆头和下面的线的间距
     */
    public TimeLineDecorator(@NonNull Drawable headDrawable, @NonNull Drawable lineDrawable, @Nullable Rect padding, @Px int centerMargin) {
        this.headDrawable = headDrawable;
        this.lineDrawable = lineDrawable;
        this.centerMargin = centerMargin;

        if (padding != null) {
            this.padding = padding;
        } else {
            this.padding = new Rect();
        }
        width = Math.max(headDrawable.getIntrinsicWidth(), lineDrawable.getIntrinsicWidth()) + this.padding.left + this.padding.right;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            final int left;
            final int right;
            //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
            if (parent.getClipToPadding()) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                c.clipRect(left, parent.getPaddingTop(), right,
                        parent.getHeight() - parent.getPaddingBottom());
            } else {
                left = 0;
                right = parent.getWidth();
            }
            final int saveCount = c.save();
            int childCount = layoutManager.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = layoutManager.getChildAt(i);

                if (child != null) {
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int top = mBounds.top + Math.round(child.getTranslationY());
                    final int headWidth = this.headDrawable.getIntrinsicWidth();
                    int headLeft = (width - headWidth >> 1) + left;
                    this.headDrawable.setBounds(headLeft, top + this.padding.top, headLeft + headWidth, top + this.padding.top + this.headDrawable.getIntrinsicHeight());
                    this.headDrawable.draw(c);
                    final int lineWidth = this.lineDrawable.getIntrinsicWidth();
                    int lineLeft = (width - lineWidth >> 1) + left;
                    this.lineDrawable.setBounds(lineLeft, this.headDrawable.getBounds().bottom + centerMargin, lineLeft + lineWidth, top + mBounds.height() - this.padding.bottom);
                    this.lineDrawable.draw(c);
                    c.restoreToCount(saveCount);
                }
            }
            c.restoreToCount(saveCount);

        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = width;
    }
}
