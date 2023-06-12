package BO;

public class DeckBO {
	
	int id;
	String deck_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeck_name() {
		return deck_name;
	}
	public void setDeck_name(String deck_name) {
		this.deck_name = deck_name;
	}

	@Override
	public String toString() {
	    return this.getDeck_name();
	}
}
