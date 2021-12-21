package service.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Employee;

class DataAccessObject {
	private PreparedStatement psmt;
	private ResultSet rs;
	DataAccessObject(){
		psmt=null;
		rs=null;
	}  
	 
	 /* Driver Loading & Create Connection */
	  Connection getConnection() {
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
	 void modifyTran(Connection connection, boolean status) {
		 try {
			 if(connection!=null&&!connection.isClosed()) {
				 connection.setAutoCommit(status);
			 }			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 /* Transaction 처리 :: Commit || Rollback */
	 void setTran(Connection connection, boolean tran) {
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
	 void closeConnection(Connection connection) {
		 try {
			 if(!psmt.isClosed()) {psmt.close();}
			if(connection!=null&&!connection.isClosed()) {
				 connection.close();
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
/* 2. DAO 연동 
		 *   2-1. STORE :: SECODE 존재 여부
		 *   2-2. EMPLOYEE :: EMCODE 존재 여부
		 *   2-3. EMPLOYEE :: PASSWORD일치 여부 :: RETURN : 1  >> P2-4 
		 *   2-4. ACCESSHISTORY : INSERT :: RETURN : 1
		 *   2-5. 정보 취합  --> ARRAYLIST<EMPLOYEE>*/
	 boolean isStore(Connection connection, Employee emp) {
		 boolean result=false;
		 String query="SELECT COUNT(*) FROM EM WHERE EM_SRCODE=? AND EM_CODE=?";
		 try {
			psmt=connection.prepareStatement(query);
			psmt.setNString(1, emp.getSecode());
			psmt.setNString(2, emp.getEmcode());
			rs=psmt.executeQuery();
			while(rs.next()) {
				result=this.convertToboolean(rs.getInt(1));			
			}
		} catch (SQLException e) { e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) {rs.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return result;
	 }
	 
	 boolean isPassword(Connection connection, Employee emp) {
		 boolean result=false;
		 String query="SELECT COUNT(*) FROM EM WHERE EM_SRCODE=? AND EM_CODE=? AND EM_PASSWORD=?";
		 try {
			psmt=connection.prepareStatement(query);
			psmt.setNString(1, emp.getSecode());
			psmt.setNString(2, emp.getEmcode());
			psmt.setNString(3, emp.getEmpass());
			rs=psmt.executeQuery();
			while(rs.next()) {
				result=this.convertToboolean(rs.getInt(1));
			}
		} catch (SQLException e) {
		}finally {
			try {
				if(!rs.isClosed()) {rs.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return result;
	 }
		/* insAccessHistory */
	 boolean insAccessHistory(Connection connection, Employee emp) {
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
	 ArrayList<Employee> getAccessInfo(Connection connection, Employee emp){
		 ArrayList<Employee> list=new ArrayList<Employee>();
		 String query="SELECT SRCODE, SRNAME, EMCODE, EMNAME, DATES FROM ACCESSINFO        "
		 		+ "WHERE DATES = (SELECT TO_CHAR(MAX(AH_ACCESSTIME), 'YYYY-MM-DD HH24:MI:SS') FROM AH"
		 		+ "                    WHERE AH_SRCODE=? AND AH_EMCODE=?)";
		 try {
			psmt=connection.prepareStatement(query);
			psmt.setNString(1, emp.getSecode());
			psmt.setNString(2, emp.getEmcode());
			rs=psmt.executeQuery();
			while(rs.next()) {
				Employee em=new Employee();
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
	 boolean convertToboolean(int value) {		 
		 return (value>0)?true:false;
	 }
}
