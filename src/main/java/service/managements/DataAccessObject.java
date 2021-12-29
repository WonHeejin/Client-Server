package service.managements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Customers;
import beans.Employees;
import beans.Goods;

public class DataAccessObject extends webpos.DataAccessObject{
	private ResultSet rs;
	
	ArrayList<Goods> getGoList(Connection con){
		ArrayList<Goods> list=new ArrayList<Goods>();
		String query="SELECT*FROM DBA_RUN.GOLIST";
		
		try {
			this.psmt=con.prepareStatement(query);
			rs=this.psmt.executeQuery();
			while(rs.next()) {
				Goods go= new Goods();
				go.setGoCode(rs.getNString("GOCODE"));
				go.setGoName(rs.getNString("GONAME"));
				go.setGoCost(rs.getInt("GOCOST"));
				go.setGoPrice(rs.getInt("GOPRICE"));
				go.setGoStocks(rs.getInt("GOSTOCKS"));
				go.setGoDiscount(rs.getInt("GODISCOUNT"));
				go.setGoCaCode(rs.getNString("GOCACODE"));
				go.setGoCaName(rs.getNString("GOCANAME"));
				go.setGoState((rs.getInt("GOSTCODE")==1)?"판매가능":"판매불가");
				list.add(go);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {try {if(!rs.isClosed()) {rs.close();}} catch (SQLException e) {}}
		return list;
	}
	
	ArrayList<Customers> getCuList(Connection con){
		ArrayList<Customers> list= new ArrayList<Customers>();
		String query="SELECT*FROM DBA_RUN.CULIST";
		try {
			this.psmt=con.prepareStatement(query);
			rs=this.psmt.executeQuery();
			while(rs.next()) {
				Customers cu= new Customers();
				cu.setCuCode(rs.getNString("CUCODE"));
				cu.setCuName(rs.getNString("CUNAME"));
				cu.setCuPhone(rs.getNString("CUPHONE"));
				cu.setCuClCode(rs.getNString("CLCODE"));
				cu.setCuClName(rs.getNString("CLNAME"));
				list.add(cu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {if(rs.isClosed()) {rs.close();}} catch (SQLException e) {e.printStackTrace();}
		}
		
		return list;
	}
	
	ArrayList<Employees> getEmpList(Connection con,Employees emp){
		ArrayList<Employees> list=new ArrayList<Employees>();
		String query = "SELECT*FROM DBA_RUN.EMLIST WHERE EMSRCODE= ?";
		try {
			this.psmt=con.prepareStatement(query);
			this.psmt.setNString(1, emp.getSecode());
			this.rs=this.psmt.executeQuery();
			while(rs.next()) {
				Employees em = new Employees();
				em.setSecode(rs.getNString("EMSRCODE"));
				em.setEmcode(rs.getNString("EMCODE"));
				em.setEmName(rs.getNString("EMNAME"));
				em.setEmStateCode(rs.getNString("EMSTATE"));
				em.setEmStateName(rs.getNString("STNAME"));
				em.setTodayInfo((rs.getInt("CNT")==0)?"결근":((rs.getInt("CNT")==1)?"출근":"퇴근"));
				list.add(em);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) {rs.close();}} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
}
