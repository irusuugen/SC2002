
public class Main {
    public static void main(String[] args) {
        BTOSystem system = new BTOSystem();
        Login login = new Login(system.getUsers());
        login.startLogin();
    }
}