package ru.samsung.itshool.memandos.ui


import android.R.attr.radius
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ru.samsung.itshool.memandos.R


class  InfoUserView  @JvmOverloads constructor(context: Context?, attrs: AttributeSet?)
    : ImageView(context, attrs) {

    private val dp : Float
    private var marginTop : Int = 0
    private var marginLeft : Int = 0
    private lateinit var toolbar : Toolbar



    private var bitmap : Bitmap? = null
    private val reactView : Rect
    private var sizeCyrcle : Size? = null

    private val paintCyrcle : Paint = Paint()

    private val userPhoto : String

    init {
        val attributes =
            context!!.obtainStyledAttributes(attrs, R.styleable.InfoUserViewAttr )
        dp = getResources().getDisplayMetrics().density
        //attributes.getString(R.styleable.InfoUserViewAttr_iuv_user_photo_url) ?:
        userPhoto = "https://i.ibb.co/w06Zg8H/s1200-1.jpg"

        Glide.with(this)
            .asBitmap()
            .load(userPhoto)
            .into(object : SimpleTarget<Bitmap>(200, 200) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    bitmap = resource
                    invalidate()
                }
            })
        reactView = Rect()
        attributes.recycle()
    }




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
       /* val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        marginLeft = width / 2
        val height: Int = size.y
        Log.d(TAG, "dp : $dp")
        Log.d(TAG, "width : $width")
        Log.d(TAG, "widthMeasureSpec : $widthMeasureSpec")
        setMeasuredDimension(width / 5, width / 5)*/
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        Log.d(TAG, "width : $w, h : $h")

        with(reactView) {
            left = 0
            top = 0
            right = w - (10 * dp).toInt()
            bottom = h - (10 * dp).toInt()
        }

        initSetup()

    }


    fun initSetup () {
        with(paintCyrcle) {
            strokeWidth = 1 * dp
            color = resources.getColor(R.color.colorPrimary)
            style = Paint.Style.STROKE
        }


    }

    override fun onDraw(canvas: Canvas) {

        bitmap?.let{

            canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), width.toFloat() / 2, paintCyrcle)

            val radius = ( width / 2 ) - 10 * dp /  2
            val clipPath = Path()
            val rect = RectF(20f, 20f, reactView.right.toFloat() , reactView.bottom.toFloat() )
            clipPath.addRoundRect(rect, radius.toFloat(), radius.toFloat(), Path.Direction.CW)
            canvas.clipPath(clipPath)
            canvas.drawBitmap(it, reactView, reactView, null)
            super.onDraw(canvas)
        }
    }


    companion object {
        private val TAG = "InfoUserView"
    }
}