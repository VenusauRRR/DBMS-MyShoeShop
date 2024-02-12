package Part2JDBCLambda;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReportMain {
    static DAO dao = DAO.getInstance();
    public static void main(String[] args) throws IOException {
        Repository repo = new Repository();
        repo.loadData();
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("Enter which report to be written:" +
                    "\n1. Report om alla kunder och hur många ordrar varje kund har lagt" +
                    "\n2. Report om alla kunder och hur mycket pengar varje kund, sammanlagt, har beställt för" +
                    "\n3. Report som listar beställningsvärde per ort");
            int input = sc.nextInt();
            if (input==1){
                reportCustomerOrder();
            } else if (input==2){
                reportCustomerOrderMoneySum();
            } else if (input==3){
                reportRegionOrderMoneySum();
            } else {
                System.out.println("not an option");
            }
            System.out.println();
        }
    }


//        1. En rapport som listar alla kunder, med namn och adress, som har handlat varor i en viss
//        storlek, av en viss färg eller av ett visst märke. Storlek, färg och märke är inparametrar och
//        ska kunna skrivas in från consolen och varieras från ditt Java-program.
    //storlek, färg, märke


//        2. En rapport som listar alla kunder och hur många ordrar varje kund har lagt. Skriv ut namn
//        och sammanlagda antalet ordrar för varje kund.

    public static void reportCustomerOrder(){
        Map<String, List<String>> m = dao.getOrderList().stream()
                .map(l -> l.getReceipt().getCustomer().getFirstname()
                        .concat(" ")
                        .concat(l.getReceipt().getCustomer().getLastname())
                )
                .collect(Collectors.groupingBy(l -> l));
        m.forEach((k,v) -> System.out.println(k + ": " + v.size() + " order(s)"));
    }//reportCustomerOrder

//        3. En rapport som listar alla kunder och hur mycket pengar varje kund, sammanlagt, har
//        beställt för. Skriv ut varje kunds namn och summa.

    public static void reportCustomerOrderMoneySum(){
        dao.getOrderList().stream()
                .collect(Collectors.groupingBy(l -> l.getReceipt().getCustomer().getFirstname()
                        .concat(" ")
                        .concat(l.getReceipt().getCustomer().getLastname())))
                //k is String, e.g.Peter Peterson
                //v is List<Order> -> stream, e.g. all orders made by Peter
                .forEach((k,v) -> System.out.println(k + " " +
                        v.stream().mapToInt(l -> l.getAmount()*l.getProduct().getModel().getPrice()).sum()
                        + " kr"));
    }

//        4. En rapport som listar beställningsvärde per ort. Skriv ut orternas namn och summa.
    public static void reportRegionOrderMoneySum(){
        dao.getOrderList().stream()
                .collect(Collectors.groupingBy(l -> l.getReceipt().getCustomer().getAddress()))
                .forEach((k,v) -> System.out.println(k + " " + v.stream().mapToInt(l -> l.getAmount()*l.getProduct().getModel().getPrice()).sum()+ " kr"));
    }

//        5. En topplista över de mest sålda produkterna, som listar varje modell och hur många ex som
//        har sålts av den modellen. Skriv ut namn på modellen och hur många ex som sålts.


}
