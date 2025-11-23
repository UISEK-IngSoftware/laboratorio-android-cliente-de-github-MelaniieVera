package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ec.edu.uisek.githubclient.databinding.FragmentRepoItemBinding
import ec.edu.uisek.githubclient.models.Repo

// 1. Clase ViewHolder: Contiene las referencias a las vistas de un solo ítem.
//    Usa la clase de ViewBinding generada para fragment_repo_item.xml.
class RepoViewHolder( val binding: FragmentRepoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    // 2. Función para vincular datos a las vistas del ítem.
    //    Por ahora, usaremos datos de ejemplo.
    fun bind(repo: Repo) {
        binding.repoName.text = repo.name
        binding.repoDescription.text = repo.description ?: "No hay descripción en el repositorio"
        binding.repoLanguage.text = repo.language ?: "No hay lenguaje definido"
        Glide.with(binding.root.context)
            .load(repo.owner.avatarUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .circleCrop()
            .into(binding.repoOwnerImage)
    }
}


class ReposAdapter : RecyclerView.Adapter<RepoViewHolder>() {

    var onEditClick: (Repo) -> Unit = {}
    var onDeleteClick: (Repo) -> Unit = {}
    private var repositories : List<Repo> = emptyList()
    override fun getItemCount(): Int = repositories.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {

        val binding = FragmentRepoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repositories[position]
        holder.bind(repo)

        holder.binding.btnEdit.setOnClickListener {
            onEditClick(repo)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(repo)
        }
    }

    fun updateRepositories(newRepositories : List<Repo>){
        repositories = newRepositories
        notifyDataSetChanged()
    }
}