package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import tmall.bean.Review;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DateUtil;
import tmall.util.DBUtil;

public class ReviewDao {
	
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select count(*) from Review ";
			
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getTotal(int pid) {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select count(*) from Review where pid =" + pid;
			
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total=rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public void add(Review bean) {
		String sql = "insert into Review values(null,?,?,?,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			
			ps.execute();
			
			ResultSet rs =ps.getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void update(Review bean) {
		String sql = "update Review set content =?, uid = ?. pid = ?, create =? where id =?";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4,DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(5, bean.getId());
			ps.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "delete * from Review where id = "+ id;
			
			s.execute(sql);
			}catch(SQLException e) {
				e.printStackTrace();
		}
	}
	
	public Review get(int id) {
		
	}
}
