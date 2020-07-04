package com.unitec.rogery;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Representational State Transfer Controller
//Los estados mas comunes son: guardar, buscar, actrualizar y borrar
@RestController
//API(Application Programming Interface)
@RequestMapping("/api")

public class ControladorPerfil {
    
    //Esta es la inversión de control o inyección de dependencias
    @Autowired RepoPerfil repoPerfil;

    //en los servicios red se tiene una url base que consiste de la ip o host,
    //seguida del puerto, despues /api/hola
    //http://localhost:8080/api/hola
    @GetMapping("/hola")
    
    public Saludo saludar(){
        Saludo s=new Saludo();
        s.setNombre("Emmanuel");
        s.setMensaje("Mi primer mensaje en Spring Rest");
        return s;
    }
    
    //El siguiente metodo va a servir para guardar en un back-end nuestros datos de perfil
    //Para guardar siempre debes usar el metodo POST
    @PostMapping("/perfil")
    public Estatus guardar(@RequestBody String json) throws Exception{
        
        /*Paso 1: Para recibir ese objeto json es leerlo y convertirlo en objeto java
        a esto se le llama des-serializacion*/
        ObjectMapper maper= new ObjectMapper();
            Perfil perfil= maper.readValue(json, Perfil.class);
            
            /*Por experiencia antes de guardar tenemos que checar que llego bien todo el
            objeto y se leyo bien*/
            System.out.println("Perfil leido " +perfil);
            
            //Aqui este objeto perfil despues se guarda con una sola linea en mongodb
            //Linea
            repoPerfil.save(perfil);
            /*Despues enviamos un mensaje de estatus al cliente para que se informe
            si se guardo o no su perfil */
            Estatus e= new Estatus();
            e.setSuccess(true);
            e.setMensaje("Perfil guardado con exito!!!");
            return e;
    }
    
    //Vamos a generar nuestro servicio para actualizar un perfil
    @PutMapping("/perfil")
    public Estatus actualizar (@RequestBody String json)throws Exception{
        ObjectMapper maper= new ObjectMapper();
            Perfil perfil= maper.readValue(json, Perfil.class);
            
            System.out.println("Perfil leido " +perfil);
            
            repoPerfil.save(perfil);
            
            Estatus e= new Estatus();
            e.setSuccess(true);
            e.setMensaje("Perfil actualizado!!!");
            return e;
    }
    
    //Metodo de borrar perfil
    @DeleteMapping("/perfil/{id}")
    public Estatus borrar (@PathVariable String id){
        //Invocamos el repositorio
        repoPerfil.deleteById(id);
        
        //Generamos un mensaje de estatus para que se informe el cliente
        Estatus e= new Estatus();
        e.setMensaje("Perfil borrado con éxito");
        e.setSuccess(true);
        return e;
    }   
    
    //Metodo para buscar todo
    @GetMapping("/perfil")
    public List<Perfil> buscarTodos(){
        return repoPerfil.findAll();
    }
    
    //Metodo para buscar por ID
    @GetMapping("/perfil/{id}")
    public Perfil buscarID(@PathVariable String id){
        return repoPerfil.findById(id).get();
    }
}
/*A este tipo de controlador REST es muy poderoso y se usa en todas las arquitecturas
estilo REST y se le denomina: CONSTRUCCIÓN DE API'S*/

//API: Application Programming Interface 
//La interface es la unión entre cliente(android) y servidor(java))
        

