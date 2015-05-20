package view;

import controll.Consulta;
import java.util.Scanner;

public class Principal {

    public static void main(String args[]) {

        System.out.print("Entre com o cpf: ");
        Scanner sc = new Scanner(System.in);
        String consultar = Consulta.consultar(sc.nextLine());
        if (consultar != null) {
            System.out.println(consultar);
        } else {
            System.out.println("Não foi possivel acessar o site!");
        }

    }

}
