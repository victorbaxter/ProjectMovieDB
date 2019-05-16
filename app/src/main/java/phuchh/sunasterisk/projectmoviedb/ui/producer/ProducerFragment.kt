package phuchh.sunasterisk.projectmoviedb.ui.producer


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Cast
import phuchh.sunasterisk.projectmoviedb.data.model.Crew
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentProducerBinding
import phuchh.sunasterisk.projectmoviedb.ui.actor.ActorActivity

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
        observeViewModel(id)
    }

    private fun initAdapter() {
        crewAdapter = DataRecyclerAdapter(castClickCallback, R.layout.item_producer)
        viewBinding.recyclerActor.adapter = crewAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(ProducerViewModel::class.java)
    }

    private fun observeViewModel(movieId: Int) {
        viewModel.getProducers(movieId).observe(viewLifecycleOwner,
            Observer<ApiResponse<List<Crew>>> {
                if (it != null) {
                    updateUI(it)
                }
            })

    }

    private val castClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(ActorActivity.getIntent(context!!, id))
            }
        }
    }

    private fun updateUI(response: ApiResponse<List<Crew>>?) {
        if (response != null) {
            val error: Throwable? = response.error
            val crews: List<Crew>? = response.result
            if (error != null) {
                showToast(error.message!!)
                return
            }
            crewAdapter.setData(crews!!)
        }
    }
}
