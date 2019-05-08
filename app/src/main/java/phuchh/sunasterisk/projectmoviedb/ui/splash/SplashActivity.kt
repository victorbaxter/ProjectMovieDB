package phuchh.sunasterisk.projectmoviedb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import phuchh.sunasterisk.projectmoviedb.ui.main.MainActivity
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}