import java.util.ArrayList;
import java.util.List;

public class Election {
    private List<Candidate> candidates;
    private List<Ballot> ballots;

    public Election() {
        this.candidates = new ArrayList<>();
        this.ballots = new ArrayList<>();
    }

    public void processElection(Ballot[] inputBallots) {
        // Convert array to a List
        for (Ballot ballot : inputBallots) {
            this.ballots.add(ballot);
        }

        // 1) Extract all unique candidates from the ballots
        extractUniqueCandidates();

        // 2) Mark all candidates as not eliminated
        for (Candidate c : candidates) {
            c.setEliminated(false);
        }

        // 3) Main RCV loop
        while (true) {
            // a) Reset vote counts
            resetVoteCounts();

            // b) Tally votes based on top valid choice
            for (Ballot ballot : ballots) {
                Candidate top = getTopChoice(ballot);
                if (top != null) {
                    top.incrementVoteCount();
                }
            }

            // c) Check for majority
            int totalActiveVotes = sumOfNonEliminatedVotes();
            double majority = totalActiveVotes / 2.0;
            Candidate winner = findMajorityCandidate(majority);
            if (winner != null) {
                System.out.println("Winner: " + winner.getName());
                break;
            }

            // d) Eliminate candidate with the fewest votes
            Candidate fewest = findFewestVotesCandidate();
            eliminateCandidate(fewest);

            // e) If only one candidate remains, they are winner by default
            if (countNonEliminated() == 1) {
                Candidate last = getLastCandidate();
                System.out.println("Winner (by default): " + last.getName());
                break;
            }
        }

        // Optional: generate a detailed report of the final state
        generateElectionReport();
    }

    private void extractUniqueCandidates() {
        for (Ballot ballot : this.ballots) {
            for (String name : ballot.getPreferences()) {
                if (!candidateExists(name)) {
                    candidates.add(new Candidate(name));
                }
            }
        }
    }

    private boolean candidateExists(String name) {
        for (Candidate c : candidates) {
            if (c.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private void resetVoteCounts() {
        for (Candidate c : candidates) {
            c.resetVoteCount();
        }
    }

    private Candidate getTopChoice(Ballot ballot) {
        for (String name : ballot.getPreferences()) {
            Candidate c = findCandidateByName(name);
            if (c != null && !c.isEliminated()) {
                return c;
            }
        }
        return null; // no valid choice found
    }

    private Candidate findCandidateByName(String name) {
        for (Candidate c : candidates) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    private int sumOfNonEliminatedVotes() {
        int total = 0;
        for (Candidate c : candidates) {
            if (!c.isEliminated()) {
                total += c.getVoteCount();
            }
        }
        return total;
    }

    private Candidate findMajorityCandidate(double majority) {
        for (Candidate c : candidates) {
            if (!c.isEliminated() && c.getVoteCount() > majority) {
                return c;
            }
        }
        return null;
    }

    private Candidate findFewestVotesCandidate() {
        Candidate fewest = null;
        int minVotes = Integer.MAX_VALUE;
        for (Candidate c : candidates) {
            if (!c.isEliminated() && c.getVoteCount() < minVotes) {
                minVotes = c.getVoteCount();
                fewest = c;
            }
        }
        return fewest;
    }

    private void eliminateCandidate(Candidate candidate) {
        if (candidate != null) {
            candidate.setEliminated(true);
            System.out.println("Eliminated: " + candidate.getName());
        }
    }

    private int countNonEliminated() {
        int count = 0;
        for (Candidate c : candidates) {
            if (!c.isEliminated()) {
                count++;
            }
        }
        return count;
    }

    private Candidate getLastCandidate() {
        for (Candidate c : candidates) {
            if (!c.isEliminated()) {
                return c;
            }
        }
        return null;
    }

    private void generateElectionReport() {
        System.out.println("---- Final Tally Report ----");
        for (Candidate c : candidates) {
            System.out.println(c.getName() + ": "
                    + c.getVoteCount()
                    + (c.isEliminated() ? " (Eliminated)" : " (Active)"));
        }
    }
}
