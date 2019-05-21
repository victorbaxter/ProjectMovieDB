package phuchh.sunasterisk.projectmoviedb.ui.cast


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Cast
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentCastBinding
import phuchh.sunasterisk.projectmoviedb.ui.actor.ActorActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class CastFragment : BaseFragment<FragmentCastBinding, CastViewModel>() {
    companion object {
        private const val ARGUMENT_ID: String = "ARGUMENT_ID"
        fun newInstance(id: Int) = CastFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGUMENT_ID, id)
            }
        }
    }

    override lateinit var viewModel: CastViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_cast
    private lateinit var viewBinding: FragmentCastBinding
    private lateinit var castAdapter: DataRecyclerAdapter<Cast>

    override fun initComponent(viewBinding: FragmentCastBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val id = arguments!!.getInt(ARGUMENT_ID)
        initViewModel()
        initAdapter()
        viewModel.getCast(id)
        observeViewModel()
    }

    private fun initAdapter() {
        castAdapter = DataRecyclerAdapter(castClickCallback, R.layout.item_cast)
        viewBinding.recyclerActor.adapter = castAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(CastViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.run {
            cast.observe(viewLifecycleOwner, Observer {
                it?.let { castAdapter.setData(it) }
            })
            error.observe(viewLifecycleOwner, Observer { it?.let { showToast(it) } })
        }
    }

    private val castClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(ActorActivity.getIntent(context!!, id))
            }
        }
    }

}
