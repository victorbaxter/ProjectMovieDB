package phuchh.sunasterisk.projectmoviedb.data.model.response

class ApiResponse<T> {
    constructor(result: T?) {
        this.result = result
    }

    constructor(error: Throwable) {
        this.error = error
    }

    var error: Throwable? = null
    var result: T? = null

}