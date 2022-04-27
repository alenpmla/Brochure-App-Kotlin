package com.example.brochureapp.presentation.ui

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.brochureapp.R
import com.example.brochureapp.domain.entities.Content
import kotlinx.android.synthetic.main.single_item.view.*


class RecyclerAdapter(private val content: List<Content>) :
    RecyclerView.Adapter<RecyclerAdapter.ContentHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentHolder {
        val inflatedView = parent.inflate(R.layout.single_item, false)
        return ContentHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val itemPhoto = content[position]
        holder.buildContent(itemPhoto)
    }

    override fun getItemCount(): Int {
        return content.size
    }


    class ContentHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var content: Content? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }

        fun buildContent(content: Content) {
            this.content = content
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.setColorSchemeColors(
                ContextCompat.getColor(
                    view.context,
                    android.R.color.holo_blue_dark
                )
            )
            circularProgressDrawable.start()
            Glide.with(view.context)
                .load(content.brochureImage)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.image_error_place_holder)
                .into(view.itemImage)
            view.retailerName.text =
                content.retailer?.name ?: view.context.getText(R.string.text_not_available)
        }
    }
}

