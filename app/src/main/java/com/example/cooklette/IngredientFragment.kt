import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cooklette.databinding.FragmentAddBinding
import com.example.cooklette.databinding.IngredientRowBinding

class IngredientFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddIngredient.setOnClickListener {
            addIngredientRow()
        }
    }

    private fun addIngredientRow() {
        val rowBinding = IngredientRowBinding.inflate(layoutInflater, binding.containerIngredients, true)

        rowBinding.buttonRemoveIngredient.setOnClickListener {
            binding.containerIngredients.removeView(rowBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
