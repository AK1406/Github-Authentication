package com.auth.firebasegithubauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class HomePageActivity : AppCompatActivity() {

    private var userName = ""
    private var twitterHandle = " "
    private var bio = " "
    private var linkedIn = " "
    private var githubId = " "
    private var follower = " "
    private var following = " "
    private var repos = " "
    private var picUrl = " "

    private lateinit var githubUserName: TextView
    private lateinit var logoutBtn: Button
    private lateinit var handle : TextView
    private lateinit var githubBio : TextView
    private lateinit var linkedInLink : TextView
    private lateinit var userId : TextView
    private lateinit var totalFollower : TextView
    private lateinit var totalFollowing : TextView
    private lateinit var totalRepos : TextView
    private lateinit var userPic : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        githubUserName = findViewById(R.id.id)
        logoutBtn = findViewById(R.id.logOut)
        handle = findViewById(R.id.twitterUserName)
        githubBio = findViewById(R.id.bio)
        linkedInLink = findViewById(R.id.linkedInLink)
        userId = findViewById(R.id.githubId)
        totalFollower = findViewById(R.id.follower)
        totalFollowing = findViewById(R.id.following)
        totalRepos = findViewById(R.id.repos)
        userPic = findViewById(R.id.profilePic)

        userName = intent.getStringExtra("githubUserName")!!
        twitterHandle = intent.getStringExtra("twitterHandle")!!
        bio = intent.getStringExtra("githubBio")!!
        linkedIn = intent.getStringExtra("linkedInLink")!!
        githubId = intent.getStringExtra("id")!!
        follower = intent.getStringExtra("follower")!!
        following = intent.getStringExtra("following")!!
        repos = intent.getStringExtra("publicRepos")!!
        picUrl = intent.getStringExtra("profilePic")!!


        githubUserName.text = userName
        handle.text= twitterHandle
        githubBio.text = bio
        linkedInLink.text = linkedIn
        userId.text = githubId
        totalFollower.text = follower
        totalFollowing.text = following
        totalRepos.text = repos


        Glide.with(this)
            .load(picUrl)
            .into(userPic)

        logoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

    }
}