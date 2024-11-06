package dam.pmdm.navigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import dam.pmdm.navigationdrawer.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding // Usar lateinit para evitar nullabilidad


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false) // Inicializar binding

        initialiceSpinner()
        return binding.root // Retornar la vista raíz
    }

    private fun initialiceSpinner() {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(), R.array.animals, R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinner.adapter = adapter

        // Asignar el adaptador al Spinner
        binding.spinner.adapter = adapter
    }
}
