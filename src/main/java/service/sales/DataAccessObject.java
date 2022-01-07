package service.sales;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Goods;

public class DataAccessObject extends webpos.DataAccessObject{
	private ResultSet rs;
	
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
