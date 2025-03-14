import java.util.*;
import java.io.*;

public class Main {
    private static Map<String, Pokemon> pokemonMap;
    private static Set<String> ColeccionUsuario = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escoga una implementacion de MAP: ");
        System.out.println("1. HashMap \n 2. TreeMap \n 3. LinkedHashMap");
        int mapChoice = scanner.nextInt();
        scanner.nextLine();

        MapFactory factory = new MapFactory();
        pokemonMap = factory.createMap(mapChoice);

        String rutaCSV = "pokemon_data_pokeapi.csv";
        cargarDatos(rutaCSV);

        while (true) {
            mostrarMenu();
            int opcion = validarEntradaNumerica(scanner);
            procesarOpcion(opcion, scanner);
        }
    }

    private static void cargarDatos(String filename) {
        File archivo = new File(filename);
        System.out.println("\n[DEBUG] Buscando archivo en: " + archivo.getAbsolutePath());

        try (Scanner fileScanner = new Scanner(archivo)) {
            fileScanner.nextLine(); // Saltar cabecera
            int contador = 0;

            while (fileScanner.hasNextLine()) {
                String[] datos = fileScanner.nextLine().split(",", -1);
                if (datos.length >= 6) {
                    Pokemon p = new Pokemon(
                            datos[0].trim(),
                            datos[1].trim(),
                            datos[5].trim());
                    pokemonMap.put(p.getName(), p);
                    contador++;
                }
            }
            System.out.println("Datos cargados: " + contador + " Pokémon");

        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
            System.exit(1);
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ POKÉMON ===");
        System.out.println("1. Agregar a tu colección");
        System.out.println("2. Consultar datos de Pokémon");
        System.out.println("3. Tu colección ordenada por Tipo");
        System.out.println("4. Todos los Pokémon ordenados por Tipo");
        System.out.println("5. Buscar por habilidad");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void procesarOpcion(int opcion, Scanner scanner) {
        switch (opcion) {
            case 1 -> agregarAColeccion(scanner);
            case 2 -> mostrarDatosPokemon(scanner);
            case 3 -> mostrarColeccionOrdenada();
            case 4 -> mostrarTodosOrdenadosTipo1();
            case 5 -> buscarPorHabilidad(scanner);
            case 6 -> System.exit(0);
            default -> System.out.println("Opción inválida");
        }
    }

    private static void agregarAColeccion(Scanner scanner) {
        System.out.print("Ingrese nombre del Pokémon: ");
        String nombre = scanner.nextLine().trim();

        if (!pokemonMap.containsKey(nombre)) {
            System.out.println("Error: No existe en la Pokédex");
            return;
        }

        if (ColeccionUsuario.add(nombre)) {
            System.out.println("¡Se a añadido a tu colección!");
        } else {
            System.out.println("Ya está en tu colección");
        }
    }

    private static void mostrarDatosPokemon(Scanner scanner) {
        System.out.print("Ingrese nombre del Pokémon: ");
        String nombre = scanner.nextLine().trim();

        Pokemon p = pokemonMap.get(nombre);
        if (p == null) {
            System.out.println("Pokémon no registrado");
        } else {
            System.out.println("\n=== " + nombre.toUpperCase() + " ===");
            System.out.println(p);
        }
    }

    private static void mostrarColeccionOrdenada() {
        if (ColeccionUsuario.isEmpty()) {
            System.out.println("Tu colección está vacía");
            return;
        }

        List<Pokemon> lista = ColeccionUsuario.stream()
                .map(nombre -> pokemonMap.get(nombre))
                .sorted(Comparator
                        .comparing(Pokemon::getType1)
                        .thenComparing(Pokemon::getName))
                .toList();

        System.out.println("\n=== TU COLECCIÓN ===");
        lista.forEach(p -> System.out.println(p.getType1() + " | " + p.getName()));
    }

    private static void mostrarTodosOrdenadosTipo1() {
        List<Pokemon> lista = pokemonMap.values().stream()
                .sorted(Comparator
                        .comparing(Pokemon::getType1)
                        .thenComparing(Pokemon::getName))
                .toList();

        System.out.println("\n=== POKÉDEX COMPLETA ===");
        lista.forEach(p -> System.out.println(p.getType1() + " | " + p.getName()));
    }

    private static void buscarPorHabilidad(Scanner scanner) {
        System.out.print("Ingrese habilidad a buscar: ");
        String habilidad = scanner.nextLine().trim().toLowerCase();

        List<Pokemon> resultados = pokemonMap.values().stream()
                .filter(p -> p.getAbility().toLowerCase().contains(habilidad))
                .toList();

        if (resultados.isEmpty()) {
            System.out.println("Ningún Pokémon tiene esta habilidad");
        } else {
            System.out.println("\n=== RESULTADOS ===");
            resultados.forEach(p -> System.out.println(p.getName() + " - " + p.getAbility()));
        }
    }

    // ======================
    // Utilidades
    // ======================

    private static int validarEntradaNumerica(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida");
            scanner.nextLine();
            System.out.print("Intente nuevamente: ");
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }
}
