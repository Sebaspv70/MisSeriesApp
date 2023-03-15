package com.sebastianpatino.misseriesapp.ui.signup


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sebastianpatino.misseriesapp.databinding.ActivitySignUpBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.Arrays


/*
* Punto 2 practica 2
* */
class SignUpActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var aaCountires: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val view = signUpBinding.root
        setContentView(view)

        val infoObserver = Observer<String> { requiret_data ->
            signUpBinding.infoTextView.text = requiret_data.toString()
        }
        mainViewModel.requiretdata.observe(this, infoObserver)

        val errorObserver = Observer<Int> { error ->
            when (error) {
                1 -> {
                    signUpBinding.nameTextInputLayout.error = "Campo obligatorio"
                }
                2 -> {
                    signUpBinding.emailTextInputLayout.error = "Campo obligatorio"
                }
                3 -> {
                    signUpBinding.passwordTextInputLayout.error = "Campo obligatorio"
                }
                4 -> {
                    signUpBinding.repPasswordTextInputLayout.error = "Campo obligatorio"
                }
                5 -> {
                    signUpBinding.dateTextInputLayout.error = "Campo obligatorio"
                }
                else -> {
                    Toast.makeText(
                        applicationContext,
                        "Las contraseñas no son iguales",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        mainViewModel.error.observe(this, errorObserver)


        val no_errorObserver = Observer<Int> { noerror ->
            when (noerror) {
                1 -> {
                    signUpBinding.nameTextInputLayout.error = null
                }
                2 -> {
                    signUpBinding.emailTextInputLayout.error = null
                }
                3 -> {
                    signUpBinding.passwordTextInputLayout.error = null
                }
                4 -> {
                    signUpBinding.repPasswordTextInputLayout.error = null
                }
                else -> {
                    signUpBinding.dateTextInputLayout.error = null
                }

            }

        }
        mainViewModel.noerror.observe(this, no_errorObserver)

        signUpBinding.registerButton.setOnClickListener {
            val name: String = signUpBinding.nameEditText.text.toString()
            val email = signUpBinding.emailEditText.text.toString()
            val password = signUpBinding.passwordEditText.text.toString()
            val repPassword = signUpBinding.redPasswordEditText.text.toString()
            val datebirth = signUpBinding.dateEditText.text.toString()
            val placebirth = signUpBinding.placeDateTextView.text.toString()


            var favoritesGenre = ""
            if (signUpBinding.actionCheckBox.isChecked) favoritesGenre = "Acción"
            if (signUpBinding.adventureCheckBox.isChecked) favoritesGenre += "Aventura"
            if (signUpBinding.comicCheckBox.isChecked) favoritesGenre += "Humor"
            if (signUpBinding.loveCheckBox.isChecked) favoritesGenre += "Romantica"
            if (signUpBinding.suspenseCheckBox.isChecked) favoritesGenre += "Suspenso"
            if (signUpBinding.terrorCheckBox.isChecked) favoritesGenre += "Terror"

            mainViewModel.requiret_data(
                name, email, password, repPassword,
                signUpBinding.maleRadioButton.isChecked,
                signUpBinding.actionCheckBox.isChecked,
                signUpBinding.adventureCheckBox.isChecked,
                signUpBinding.comicCheckBox.isChecked,
                signUpBinding.loveCheckBox.isChecked,
                signUpBinding.suspenseCheckBox.isChecked,
                signUpBinding.terrorCheckBox.isChecked, datebirth, placebirth
            )
        }
        // ----------------Programación del Spinner
        aaCountires = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item
        )
        aaCountires.addAll(
            Arrays.asList(
                "Medellín",
                "Bogotá",
                "Cartagena",
                "Barranquilla",
                "Armenia"
            )
        )
        signUpBinding.citiesSpinner.onItemSelectedListener = this
        signUpBinding.citiesSpinner.adapter = aaCountires
        // ----------------Programación del DataPicker
        KeyboardVisibilityEvent.setEventListener(this) {
            println("keyboard isOpen: $it")
        }
        signUpBinding.dateEditText.setOnClickListener {
            showDatePickerDialog()
            UIUtil.hideKeyboard(this@SignUpActivity)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val countrySelected = aaCountires.getItem(position)
        signUpBinding.placeDateTextView.text = countrySelected
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun showDatePickerDialog() {

        val datepicker =
            DatePickerFragment { day, month, year -> onDateSelected(day, month + 1, year) }
        datepicker.show(supportFragmentManager, "datepicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        signUpBinding.dateEditText.setText("$day/$month/$year")
    }
}




