
public class Farey {
	private int nume, denom;
	public Farey(int nume, int denom){
		this.nume = nume;
		this.denom = denom;
	}
	
	public boolean isSame(Farey f){
		if(nume == f.nume() && denom == f.denom()){
			return true;
		}
		return false;
	}
	public Farey add(Farey f){
		return new Farey(nume + f.nume(), denom + f.denom());
	}
	public int nume(){
		return nume;
	}
	public int denom(){
		return denom;
	}
	public double toDouble(){
		if(denom == 0){
			if(nume >= 0){
				return Double.POSITIVE_INFINITY;
			}else{
				return Double.NEGATIVE_INFINITY;
			}
		}
		return (double) nume / (double) denom;
	}
	public String toString(){
		return nume +"/"+ denom;
	}
}
