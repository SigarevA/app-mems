package ru.samsung.itshool.memandos.ui


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ru.samsung.itshool.memandos.R


private const val TAG = "InfoUserView"

class InfoUserView @JvmOverloads constructor(context: Context?, attrs: AttributeSet?) :
    ImageView(context, attrs) {

    private val dp: Float
    private lateinit var toolbar: Toolbar
    private var bitmap: Bitmap? = null
    private val reactView: Rect
    private val paintCyrcle: Paint = Paint()
    private var userPhoto: String = ""

    init {

        context!!.obtainStyledAttributes(attrs, R.styleable.InfoUserViewAttr).apply {
            this.getString(R.styleable.InfoUserViewAttr_iuv_user_photo_url)?.let {
                loadImage(it)
                userPhoto = it
            }
        }
            .recycle()

        reactView = Rect()
        dp = getResources().getDisplayMetrics().density

    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(userPhoto)
            .into(object : SimpleTarget<Bitmap>(200, 200) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    bitmap = resource
                    invalidate()
                }
            })
    }

    fun getImgPath(): String {
        return userPhoto
    }

    fun setImgPath(urlPhoto: String) {
        userPhoto = urlPhoto
        loadImage(urlPhoto)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        with(reactView) {
            left = 0
            top = 0
            right = w - (10 * dp).toInt()
            bottom = h - (10 * dp).toInt()
        }
        initSetup()
    }


    fun initSetup() {
        with(paintCyrcle) {
            strokeWidth = 1 * dp
            color = resources.getColor(R.color.colorPrimary)
            style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas) {

        bitmap?.let {

            canvas.drawCircle(
                (width / 2).toFloat(),
                (width / 2).toFloat(),
                width.toFloat() / 2,
                paintCyrcle
            )

            val radius = (width / 2) - 10 * dp / 2
            val clipPath = Path()
            val rect = RectF(20f, 20f, reactView.right.toFloat(), reactView.bottom.toFloat())
            clipPath.addRoundRect(rect, radius.toFloat(), radius.toFloat(), Path.Direction.CW)
            canvas.clipPath(clipPath)
            canvas.drawBitmap(it, reactView, reactView, null)
            super.onDraw(canvas)
        }
    }
}