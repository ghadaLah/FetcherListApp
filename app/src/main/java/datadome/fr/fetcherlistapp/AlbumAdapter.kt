package datadome.fr.fetcherlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import datadome.fr.fetcher.data.AlbumModel
import kotlinx.android.synthetic.main.album_item.view.*

class AlbumAdapter: PagedListAdapter<AlbumModel, AlbumAdapter.AlbumViewHolder>(
    DiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class AlbumViewHolder(item: View): RecyclerView.ViewHolder(item) {
        fun bindView(album: AlbumModel?) {
            /*Glide.with(itemView.context)
                .load(album?.thumbnailUrl)
                .into(itemView.album_image)*/
            Picasso.get().load(album?.url).into(itemView.album_image)
            itemView.album_title.text = album?.title
        }
    }

    private class DiffCallBack: DiffUtil.ItemCallback<AlbumModel>() {
        override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return true
        }

    }
}