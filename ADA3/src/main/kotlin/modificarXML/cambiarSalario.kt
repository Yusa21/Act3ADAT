package modificarXML

import clases.Empleado
import escribirEmpleado.escribirXML

//Recibe el diccionario y devuelve el diccionario con la salario cambiado
fun cambiarSalario(idObjetivo: Int, nuevoSalario: Float, empleados: Map <String, Empleado> ): Int {

    //Recorre todos los empleados cargados
    empleados.forEach { empleado ->
        //Si encuentra alguna que coincida modifica los datos cargados y el fichero
        if(empleado.key.toInt() == idObjetivo) {
            empleado.value.salario = nuevoSalario
            escribirXML(empleados)
            return 0
        }
    }

    return -1

}