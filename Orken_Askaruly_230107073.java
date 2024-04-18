
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

interface PizzaInterface {
    Pizza getPizzaBySize(String size) throws PizzaNotFoundException;
    Pizza getPizzaById(int pizzaId) throws PizzaNotFoundException;
    Pizza getPizzaByName(String name) throws PizzaNotFoundException;
    Pizza orderNewPizza(Pizza pizza, Customer customer);
    List<Pizza> getAllPizzas();
    Pizza updatePrice(Pizza pizza, double newPrice);
    void deletePizza(Pizza pizza);
    Pizza addNewPizza(Pizza pizza);
}

class PizzaNotFoundException extends Exception {
    public PizzaNotFoundException(String pizzaNotFoundMessage) {
        super(pizzaNotFoundMessage);
    }
}

public class Orken_Askaruly_230107073 {
    static int pizzaid = 1;
    static int customerid = 1;
    static int orderid = 1;
    static PizzaStore p1 = new PizzaStore();
    static PizzaStore pizzaStore = new PizzaStore(1, "Chef Dynamike's", "Rudny");
    static PizzaService pizzaServ = new PizzaService(p1);

    public static void main(String[] args) throws PizzaNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        System.out.println("Hey Buddy, Welcome to Dynamike's Pizza Spot!!!");
            while (true) {
                try {
                System.out.println("Choose role to Login: ");
                System.out.println("1) ADMIN");
                System.out.println("2) CUSTOMER");
                System.out.println("3) EXIT SYSTEM");
                System.out.print("Choose an Option: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 3) {
                    while (choice < 1 || choice > 3) {
                        System.out.print("Invalid option, choose 1-3:");
                        choice = scanner.nextInt();
                    }
                }
                switch (choice) {
                    case 1:
                        openAdminRole(scanner, pizzaServ);
                        break;
                    case 2:
                        openCustomerRole(scanner, pizzaServ);
                        break;
                    case 3:
                        return;
                }
            } catch (InputMismatchException e) {
                    System.out.println("invalid input!");
                    scanner.nextLine();
            }catch (NumberFormatException e){
                    System.out.println("invalid input!");
                    scanner.nextLine();
            }catch (IndexOutOfBoundsException e){
                    System.out.println("Enter as we suggested,don't enter more.Try again!");
                    scanner.nextLine();
                }
        }
    }

    public static void openAdminRole(Scanner scanner, PizzaInterface pizzaService) {
        int adminchoice = 0;
        while (adminchoice != 6) {
            System.out.println("Welcome to Admin console !!!");
            System.out.println("Choose an option: ");
            System.out.println("1) Add Pizza");
            System.out.println("2) Update Price");
            System.out.println("3) Delete Pizza");
            System.out.println("4) View all Pizza");
            System.out.println("5) Search Pizza");
            System.out.println("6) Exit Admin console");
            System.out.print("Choose an Option: ");
            adminchoice = scanner.nextInt();
            if (adminchoice < 1 || adminchoice > 6) {
                while (adminchoice < 1 || adminchoice > 6) {
                    System.out.print("Invalid option, choose 1-6:");
                    adminchoice = scanner.nextInt();
                }
            }
            scanner.nextLine();
            switch (adminchoice) {
                case 1:
                    System.out.println("<=>Add New Pizza Menu<=>");
                    System.out.println();

                    System.out.println("Enter Details as name of Topping, spice level(basic/mediate/full), description to add a new Topping...");
                    String details = scanner.nextLine();
                    String[] splitted = details.split(",");
                    Topping topping = new Topping(splitted[0], splitted[1], splitted[2]);

                    System.out.println("Enter Details as name of Base, type(thin/thick), decription to add a new PizzaBase...");
                    String details2 = scanner.nextLine();
                    String[] splitted2 = details2.split(",");
                    PizzaBase pizzabase = new PizzaBase(splitted2[0], splitted2[1], splitted2[2]);

                    System.out.println("Enter Details as name of Pizza, price, size(small/Medium/Large) to add a new Pizza...");
                    String details3 = scanner.nextLine();
                    String[] splitted3 = details3.split(",");
                    Pizza pizza = new Pizza(pizzaid, splitted3[0], Double.parseDouble(splitted3[1]), splitted3[2], topping, pizzabase);
                    System.out.println(pizzaServ.addNewPizza(pizza));
                    pizzaid++;

                    try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Desktop/pizzas.txt", true)) {
                        writer.write(pizza.toString() + System.lineSeparator());
                        System.out.println("Pizza information saved to file successfully.");
                    } catch (IOException e) {
                        System.out.println("Error writing pizza information to file: " + e.getMessage());
                    }

                    System.out.println("New Pizza Added Successfully !!!");
                    System.out.println();
                    break;

                case 2:
                    System.out.println("<=> Update Pizza Menu <=>");
                    System.out.println("Enter Pizza Name: ");
                    String name = scanner.next();
                    try {
                        System.out.println("Enter new price: ");
                        double price = scanner.nextDouble();
                        System.out.println(pizzaServ.updatePrice(pizzaServ.getPizzaByName(name), price));
                    } catch (PizzaNotFoundException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                    break;

                case 3:
                    System.out.println("<=> Delete Pizza Menu <=>");
                    System.out.println("Enter Pizza Name: ");
                    String pizzaNameToDelete = scanner.next();
                    try {
                        pizzaServ.deletePizza(pizzaServ.getPizzaByName(pizzaNameToDelete));
                        System.out.println("Pizza Deleted Successfully");
                    } catch (PizzaNotFoundException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                    break;

                case 4:
                    System.out.println();
                    System.out.println("<=> All Pizzas <=>");
                    for (int i = 0; i < pizzaServ.getAllPizzas().size(); i++) {
                        System.out.println("Pizza number: " + (i + 1));
                        System.out.println(pizzaServ.getAllPizzas().get(i));
                        System.out.println();
                    }
                    System.out.println();
                    break;

                case 5:
                    searchPizza(scanner, pizzaService);
                    break;

                case 6:
                    System.out.println("Thank You Admin !!!");
                    System.out.println();
                    break;
            }
        }
    }

    public static void openCustomerRole(Scanner scanner, PizzaInterface pizzaService) {
        int customerChoice = 0;

        System.out.println("Enter your doorNumber, street, city, district, state...");
        String details1 = scanner.nextLine();
        String[] splitted1 = details1.split(",");
        Address address = new Address(Integer.parseInt(splitted1[0]), splitted1[1], splitted1[2], splitted1[3], splitted1[4]);

        System.out.println("Enter your Details as name, email, mobile...");
        String details2 = scanner.nextLine();
        String[] splitted2 = details2.split(",");

        Customer customer = new Customer(customerid, splitted2[0], splitted2[1], Long.parseLong(splitted2[2]), address);
        pizzaStore.addCustomer(customer);
        customerid++;

        while (customerChoice != 6) {
            System.out.println();
            System.out.println("Welcome to customer console !!!");
            System.out.println("1) Order Pizza");
            System.out.println("2) View Your Orders");
            System.out.println("3) Pay Bill");
            System.out.println("4) View All Pizza");
            System.out.println("5) Search Pizza");
            System.out.println("6) Exit Customer Console");
            System.out.print("Choose an Option: ");
            customerChoice = scanner.nextInt();
            if (customerChoice < 1 || customerChoice > 6) {
                while (customerChoice < 1 || customerChoice > 6) {
                    System.out.print("Invalid option, choose 1-6:");
                    customerChoice = scanner.nextInt();
                }
            }
            scanner.nextLine();

            switch (customerChoice) {
                case 1:
                    System.out.println("<=>Order New Pizza Menu<=>");
                    System.out.println();
                    System.out.println("Available Pizza: ");
                    for (int i = 0; i < pizzaService.getAllPizzas().size(); i++) {
                        System.out.println("Pizza number: " + (i + 1));
                        System.out.println(pizzaService.getAllPizzas().get(i));
                    }
                    System.out.print("Enter Pizza Name To Place Your Order: ");
                    String pizzaName = scanner.next();
                    System.out.println("Enter your Descriptions: ");
                    String description = scanner.next();

                    try {
                        pizzaService.getPizzaByName(pizzaName);
                        pizzaService.orderNewPizza(pizzaService.getPizzaByName(pizzaName), customer);
                        Order order = new Order(orderid, String.valueOf(new Date()), pizzaService.getPizzaByName(pizzaName).getPrice(), description);
                        customer.addOrder(order);

                        try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Desktop/customers.txt", true)) {
                            writer.write(customer.toStringWithOrders() + System.lineSeparator());
                        } catch (IOException e) {
                            System.out.println("Error writing customer information with orders to file: " + e.getMessage());
                        }

                        try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Desktop/orders.txt", true)) {
                            writer.write("Customer ID: " + customer.getCustomerId() + ", Order: " + order + System.lineSeparator());
                        } catch (IOException e) {
                            System.out.println("Error writing order information to file: " + e.getMessage());
                        }
                        orderid++;
                        System.out.println("Your order has been successfully placed !!!");
                    } catch (PizzaNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println();
                    System.out.println("Your Orders: ");
                    for (int i = 0; i < customer.getOrders().size(); i++) {
                        Order order = customer.getOrders().get(i);
                        System.out.println(order);
                        try (FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Desktop/orders.txt", true)) {
                            writer.write("Customer ID: " + customer.getCustomerId() + ", Order: " + order + System.lineSeparator());
                        } catch (IOException e) {
                            System.out.println("Error writing order information to file: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    System.out.println();
                    System.out.print("Your payable bill amount for all orders is: " + customer.getPayableAmount());
                    System.out.println();
                    break;

                case 4:
                    System.out.println();
                    System.out.println("<=> All Pizzas <=>");
                    for (int i = 0; i < pizzaService.getAllPizzas().size(); i++) {
                        System.out.println("Pizza number: " + (i + 1));
                        System.out.println(pizzaService.getAllPizzas().get(i));
                        System.out.println();
                    }
                    break;

                case 5:
                    searchPizza(scanner, pizzaService);
                    break;

                case 6:
                    System.out.println("Thank You customer");
                    break;
            }
        }
    }

    public static void searchPizza(Scanner scanner, PizzaInterface pizzaService){
            System.out.println("<=> Search Pizza Menu<=>");
            System.out.println();
            System.out.println("Choose your Search Type: ");
            System.out.println("1) Search by Name");
            System.out.println("2) Search by ID");
            System.out.println("3) Search by Size");
            System.out.println("4) Get back");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            if (choice < 1 || choice > 4) {
                while (choice < 1 || choice > 4) {
                    System.out.print("Invalid option, choose 1-4:");
                    choice = scanner.nextInt();
                }
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter Pizza name to search: ");
                    String name = scanner.next();
                    try {
                        System.out.println(pizzaService.getPizzaByName(name));
                    } catch (PizzaNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter Pizza id to search: ");
                    int id = scanner.nextInt();
                    try {
                        System.out.println(pizzaService.getPizzaById(id));
                    } catch (PizzaNotFoundException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                    break;
                case 3:
                    System.out.print("Enter Pizza size to search: ");
                    String size = scanner.next();
                    try {
                        System.out.println(pizzaService.getPizzaBySize(size));
                    } catch (PizzaNotFoundException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                    break;
                case 4:
                    break;
            }
    }
}


class PizzaService implements PizzaInterface{
    private String pizzaNotFoundMessage;
    private PizzaStore pizzastore;
    public PizzaService(PizzaStore pizzaStore){
        this.pizzastore=pizzaStore;
    }
    public List<Pizza> getAllPizzas(){
        return pizzastore.getPizzas();
    }
    public Pizza addNewPizza(Pizza pizza){
        pizzastore.addPizza(pizza);
        return pizza;
    }
    public void deletePizza(Pizza pizza){
        boolean found = false;
        for (int i = 0; i < getAllPizzas().size();i++) {
            if (getAllPizzas().get(i).equals(pizza)) {
                getAllPizzas().remove(i);
                found=true;
                break;
            }
            if(found)System.out.println("Pizza Deleted Successfully");
            else System.out.println("Pizza not found");
        }
    }
    public Pizza updatePrice(Pizza pizza,double newPrice){
        for (int i = 0; i < getAllPizzas().size(); i++) {
            if(getAllPizzas().get(i).equals(pizza)){
                getAllPizzas().get(i).setPrice(newPrice);
                return  getAllPizzas().get(i);
            }
        }
        return  null;
    }
    public Pizza orderNewPizza(Pizza pizza, Customer customer){
        return null;
    }

    public Pizza getPizzaByName(String pizzaName) throws PizzaNotFoundException {
        for (int i = 0; i < getAllPizzas().size(); i++) {
            if(getAllPizzas().get(i).getPizzaName().equals(pizzaName)){
                return  getAllPizzas().get(i);
            }
        }
        throw new PizzaNotFoundException("Pizza Not Found");
    }
    public Pizza getPizzaById(int pizzaId) throws PizzaNotFoundException{
        for (int i = 0; i < getAllPizzas().size(); i++) {
            if(getAllPizzas().get(i).getPizzaId()==pizzaId){
                return  getAllPizzas().get(i);
            }
        }
        throw new PizzaNotFoundException("Pizza Not Found");
    }
    public Pizza getPizzaBySize(String size) throws PizzaNotFoundException{
        for (int i = 0; i < getAllPizzas().size(); i++) {
            if (getAllPizzas().get(i).getSize().equals(size)) {
                return getAllPizzas().get(i);
            }
        }
        throw new PizzaNotFoundException("Pizza not Found");
    }
}


class PizzaStore {
    private List<Customer> customers;
    private List<Pizza> pizzas;
    private String storeLocation;
    private String storeName;
    private int storeId;
    public PizzaStore(){
        pizzas = new ArrayList<>();
        customers = new ArrayList<>();
    }
    public PizzaStore(int storeId,String storeName,String storeLocation){
        this.storeId=storeId;
        this.storeName=storeName;
        this.storeLocation=storeLocation;
        this.customers=new ArrayList<>();
        this.pizzas=new ArrayList<>();
    }
    public void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public void deletePizza(Pizza pizza){
        for(int i=0;i<pizzas.size();i++){
            if(pizzas.get(i).equals(pizza)){
                pizzas.remove(pizzas.get(i));
            }
        }
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    public String toString(){
        return "storeName: "+getStoreName()+" ,storeId: "+getStoreId()+" ,storeLocation: "+getStoreLocation();
    }
}

class Pizza {
    private PizzaBase pizzaBase;
    private Topping topping;
    private String size;
    private double price;
    private String pizzaName;
    private int pizzaId;
    public Pizza(int pizzaId,String pizzaName,double price,String size,Topping topping,PizzaBase pizzaBase){
        this.pizzaId=pizzaId;
        this.pizzaName=pizzaName;
        this.price=price;
        this.size=size;
        this.topping=topping;
        this.pizzaBase=pizzaBase;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public PizzaBase getPizzaBase() {
        return pizzaBase;
    }

    public void setPizzaBase(PizzaBase pizzaBase) {
        this.pizzaBase = pizzaBase;
    }
    public String toString(){
        return "Pizza Details => ID : "+getPizzaId()+", Name: "+getPizzaName()+", Price: "+getPrice()+", Size: "+getSize()+" \nTopping Details => "+getTopping()+" \nPizza Base => "+getPizzaBase();
    }
}


class Customer {
    private List<Order>orders;
    private Address address;
    private long moblie;
    private String email;
    private String customerName;
    private int customerId;

    public Customer(int customerId,String customerName,String email,long moblie,Address address){
        this.customerId=customerId;
        this.customerName=customerName;
        this.email=email;
        this.moblie=moblie;
        this.address=address;
        this.orders = new ArrayList<>();
    }
    public void addOrder(Order order){
        orders.add(order);
    }
    public double getPayableAmount(){
        double sum = 0;
        for (int i = 0; i < orders.size(); i++) {
            sum += orders.get(i).getPayableBillAmount();
        }
        return sum;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMoblie() {
        return moblie;
    }

    public void setMoblie(long moblie) {
        this.moblie = moblie;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public String toStringWithOrders() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(toString()).append(System.lineSeparator());

        for (Order order : orders) {
            stringBuilder.append("Order: ").append(order).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public String toString(){
        return "Customer Details =>  ID: "+getCustomerId()+", Name: "+getCustomerName()+", Email: "+getEmail()+", Mobile: "+getMoblie()+"\nAddress => "+getAddress();
    }
}

class Topping {
    private String description;
    private String spiceLevel;
    private String toppingName;
    Topping(){}
    public Topping(String toppingName,String spiceLevel,String description){
        this.toppingName=toppingName;
        this.spiceLevel=spiceLevel;
        this.description=description;
    }

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String toString(){
        return "Topping name: "+this.toppingName+", spiceLevel: "+this.spiceLevel+", Description: "+this.description;
    }
}



class Order {
    private List<Pizza> pizzas;
    private String orderDescription;
    private double payableBillAmount;
    private String orderDate;
    private int orderId;
    public Order(){
    }
    public Order(int orderId,String orderDate,double payableBillAmount,String orderDescription){
        this.orderId=orderId;
        this.orderDate=orderDate;
        this.orderDescription=orderDescription;
        this.payableBillAmount=payableBillAmount;
    }
    public void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getPayableBillAmount() {
        return payableBillAmount;
    }

    public void setPayableBillAmount(double payableBillAmount) {
        this.payableBillAmount = payableBillAmount;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
    public String toString(){
        return "Order Id: "+getOrderId()+" ,Order Date: "+getOrderDate()+" ,PayableBillAmount: "+getPayableBillAmount()+" ,Order Description: "+getOrderDescription();
    }
}

class Address {
    private String state;
    private String district;
    private String city;
    private String street;
    private int doornumber;
    public Address(int doornumber,String street,String city,String district,String state){
        this.state=state;
        this.city=city;
        this.district=district;
        this.street=street;
        this.doornumber=doornumber;
    }

    public int getDoornumber() {
        return doornumber;
    }

    public void setDoornumber(int doornumber) {
        this.doornumber = doornumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String toString(){
        return "Door Number: "+getDoornumber()+", Street: "+getStreet()+", City: "+getCity()+", District: "+getDistrict()+", State: "+getState();
    }
}

class PizzaBase {
    private String description;
    private String baseType;
    private String baseName;
    public PizzaBase(String baseName,String baseType,String description){
        this.baseName=baseName;
        this.baseType=baseType;
        this.description=description;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String toString(){
        return "BaseName: "+getBaseName()+", Type: "+getBaseType()+", Description: "+getDescription();
    }
}

