package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.models.RepoRequest
import ec.edu.uisek.githubclient.models.UpdateRepoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface GitHubApiService {
    @GET("user/repos")
    fun getRepos(): Call<List<Repo>>

    @POST("user/repos")
    fun addRepository(
        @Body repoRequest: RepoRequest
    ) : Call<Repo>

    @PATCH("repos/{owner}/{repo}")
    fun updateRepo(
        @retrofit2.http.Path("owner") owner: String,
        @retrofit2.http.Path("repo") repo: String,
        @Body request: UpdateRepoRequest
    ): Call<Repo>

    @DELETE("repos/{owner}/{repo}")
    fun deleteRepo(
        @retrofit2.http.Path("owner") owner: String,
        @retrofit2.http.Path("repo") repo: String
    ): Call<Unit>
}