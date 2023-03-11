package com.sebastianpatino.misseriesapp.ui.signup


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.sebastianpatino.misseriesapp.databinding.ActivitySignUpBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.Arrays


/*
* Punto 2 practica 2
* */
class SignUpActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var aaCountires: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)

        // ----------------Programación del Spinner
        aaCountires = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)
        aaCountires.addAll(Arrays.asList("Medellín","Bogotá", "Cartagena","Barranquilla","Armenia"))
        signUpBinding.citiesSpinner.onItemSelectedListener = this
        signUpBinding.citiesSpinner.adapter = aaCountires
        // ----------------Programación del DataPicker
        KeyboardVisibilityEvent.setEventListener(this){
            println("keyboard isOpen: $it")
        }
        signUpBinding.dateEditText.setOnClickListener{
            showDatePickerDialog()
            UIUtil.hideKeyboard(this@SignUpActivity)
        }

        signUpBinding.registerButton.setOnClickListener {
            val name :String = signUpBinding.nameEditText.text.toString()
            val email = signUpBinding.emailEditText.text.toString()
            val password = signUpBinding.passwordEditText.text.toString()
            val repPassword = signUpBinding.redPasswordEditText.text.toString()
            val datebirth = signUpBinding.dateEditText.text.toString()
            val placebirth = signUpBinding.placeDateTextView.text.toString()

            val genre = if(signUpBinding.maleRadioButton.isChecked)
                "Masculino"
            else
                "Femenino"


            var favoritesGenre = ""
            if (signUpBinding.actionCheckBox.isChecked) favoritesGenre = "Acción"
            if (signUpBinding.adventureCheckBox.isChecked) favoritesGenre += "Aventura"
            if (signUpBinding.comicCheckBox.isChecked) favoritesGenre += "Humor"
            if (signUpBinding.loveCheckBox.isChecked) favoritesGenre += "Romantica"
            if (signUpBinding.suspenseCheckBox.isChecked) favoritesGenre += "Suspenso"
            if (signUpBinding.terrorCheckBox.isChecked) favoritesGenre += "Terror"

            val info = "Nombre: $name\nEmail: $email\nPassword: $password\nGenero: $genre\n" +
                    "Generos favoritos: $favoritesGenre\nFecha de Nacimiento:  $datebirth\n" +
                    "Lugar de Nacieminto: $placebirth"

            /* Formas de sacar mensajes de error*/
            if (name != ""){
                signUpBinding.nameTextInputLayout.error = null
                if (email != ""){
                    signUpBinding.emailTextInputLayout.error = null
                    if(password != ""){
                        signUpBinding.passwordTextInputLayout.error = null
                        if(repPassword != ""){
                            signUpBinding.repPasswordTextInputLayout.error = null
                            if (password == repPassword) {
                                if (datebirth != "") {
                                    signUpBinding.dateTextInputLayout.error = null
                                    signUpBinding.infoTextView.setText(info)
                                } else
                                    signUpBinding.dateTextInputLayout.error = "Campo obligatorio"
                            }
                            else
                                Toast.makeText(applicationContext, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show()
                        }
                        else
                            signUpBinding.repPasswordTextInputLayout.error = "Campo obligatorio"
                    }
                    else
                        signUpBinding.passwordTextInputLayout.error = "Campo obligatorio"
                }
                else
                    signUpBinding.emailTextInputLayout.error = "Campo obligatorio"
            }
            else
                signUpBinding.nameTextInputLayout.error = "Campo obligatorio"



            /*
            signUpBinding.repPasswordTextInputLayout.error = "Las contraseñas no son iguales"

            Snackbar.make(signUpBinding.linerLayout,"Las contraseñas no son iguales",Snackbar.LENGTH_LONG)
                .setAction("Aceptar"){
                    signUpBinding.redPasswordEditText.setText("")
                    signUpBinding.repPasswordTextInputLayout.isErrorEnabled = false
                }
                .show()

             */
            /*
            val numero1 = signUpBinding.nameEditText.text.toString().toInt()
            val numero2 = signUpBinding.emailEditText.text.toString().toInt()
            val suma = numero1 + numero2
            signUpBinding.passwordEditText.setText(suma.toString())
             */
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val countrySelected = aaCountires.getItem(position)
        signUpBinding.placeDateTextView.text = countrySelected
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun showDatePickerDialog() {

        val datepicker = DatePickerFragment {day, month, year ->onDateSelected(day,month+1,year)  }
        datepicker.show(supportFragmentManager,"datepicker")
    }
    fun onDateSelected(day:Int,month:Int,year:Int){
        signUpBinding.dateEditText.setText("$day/$month/$year")
    }
}




