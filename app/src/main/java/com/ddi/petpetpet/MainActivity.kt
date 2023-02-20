package com.ddi.petpetpet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ddi.petpetpet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la vista principal a este código
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear la base de datos
        dbHelper = DatabaseHelper(this, null)

        // Insertar el usuario admin si no existe
        if (!dbHelper.checkUser(ADMIN_USERNAME, ADMIN_PASSWORD)) {
            dbHelper.insertUsuario(usuario = ADMIN_USERNAME, contrasena = ADMIN_PASSWORD)
        }

        // comportamiento del botón "Login"
        binding.loginButton.setOnClickListener {
            // Ocultar teclado
            binding.usuario.showSoftInputOnFocus = false
            binding.contrasena.showSoftInputOnFocus = false

            // Recoge el nombre tecleado en el editText
            val usuario = binding.usuario.text.toString()
            val contrasena = binding.contrasena.text.toString()

            // Verificar si el usuario y contraseña son válidos
            if (dbHelper.checkUser(usuario, contrasena)) {
                // El usuario y la contraseña son correctos
                val toast = Toast.makeText(this, "Bienvenido, $usuario", Toast.LENGTH_LONG)
                toast.show()

                // Genera el contenedor de datos que llamaremos intent
                val intent = Intent(this, RegistroAnimalesActivity::class.java )
                // Introduce en el contenedor el dato que pasamos a la otra actividad
                intent.putExtra("Usuario",usuario)
                // Llama a la otra actividad
                startActivity(intent)
                // Finaliza la actividad actual
                finish()

            } else {
                // El usuario y/o la contraseña son incorrectos
                val toast2 = Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG)
                toast2.show()
            }

        }//btnLogin



    }//onCreate
    override fun onDestroy() {
        super.onDestroy()
        // Cerrar la conexión a la base de datos al salir de la aplicación
        dbHelper.close()
    }

    companion object {
        private const val ADMIN_USERNAME = "admin"
        private const val ADMIN_PASSWORD = "admin"
    }


}//main