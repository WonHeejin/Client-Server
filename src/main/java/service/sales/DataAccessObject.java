package service.sales;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Goods;
import beans.Orders;

public class DataAccessObject extends webpos.DataAccessObject{
	private ResultSet rs;
	
	boolean updOrderState(Connection con, Orders od) {
		boolean result=false;
		String dml="UPDATE OD SET OD_STCODE=7 WHERE OD_CODE=? AND OD_POSRCODE=? AND OD_POCODE=?";
		try {
			this.psmt=con.prepareStatement(dml);
			this.psmt.setNString(1, od.getOdCode());
			this.psmt.setNString(2, od.getOdPoSrCode());
			this.psmt.setNString(3, od.getOdPoCode());
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}
	boolean insOrderDetail(Connection con, Orders od, int idx) {
		boolean result=false;
		String dml="INSERT INTO OS(OS_ODCODE, OS_ODPOSRCODE, OS_ODPOCODE, OS_GOCODE, OS_QUANTITY)"
				+ "       VALUES (?, ?, ?, ?, ?)";
		try {
			this.psmt=con.prepareStatement(dml);
			this.psmt.setNString(1, od.getOdCode());
			this.psmt.setNString(2, od.getOdPoSrCode());
			this.psmt.setNString(3, od.getOdPoCode());
			this.psmt.setNString(4, od.getGoCode()[idx]);
			this.psmt.setInt(5, Integer.parseInt(od.getGoQuantity()[idx]));
			result=this.convertToboolean(this.psmt.executeUpdate());
		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}
	
	void insOrders(Connection con, Orders od) {
		String dml="INSERT INTO OD(OD_CODE, OD_POSRCODE, OD_POCODE, OD_CUCODE, OD_STCODE) VALUES(DEFAULT, ?, ?, ?, DEFAULT)";
		String query="SELECT MAX(OD_CODE) AS ODCODE FROM OD WHERE OD_POSRCODE=? AND OD_POCODE=?";
		try {
			this.psmt=con.prepareStatement(dml);	
			this.psmt.setNString(1, od.getOdPoSrCode());
			this.psmt.setNString(2, od.getOdPoCode());
			this.psmt.setNString(3, od.getOdCuCode());
			if(this.convertToboolean(this.psmt.executeUpdate())) {
				this.psmt=con.prepareStatement(query);
				this.psmt.setNString(1, od.getOdPoSrCode());
				this.psmt.setNString(2, od.getOdPoCode());
				this.rs=this.psmt.executeQuery();
				while(this.rs.next()) {
					od.setOdCode(rs.getNString("ODCODE"));
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			try {if(!this.rs.isClosed()) this.rs.close();}catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	
	ArrayList<Goods> getGoodsInfo(Connection con, Goods go){
		ArrayList<Goods> list = new ArrayList<Goods>();
		String query="SELECT GO_STCODE AS GOSTCODE, GO_CODE AS GOCODE, GO_NAME AS GONAME, GO_PRICE AS GOPRICE, GO_DISCOUNT AS GODISCOUNT "
				+ "FROM GO WHERE GO_CODE=?";
		try {
			this.psmt=con.prepareStatement(query);
			this.psmt.setNString(1, go.getGoCode());
			this.rs=this.psmt.executeQuery();
			while(this.rs.next()) {
				Goods gd=new Goods();
				gd.setGoState(this.rs.getInt("GOSTCODE"));
				gd.setGoCode(this.rs.getNString("GOCODE"));
				gd.setGoName(this.rs.getNString("GONAME"));
				gd.setGoPrice(this.rs.getInt("GOPRICE"));
				gd.setGoQuantity(1);
				gd.setGoDiscount(this.rs.getInt("GOPRICE")*this.rs.getInt("GODISCOUNT")/100);
				list.add(gd);
			}
		}catch(SQLException e) {e.printStackTrace();}
		return list;
	}
}
