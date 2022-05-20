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

class PaginaRecuperacion : AppCompatActivity() {
    private var botonRecuperar: Button? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var cancelar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_recuperacion)
        mAuth = FirebaseAuth.getInstance()
        botonRecuperar = findViewById(R.id.botonRecuperar)
        cancelar = findViewById(R.id.cancelar)

        val editUsuario: EditText = findViewById(R.id.usuario)

        botonRecuperar?.setOnClickListener {
            if(editUsuario.text.toString()=="") {
                Toast.makeText(this, "Diligencia el campo", Toast.LENGTH_SHORT)
                    .show()
            }else{
                mAuth!!.sendPasswordResetEmail(
                    editUsuario.text.toString(),
                ).addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        Toast.makeText(this, "Correo enviado con éxito", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(
                            Intent(this, PaginaLogin::class.java).putExtra(
                                "email",
                                user?.email
                            )
                        )
                    } else {
                        Log.e("Autenticacion", "Fallo en autenticación", task.exception)
                        Toast.makeText(this, "No se encontró un usuario identificado con este correo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        cancelar.setOnClickListener{
            startActivity(Intent(this, PaginaLogin::class.java))
        }
    }
}