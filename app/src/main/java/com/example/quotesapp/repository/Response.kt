package com.example.quotesapp.repository

/*sealed class Response(val data: PopulationYearWise? = null, val errorMessage: String? = null) {
    class Loading: Response()
    class Success(data: PopulationYearWise): Response(data)
    class Error(errorMessage: String): Response(errorMessage = errorMessage)
}*/

sealed class Response<T>(val data: T? = null, val errorMessage: String? = null) {
    class Loading<T>(val loadingState: Boolean): Response<T>()
    class Success<T>(data: T): Response<T>(data)
    class Failure<T>(errorMessage: String): Response<T>(errorMessage = errorMessage)

}