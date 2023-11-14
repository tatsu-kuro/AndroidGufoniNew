package com.kuroda33.gufonimaneuver

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    var imgBArray = arrayListOf<Int>()//back
    var imgFArray = arrayListOf<Int>()//front
    var sndArray = arrayListOf<Int>()
    var lenArray = arrayListOf<Int>()//length time
    var remArray = arrayListOf<Int>()//remain time
    //    private var mediaPlayer: MediaPlayer? = null
    private var mTimer: Timer? = null
    private var mHandler = Handler()
    private var cnt:Int = 0
    //   var snd:Boolean=true
    var sndF:Int =0
    //    private var cnt10:Int = 0
    private var remain:Int = 0
    //    private var playingF:Boolean=false
    // 最低スワイプ距離
    private val SWIPEDISTANCE = 10
    // 最低スワイプスピード
    private val SWIPEVELOCITY = 200
    // X軸の移動距離 これ以上なら縦移動を判定しない
    //   private val SWIPE_MAX_OFF_PATH = 200
    // タッチイベントを処理するためのインタフェース
    private var mGestureDetector: GestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF=pref.getInt("sndF",1)
        mGestureDetector = GestureDetector(this, mOnGestureListener) // => 忘れない
   //     if(sndF==1)sndOn()
     //   else sndOff()
        playnext.setOnClickListener(this)
        playback.setOnClickListener(this)
        val type=intent.getIntExtra("LCa,RCa,LCu,RCu",0)
        Remain.text = ""
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

        if(type == 0){//LCanal
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

        }else if(type ==1){//RCanal
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
        }else if(type==2){//LCupla
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

        }else {//RCupla
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
        cnt=0
        //      cnt10=0
        remain=0
        mTimer = Timer()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)    // タイマーの始動
        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                if(cnt%10==0) {
                    if (remain > 0) {
                        mHandler.post {
                            Remain.text = String.format("%d", remain / 10)
                        }
                        remain -= 10
                    } else {
                        mHandler.post {
                            Remain.text = "    "
                        }
                    }
                }

                for (i in 0..5) {
                    if(i==0 && cnt==lenArray[i]+10){
                        if(sndF==1)audioPlay(sndArray[i])
                    }
                    else if (i>1 && cnt == lenArray[i] + 20) {
                        if(sndF==1)audioPlay(R.raw.alarm1)
                    }else
                        if (cnt == lenArray[i] + 40) {
                            remain = remArray[i]
                            if (i != 0 && sndF==1) audioPlay(sndArray[i])
                            //                      Log.d("cntimg", String.format("%d %d %d", cnt, i, remain))
                        }else
                            if (cnt > lenArray[i]&&cnt<lenArray[i]+50) {
                                //                   if (cnt % 10 != 0) {
                                mHandler.post {
                                    EpleyImage2.setImageResource(imgFArray[i])
                                    EpleyImage.setImageResource(imgBArray[i])
                                    EpleyImage2.alpha = 0.02f * ((cnt - lenArray[i]).toFloat())
                                    //  val temp=cnt - lenArray[i]
                                    //   Log.d("FLOATALPH", temp.toString())
                                }
                                //                 }
                            }
                }
                if (cnt == lenArray[6]) {
                    //window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    finish()
                }
                cnt += 1
            }
        }, 100, 100)
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlay.release()
        if (mTimer != null){
            mTimer!!.cancel()
            mTimer = null
        }
        //     window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //遷移すると自動でクリアされる
    }
    private fun audioPlay(snd:Int){
        mediaPlay.release()
        mediaPlay = MediaPlayer.create(this,snd)
        mediaPlay.start()
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.playback-> backScene()
            R.id.playnext -> nextScene()
        }





    /*    val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF=pref.getInt("sndF",1)
        val editor: SharedPreferences.Editor=pref.edit()
        if (sndF==0){
            sndF=1
        }else {
            sndF = 0
        }
        editor.putInt("sndF",sndF)
        editor.commit()
        if(sndF==1)sndOn()
        else sndOff()
        Log.d("sndF::",sndF.toString())*/
    }
    /*
    private fun sndOff(){
            snd=false
       // mediaPlay.release()
        //ButtonSndOnOff.setImageResource(android.R.drawable.ic_lock_silent_mode_off)
    }
    private fun sndOn(){
           snd=true
    //    ButtonSndOnOff.setImageResource(android.R.drawable.ic_lock_silent_mode)
    }*/
    private var mediaPlay = MediaPlayer()
    private fun nextScene(){
        remain=0
        mediaPlay.release()
        for(i in 0..5){
            if(cnt>lenArray[i]&&cnt<lenArray[i+1]){
                cnt=lenArray[i+1]
            }
        }
    }
    private fun backScene(){
        remain=0
        mediaPlay.release()
        for(i in 0..5){
            if(cnt>lenArray[i] && cnt<lenArray[i+1]+50){
                cnt=lenArray[i]
            }else if(cnt>lenArray[6]) {
                cnt=lenArray[6]
            }
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector!!.onTouchEvent(event)
    }
    // タッチイベントのリスナー
    private val mOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        // フリックイベント
        override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            try {
                if (event2.x - event1.x > SWIPEDISTANCE && Math.abs(velocityX) > SWIPEVELOCITY) {
                    //                  Log.d("onFling","右へ")
                    backScene()
                    // 終了位置から開始位置の移動距離が指定値より大きい
                    // Y軸の移動速度が指定値より大きい
                } else if (event1.x - event2.x > SWIPEDISTANCE && Math.abs(velocityX) > SWIPEVELOCITY) {
                    //              Log.d("onFling","左へ")
                    nextScene()
                }
            } catch (e: Exception) {
                // TODO
            }
            return false
        }
    }
}
