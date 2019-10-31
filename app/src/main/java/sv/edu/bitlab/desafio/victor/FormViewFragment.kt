package sv.edu.bitlab.desafio.victor


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import sv.edu.bitlab.desafio.victor.data.Account


class FormViewFragment : Fragment() {
    var listener: InterfaceFormFragment? = null
    //From variable
    var AccountName: EditText? = null
    var AccountEmail: EditText? = null
    var AccountPhone: EditText? = null
    var AccountFoundBy: Spinner? = null
    var btn_send: Button? = null
    var txt_Colleccion: TextView? = null
    var v: View? = null
    //FIREBASE
    var db = FirebaseFirestore.getInstance()

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
          v = LayoutInflater.from(container?.context).inflate(R.layout.fragment_form_view, container, false)
          //Button and text clickleable
          btn_send = v?.findViewById<Button>(R.id.button_send)
          txt_Colleccion = v?.findViewById<TextView>(R.id.txtVerColeccion)
          //Form Data
          AccountName = v?.findViewById<EditText>(R.id.input_name)
          AccountEmail = v?.findViewById<EditText>(R.id.input_email)
          AccountPhone = v?.findViewById<EditText>(R.id.input_phone)
          AccountFoundBy = v?.findViewById(R.id.spinner_hownotice)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_Colleccion?.setOnClickListener {
            Toast.makeText(activity,"Agregado a la coleccion", Toast.LENGTH_SHORT).show()
            listener?.onFragmentButtonSend()
        }
        btn_send?.setOnClickListener {
            if(AccountName?.text.toString().isEmpty() || AccountEmail?.text.toString().isEmpty()){
                Toast.makeText(activity,"No olvide completar los campos obligatorios", Toast.LENGTH_SHORT).show()
            }else{
                val userAccount:Account = Account(AccountName?.text.toString(), AccountEmail?.text.toString(), AccountPhone?.text.toString(), AccountFoundBy?.getSelectedItem().toString(), "example")
                Log.i("CLICK","el AccountName es: ${AccountName?.text.toString()}, AccountEmail: ${AccountEmail?.text.toString()}, AccountFoundBy: ${AccountFoundBy?.getSelectedItem().toString()} ")
                //sendData(userAccount)
            }
        }
    }

    fun sendData(data:Account){
        db.collection("accounts")
            .add(data)
            .addOnSuccessListener{documentReference ->
                Log.d("TAG", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener{e ->
                Log.w("TAG", "Error adding document", e)
                }
            }

interface InterfaceFormFragment{
    fun onFragmentButtonSend()
}
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as InterfaceFormFragment
        } catch (e: ClassCastException){
            throw ClassCastException(context.toString() + "debes implementar la interface")
        }

    }

}

