import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    private static SimpleDateFormat sdf;

    public static void main(String[] args) {
        String dataPath = "";
        String schemaPath = "";
            try {
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                boolean flag = true;
                while (flag) {
                    System.out.print("Enter path to xml with data: ");
                    dataPath = stdin.readLine();
                    System.out.print("Enter path to xml with schema (XSD): ");
                    schemaPath = stdin.readLine();
                    String result = XmlUtils.validate(dataPath, schemaPath);
                    if (result == null) {
                        flag = false;
                        System.out.println("Valid files");
                    } else {
                        System.out.println("Error occured: " + result);
                        System.out.println("Try again!");
                    }
                }

                String choice = "";
                while (!choice.equals("q")) {
                    System.out.println("\nEnter action: ");
                    System.out.println("1 View");
                    System.out.println("2 Add");
                    System.out.println("q Exit");
                    choice = stdin.readLine();

                    switch (choice) {
                        case "1":
                            System.out.println("TICKETS LIST");
                            String data = FileUtils.getFileData(dataPath);
                            List<Ticket> tickets = XmlUtils.parseTicketsFromXml(data);
                            for (Ticket ticket: tickets) {
                                System.out.println(ticket);
                            }
                            break;
                        case "2":
                            System.out.println("ADD TICKET");
                            Ticket.Builder b = new Ticket.Builder();
                            System.out.print("Enter arrival time (" + Ticket.DATE_FORMAT + "): ");
                            b.setArrivalTime(stringToLong(stdin.readLine()));
                            System.out.print("Enter departure time (" + Ticket.DATE_FORMAT + "): ");
                            b.setDepartureTime(stringToLong(stdin.readLine()));
                            System.out.print("Enter from: ");
                            b.setFrom(stdin.readLine());
                            System.out.print("Enter to: ");
                            b.setTo(stdin.readLine());
                            System.out.print("Enter route num: ");
                            b.setRouteNum(Integer.valueOf(stdin.readLine()));
                            System.out.print("Enter sell time (" + Ticket.DATE_FORMAT + "): ");
                            b.setSellDate(stringToLong(stdin.readLine()));
                            System.out.print("Enter passenger name: ");
                            b.setPassengerName(stdin.readLine());

                            XmlUtils.addToXml(dataPath, b.ticket());
                            break;
                        case "q":
                            System.out.println("EXIT");
                            break;
                        default:
                            System.out.println("Unknown command");
                            break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static long stringToLong(String read) {
        if (sdf == null) {
            sdf = new SimpleDateFormat(Ticket.DATE_FORMAT);
        }
        long time;
        try {
            time = sdf.parse(read).getTime();
        } catch (ParseException e) {
            time = new Date().getTime();
        }
        return time;
    }
}
