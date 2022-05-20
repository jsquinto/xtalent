package co.edu.xtalent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class PaginaLogin : AppCompatActivity() {
    private var botonAutenticar: Button? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var recuperarContrasena: TextView
    private lateinit var registrarUsuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_login)
        mAuth = FirebaseAuth.getInstance()
        botonAutenticar = findViewById(R.id.botonAutenticar)
        recuperarContrasena = findViewById(R.id.recuperarContrasena)
        registrarUsuario = findViewById(R.id.registrarUsuario)

        val editUsuario: EditText = findViewById(R.id.usuario)
        val editContrasena: EditText = findViewById(R.id.contrasena)

        val emailRecibir=intent.extras?.get("email")

        if(emailRecibir.toString()!="null") {
            val mensaje = "" + emailRecibir
            editUsuario.setText(mensaje)
        }

        botonAutenticar?.setOnClickListener {
            if(editUsuario.text.toString()=="" || editContrasena.text.toString()=="") {
                Toast.makeText(this, "Diligencia los campos", Toast.LENGTH_SHORT)
                    .show()
            }else{
                mAuth!!.signInWithEmailAndPassword(
                    editUsuario.text.toString(),
                    editContrasena.text.toString()
                ).addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        Toast.makeText(this, "Usuario autenticado con éxito", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(
                            Intent(this, PaginaInicio::class.java).putExtra(
                                "email",
                                user?.email
                            )
                        )
                    } else {
                        Log.e("Autenticacion", "Fallo en autenticación", task.exception)
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        recuperarContrasena.setOnClickListener{
            startActivity(Intent(this, PaginaRecuperacion::class.java))
        }

        registrarUsuario.setOnClickListener{
            startActivity(Intent(this, PaginaRegistro::class.java))
        }
    }
}