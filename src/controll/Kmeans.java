package controll;

import java.awt.image.BufferedImage;
import model.Centroide;
import model.ClassificadorCentroide;
import model.Pixel;

public class Kmeans extends ClassificadorCentroide {
    
	public Kmeans(BufferedImage imagem, int nrClasses) {
		super(imagem, nrClasses);
		processar(imagem);
	}

	@Override
	protected void associarAoCentroide(Pixel pixel) {
            
		double menorDistancia = centroides.get(0).distancia(pixel);
		Centroide maisProximo = centroides.get(0);
		
		for (int i = 1; i < centroides.size(); i++) {
			double distancia = centroides.get(i).distancia(pixel);
			if (distancia < menorDistancia) {
				menorDistancia = distancia;
				maisProximo = centroides.get(i);
			}
		}
		maisProximo.adicionarPonto(pixel);
	}
}
