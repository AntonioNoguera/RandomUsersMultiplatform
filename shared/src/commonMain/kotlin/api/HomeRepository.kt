package api

import data.RandomUser
import data.RandomUserApiResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class HomeRepository {

    //Fetching Call
    private suspend fun fetchUsers(): RandomUserApiResponse {
        val response = httpClient.get("https://randomuser.me/api/?page=1&results=20&seed=abc")
        return response.body()
    }

    //For Android
    fun getUsers(): Flow<List<RandomUser>> = flow {
        emit(fetchUsers().results)
    }

    //For iOS
    fun getUsersSync(): List<RandomUser> {
        return runBlocking {
            fetchUsers().results
        }
    }
}