
public class Connection implements AutoCloseable{
	
	//we defined the interface autocloseable
	//so we can simplify our code
	public Connection() {
		System.out.println("Opening connection");
		//throw new IllegalStateException();
	}
	
	public void readData() {
		System.out.println("Receiving data");
		throw new IllegalStateException();
	}
	
	@Override
	public void close() {
		System.out.println("Closing connection");
	}
	// I fused my previous close method with the default that is presented
	// by the class itself, although I removed the "extends Exception"
	// which is safer in this case

}
