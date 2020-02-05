import java.util.Scanner;

class EssaiScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Saisissez une ligne");
        String ligne = scanner.nextLine();
        System.out.println("Vous avez saisi la ligne : " + ligne);
    }
}
