package tmall.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDao {
	public int getTotal() {//获取总数
		int total = 0;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
		String sql = "select count(*) from category";
		ResultSet rs = s.executeQuery(sql);
		while (rs.next()) {
			total = rs.getInt(1);
		}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
			return total;
		}
	public void add(Category bean) {//增加
		String sql = "insert into category values(null,?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
		
		ps.setString(1, bean.getName());//1指的是第一个问好位置 getname是带入的参数值
		
		ps.execute();
		
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			int id = rs.getInt(1);
			bean.setId(id);//这是DAO常见行为，一个对象被add增加到数据库之后，很有可能在接下来的业务里，需要知道这个对象在表中的id是多少呢
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void update(Category bean) {//修改
		String sql = "update category set name = ? where id = ?" ;
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void delete(int id) {//删除
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "delete from Category where id = " + id;
			s.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Category get(int id) {//根据id获取
		Category bean = null ;
		
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select * from category where id = " + id;
			ResultSet rs = s.executeQuery(sql);//用executeQuery时，执行sql语句并返回该查询生成的resultset对象
			if(rs.next()) {
				bean = new Category();
				String name = rs.getString(2);
				bean.setName(name);
				bean.setId(id);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;//return bean会return什么?
	}
	public List<Category> list(){//分页查询
		return list(0,Short.MAX_VALUE);//Java有一个名为Short的类，它定义了两个常量来表示短数据类型的最大值和最小值，它们分别是：Short.MAX_VALUE和Short.MIN_VALUE。
	}//为什么要限制？ 初始化?
	
	public List<Category> list(int start, int count){
		List<Category> beans = new ArrayList<Category>();
		String sql = "select * from Category order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Category bean = new Category();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				bean.setId(id);
				bean.setName(name);
				beans.add(bean);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	}

