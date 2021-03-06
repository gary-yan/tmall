package tmall.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDao {

//************************************************************************//	
	public int getTotal() {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select count(*) from user";// 查询T表中记录的行�? 结果是一个数�?
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

//************************************************************************//	

	public void add(User bean) {
		String sql = "insert into user values(null,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassowrd());
			ResultSet rs = ps.getGeneratedKeys();// 想获得刚插入记录的ID，可以用getGeneratedKeys获得insert插入后生成的主键ID
			if (rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//************************************************************************//	

	public void update(User bean) {

		String sql = "update user set name = ?, password =? where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassowrd());
			ps.setInt(3, bean.getId());

			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//************************************************************************//	

	public void delete(int id) {
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "delete from User wehre id = " + id;
			s.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//************************************************************************//	

	public User get(int id) {
		User bean = null;

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select * from user where id = " + id;

			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				bean = new User();
				String name = rs.getString("name");
				bean.setName(name);
				String password = rs.getString("password");
				bean.setPassowrd(password);
				bean.setId(id);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

//************************************************************************//	

	public List<User> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<User> list(int start, int count) {
		List<User> beans = new ArrayList<User>();

		String sql = "select * from user order by id desc limit?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setInt(1, start);
			ps.setInt(2, count);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User bean = new User();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				bean.setName(name);
				String password = rs.getString("password");
				bean.setPassowrd(password);
				bean.setId(id);
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

//************************************************************************//	

	public User get(String name) {
		User bean = null;

		String sql = "select * from user where name = ?";// why not use name as arguement
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new User();
				int id = rs.getInt("id");
				bean.setName(name);
				String password = rs.getString("password");
				bean.setPassowrd(password);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

//************************************************************************//	

	public User get(String name, String password) {
		User bean = null;
		String sql = "select * from User where name = ? and password=?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new User();
				int id = rs.getInt("id");
				bean.setName(name);
				bean.setPassowrd(password);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
}

//	public int getTotal() {
//		int total = 0;
//		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
//			String sql = "select count(*) from User";
//			ResultSet rs = s.executeQuery(sql);
//			while(rs.next()) {
//				total = rs.getInt(1);
//			}
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return total;
//	}
//	public void add(User bean) {
//		
//	}
