package com.bangkit.event

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bangkit.event.databinding.ActivityDetailBinding
import com.bangkit.event.entity.EntityEvent
import com.bangkit.event.repository.RepositoryEvent
import com.bangkit.event.response.ListEventsItem
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteEventRepo: RepositoryEvent
    private var favorite = false

    companion object {
        const val EVENT_KEY = "event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteEventRepo = RepositoryEvent.getInstance(this)

        val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EVENT_KEY, ListEventsItem::class.java)
                ?: intent.getParcelableExtra(EVENT_KEY, EntityEvent::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EVENT_KEY) as? ListEventsItem
                ?: intent.getParcelableExtra(EVENT_KEY) as? EntityEvent
        }

        event?.let {
            when (it) {
                is ListEventsItem -> setupUI(it)
                is EntityEvent -> setupUI(it)
            }

            binding.btnRegister.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse((event as? ListEventsItem)?.link)))
            }

            checkFavoriteStatus(event)

            binding.btnFavorite.setOnClickListener {
                favorite = !favorite
                if (event is ListEventsItem) saveFavoriteStatus(event)
                else if (event is EntityEvent) saveFavoriteStatus(event)
                updateFavoriteIcon()
            }
        } ?: run {
            Snackbar.make(binding.root, "Event tidak ditemukan", Snackbar.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupUI(event: ListEventsItem) {
        with(binding) {
            Name.text = event.name
            Kuota.text = getString(R.string.quota, event.quota - event.registrants)
            NameOwner.text = event.ownerName
            TimeBegin.text = event.beginTime
            Deskripsi.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            MediaCover.loadImage(event.imageLogo)
        }
    }

    private fun setupUI(event: EntityEvent) {
        with(binding) {
            Name.text = event.name
            Kuota.text = getString(R.string.quota, event.quota - event.registrants)
            NameOwner.text = event.ownerName
            TimeBegin.text = event.beginTime
            Deskripsi.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            MediaCover.loadImage(event.imageLogo)
        }
    }

    private fun updateFavoriteIcon() {
        binding.btnFavorite.setImageResource(
            if (favorite) R.drawable.ic_filled_favorite
            else R.drawable.ic_outlined_favorite
        )
    }

    private fun checkFavoriteStatus(event: Any) {
        val eventId = when (event) {
            is ListEventsItem -> event.id.toString()
            is EntityEvent -> event.id
            else -> null
        }
        eventId?.let {
            favoriteEventRepo.getFavoriteEventById(it).observe(this) { favoriteEvent ->
                favorite = favoriteEvent != null
                updateFavoriteIcon()
            }
        }
    }

    private fun saveFavoriteStatus(event: ListEventsItem) {
        if (favorite) {
            favoriteEventRepo.insertEvent(EntityEvent(event.id.toString(), event.name, event.imageLogo,event.quota,event.ownerName,event.beginTime,event.description,event.registrants))
        } else {
            favoriteEventRepo.getFavoriteEventById(event.id.toString()).observe(this) { favoriteEvent ->
                favoriteEvent?.let { favoriteEventRepo.delete(it) }
            }
        }
    }

    private fun saveFavoriteStatus(event: EntityEvent) {
        if (favorite) {
            favoriteEventRepo.insertEvent(event)
        } else {
            favoriteEventRepo.getFavoriteEventById(event.id).observe(this) { favoriteEvent ->
                favoriteEvent?.let { favoriteEventRepo.delete(it) }
            }
        }
    }
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).centerCrop().into(this)
}
