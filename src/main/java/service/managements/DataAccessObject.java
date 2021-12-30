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
	
	boolean insRegMmb(Connection con, Customers cu) {
		boolean result=false;
		String dml = "INSERT INTO CU(CU_CODE,CU_NAME,CU_PHONE,CU_CLCODE) VALUES(?,?,?,?)";
		try {
			this.psmt=con.prepareStatement(dml);
			this.psmt.setNString(1, cu.getCuCode());
			this.psmt.setNString(2, cu.getCuName());
			this.psmt.setNString(3, cu.getCuPhone());
			this.psmt.setInt(4, cu.getCuClCode());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}
	boolean insRegEmp(Connection con,Employees emp) {
		boolean result=false;
		String dml = "INSERT INTO EM(EM_SRCODE,EM_CODE,EM_NAME,EM_PASSWORD,EM_STATE)VALUES(?,?,?,?,?)";
		try {
			this.psmt=con.prepareStatement(dml);
			psmt.setNString(1, emp.getSecode());
			psmt.setNString(2, emp.getEmcode());
			psmt.setNString(3, emp.getEmName());
			psmt.setNString(4, emp.getEmpass());
			psmt.setInt(5, emp.getEmStateCode());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
	void getMmbMax(Connection con, Customers cu) {
		String query="SELECT MAX(CUCODE) AS CUMAX FROM DBA_RUN.CULIST";
		try {
			this.psmt=con.prepareStatement(query);
			this.rs=this.psmt.executeQuery();
			while(rs.next()) {
				cu.setCuCode((Integer.parseInt(rs.getNString("CUMAX"))+1)+"");
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {try{if(!rs.isClosed()) {rs.close();}}catch(SQLException e) {e.printStackTrace();}}
		
	}
	void getEmpMax(Connection con, Employees emp) {
		String query="SELECT MAX(EM_CODE) AS MAXEMP FROM EM WHERE EM_SRCODE=?";
		try {
			this.psmt=con.prepareStatement(query);
			psmt.setNString(1, emp.getSecode());
			this.rs=psmt.executeQuery();
			while(rs.next()) {
				
				String text=rs.getNString("MAXEMP").substring(0,1);
				int number=Integer.parseInt(rs.getNString("MAXEMP").substring(1,3))+1;
				if(number>0&&number<10) {
					emp.setEmcode(text+"0"+(number+""));
				}else {
					emp.setEmcode(text+(number+""));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {if(!rs.isClosed()) {rs.close();}}catch(SQLException e) {e.printStackTrace();}
		}
	}
	
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
				cu.setCuClCode(rs.getInt("CLCODE"));
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
				em.setEmStateCode(rs.getInt("EMSTATE"));
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
