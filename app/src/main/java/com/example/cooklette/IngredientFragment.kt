package com.example.cooklette

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cooklette.databinding.FragmentAddBinding
import com.example.cooklette.databinding.IngredientRowBinding

class IngredientFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddIngredient.setOnClickListener {
            addIngredientRow()
        }

//        binding.photoSection.buttonTakePhoto.setOnClickListener {
//            Log.d(TAG, "take photo called")
//            takePhoto()
//        }
//
//        binding.photoSection.buttonChoosePhoto.setOnClickListener {
//            choosePhoto()
//        }
//
//        binding.photoSection.buttonRemovePhoto.setOnClickListener {
//            removePhoto()
//        }
    }

    private fun addIngredientRow() {
        val rowBinding = IngredientRowBinding.inflate(layoutInflater, binding.containerIngredients, true)

        rowBinding.buttonRemoveIngredient.setOnClickListener {
            binding.containerIngredients.removeView(rowBinding.root)
        }
    }

//    private fun takePhoto() {
//        // Check camera permission
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.CAMERA
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.CAMERA),
//                REQUEST_IMAGE_CAPTURE
//            )
//        } else {
//            startCamera()
//        }
//    }
//
//    private fun startCamera() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        }
//    }
//
//    private fun choosePhoto() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, REQUEST_IMAGE_PICK)
//    }
//
//    private fun removePhoto() {
//        binding.photoSection.imageViewPhoto.setImageBitmap(null)
//        binding.photoSection.imageViewPhoto.visibility = View.GONE
//        binding.photoSection.buttonRemovePhoto.visibility = View.GONE
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                REQUEST_IMAGE_CAPTURE -> {
//                    val imageBitmap = data?.extras?.get("data") as Bitmap
//                    binding.photoSection.imageViewPhoto.setImageBitmap(imageBitmap)
//                    binding.photoSection.imageViewPhoto.visibility = View.VISIBLE
//                    binding.photoSection.buttonRemovePhoto.visibility = View.VISIBLE
//                }
//                REQUEST_IMAGE_PICK -> {
//                    val selectedImageUri: Uri? = data?.data
//                    val imageBitmap = decodeUri(selectedImageUri)
//                    binding.photoSection.imageViewPhoto.setImageBitmap(imageBitmap)
//                    binding.photoSection.imageViewPhoto.visibility = View.VISIBLE
//                    binding.photoSection.buttonRemovePhoto.visibility = View.VISIBLE
//                }
//            }
//        }
//    }
//
//    private fun decodeUri(uri: Uri?): Bitmap? {
//        uri?.let {
//            val inputStream = requireActivity().contentResolver.openInputStream(it)
//            inputStream?.let { stream ->
//                return BitmapFactory.decodeStream(stream)
//            }
//        }
//        return null
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
