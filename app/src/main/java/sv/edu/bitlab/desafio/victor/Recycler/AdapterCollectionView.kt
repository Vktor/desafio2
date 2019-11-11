package sv.edu.bitlab.desafio.victor.Recycler

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.extensions.LayoutContainer
import sv.edu.bitlab.desafio.victor.R
import sv.edu.bitlab.desafio.victor.data.Account

class AdapterCollectionView(options:FirestoreRecyclerOptions<Account>):
    FirestoreRecyclerAdapter<Account, AdapterCollectionView.CollectionHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return CollectionHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionHolder, position: Int, model: Account) {
        holder.apply {
            name.text = model.accountName
            email.text = model.accountEmail
            phone.text = model.accountPhone
            found.text = model.accountFoundOutBy
            //imageGlide(image, model.accountImage)
        }
    }


    inner class CollectionHolder(override val containerView: View):RecyclerView.ViewHolder(containerView), LayoutContainer{
        var name = containerView.findViewById<TextView>(R.id.tv_nombre_item)
        var email = containerView.findViewById<TextView>(R.id.tv_email_item)
        var phone = containerView.findViewById<TextView>(R.id.tv_item_phone)
        var found = containerView.findViewById<TextView>(R.id.tv_howiknow_item)
        //var image = containerView.findViewById<ImageView>(R.id.iv_item)

    }

}