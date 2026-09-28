import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleDeduplicationEngine {
   
    // Master storage: Content Hash -> Actual File Content
    private static Map<Integer, String> cloudStorage = new HashMap<>();
   
    // User files index: File Name -> Content Hash Pointer
    private static Map<String, Integer> userFiles = new HashMap<>();

    public static void uploadFile(String fileName, String content) {
        int hash = content.hashCode();
       
        System.out.println("\n[SYSTEM] Processing upload...");
       
        if (cloudStorage.containsKey(hash)) {
            System.out.println("DUPLICATE DETECTED: This content already exists on the server.");
            System.out.println("ACTION: Skipped physical save. Created a reference pointer.");
        } else {
            cloudStorage.put(hash, content);
            System.out.println("UPLOADED: New unique file saved to server storage.");
        }
       
        userFiles.put(fileName, hash);
        System.out.println("Success: '" + fileName + "' is now ready in your drive.");
    }

    public static void downloadFile(String fileName) {
        if (!userFiles.containsKey(fileName)) {
            System.out.println("\nError: File not found in your drive.");
            return;
        }
       
        int hashPointer = userFiles.get(fileName);
        String content = cloudStorage.get(hashPointer);
       
        System.out.println("\nDownloading [" + fileName + "] from server...");
        System.out.println("Content: " + content);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("=== Simple Cloud Deduplication System ===");
       
        while (true) {
            System.out.println("\n1. Upload File");
            System.out.println("2. Download File");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
           
            String choice = scanner.nextLine();
           
            if (choice.equals("1")) {
                System.out.print("Enter file name: ");
                String name = scanner.nextLine();
                System.out.print("Enter file text content: ");
                String content = scanner.nextLine();
                uploadFile(name, content);
               
            } else if (choice.equals("2")) {
                System.out.print("Enter file name to download: ");
                String name = scanner.nextLine();
                downloadFile(name);
               
            } else if (choice.equals("3")) {
                System.out.println("Exiting program. Goodbye!");
                break;
               
            } else {
                System.out.println("Invalid option. Try again.");
            }
											}
        scanner.close();
    }
}