package com.geekymanish.remote

import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object NetworkUtilities {

    /**
     * Converts an object to a JSON request body.
     * @return The object converted to a JSON request body.
     */
    fun Any.getJsonRequestBody() =
        this.toString().toRequestBody("application/json".toMediaTypeOrNull())

    /**
     * Converts an object to a form data request body.
     * @return The object converted to a form data request body.
     */
    fun Any.getFormDataBody() =
        this.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

    fun String.toBearer() = "Bearer ".plus(this)

    fun String?.getPartFromFile(param: String): MultipartBody.Part? {
        return if (!isNullOrBlank()) {
            val file = File(this)
            val reqFile = file.asRequestBody(this.getMimeType().toMediaTypeOrNull())
            MultipartBody.Part.createFormData(param, file.name, reqFile)
        } else null
    }

    fun File?.getPartFromFile(param: String): MultipartBody.Part? {
        return if (this?.exists() == true) {
            val reqFile = this.asRequestBody(this.absolutePath.getMimeType().toMediaTypeOrNull())
            MultipartBody.Part.createFormData(param, this.name, reqFile)
        } else null
    }

    private fun String.getMimeType(): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(this) ?: return "image/jpg"
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "image/jpg"
    }

}