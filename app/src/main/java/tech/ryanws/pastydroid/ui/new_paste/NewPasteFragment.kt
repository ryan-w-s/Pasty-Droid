package tech.ryanws.pastydroid.ui.new_paste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tech.ryanws.pastydroid.databinding.FragmentNewPasteBinding
import android.view.WindowManager

class NewPasteFragment : Fragment() {
    private var _binding: FragmentNewPasteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewPasteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewPasteViewModel::class.java]
        _binding = FragmentNewPasteBinding.inflate(inflater, container, false)
        
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        
        setupViews()
        observeViewModel()
        
        return binding.root
    }

    private fun setupViews() {
        binding.buttonCreate.setOnClickListener {
            val content = binding.inputContent.text.toString()
            if (content.isNotBlank()) {
                viewModel.createPaste(content)
            } else {
                binding.inputLayoutContent.error = "Content cannot be empty"
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.buttonCreate.isEnabled = !isLoading
        }

        viewModel.success.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "Paste created successfully", Toast.LENGTH_SHORT).show()
                binding.inputContent.text?.clear()
            } else {
                Toast.makeText(context, "Failed to create paste", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        _binding = null
    }
} 