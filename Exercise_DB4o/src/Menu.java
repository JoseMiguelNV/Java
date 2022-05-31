import java.util.Scanner;

public class Menu {

    static Scanner sc = new Scanner(System.in);
    public void Run(){

        String option = ShowMenu.showMenu();
        while (!option.equals("0")) {
            switch (option) {
                case "1": {
                    String option2 = ShowMenu.addItem();
                    while (!option2.equals("0")) {
                        switch (option2) {
                            case "1": {
                                ShowMenu.addAuthor();
                                Controller.addAuthor();
                                break;
                            }
                            case "2": {
                                ShowMenu.addArtWork();
                                Controller.addArtWork();
                                break;
                            }
                            case "3": {
                                ShowMenu.addPainting();
                                Controller.addPainting();
                                break;
                            }
                            case "4": {
                                ShowMenu.addSculpture();
                                Controller.addSculpture();
                                break;
                            }
                        }
                        option2 = ShowMenu.addItem();
                    }
                    System.out.println();
                    break;
                }
                case "2": {
                    ShowMenu.showArtWorkByAuthor();
                    Controller.showArtWorkByAtuthor();
                    System.out.println();
                    break;
                }
                case "3": {
                    String option3 = ShowMenu.listArtWorks();
                    while (!option3.equals("0")) {
                        switch (option3) {
                            case "1": {
                                ShowMenu.paintingType();
                                Controller.paintingTypeArtWork();
                                break;
                            }
                            case "2": {
                                ShowMenu.materialType();
                                Controller.materialTypeArtWork();
                                break;
                            }
                            case "3": {
                                ShowMenu.style();
                                Controller.styleArtWork();
                                break;
                            }
                        }
                        option3 = ShowMenu.listArtWorks();
                    }
                    System.out.println();
                    break;
                }
            }
            option = ShowMenu.showMenu();
        }
    }
}
