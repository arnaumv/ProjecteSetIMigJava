package sieteymedio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import BO.PlayerBO;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<PlayerBO> list = new ArrayList<>();
		
		PlayerBO pl = new PlayerBO();
		pl.setName("P1");
		pl.setPoints(5.0);
		list.add(pl);
		
		pl = new PlayerBO();
		pl.setName("P2");
		pl.setPoints(7.0);
		list.add(pl);
		
		pl = new PlayerBO();
		pl.setName("P3");
		pl.setPoints(2.0);
		list.add(pl);
		
		pl = new PlayerBO();
		pl.setName("P4");
		pl.setPoints(1.0);
		list.add(pl);
		
		pl = new PlayerBO();
		pl.setName("P5");
		pl.setPoints(6.0);
		list.add(pl);
		
		list = list.stream()
		.sorted(Comparator.comparingDouble(PlayerBO::getPoints)
		.reversed())
		.toList();
		
		System.out.println("dd");

	}

}
