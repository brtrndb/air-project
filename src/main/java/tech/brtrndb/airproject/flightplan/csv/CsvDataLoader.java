package tech.brtrndb.airproject.flightplan.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import tech.brtrndb.airproject.flightplan.error.CsvDataLoaderException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvDataLoader {

    private static final char CSV_SEPARATOR = ';';

    public static List<CustomerCSV> loadCustomersFromFile(String customerFile) throws CsvDataLoaderException {
        try {
            return CsvUtils.readCsvResource(customerFile, CustomerCSV.class, CSV_SEPARATOR);
        } catch (IOException e) {
            throw new CsvDataLoaderException(customerFile, e);
        }
    }

    public static List<DroneCSV> loadDronesFromFile(String droneFile) throws CsvDataLoaderException {
        try {
            return CsvUtils.readCsvResource(droneFile, DroneCSV.class, CSV_SEPARATOR);
        } catch (IOException e) {
            throw new CsvDataLoaderException(droneFile, e);
        }
    }

    public static List<OrderCSV> loadOrdersFromFile(String orderFile) throws CsvDataLoaderException {
        Resource resource = new ClassPathResource(orderFile);

        try (InputStream is = resource.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr);
        ) {
            // Ignore first line (CSV header).
            String line = reader.readLine();

            List<OrderCSV> orders = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                // Split line at CSV_SEPARATOR.
                List<String> parts = Arrays.stream(line.split(String.valueOf(CSV_SEPARATOR))).toList();

                // Extract, orderId, customerId and item quantity from line.
                String orderId = parts.get(0);
                String customerId = parts.get(1);
                List<String> itemQuantityParts = parts.subList(2, parts.size());

                // Transform line's item quantity part into map(productId, itemQuantity).
                int n = 2;
                Map<String, Integer> items = IntStream.range(0, itemQuantityParts.size())
                        .filter(i -> i % n == 0)                                                                // Filter by partition.
                        .mapToObj(i -> itemQuantityParts.subList(i, Math.min(i + n, itemQuantityParts.size()))) // Sublists of 2 elements: productId and itemQuantity.
                        .collect(Collectors.toMap(l -> l.get(0), l -> Integer.valueOf(l.get(1))));              // Collect as map.

                OrderCSV order = new OrderCSV(orderId, customerId, items);
                orders.add(order);
            }

            return orders;
        } catch (IOException e) {
            throw new CsvDataLoaderException(orderFile, e);
        }
    }

    public static List<ProductCSV> loadProductsFromFile(String productFile) throws CsvDataLoaderException {
        Resource resource = new ClassPathResource(productFile);

        try (InputStream is = resource.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr);
        ) {
            // Ignore first line (CSV header).
            String line = reader.readLine();

            List<ProductCSV> products = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                // Split line at CSV_SEPARATOR.
                List<String> parts = Arrays.stream(line.split(String.valueOf(CSV_SEPARATOR))).toList();

                // Extract, productId, productName and store stock quantity from line.
                String productId = parts.get(0);
                String productName = parts.get(1);
                List<String> stockQuantityParts = parts.subList(2, parts.size());

                // Transform line's store stock part into map(storeId, stockQuantity).
                int n = 2;
                Map<String, Integer> stockQuantity = IntStream.range(0, stockQuantityParts.size())
                        .filter(i -> i % n == 0)                                                                    // Filter by partition.
                        .mapToObj(i -> stockQuantityParts.subList(i, Math.min(i + n, stockQuantityParts.size())))   // Sublists of 2 elements: storeId and stockQuantity.
                        .collect(Collectors.toMap(l -> l.get(0), l -> Integer.valueOf(l.get(1))));                  // Collect as map.

                ProductCSV product = new ProductCSV(productId, productName, stockQuantity);
                products.add(product);
            }

            return products;
        } catch (IOException e) {
            throw new CsvDataLoaderException(productFile, e);
        }
    }

    public static List<StoreCSV> loadStoresFromFile(String storeFile) throws CsvDataLoaderException {
        try {
            return CsvUtils.readCsvResource(storeFile, StoreCSV.class, CSV_SEPARATOR);
        } catch (IOException e) {
            throw new CsvDataLoaderException(storeFile, e);
        }
    }

}
