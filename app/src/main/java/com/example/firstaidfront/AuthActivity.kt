package com.example.firstaidfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.firstaidfront.auth.AuthManager
import com.example.firstaidfront.config.TokenManager
import com.example.firstaidfront.data.AuthViewModel
import kotlinx.coroutines.launch
import android.webkit.CookieManager

class AuthActivity : AppCompatActivity() {
    private val TAG = "AuthActivity"
    private lateinit var authManager: AuthManager
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        progressBar = findViewById(R.id.progressBar)
        authManager = AuthManager(this)

        // Enhanced logging for onCreate
        Log.d(TAG, "onCreate called")
        Log.d(TAG, "Intent action: ${intent?.action}")
        Log.d(TAG, "Intent data: ${intent?.data}")
        Log.d(TAG, "Intent scheme: ${intent?.data?.scheme}")
        Log.d(TAG, "Intent path: ${intent?.data?.path}")
        Log.d(TAG, "SavedInstanceState: $savedInstanceState")

        when {
            intent?.action == Intent.ACTION_VIEW && intent.data != null -> {
                Log.d(TAG, "Handling redirect from auth server")
                handleAuthResult(intent)
            }
            savedInstanceState == null -> {
                Log.d(TAG, "Starting new auth flow")
                startAuth()
            }
        }
    }

    private fun startAuth() {
        try {
            val authUrl = authManager.getAuthUrl()
            Log.d(TAG, "Starting auth with URL: $authUrl")

            val customTabsIntent = CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.primary))
                .setShowTitle(true)
                .build()

            // Modified flags
            customTabsIntent.intent.apply {
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

            // Add logging before launch

            Log.d(TAG, "Package name: ${packageName}")

            customTabsIntent.launchUrl(this, Uri.parse(authUrl))
            Log.d(TAG, "Successfully launched auth URL")
        } catch (e: Exception) {
            Log.e(TAG, "Auth start failed", e)
            showError("Auth start failed: ${e.message}")
        }
    }

    private fun handleAuthResult(intent: Intent) {
        val uri = intent.data
        Log.d(TAG, "Handling redirect URI: $uri")
        Log.d(TAG, "URI scheme: ${uri?.scheme}")
        Log.d(TAG, "URI host: ${uri?.host}")
        Log.d(TAG, "URI path: ${uri?.path}")
        Log.d(TAG, "URI query: ${uri?.query}")

        if (uri?.scheme == "com.firstaid.app") {
            Log.d(TAG, "Valid scheme detected")
            showLoading()

            lifecycleScope.launch {
                try {
                    Log.d(TAG, "Beginning auth code extraction")
                    val authCode = authManager.extractAuthCode(intent)
                    Log.d(TAG, "Extracted auth code: ${authCode?.take(5)}...")

                    if (authCode != null) {
                        Log.d(TAG, "Starting token exchange")
                        val authResponse = viewModel.handleAuthCode(authCode)
                        Log.d(TAG, "Token exchange successful: ${authResponse.accessToken.take(10)}...")

                        Log.d(TAG, "Saving auth data")
                        TokenManager.saveAuthData(this@AuthActivity, authResponse)

                        Log.d(TAG, "Clearing browser data")
                        clearBrowserData()

                        Log.d(TAG, "Starting main activity")
                        startMainActivity()
                    } else {
                        Log.e(TAG, "Auth code is null")
                        throw IllegalStateException("Auth code is null")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Auth failed", e)
                    hideLoading()
                    showError("Authentication failed: ${e.message}")
                }
            }
        } else {
            Log.e(TAG, "Invalid redirect URI - scheme: ${uri?.scheme}")
            showError("Invalid redirect URI")
        }
    }

    private fun clearBrowserData() {
        try {
            Log.d(TAG, "Clearing browser cookies")
            CookieManager.getInstance().apply {
                removeAllCookies(null)
                flush()
            }

            Log.d(TAG, "Clearing WebView data")
            android.webkit.WebStorage.getInstance().deleteAllData()
            Log.d(TAG, "Successfully cleared browser data")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to clear browser data", e)
        }
    }

    private fun startMainActivity() {
        Log.d(TAG, "Starting MainActivity")
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun showError(message: String) {
        Log.e(TAG, "Showing error: $message")
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        Log.d(TAG, "Showing loading indicator")
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        Log.d(TAG, "Hiding loading indicator")
        progressBar.visibility = View.GONE
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent called")
        Log.d(TAG, "Intent action: ${intent?.action}")
        Log.d(TAG, "Intent data: ${intent?.data}")
        Log.d(TAG, "Intent scheme: ${intent?.data?.scheme}")
        Log.d(TAG, "Intent path: ${intent?.data?.path}")
        Log.d(TAG, "Intent query: ${intent?.data?.query}")

        setIntent(intent)

        intent?.let {
            if (it.action == Intent.ACTION_VIEW) {
                Log.d(TAG, "Processing VIEW action in onNewIntent")
                handleAuthResult(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
        authManager.dispose()
    }
}