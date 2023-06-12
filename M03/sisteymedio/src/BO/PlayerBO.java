package BO;

public class PlayerBO {

	private int id;
    private String name;
    private int playerRisk;
    private int human;
    private int priority;
    private double points = 20;
    private int isBank;
    private int bet;
    private double totalRound=0;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlayerRisk() {
		return playerRisk;
	}
	public void setPlayerRisk(int playerRisk) {
		this.playerRisk = playerRisk;
	}
	public int getHuman() {
		return human;
	}
	public void setHuman(int human) {
		this.human = human;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public int isBank() {
		return isBank;
	}
	public void setIsBank(int isBank) {
		this.isBank = isBank;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public double getTotalRound() {
		return totalRound;
	}
	public void setTotalRound(double totalRound) {
		this.totalRound = totalRound;
	}
    
}
