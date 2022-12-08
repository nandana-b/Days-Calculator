package com.example.agecalculator

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.joda.time.Period
import org.joda.time.PeriodType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    // initializing variables
    var btn_first: Button? = null
    var btn_today: Button? = null
    var btn_calculate: Button? = null
    var Result: TextView? = null
    var dateSetListener1: OnDateSetListener? = null
    var dateSetListener2: OnDateSetListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //dialog box button
        val btn2=findViewById<Button>(R.id.dbtn)
        btn2.setOnClickListener{
            val dialogBinding=layoutInflater.inflate(R.layout.d_box,null)
            val myDlg= Dialog(this)
            myDlg.setContentView(dialogBinding)
            myDlg.setCancelable(true)
            myDlg.window?.setBackgroundDrawable(ColorDrawable(Color.BLUE))
            myDlg.show()
            val okbtn=dialogBinding.findViewById<Button>(R.id.alert_yes)
            okbtn.setOnClickListener{
                myDlg.dismiss()
            }

        }
        // assign variables
        btn_first = findViewById(R.id.bt_first)
        btn_today = findViewById(R.id.bt_today)
        btn_calculate = findViewById(R.id.btn_calculate)
        Result = findViewById(R.id.result)

        // calendar format is imported to pick date
        val calendar = Calendar.getInstance()

        // for year
        val year = calendar[Calendar.YEAR]

        // for month
        val month = calendar[Calendar.MONTH]

        // for date
        val day = calendar[Calendar.DAY_OF_MONTH]
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

        // to set the current date as by default
        val date = simpleDateFormat.format(Calendar.getInstance().time)
        btn_today!!.setText(date)

        // action to be performed when button 1 is clicked
        btn_first!!.setOnClickListener(View.OnClickListener { // date picker dialog is used
            // and its style and color are also passed
            val datePickerDialog = DatePickerDialog(
                this@MainActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener1,
                year,
                month,
                day
            )
            // to set background for datepicker
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        })

        // it is used to set the date which user selects
        dateSetListener1 =
            OnDateSetListener { view, year, month, day -> // here month+1 is used so that
                // actual month number can be displayed
                // otherwise it starts from 0 and it shows
                // 1 number less for every month
                // example- for january month=0
                var month = month
                month = month + 1
                val date = "$day/$month/$year"
                btn_first!!.setText(date)
            }

        // action to be performed when button 2 is clicked
        btn_today!!.setOnClickListener(View.OnClickListener { // date picker dialog is used
            // and its style and color are also passed
            val datePickerDialog = DatePickerDialog(
                this@MainActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener2,
                year,
                month,
                day
            )
            // to set background for datepicker
            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        })

        // it is used to set the date which user selects
        dateSetListener2 =
            OnDateSetListener { view, year, month, day -> // here month+1 is used so that
                // actual month number can be displayed
                // otherwise it starts from 0 and it shows
                // 1 number less for every month
                // example- for january month=0
                var month = month
                month = month + 1
                val date = "$day/$month/$year"
                btn_today!!.setText(date)
            }

        // action to be performed when calculate button is clicked
        btn_calculate!!.setOnClickListener(View.OnClickListener {
            // converting the inputted date to string
            val sDate = btn_first!!.getText().toString()
            val eDate = btn_today!!.getText().toString()
            val simpleDateFormat1 = SimpleDateFormat("dd/MM/yyyy")
            try {
                // converting it to date format
                val date1 = simpleDateFormat1.parse(sDate)
                val date2 = simpleDateFormat1.parse(eDate)
                val startdate = date1.time
                val endDate = date2.time

                // condition
                if (startdate <= endDate) {
                    val period = Period(startdate, endDate, PeriodType.yearMonthDay())
                    val years = period.years
                    val months = period.months
                    val days = period.days

                    // to show the final output
                    Result!!.setText(years.toString() + " Years + " + months + "Months + " + days + "Days")
                    // toast message
                    Toast.makeText(
                        this@MainActivity,
                        "Calculated",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // toast error message
                    Toast.makeText(
                        this@MainActivity,
                        "Initial date should not be larger than End date!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        })
    }
}
