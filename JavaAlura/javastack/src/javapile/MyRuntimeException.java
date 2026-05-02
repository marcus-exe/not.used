package javapile;
//those are unchecked
//because they are not verified by the compiler

public class MyRuntimeException extends RuntimeException{
	public MyRuntimeException(String mymessage) {
		super(mymessage);
	}
}
