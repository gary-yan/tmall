package tmall.dao;
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
	public PropertyValue get(int id) {
		PropertyValue bean = new PropertyValue();
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select * from PropertyValue where id ="+ id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");
				
				Product product = new ProductDao().get(pid);//?
				Property property = new PropertyDao().get(ptid);//?
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public PropertyValue get(int ptid, int pid) {
		PropertyValue bean = null;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select * from PropertyValue where ptid = "+ ptid +"and pid =" +pid;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				bean = new PropertyValue();
				int id = rs.getInt("id");
				String value = rs.getString("value");
				Product product = new ProductDao().get(pid);
				Property property = new PropertyDao().get(ptid);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<PropertyValue> list(){
		return list(0, Short.MAX_VALUE);
	}
	public List<PropertyValue> list(int start, int count){
		List<PropertyValue> beans = new ArrayList<PropertyValue>();
		
		String sql = "select * from PropertyValue order by id desc limit ?,?";//从第一个？条记录开始 取第二个？条记录
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PropertyValue bean = new PropertyValue();
				int id = rs.getInt(1);
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");
				
				Product product = new ProductDao().get(pid);
				Property property = new PropertyDao().get(ptid);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
				beans.add(bean);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//初始化
	public void init(Product p) {
		List<Property> pts = new PropertyDao().list(p.getCategory().getId());
		
		for(Property pt: pts) {
			PropertyValue pv = get(pt.getId(), p.getId());
			if(null==pv) {
				pv = new PropertyValue();
				pv.setProduct(p);
				pv.setProperty(pt);
				this.add(pv);
			}
		}
	}
	
	public List<PropertyValue> list (int pid){
		List<PropertyValue> beans = new ArrayList<PropertyValue>();
		String sql = "select * from PropertyValue where pid = ? order by ptid desc";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PropertyValue bean = new PropertyValue();
				int id = rs.getInt(1);
				
				
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");
				
				Product product = new ProductDao().get(ptid);
				 Property property = new PropertyDao().get(ptid);
	                bean.setProduct(product);
	                bean.setProperty(property);
	                bean.setValue(value);
	                bean.setId(id);          
	                beans.add(bean);
	            }
	        } catch (SQLException e) {
	  
	            e.printStackTrace();
	        }
	        return beans;
	    }
				
			}
		
	

