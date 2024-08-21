import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SalesReport {
    public static void main(String[] args) {
        String path = "C:\\Users\\apaca\\IdeaProjects\\DesafioVenda2\\src\\base-de-dados.csv";

        List<Sale> sales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // Pular o cabeçalho
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);

                sales.add(new Sale(month, year, seller, items, total));
            }

            Set<String> sellers = sales.stream()
                    .map(Sale::getSeller)
                    .collect(Collectors.toSet());

            System.out.println("Total de vendas por vendedor:");
            for (String seller : sellers) {
                Double totalSales = sales.stream()
                        .filter(s -> s.getSeller().equals(seller))
                        .mapToDouble(Sale::getTotal)
                        .sum();
                System.out.printf("%s - R$ %.2f%n", seller, totalSales);
            }

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
