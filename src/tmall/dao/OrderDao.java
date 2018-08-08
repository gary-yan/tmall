package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderDao {
	public static final String waitPay = "waitPay";
	public static final String waitDelivery = "waitDelivery";
	public static final String waitConfirm = "waitConfirm";
	public static final String waitReview = "waitReview";
	public static final String finish = "finish";
	public static final String delete = "delete";
	
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select count(*) from Order_"; //因为数据库order是关键字 所以 order加下划线
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Order bean) {
		//Order bean = new Order();//是否与Order bean有着相同的效果
		String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
            ps.setString(4, bean.getReceiver());
            ps.setString(5, bean.getMobile());
            ps.setString(6, bean.getUserMessage());
		}
	}
}
