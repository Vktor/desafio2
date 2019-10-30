package sv.edu.bitlab.desafio.victor


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction


class FormViewFragment : Fragment() {
    var listener: interfaceFormFragment? = null
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        var v = LayoutInflater.from(container?.context).inflate(R.layout.fragment_form_view, container, false)
        val btn_send = v.findViewById<Button>(R.id.button_send)
        btn_send.setOnClickListener {
            Log.i("CLICK","di clic en el boton SEND")
            Toast.makeText(activity,"Este es un mensaje", Toast.LENGTH_SHORT).show()
            listener?.OnFragmentButtonSend()
        }
        return v

    }


interface interfaceFormFragment{
    fun OnFragmentButtonSend()
}
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            listener = context as interfaceFormFragment
        } catch (e: ClassCastException){
            throw ClassCastException(context.toString() + "debes implementar la interface")
        }

    }

}

