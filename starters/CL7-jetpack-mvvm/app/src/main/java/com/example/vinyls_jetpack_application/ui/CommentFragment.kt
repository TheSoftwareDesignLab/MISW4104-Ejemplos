package com.example.vinyls_jetpack_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vinyls_jetpack_application.R

class CommentFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_comments)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.actionBar?.title = getString(R.string.title_comments)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun onNetworkError() {
    }

}