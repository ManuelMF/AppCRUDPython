package a07ErroresDeLaImpresora;

import java.util.ArrayList;

public class printer {
	 public static String printerError(String s) {
		
		 ArrayList<String> listacaract = new ArrayList<>();
		 for (int x=0 ; x<s.length() ; x++) {
			 listacaract.add(s.charAt(x)+"");
		 }
		 
		int numError=0;
		
		for(String l : listacaract) {
			if (l.equals("n") ||
				l.equals("o") ||
				l.equals("p") ||
				l.equals("q") ||
				l.equals("r") ||
				l.equals("s") ||
				l.equals("t") ||
				l.equals("u") ||
				l.equals("v") ||
				l.equals("w") ||
				l.equals("x") ||
				l.equals("y") ||
				l.equals("z") 
				) numError++;
		}
		 return s.length()+"/"+numError;
	    }
}
