# 使用RecyclerView.ItemDecorator 实现的时间轴布局

demo图

![](https://github.com/pokercc/TimeLineDecorator/blob/master/timeline.png)


就一个类
```
pokercc.android.timeline.TimeLineDecorator
...
public class TimeLineDecorator extends RecyclerView.ItemDecoration {
    
    /**
     * @param headDrawable 小圆头的drawable
     * @param lineDrawable 下面的线的drawable
     * @param padding      padding
     * @param centerMargin 小圆头和下面的线的间距
     */
    public TimeLineDecorator(@NonNull Drawable headDrawable, @NonNull Drawable lineDrawable, @Nullable Rect padding, @Px int centerMargin) 
}
```

参数解释:

![](https://github.com/pokercc/TimeLineDecorator/blob/master/params.png)

原设计图
https://www.meiyou.com/dashijian