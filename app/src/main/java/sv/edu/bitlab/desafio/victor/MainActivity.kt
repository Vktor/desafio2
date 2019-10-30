package sv.edu.bitlab.desafio.victor

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), FormViewFragment.interfaceFormFragment, CollectionViewFragment.OnFragmentInteractionListener {
    val fragM: FragmentTransaction = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadForm(FormViewFragment())
    }


    //call first Fragment(FormFragment)
    private fun loadForm(fr:FormViewFragment){
        fragM.replace(R.id.fragment_container, fr)
        fragM.commit()
    }
    override fun OnFragmentButtonSend() {
        var fragMan: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = CollectionViewFragment.newInstance()
        Log.i("CLICK2","Existo en el Activity")
        fragMan.replace(R.id.fragment_container, fragment, "TAG2")
            .addToBackStack("TAG2")
        fragMan.commit()
    }
    override fun onFragmentInteraction() {
        val fragment = CollectionViewFragment.newInstance()
        fragM.replace(R.id.fragment_container, fragment, "TAG")
            .addToBackStack("TAG")
        fragM.commitAllowingStateLoss()
    }
}
