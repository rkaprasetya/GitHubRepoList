package com.raka.repositorieslistgithub.presentation.repolist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raka.repositorieslistgithub.BR
import com.raka.repositorieslistgithub.data.model.compact.RepoCompact
import com.raka.repositorieslistgithub.data.model.response.Item
import com.raka.repositorieslistgithub.databinding.ItemRepoListBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo_list.view.*

class RepoListAdapter(private val viewModel: RepoListViewModel) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {
    var repoList: List<RepoCompact> = emptyList()
//    internal var mFilter : NewFilter
//    init {
//        mFilter = NewFilter(this@RepoListAdapter,repoList)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemRepoListBinding.inflate(inflater,parent,false)
        return ViewHolder(dataBinding)
    }

    override fun getItemCount() = repoList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(repoList[position])
    }

    fun updateList(repoList:List<RepoCompact>){
        this.repoList = repoList
        notifyDataSetChanged()
    }

    class ViewHolder constructor(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        val avatarImage = itemView.item_avatar
        fun setup(itemData: RepoCompact) {
            dataBinding.setVariable(BR.itemData,itemData)
            dataBinding.executePendingBindings()
            Picasso.get().load(itemData.avatar_url).into(avatarImage)
            itemView.setOnClickListener {
                val bundle = bundleOf("url" to itemData.html_url)
//                itemView.findNavController().navigate()
            }
        }
    }

//    override fun getFilter() = mFilter

//    inner class NewFilter(private val mAdapter:RepoListAdapter,private val list:List<RepoCompact> ):Filter(){
//        override fun performFiltering(charSequence: CharSequence?): FilterResults {
//            val results = FilterResults()
//            var listFiltered : MutableList<RepoCompact> = mutableListOf()
//            if(charSequence!!.length == 0){
//                listFiltered = completeList as MutableList<RepoCompact>
//            }else{
//                charSequence.toString().toLowerCase().trim().let {
//                    Log.e("charseq","charseq $it --> $list")
//                    for (item in list )
//                        Log.e("item","item desc ${item.description.toLowerCase()}")
////                        if (item.description.toLowerCase().contains(it)){
////                            listFiltered.add(item)
////                        }
//
//                }
//            }
//            results.values = listFiltered
//            results.count = listFiltered.size
//            Log.e("listFiletered","= ${listFiltered}")
////            updateList(listFiltered)
//            return results
//        }
//
//        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//            mAdapter.notifyDataSetChanged()
//        }
//
//    }

}