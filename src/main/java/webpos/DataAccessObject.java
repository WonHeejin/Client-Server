package webpos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Employees;

public class DataAccessObject {
	
	protected PreparedStatement psmt;	
	protected ResultSet rs;
	
	protected DataAccessObject() {
		this.psmt=null;
		this.rs=null;
	}
	
	 /* Driver Loading & Create Connection */
	public Connection getConnection() {
		 Connection connection= null;
		 String[] url= {"jdbc:oracle:thin:@192.168.0.139:1521:xe","HJ","1234"};
		 try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 connection=DriverManager.getConnection(url[0], url[1], url[2]);
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }		 
		 return connection;
	 }
	 
	 /* Transaction 상태 변경(수동) */
	public void modifyTran(Connection connection, boolean status) {
		 try {
			 if(connection!=null&&!connection.isClosed()) {
				 connection.setAutoCommit(status);
			 }			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 /* Transaction 처리 :: Commit || Rollback */
	public void setTran(Connection connection, boolean tran) {
		 try {
			 if(connection!=null&&!connection.isClosed()) {
				 if(tran) {
					 connection.commit();
				 }else {
					 connection.rollback();
				 }
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 /* Close Connection */
	public void closeConnection(Connection connection) {
		 try {
			 if(!psmt.isClosed()) {psmt.close();}
			if(connection!=null&&!connection.isClosed()) {
				 connection.close();
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	/* insAccessHistory */
	public boolean insAccessHistory(Connection connection, Employees emp) {
		 boolean result=false;
		 String dml="INSERT INTO AH(AH_SRCODE,AH_EMCODE,AH_ACCESSTIME,AH_ACCESSTYPE) VALUES (?,?,DEFAULT,?)";
		 try {
			psmt=connection.prepareStatement(dml);
			psmt.setNString(1, emp.getSecode());
			psmt.setNString(2, emp.getEmcode());
			psmt.setInt(3, emp.getStates());
			result=this.convertToboolean(psmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}	 
		 return result;
	 }
		/* getAccessInfo */
	 public ArrayList<Employees> getAccessInfo(Connection connection, Employees emp){
		 ArrayList<Employees> list=new ArrayList<Employees>();
		 String query="SELECT SRCODE, SRNAME, EMCODE, EMNAME, DATES FROM ACCESSINFO        "
		 		+ "WHERE DATES = (SELECT TO_CHAR(MAX(AH_ACCESSTIME), 'YYYY-MM-DD HH24:MI:SS') FROM AH"
		 		+ "                    WHERE AH_SRCODE=? AND AH_EMCODE=?)";
		 try {
			psmt=connection.prepareStatement(query);
			psmt.setNString(1, emp.getSecode());
			psmt.setNString(2, emp.getEmcode());
			rs=psmt.executeQuery();
			while(rs.next()) {
				Employees em=new Employees();
				em.setSecode(rs.getNString("SRCODE"));
				em.setSename(rs.getNString("SRNAME"));
				em.setEmcode(rs.getNString("EMCODE"));
				em.setEmName(rs.getNString("EMNAME"));
				em.setDate(rs.getNString("DATES"));
				list.add(em);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) {rs.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return list;
	 }
	
	protected boolean convertToboolean(int value) {		 
		 return (value>0)?true:false;
	 } 
}
