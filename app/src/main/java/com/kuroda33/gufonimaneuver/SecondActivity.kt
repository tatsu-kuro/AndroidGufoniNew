package com.kuroda33.gufonimaneuver

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kuroda33.gufonimaneuver.databinding.ActivitySecondBinding
import java.util.Timer
import java.util.TimerTask

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySecondBinding

    private val imgBArray = arrayListOf<Int>() // back
    private val imgFArray = arrayListOf<Int>() // front
    private val sndArray = arrayListOf<Int>()
    private val lenArray = arrayListOf<Int>() // length time
    private val remArray = arrayListOf<Int>() // remain time

    private var mTimer: Timer? = null
    private val mHandler = Handler()
    private var cnt: Int = 0
    private var sndF: Int = 0
    private var remain: Int = 0

    private var mGestureDetector: GestureDetector? = null
    private var mediaPlay = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF = pref.getInt("sndF", 1)

        mGestureDetector = GestureDetector(this, mOnGestureListener)

        binding.playnext.setOnClickListener(this)
        binding.playback.setOnClickListener(this)

        val type = intent.getIntExtra("LCa,RCa,LCu,RCu", 0)

        binding.remain.text = ""

        imgBArray.clear()
        imgFArray.clear()
        sndArray.clear()
        lenArray.clear()
        remArray.clear()

        remArray.add(0)
        remArray.add(0)
        remArray.add(1200)
        remArray.add(1200)
        remArray.add(1200)
        remArray.add(0)

        lenArray.add(0)
        lenArray.add(40)
        lenArray.add(lenArray.last() + 60)
        lenArray.add(lenArray.last() + 1250)
        lenArray.add(lenArray.last() + 1250)
        lenArray.add(lenArray.last() + 1250)
        lenArray.add(lenArray.last() + 200)

        if (type == 0) { // LCanal
            imgBArray.add(R.drawable.gufonilca)
            imgFArray.add(R.drawable.gufonilca)
            imgBArray.add(R.drawable.gufonilca)
            imgFArray.add(R.drawable.gufonil1ca)
            imgBArray.add(R.drawable.gufonil1can)
            imgFArray.add(R.drawable.gufonil2ca)
            imgBArray.add(R.drawable.gufonil2can)
            imgFArray.add(R.drawable.gufonil3ca)
            imgBArray.add(R.drawable.gufonil3can)
            imgFArray.add(R.drawable.gufonil4ca)
            imgBArray.add(R.drawable.gufonil4can)
            imgFArray.add(R.drawable.gufoniend)

            sndArray.add(R.raw.gufonistart)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.gufoniend)

        } else if (type == 1) { // RCanal
            imgBArray.add(R.drawable.gufonirca)
            imgFArray.add(R.drawable.gufonirca)
            imgBArray.add(R.drawable.gufonirca)
            imgFArray.add(R.drawable.gufonir1ca)
            imgBArray.add(R.drawable.gufonir1can)
            imgFArray.add(R.drawable.gufonir2ca)
            imgBArray.add(R.drawable.gufonir2can)
            imgFArray.add(R.drawable.gufonir3ca)
            imgBArray.add(R.drawable.gufonir3can)
            imgFArray.add(R.drawable.gufonir4ca)
            imgBArray.add(R.drawable.gufonir4can)
            imgFArray.add(R.drawable.gufoniend)

            sndArray.add(R.raw.gufonistart)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.gufoniend)

        } else if (type == 2) { // LCupla
            imgBArray.add(R.drawable.gufonilcu)
            imgFArray.add(R.drawable.gufonilcu)
            imgBArray.add(R.drawable.gufonilcu)
            imgFArray.add(R.drawable.gufonil1cu)
            imgBArray.add(R.drawable.gufonil1cun)
            imgFArray.add(R.drawable.gufonil2cu)
            imgBArray.add(R.drawable.gufonil2cun)
            imgFArray.add(R.drawable.gufonil3cu)
            imgBArray.add(R.drawable.gufonil3cun)
            imgFArray.add(R.drawable.gufonil4cu)
            imgBArray.add(R.drawable.gufonil4cun)
            imgFArray.add(R.drawable.gufoniend)

            sndArray.add(R.raw.gufonistart)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.gufoniend)

        } else { // RCupla
            imgBArray.add(R.drawable.gufonircu)
            imgFArray.add(R.drawable.gufonircu)
            imgBArray.add(R.drawable.gufonircu)
            imgFArray.add(R.drawable.gufonir1cu)
            imgBArray.add(R.drawable.gufonir1cun)
            imgFArray.add(R.drawable.gufonir2cu)
            imgBArray.add(R.drawable.gufonir2cun)
            imgFArray.add(R.drawable.gufonir3cu)
            imgBArray.add(R.drawable.gufonir3cun)
            imgFArray.add(R.drawable.gufonir4cu)
            imgBArray.add(R.drawable.gufonir4cun)
            imgFArray.add(R.drawable.gufoniend)

            sndArray.add(R.raw.gufonistart)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.no)
            sndArray.add(R.raw.gufoniend)
        }

        cnt = 0
        remain = 0

        mTimer = Timer()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                if (cnt % 10 == 0) {
                    if (remain > 0) {
                        mHandler.post {
                            binding.remain.text = String.format("%d", remain / 10)
                        }
                        remain -= 10
                    } else {
                        mHandler.post { binding.remain.text = "    " }
                    }
                }

                for (i in 0..5) {
                    if (i == 0 && cnt == lenArray[i] + 10) {
                        if (sndF == 1) audioPlay(sndArray[i])
                    } else if (i > 1 && cnt == lenArray[i] + 20) {
                        if (sndF == 1) audioPlay(R.raw.alarm1)
                    } else if (cnt == lenArray[i] + 40) {
                        remain = remArray[i]
                        if (i != 0 && sndF == 1) audioPlay(sndArray[i])
                    } else if (cnt > lenArray[i] && cnt < lenArray[i] + 50) {
                        mHandler.post {
                            binding.epleyImage2.setImageResource(imgFArray[i])
                            binding.epleyImage.setImageResource(imgBArray[i])
                            binding.epleyImage2.alpha = 0.02f * ((cnt - lenArray[i]).toFloat())
                        }
                    }
                }

                if (cnt == lenArray[6]) finish()
                cnt += 1
            }
        }, 100, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        runCatching { mediaPlay.release() }
        mTimer?.cancel()
        mTimer = null
    }

    private fun audioPlay(snd: Int) {
        runCatching { mediaPlay.release() }
        mediaPlay = MediaPlayer.create(this, snd)
        mediaPlay.start()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.playback -> backScene()
            R.id.playnext -> nextScene()
        }
    }

    private fun nextScene() {
        remain = 0
        runCatching { mediaPlay.release() }
        for (i in 0..5) {
            if (cnt > lenArray[i] && cnt < lenArray[i + 1]) cnt = lenArray[i + 1]
        }
    }

    private fun backScene() {
        remain = 0
        runCatching { mediaPlay.release() }
        for (i in 0..5) {
            if (cnt > lenArray[i] && cnt < lenArray[i + 1] + 50) cnt = lenArray[i]
            else if (cnt > lenArray[6]) cnt = lenArray[6]
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector?.onTouchEvent(event) ?: super.onTouchEvent(event)
    }

    private val mOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        // 今はコメントアウトのままでOK
    }
}
