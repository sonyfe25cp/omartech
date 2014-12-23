package cn.techwolf.data.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: ChenJie
 * Date: 20/10/14
 * Time: 10:27 AM
 * 使用方法：把create table的sql中需要做插入操作的行粘贴到文件中
 * 注意：不要有空行 && 记得该tablename
 */
public class AutoCreateSql {

    static Logger logger = LoggerFactory.getLogger(AutoCreateSql.class);

    public static void main(String[] args) {
        AutoCreateSql acs = new AutoCreateSql();
        acs.createInsertFromSql("/tmp/user.m");
//        acs.createSelectFromBean("/tmp/edu_th");
//        acs.createSelectFromSql("/tmp/r1.sql", true);
    }

    void createSelectFromBean(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("String sql = \"select ");
        try {
            List<String> lines = FileUtils.readLines(new File(path));
            for (String line : lines) {
                line = line.trim();
                if (line.trim().length() > 0) {
                    String[] split = line.split(" ");
                    String type = split[1];
                    String variable = split[2].substring(0, split[2].indexOf(";"));
                    sb.append(variable + ", ");
                }
            }
            sb.setLength(sb.length() - 2);
            sb.append(" from TABLE \";");
            System.out.println(sb.toString());
            System.out.println("try{");
            System.out.println("\tPreparedStatement psmt = conn.prepareStatement(sql);");
            System.out.println("\tResultSet rs = psmt.executeQuery();");
            System.out.println("\twhile(rs.next()){");
            for (String line : lines) {
                if (line.trim().length() > 0) {
                    String[] split = line.split(" ");
                    String type = split[1];
                    String variable = split[2].substring(0, split[2].indexOf(";"));
                    switch (type) {
                        case "int":
                            System.out.println("\t\tint " + variable + " = rs.getInt(\"" + variable + "\");");
                            break;
                        case "String":
                            System.out.println("\t\tString " + variable + " = rs.getString(\"" + variable + "\");");
                            break;
                        default:
                            System.out.println("something missed");
                            break;
                    }
                    System.out.println("\t\tobject." + variable + " = " + variable + ";");
                }
            }
            System.out.println("}");
            System.out.println("\trs.close();");
            System.out.println("\tpsmt.close();");
            System.out.println("} catch (SQLException e) {\n" +
                    "\te.printStackTrace();\n" +
                    "}");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    void createSelectFromSql(String path, boolean pub) {
        StringBuilder sb = new StringBuilder();
        sb.append("String sql = \"select ");
        try {
            List<String> lines = FileUtils.readLines(new File(path));
            for (String line : lines) {
                line = line.trim();
                if (line.trim().length() > 0) {
                    String[] split = line.split(" ");
                    String variable = split[0].replaceAll("`", "");
                    String type = split[1];
                    sb.append(variable + ", ");
                }
            }
            sb.setLength(sb.length() - 2);
            sb.append(" from TABLE \";");
            System.out.println(sb.toString());
            System.out.println("try{");
            System.out.println("\tPreparedStatement psmt = conn.prepareStatement(sql);");
            System.out.println("\tResultSet rs = psmt.executeQuery();");
            System.out.println("\twhile(rs.next()){");
            for (String line : lines) {
                line = line.trim();
                if (line.trim().length() > 0) {
                    String[] split = line.split(" ");
                    String variable = split[0].replaceAll("`", "");
                    String type = split[1].replaceAll("\\(.*\\)", "");
                    switch (type) {
                        case "int":
                            System.out.println("\t\tint " + variable + " = rs.getInt(\"" + variable + "\");");
                            break;
                        case "tinyint":
                            System.out.println("\t\tint " + variable + " = rs.getInt(\"" + variable + "\");");
                            break;
                        case "bigint":
                            System.out.println("\t\tlong " + variable + " = rs.getLong(\"" + variable + "\");");
                            break;
                        case "varchar":
                            System.out.println("\t\tString " + variable + " = rs.getString(\"" + variable + "\");");
                            break;
                        case "char":
                            System.out.println("\t\tString " + variable + " = rs.getString(\"" + variable + "\");");
                            break;
                        case "text":
                            System.out.println("\t\tString " + variable + " = rs.getString(\"" + variable + "\");");
                            break;
                        case "mediumtext":
                            System.out.println("\t\tString " + variable + " = rs.getString(\"" + variable + "\");");
                            break;
                        case "longtext":
                            System.out.println("\t\tString " + variable + " = rs.getString(\"" + variable + "\");");
                            break;
                        case "date":
                            System.out.println("\t\tDate " + variable + " = rs.getDate(\"" + variable + "\");");
                            break;
                        case "datetime":
                            System.out.println("\t\tDate " + variable + " = rs.getDate(\"" + variable + "\");");
                            break;
                        default:
                            System.out.println("something missed : " + split[1]);
                            break;
                    }
                    if (pub) {
                        System.out.println("\t\tobject." + variable + " = " + variable + ";");
                    } else {
                        System.out.println("\t\tobject.set" + headUpperCase(variable) + "(" + variable + ");");
                    }
                }
            }
            System.out.println("}");
            System.out.println("\trs.close();");
            System.out.println("\tpsmt.close();");
            System.out.println("} catch (SQLException e) {\n" +
                    "\te.printStackTrace();\n" +
                    "}");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    String headUpperCase(String str) {
        String head = "";
        String first = str.charAt(0) + "";
        first = first.toUpperCase();
        head = first + str.substring(1);
        return head;
    }

    void createInsertFromSql(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = FileUtils.readLines(new File(path));
            sb.append("insert into TABLENAME(");
            for (String line : lines) {
                line = line.trim();
                String[] split = line.split(" ");
                String name = split[0];
                String type = split[1];
                sb.append(name + ", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append(") values(");
            for (int i = 0; i < lines.size(); i++) {
                sb.append("?, ");
            }
            sb.setLength(sb.length() - 2);
            sb.append(");");
            System.out.println("String sql = \"" + sb.toString() + "\";");//sql
            int index = 1;
            System.out.println("try{");
            System.out.println("PreparedStatement psmt = conn.prepareStatement(sql);");
            for (String line : lines) {
                line = line.trim();
                String[] split = line.split(" ");
                String name = split[0].replaceAll("`", "");
                String type = split[1].toLowerCase();
                if (type.startsWith("int")) {
                    String psmt = "psmt.setInt(" + index + ", " + name + ");//" + name;
                    System.out.println(psmt);
                } else if (type.startsWith("varchar") || type.contains("text")) {
                    String psmt = "psmt.setString(" + index + ", " + name + ");//" + name;
                    System.out.println(psmt);
                } else if (type.equals("date")) {
                    String psmt = "psmt.setDate(" + index + ", " + name + ");//" + name;
                    System.out.println(psmt);
                } else if (type.equals("datetime")) {
                    String psmt = "psmt.setTimestamp(" + index + ", " + name + ");//" + name;
                    System.out.println(psmt);
                } else if (type.startsWith("bigint")) {
                    String psmt = "psmt.setLong(" + index + ", " + name + ");//" + name;
                    System.out.println(psmt);
                }else{
                    System.out.println(type);
                }
                index++;
            }
            System.out.println("psmt.execute();");
            System.out.println("psmt.close();");
            System.out.println("} catch (SQLException e) {\n" +
                    "\te.printStackTrace();\n" +
                    "}");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
