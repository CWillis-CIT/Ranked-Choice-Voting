import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 1) Ask for total number of voters
        System.out.print("Enter the total number of voters: ");
        int totalVoters = Integer.parseInt(in.nextLine());

        // 2) Gather all ballots
        List<Ballot> ballotList = new ArrayList<>();

        for (int i = 0; i < totalVoters; i++) {
            System.out.println("\n-- Voter " + (i + 1) + " --");
            // Read comma-separated preferences (e.g., "Jack,Steve,Taylor")
            System.out.print("Enter your ranked preferences (comma-separated): ");
            String inputLine = in.nextLine();
            String[] prefs = inputLine.split(",");

            // Trim whitespace for each candidate name
            for (int j = 0; j < prefs.length; j++) {
                prefs[j] = prefs[j].trim();
            }

            // Create and store the new ballot
            ballotList.add(new Ballot(prefs));
        }

        // 3) Convert the ballot list to an array
        Ballot[] ballotsArray = ballotList.toArray(new Ballot[0]);

        // 4) Run the ranked-choice election
        Election election = new Election();
        election.processElection(ballotsArray);

        in.close();
    }
}
