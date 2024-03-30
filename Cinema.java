package cinema;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Scanner for reading input from the console
        Scanner scanner = new Scanner(System.in);

        // Prompting for the number of rows and seats per row
        System.out.println("Enter the number of rows: ");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of seats in each row: ");
        int seats = Integer.parseInt(scanner.nextLine());

        // Creating a 2D array to represent the cinema seats
        char[][] seatsArray = makeCinema(rows, seats);

        // Variables to keep track of user selections and statistics
        int chosenRow = 0;
        int chosenSeats = 0;
        int totalTicket = 0;
        int currentIncome = 0;
        System.out.println();

        // Main loop for the menu
        while (true) {
            // Displaying menu options
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int input = Integer.parseInt(scanner.nextLine());

            switch(input) {
                case 1:
                    // Displaying the cinema seat layout
                    System.out.println();
                    printCinema(seatsArray, seats);
                    System.out.println();
                    break;
                case 2:
                    // Process for buying a ticket
                    while (true) {
                        System.out.println("\nEnter a row number:");
                        chosenRow = Integer.parseInt(scanner.nextLine());
                        System.out.println("Enter a seat number in that row:");
                        chosenSeats = Integer.parseInt(scanner.nextLine());

                        // Validation for seat selection
                        if (chosenRow > rows || chosenSeats > seats) {
                            System.out.println("\nWrong input!");
                        } else if (seatsArray[chosenRow - 1][chosenSeats - 1] == 'B') {
                            System.out.println("\nThat ticket has already been purchased!");
                        } else {
                            // Successful seat selection
                            System.out.println("\nTicket price: $" + calculateTicketPrice(rows, seats, chosenRow));
                            totalTicket++;
                            currentIncome += calculateTicketPrice(rows, seats, chosenRow);
                            System.out.println();
                            pickSeats(seatsArray, chosenRow, chosenSeats);
                            break;
                        }
                    }
                    break;
                case 3:
                    // Displaying statistics
                    System.out.println();
                    printStat(rows, seats, totalTicket, currentIncome);
                    break;
                case 0:
                    // Exiting the application
                    return;
                default:
                    // Handling invalid inputs
                    System.out.println("Invalid input!");
                    break;
            }
        }
    }

    /**
     * Initializes the cinema with all seats set to 'S' (indicating they are free).
     * @param rows Number of rows in the cinema.
     * @param seats Number of seats in each row.
     * @return A 2D array representing the cinema seats.
     */
    public static char[][] makeCinema(int rows, int seats) {
        char freeSeat = 'S';
        char[][] twoDimArr = new char[rows][seats];
        for (char[] chars : twoDimArr) {
            Arrays.fill(chars, freeSeat);
        }
        return twoDimArr;
    }

    /**
     * Marks a selected seat as booked by setting it to 'B'.
     * @param seatsArray The cinema seats array.
     * @param chosenRow The row of the selected seat.
     * @param chosenSeat The seat number in the selected row.
     */
    public static void pickSeats(char[][] seatsArray, int chosenRow, int chosenSeat) {
        seatsArray[chosenRow - 1][chosenSeat - 1] = 'B';
    }

    /**
     * Prints the current state of the cinema seats.
     * @param twoDimArr The cinema seats array.
     * @param seats Number of seats in each row (used for header).
     */
    public static void printCinema(char[][] twoDimArr, int seats) {
        System.out.println("Cinema: ");
        // Printing column numbers
        for(int k = 0; k <= seats; k++) {
            if(k == 0) {
                System.out.print("  ");
            } else {
                System.out.print(k + " ");
            }
        }
        System.out.println();

        // Printing row numbers and seats
        for(int i = 0; i < twoDimArr.length; i++) {
            System.out.print(i + 1 + " ");
            for(char seat : twoDimArr[i]){
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }

    /**
     * Calculates the price of a ticket based on the seat location and total number of seats.
     * @param rows Total number of rows in the cinema.
     * @param seats Number of seats in each row.
     * @param chosenRow The row of the selected seat.
     * @return The price of the ticket.
     */
    public static int calculateTicketPrice(int rows, int seats, int chosenRow) {
        int totalSeats = rows * seats;
        int ticketPrice;
        if(totalSeats <= 60) {
            ticketPrice = 10;
        } else {
            int frontHalfRow = rows / 2;
            ticketPrice = (chosenRow <= frontHalfRow) ? 10 : 8;
        }
        return ticketPrice;
    }

    /**
     * Prints statistics about the tickets sold, current income, and potential total income.
     * @param rows Number of rows in the cinema.
     * @param seats Number of seats in each row.
     * @param totalTicket Total number of tickets sold.
     * @param currentIncome Total income from sold tickets.
     */
    public static void printStat(int rows, int seats, int totalTicket, int currentIncome){
        int totalSeats = rows * seats;
        int totalIncome;
        if (totalSeats <= 60) {
            totalIncome = totalSeats * 10;
        } else {
            int splitRows = rows / 2;
            totalIncome = (splitRows * 10 * seats) + ((rows - splitRows) * 8 * seats);
        }
        double percentage = (100.0 * totalTicket) / totalSeats;

        System.out.println("Number of purchased tickets: " + totalTicket);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

}
