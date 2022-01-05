package service.managements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Customers;
import beans.Employees;
import beans.Goods;
import beans.Sales;

public class DataAccessObject extends webpos.DataAccessObject{
	private ResultSet rs;
	
	ArrayList<Sales> getGoodsInfo(Connection con, Sales sales){
		ArrayList<Sales> list= new ArrayList<Sales>();
		String query="SELECT SUBSTR(MONTHLY,1,6) AS MONTHLY, SRCODE, SRNAME,GOCODE, SUM(AMOUNT) AS AMOUNT, SUM(GOCOST) AS GOCOST, SUM(PROFIT) AS PROFIT "
				+ "FROM DBA_RUN.SALESINFO WHERE SUBSTR(MONTHLY,1,6)=TO_CHAR(SYSDATE,'YYYYMM') AND SRCODE=? "
				+ "GROUP BY SUBSTR(MONTHLY,1,6),GOCODE, SRCODE,SRCODE, SRNAME";
		try {
			this.psmt=con.prepareStatement(query);
			this.psmt.setNString(1, sales.getSeCode());
			rs=this.psmt.executeQuery();
			while(rs.next()) {
				Sales s = new Sales();
				s.setMonthly(rs.getNString("MONTHLY"));
				s.setSeCode(rs.getNString("SRCODE"));
				s.setSeName(rs.getNString("SRNAME"));
				s.setGoCode(rs.getNString("GOCODE"));
				s.setAmount(rs.getInt("AMOUNT"));
				s.setGoCost(rs.getInt("GOCOST"));
				s.setProfit(rs.getInt("PROFIT"));
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {if(rs.isClosed()) {rs.close();}} catch (SQLException e) {e.printStackTrace();}
		}
		
		return list;
	}
	ArrayList<Sales> getSalesInfo(Connection con, Sales sales){
		ArrayList<Sales> list= new ArrayList<Sales>();
		String query="SELECT SUBSTR(MONTHLY,1,6) AS MONTHLY, SRCODE, SRNAME, SUM(AMOUNT) AS AMOUNT, SUM(GOCOST) AS GOCOST, SUM(PROFIT) AS PROFIT "
				+ "FROM DBA_RUN.SALESINFO WHERE SUBSTR(MONTHLY,1,6)=TO_CHAR(SYSDATE,'YYYYMM') AND SRCODE=? "
				+ "GROUP BY SUBSTR(MONTHLY,1,6), SRCODE,SRCODE, SRNAME";
		try {
			this.psmt=con.prepareStatement(query);
			this.psmt.setNString(1, sales.getSeCode());
			rs=this.psmt.executeQuery();
			while(rs.next()) {
				Sales s = new Sales();
				s.setMonthly(rs.getNString("MONTHLY"));
				s.setSeCode(rs.getNString("SRCODE"));
				s.setSeName(rs.getNString("SRNAME"));
				s.setAmount(rs.getInt("AMOUNT"));
				s.setGoCost(rs.getInt("GOCOST"));
				s.setProfit(rs.getInt("PROFIT"));
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {if(rs.isClosed()) {rs.close();}} catch (SQLException e) {e.printStackTrace();}
		}
		
		return list;
	}
	boolean modGo(Connection con, Goods go) {	
		boolean result=false;
		
		String dml = "UPDATE GO SET GO_COST=?, GO_PRICE=?, GO_STOCKS=?, GO_DISCOUNT=?, GO_CACODE=?, GO_STCODE=? WHERE GO_CODE=?";
		try {
			this.psmt=con.prepareStatement(dml);
			this.psmt.setInt(1, go.getGoCost());
			this.psmt.setInt(2, go.getGoPrice());
			this.psmt.setInt(3, go.getGoStocks());
			this.psmt.setInt(4, go.getGoDiscount());
			this.psmt.setNString(5, go.getGoCaCode());
			this.psmt.setInt(6, go.getGoState());
			this.psmt.setNString(7, go.getGoCode());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	boolean updMmb(Connection con, Customers cu) {	
		boolean result=false;
		
		String dml = "UPDATE CU SET CU_CLCODE=? WHERE CU_CODE=?";
		try {
			this.psmt=con.prepareStatement(dml);
			this.psmt.setNString(1, cu.getCuClCode()+"");
			this.psmt.setNString(2, cu.getCuCode());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	ArrayList<Customers>getMmbCodeList(Connection con){
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
	boolean updEmp(Connection con,Employees emp) {
		boolean result=false;
		String dml = "UPDATE EM SET EM_PASSWORD=?, EM_STATE=? WHERE EM_SRCODE=? AND EM_CODE=?";
		try {
			this.psmt=con.prepareStatement(dml);
			psmt.setNString(1, emp.getEmpass());
			psmt.setInt(2, emp.getEmStateCode());
			psmt.setNString(3, emp.getSecode());
			psmt.setNString(4, emp.getEmcode());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	ArrayList<Employees>getEmpCodeList(Connection con,Employees emp){
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
	boolean insRegGo(Connection con, Goods go) {
		boolean result=false;
		String dml="INSERT INTO GO(GO_CODE,GO_NAME,GO_COST,GO_PRICE,GO_STOCKS,GO_DISCOUNT,GO_CACODE,GO_STCODE)"
				+ "       VALUES(?,?,?,?,?,?,?,?)";
		try {
System.out.println(go.getGoState());
			this.psmt=con.prepareStatement(dml);
			this.psmt.setNString(1, go.getGoCode());
			this.psmt.setNString(2, go.getGoName());
			this.psmt.setInt(3, go.getGoCost());
			this.psmt.setInt(4, go.getGoPrice());
			this.psmt.setInt(5, go.getGoStocks());
			this.psmt.setInt(6, go.getGoDiscount());
			this.psmt.setNString(7, go.getGoCaCode());
			this.psmt.setInt(8, go.getGoState());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		
		return result;
	}
	
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
				go.setGoState(rs.getInt("GOSTCODE"));
				go.setGoStName(rs.getNString("STNAME"));
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
