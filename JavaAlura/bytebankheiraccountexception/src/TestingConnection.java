
public class TestingConnection {
	
	public static void main(String[] args) {
		
		try(Connection connection = new Connection() ){
			connection.readData();
		} catch(IllegalStateException ex) {
			System.out.println("Conection Error");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		Connection connection = null;
//		try {
//			connection = new Connection();
//			connection.readData();
//		} catch(IllegalStateException ex) {
//			System.out.println("Conection Error");
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//		}
	
		
	}
}
