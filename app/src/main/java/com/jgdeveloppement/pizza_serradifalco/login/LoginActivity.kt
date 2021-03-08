package com.jgdeveloppement.pizza_serradifalco.login

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityLoginBinding
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.networking.ConnexionInternet
import com.jgdeveloppement.pizza_serradifalco.utils.Constantes
import com.jgdeveloppement.pizza_serradifalco.utils.Constantes.RC_SIGN_IN

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        onClickEmailLoginButton()
        onClickGoogleLoginButton()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        rooting()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginLayout(true)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                HomeActivity.navigate(this)
            }else{
                when (response?.error?.errorCode) {
                    ErrorCodes.NO_NETWORK -> showSnackBar(binding.loginLayout, getString(R.string.error_no_internet))
                    ErrorCodes.UNKNOWN_ERROR -> showSnackBar(binding.loginLayout, getString(R.string.error_unknown_error))
                    else -> showSnackBar(binding.loginLayout, getString(R.string.error_authentication_canceled))
                }
            }

        }else{
            showSnackBar(binding.loginLayout, getString(R.string.error_authentication_canceled))
        }
    }

    /** Rooting  */
    private fun rooting() {
        loginLayout(false)
        Handler().postDelayed({
            ConnexionInternet.isConnected { ok ->
                if (ok){
                   if (FirebaseAuth.getInstance().currentUser != null) HomeActivity.navigate(this)
                   else loginLayout(true)
                }
                else{
                    loginLayout(true)
                    showSnackBar(binding.loginLayout, getString(R.string.error_no_internet))
                }
            }
        }, (3 * 1000).toLong())
    }

    /** Login with Google  */
    private fun onClickGoogleLoginButton(){
        binding.loginGoogleButton.setOnClickListener {
            startSignInActivity(AuthUI.IdpConfig.GoogleBuilder().build())
        }
    }

    /** Login with Mail  */
    private fun onClickEmailLoginButton(){
        binding.loginEmailButton.setOnClickListener {
            startSignInActivity(AuthUI.IdpConfig.EmailBuilder().build())
        }
    }

    /** Login with FirebaseUI  */
    private fun startSignInActivity(authUI: AuthUI.IdpConfig) {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(authUI))
                .setIsSmartLockEnabled(false, true)
                .build(),
            RC_SIGN_IN
        )
        loginLayout(false)
    }

    private fun loginLayout(visibility: Boolean){
        if (visibility) binding.loginProgressLayout.visibility = View.GONE
        else binding.loginProgressLayout.visibility = View.VISIBLE
    }

    private fun showSnackBar(view: View?, message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, LoginActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
            activity.finish()
        }
    }
}
