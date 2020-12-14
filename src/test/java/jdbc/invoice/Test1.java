package jdbc.invoice;

import com.mycompany.springbasic1123.jdbc.invoice.entity.Invoice;
import com.mycompany.springbasic1123.jdbc.invoice.entity.Item;
import com.mycompany.springbasic1123.jdbc.invoice.entity.ItemProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Test1 {
    
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc-config.xml");
    JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    
    
    class ItemMapper implements RowMapper<Item>{

        @Override
        public Item mapRow(ResultSet rs, int i) throws SQLException {
            Item item = new Item();
            item.setId(rs.getInt("id")); // 注入 id
            item.setAmount(rs.getInt("amount"));  // 注入 amount
            
            // 注入 product 物件
            Integer ipid = rs.getInt("ipid");
            String sql = "SELECT * FROM ItemProduct WHERE id=?";
            ItemProduct product = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ItemProduct.class), ipid);
            item.setProduct(product); // 注入 product 物件
            
            // 注入 invoice 物件
            Integer invid = rs.getInt("invid");
            String sql2 = "SELECT * FROM Invoice WHERE id=?";
            Invoice invoice = jdbcTemplate.queryForObject(sql2, new BeanPropertyRowMapper<>(Invoice.class), invid);
            item.setInvoice(invoice); // 注入 invoice 物件
            
            return item;
        }
    
    }
    
    
    @Test
    public void t1(){
        String sql = "SELECT * FROM Item";
        List<Item> items = jdbcTemplate.query(sql, new ItemMapper());
        System.out.println(items);
    }
}
