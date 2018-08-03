package tmall.bean;
//package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDao {
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement(); ){
			String sql = "select count(*) from PropertyValue" ;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total =rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(PropertyValue bean) {
		String sql = "insert into Property values (null,?,?,?)";
		try(Connection c =DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2,bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);//这个id是propertyvalue的id?
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(PropertyValue bean) {
		String sql = "update propertyvalue set pid = ?, ptid = ?,value = ? where id = ? ";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "delete from propertyvalue where id =" + id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
