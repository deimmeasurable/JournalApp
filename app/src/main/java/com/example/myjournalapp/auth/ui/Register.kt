package com.example.myjournalapp.auth.ui


import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myjournalapp.auth.Event.AuthEvent
import com.example.myjournalapp.auth.viewmodels.RegisterViewModel
import com.example.myjournalapp.data.model.RegistrationRequest
import com.example.myjournalapp.data.model.Status
import com.example.myjournalapp.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Register : Fragment() {

    init {
        val threadInfo = Thread.currentThread().name
        Log.i("register frag", "running on thread $threadInfo")
    }

    private lateinit var registerBinding: FragmentRegisterBinding
    private  val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return registerBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = registerBinding.registerBtn
        val loginLink =registerBinding.loginLink
        val navController = findNavController()
        loginLink.setOnClickListener{
            val action = RegisterDirections.actionRegisterToLogin()
            navController.navigate(action)
        }
        registerViewModel.isFormValid.observe(viewLifecycleOwner) { isValidOptional ->
            Log.i("register", "is from valid value changed")
            isValidOptional?.let { isValid ->
                Log.i("register frag", "is from valid is $isValid")
                if (!isValid) {
                    Log.i("register  frag", "displaying snackbar")
                    val snackbar = Snackbar.make(
                        registerBinding.root,
                        "please complete all fields",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
            }
        }

        registerViewModel.status.observe(viewLifecycleOwner) {
            Log.i("regsiter frag", "status value changed")
            it?.let {
                if (it.status == Status.LOADING) {
                    Log.i("register frag", "status changed to loading")
                    btn.text = "Loading ..."
                }
                if (it.status == Status.ERROR) {
                    Log.i("register frag", "status changed to error")
                    btn.text = "Register"
                    val snackbar = Snackbar.make(
                        registerBinding.root,
                        "Registration Success",
                        Snackbar.LENGTH_SHORT
                    )
                    Log.i("register frag", "navigating to login")
                    snackbar.setAction("Dismiss") {
                        val action = RegisterDirections.actionRegisterToLogin()
                        navController.navigate(action)
                    }
                    snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                val action = RegisterDirections.actionRegisterToLogin()
                                navController.navigate(action)
                            }

                        }
                    })
                    snackbar.show()
                }
            }
        }
        btn.setOnClickListener{
            val  firstName = registerBinding.firstNameInput.text.toString()
            val lastName = registerBinding.lastNameInput.text.toString()
            val email = registerBinding.emailInput.text.toString()
            val password= registerBinding.passwordInput.text.toString()
            val request = RegistrationRequest(firstName,lastName,email,password)
            val event = AuthEvent.RegistrationEvent(request)
            Log.i("register-event",event.toString())
            registerViewModel.onEvent(event)
        }

        }
    }

