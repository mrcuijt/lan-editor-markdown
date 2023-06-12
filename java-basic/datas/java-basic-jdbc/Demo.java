
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.log.Log;

public class Demo {

  private static final Logger logger = LoggerFactory.getLogger(Demo.class);
  private static Log log;

  public static void main(String[] args){
    run();
  }

  public static void run(){
    runAddBatch();
    query();
  }

  public static void runAddBatch(){

    String sql = "INSERT INTO user1(name, age) values (?,?)";

    User user1 = new User();
    user1.setName("zhangsan");
    user1.setAge(22);
    User user2 = new User();
    user2.setName("zhangsan1");
    user2.setAge(23);

    Connection conn = JDBCUtil.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {

      // MySQL Logger
      log = ((MySQLConnection)conn).getLog();
      log.logInfo("Get MySQL logger: " + log.getClass());
      log.logInfo("Get MySQL Connection:" + conn.getClass());

      // set auto commit false, like begin transaction
      conn.setAutoCommit(false);

      // MySQL Driver version 5.1.17 later, get auto increment
      // need PrepareStatement.RETURN_GENERATED_KEYS parameter
      ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

      ps.setString(1, user1.getName());
      ps.setInt(2, user1.getAge());
      ps.addBatch();
      ps.setString(1, user2.getName());
      ps.setInt(2, user2.getAge());
      ps.addBatch();

      // execute all batch
      // each of result for ever SQL affected row
      int[] results = ps.executeBatch();
      // clear batch cache
      ps.clearBatch();
      // clear batch parameter
      ps.clearParameters();
      // commit transaction
      conn.commit();

      // reset auto commit : true // for connection pool use
      try { 
        if(conn != null) conn.setAutoCommit(true);
      } catch(SQLException e){ 
        e.printStackTrace();
      }

      logger.info("{}", results);

    } catch(SQLException e){
      try { conn.rollback(); } catch(SQLException e1){ e1.printStackTrace(); }
      e.printStackTrace();
    } catch(Exception e){
      try { conn.rollback(); } catch(SQLException e1){ e1.printStackTrace(); }
      e.printStackTrace();
    } finally {
      // reset auto commit : true // for connection pool use
      try { 
        if(conn != null) conn.setAutoCommit(true);
      } catch(SQLException e){ 
        e.printStackTrace();
      }
      JDBCUtil.closeAll(rs, ps, conn);
    }
  }

  public static void query(){

    List<User> users = new ArrayList<User>();

    String sql = "SELECT uid,name,age FROM user1";

    Connection conn = JDBCUtil.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
        User user = convertResultSetToUser(rs);
        users.add(user);
      }
    } catch(SQLException e){
      e.printStackTrace();
    } catch(Exception e){
      e.printStackTrace();
    } finally {
      JDBCUtil.closeAll(rs, ps, conn);
    }

    for(User user : users){
      logger.info("{} {} {}", user.getUid(), user.getName(), user.getAge());
    }

  }

  public static User convertResultSetToUser(ResultSet rs) throws SQLException {
    User user = new User();
    user.setUid(rs.getInt("uid"));
    user.setName(rs.getString("name"));
    user.setAge(rs.getInt("age"));
    return user;
  }

}
