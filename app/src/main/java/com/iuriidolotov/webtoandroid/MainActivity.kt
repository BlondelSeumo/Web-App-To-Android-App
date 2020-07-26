package com.iuriidolotov.webtoandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    private lateinit var mInterstitialAd: InterstitialAd

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)

                return true
            }
        }

        //PLACE YOU WEB URL HERE:
        webView.loadUrl("https://www.codester.com/yuradolotov")

        MobileAds.initialize(this,getString(R.string.AdMobAppId))

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId= getString(R.string.AdMobAppInterstitialId)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val mFab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        mFab.setOnClickListener {

            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                Toast.makeText(this@MainActivity, "There are no previous web pages", Toast.LENGTH_LONG).show()
            }

            loadAds()
        }

        //SHARE BUTTON HERE
        val mFabShare = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        mFabShare.setOnClickListener {
            val s = webView.getUrl()
            //Intent to share the text
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, s);
            startActivity(Intent.createChooser(shareIntent,"Share via:"))
        }
    }

   private fun loadAds() {
        if(mInterstitialAd.isLoaded)
            mInterstitialAd.show()
    }
}

