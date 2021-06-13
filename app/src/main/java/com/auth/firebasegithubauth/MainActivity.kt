package com.auth.firebasegithubauth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var loginBtn: Button
    private lateinit var githubEdit: EditText

    // firebaseAuth variable to be initialized later
    private lateinit var auth: FirebaseAuth

    //an instance of an OAuthProvider using its Builder with the provider ID github.com
    private val provider = OAuthProvider.newBuilder("github.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn = findViewById(R.id.github_login_btn)
        githubEdit = findViewById(R.id.githubId)
        auth = FirebaseAuth.getInstance() //initializing auth

        // Target specific email with login hint.
        provider.addCustomParameter("login", githubEdit.text.toString())

        // Request read access to a user's email addresses.
        // This must be preconfigured in the app's API permissions.
        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes

        //call signInWithGithubProvider() method after clicking login Button
        loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(githubEdit.text.toString())) {
                Toast.makeText(this, "Enter your github id", Toast.LENGTH_LONG).show()
            } else {
                signInWithGithubProvider()
            }
        }
    }

    //To check if there is a pending result, call pendingAuthResult

    private fun signInWithGithubProvider() {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener {
                        // User is signed in.
                        Toast.makeText(this, "User exist", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        // Handle failure.
                        Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                    }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */this, provider.build())
                    .addOnSuccessListener(
                            OnSuccessListener<AuthResult?> {
                                // User is signed in.
                                //retrieve the current user
                                firebaseUser = auth.currentUser!!
                                val user : Map<String,Any> = it.additionalUserInfo!!.profile as Map<String, Any>
                                //navigate to HomePageActivity after successful login
                                val intent = Intent(this, HomePageActivity::class.java)
                                //send github user name from MainActivity to HomePageActivity
                                intent.putExtra("githubUserName", firebaseUser.displayName)
                                intent.putExtra("twitterHandle",user["twitter_username"].toString())
                                intent.putExtra("githubBio",user["bio"].toString())
                                intent.putExtra("linkedInLink",user["blog"].toString())
                                intent.putExtra("id",user["login"].toString())
                                intent.putExtra("follower",user["followers"].toString())
                                intent.putExtra("following",user["following"].toString())
                                intent.putExtra("publicRepos",user["public_repos"].toString())
                                intent.putExtra("location",user["location"].toString())
                                intent.putExtra("profilePic",user["avatar_url"].toString())
                                this.startActivity(intent)
                                Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()

                            })
                    .addOnFailureListener(
                            OnFailureListener {
                                // Handle failure.
                                Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                            })
        }

    }
}