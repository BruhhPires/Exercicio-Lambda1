package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter full file path: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){  // METODO DE LER UM ARQUIVO
			
			List<Product> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}
			
			double avg = list.stream()  // CRIA UMA STREAM PARA DEFINIR A MEDIA
					.map(p -> p.getPrice()) // PEGA O PREÇO DE CADA ITEM
					.reduce(0.0, (x,y) -> x + y ) / list.size(); // COM OS PARAMETROS X E Y APLICA A FUNÇÃO DE MEDIA
			
			System.out.println("Average price: " + String.format("%.2f", avg));
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase()); // CRIA O METODO DE COMPARATOR PRA ORDENAR OS VALORES
			
			List<String> names = list.stream()  // CRIA A STREAM QUE RETORNARÁ COMO LISTA
					.filter(p -> p.getPrice() < avg)  // FILTRA APENAS OS VALORES MENORES QUE A MÉDIA
					.map(p -> p.getName()) // PEGA APENAS O NOME DESSES PRODUTOS
					.sorted(comp.reversed()) // ORDENA DESCRECENTE  COM .REVERSED
					.collect(Collectors.toList()); // RETORNA COMO LISTA
			
			names.forEach(System.out::println); // IMPRIME CADA UM DOS ITENS
		
		}
		catch(IOException e) {
			System.out.println("Error :" + e.getMessage());
		}
		
		sc.close();
	}

}
