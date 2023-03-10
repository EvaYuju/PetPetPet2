package com.ddi.petpetpet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ddi.petpetpet.databinding.ActivityRegistroAnimalBinding
import com.ddi.petpetpet.db.DatabaseHelper
import com.google.android.material.snackbar.Snackbar

class RegistroAnimalesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroAnimalBinding
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula a la vista secundaria este código
        binding = ActivityRegistroAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear la base de datos
        dbHelper = DatabaseHelper(this, null)

        // Recupera el nombre de usuario del Intent que inició esta actividad
        val usuario = intent.getStringExtra("Usuario")
        // Actualiza el TextView con el nombre de usuario
        binding.usuarioLogeado.text = "Usuario: $usuario"

        // Asignar escuchadores de clic a los botones
        // Botón insertar
        binding.btnAlta.setOnClickListener {
            // Limpiar los campos
            binding.ptCodigo.setText("")
            binding.ptNombre.setText("")
            binding.ptRaza.setText("")
            binding.ptSexo.setText("")
            binding.ptFecNac.setText("")
            binding.ptDNI.setText("")
            // Código para guardar el registro
            val codigo = binding.ptCodigo.text.toString()
            val nombre = binding.ptNombre.text.toString()
            val raza = binding.ptRaza.text.toString()
            val sexo = binding.ptSexo.text.toString()
            val fecnac = binding.ptFecNac.text.toString()
            val dniPropietario = binding.ptDNI.text.toString()
            val imagen =

                // Verificar si todos los campos están llenos
                if (codigo.isNotEmpty() && nombre.isNotEmpty() && raza.isNotEmpty() && sexo.isNotEmpty() && fecnac.isNotEmpty() && dniPropietario.isNotEmpty()) {
                    // Verificar si el código ya existe en la base de datos
                    val dbHandler = DatabaseHelper(this, null)
                    val animal = dbHandler.getAnimal(codigo)
                    if (animal == null) {
                        // Insertar el nuevo animal en la base de datos
                        dbHandler.insertAnimal(codigo, nombre, raza, sexo, fecnac, dniPropietario)
                        // Limpiar los campos después de insertar
                        binding.ptCodigo.setText("")
                        binding.ptNombre.setText("")
                        binding.ptRaza.setText("")
                        binding.ptSexo.setText("")
                        binding.ptFecNac.setText("")
                        binding.ptDNI.setText("")
                        // Aquí es donde se llama a Snackbar en lugar de Toast
                        Snackbar.make(binding.root, "Datos insertados", Snackbar.LENGTH_SHORT).show()
                    } else {
                        // Aquí es donde se llama a Snackbar en lugar de Toast
                        Snackbar.make(
                            binding.root,
                            "El código ya existe en la base de datos",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Aquí es donde se llama a Snackbar en lugar de Toast
                    Snackbar.make(
                        binding.root,
                        "Todos los campos son obligatorios",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
        }

        // Botón actualizar
        // Botón actualizar
        binding.btnModif.setOnClickListener {
            // Código para modificar el registro
            // Obtenemos los nuevos valores de los campos
            val codigo = binding.ptCodigo.text.toString()
            val nombre = binding.ptNombre.text.toString()
            val raza = binding.ptRaza.text.toString()
            val sexo = binding.ptSexo.text.toString()
            val fecnac = binding.ptFecNac.text.toString()
            val dniPropietario = binding.ptDNI.text.toString()

            val dbHandler = DatabaseHelper(this, null)
            val animal = dbHandler.getAnimal(codigo)

            if (animal != null) {
                // Actualizamos los datos del animal con los nuevos valores
                dbHandler.updateAnimal(codigo, nombre, raza, sexo, fecnac, dniPropietario)

                // Limpiamos los campos de entrada de texto
                binding.ptCodigo.text.clear()
                binding.ptNombre.text.clear()
                binding.ptRaza.text.clear()
                binding.ptSexo.text.clear()
                binding.ptFecNac.text.clear()
                binding.ptDNI.text.clear()

                // Mostramos un mensaje al usuario
                Snackbar.make(binding.root, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
            } else {
                // Si no se encuentra el animal en la base de datos, mostramos un mensaje de error
                Snackbar.make(
                    binding.root,
                    "No se ha encontrado el animal con código $codigo",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }


        // Botón borrar
        binding.btnBorrar.setOnClickListener {
            val codigo = binding.ptCodigo.text.toString().trim()

            val dbHandler = DatabaseHelper(this, null)
            val result = dbHandler.deleteAnimal(codigo)

            if (result != null) {
                Toast.makeText(this, "Registro borrado", Toast.LENGTH_SHORT).show()
                binding.ptCodigo.setText("")
                binding.ptNombre.setText("")
                binding.ptRaza.setText("")
                binding.ptSexo.setText("")
                binding.ptFecNac.setText("")
                binding.ptDNI.setText("")
            } else {
                Snackbar.make(binding.root, "No se encontró el registro", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        // Botón leer
        // Botón consulta
        binding.btnConsul.setOnClickListener {
            // Código para consultar el registro
            val codigo = binding.ptCodigo.text.toString().trim()
            val dbHandler = DatabaseHelper(this, null)
            val cursor = dbHandler.getName()

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val dbCodigo = cursor.getString(cursor.getColumnIndexOrThrow("codigo"))
                    if (dbCodigo == codigo) {
                        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                        val raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                        val sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"))
                        val fecnac = cursor.getString(cursor.getColumnIndexOrThrow("fecnac"))
                        val dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                        binding.ptNombre.setText(nombre)
                        binding.ptRaza.setText(raza)
                        binding.ptSexo.setText(sexo)
                        binding.ptFecNac.setText(fecnac)
                        binding.ptDNI.setText(dni)
                        Snackbar.make(binding.root, "Datos cargados", Snackbar.LENGTH_SHORT).show()
                        break
                    }
                } while (cursor.moveToNext())

                cursor.close()
                dbHandler.close()
            } else {

                Snackbar.make(binding.root, "No se encontraron resultados", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }
        // Botón leer
        // Botón consulta todos
        binding.btnConsulTodo.setOnClickListener {
            val intent = Intent(this, ReciclerViewActivity::class.java)
            // Introduce en el contenedor el dato que pasamos a la otra actividad
            intent.putExtra("Usuario", usuario)
            // Llama a la otra actividad
            startActivity(intent)

        }
    }

}