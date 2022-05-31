import java.util.Scanner;

public class ShowMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void input() {
        do {
            System.out.print("Enter the option: ");
        } while (!scanner.hasNextLine());
        System.out.println();
    }

    public static String showMenu(){
        System.out.println("------------------ M E N U -------------------");
        System.out.println();
        System.out.println("1) Add item ");
        System.out.println("2) Search ArtWork by Author ");
        System.out.println("3) List artworks from a specific category ");
        System.out.println("0) Exit ");
        System.out.println();

        input();
        return (scanner.nextLine());
    }

    public static String addItem(){
        System.out.println("-------------- A D D    I T E M --------------");
        System.out.println();
        System.out.println("1) Add Author ");
        System.out.println("2) Add ArtWork ");
        System.out.println("3) Add Painting ");
        System.out.println("4) Add Sculpture ");
        System.out.println("0) Exit ");
        System.out.println();
        System.out.println();

        input();
        return (scanner.nextLine());
    }

    public static void addAuthor(){
        System.out.println();
        System.out.println("------------- A D D   A U T H O R -------------");
        System.out.println();
    }

    public static void addArtWork(){
        System.out.println();
        System.out.println("------------ A D D   A R T W O R K ------------");
        System.out.println();
    }

    public static void addPainting(){
        System.out.println();
        System.out.println("----------- A D D   P A I N T I N G -----------");
        System.out.println();
    }

    public static void addSculpture(){
        System.out.println();
        System.out.println("---------- A D D   S C U L P T U R E ----------");
        System.out.println();
    }

    public static void showArtWorkByAuthor(){
        System.out.println();
        System.out.println("----------- S H O W   A R T W O R K   B Y   A U T H O R -----------");
        System.out.println();
    }

    public static String listArtWorks() {
        System.out.println();
        System.out.println("--------------- C A T E G O R I E S ---------------");
        System.out.println();
        System.out.println("1) Painting Type ");
        System.out.println("2) Material Type ");
        System.out.println("3) Style ");
        System.out.println("0) Exit ");
        System.out.println();

        input();
        return (scanner.nextLine());
    }

    public static void paintingType(){
        System.out.println();
        System.out.println("------------ P A I N T I N G   T Y P E ------------");
        System.out.println();
    }

    public static void materialType(){
        System.out.println();
        System.out.println("------------ M A T E R I A L   T Y P E ------------");
        System.out.println();
    }

    public static void style(){
        System.out.println();
        System.out.println("-------------------- S T Y L E --------------------");
        System.out.println();
    }
}
