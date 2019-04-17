package phuchh.sunasterisk.projectmoviedb.ui.splash

import android.content.Intent
import phuchh.sunasterisk.projectmoviedb.ui.main.MainActivity
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity

class SplashActivity : BaseActivity() {
    override fun initView() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}