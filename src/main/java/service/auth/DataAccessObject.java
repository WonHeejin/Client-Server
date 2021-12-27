package service.auth;

import java.sql.Connection;
import java.sql.SQLException;
import beans.Employee;

class DataAccessObject extends webpos.DataAccessObject{

	DataAccessObject(){
		
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
		
	
}
