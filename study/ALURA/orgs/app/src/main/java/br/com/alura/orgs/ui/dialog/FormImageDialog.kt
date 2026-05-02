package br.com.alura.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.orgs.databinding.FormImageBinding
import br.com.alura.orgs.extensions.tryUploadImage

class FormImageDialog(private val context: Context) {
    fun showDialog(
        defaultUrl: String? = null,
        whenImageLoaded: (image: String) -> Unit
    ) {
        FormImageBinding
            .inflate(LayoutInflater.from(context)).apply {
                defaultUrl?.let {
                    formImageImageView.tryUploadImage(it)
                    formImageUrl.setText(it)
                }
                formImageButtonUpload.setOnClickListener {
                    val url = formImageUrl.text.toString()
                    formImageImageView.tryUploadImage(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirm") { _, _ ->
                        val url = formImageUrl.text.toString()
                        whenImageLoaded(url)
                        formImageImageView.tryUploadImage(url)
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()


            }
    }
}