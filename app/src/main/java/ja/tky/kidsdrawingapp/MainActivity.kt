package ja.tky.kidsdrawingapp

import android.os.Bundle
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

    }
}