// このファイルが画面のViewになる
package ja.tky.kidsdrawingapp

import android.content.Context
import android.graphics.* // .* することでgraphicsライブラリ全てがimportされる
import android.util.AttributeSet
import android.view.MotionEvent
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
    private val mPaths = ArrayList<CustomPath>() // 複数のpathを格納する変数

    // ④
    // 最初に呼び出したい処理
    init {
        setUpDrawing()
    }

    // 初期値の設定関数
    private fun setUpDrawing() {
        // インナークラス(CustomPath)に引数を渡してインスタンスを作成する
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

    // ⑤　https://developer.android.com/reference/android/view/View#size,-padding-and-margins
    // onSizeChangedは元々Viewに用意されているメソッド
    // Viewのサイズが変更されたときに呼び出される
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // ビットマップ作成
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        // キャンバスにビットマップを渡す。(nullを渡せないので「!!」を付けてnullの場合に例外となるように引数に渡す)
        canvas = Canvas(mCanvasBitmap!!)
    }

    // ⑥　https://developer.android.com/reference/android/view/View#size,-padding-and-margins
    // onDrawは元々Viewに用意されているメソッド
    // Viewに描きたい内容をonDrawで記述する
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // drawBitmapを使って描画する
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        // いままで描いたPathを描画する
        // mPathsをループしてcanvasで描画することで画面に描いたpathが残る
        for (path in mPaths) {
            // いままで描いた各pathのサイズを取得
            mDrawPaint!!.strokeWidth = path.brushThickness
            // いままで描いた各pathの色を取得
            mDrawPaint!!.color = path.color
            // 画面に描画
            canvas.drawPath(path, mDrawPaint!!)
        }

        // mDrawPathが存在しない場合
        if (!mDrawPath!!.isEmpty) {
            // ペイントの幅を取得
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            // ペイントの色を取得
            mDrawPaint!!.color = mDrawPath!!.color
            // パスを渡して描画する
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    // ⑦
    // onTouchEventは元々Viewに用意されているメソッド
    // 実際に描画するイベント
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 座標を取得
        val touchX = event.x
        val touchY = event.y

        // ⑧ 各アクションが発生した時のアクションを設定
        when (event.action) {
            // 画面タップ時
            MotionEvent.ACTION_DOWN -> {
                println("タップした")
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                // パスのリセット
                mDrawPath!!.reset()
                // moveToで引数のxとy座標に目掛けて描き始めますよ。という命令を送る
                mDrawPath!!.moveTo(touchX!!, touchY!!)
            }

            // 動かした時
            MotionEvent.ACTION_MOVE -> {
                // lineToで引数のxとyに線を引きますよ。という命令を送る
                mDrawPath!!.lineTo(touchX!!, touchY!!)
            }

            // タッチ状態から手を離した時
            MotionEvent.ACTION_UP -> {
                println("dd $mDrawPath ${mDrawPath?.brushThickness}")
                // 描いたパスを配列に保存
                mPaths.add(mDrawPath!!)
                // パスを初期化
                mDrawPath = CustomPath(color, mBrushSize)
            }

            else -> return false
        }

        // invalidate()を呼び出すことでonDrawが再度呼び出される
        // onDrawが呼ばれることで再描画される
        invalidate()

        // trueを返すことで何回も処理を繰り返すことができる
        return true
    }

    // ③
    // Pathを継承したクラスで動的に変わる色とサイズを持つpathを返す
    // DrawingView内でのみ使用できるCustomPathクラス
    // graphicsライブラリのPathをimport
    // CustomPathクラスは色と、ブラシの太さを持つ(colorとbrushThickness)
    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }

}
