package dam.pmdm.navigationdrawer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME_OUT = 3000L // 3 segundos para la Splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si la versión de la API es inferior a 31, mostramos la Splash personalizada
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            // Si la versión es inferior a Android 12 (API 31), manejamos la Splash manualmente
            setContentView(R.layout.splash)  // Asegúrate de tener este layout para la Splash

            // Lanzamos la corutina en el ciclo de vida de la actividad
            lifecycleScope.launch {
                delay(SPLASH_TIME_OUT)  // Esperamos durante el tiempo de la Splash

                // Después del tiempo de la Splash, se inicia la MainActivity
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()  // Terminamos la SplashActivity para que no vuelva a abrirse
            }
        } else {
            // Si la versión es 31 o superior, dejamos que Android maneje la Splash automáticamente
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()  // Terminamos la SplashActivity ya que no necesitamos hacer nada más
        }
    }
}
