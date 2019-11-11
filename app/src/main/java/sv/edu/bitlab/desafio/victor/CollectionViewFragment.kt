package sv.edu.bitlab.desafio.victor

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import sv.edu.bitlab.desafio.victor.Recycler.AdapterCollectionView
import sv.edu.bitlab.desafio.victor.data.Account

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CollectionViewFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class CollectionViewFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    val db = FirebaseFirestore.getInstance()
    var collectRef: CollectionReference = db.collection("accounts")
    var adapter: AdapterCollectionView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_view, container, false)
    }


    fun setUpRecyclerView(){
        var query: Query = collectRef.orderBy("accountName", Query.Direction.DESCENDING)
        var options: FirestoreRecyclerOptions<Account> = FirestoreRecyclerOptions.Builder<Account>()
            .setQuery(query,Account::class.java)
            .build()
        adapter = AdapterCollectionView(options)
        var recycler = view?.findViewById<RecyclerView>(R.id.recyclerView_collection)
        recycler!!.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter=adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv_new = view.findViewById<TextView>(R.id.tv_back)
        tv_new.setOnClickListener {
            back()
        }
        setUpRecyclerView()

    }

    override fun onStart(){
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop(){
        super.onStop()
        adapter!!.startListening()
    }

    fun back(){
        listener!!.back(FormViewFragment())
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and accountName
        fun onFragmentInteraction()
        fun back(bk:FormViewFragment)
    }
companion object{
    @JvmStatic
    fun newInstance(): CollectionViewFragment{
        val params = Bundle()
        val fragment = CollectionViewFragment()
        fragment.arguments = params
        return fragment
    }
}
}

