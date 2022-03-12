package com.kradwan.codegeneartormvvmsample.presentation._base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<VB : ViewBinding> : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = _bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

    }

    abstract fun setup()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private var _binding: ViewBinding? = null
    abstract val _bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB


}
