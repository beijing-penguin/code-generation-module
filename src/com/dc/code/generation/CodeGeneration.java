package com.dc.code.generation;

import java.io.File;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.yanxiu.jdbc.core.entity.ColumnBean;
import com.yanxiu.jdbc.core.entity.TableInfoBean;
import com.yanxiu.jdbc.core.utils.JDBCUtils;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author DC
 * @time 2016-11-29
 */
public class CodeGeneration {
	private static String jdbc_url = "jdbc:mysql://localhost:3306/ucenterlog_0?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false";
	private static String username = "root";
	private static String pwd = "123456";
	public static void main( String[] args ) throws Exception{
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setIdleTimeout(3000);
		dataSource.setMaximumPoolSize(100);
		dataSource.setJdbcUrl(jdbc_url);
		dataSource.setUsername(username);
		dataSource.setPassword(pwd);
		dataSource.setConnectionTimeout(30000);
		dataSource.setLoginTimeout(10000);
		
		String dataBaseName = dataSource.getJdbcUrl().substring(dataSource.getJdbcUrl().indexOf("//")+2);
		dataBaseName = dataBaseName.substring(dataBaseName.indexOf("/")+1,dataBaseName.indexOf("?"));
		dataBaseName = JDBCUtils.getBeanName(dataBaseName);
		List<TableInfoBean> databaseList = JDBCUtils.initDataBaseInfo(dataSource);
		for (int i = 0; i < databaseList.size(); i++) {
			TableInfoBean tabInfo = databaseList.get(i);
			String tableName = tabInfo.getTableName();
			HashSet<String> importSet = new HashSet<String>();
			//String packageStr = "package"+" "+packageName+";\r\n\r\n";
			String packageStr = "";
			List<ColumnBean>  colList = tabInfo.getColumnList();
			String className = JDBCUtils.getBeanName(tableName);
			className = className.substring(0,1).toUpperCase()+className.substring(1);
			String classFileStrStart = "public class "+className +" {\r\n";
			String tempFieldName = "";
			String classFileStr = "";
			
			String getSetStr = "";
			tempFieldName += "\tpublic static final String TABLE_NAME = \""+tableName+"\";\r\n";
			for (int j = 0; j < colList.size(); j++) {
				ColumnBean colbean = colList.get(j);
				int type = colbean.getColumnType();
				String fieldTypeStr = null;
				if(type== Types.INTEGER){
					fieldTypeStr = "Integer";
				}else if(type== Types.SMALLINT){
					fieldTypeStr = "Short";
				}else if(type== Types.BIGINT){
					fieldTypeStr = "Long";
				}else if(type== Types.TINYINT || type== Types.LONGVARBINARY || type== Types.BIT){
					fieldTypeStr = "Byte";
				}else if(type== Types.FLOAT){
					fieldTypeStr = "Float";
				}else if(type== Types.DOUBLE){
					fieldTypeStr = "Double";
				}else if(type== Types.CHAR){
					fieldTypeStr = "Character";
				}else if(type== Types.TIMESTAMP || type== Types.DATE){
					fieldTypeStr = "Date";
					importSet.add("import java.util.Date;\r\n");
				}else if(type== Types.VARCHAR){
					fieldTypeStr = "String";
				}
				String fieldName = JDBCUtils.getBeanName(colbean.getColumnName());
				classFileStr += "\tprivate "+fieldTypeStr+" "+fieldName+";\r\n";
				tempFieldName += "\tpublic static final String "+colbean.getColumnName().toUpperCase()+" = \""+colbean.getColumnName()+"\";\r\n";
				
				getSetStr+= "\tpublic "+fieldTypeStr+" get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1)+"() {\r\n\t\treturn "+fieldName+";\r\n\t}\r\n";
				getSetStr+= "\tpublic void set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1)+"("+fieldTypeStr+" "+fieldName+") {\r\n\t\tthis."+fieldName+"="+fieldName+";\r\n\t}\r\n";
			}
			for (String imStr :importSet) {
				packageStr +=  imStr;
			}
			//classFileStr = packageStr + classFileStr + getSetStr;
			//classFileStr += "}";
			String entityData = packageStr + classFileStrStart + tempFieldName + classFileStr + getSetStr +"}";
			File file = new File("E:/generation/entity/"+className+".java");
			FileUtils.write(file, entityData, "UTF-8");
			System.out.println(file.getAbsolutePath()+"生成成功");
			//生成dao
			String daoFileStr = "";
			String daoFileStrMothed = "";
			String serviceFileStr = "";
			String serviceFileStrMothed = "";
			daoFileStr += "import com.yanxiu.jdbc.core.DBHelper;\r\n";
			daoFileStr += "import javax.annotation.Resource;\r\n";
			serviceFileStr += "import javax.annotation.Resource;\r\n";
			daoFileStr += "import org.springframework.stereotype.Component;\r\n";
			daoFileStr += "import java.util.List;\r\n\r\n";
			serviceFileStr += "import java.util.List;\r\n\r\n";
			daoFileStr += "import java.util.ArrayList;\r\n\r\n";
			serviceFileStr += "import java.util.ArrayList;\r\n\r\n";
			daoFileStr += "@Component\r\npublic class "+className+"Dao {\r\n";
			serviceFileStr += "@Service\r\npublic class "+className+"ServiceImpl implements "+className+"Service{\r\n";
			String dbhelperStr = dataBaseName+"DBHelper";
			daoFileStr += "\t@Resource\r\n\tprivate DBHelper "+dbhelperStr+";\r\n\r\n";
			serviceFileStr += "\t@Resource\r\n\tprivate "+className+"Dao "+JDBCUtils.getBeanName(className)+"Dao;\r\n\r\n";
			
			String methodParamStr = JDBCUtils.getBeanName(className);
			daoFileStrMothed += "\tpublic Pagination<"+className+"> queryList"+className+"ByPage(Pagination<"+className+"> page) throws Exception{\r\n";
			daoFileStrMothed += "\t\tif(page == null) {\r\n";
			daoFileStrMothed += "\t\t\tpage = new Pagination<"+className+"> ();\r\n";
			daoFileStrMothed += "\t\t}\r\n";
			daoFileStrMothed += "\t\tString sql = \"select * from \" +"+className+".TABLE_NAME;\r\n";
			daoFileStrMothed += "\t\tpage.setTotalElements("+dbhelperStr+".selectCount(sql));\r\n";
			daoFileStrMothed += "\t\tsql = sql + \"limit ?,?\";\r\n";
			daoFileStrMothed += "\t\tList<Object> paramsList = new ArrayList<Object>();\r\n";
			daoFileStrMothed += "\t\tparamsList.add(page.getOffset());\r\n";
			daoFileStrMothed += "\t\tparamsList.add(page.getPageSize());\r\n";
			daoFileStrMothed += "\t\tpage.setElements("+dbhelperStr+".selectList(sql,"+className+".class,paramsList));\r\n";
			daoFileStrMothed += "\t\treturn page;\r\n";
			daoFileStrMothed += "\t}\r\n";
			
			daoFileStrMothed += "\tpublic List<"+className+"> queryList"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			daoFileStrMothed += "\t\treturn "+dbhelperStr+".selectEntity("+methodParamStr+");\r\n\t}\r\n";
			
			daoFileStrMothed += "\tpublic "+className+" queryOne"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			daoFileStrMothed += "\t\treturn "+dbhelperStr+".selectOneEntity("+methodParamStr+");\r\n\t}\r\n";
			
			daoFileStrMothed += "\tpublic int delete"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			daoFileStrMothed += "\t\treturn "+dbhelperStr+".deleteEntity("+methodParamStr+");\r\n\t}\r\n";
			
			daoFileStrMothed += "\tpublic int update"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			daoFileStrMothed += "\t\treturn "+dbhelperStr+".updateEntity("+methodParamStr+");\r\n\t}\r\n";
			
			daoFileStrMothed += "\tpublic int insert"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			daoFileStrMothed += "\t\treturn "+dbhelperStr+".insertEntity("+methodParamStr+");\r\n\t}\r\n";
			
			daoFileStrMothed += "}";
			File fileDao = new File("E:/generation/dao/"+className+"Dao.java");
			FileUtils.write(fileDao, daoFileStr+daoFileStrMothed, "UTF-8");
			System.out.println(fileDao.getAbsolutePath()+"生成成功");
			
			//生成service
			serviceFileStrMothed += "\t@Override\r\n\tpublic Pagination<"+className+"> queryList"+className+"ByPage(Pagination<"+className+"> page) throws Exception{\r\n";
			serviceFileStrMothed += "\t\treturn "+methodParamStr+"Dao.queryList"+className+"ByPage(page);\r\n\t}\r\n";
			
			serviceFileStrMothed += "\t@Override\r\n\tpublic int queryList"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			serviceFileStrMothed += "\t\treturn "+methodParamStr+"Dao.queryList"+className+"("+methodParamStr+");\r\n\t}\r\n";
			
			serviceFileStrMothed += "\t@Override\r\n\tpublic int queryOne"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			serviceFileStrMothed += "\t\treturn "+methodParamStr+"Dao.queryOne"+className+"("+methodParamStr+");\r\n\t}\r\n";
			
			serviceFileStrMothed += "\t@Override\r\n\tpublic int delete"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			serviceFileStrMothed += "\t\treturn "+methodParamStr+"Dao.delete"+className+"("+methodParamStr+");\r\n\t}\r\n";
			
			serviceFileStrMothed += "\t@Override\r\n\tpublic int update"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			serviceFileStrMothed += "\t\treturn "+methodParamStr+"Dao.update"+className+"("+methodParamStr+");\r\n\t}\r\n";
			
			serviceFileStrMothed += "\t@Override\r\n\tpublic int insert"+className+"("+className+" "+methodParamStr+") throws Exception{\r\n";
			serviceFileStrMothed += "\t\treturn "+methodParamStr+"Dao.insert"+className+"("+methodParamStr+");\r\n\t}\r\n";
			
			File fileService = new File("E:/generation/service/impl/"+className+"ServiceImpl.java");
			FileUtils.write(fileService, serviceFileStr+serviceFileStrMothed +"}", "UTF-8");
			System.out.println(fileService.getAbsolutePath()+"生成成功");
			//end
			
			//生成服务层接口
			String interFileStr = "";
			interFileStr += "\r\npublic interface "+className+"Service {\r\n";
			interFileStr += "\tpublic Pagination<"+className+"> queryList"+className+"ByPage(Pagination<"+className+"> page) throws Exception;\r\n";
			
			interFileStr += "\tpublic int queryList"+className+"("+className+" "+methodParamStr+") throws Exception;\r\n";
			
			interFileStr += "\tpublic int queryOne"+className+"("+className+" "+methodParamStr+") throws Exception;\r\n";
			
			interFileStr += "\tpublic int delete"+className+"("+className+" "+methodParamStr+") throws Exception;\r\n";
			
			interFileStr += "\tpublic int update"+className+"("+className+" "+methodParamStr+") throws Exception;\r\n";
			
			interFileStr += "\tpublic int insert"+className+"("+className+" "+methodParamStr+") throws Exception;\r\n";
			
			interFileStr += "}";
			File fileinter = new File("E:/generation/service/"+className+"Service.java");
			FileUtils.write(fileinter, interFileStr, "UTF-8");
			System.out.println(fileinter.getAbsolutePath()+"生成成功");
		}
	}
}
