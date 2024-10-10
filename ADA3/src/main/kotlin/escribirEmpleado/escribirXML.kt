package escribirEmpleado

import clases.Empleado
import org.w3c.dom.*
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.*
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.io.path.*


fun escribirXML(empleados: Map<String,Empleado>) {
    // Sacamos el path del archivo objetivo
    val path = Path.of("src").resolve("main").resolve("resources")
    val ficheroXML = path.resolve("empleados.xml")

    //Si el fichero no existe lo creamos
    if (ficheroXML.notExists()) {
        Files.createDirectories(ficheroXML.parent)
        Files.createFile(ficheroXML)
        ficheroXML.writeText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    }

    val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    val builder: DocumentBuilder = factory.newDocumentBuilder()
    val imp: DOMImplementation = builder.domImplementation
    val document: Document = imp.createDocument(null, "empleados", null)

    val root: Element = document.documentElement
    root.normalize()

    //Iterar sobre los empleados y crear los nodos del archivoXML
    empleados.forEach{ empleado ->
        //Crea el elemento apellido y luego le pone el texto
        val apellidoNode = document.createElement("apellido")
        val newTextApellido = document.createTextNode(empleado.value.apellido)
        apellidoNode.appendChild(newTextApellido)
        //Lo mismo con departamento
        val departamentoNode = document.createElement("departamento")
        val newTextDepartamento = document.createTextNode(empleado.value.departamento)
        departamentoNode.appendChild(newTextDepartamento)
        //Lo mismo con salario
        val salarioNode = document.createElement("salario")
        val newTextSalario = document.createTextNode(empleado.value.salario.toString())
        salarioNode.appendChild(newTextSalario)


        val empleadoNode = document.createElement("empleado")
        empleadoNode.setAttribute("id",empleado.value.id.toString())
        empleadoNode.appendChild(apellidoNode)
        empleadoNode.appendChild(departamentoNode)
        empleadoNode.appendChild(salarioNode)

        //Aqui es donde se añade el empleado a la lista
        root.appendChild(empleadoNode)

    }

    val source: Source = DOMSource(document)
    val fichToWrite = Path.of("src/main/resources/empleados.xml")
    val result: Result = StreamResult(fichToWrite.toFile())
    val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
    transformer.setOutputProperty(OutputKeys.INDENT, "yes")
    transformer.transform(source, result)
}


