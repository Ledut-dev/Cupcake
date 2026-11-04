package app.persistence;

public class UserMapper {
	private ConnectionPool connectionPool;

	public UserMapper(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
}
