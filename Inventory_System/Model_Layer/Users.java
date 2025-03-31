package Inventory_System.Model_Layer;

public class Users {

    // variables
    private int user_id;
    private String user_name;
    private String email;
    private String password;
    private String role;

    //constructor
    public Users(){}

    public Users(int user_id, String user_name, String email, String password, String role){
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //getters
    public int get_userId(){
        return user_id;
    }

    public String get_userName(){
        return user_name;
    }

    public String get_email(){
        return email;
    }

    public String get_password(){
        return password;
    }

    public String role(){
        return role;
    }

    //setters
    public void set_userId(int user_id){
        this.user_id = user_id;
    }

    public void set_userName(String user_name){
        this.user_name = user_name;
    }

    public void set_email(String email){
        this.email = email;
    }

    public void set_password(String password){
        this.password = password;
    }
    public void set_role(String role){
        this.role = role;
    }    

    public String toString(){
        return "Users{" +
                "UserId=" + user_id +
                ", userName='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", role=" + role +
                '}';
    }
    public static void main(String[] args) {
        // Create an inventory item
        Users user = new Users(1, "vaishnavi00", "vaishnavi@gmail.com", "ABC", "admin");

        // Print item details
        System.out.println("\n" + user);

    }
}
