package com.kuroda33.gufonimaneuver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.preference.PreferenceManager
import android.util.Log
import com.kuroda33.gufonimaneuver.R.id.RCanal
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , View.OnClickListener {
    var sndF:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF=pref.getInt("sndF",1)
        setSoundimage(sndF)
        ButtonSpeaker.setOnClickListener(this)
        RCanal.setOnClickListener(this)
        LCanal.setOnClickListener(this)
        RCupla.setOnClickListener(this)
        LCupla.setOnClickListener(this)
    }
    fun setSoundimage(snd:Int)
    {
        Log.d("FLOATALPH1", sndF.toString())
        if(snd==1){
            ButtonSpeaker.setImageResource(R.drawable.speakeron)
        }else{
            ButtonSpeaker.setImageResource(R.drawable.speakeroff)
        }
        Log.d("FLOATALPH3", sndF.toString())
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.RCanal -> RCanal()
            R.id.LCanal -> LCanal()
            R.id.RCupla -> RCupla()
            R.id.LCupla -> LCupla()
            R.id.ButtonSpeaker -> speakerOnOff()
        }
    }
    fun speakerOnOff(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF=pref.getInt("sndF",1)
        val editor: SharedPreferences.Editor=pref.edit()
        if (sndF==0){
            sndF=1
        }else {
            sndF = 0
        }
        editor.putInt("sndF",sndF)
        editor.commit()
        setSoundimage(sndF)
        Log.d("sndF::",sndF.toString())
    }
    fun LCanal(){
        val intent= Intent(this,SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu",0)
        startActivity(intent)
    }
    fun RCanal(){
        val intent= Intent(this,SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu",1)
        startActivity(intent)
    }
    fun LCupla(){
        val intent= Intent(this,SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu",2)
        startActivity(intent)
    }
    fun RCupla(){
        val intent= Intent(this,SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu",3)
        startActivity(intent)
    }
 }
