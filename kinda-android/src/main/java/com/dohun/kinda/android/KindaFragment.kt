package com.dohun.kinda.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dohun.kinda.core.KindaEvent
import com.dohun.kinda.core.KindaSideEffect
import com.dohun.kinda.core.KindaState
import com.dohun.kinda.core.KindaViewEffect

abstract class KindaFragment<S : KindaState, E : KindaEvent, SE : KindaSideEffect, VE : KindaViewEffect, VIEW : ViewDataBinding> :
    Fragment() {

    abstract val layoutResourceId: Int
    abstract val viewModel: KindaViewModel<S, E, SE, VE>

    open fun onStateChanged(state: S) {
    }

    open fun onViewEffect(viewEffect: VE) {
    }

    lateinit var binding: VIEW

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = this

        viewModel.currentState.observe(this, Observer {
            onStateChanged(it)
        })

        viewModel.viewEffect.observe(this, Observer {
            it?.let { onViewEffect(it) }
        })

        return binding.root
    }
}