package sieteymedio;

import java.awt.Color;
import java.awt.Font;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import BO.PlayerBO;

public class Points {
	
	JTextArea textArea;
	
	public void print(String msg) {
		textArea.append(msg + "\n");
	}
	
	public List<PlayerBO> distributePoints(List<PlayerBO> playerList, JTextArea textArea) {
		
		print("");
		print("Distribuyendo puntos");
		print("");
		
		PlayerBO bank = playerList.get(playerList.size() - 1);
		PlayerBO winner = null;
		
		// 1.La banca juega contra todos y cada uno de los jugadores, y, por lo tanto, si ella se ha pasado, deberá pagar su apuesta a todos aquellos jugadores que se hubieran plantado sin pasarse.
		if(bank.getTotalRound() > 0) {
			bankPass(playerList, textArea);
			return playerList;
		} 
		
		// 2.Si la banca se ha plantado, comprueba con cada jugador su jugada para ver a quién vence y con quién pierde. En cada apuesta vence quien más se acerque a siete y medio.
		for(int i=0; i<playerList.size()-1; i++) {
			PlayerBO player = playerList.get(i);
			
			if(bank.getTotalRound()==player.getTotalRound()) {
				// 3.En caso de empate gana la banca; por lo tanto, si la banca tiene siete y medio, gana automáticamente a todos los jugadores.
				double points = player.getPoints() - player.getBet();
				player.setPoints(points);
				winner = bank;
			} else if(player.getPoints()==7.5 && bank.getPoints()<7.5) {
				// 5.Si un jugador tiene siete y medio (y la banca no) cobra el doble de lo apostado y además toma pasa a ser candidato a banca la ronda siguiente.
				double points = player.getPoints() + (player.getBet() * 2);
				player.setPoints(points);
			} else if (bank.getTotalRound() > player.getTotalRound()){
				// 4.cada jugador que pierda con la banca debe pagarle a esta lo apostado.
				double points = player.getPoints() - player.getBet();
				player.setPoints(points);
			} else {
				// 4.La banca debe pagar la cantidad apostada, a cada jugador con el que pierda,
				double points = player.getPoints() + player.getBet();
				player.setPoints(points);
			}
		}
		
		// El jugador que pierde sus puntos queda eliminado de la partida.
		for(int i=0; i<playerList.size(); i++) {
			PlayerBO player = playerList.get(i);
			if(player.getPoints()<=0) {
				playerList.remove(i);
			}
		}
		
		if(winner==null) {
			winner = playerList.stream()
					.sorted(Comparator.comparingDouble(PlayerBO::getPoints)
					.reversed())
					.toList()
					.get(0);
			
		}
		
		print("The winner is: " + winner.getName());
		
		return playerList;
	}
	
	private void bankPass(List<PlayerBO> playerList, JTextArea textArea) {
		
		// pay players
		for(int i=0; i<playerList.size()-1; i++) {
			// check if player <= 7.5
			double totalRound = playerList.get(i).getTotalRound();
			if(totalRound <= 7.5) {
				PlayerBO player = playerList.get(i);
				double points = player.getPoints() + player.getBet();
				player.setPoints(points);
				print("Player: " + player.getName() + " received: " + player.getBet() + " points, Total Player("+player.getPoints()+")");
			}
			
		}
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	

}
