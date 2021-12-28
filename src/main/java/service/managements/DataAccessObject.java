package service.managements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Employee;

public class DataAccessObject extends webpos.DataAccessObject{
	private ResultSet rs;
	ArrayList<Employee> getEmpList(Connection con,Employee emp){
		ArrayList<Employee> list=new ArrayList<Employee>();
		String query = "SELECT*FROM DBA_RUN.EMLIST WHERE EMSRCODE= ?";
		try {
			this.psmt=con.prepareStatement(query);
			this.psmt.setNString(1, emp.getSecode());
			this.rs=this.psmt.executeQuery();
			while(rs.next()) {
				Employee em = new Employee();
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
