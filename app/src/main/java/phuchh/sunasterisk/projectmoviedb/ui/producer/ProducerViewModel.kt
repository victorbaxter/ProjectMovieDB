package phuchh.sunasterisk.projectmoviedb.ui.producer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Crew
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class ProducerViewModel(val repository: MovieRepository) : BaseViewModel() {
    fun getProducers(id: Int) = Transformations.switchMap(repository.getCrew(id)) { filterCrew(it) }

    private fun filterCrew(response: ApiResponse<List<Crew>>): LiveData<ApiResponse<List<Crew>>> {
        val data = MutableLiveData<ApiResponse<List<Crew>>>()
        if (response.error == null) {
            data.postValue(ApiResponse(response.result!!.filter {
                it.job!!.contains(
                    "producer",
                    true
                ) || it.job.contains("directer", true)
            }))
        }
        return data
    }

}