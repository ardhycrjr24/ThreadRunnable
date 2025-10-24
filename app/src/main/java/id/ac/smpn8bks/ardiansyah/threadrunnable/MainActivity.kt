package id.ac.smpn8bks.ardiansyah.threadrunnable

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import id.ac.smpn8bks.ardiansyah.threadrunnable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val maxCounter = workDataOf(MyWorker.COUNTER to 10)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val myWorker = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(maxCounter)
            .setConstraints(constraints)
            .build()

        binding.button.setOnClickListener {
            Toast.makeText(this, "Menjalankan WorkManager...", Toast.LENGTH_SHORT).show()
            workManager.enqueueUniqueWork(
                "oneTimeRequest",
                ExistingWorkPolicy.REPLACE,
                myWorker
            )
        }

        workManager.getWorkInfoByIdLiveData(myWorker.id)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    val progress = workInfo.progress.getInt(MyWorker.PROGRESS, 0)
                    binding.textView.text = "Progres: $progress"

                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val msg = workInfo.outputData.getString(MyWorker.MESSAGE)
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
