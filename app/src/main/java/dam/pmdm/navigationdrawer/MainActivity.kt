package dam.pmdm.navigationdrawer

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.navigationdrawer.databinding.ActivityMainBinding
import dam.pmdm.navigationdrawer.databinding.GuideBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var guideBinding: GuideBinding
    private lateinit var navController: NavController

    private var needToStartGuide: Boolean = true
    private var stepGuide: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        guideBinding = binding.includeLayout
        setContentView(binding.root)

        // Obtener el NavController desde el NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        // Configurar el icono del menú en la ActionBar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeGuide()


//        Configurar menu toogle
        configureToggleMenu();
        // Configurar la navegación
        configureNavigation();

    }

    private fun initializeGuide() {
        guideBinding.exitGuide.setOnClickListener(::onExitGuide)

        if (needToStartGuide) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            guideBinding.guideLayout.visibility = View.VISIBLE


            val scaleX = ObjectAnimator.ofFloat(guideBinding.pulseImage, "scaleX", 1f, 0.5f)
            val scaleY = ObjectAnimator.ofFloat(guideBinding.pulseImage, "scaleY", 1f, 0.5f)

            val fadeIn = ObjectAnimator.ofFloat(guideBinding.textStep, "alpha", 0f, 1f)

            scaleX.repeatCount = 3
            scaleY.repeatCount = 3
            val animatorSet = AnimatorSet()
            animatorSet.play(scaleX).with(scaleY).before(fadeIn)


            animatorSet.duration = 1000 // Duración de la animación
            animatorSet.start()
            animatorSet.doOnEnd {
                if (needToStartGuide) {
                    binding.drawerLayout.open()
                    guideBinding.pulseImage.visibility = View.GONE
                    guideBinding.textStep.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun onExitGuide(view: View?) {
        needToStartGuide = false;

//        val soundPool = SoundPool.Builder().setMaxStreams(1).build()
//        val soundId = soundPool.load(this, R.raw.pulse, 1)
//
//        soundPool.setOnLoadCompleteListener { _, _, status ->
//            if (status == 0) {  // Carga exitosa
//                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
//            }
//        }

        val mediaPlayer = MediaPlayer.create(this, R.raw.pulse)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            guideBinding.guideLayout.visibility = View.GONE
        }
    }

    private fun configureToggleMenu() {
        // Configurar el ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    private fun configureNavigation() {
        NavigationUI.setupWithNavController(binding.navView, navController)
        // Manejar la selección de elementos del menú
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.homeFragment) // Navegar al fragmento de inicio
                }
            }
            binding.drawerLayout.closeDrawers() // Cerrar el menú
            true
        }

//        Maneja la opción de perfil del header del menú
        val headerView = binding.navView.getHeaderView(0) // Obtiene la vista del encabezado
        val profileImageView: ImageView = headerView.findViewById(R.id.header_image)

        profileImageView.setOnClickListener {
            navController.navigate(R.id.profileFragment) // Navegar al fragmento de perfil
            binding.drawerLayout.closeDrawers() // Cerrar el menú
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Manejar clics en el icono del menú
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            NavHostFragment.findNavController(binding.navView.findFragment()),
            binding.drawerLayout
        ) || super.onSupportNavigateUp()
    }

}
