import java.io.*;
import java.util.*;

public class AddressBook {
    private Map<String, String> contacts;
    private static final String FILE_NAME = "contacts.csv";

    public AddressBook() {
        contacts = new HashMap<>();
        load();
    }

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println(">>No se pudo cargar el archivo o aún no existe<<");
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(">>Error al guardar los contactos<<");
        }
    }

    public void list() {
        System.out.println(">>>Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
        save();
        System.out.println(">>Contacto agregado<<");
    }

    public void delete(String number) {
        if (contacts.remove(number) != null) {
            save();
            System.out.println(">>Contacto eliminado<<");
        } else {
            System.out.println(">>El contacto no existe<<");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        while (true) {
            System.out.println("\n>>>Menú:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Agregar contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print(">>>Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    String number;
                    while (true) {
                        System.out.print(">>>Ingrese el número (10 dígitos): ");
                        number = scanner.nextLine();
                        if (number.matches("\\d{10}")) {
                            break;
                        } else {
                            System.out.println(">>Error: El número debe tener exactamente 10 dígitos y solo contener números<<");
                        }
                    }
                    System.out.print(">>>Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    System.out.print(">>>Ingrese el número a eliminar: ");
                    String numToDelete = scanner.nextLine();
                    addressBook.delete(numToDelete);
                    break;
                case 4:
                    System.out.println(">>>Saliendo...");
                    return;
                default:
                    System.out.println(">>Opción no válida<<");
            }
        }
    }
}
