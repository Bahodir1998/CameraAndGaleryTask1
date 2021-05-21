package com.example.cameraandgallerytask1

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cameraandgallerytask1.databinding.FragmentFirthBinding
import com.example.cameraandgallerytask1.db.MyDbHelper
import com.example.cameraandgallerytask1.models.Sign
import com.github.florent37.runtimepermission.kotlin.askPermission
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FirthFragment : Fragment() {

    lateinit var binding: FragmentFirthBinding
    var imgUri: Uri? = null
    lateinit var myDbHelper: MyDbHelper
    lateinit var signById: Sign
    var isclicked = 0
    var iscamera = 0
    lateinit var currentImagePath: String
    var requestCode = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirthBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        val id = arguments?.getInt("id")
        myDbHelper = MyDbHelper(binding.root.context)

        if (id == null) {
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle("Yo’l belgisini qo’shish")
        } else {
            signById = myDbHelper.getSignById(id!!)
            imgUri = Uri.parse(signById.imagePath)
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle("Yo’l belgisini o'zgartirish")
            binding.imageView.setImageURI(Uri.parse(signById.imagePath))
            binding.signNameEt.setText(signById.name)
            binding.signContentEt.setText(signById.content)
            binding.spinner.setSelection(signById.type!!)
        }
        binding.imageView.setOnClickListener {
            askPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ) {
                val dialog = Dialog(binding.root.context)
                dialog.setContentView(R.layout.custom_dial)
                dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)


                dialog.findViewById<ImageView>(R.id.camera).setOnClickListener {

                    dialog.dismiss()
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.resolveActivity(requireActivity().packageManager)

                    val photoFile = createImageFile()

                    photoFile.also {
                        val photoUri = FileProvider.getUriForFile(
                            requireContext(),
                            BuildConfig.APPLICATION_ID,
                            it
                        )
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                        startActivityForResult(intent, requestCode)
                    }
                }
                dialog.findViewById<ImageView>(R.id.galery).setOnClickListener {
                    dialog.dismiss()
                    getImageContent.launch("image/*")
                }
                dialog.show()

            }.onDeclined { e ->
                println(3)
                if (e.hasDenied()) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Please accept to our permissions")
                        .setPositiveButton("ok") { dialog, which ->
                            e.askAgain()
                        }
                        .setNegativeButton("cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }

                if (e.hasForeverDenied()) {
                    e.goToSettings()
                }
            }
        }

        binding.saveBtn.setOnClickListener {
            val name = binding.signNameEt.text.toString()
            val content = binding.signContentEt.text.toString()
            val type = binding.spinner.selectedItemPosition

            if (imgUri != null && name.isNullOrBlank() != true && content.isNullOrBlank() != true && isclicked == 1) {
                val openInputStream = requireActivity().contentResolver.openInputStream(imgUri!!)
                val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val file = File(requireActivity().filesDir, "JPEG_$format.jpg")
                val fileOutputStream = FileOutputStream(file)
                openInputStream?.copyTo(fileOutputStream)
                openInputStream?.close()
                fileOutputStream?.close()
                val fileAbsolutePath = file.absolutePath

                if (id == null) {
                    val sign = Sign(name, content, type, fileAbsolutePath, 0)
                    myDbHelper.addSign(sign)
                } else {
                    val sign = Sign(id, name, content, type, fileAbsolutePath, signById.like)
                    myDbHelper.updateSign(sign)
                }

                findNavController().popBackStack()
            } else if (imgUri != null && name.isNullOrBlank() != true && content.isNullOrBlank() != true && isclicked == 0&&iscamera==0) {
                val sign = Sign(id, name, content, type, signById.imagePath, signById.like)
                myDbHelper.updateSign(sign)
                findNavController().popBackStack()
            } else if (name.isNullOrBlank() != true && content.isNullOrBlank() != true && iscamera == 1) {
                if (id == null) {
                    val sign = Sign(name, content, type, currentImagePath, 0)
                    myDbHelper.addSign(sign)
                } else {
                    val sign = Sign(id, name, content, type, currentImagePath, signById.like)
                    myDbHelper.updateSign(sign)
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Iltimos aval to'ldiring", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {

        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        val externalFilesDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("JPEG_$format", ".jpg", externalFilesDir).apply {
            currentImagePath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (::currentImagePath.isInitialized) {
            binding.imageView.setImageURI(Uri.parse(currentImagePath))
            iscamera = 1
        }
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            binding.imageView.setImageURI(uri)
            imgUri = uri
            isclicked = 1
        }
}