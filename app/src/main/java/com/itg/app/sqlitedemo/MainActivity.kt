package com.itg.app.sqlitedemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.itg.app.sqlitedemo.entities.SyncDataResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_data_adapter.view.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
        subscribeViewModel()
    }

    private fun fetchData() {
        viewModel.syncData(action = "showData")
    }

    private fun subscribeViewModel() {
        viewModel.syncDataResponse.observe(this, Observer { data ->
            list.apply {
                val itemAdapter = ItemAdapter()
                itemAdapter.data = data
                this.adapter = itemAdapter
            }
            progress.visibility = View.GONE
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        })
    }

    inner class ItemAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        lateinit var data: MutableList<SyncDataResponse>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_data_adapter, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is ViewHolder -> {
                    holder.bindViewHolder(data[position])
                }
            }
        }

    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindViewHolder(syncDataResponse: SyncDataResponse) {
            view.title.text = syncDataResponse.title
            view.retailId.text = syncDataResponse.retailerId
            view.status.text = syncDataResponse.status
        }

    }
}
