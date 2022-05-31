import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Controller {

    static Scanner sc = new Scanner(System.in);

    //METHOD FOR EMULATED TRY PARSE INT
    public static Integer parseIntOption() {
        String codeArtWork;
        do {
            System.out.print("Enter code ArtWork: ");
            codeArtWork = sc.nextLine();
            try {
                return Integer.parseInt(codeArtWork);
            } catch (NumberFormatException e) {
                System.out.println("Invalid format, enter an integer.");
            }
        }while(true);
    }

    //METHOD FOR CHECK IF FIELD OF CODE AUTHOR IS NOT NULL
    public static String checkCodeAuthor(){
        String codeAuthor;
        do{
            System.out.print("Enter code Author: ");
            codeAuthor = sc.nextLine();
            if(codeAuthor.equals(""))
                System.out.print("Code not inserted... ");
        }while(codeAuthor.equals(""));
        return codeAuthor;
    }

    //METHOD FOR CHECK CODE AUTHOR
    public static boolean searchCodeAuthor(String codeAuthor){
        boolean exists;
        ObjectContainer db = null;
        try {
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            Author author = new Author(codeAuthor, null, null);
            if(!db.get(author).hasNext()){
                exists = false;
            }else{
                System.out.println("The Author exists in the database.");
                exists = true;
            }
        }
        finally {
            if (db != null){
                db.close();
            }
        }
        return exists;
    }

    //METHOD FOR CHECK CODE ARTWORK
    public static boolean searchCodeArtWork(int codeArtWork){
        boolean exists;
        ObjectContainer db = null;
        try {
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            ArtWork artWork = new ArtWork(codeArtWork, null, null, null, null);
            if (!db.get(artWork).hasNext()) {
                exists = false;
            } else {
                System.out.println("The artWork exists in the database.");
                exists = true;
            }
        }finally {
            if (db != null){
                db.close();
            }
        }
        return exists;
    }

    //METHOD FOR INSERT THE DATA OF ARTWORKS
    public static ArtWork enterDataArtWork(){
        String dateFormat = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)" +
                "(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)" +
                "(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$" +
                "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        String codeAuthor, style, title, date;
        int codeArtWork;
        LocalDate dateArtWork;
        do {
            codeArtWork = parseIntOption();
        } while(searchCodeArtWork(codeArtWork));
        do{
            System.out.print("Enter title: ");
            title = sc.nextLine();
            if(title.equals(""))
                System.out.print(("Title not inserted... "));
        }while(title.equals(""));
        do{
            System.out.print("Enter date 'dd/MM/yyyy': ");
            date = sc.nextLine();
            if(date.equals("") || !date.matches(dateFormat))
                System.out.print("Date not inserted... ");
        }while(!date.matches(dateFormat));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateArtWork = LocalDate.parse(date, format);
        do{
            System.out.print("Enter Style, select between grecoRoman, neoClassic or Cubism: ");
            style = sc.nextLine();
        }while(!Styles.ifExist(style));
        Styles styleArtWork = Styles.valueOf(style);
        do{
            codeAuthor = checkCodeAuthor();
        }while(!searchCodeAuthor(codeAuthor));
        ArtWork artWork = new ArtWork(codeArtWork, title, dateArtWork, styleArtWork, codeAuthor);
        return artWork;
    }



    //------------------------------------ METHODOLOGIES SECTION ONE ------------------------------------------

    //ADD AUTHORS
    public static void addAuthor(){
        String codeAuthor, nameAuthor, nationality;
        do{
            codeAuthor = checkCodeAuthor();
        }while(searchCodeAuthor(codeAuthor));
        do{
            System.out.print("Enter name: ");
            nameAuthor = sc.nextLine();
            if(nameAuthor.equals(""))
                System.out.print("Name not inserted... ");
        }while(nameAuthor.equals(""));
        do{
            System.out.print("Enter nationality: ");
            nationality = sc.nextLine();
            if(nationality.equals(""))
                System.out.print("Nationality not inserted... ");
        }while(nationality.equals(""));
        Author author = new Author(codeAuthor, nameAuthor, nationality);
        ObjectContainer db = null;
        try {
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            db.set(author);
            db.commit();
        }
        finally {
            if (db != null){
                db.close();
            }
        }
        System.out.println();
        System.out.println("Author added correctly!");
        System.out.println();
    }

    //ADD ARTWORKS
    public static void addArtWork() {
        ArtWork artWork = enterDataArtWork();
        ObjectContainer db = null;
        try {
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            db.set(artWork);
            db.commit();
        }
        finally {
            if (db != null){
                db.close();
            }
        }
        System.out.println();
        System.out.println("ArtWork added correctly!");
        System.out.println();
    }

    //ADD PAINTINGS
    public static void addPainting(){
        ArtWork artWork = enterDataArtWork();
        String width_regex = "^[0-9]+([.]{1}[0-9]+)?$";
        String height_regex = "^[0-9]+([.]{1}[0-9]+)?$";
        double width, height;
        String paint, widths, heights;
        do{
            System.out.print("Enter Painting type, select between oilPainting, waterColour or pastel: ");
            paint = sc.nextLine();
        }while(!PaintingTypes.ifExist(paint));
        PaintingTypes paintingType = PaintingTypes.valueOf(paint);
        do{
            System.out.print("Enter width: ");
            widths = sc.nextLine();
            if(widths.equals("") || !widths.matches(width_regex))
                System.out.print("Width not inserted... ");
        }while(!widths.matches(width_regex));
        width = Double.parseDouble(widths);
        do{
            System.out.print("Enter height: ");
            heights = sc.nextLine();
            if(heights.equals("") || !heights.matches(height_regex))
                System.out.print("Height not inserted...");
        }while(!heights.matches(height_regex));
        height = Double.parseDouble(heights);

        DimensionsType dimensionType = new DimensionsType(width, height);
        Painting painting = new Painting(artWork.getCodeArtWork(), artWork.getTitle(), artWork.getDated(), artWork.getStyle(), artWork.getAuthor(), paintingType, dimensionType);
        ObjectContainer db = null;
        try {
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            db.set(painting);
            db.commit();
        }
        finally {
            if (db != null){
                db.close();
            }
        }
        System.out.println();
        System.out.println("Painting added correctly!");
        System.out.println();
    }

    //ADD SCULPTURES
    public static void addSculpture(){
        ArtWork artWork = enterDataArtWork();
        String weigth_regex = "^[0-9]+([.]{1}[0-9]+)?$";
        String material, weigths;
        double weigth;
        do{
            System.out.print("Enter Material type, select between iron, bronze or marble: ");
            material = sc.nextLine();
        }while(!MaterialTypes.ifExist(material));
        MaterialTypes materialType = MaterialTypes.valueOf(material);
        do{
            System.out.print("Enter weigth: ");
            weigths = sc.nextLine();
            if(weigths.equals("") || !weigths.matches(weigth_regex))
                System.out.print("Weigth not inserted... ");
        }while(!weigths.matches(weigth_regex));
        weigth = Double.parseDouble(weigths);
        Sculpture sculpture = new Sculpture(artWork.getCodeArtWork(), artWork.getTitle(), artWork.getDated(), artWork.getStyle(), artWork.getAuthor(), materialType, weigth);
        ObjectContainer db = null;
        try {
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            db.set(sculpture);
            db.commit();
        }
        finally {
            if (db != null){
                db.close();
            }
        }
        System.out.println();
        System.out.println("Sculpture added correctly!");
        System.out.println();
    }



    //----------------------------------- METHODOLOGIES SECTION TWO -----------------------------------------

    //GET ARTWORKS BY AUTHOR
    public static void showArtWorkByAtuthor(){
        System.out.print("Enter code author: ");
        String codeAuthor = sc.nextLine();
        ObjectContainer db = null;
        try{
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            ObjectSet<Object> artWork = db.get(new ArtWork(0, null, null, null, codeAuthor));
            if(artWork.hasNext()){
                System.out.println();
                while (artWork.hasNext()){
                    System.out.println(artWork.next());
                }
                System.out.println();
            }else{
                System.out.println();
                System.out.println("This Author don't have any artwork.");
                System.out.println();
            }
        }  finally {
            if (db != null)
                db.close();
        }
    }



    //---------------------------------- METHODOLOGIES SECTION THREE ---------------------------------------

    //GET ARTWORKS BY PAINTING TYPES
    public static void paintingTypeArtWork(){
        String paint;
        do{
            System.out.print("Enter Painting type, select between oilPainting, waterColour or pastel: ");
            paint = sc.nextLine();
        }while(!PaintingTypes.ifExist(paint));
        PaintingTypes paintingType = PaintingTypes.valueOf(paint);
        ObjectContainer db = null;
        try{
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            ObjectSet<Object> painting = db.get(new Painting(0, null, null, null, null, paintingType, null));
            if(painting.hasNext()) {
                System.out.println();
                while (painting.hasNext()) {
                    System.out.println(painting.next());
                }
                System.out.println();
            }
            else{
                System.out.println();
                System.out.println("There are not artWorks with the selected painting type");
                System.out.println();
            }
        }  finally {
            if (db != null)
                db.close();
        }
    }

    //GET ARTWORKS BY MATERIAL TYPES
    public static void materialTypeArtWork(){
        String material;
        do{
            System.out.print("Enter Material type, select between iron, bronze or marble: ");
            material = sc.nextLine();
        }while(!MaterialTypes.ifExist(material));
        MaterialTypes materialType = MaterialTypes.valueOf(material);
        ObjectContainer db = null;
        try{
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            ObjectSet<Object> sculpture = db.get(new Sculpture(0, null, null, null, null, materialType, 0));
            if(sculpture.hasNext()) {
                System.out.println();
                while (sculpture.hasNext()) {
                    System.out.println(sculpture.next());
                }
                System.out.println();
            }
            else{
                System.out.println();
                System.out.println("There are not artWorks with the selected material type");
                System.out.println();
            }
        }  finally {
            if (db != null)
                db.close();
        }
    }

    //GET ARTWORKS BY STYLES
    public static void styleArtWork(){
        String style;
        do{
            System.out.print("Enter Style, select between grecoRoman, neoClassic or Cubism: ");
            style = sc.nextLine();
        }while(!Styles.ifExist(style));
        Styles styleArtWork = Styles.valueOf(style);
        ObjectContainer db = null;
        try{
            db = Db4o.openFile("ArtWorksCollectionDB.dat");
            ObjectSet<Object> artWork = db.get(new ArtWork(0, null, null, styleArtWork, null));
            if(artWork.hasNext()) {
                System.out.println();
                while (artWork.hasNext()) {
                    System.out.println(artWork.next());
                }
                System.out.println();
            }
            else{
                System.out.println();
                System.out.println("There are not artWorks with the selected style");
                System.out.println();
            }
        }  finally {
            if (db != null)
                db.close();
        }
    }
}
