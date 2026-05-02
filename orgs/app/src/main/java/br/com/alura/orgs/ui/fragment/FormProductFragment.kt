package br.com.alura.orgs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.alura.orgs.dao.ProductsDao
import br.com.alura.orgs.databinding.FragmentFormProductBinding
import br.com.alura.orgs.extensions.tryUploadImage
import br.com.alura.orgs.models.Product
import br.com.alura.orgs.ui.dialog.FormImageDialog
import java.math.BigDecimal

class FormProductFragment : Fragment() {
    private var _binding: FragmentFormProductBinding? = null
    private val binding: FragmentFormProductBinding get() = _binding!!

    val dao = ProductsDao()

    private var url: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormProductBinding.inflate(inflater, container, false)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSaveButton()
        binding.fragmentFormProductImage.setOnClickListener {
            FormImageDialog(this.requireContext())
                .showDialog(url) { image ->
                    url = image
                    binding.fragmentFormProductImage.tryUploadImage(url)
                }
        }

    }


    private fun configureSaveButton() {
        val saveButton = binding.fragmentFormSaveButton
        saveButton.setOnClickListener {
            createProduct()
        }
    }

    private fun createProduct() {
        val fieldName = binding.fragmentFormProductName
        val name = fieldName.text.toString()
        val fieldDescription = binding.fragmentFormProductDescription
        val description = fieldDescription.text.toString()
        val fieldValue = binding.fragmentFormProductValue
        val textValue = fieldValue.text.toString()

        val value =
            if (textValue.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(textValue)
            }
        dao.add(
            product = Product(
                name = name,
                description = description,
                value = value,
                image = url
            )
        )
        activity?.onBackPressed()
    }
}