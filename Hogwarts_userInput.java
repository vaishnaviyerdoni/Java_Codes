import java.util.Scanner;

public class Hogwarts_userInput{
    private String house;
    private String name;
    private String pet;
    private int age;

    //Constructor
    public Hogwarts_userInput(String house, String name, String pet, int age){
        this.house = house;
        this.name = name;
        this.pet = pet;
        this.age = age;
    }

    //Getters
    public String getHouse(){
        return house;
    }

    public String getName(){
        return name;
    }

    public String getPet(){
        return pet;
    }

    public int getAge(){
        return age;
    }

    //Setters

    public void setHouse(String house){
        this.house = house;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPet(String pet){
        this.pet = pet;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString(){
        return name + " is a Hogwarts Student, studying in " + house + " house. " + 
            name + " is " + age + " years old and has a pet " + pet + ".";
    }
    public static void main(String args[]){

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter  your hogwarts house: ");
        String house = scan.nextLine();
        System.out.print("Enter you name: ");
        String name = scan.nextLine();
        System.out.print("What animal have you brought as pet? ");
        String pet = scan .nextLine();
        System.out.print("How old are you? ");
        int age = scan.nextInt();

        Hogwarts_userInput student = new Hogwarts_userInput(house, name, pet, age);
        System.out.println("\n" + student);

        scan.close();
    }
}