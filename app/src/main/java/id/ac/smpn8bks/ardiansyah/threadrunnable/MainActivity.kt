package id.ac.smpn8bks.ardiansyah.threadrunnable

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.ac.smpn8bks.ardiansyah.threadrunnable.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            Worker().execute()
        }
    }
    @SuppressLint("StaticFieldLeak")
    inner class Worker : AsyncTask<Void, String, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean {
            for (i in 1..20) {
                publishProgress(i.toString())
                Thread.sleep(2000)
            }
            return true
        }
        override fun onProgressUpdate(vararg values: String?) {
            binding.textView.text = values[0]
        }
        override fun onPostExecute(result: Boolean?) {
            println(result)
        }
    }
}