package leerFichero

import clases.Empleado
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory

//Lee el XML y devuelve un diccionario con los objetos que contienen los datos
fun leerXMl(rutaFichero: String): Map<String, Empleado>{
    val dbf = DocumentBuilderFactory.newInstance()
    val db = dbf.newDocumentBuilder()
    val ficheroXMl = Path.of(rutaFichero)
    val document = db.parse(ficheroXMl.toFile())

    val root: Element = document.documentElement
    root.normalize()

    val listaNodos: NodeList = root.getElementsByTagName("empleado")
    val empleados = mutableMapOf<String, Empleado>()
    for (i in 0..<listaNodos.length){

        // Para acceder a un item en particular, accedemos a través del index
        val nodo:Node = listaNodos.item(i)

        // Para acceder al tipo del Nodo, usamos .nodeType
        if(nodo.nodeType == Node.ELEMENT_NODE) {

            // "Casteamos" a Element
            val nodoElemento = nodo as Element
            val atributoId = nodoElemento.attributes.getNamedItem("id")

            // Podemos buscar los elementos que nos convienen
            val elementoApellido = nodoElemento.getElementsByTagName("apellido")
            val elementoDepartamento = nodoElemento.getElementsByTagName("departamento")
            val elementoSalario = nodoElemento.getElementsByTagName("salario")

            // Una vez tenemos el elemento que queremos, podemos acceder a su contenido
            val textContentApellido = elementoApellido.item(0).textContent
            val textContentDepartamento = elementoDepartamento.item(0).textContent
            val textContentSalario = elementoSalario.item(0).textContent.toFloat()
            val textContentId = atributoId.textContent.toInt()

            // Crear el objeto clases.Empleado
            val empleado = Empleado(
                textContentId,
                textContentApellido,
                textContentDepartamento,
                textContentSalario
            )

            // Añadirlo al diccionario usando el nombre como clave
            empleados[textContentId.toString()] = empleado
        }


    }
    return empleados
}