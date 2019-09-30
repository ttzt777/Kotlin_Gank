package com.bear3.gank_kotlin.ui.common.weight

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.Path
import android.util.AttributeSet
import android.widget.ImageView
import com.bear3.gank_kotlin.R
import com.bear3.gank_kotlin.ui.common.weight.RoundImageType.Companion.getEnumViaType
import kotlin.math.min

private const val RATIO_REFERENCE_WIDTH = 0
private const val RATIO_REFERENCE_HEIGHT = 1

/**
 * Description:
 * Author: TT
 * From: 2019/9/2
 * Last Version: 1.0.0
 * Last Change Time: 2019/9/2
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/9/2
 * *-*
 */
class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ImageView(context, attrs, defStyle) {

    // 控件类型 方形/圆角/圆形
    private var type = RoundImageType.Rect
        set(value) {
            field = value
            requestLayout()
        }

    // 圆角大小（4对rx, ry，左上角开始，顺时针方向）
    private var radiusArray: FloatArray = FloatArray(8) { 0f }
        set(value) {
            if (value.size != 8) {
                throw IllegalStateException("Radius Array size must be 8!")
            }
            field = value
            createPath()
            invalidate()
        }

    // 固定宽高比基准
    private var ratioRefer = RATIO_REFERENCE_WIDTH
        set(value) {
            field = value
            requestLayout()
        }

    // 宽高比例
    private var ratio = 0f
        set(value) {
            field = value
            requestLayout()
        }

    // 边框宽度
    private var strokeWidth = 0f
        set(value) {
            field = value
            strokePaint.strokeWidth = value
            invalidate()
        }

    // 边框颜色
    private var strokeColor = 0x00FF_FFFF
        set(value) {
            field = value
            strokePaint.color = strokeColor
            invalidate()
        }

    // 边缘路径
    private val path: Path = Path()
    // 边线画笔
    private val strokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    // clipPath抗锯齿
    private val filter: PaintFlagsDrawFilter =
        PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    init {
        fun initRadiusArray(array: TypedArray) {
            with(array) {
                val radius = getDimension(R.styleable.RoundImageView_radius_size, 0f)
//                val radiusArray = FloatArray(8)
                for (index in radiusArray.indices) {
                    // 先将统一设置的值给上，然后再取每个的值
                    radiusArray[index] = radius

                    val temp = when (index) {
                        0 -> getDimension(R.styleable.RoundImageView_top_left_rx, -1f)
                        1 -> getDimension(R.styleable.RoundImageView_top_left_ry, -1f)
                        2 -> getDimension(R.styleable.RoundImageView_top_right_rx, -1f)
                        3 -> getDimension(R.styleable.RoundImageView_top_right_ry, -1f)
                        4 -> getDimension(R.styleable.RoundImageView_bottom_right_rx, -1f)
                        5 -> getDimension(R.styleable.RoundImageView_bottom_right_ry, -1f)
                        6 -> getDimension(R.styleable.RoundImageView_bottom_left_rx, -1f)
                        7 -> getDimension(R.styleable.RoundImageView_bottom_left_ry, -1f)
                        else -> getDimension(R.styleable.RoundImageView_bottom_left_ry, -1f)
                    }

                    if (temp != -1f) {
                        radiusArray[index] = temp
                    }
                }
//                this@RoundImageView.radiusArray = radiusArray
            }
        }

        attrs?.let {
            val array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)

            with(array) {
                // 类型
                type = getEnumViaType(
                    getInteger(
                        R.styleable.RoundImageView_round_type,
                        0
                    )
                )

                // 固定宽高比
                ratioRefer = getInteger(
                    R.styleable.RoundImageView_ratio_w_h_reference,
                    RATIO_REFERENCE_WIDTH
                )
                ratio = getFloat(R.styleable.RoundImageView_ratio_w_h_size, 0f)

                // 边框
                strokeWidth = getDimension(R.styleable.RoundImageView_stroke_width, 0f)
                strokeColor = getColor(R.styleable.RoundImageView_stroke_color, 0x00FF_FFFF)

                // 圆角
                initRadiusArray(array)

                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (type == RoundImageType.Rect) {
            ratio = 1f
        }

        if (ratio > 0f) {
            // 有固定宽高比
            val measureWidth: Int
            val measureHeight: Int

            if (ratioRefer == RATIO_REFERENCE_WIDTH) {
                // 以宽为基准
                measureWidth = MeasureSpec.getSize(widthMeasureSpec)
                measureHeight = (measureWidth / ratio).toInt()
            } else {
                measureHeight = MeasureSpec.getSize(heightMeasureSpec)
                measureWidth = (measureHeight * ratio).toInt()
            }

            super.onMeasure(
                MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY)
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (type == RoundImageType.Circle) {
            for (index in radiusArray.indices) {
                radiusArray[index] = height / 2f
            }
        }

        createPath()
    }

    override fun onDraw(canvas: Canvas) {
        // 处理圆角
        if (type == RoundImageType.Round || type == RoundImageType.Circle) {
            canvas.drawFilter = filter
            canvas.clipPath(path)
        }

        // 绘制原始图片
        super.onDraw(canvas)

        // 处理边框
        if (strokeWidth > 0f) {
            canvas.drawPath(path, strokePaint)
        }
    }

    private fun createPath() {
        path.reset()

        when (type) {
            RoundImageType.Rect -> {
                path.addRect(
                    paddingStart.toFloat(),
                    paddingTop.toFloat(),
                    (width - paddingEnd).toFloat(),
                    (height - paddingBottom).toFloat(),
                    Path.Direction.CW
                )
            }
            RoundImageType.Round -> {
                for (index in radiusArray.indices) {
                    if (radiusArray[index] < 0) {
                        radiusArray[index] = 0f
                    }
                }
                path.addRoundRect(
                    paddingStart.toFloat(),
                    paddingEnd.toFloat(),
                    (width - paddingEnd).toFloat(),
                    (height - paddingBottom).toFloat(),
                    radiusArray,
                    Path.Direction.CW
                )
            }
            RoundImageType.Circle -> {
                val circleRadius = min(
                    (width - paddingStart - paddingEnd) / 2f,
                    (height - paddingTop - paddingBottom) / 2f
                )
                path.addCircle(width / 2f, height / 2f, circleRadius, Path.Direction.CW)
            }
        }
    }
}

enum class RoundImageType(var type: Int) {
    Rect(0),
    Round(1),
    Circle(2);

    companion object {
        fun getEnumViaType(type: Int): RoundImageType {
            return when (type) {
                0 -> Rect
                1 -> Round
                2 -> Circle
                else -> Rect
            }
        }
    }
}
