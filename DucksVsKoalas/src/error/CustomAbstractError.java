package error;

@SuppressWarnings("serial")
public class CustomAbstractError extends Throwable {
	@SuppressWarnings("rawtypes")
	private Class _class;
	@SuppressWarnings("rawtypes")
	public CustomAbstractError(Class _class) {
		this._class = _class;
	}
	public void printStackTrace() {
		System.out.println(_class.getName() + " is a custom form of an abstract class.");
	}
}
