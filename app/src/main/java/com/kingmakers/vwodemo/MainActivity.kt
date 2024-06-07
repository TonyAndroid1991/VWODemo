package com.kingmakers.vwodemo

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kingmakers.vwodemo.ui.theme.VWODemoTheme
import com.vwo.insights.VWOInsights
import com.vwo.insights.exposed.IVwoInitCallback
import com.vwo.insights.exposed.models.ClientConfiguration
import com.vwo.mobile.VWO
import com.vwo.mobile.events.VWOStatusListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        VWO.with(this, "a71c474b9f5cb0dce089fa3473b8116b-902006")
            .launch(object : VWOStatusListener {
                override fun onVWOLoaded() {
                    // VWO loaded successfully
                    VWO.trackConversion("screen")

                    val connected = VWO.getBooleanForKey("connected", true)
                    val versionCode = VWO.getIntegerForKey("version_code", 100)
                    val speed = VWO.getIntegerForKey("speed", 11)

                    val variationName = VWO.getVariationNameForTestKey("kingmakers_app")
                    if (variationName != null && variationName == "Variation-1") {
                        val connected = VWO.getBooleanForKey("connected", true)
                        val versionCode = VWO.getIntegerForKey("version_code", 100)
                        val speed = VWO.getIntegerForKey("speed", 11)

                    } else if (variationName != null && variationName == "Variation 1") {
                        val speed = VWO.getBooleanForKey("connected", true)
                    } else {
                        val speed = VWO.getBooleanForKey("connected", true)
                    }
                }
                override fun onVWOLoadFailure(reason: String) {
                    // VWO not loaded
                }
            })


        val configuration = ClientConfiguration("902006","a71c474b9f5cb0dce089fa3473b8116b")
        VWOInsights.init(this.application, object : IVwoInitCallback {
            override fun vwoInitSuccess(message: String) {
                VWOInsights.startSessionRecording()
            }
            override fun vwoInitFailed(message: String) {
                Log.e("VWO", "VWO Insights init failed: $message")
            }

        }, configuration)

        GlobalScope.launch {
            delay(10000)
            VWOInsights.stopSessionRecording()
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VWODemoTheme {
        Greeting("Android")
    }
}