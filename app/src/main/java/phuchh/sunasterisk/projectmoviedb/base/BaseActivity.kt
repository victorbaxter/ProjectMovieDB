package phuchh.sunasterisk.projectmoviedb.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var viewBinding: VB
    protected abstract fun getLayoutRes(): Int
    protected abstract var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        viewBinding.lifecycleOwner = this
        initComponent(viewBinding, savedInstanceState)
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }

    fun replaceFragment(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean = true,
        tag: String
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(container, fragment, tag)
            if (isAddToBackStack)
                addToBackStack(tag)
            commit()
        }
    }

    fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    abstract fun initComponent(viewBinding: VB, savedInstanceState: Bundle?)
}
