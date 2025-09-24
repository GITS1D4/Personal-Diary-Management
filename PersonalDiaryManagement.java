import java.io.*;
import java.util.*;

class DiaryEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private String date;
    private String title;
    private String content;

    public DiaryEntry(String date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nTitle: " + title + "\nContent: " + content + "\n";
    }
}

public class PersonalDiaryManagement {
    private static final String FILE_NAME = "diaryEntries.ser";
    private static List<DiaryEntry> diaryEntries = new ArrayList<>();

    public static void main(String[] args) {
        loadEntries();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPersonal Diary Management System");
            System.out.println("1. Add Entry");
            System.out.println("2. View Entry");
            System.out.println("3. Delete Entry");
            System.out.println("4. List All Entries");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEntry(scanner);
                    break;
                case 2:
                    viewEntry(scanner);
                    break;
                case 3:
                    deleteEntry(scanner);
                    break;
                case 4:
                    listEntries();
                    break;
                case 5:
                    saveEntries();
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addEntry(Scanner scanner) {
        System.out.print("Enter date (e.g., 2024-11-11): ");
        String date = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        diaryEntries.add(new DiaryEntry(date, title, content));
        System.out.println("Diary entry added successfully!");
    }

    private static void viewEntry(Scanner scanner) {
        System.out.print("Enter date to view (e.g., 2024-11-11): ");
        String date = scanner.nextLine();

        for (DiaryEntry entry : diaryEntries) {
            if (entry.getDate().equals(date)) {
                System.out.println("\n" + entry);
                return;
            }
        }
        System.out.println("No entry found for the specified date.");
    }

    private static void deleteEntry(Scanner scanner) {
        System.out.print("Enter date to delete (e.g., 2024-11-11): ");
        String date = scanner.nextLine();

        Iterator<DiaryEntry> iterator = diaryEntries.iterator();
        while (iterator.hasNext()) {
            DiaryEntry entry = iterator.next();
            if (entry.getDate().equals(date)) {
                iterator.remove();
                System.out.println("Diary entry deleted successfully!");
                return;
            }
        }
        System.out.println("No entry found for the specified date.");
    }

    private static void listEntries() {
        if (diaryEntries.isEmpty()) {
            System.out.println("No diary entries available.");
        } else {
            System.out.println("\nAll Diary Entries:");
            for (DiaryEntry entry : diaryEntries) {
                System.out.println(entry);
            }
        }
    }

    private static void saveEntries() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(diaryEntries);
            System.out.println("Diary entries saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving diary entries: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadEntries() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            diaryEntries = (List<DiaryEntry>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, initialize empty diary
            diaryEntries = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading diary entries: " + e.getMessage());
            diaryEntries = new ArrayList<>();
        }
    }
}
