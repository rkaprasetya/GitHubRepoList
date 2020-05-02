package com.raka.repositorieslistgithub.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.raka.repositorieslistgithub.presentation.common.SharedViewModel

abstract class BaseFragment : Fragment() {
    protected var rootView : View? = null
    private var sharedViewModel : SharedViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(),container,false)
            initSharedViewModel()
        }
        return rootView
    }
    abstract fun getLayoutId():Int

    private fun initSharedViewModel(){
        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

    }

    open fun onFragmentResult(data:String){

    }
    protected fun startFragmentForResult(actionId: Int){
        findNavController(this).navigate(actionId)
    }

    protected fun popBackStackWithResult(result: String){
        findNavController(this).popBackStack()
        sharedViewModel?.share(result)
    }
}
