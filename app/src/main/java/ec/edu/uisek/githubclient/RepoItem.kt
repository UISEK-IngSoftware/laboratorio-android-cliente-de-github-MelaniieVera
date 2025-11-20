package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.uisek.githubclient.databinding.FragmentRepoItemBinding

class RepoItem : Fragment() {
    private var _binding: FragmentRepoItemBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repoName.text = "Mi repositorio Github"
        binding.repoDescription.text = "Esto es un repositorio de prueba"
        binding.repoLanguage.text = "Kotlin"
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepoItem().apply {
                arguments = Bundle().apply {

                }
            }
    }
}