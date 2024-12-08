package com.example.firstaidfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope
import com.example.firstaidfront.config.AuthConfig
import com.example.firstaidfront.config.TokenManager
import com.example.firstaidfront.data.AuthViewModel
import kotlinx.coroutines.launch


class KeycloakLoginActivity : AppCompatActivity() {
    private val TAG = "KeycloakLogin"
    private lateinit var webView: WebView
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keycloak_login)

        setupWebView()
        loadKeycloakLoginPage()
    }

    private fun setupWebView() {
        webView = findViewById(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { urlString ->
                    if (urlString.startsWith(AuthConfig.REDIRECT_URI)) {
                        val uri = Uri.parse(urlString)
                        val code = uri.getQueryParameter("code")
                        Log.d(TAG, "Redirect detected - Code: $code")

                        if (code != null) {
                            handleAuthCode(code)
                            return true
                        } else {
                            Log.e(TAG, "No code found in redirect URL")
                        }
                    }
                }
                return false
            }
        }
    }

    private fun loadKeycloakLoginPage() {

        val authUrl = StringBuilder().apply {
            append(AuthConfig.AUTH_URI)
            append("?client_id=").append(AuthConfig.CLIENT_ID)
            append("&redirect_uri=").append(AuthConfig.REDIRECT_URI)
            append("&response_type=code")
            append("&scope=").append(AuthConfig.SCOPE)
            append("&state=1234")
        }.toString()


        Log.d(TAG, "Loading auth URL: $authUrl")

        webView.loadUrl(authUrl)
    }

    private fun handleAuthCode(code: String) {
        Log.d(TAG, "Handling auth code: $code")
        lifecycleScope.launch {
            try {
                Log.d(TAG, "Starting token exchange")
                val authResponse = viewModel.handleAuthCode(code)
                Log.d(TAG, "Token exchange successful - User ID: ${authResponse.userId}")

                TokenManager.saveAuthData(this@KeycloakLoginActivity, authResponse)
                Log.d(TAG, "Auth data saved to preferences")

                startActivity(Intent(this@KeycloakLoginActivity, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
                Log.e(TAG, "Authentication failed", e)
                val errorMessage = when (e) {
                    is retrofit2.HttpException -> "Server error: ${e.code()}"
                    is java.net.SocketTimeoutException -> "Connection timeout"
                    is java.net.UnknownHostException -> "No network connection"
                    else -> "Authentication failed: ${e.message}"
                }

                Toast.makeText(
                    this@KeycloakLoginActivity,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}