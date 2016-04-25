package bas.com.yamob.ui.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectImageView extends ImageView {
    private double aspect = 1d;

    public AspectImageView(Context context) {
        super(context);
    }

    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHeightAspect(double heightAspect) {
        this.aspect = heightAspect;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * aspect);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (height > getMaxHeight()) height = getMaxHeight();
        }
        setMeasuredDimension(width, height);
    }
}