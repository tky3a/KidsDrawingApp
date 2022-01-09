package ja.tky.kidsdrawingapp

import android.content.Context
import android.graphics.* // .* することでgraphicsライブラリ全てがimportされる
import android.util.AttributeSet
import android.view.View

// ①
// Viewを継承したDrawingViewクラス
// viewはcontextとattrを設定する必要がある
class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // ② 必要な変数を定義
    // パスを変数定義(CustomPathクラスが代入される)
    private var mDrawPath: CustomPath? = null // 描いたパス
    private var mCanvasBitmap: Bitmap? = null // ビットマップ(キャンバスの座標？)
    private var mDrawPaint: Paint? = null // 描くペイントオブジェクト(colorとかブラシサイズなど、、、各種情報)
    private var mCanvasPaint: Paint? = null // キャンバスの絵?
    private var mBrushSize: Float = 0.toFloat() // ブラシのサイズ
    private var color = Color.BLACK // カラー
    private var canvas: Canvas? = null //　キャンバス

    // ④
    // 最初に呼び出したい処理
    init {
        setUpDrawing()
    }

    // 初期値の設定関数
    private fun setUpDrawing() {
        mDrawPath = CustomPath(color, mBrushSize)
        // ここでペイントオブジェクトを代入
        mDrawPaint = Paint()
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20.toFloat()
    }

    // ③
    // DrawingView内でのみ使用できるCustomPathクラス
    // graphicsライブラリのPathをimport
    // CustomPathクラスは色と、ブラシの太さを持つ(colorとbrushThickness)
    internal inner class CustomPath(var color: Int, brushThickness: Float) : Path() {

    }

}
