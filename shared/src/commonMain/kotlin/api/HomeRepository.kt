package api

import data.RandomUser
import data.RandomUserApiResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class HomeRepository {

    suspend fun getRandomUsers(): RandomUserApiResponse {
        val response = httpClient.get("https://randomuser.me/api/?page=1&results=20&seed=abc")
        return response.body()
    }

    fun getUsers(): Flow<List<RandomUser>> = flow {
        emit(getRandomUsers().results)
    }

    suspend fun getUsersWithoutflow(): List<RandomUser> {
        return getRandomUsers().results
    }

    // Agregar método con nombre diferente que se exponga correctamente
    suspend fun fetchUserList(): List<RandomUser> {
        return getRandomUsers().results
    }

    // O método síncrono
    fun getUsersSync(): List<RandomUser> {
        return runBlocking {
            getRandomUsers().results
        }
    }
}