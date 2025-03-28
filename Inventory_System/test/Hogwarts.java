package Inventory_System.test;

    public class Hogwarts{
        private String house;
        private String name;
        private String pet;
        private int age;
    
        //default constructor
        public Hogwarts() {
            // Default values
            this.house = "Unknown";
            this.name = "Unknown";
            this.pet = "Unknown";
            this.age = 0;
        }
    
        //Constructor
        public Hogwarts(String house, String name, String pet, int age){
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
    
        public static void main(String args[]){
    
            Hogwarts school = new Hogwarts();
            school.setHouse("Gryffindor");
            school.setName("Harry");
            school.setPet("Owl");
            school.setAge(11);
    
            System.out.println();
            System.out.println(school.getName() + " is a Hogwarts Students.");
            System.out.println("He is in " + school.getHouse()+ " house.");
            System.out.println("He is " + school.getAge() + " years old");
            System.out.println("He has pet "+ school.getPet() + " named Hedwig.");
            System.out.println();
        }
    }

