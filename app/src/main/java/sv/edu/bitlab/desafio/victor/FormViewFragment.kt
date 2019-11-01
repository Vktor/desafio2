package sv.edu.bitlab.desafio.victor


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.Constraints
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import sv.edu.bitlab.desafio.victor.data.Account
import com.google.firebase.storage.StorageReference




class FormViewFragment : Fragment() {
    var listener: InterfaceFormFragment? = null
    //From variable
    var accountName: EditText? = null
    var accountEmail: EditText? = null
    var accountPhone: EditText? = null
    var accountFoundBy: Spinner? = null
    var btn_send: Button? = null
    var txt_Colleccion: TextView? = null
    var success_view: View? =null
    var form_view: View? = null
    var v: View? = null
    //FIREBASE
    private var db = FirebaseFirestore.getInstance()
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference

    val imageRef = mStorageRef.child("accounts-image/avatar_victor.png")
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
          v = LayoutInflater.from(container?.context).inflate(R.layout.fragment_form_view, container, false)
          //Button and text clickleable
          btn_send = v?.findViewById<Button>(R.id.button_send)
          txt_Colleccion = v?.findViewById<TextView>(R.id.txtVerColeccion)
          //Form Data
          accountName = v?.findViewById<EditText>(R.id.input_name)
          accountEmail = v?.findViewById<EditText>(R.id.input_email)
          accountPhone = v?.findViewById<EditText>(R.id.input_phone)
          accountFoundBy = v?.findViewById(R.id.spinner_hownotice)
          success_view = v?.findViewById(R.id.success_view)
          //FIREBASE STORAGE
          mStorageRef = FirebaseStorage.getInstance().getReference();
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_Colleccion?.setOnClickListener {
            Toast.makeText(activity,"Agregado a la coleccion", Toast.LENGTH_SHORT).show()
            listener?.onFragmentButtonSend()
        }
        btn_send?.setOnClickListener {
            if(accountName?.text.toString().isEmpty() || accountEmail?.text.toString().isEmpty()){
                Toast.makeText(activity,"No olvide completar los campos obligatorios", Toast.LENGTH_SHORT).show()
            }else{
                //UPLOAD IMAGE
                var image:Uri = Uri.parse("android.resource://"+ context?.packageName+"/drawable/avatar")
                imageRef.putFile(image).addOnSuccessListener {
                        imageRef.downloadUrl.addOnCompleteListener{task ->
                            var url = task.result
                            val userAccount:Account = Account(
                                accountName?.text.toString(),
                                accountEmail?.text.toString(),
                                accountPhone?.text.toString(),
                                accountFoundBy?.getSelectedItem().toString(),
                                url.toString()
                            )
                            //Log.i("CLICK","el accountName es: ${accountName?.text.toString()}, URL DE LA IMAGEN: ${url.toString()}, accountFoundBy: ${accountFoundBy?.getSelectedItem().toString()} ")
                            sendData(userAccount)
                            success_view?.visibility = View.VISIBLE
                            Handler().postDelayed({
                               listener?.onFragmentButtonSend()
                                success_view?.visibility = View.GONE
                            },3000)
                        }
                }

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

