package view;

import controll.Consulta;

public class Principal {

    public static void main(String args[]) {

        int cont = 0;
//        while (true) {
            String consultar = Consulta.consultar("11180256603");
            cont++;
//            if(consultar != null){
//                System.out.println(consultar);
//                System.exit(1);
//            }
            System.out.println(consultar);
//        }

    }

}
