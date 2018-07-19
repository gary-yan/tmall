package tmall.bean;

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
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select count(*) from user";//查询T表中记录的行数 结果是一个数值
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public void add(User bean) {
		String sql = "insert into user values(null,?,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps= c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassowrd());
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
			}catch(SQLException e) {
				e.printStackTrace();
		}
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

