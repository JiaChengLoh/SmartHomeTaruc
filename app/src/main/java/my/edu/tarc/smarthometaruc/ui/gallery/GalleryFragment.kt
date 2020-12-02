package my.edu.tarc.smarthometaruc.ui.gallery

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.smarthometaruc.R
import java.util.*

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel


    var database = FirebaseDatabase.getInstance()

    //Getting Database Reference
    var myRef = database.reference

    //Getting Reference to Root Node
    var myRef1 = database.getReference("PI_04_CONTROL/lcdtxt")



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = view.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val test: TextView = view.findViewById(R.id.textViewTestValue)

        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour :String = c.get(Calendar.HOUR_OF_DAY).toString()

        val childName = "PI_04_2020$month$day"
        val hourName = "$hour"

        //private val mHandler = Handler()

        Log.i("childName","$childName")
        Log.i("hourName","$hourName")

        myRef1.setValue("=App II running=")

        myRef.child("PI_04_2020$month$day").child("$hour").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val test: TextView = view.findViewById(R.id.textViewTestValue)
                val test2: TextView = view.findViewById(R.id.textViewTestValue2)
                val lightLevel = dataSnapshot.child("light").value.toString()
                val soundLevel = dataSnapshot.child("sound").value.toString()
                test.text = lightLevel
                test2.text = soundLevel
                Log.i("Light Value","$lightLevel")
                Log.i("Sound Value","$soundLevel")

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("child", "postComments:onCancelled", databaseError.toException())
            }
        })


        return view
    }
}