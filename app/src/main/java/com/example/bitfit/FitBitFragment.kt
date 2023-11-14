package com.example.bitfit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "ArticleListFragment"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val ARTICLE_SEARCH_URL =
    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

class FitBitFragment : Fragment() {
    private val articles = mutableListOf<DisplayFit>()
    private lateinit var fitRecyclerView: RecyclerView
    private lateinit var fitAdapter: FitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_fit_bit, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        fitRecyclerView = view.findViewById(R.id.article_recycler_view)
        fitRecyclerView.layoutManager = layoutManager
        fitRecyclerView.setHasFixedSize(true)
        fitAdapter = FitAdapter(view.context, articles)
        fitRecyclerView.adapter = fitAdapter

        lifecycleScope.launch{
            (activity?.application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFit(
                        entity.name.toString(),
                        entity.calories
                    )
                }.also { mappedList ->
                    articles.clear()
                    articles.addAll(mappedList)
                    fitAdapter.notifyDataSetChanged()
                }
            }
        }

        // Update the return statement to return the inflated view from above
        return view
 
    }

    private fun fetchArticles() {
        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchArticles()
    }

    companion object {
        fun newInstance(): FitBitFragment {
            return FitBitFragment()
        }
    }
}