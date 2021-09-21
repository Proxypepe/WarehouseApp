package com.warehouse.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.warehouse.R
import com.warehouse.repository.database.entity.Request
import java.util.*


class RequestListAdapter : ListAdapter<Request, RequestListAdapter.RequestViewHolder>(REQUEST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.productName, current.amount, current.warehousePlace,
                    current.status, current.arrivalDate)
    }

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val requestIDTV: TextView = itemView.findViewById(R.id.requestIDTV)
        private val productNameTV: TextView = itemView.findViewById(R.id.productNameTV)
        private val amountTV: TextView = itemView.findViewById(R.id.amountTV)
        private val warehousePlaceTV: TextView = itemView.findViewById(R.id.warehousePlaceTV)
        private val statusTV: TextView = itemView.findViewById(R.id.statusTV)
        private val arrivalDateTV: TextView = itemView.findViewById(R.id.arrivalDateTV)


        fun bind(requestID: Int?, productName: String?, amount: Int?,
                 warehousePlace: Int?, status: String?, arrivalDate: Date?) {
            requestIDTV.text = requestID.toString()
            productNameTV.text = productName
            amountTV.text = amount.toString()
            warehousePlaceTV.text = warehousePlace.toString()
            statusTV.text = status
            arrivalDateTV.text = arrivalDate.toString()
        }

        companion object {
            fun create(parent: ViewGroup): RequestViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return RequestViewHolder(view)
            }
        }
    }

    companion object {
        private val REQUEST_COMPARATOR = object : DiffUtil.ItemCallback<Request>() {
            override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
                return oldItem.productName == newItem.productName
            }
        }
    }
}