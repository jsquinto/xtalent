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

class PaginaRegistro : AppCompatActivity() {
    private var botonAutenticar: Button? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var paginaLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_registro)
        mAuth = FirebaseAuth.getInstance()
        botonAutenticar = findViewById(R.id.btnRegistrarse)
        paginaLogin = findViewById(R.id.paginaLogin)

        val editUsuario: EditText = findViewById(R.id.txtEmail)
        val editContrasena: EditText = findViewById(R.id.contrasena)
        botonAutenticar?.setOnClickListener {
            if(editUsuario.text.toString()=="" || editContrasena.text.toString()=="") {
                Toast.makeText(this, "Diligencia los campos", Toast.LENGTH_SHORT)
                    .show()
            }else{
                mAuth!!.createUserWithEmailAndPassword(
                    editUsuario.text.toString(),
                    editContrasena.text.toString()
                ).addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(
                            Intent(this, PaginaLogin::class.java).putExtra(
                                "email",
                                user?.email
                            )
                        )
                    } else {
                        Log.e("Autenticacion", "Fallo en autenticación", task.exception)
                        Toast.makeText(this, "La dirección de correo ya existe", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        paginaLogin.setOnClickListener{
            startActivity(Intent(this, PaginaLogin::class.java))
        }
    }
}