package com.sebastianpatino.misseriesapp.ui.signup

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val requiretdata: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val error: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val noerror: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun requiret_data(
        name: String, email: String, password: String,
        repPassword: String, genre: Boolean, action: Boolean, adventure: Boolean,
        comic: Boolean, love: Boolean, suspense: Boolean, terror: Boolean,
        datebirth: String, placebirth: String
    ) {

        val c_genre = if (genre)
            "Masculino"
        else
            "Femenino"

        var favoritesGenre = ""
        if (action) favoritesGenre = "Acción"
        if (adventure) favoritesGenre += "Aventura"
        if (comic) favoritesGenre += "Humor"
        if (love) favoritesGenre += "Romantica"
        if (suspense) favoritesGenre += "Suspenso"
        if (terror) favoritesGenre += "Terror"

        if (name != "") {
            noerror.value = 1
            if (email != "") {
                noerror.value = 2
                if (password != "") {
                    noerror.value = 3
                    if (repPassword != "") {
                        noerror.value = 4
                        if (password == repPassword) {
                            if (datebirth != "") {
                                noerror.value = 5
                                requiretdata.value = "Nombre: $name\nEmail: $email\n" +
                                        "Password: $password\nGenero: $c_genre\n" +
                                        "Generos favoritos: $favoritesGenre\nFecha de Nacimiento:  $datebirth\n" +
                                        "Lugar de Nacieminto: $placebirth"
                            } else
                                error.value = 5
                        } else
                            error.value = 6
                    } else
                        error.value = 4
                } else
                    error.value = 3
            } else
                error.value = 2
        } else
            error.value = 1
    }

}