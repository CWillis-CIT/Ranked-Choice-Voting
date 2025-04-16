public class Candidate {
    private String name;
    private int voteCount;
    private boolean eliminated;

    public Candidate(String name) {
        this.name = name;
        this.voteCount = 0;
        this.eliminated = false;
    }

    public String getName() {
        return name;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public void incrementVoteCount() {
        this.voteCount++;
    }

    public void resetVoteCount() {
        this.voteCount = 0;
    }
}
