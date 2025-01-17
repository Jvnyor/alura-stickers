import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
	public static void main(String[] args) throws Exception {
		// fazer uma conexão HTTP e buscar o top 250 filmes
		String url = "https://alura-imdb-api.herokuapp.com/movies";
		URI endereco = URI.create(url);
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(endereco).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String body = response.body();

		// extrair só os dados que interessam (titulo, poster e a classificação)
		var parser = new JsonParser();
		List<Map<String, String>> listaDeFilmes = parser.parse(body);
		
		// exibir e manipular os dados

		for (Map<String,String> filme : listaDeFilmes) {
			System.out.println("Título: " + filme.get("title"));
			System.out.println("Pôster: " + filme.get("image"));
			System.out.println("Classificação: " + filme.get("imDbRating"));
			Double classificacao = Double.parseDouble(filme.get("imDbRating"));
			String[] estrelas = new String[classificacao.intValue()];
			for (int i = 0; i < classificacao.intValue(); i++) {
				estrelas[i] = "⭐";
			}
			System.out.println(String.join(" ", estrelas));
			System.out.println();
		}
	}
}
