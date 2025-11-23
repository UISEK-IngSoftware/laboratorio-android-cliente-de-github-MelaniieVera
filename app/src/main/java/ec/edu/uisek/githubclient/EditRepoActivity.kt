package ec.edu.uisek.githubclient

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uisek.githubclient.databinding.ActivityEditRepoBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.models.UpdateRepoRequest
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRepoBinding

    private lateinit var originalRepoName: String
    private lateinit var ownerLogin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalRepoName = intent.getStringExtra("repo_name") ?: ""
        val repoDescription = intent.getStringExtra("repo_desc") ?: ""
        ownerLogin = intent.getStringExtra("repo_owner") ?: ""

        binding.editRepoNameInput.setText(originalRepoName)
        binding.editRepoDescriptionInput.setText(repoDescription)

        binding.editCancelButton.setOnClickListener { finish() }


        binding.editSaveButton.setOnClickListener { updateRepo() }
    }

    private fun validateForm(): Boolean {
        val repoName = binding.editRepoNameInput.text.toString()

        if (repoName.isBlank()) {
            binding.editRepoNameInput.error = "El nombre es requerido"
            return false
        }
        if (repoName.contains(" ")) {
            binding.editRepoNameInput.error = "No puede contener espacios"
            return false
        }
        return true
    }

    private fun updateRepo() {
        if (!validateForm()) return

        val newName = binding.editRepoNameInput.text.toString()
        val newDesc = binding.editRepoDescriptionInput.text.toString()

        val request = UpdateRepoRequest(
            name = newName,
            description = newDesc
        )

        val apiService = RetrofitClient.gitHubApiService
        val call = apiService.updateRepo(ownerLogin, originalRepoName, request)

        call.enqueue(object : Callback<Repo> {
            override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                if (response.isSuccessful) {
                    showMessage("Repositorio actualizado correctamente")
                    finish()
                } else {
                    val errMsg = when (response.code()) {
                        401 -> "Error de autenticación"
                        403 -> "Acceso denegado"
                        404 -> "Repositorio no encontrado"
                        else -> "Error ${response.code()}: ${response.message()}"
                    }
                    Log.e("EditRepoActivity", errMsg)
                    showMessage(errMsg)
                }
            }

            override fun onFailure(call: Call<Repo>, t: Throwable) {
                val errMsg = "Error de conexión: ${t.message}"
                Log.e("EditRepoActivity", errMsg, t)
                showMessage(errMsg)
            }
        })
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
