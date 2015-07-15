package ee.inmobile.util.commons;

import java.util.ArrayList;
import java.util.List;

import ee.inmobile.design.model.Menu;
import ee.inmobile.design.slidemenu1.R;


public class MenuConfigAdapter {
	public static List<Menu> getMenuDefault(){
		//Menu menu = new Menu(int id, int drawableIcon, String title, String description)
		Menu menu1 = new Menu(1, R.drawable.news, "News", "Recent news");
		Menu menu2 = new Menu(2, R.drawable.todolist, "Todo", "List of to DOs");
		
		List<Menu> listMenu = new ArrayList<Menu>();  
		listMenu.add(menu1);
		listMenu.add(menu2);
		
		return listMenu;
	}
}
