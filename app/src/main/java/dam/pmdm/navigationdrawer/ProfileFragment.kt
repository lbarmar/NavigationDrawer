package dam.pmdm.navigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dam.pmdm.navigationdrawer.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding // Usar lateinit para evitar nullabilidad

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.videoView.setVideoPath("android.resource://dam.pmdm.navigationdrawer/${R.raw.agua}")
        binding.videoView.start()

        return binding.root
    }
}
