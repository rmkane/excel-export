import java.time.LocalDate;

public class Employee {
	private long id;
	private String name;
	private LocalDate dob;

	public Employee() {
		this(0, null, null);
	}

	public Employee(long id, String name, LocalDate dob) {
		this.id = id;
		this.name = name;
		this.dob = dob;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return String.format("{ 'id' : %d, 'name' : '%s', 'dob' : '%s' }", id, name, dob);
	}
}
