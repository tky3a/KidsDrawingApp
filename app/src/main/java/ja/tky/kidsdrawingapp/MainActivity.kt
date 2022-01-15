package ja.tky.kidsdrawingapp

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // ⑧
    // DrawingViewの変数を定義
    private var drawingView: DrawingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ⑧
        // drawing_viewを取得
        // 大元のViewがktファイルで作成しているため、関数などのアクセスが可能になる
        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())

        // ブラシボタン
        val ibBrash: ImageButton = findViewById(R.id.ibBrush)
        ibBrash.setOnClickListener {
            showBrushSizeChooseDialog()
        }
    }

    /**
     * ブラシダイアログを表示する関数
     * **/
    private fun showBrushSizeChooseDialog() {
        // ダイアログ インスタンス生成
        val brushDialog = Dialog(this)

        // ----- ダイアログ生成 start -----------

        // 表示するレイアウトを設定
        brushDialog.setContentView(R.layout.dialog_brush_size)
        // タイトル設定
        brushDialog.setTitle("ブラシサイズ")
        // 設定したレイアウトからボタン情報を取得
        val smallBtn: ImageButton = brushDialog.findViewById(R.id.ibSmallBrush)
        val mediumBtn: ImageButton = brushDialog.findViewById(R.id.ibMediumBrush)
        val largeBtn: ImageButton = brushDialog.findViewById(R.id.ibLargeBrush)

        // 最小のアイコンタップした時のアクション
        smallBtn.setOnClickListener {
            // drawingView.ktで設定している関数を呼び出す(対象のブラシサイズを設定)
            drawingView?.setSizeForBrush(10.toFloat())
            // ダイアログを閉じる
            brushDialog.dismiss()
        }

        // 真ん中のアイコンタップした時のアクション
        mediumBtn.setOnClickListener {
            // drawingView.ktで設定している関数を呼び出す(対象のブラシサイズを設定)
            drawingView?.setSizeForBrush(20.toFloat())
            // ダイアログを閉じる
            brushDialog.dismiss()
        }

        // 最大のアイコンタップした時のアクション
        largeBtn.setOnClickListener {
            // drawingView.ktで設定している関数を呼び出す(対象のブラシサイズを設定)
            drawingView?.setSizeForBrush(30.toFloat())
            // ダイアログを閉じる
            brushDialog.dismiss()
        }

        // 閉じるアクションが発火した時はスルーされる
        // ダイアログを表示
        brushDialog.show()

        // ------ ダイアログ生成完了 -------------
    }
}