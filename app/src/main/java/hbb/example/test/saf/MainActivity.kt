package hbb.example.test.saf

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import hbb.example.test.R
import hbb.example.test.bilibili.Solution
//import hbb.example.test.bilibili.ZhenTi
import hbb.example.test.coroutines.CoroutinesGuide
import hbb.example.test.databinding.ActivitySafMainBinding
import hbb.example.test.saf.vm.ClickListener
import hbb.example.test.saf.vm.Vm
import kotlinx.android.synthetic.main.activity_saf_main.view.*
import java.io.*

/**
 * Created by hjh on 2020/8/3
 * https://developer.android.google.cn/guide/topics/providers/document-provider
 * 自定义选择器
 * https://developer.android.google.cn/guide/topics/providers/create-document-provider
 */

class  MainActivity  : AppCompatActivity(){

    companion object{
        private const val READ_REQUEST_CODE: Int = 42
        private const val WRITE_REQUEST_CODE: Int = 43
        private const val EDIT_REQUEST_CODE: Int = 44
    }

    private lateinit var mainBinding:ActivitySafMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding = ActivitySafMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        CoroutinesGuide.show1()
//        ZhenTi.calcLr(intArrayOf(1,2,8,3,5,7))
        Solution.maxProfit(intArrayOf(1,2))
//        val intent = Intent()
//        AdvertisingIgetAdvertisingIdInfo()
//        intent.action= Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
//        startActivity(intent)
        mainBinding.vm = Vm()
        mainBinding.onClick = object : ClickListener{
            override fun onCLick(v: View) {
                when(v.id){
                    R.id.btn0 -> actionOpenDocument()
                    R.id.btn1 -> actionCreateDocument()
                    R.id.btn2 -> actionEditDocument()
                    R.id.btn3 -> actionManageAllFilesAccess()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //GET PHOTO
            data?.data?.also { uri ->
                Log.i("TAG", "choice Uri: $uri")
                dumpImageMetaData(uri);
                mainBinding.iv.setImageBitmap(getBitmapFromUri(uri))
            }
        }else if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //CREATE PHOTO
            data?.data?.also {
                uri -> Log.i("TAG","Create Uri:$uri")
                writeToFile(uri)
                mainBinding.tv.text = readTextFromUri(uri)
            }
        }else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.data?.also {
                uri -> Log.i("TAG","choice Uri:$uri")
                saveUri(uri)
                updateToFile(uri)
                mainBinding.tv.text = readTextFromUri(uri)
            }
        }
    }

    //判断是否为虚拟文件
//    虚拟文件没有二进制表示形式，客户端应用也可将其强制转换为其他文件类型，
//    或使用 ACTION_VIEW Intent 查看这些文件，从而打开文件中的内容。
//    指明mime 来查看文件内容
    private fun isVirtualFile(uri: Uri): Boolean {
        if (!DocumentsContract.isDocumentUri(this, uri)) {
            return false
        }

        val cursor: Cursor? = contentResolver.query(
            uri,
            arrayOf(DocumentsContract.Document.COLUMN_FLAGS),
            null,
            null,
            null
        )

        val flags: Int = cursor?.use {
            if (cursor.moveToFirst()) {
                cursor.getInt(0)
            } else {
                0
            }
        } ?: 0

        return flags and DocumentsContract.Document.FLAG_VIRTUAL_DOCUMENT != 0
    }

//    在验证文件为虚拟文件后，您可以将其强制转换为另一种 MIME 类型，
//    如图片文件。以下代码段展示如何检查虚拟文件能否表示为图像，如果是，则从虚拟文件获取输入流。
    @Throws(IOException::class)
    private fun getInputStreamForVirtualFile(uri: Uri, mimeTypeFilter: String): InputStream {

        val openableMimeTypes: Array<String>? = contentResolver.getStreamTypes(uri, mimeTypeFilter)

        return if (openableMimeTypes?.isNotEmpty() == true) {
            contentResolver
                .openTypedAssetFileDescriptor(uri, openableMimeTypes[0], null)!!
                .createInputStream()
        } else {
            throw FileNotFoundException()
        }
    }

    //保留系统向应用授予的权限
//    您的应用是“获取”了系统提供的 URI 持久授权。如此一来，用户便可通过您的应用持续访问文件，即使设备已重启也不受影响：
//    没获取前手机重启，会失去编辑过的记录
    private fun saveUri(uri:Uri){
        val takeFlags: Int = intent.flags and
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
// Check for the freshest data.
        contentResolver.takePersistableUriPermission(uri, takeFlags)
    }

    private fun writeToFile(uri:Uri){
        try {
            contentResolver.openFileDescriptor(uri, "w")?.use {
                // use{} lets the document provider know you're done by automatically closing the stream
                FileOutputStream(it.fileDescriptor).use { fos ->
                    fos.write( ("guestId + ${System.currentTimeMillis()}\n").toByteArray())
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateToFile(uri:Uri){
        try {
            contentResolver.openFileDescriptor(uri, "w")?.use {
                // use{} lets the document provider know you're done by automatically closing the stream
                FileOutputStream(it.fileDescriptor).use { fos ->
                    fos.write( ("update guestId + ${System.currentTimeMillis()}\n").toByteArray())
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //检查文档元数据
    private fun dumpImageMetaData(uri: Uri) {

        // The query, since it only applies to a single document, will only return
        // one row. There's no need to filter, sort, or select fields, since we want
        // all fields for one document.
        val cursor: Cursor? = contentResolver.query( uri, null, null, null, null, null)

        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {
                //包含的 属性
                for ( i in 0 until it.columnCount ){
                    Log.i("TAG",it.getColumnName(i))
                }
                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i("TAG", "Display Name: $displayName")
                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null.  But since an
                // int can't be null in Java, the behavior is implementation-specific,
                // which is just a fancy term for "unpredictable".  So as
                // a rule, check if it's null before assigning to an int.  This will
                // happen often:  The storage API allows for remote files, whose
                // size might not be locally known.
                val size: String = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)
                } else {
                    "Unknown"
                }
                Log.i("TAG", "Size: $size")
            }
        }
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")!!
        val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }

    private fun actionEditDocument() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's
        // file browser.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones).
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only text files.
            type = "text/plain"
        }

        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }

    private fun actionCreateDocument(){
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as
            // a file (as opposed to a list of contacts or timezones).
            addCategory(Intent.CATEGORY_OPENABLE)

            // Create a file with the requested MIME type.
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "test")
        }

        startActivityForResult(intent, WRITE_REQUEST_CODE)
    }

    private fun actionOpenDocument(){
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // ACTION_GET_CONTENT。
        //如果您只想让应用读取/导入数据，请使用 ACTION_GET_CONTENT。使用此方法时，应用会导入数据（如图片文件）的副本。
        //如果您想让应用获得对文档提供程序所拥有文档的长期、持续性访问权限，请使用 ACTION_OPEN_DOCUMENT。例如，照片编辑应用可让用户编辑存储在文档提供程序中的图像。
        // ACTION_CREATE_DOCUMENT
        // browser.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "*/*"
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    /**
     * 让用户自己授权文件权限
     * */
    private fun actionManageAllFilesAccess(){
//        var intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
    }

}