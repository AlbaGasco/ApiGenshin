package com.example.easylearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    var userList = arrayListOf<User>()
    val apiSample = "https://gsi.fly.dev/characters"
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView =  findViewById(R.id.recyclerView)

        val reqQueue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,apiSample, null, { res ->
            Log.d("Volley Sample", res.getString("page"))

                    val jsonArray = res.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)

                val user = User(
                    jsonObj.getInt("id"),
                    jsonObj.getString("name"),
                    jsonObj.getString("weapon"),
                    jsonObj.getString("vision")
                )

                userList.add(user)
            }

            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = UserAdapter(userList)



        }, {err ->
                Log.d("Fail", err.message.toString())
        })

        reqQueue.add(request)

    }
}