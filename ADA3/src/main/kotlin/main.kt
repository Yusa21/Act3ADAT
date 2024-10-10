import escribirEmpleado.escribirXML
import leerFichero.leerXMl
import leerFichero.readEmpleados
import modificarXML.cambiarSalario
import kotlin.io.path.Path

fun main() {
    val path = Path("src").resolve("main").resolve("resources").resolve("empleado")
    val listaEmpleados = readEmpleados(path.toString())
    escribirXML(listaEmpleados)

    val pathXML = Path("src").resolve("main").resolve("resources").resolve("empleados.xml")
    var empleados = leerXMl(pathXML.toString())

    //Este trozo de código pregunta al usuario si quiere modificar los sueldos y controla que los datos sean correctos
    var cont1 = true
    while (cont1) {
        println("Quieres modificar algún sueldo? s/n")
        var choice = readln()
        if (choice.lowercase() == "s") {
            var cont2 = true
            var idObjetivo = 0
            var nuevoSalario = 0F
            while (cont2) {
                try {
                    println("ID del empleado")
                    idObjetivo = readln().toInt()
                    try {
                        println("Nuevo salario")
                        nuevoSalario = readln().toFloat()
                        cont2 = false
                    } catch (e: Exception) {
                        println("El salario tiene que ser un Float")
                    }
                } catch (e: Exception) {
                    println("El Id tiene que ser un entero")
                }
                //Cambia la el salario en el mapa y luego lo guarda volviendo a escribir el fichero XML
                if (cambiarSalario(idObjetivo, nuevoSalario, empleados) == 0) {
                    println("Se ha cambiado el salario correctamente")
                } else {
                    println("El ID marcado no existe")
                }

            }
        } else if (choice.lowercase() == "n") {
            cont1 = false
        } else {
            println("Es opción no es valida")
        }


    }

    //Imprimimos por consola en el main para respetar la modularidad
    empleados.forEach { empleado ->
        println("ID: ${empleado.value.id}, Apellido: ${empleado.value.apellido}, Departamento: ${empleado.value.departamento}, Salario: ${empleado.value.salario}")
    }


}