package com.androidhuman.example.simplegithub.ui.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.androidhuman.example.simplegithub.BuildConfig
import com.androidhuman.example.simplegithub.R
import com.androidhuman.example.simplegithub.api.provideAuthApi
import com.androidhuman.example.simplegithub.data.AuthTokenProvider
import com.androidhuman.example.simplegithub.extensions.plusAssign
import com.androidhuman.example.simplegithub.rx.AutoClearedDisposable
import com.androidhuman.example.simplegithub.ui.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.newTask

class SignInActivity : AppCompatActivity() {


    internal val api by lazy { provideAuthApi() }

    internal val authTokenProvider by lazy { AuthTokenProvider(this) }

    internal val disposable = AutoClearedDisposable(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        lifecycle += disposable
        btnActivitySignInStart.setOnClickListener {
            val authUri = Uri.Builder().scheme("https").authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
                    .build()

            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(this@SignInActivity, authUri)
        }

        if (null != authTokenProvider.token) {
            launchMainActivity()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        showProgress()

        val code = intent.data?.getQueryParameter("code")
                ?: throw IllegalStateException("No code exists")

        getAccessToken(code)
    }

//    override fun onStop() {
//        super.onStop()
////        accessTokenCall?.run { cancel() }
//        // 관리하고 있던 모든 객체를 해제
//        disposable.clear()
//    }

    private fun getAccessToken(code: String) {
        // disposable에 등록
        disposable += api.getAccessToken(BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET, code)
                // 엑세스 토큰으로 변경
                .map { it.accessToken }
                // 이후작업은 메인 스레드에서 처리
                .observeOn(AndroidSchedulers.mainThread())
                // 구독시작시
                .doOnSubscribe { showProgress() }
                // 스트림 종료시
                .doOnTerminate { hideProgress() }
                // 옵서버블 구독
                .subscribe({ token ->
                    authTokenProvider.updateToken(token)
                    launchMainActivity()
                }) {
                    showError(it)
                }

//        showProgress()
//
//        accessTokenCall = api.getAccessToken(
//                BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET, code)
//
//        accessTokenCall!!.enqueue(object : Callback<GithubAccessToken> {
//            override fun onResponse(call: Call<GithubAccessToken>,
//                    response: Response<GithubAccessToken>) {
//                hideProgress()
//
//                val token = response.body()
//                if (response.isSuccessful && null != token) {
//                    authTokenProvider.updateToken(token.accessToken)
//
//                    launchMainActivity()
//                } else {
//                    showError(IllegalStateException(
//                            "Not successful: " + response.message()))
//                }
//            }
//
//            override fun onFailure(call: Call<GithubAccessToken>, t: Throwable) {
//                hideProgress()
//                showError(t)
//            }
//        })
    }

    private fun showProgress() {
        btnActivitySignInStart.visibility = View.GONE
        pbActivitySignIn.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        btnActivitySignInStart.visibility = View.VISIBLE
        pbActivitySignIn.visibility = View.GONE
    }

    private fun showError(throwable: Throwable) {
        longToast(throwable.message ?: "No message available")
    }

    private fun launchMainActivity() {
        startActivity(intentFor<MainActivity>().clearTask().newTask())
    }
}
