package view;

import controll.Consulta;

public class Principal {

    public static void main(String args[]) {

        int cont = 0;
        String consultar = null;
        while (consultar == null) {
            consultar = Consulta.consultar("11180256603");
            cont++;
            if(consultar != null){
                System.out.println(consultar);
                System.exit(1);
            }
        }

    }
}
