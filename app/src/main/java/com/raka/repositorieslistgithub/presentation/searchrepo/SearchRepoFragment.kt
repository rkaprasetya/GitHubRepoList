package com.raka.repositorieslistgithub.presentation.searchrepo

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.phelat.navigationresult.navigateUp
import com.raka.repositorieslistgithub.R
import com.raka.repositorieslistgithub.presentation.common.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search_repo.view.*


class SearchRepoFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel//NOT USED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_repo, container, false).apply {
            sharedViewModel = ViewModelProviders.of(this@SearchRepoFragment).get(SharedViewModel::class.java)//NOT USED
        }
        setSearchListener(view)
        val keyword = arguments?.getString("searchkeyword")
        setSearchKeyword(keyword,view)
        return view
    }

    private fun setSearchKeyword(keyword:String?,v: View){
        v.et_search.setText(keyword?: "")
    }

    private fun setSearchListener(v:View){
        v.et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(tv: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sharedViewModel.searchKeywords.value = tv!!.text.toString().trim()//NOT USED
                    val bundle = Bundle().apply {
                        putString("SEARCH_KEYWORDS", tv!!.text.toString().trim())
                    }
                    navigateUp(10,bundle)
//                    view.findNavController().popBackStack()
                    return true;
                }
                return false;
            }
        })
    }


}
