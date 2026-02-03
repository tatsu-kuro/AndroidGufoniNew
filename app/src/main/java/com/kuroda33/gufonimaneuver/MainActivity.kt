package com.kuroda33.gufonimaneuver

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.kuroda33.gufonimaneuver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var sndF: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ★ ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF = pref.getInt("sndF", 1)
        setSoundimage(sndF)

        // ★ synthetic の代わりに binding 経由で参照
        binding.buttonSpeaker.setOnClickListener(this)
        binding.rCanal.setOnClickListener(this)
        binding.lCanal.setOnClickListener(this)
        binding.rCupla.setOnClickListener(this)
        binding.lCupla.setOnClickListener(this)
    }

    private fun setSoundimage(snd: Int) {
        Log.d("FLOATALPH1", sndF.toString())
        if (snd == 1) {
            binding.buttonSpeaker.setImageResource(R.drawable.speakeron)
        } else {
            binding.buttonSpeaker.setImageResource(R.drawable.speakeroff)
        }
        Log.d("FLOATALPH3", sndF.toString())
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.rCanal.id -> RCanal()
            binding.lCanal.id -> LCanal()
            binding.rCupla.id -> RCupla()
            binding.lCupla.id -> LCupla()
            binding.buttonSpeaker.id -> speakerOnOff()
        }
    }

    private fun speakerOnOff() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        sndF = pref.getInt("sndF", 1)

        val editor: SharedPreferences.Editor = pref.edit()
        sndF = if (sndF == 0) 1 else 0
        editor.putInt("sndF", sndF)
        editor.apply() // commit() でもOKだが apply 推奨

        setSoundimage(sndF)
        Log.d("sndF::", sndF.toString())
    }

    private fun LCanal() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu", 0)
        startActivity(intent)
    }

    private fun RCanal() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu", 1)
        startActivity(intent)
    }

    private fun LCupla() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu", 2)
        startActivity(intent)
    }

    private fun RCupla() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("LCa,RCa,LCu,RCu", 3)
        startActivity(intent)
    }
}
