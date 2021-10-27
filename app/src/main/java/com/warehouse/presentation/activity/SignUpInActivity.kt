package com.warehouse.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.warehouse.domain.LoginViewModel
import com.warehouse.domain.LoginViewModelFactory
import com.warehouse.domain.SignupViewModel
import com.warehouse.domain.SignupViewModelFactory
import com.warehouse.presentation.screens.LoginScreen
import com.warehouse.presentation.screens.SighupScreen
import com.warehouse.repository.RequestsApplication
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import android.content.Intent
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.warehouse.presentation.screens.LoadProgress
import com.warehouse.presentation.screens.RunProgress
import com.warehouse.repository.database.entity.UserDTO
import java.lang.Thread.sleep


class SignUpInActivity : AppCompatActivity() {

    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory((application as RequestsApplication).repository)
    }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as RequestsApplication).repository)
    }

    private var RC_SIGN_IN = 0

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fullUri = intent.data
        var start = "Login"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setContent {
            val navController = rememberAnimatedNavController()
            fullUri?.let {
                start = "App"
            }
            AnimatedNavHost(navController = navController, startDestination = start) {
                composable("Login", exitTransition = { _, target ->
                    if (target.destination.hierarchy.any { it.route == "Login" }) {
                        slideOutHorizontally(targetOffsetX = { -1000 })
                    } else {
                        null
                    }
                }) { LoginScreen(navController, loginViewModel, mGoogleSignInClient, RC_SIGN_IN) }
                composable("Signup") { SighupScreen(navController, signupViewModel) }
                // add router for sharing
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            setContent{
                RunProgress(loginViewModel)
            }
            account?.let {
                val user = UserDTO(fullname = it.displayName!!, email = it.email!!, password = null, role = "single_user")
                loginViewModel.insertUser(user)
                sleep(2000)
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("email", user.email)
                }
                startActivity(intent)
            }

            Log.d("Yep", "Working")
        } catch (e: ApiException) {
            Log.w("", "signInResult:failed code=" + e.statusCode)
        }
    }

}