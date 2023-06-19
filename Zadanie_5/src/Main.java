import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.*;
import java.util.Collections;
import java.lang.Math;

public class Main {

    public static abstract class Figure{
        public String label;

        String getLabel(){
            return label;
        }
        void setLabel(String label){
            this.label = label;
        }


        void move(double dx, double dy){}

        double getArea(){
            return 0;
        }

        double getDistanceFromOrigin() {
            return 0;
        }
    }

    public static class Point extends Figure{
        private double x;
        private double y;
        public Point() {
            this.x = 0.00;
            this.y = 0.00;
            this.label = "no label set";
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
            this.label = "no label set";
        }

        public Point(double x, double y, String label){
            this.x = x;
            this.y = y;
            this.label = label;
        }

        public void move(double dx, double dy) {
            x+=dx; y+=dy;
        }

        @Override
        public String toString(){
            if (label == "no label set")
                return "Punkt> x:" + x + ", y:" + y + "\n";
            else
                return "Punkt> x:" + x + ", y:" + y + " - " + label + " -\n";
        }

        public double getArea(){
            return 0;
        }

        public double getDistanceFromOrigin(){
            double distance;
            distance = Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));

            return distance;
        }
    }

    public static class Section extends Figure{
        private Point pointA;
        private Point pointB;
        public Section() {
            this.pointA.x = 0;
            this.pointA.y = 0;
            this.pointB.x = 0;
            this.pointB.y = 0;
            this.label = "no label set";
        }

        public Section(Point pointA, Point pointB) {
            this.pointA = pointA;
            this.pointB = pointB;
            this.label = "no label set";
        }

        public Section(Point pointA, Point pointB, String label) {
            this.pointA = pointA;
            this.pointB = pointB;
            this.label = label;
        }

        public void move(double dx, double dy){
            pointA.move(dx, dy);
            pointB.move(dx, dy);
        }

        @Override
        public String toString(){
            if(label == "no label set")
                return "Odcinek> { 1: "+pointA+" 2: "+pointB+" }\n";
            else
                return "Odcinek> { 1: "+ pointA +" 2: "+pointB+" } - " + label + " -\n";
        }
        public double getArea(){
            return 0;
        }

        public double getDistanceFromOrigin() {
            double distance;
            distance = Math.sqrt(Math.pow((this.pointA.x+this.pointB.x)/2,2)+Math.pow((this.pointA.y+this.pointB.y)/2,2));

            return distance;
        }
    }

    public static abstract class absCircle extends Figure{
        abstract public void move(double dx, double dy);

        abstract public String toString();

        abstract public double getArea();
    }

    public static class Circle extends absCircle implements Fillable, Scalable{
        private Point srodek;
        private double promien;
        private String color;

        public Circle() {
            this.srodek.x = 0;
            this.srodek.y = 0;
            this.promien = 0;
            this.label = "no label set";
            this.color = "no_color";
        }

        public Circle(Point srodek, double promien) {
            this.srodek = srodek;
            this.promien = promien;
            this.label = "no label set";
        }

        public Circle(Point srodek, double promien, String label){
            this.srodek = srodek;
            this.promien = promien;
            this.label = label;
        }

        public void move(double dx, double dy) {
            srodek.move(dx, dy);
        }

        @Override
        public String toString() {
            if(label == "no label set")
                return "Koło> { Środek = " + srodek.toString() + " Promień = " + this.promien + " }\n";
            else
                return "Koło> { Środek = " + srodek.toString() + " Promień = " + this.promien + " } - " + label + " -\n";
        }

        public double getArea(){
            return Math.pow(this.promien,2)*Math.PI;
        }

        public double getDistanceFromOrigin(){
            double distance;
            distance = Math.sqrt(Math.pow(this.srodek.x,2)+Math.pow(this.srodek.y,2));

            return distance;
        }

        @Override
        public void fill(int color) {
            if (color == 1){
                this.color = "red";
            }
            else if (color == 2){
                this.color = "green";
            }
            else if (color == 3){
                this.color = "blue";
            }
            else {
                System.out.println("Error> invalid_color");
            }
        }

        @Override
        public void scalePerimeter(double k) {
            double newPerimeter, oldPerimeter;
            double newPromien = this.promien;

            oldPerimeter = 2 * Math.PI * this.promien;
            newPerimeter = oldPerimeter * k;

            newPromien = newPerimeter/(2 * Math.PI * k);

            this.promien = newPromien;
        }
    }

    static class LabelComparator implements Comparator<Figure> {

        @Override
        public int compare(Figure o1, Figure o2){
            return o1.getLabel().compareTo(o2.getLabel());
        }
    }

    static class OriginDistanceComparator implements Comparator<Figure>{

        @Override
        public int compare(Figure o1, Figure o2){
            if (o1.getDistanceFromOrigin() < o2.getDistanceFromOrigin()){
                return -1;
            }

            else if ((o1.getDistanceFromOrigin() > o2.getDistanceFromOrigin())){
                return 1;
            }

            else return 0;
        }
    }

    static class ClassNameComparator implements Comparator<Figure>{

        @Override
        public int compare(Figure o1, Figure o2){
            return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
        }
    }

    interface Fillable{
        void fill(int color);
    }

    interface Scalable{
        void scalePerimeter(double k);
    }

    public static abstract class Picture extends Figure{

        public ArrayList<Figure> elements;

        public Picture(){
            this.elements = new ArrayList<>();
        }

        abstract boolean addElement(Figure element);

        @Override
        public void move(double dx, double dy) {
            for (Figure shape : elements) {
                shape.move(dx, dy);
            }
        }

        public double getArea(){
            double area = 0.00;
            for (Figure s : elements){
                area += s.getArea();
            }
            return area;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Obraz:\n");

            sb.append("Elementy:\n");
            for (Figure s : elements) {
                sb.append(s.toString());
            }
            return sb.toString();
        }

        public String toStringSortedByLabel(){
            ArrayList<Figure> copy = new ArrayList<>(elements);
            Collections.sort(copy,new LabelComparator());

            Collections.reverse(copy);

            return copy.toString();
        }

        public String toStringSortedByDistanceFromOrigin(){
            ArrayList<Figure> copy = new ArrayList<>(elements);
            Collections.sort(copy,new OriginDistanceComparator());
            return copy.toString();
        }

        public String toStringSortedByClassName(){
            ArrayList<Figure> copy = new ArrayList<>(elements);
            Collections.sort(copy,new ClassNameComparator());

            return copy.toString();
        }

    }
    public static class UniquePicture extends Picture{

        @Override
        public boolean addElement(Figure element) {
            for (Figure s : elements) {
                if (s.getLabel().equals(element.getLabel())) {
                    System.out.println("Błąd. Ta etykieta już istnieje");
                    return false;
                }
            }
            elements.add(element);
            return true;
        }

    }
    public static class StandarizedPicture extends Picture{
        public boolean addElement(Figure element) {
            String tag = element.getLabel();
            Pattern labelPattern = Pattern.compile("^[A-Z][A-Z0-9]*$");         //sprawdzić poprawność kompilacji Pattern
            Matcher labelMatch = labelPattern.matcher(tag);
            if (labelMatch.matches()) {
                elements.add(element);
                return true;
            }
            System.out.println(tag+" zawiera niedozwolone znaki");
            return false;
        }
    }


    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);
        String opcja;
        int wybierzObraz;

        UniquePicture picture = new UniquePicture();
        StandarizedPicture StdPicture = new StandarizedPicture();

        do {
            opcja = "";
            wybierzObraz = 0;

            System.out.println("1. Dodaj do obrazu\n2. Wyświetl Obraz\n3. Przesuń Obraz\n4. Wyświetl Sumę Pól\nw Wyjdź\nWybierz>");
            opcja = scanner.nextLine();
            switch (opcja) {
                case "1":
                    System.out.println("Do którego obrazu chcesz dodać?\n1 UniquePicture\n2 StandarizedPicture\nWybierz>");
                    wybierzObraz=scanner.nextInt();
                    scanner.nextLine();
                    if(wybierzObraz == 1 || wybierzObraz == 2){
                        opcja = "";
                        System.out.println("1 Punkt\n2 Odcinek\n3 Okrąg\nWybierz>");
                        opcja = scanner.nextLine();
                        switch (opcja) {

                            case "1":
                                System.out.println("Wprowadź koordynat x:");
                                double x = scanner.nextDouble();
                                System.out.println("Wprowadź koordynat y:");
                                double y = scanner.nextDouble();
                                System.out.println("Dodaj label (opcjonalne, ENTER by pominąć)");
                                scanner.nextLine();
                                String labelPoint = scanner.nextLine();

                                Point p = new Point(x, y, labelPoint);
                                if(wybierzObraz==1){
                                    picture.addElement(p);
                                }else if(wybierzObraz==2){
                                    StdPicture.addElement(p);
                                }
                                wybierzObraz=0;
                                break;

                            case "2":
                                System.out.println("Wprowadź koordynaty początku nowego odcinka:");
                                System.out.print("Wprowadź x: ");
                                double xA = scanner.nextDouble();
                                System.out.print("Wprowadź y: ");
                                double yA = scanner.nextDouble();

                                System.out.println("Wprowadź koordynaty końca nowego odcinka:");
                                System.out.print("Wprowadź x: ");
                                double xB = scanner.nextDouble();
                                System.out.print("Wprowadź y: ");
                                double yB = scanner.nextDouble();
                                System.out.println("Dodaj label (opcjonalne, ENTER by pominąć)");
                                scanner.nextLine();
                                String labelSection = scanner.nextLine();
                                Point p1 = new Point(xA, yA);
                                Point p2 = new Point(xB, yB);

                                Section se = new Section(p1, p2, labelSection);
                                if(wybierzObraz==1){
                                    picture.addElement(se);
                                }else if(wybierzObraz==2){
                                    StdPicture.addElement(se);
                                }
                                wybierzObraz=0;
                                System.out.println("Nowy odcinek stworzony: " + se.toString());
                                scanner.nextLine();
                                break;

                            case "3":
                                System.out.println("Wprowadź koordynaty środka nowego koła:");
                                System.out.print("Wprowadź x: ");
                                double xKolo = scanner.nextDouble();
                                System.out.print("Wprowadź y: ");
                                double yKolo = scanner.nextDouble();
                                System.out.print("Wprowadź promień: ");
                                double promien = scanner.nextDouble();
                                System.out.println("Dodaj label (opcjonalne, ENTER by pominąć)");
                                scanner.nextLine();
                                String labelCircle = scanner.nextLine();

                                Point k = new Point(xKolo, yKolo);
                                Circle c = new Circle(k, promien, labelCircle);
                                if(wybierzObraz==1){
                                    picture.addElement(c);
                                }else if(wybierzObraz==2){
                                    StdPicture.addElement(c);
                                }
                                wybierzObraz=0;
                                System.out.println("Nowe koło stworzone: " + c.toString());
                                scanner.nextLine();
                                break;
                        }
                    }
                    else {
                        System.out.println("Błąd. Nie ma takiego obrazu");
                    }
                    break;
                case "2":
                    System.out.println("Który obraz chcesz wyświetlić?\n1 UniquePicture\n2 StandarizedPicture\nWybierz>");
                    wybierzObraz = scanner.nextInt();
                    scanner.nextLine();
                    if(wybierzObraz == 1 || wybierzObraz ==2){
                        if(wybierzObraz==1){
                            System.out.println(picture.toString());
                        }else if(wybierzObraz==2){
                            System.out.println(StdPicture.toString());
                        }
                        wybierzObraz=0;
                    }else{
                        System.out.println("Błąd. Nie ma takiego obrazu");
                    }
                    break;
                case "3":
                    System.out.println("Który obraz chcesz przesunąć?\n1 UniquePicture\n2 StandarizedPicture\nWybierz>");
                    wybierzObraz = scanner.nextInt();
                    scanner.nextLine();
                    if(wybierzObraz == 1 || wybierzObraz == 2){
                        System.out.print("Wprowadź dx: ");
                        double dx = scanner.nextDouble();
                        System.out.print("Wprowadź dy: ");
                        double dy = scanner.nextDouble();
                        if(wybierzObraz==1){
                            picture.move(dx, dy);
                        }else if(wybierzObraz==2){
                            StdPicture.move(dx, dy);
                        }
                        wybierzObraz=0;
                    }else{
                        System.out.println("Błąd. Nie ma takiego obrazu");
                    }
                    break;
                case "4":
                    double area=0;
                    System.out.println("Sumę pól którego obrazu chcesz uzyskać?\n1 UniquePicture\n2 StandarizedPicture\nWybierz>");
                    wybierzObraz = scanner.nextInt();
                    scanner.nextLine();
                    if(wybierzObraz == 1 || wybierzObraz ==2){
                        if(wybierzObraz==1){
                            area = picture.getArea();
                        }else if(wybierzObraz==2){
                            area = StdPicture.getArea();
                        }
                        wybierzObraz=0;
                        System.out.println("Suma pól: " + area);
                    }else{
                        System.out.println("Błąd. Nie ma takiego obrazu");
                    }
                    break;
            }
        } while (!"w".equals(opcja));
        scanner.close();
    }
}

/*
    Zmodyfikuj zadanie z poprzednich zajęć:

DONE>>  Do klasy Picture dodaj 3 metody, zwracające tekstową reprezentację Picture z obiektami posortowanymi według ustalonego porządku (wykorzystaj Arrays.sort i interfejs Comparator):

DONE>>  String toStringSortedByLabel() // posortowane po etykiekiecie, malejąco
DONE>>  String toStringSortedByClassName() // posortowane po nazwie klasy, rosnąco
DONE>>  String toStringSortedByDistanceFromOrigin() // posortowane wg. odległości punktu centroida obiektu od początku układu współrzędnych.

DONE>>  Stwórz 2 intefejsy reprezentujace operacje, jakie można wykonać na danym obiekcie graficznym, dodaj ich implementację do wybranych klas:

DONE>>  Filllable z metodą fill(int color), implementowana przez wszystkie figury z polem (z wyjątkiem Point i Section),
DONE>>  Scalable z metodą scalePerimeter(double k), która liniowo skaluje obwód obiektu, zaimplementowana przez wybrane klasy.

        Dodaj do klasy Picture metody fillObjects i scaleObjects, która wykonuje operacje fill/scalePerimiter na obiektach posiadających odpowiedni interfejs (wykorzystaj operator instanceof).

        Dodaj możliwość zapisu/odczytu obrazu z pliku za pomocą mechanizmu serializacji.
*/