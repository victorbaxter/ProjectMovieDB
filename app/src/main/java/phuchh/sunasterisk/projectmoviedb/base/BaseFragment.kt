package phuchh.sunasterisk.projectmoviedb.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import phuchh.sunasterisk.projectmoviedb.BR

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    private lateinit var viewBinding: VB
    protected abstract fun getLayoutRes(): Int
    protected abstract var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return viewBinding.root
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponent(viewBinding, savedInstanceState)
        lifecycle.addObserver(viewModel)
        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
        }
    }

    fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    abstract fun initComponent(
        viewBinding: VB,
        savedInstanceState: Bundle?
    )
}