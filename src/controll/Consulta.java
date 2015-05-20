package controll;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Consulta {

    public static String consultar(String cpf) {

        try (CloseableHttpClient httpClient = (HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy())).setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).build()) {

            BasicCookieStore cookie = new BasicCookieStore();
            BasicHttpContext contexto = new BasicHttpContext();
            contexto.setAttribute(HttpClientContext.COOKIE_STORE, cookie);
            HttpResponse resposta;
            InputStream entrada;

            HttpGet requisicao1 = new HttpGet("http://www.receita.fazenda.gov.br/Aplicacoes/ATCTA/CPF/captcha/gerarCaptcha.asp");

            resposta = httpClient.execute(requisicao1, contexto);

            HttpEntity entidade = resposta.getEntity();

            entrada = entidade.getContent();

            BufferedImage b = ImageIO.read(entrada);

            Imagem.exibir(b);
//          String captcha = JOptionPane.showInputDialog("Entre com o captcha:");
            String captcha = Captcha.captchaToString(b);
            System.out.println("Tentativa: " + captcha);

            HttpPost requisicao2 = new HttpPost("http://www.receita.fazenda.gov.br/Aplicacoes/ATCTA/CPF/ConsultaPublicaExibir.asp");

            List<NameValuePair> nameValuePairs = new ArrayList<>();

            nameValuePairs.add(new BasicNameValuePair("txtCPF", cpf));
            nameValuePairs.add(new BasicNameValuePair("txtTexto_captcha_serpro_gov_br", captcha));
            nameValuePairs.add(new BasicNameValuePair("Enviar", "Consultar"));

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "ISO-8859-1");

            requisicao2.setEntity(urlEncodedFormEntity);

            resposta = httpClient.execute(requisicao2, contexto);

            entidade = resposta.getEntity();

            entrada = entidade.getContent();

            BufferedReader in = new BufferedReader(new InputStreamReader(entrada, "ISO-8859-1"));

            StringBuilder str = new StringBuilder();

            while (in.ready()) {
                str.append((char) in.read());
            }

            Document parse = Jsoup.parse(str.toString(), "ISO-8859-1");

            Element element = null;

            if ((element = parse.getElementById("idMensagemErro")) != null) {
                return element.text();
            } else {
                return parse.getElementsByClass("clConteudoEsquerda").text();
            }

        } catch (IOException e) {
            return null;
        }
    }

}
