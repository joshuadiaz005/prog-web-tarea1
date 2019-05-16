package com.joshua.practica1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int contadorFormuario = 1;
        int formularioInput = 1;

        System.out.println("Por favor, introducir una URL válida: ");

        //Lee la url introducida
        Scanner read = new Scanner(System.in);
        String direccionURL = read.next();

        //Establece la coneccion con la url
        Connection.Response response = Jsoup.connect(direccionURL).execute();

        //Imprime el total de lineas encontradas
        System.out.println("Cantidad de lineas retornado: " + response.body().split("\n").length);

        Document document = Jsoup.connect(direccionURL).get();
        Elements FormsPost = document.select("form[method=POST]");
        int FormPost = FormsPost.size();
        Elements FormsGet = document.select("form[method=GET]");
        int FormGet = FormsGet.size();


        System.out.println("CANTIDAD de parrafos: " + document.getElementsByTag("p").size());

        System.out.println("CANTIDAD de imagenes: " + document.select("p img").size());

        System.out.println("Formularios en el HTML bajo el método GET: "
                + document.select("form[method='GET']").size());

        System.out.println("Formularios en el HTML bajo el método POST: "
                + document.select("form[method='POST']").size()); //

        for(Element formulario : document.getElementsByTag("form")) {
            System.out.println("Formulario -> id = " + contadorFormuario);

            for(Element input : formulario.getElementsByTag("input")) {
                System.out.println("\tInput -> id = " + formularioInput + " tipo: " + input.attr("type")); // hidden, text, file

                formularioInput++;
            }
            contadorFormuario++;
        }

        for (Element post : FormsPost){
            System.out.println("Nombre del form: "+ post.id());
            System.out.println("Metodo = Post");
            Elements Input = document.select("Input");
            System.out.println(Input.eachAttr("type"));
            String url = post.absUrl("action");
            System.out.println(url);
            Connection c = Jsoup.connect(url).data("asignatura", "practica1").method(Connection.Method.POST).header("Matricula", "2015-1712");
            Connection.Response r = c.execute();
            System.out.println("Estado del request: "+r.statusCode()+" "+ r.statusMessage());
            System.out.println("Respuesta: ");
            System.out.println(r.body());

        }
        for (Element get : FormsGet){
            System.out.println("Nombre del form: "+ get.id());
            System.out.println("Metodo = Get");
            Elements Input = document.select("Input");
            System.out.println(Input.eachAttr("type"));
        }



    }
}
