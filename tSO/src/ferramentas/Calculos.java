package ferramentas;

public class Calculos{
	
	public static float round(float valor, byte precisao){
	    int scale = (int) Math.pow(10, precisao);
	    return (float) Math.round(valor * scale) / scale;
	}

}
