package phuchh.sunasterisk.projectmoviedb.ui.producer


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Crew
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentProducerBinding
import phuchh.sunasterisk.projectmoviedb.ui.actor.ActorActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class ProducerFragment :
    BaseFragment<FragmentProducerBinding, ProducerViewModel>() {
    companion object {
        private const val ARGUMENT_ID: String = "ARGUMENT_ID"
        fun newInstance(id: Int) = ProducerFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGUMENT_ID, id)
            }
        }
    }

    override lateinit var viewModel: ProducerViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_producer
    private lateinit var viewBinding: FragmentProducerBinding
    private lateinit var crewAdapter: DataRecyclerAdapter<Crew>

    override fun initComponent(viewBinding: FragmentProducerBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val id = arguments!!.getInt(ARGUMENT_ID)
        initViewModel()
        initAdapter()
        viewModel.getProducers(id)
        observeViewModel()
    }

    private fun initAdapter() {
        crewAdapter = DataRecyclerAdapter(castClickCallback, R.layout.item_producer)
        viewBinding.recyclerActor.adapter = crewAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(ProducerViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.run {
            producers.observe(viewLifecycleOwner, Observer {
                it?.let { crewAdapter.setData(it) }
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
