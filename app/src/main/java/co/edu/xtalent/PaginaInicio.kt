package co.edu.xtalent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class PaginaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_inicio)
        val mensaje:TextView = findViewById(R.id.mensaje)
        val emailRecibir=intent.extras?.get("email")

        val palabra = "$emailRecibir"
        mensaje.text = palabra
        Log.e("error","error")
        val instanciaFirestore= FirebaseFirestore.getInstance()
        instanciaFirestore.collection("persona").add(mapOf("nombre" to "camilo","edad" to 20)).addOnSuccessListener {
            Log.e("error","error1") term
            instanciaFirestore.collection("persona").document(it.id).update(mapOf("IdDoc" to it.id))
        }.addOnFailureListener{
            Log.e("error","error2")
            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        }

        instanciaFirestore.collection("persona").whereEqualTo("nombre","camilo").get().addOnSuccessListener {
            it.documents.forEach{
                Log.i("items", it.data.toString())
                Log.i("data_ejemplo",it?.data?.get("edad").toString())
            }
        }

    }
}



