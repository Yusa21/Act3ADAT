package leerFichero

import clases.Empleado
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path

// Función que recibe la ruta del fichero y devuelve un Map con los objetos clases.Empleado la clave es el ID
fun readEmpleados(rutaFichero: String): Map<String, Empleado> {
    val empleados = mutableMapOf<String, Empleado>()
    // Leer el archivo línea por línea
    val rutaFicheroPath= Path.of(rutaFichero)
    val br: BufferedReader = Files.newBufferedReader(rutaFicheroPath)

    br.use{ flujo ->
        //Nos saltamos la primera linea porque es donde están los encabezados
        flujo.lineSequence().drop(1).forEach{ line ->
            // Dividir la línea por ";"
            val datos = line.split(",")

            // Convertir cada valor a su tipo correspondiente y tener en cuenta los decimales con coma
            val id = datos[0].toInt()
            val apellido = datos[1]
            val departamento = datos[2]
            val salario = datos[3].toFloat()

            // Crear el objeto clase Empleado
            val empleado = Empleado(
                id,
                apellido,
                departamento,
                salario
            )

            // Añadirlo al diccionario usando el nombre como clave
            empleados[id.toString()] = empleado

        }
    }

    return empleados
}
