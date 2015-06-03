package view;

import controll.Consulta;

public class Principal {

    public static void main(String args[]) {

        String consultar = Consulta.consultar("11180256603", "21/04/1993");
        if (consultar != null) {
            System.out.println(consultar);
        } else {
            System.out.println("Não foi possivel acessar o site!");
        }

    }

}
