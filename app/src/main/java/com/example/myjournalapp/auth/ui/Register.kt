package com.example.myjournalapp.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myjournalapp.databinding.FragmentRegisterBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Register : Fragment() {

    private lateinit var registerBinding: FragmentRegisterBinding
//    private  val re


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return registerBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        }
    }

