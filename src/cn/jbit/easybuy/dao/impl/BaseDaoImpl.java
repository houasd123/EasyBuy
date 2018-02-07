package cn.jbit.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.jbit.easybuy.entity.Pager;
import cn.jbit.easybuy.util.Validator;

public class BaseDaoImpl {
	protected Connection connection;

	public BaseDaoImpl(Connection connection) {
		this.connection = connection;
	}
	
	//获取一共多少条记录
	protected long getRowCount(String tableName) throws SQLException {
		return this.getRowCount(tableName, null);
	}

	//获取一共多少条记录，带有条件
	protected long getRowCount(String tableName, String condition)
			throws SQLException {
		PreparedStatement ps = connection
				.prepareStatement("SELECT count(*) FROM "
						+ tableName
						+ ((Validator.isEmpty(condition)) ? ""
								: (" WHERE " + condition)));
		if(tableName.equals("easybuy_order")){//查询订单时涉及到多个表			
			ps = connection
					.prepareStatement("select COUNT(  EASYBUY_PRODUCT.EP_ID)   from  EASYBUY_PRODUCT,EASYBUY_ORDER_DETAIL,EASYBUY_ORDER  where   EASYBUY_ORDER.EO_ID=EASYBUY_ORDER_DETAIL.EO_ID and EASYBUY_ORDER_DETAIL.EP_ID= EASYBUY_PRODUCT.EP_ID  "
											+ ((Validator.isEmpty(condition)) ? ""
									: (" and " + condition)));
		}
		ResultSet rs = ps.executeQuery();
		rs.next();
		Long rtn = rs.getLong(1);
		rs.close();
		ps.close();
		return rtn.longValue();
	}
	//分页时的SQL语句
	protected String getSqlForPages(String tableName, String key,
			String orderBy, String orderBy2,Pager pager) {		
		return "select top "+pager.getRowPerPage()+" * from "+tableName+" where "+key+" not in(select top "+
				(pager.getCurrentPage()-1)*pager.getRowPerPage()+" "+key+" from "+tableName+orderBy+") "+orderBy;
	}
	protected String getSqlForPages(String tableName, String where,String key,
			String orderBy, String orderBy2,Pager pager) {		
		return "select top "+pager.getRowPerPage()+" * from "+tableName+" where "+where+" and "+key+" not in(select top "+
				(pager.getCurrentPage()-1)*pager.getRowPerPage()+" "+key+" from "+tableName+" where "+where+orderBy+") "+orderBy;
	}
	protected String getSqlForPages(String tableName, String key,
			String orderBy,Pager pager) {		
		return "select top "+pager.getRowPerPage()+" * from "+tableName+" where "+key+" not in(select top "+
				(pager.getCurrentPage()-1)*pager.getRowPerPage()+" "+key+" from "+tableName+orderBy+") "+orderBy;
	}
	protected String getSqlForPages(String tableName1,String tableName2, String file,String key,String where,String where2,
			String orderBy,String orderBy2,Pager pager) {		
		return "select top "+pager.getRowPerPage()+file+"  from "+tableName1+" where "+where+" and "+key+" not in(select top "+
				(pager.getCurrentPage()-1)*pager.getRowPerPage()+" "+key+" from "+tableName2+where2+orderBy+") "+orderBy2;
	}
}
