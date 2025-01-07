package tech.ryanws.pastydroid.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tech.ryanws.pastydroid.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        
        setupViews()
        observeViewModel()
        
        return binding.root
    }

    private fun setupViews() {
        binding.buttonFetch.setOnClickListener {
            val id = binding.inputPasteId.text.toString().toIntOrNull()
            if (id != null) {
                viewModel.fetchPaste(id)
            } else {
                // Show error
                binding.inputLayoutPasteId.error = "Please enter a valid number"
            }
        }
    }

    private fun observeViewModel() {
        viewModel.paste.observe(viewLifecycleOwner) { paste ->
            binding.cardPaste.visibility = if (paste != null) View.VISIBLE else View.GONE
            binding.textPasteContent.text = paste?.content
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.buttonFetch.isEnabled = !isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}