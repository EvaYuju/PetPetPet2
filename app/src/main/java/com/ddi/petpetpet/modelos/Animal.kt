package com.ddi.petpetpet.modelos


class Animal {
    var codigo: String = ""
    var nombre: String = ""
    var raza: String = ""
    var fecnac: String = ""
    var sexo: String = ""
    var dni: String = ""

    constructor(
        codigo: String,
        nombre: String,
        raza: String,
        fecnac: String,
        sexo: String,
        dni: String
    ) {
        this.codigo = codigo
        this.nombre = nombre
        this.raza = raza
        this.fecnac = fecnac
        this.sexo = sexo
        this.dni = dni
    }

    constructor() {
    }

}

/*class Animal(var codigo: Int, var nombre: String, var raza: String, var fecnac: String, var sexo: String) {
    override fun toString(): String {
        return "Código: $codigo, Nombre: $nombre, Raza: $raza, Fecha de nacimiento: $fecnac, Sexo: $sexo"
    }
}
*/