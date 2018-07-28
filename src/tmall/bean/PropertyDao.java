package tmall.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class PropertyDao {
	
	public int getTotal(int cid) {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			
			String sql = "select count(*) from Property where cid =" + cid;
			
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Property bean) {
		String sql = "insert into Property values(null,?,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update (Property bean) {
		String sql = "updagte Property set cid = ?, name =? where id =?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName() );
			ps.setInt(3, bean.getId());
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
