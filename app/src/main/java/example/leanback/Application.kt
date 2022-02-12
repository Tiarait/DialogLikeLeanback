package example.leanback

import android.os.StrictMode
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication

class Application : MultiDexApplication() {
    companion object {
        lateinit var instance: Application
            private set
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        initializeStrictMode()
        initializeAppData()
    }

    private fun initializeStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }

    private fun initializeAppData() {
    }
}