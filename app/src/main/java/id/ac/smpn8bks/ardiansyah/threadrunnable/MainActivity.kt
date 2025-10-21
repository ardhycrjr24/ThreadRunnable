package id.ac.smpn8bks.ardiansyah.threadrunnable

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import id.ac.smpn8bks.ardiansyah.threadrunnable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi handler untuk update UI
        mHandler = MyHandler()

        // Tombol untuk mulai proses thread
        binding.button.setOnClickListener {
            Thread {
                killSomeTime()
            }.start()
        }
    }

    /**
     * Handler digunakan untuk menerima pesan dari thread background
     * dan memperbarui UI di Main Thread.
     */
    @SuppressLint("HandlerLeak")
    inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {

            val counterValue = msg.data?.getString("counter")
            binding.textView.text = "Hitungan ke-$counterValue"
        }
    }

    /**
     * Simulasi proses berat (looping 1..20)
     * Setiap iterasi mengirim pesan ke Handler.
     */
    private fun killSomeTime() {
        for (i in 1..20) {

            val msg = Message.obtain()
            msg.data.putString("counter", i.toString())

            mHandler.sendMessage(msg)

            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            println("i: $i")
        }


        val msg = Message.obtain()
        msg.data.putString("counter", "Selesai!")
        mHandler.sendMessage(msg)
    }
}
